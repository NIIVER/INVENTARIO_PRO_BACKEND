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

import com.sistemas.model.Proveedor;
import com.sistemas.service.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "http://localhost:4200")

public class ProveedorController {
	private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Proveedor> listar() { return service.listar(); }

    @PostMapping
    public Proveedor crear(@RequestBody Proveedor p) { return service.guardar(p); }

    @GetMapping("/{id}")
    public Proveedor obtener(@PathVariable Long id) { return service.obtenerPorId(id); }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long id, @RequestBody Proveedor p) {
        Proveedor exist = service.obtenerPorId(id);
        if (exist == null) {
            return ResponseEntity.notFound().build();
        }
        exist.setNombre(p.getNombre());
        exist.setEmail(p.getEmail());
        exist.setRuc(p.getRuc());
        exist.setTelefono(p.getTelefono());
     
        return ResponseEntity.ok(service.guardar(exist));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}

