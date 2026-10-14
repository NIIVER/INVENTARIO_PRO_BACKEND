package com.sistemas.controller;

import com.sistemas.model.Proveedor;
import com.sistemas.repository.ProveedorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores-soap")
@CrossOrigin(origins = "http://localhost:4200")
public class ProveedorSoapRestController {

    private final ProveedorRepository proveedorRepository;

    public ProveedorSoapRestController(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Proveedor>> buscarProveedorViaSoap(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorRepository.findById(id));
    }
}