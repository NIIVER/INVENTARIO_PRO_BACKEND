package com.sistemas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemas.model.ConfiguracionSistema;
import com.sistemas.service.ConfigurarService;

@RestController
@RequestMapping("/api/configuracion")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfiguracionController {

    private final ConfigurarService configuracionService;

    public ConfiguracionController(ConfigurarService configuracionService) {
        this.configuracionService = configuracionService;
    }

    // Obtener configuración global actual
    @GetMapping
    public ResponseEntity<ConfiguracionSistema> obtenerConfiguracion() {
        return ResponseEntity.ok(configuracionService.obtenerConfiguracion());
    }

    // Guardar cambios del sistema (Pantalla 15)
    @PutMapping
    public ResponseEntity<ConfiguracionSistema> guardarConfiguracion(@RequestBody ConfiguracionSistema nuevaConfig) {
        return ResponseEntity.ok(configuracionService.actualizarConfiguracion(nuevaConfig));
    }
}