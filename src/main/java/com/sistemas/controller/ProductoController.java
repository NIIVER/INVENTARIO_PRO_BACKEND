package com.sistemas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemas.model.Producto;
import com.sistemas.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {
    
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Producto> listar() { 
        return service.listar(); 
    }

    @PostMapping
    public Producto crear(@RequestBody Producto p) { 
        return service.guardar(p); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) { 
        Producto p = service.obtenerPorId(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto p) {
        Producto exist = service.obtenerPorId(id);
        if (exist == null) return ResponseEntity.notFound().build();
        
        exist.setNombre(p.getNombre());
        exist.setDescripcion(p.getDescripcion());
        exist.setPrecio(p.getPrecio());
        exist.setStock(p.getStock());
        exist.setStockMinimo(p.getStockMinimo()); // Actualiza stock mínimo (Pantalla 6)
        exist.setImagenUrl(p.getImagenUrl());     // Actualiza la imagen (Pantalla 6)
        exist.setProveedor(p.getProveedor());
        exist.setCategoria(p.getCategoria());
        
        return ResponseEntity.ok(service.guardar(exist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) { 
        service.eliminar(id); 
        return ResponseEntity.noContent().build();
    }

    // 🆕 NUEVO: Obtener alertas de stock (Pantalla 12)
    @GetMapping("/bajo-stock")
    public List<Producto> listarBajoStock() {
        return service.listarBajoStock();
    }
}
