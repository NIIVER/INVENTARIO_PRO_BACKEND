package com.sistemas.controller;

import com.sistemas.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sistemas.repository.ProductoRepository;
import com.sistemas.repository.ProveedorRepository;
import com.sistemas.model.MovimientoInventario;
import com.sistemas.repository.CategoriaRepository;
import com.sistemas.repository.MovimientoRepository;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final MovimientoRepository movimientoRepository; 

    public DashboardController(ProductoRepository productoRepository, 
                               ProveedorRepository proveedorRepository, 
                               MovimientoRepository movimientoRepository, 
                               UsuarioRepository usuarioRepository,
                               CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.movimientoRepository = movimientoRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> obtenerResumen() {
        Map<String, Object> respuesta = new HashMap<>();

        long totalProductos = productoRepository.count();
        long totalProveedores = proveedorRepository.count();
        long totalUsuarios = usuarioRepository.count(); 
        long totalCategorias = categoriaRepository.count();
        
        long stockTotal = productoRepository.findAll().stream()
                .mapToLong(p -> p.getStock() != null ? p.getStock() : 0)
                .sum();
                
       
        long pedidosRealizados = movimientoRepository.findAll().stream()
                .filter(m -> m.getTipo() != null && "SALIDA".equals(m.getTipo().toString()))
                .count();

        respuesta.put("totalProductos", totalProductos);
        respuesta.put("totalProveedores", totalProveedores);
        respuesta.put("stockTotal", stockTotal);
        respuesta.put("pedidosRealizados", pedidosRealizados);
        respuesta.put("totalUsuarios", totalUsuarios);
        respuesta.put("totalCategorias", totalCategorias);

        
     
        List<MovimientoInventario> todosLosMovimientos = movimientoRepository.findAll();
        List<MovimientoInventario> ultimosMovimientos = todosLosMovimientos.stream()
                .sorted((m1, m2) -> m2.getId().compareTo(m1.getId()))
                .limit(5)
                .collect(Collectors.toList());
        
        respuesta.put("ultimosMovimientos", ultimosMovimientos);

        // 3. 🟢 MAPAS SEPARADOS DE ENERO A DICIEMBRE PARA ANGULAR
        Map<String, Integer> resumenEntradasMensuales = new LinkedHashMap<>();
        Map<String, Integer> resumenSalidasMensuales = new LinkedHashMap<>();
        
        String[] mesesNombres = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", 
                                 "JULIO", "AGOSTO", "SETIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};

   
        for (String mes : mesesNombres) {
            resumenEntradasMensuales.put(mes, 0);
            resumenSalidasMensuales.put(mes, 0);
        }

        
        for (MovimientoInventario mov : todosLosMovimientos) {
            if (mov.getFecha() != null && mov.getTipo() != null) {
                int numeroMes = mov.getFecha().getMonthValue(); 
                
                if (numeroMes >= 1 && numeroMes <= 12) {
                    String nombreMes = mesesNombres[numeroMes - 1];
                    int cantidad = mov.getCantidad() != null ? mov.getCantidad() : 0;
                    String tipoMov = mov.getTipo().toString(); 

                    if ("ENTRADA".equals(tipoMov)) {
                        resumenEntradasMensuales.put(nombreMes, resumenEntradasMensuales.get(nombreMes) + cantidad);
                    } else if ("SALIDA".equals(tipoMov)) {
                        resumenSalidasMensuales.put(nombreMes, resumenSalidasMensuales.get(nombreMes) + cantidad);
                    }
                }
            }
        }

        // 5. 🟢 ENVIAR LAS PROPIEDADES EXACTAS QUE BUSCA TU ANGULAR
        respuesta.put("resumenEntradasMensuales", resumenEntradasMensuales);
        respuesta.put("resumenSalidasMensuales", resumenSalidasMensuales);

        return ResponseEntity.ok(respuesta);
    }
}