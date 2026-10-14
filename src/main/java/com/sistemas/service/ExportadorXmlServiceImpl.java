package com.sistemas.service;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;


import com.sistemas.dto.ListaProductosXml;
import com.sistemas.model.Producto;
import com.sistemas.repository.ProductoRepository;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

@Service
public class ExportadorXmlServiceImpl implements ExportadorXmlService {

    private final ProductoRepository productoRepository;
    // Guardaremos el archivo en la raíz del proyecto, simulando el volumen de telemetría de tu almacén
    private final String RUTA_XML = "productos_live_backup.xml"; 

    public ExportadorXmlServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Map<String, Object> exportarInventarioAXml() {
        Map<String, Object> resultado = new HashMap<>();
        try {
            // 1. Extraemos los productos reales directamente de tu base de datos de Almacén
            List<Producto> productosBD = productoRepository.findAll();
            ListaProductosXml envoltura = new ListaProductosXml(productosBD);

            // 2. Inicializamos el contexto JAXB con Jakarta para la serialización (Marshalling)
            JAXBContext contexto = JAXBContext.newInstance(ListaProductosXml.class);
            Marshaller marshaller = contexto.createMarshaller();
            
            // Configuramos salida formateada para que el archivo XML sea totalmente legible
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            // Escribimos el flujo de datos directamente en el archivo físico del disco duro
            File archivoDestino = new File(RUTA_XML);
            marshaller.marshal(envoltura, archivoDestino);

            resultado.put("status", "SUCCESS");
            resultado.put("mensaje", "Catálogo serializado exitosamente a XML.");
            resultado.put("archivo_ruta", archivoDestino.getAbsolutePath());
            resultado.put("total_exportado", productosBD.size());
            
        } catch (Exception e) {
            resultado.put("status", "ERROR");
            resultado.put("error", e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public Map<String, Object> importarInventarioDesdeXml() {
        Map<String, Object> resultado = new HashMap<>();
        try {
            File archivoSource = new File(RUTA_XML);
            if (!archivoSource.exists()) {
                resultado.put("status", "ERROR");
                resultado.put("mensaje", "No se encontró ningún archivo de respaldo XML para deserializar.");
                return resultado;
            }

            // 3. Inicializamos el deserializador (Unmarshaller) para reconstruir los objetos en memoria
            JAXBContext contexto = JAXBContext.newInstance(ListaProductosXml.class);
            Unmarshaller unmarshaller = contexto.createUnmarshaller();
            
            ListaProductosXml datosLeidos = (ListaProductosXml) unmarshaller.unmarshal(archivoSource);
            
            resultado.put("status", "SUCCESS");
            resultado.put("mensaje", "Archivo XML deserializado correctamente.");
            resultado.put("productos_detectados", datosLeidos.getProductos().size());
            
            // Log de control para verificar la reconstrucción de los objetos en la consola de Spring Boot
            System.out.println("📦 [JAXB DESERIALIZACIÓN] Objetos reconstruidos:");
            datosLeidos.getProductos().forEach(p -> System.out.println("-> ID: " + p.getId() + " | Producto: " + p.getNombre() + " | Stock Físico: " + p.getStock()));

        } catch (Exception e) {
            resultado.put("status", "ERROR");
            resultado.put("error", e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
}