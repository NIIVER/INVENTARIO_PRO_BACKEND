package com.sistemas.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistemas.model.MovimientoInventario;
import com.sistemas.service.ReporteService;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "http://localhost:4200")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // Obtiene los datos filtrados para armar reportes (Pantalla 13)
    @GetMapping("/movimientos")
    public ResponseEntity<List<MovimientoInventario>> generarReporte(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        
        // Convertimos fechas de inicio y fin a LocalDateTime completo
        LocalDateTime fechaInicio = desde.atStartOfDay();
        LocalDateTime fechaFin = hasta.atTime(LocalTime.MAX);

        List<MovimientoInventario> datosReporte = reporteService.generarReporteMovimientos(fechaInicio, fechaFin);
        return ResponseEntity.ok(datosReporte);
    }
}
