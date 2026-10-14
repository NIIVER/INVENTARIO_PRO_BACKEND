package com.sistemas.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemas.dto.DashboardResponse;
import com.sistemas.model.Producto;
import com.sistemas.model.MovimientoInventario;
import com.sistemas.repository.MovimientoRepository;
import com.sistemas.repository.ProductoRepository;
import com.sistemas.repository.ProveedorRepository;
import com.sistemas.repository.UsuarioRepository;  
import com.sistemas.repository.CategoriaRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProductoRepository productoRepo;
    private final ProveedorRepository proveedorRepo;
    private final MovimientoRepository movimientoRepo;
    private final UsuarioRepository usuarioRepo;     
    private final CategoriaRepository categoriaRepo; 

    
    public DashboardServiceImpl(ProductoRepository productoRepo, 
                                ProveedorRepository proveedorRepo, 
                                MovimientoRepository movimientoRepo,
                                UsuarioRepository usuarioRepo,
                                CategoriaRepository categoriaRepo) {
        this.productoRepo = productoRepo;
        this.proveedorRepo = proveedorRepo;
        this.movimientoRepo = movimientoRepo;
        this.usuarioRepo = usuarioRepo;
        this.categoriaRepo = categoriaRepo;
    }

    @Override
    public DashboardResponse obtenerResumenAvanzado() {

        long totalProductos = productoRepo.count();
        long totalProveedores = proveedorRepo.count();
        long totalUsuarios = usuarioRepo.count();      
        long totalCategorias = categoriaRepo.count();   
        long pedidosRealizados = movimientoRepo.count();
        
        int stockTotal = productoRepo.findAll().stream()
                .mapToInt(Producto::getStock)
                .sum();

       
        List<MovimientoInventario> ultimosMovimientos = movimientoRepo.findAllByOrderByFechaDesc();

        Map<String, Integer> entradasAnuales = inicializarMapaAnual();
        Map<String, Integer> salidasAnuales = inicializarMapaAnual();

        for (MovimientoInventario mov : ultimosMovimientos) {
            if (mov.getFecha() != null && mov.getTipo() != null) {
                String mesClave = traducirMes(mov.getFecha().getMonth().name());
                String tipo = mov.getTipo().name(); // ENTRADA o SALIDA

                if ("ENTRADA".equals(tipo)) {
                    entradasAnuales.put(mesClave, entradasAnuales.getOrDefault(mesClave, 0) + mov.getCantidad());
                } else if ("SALIDA".equals(tipo)) {
                    salidasAnuales.put(mesClave, salidasAnuales.getOrDefault(mesClave, 0) + mov.getCantidad());
                }
            }
        }


        return new DashboardResponse(totalProductos, totalProveedores, stockTotal, pedidosRealizados,
                totalUsuarios, totalCategorias, ultimosMovimientos, entradasAnuales,  salidasAnuales
        );
    }

    private Map<String, Integer> inicializarMapaAnual() {
        Map<String, Integer> mapa = new LinkedHashMap<>();
        mapa.put("ENERO", 0);
        mapa.put("FEBRERO", 0);
        mapa.put("MARZO", 0);
        mapa.put("ABRIL", 0);
        mapa.put("MAYO", 0);
        mapa.put("JUNIO", 0);
        mapa.put("JULIO", 0);
        mapa.put("AGOSTO", 0);
        mapa.put("SETIEMBRE", 0);
        mapa.put("OCTUBRE", 0);
        mapa.put("NOVIEMBRE", 0);
        mapa.put("DICIEMBRE", 0);
        return mapa;
    }

   
    private String traducirMes(String mesIngles) {
        switch (mesIngles) {
            case "JANUARY": return "ENERO";
            case "FEBRUARY": return "FEBRERO";
            case "MARCH": return "MARZO";
            case "APRIL": return "ABRIL";
            case "MAY": return "MAYO";
            case "JUNE": return "JUNIO";
            case "JULY": return "JULIO";
            case "AUGUST": return "AGOSTO";
            case "SEPTEMBER": return "SETIEMBRE";
            case "OCTOBER": return "OCTUBRE";
            case "NOVEMBER": return "NOVIEMBRE";
            case "DECEMBER": return "DICIEMBRE";
            default: return "ENERO";
        }
    }
}