package com.sistemas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemas.dto.MovimientoRequest;
import com.sistemas.model.MovimientoInventario;
import com.sistemas.service.InventarioService;

@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = "http://localhost:4200")
public class MovimientoController {

    private final InventarioService movimientoService;

    public MovimientoController(InventarioService movimientoService) {
        this.movimientoService = movimientoService;
    }

    // Registrar Entrada de Inventario (Pantalla 9)
    @PostMapping("/entrada")
    public ResponseEntity<MovimientoInventario> registrarEntrada(@RequestBody MovimientoRequest request) {
        try {
           return ResponseEntity.ok(movimientoService.registrarEntrada(request));
       } catch (RuntimeException e) {
           return ResponseEntity.badRequest().build();
       }
    }
  
    // Registrar Salida de Inventario (Pantalla 10)
    @PostMapping("/salida")
    public ResponseEntity<MovimientoInventario> registrarSalida(@RequestBody MovimientoRequest request) {
        try {
            return ResponseEntity.ok(movimientoService.registrarSalida(request));
        } catch (RuntimeException e) {
        	System.out.println(" El error es : " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Historial de Movimientos de Inventario (Pantalla 11)
    @GetMapping
    public List<MovimientoInventario> listarHistorial() {
        return movimientoService.listarHistorial();
    }
}
