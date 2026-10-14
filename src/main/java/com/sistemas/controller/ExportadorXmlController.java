package com.sistemas.controller;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sistemas.service.ExportadorXmlService;

@RestController
@RequestMapping("/api/inventario/xml")
@CrossOrigin(origins = "http://localhost:4200")
public class ExportadorXmlController {

    private final ExportadorXmlService xmlService;

    public ExportadorXmlController(ExportadorXmlService xmlService) {
        this.xmlService = xmlService;
    }

    // Endpoint para Serializar (Convertir Base de datos de Productos -> Archivo XML)
    @GetMapping("/exportar")
    public ResponseEntity<Map<String, Object>> exportarAXml() {
        return ResponseEntity.ok(xmlService.exportarInventarioAXml());
    }

    // Endpoint para Deserializar (Leer Archivo XML -> Reconstruir objetos e imprimirlos en consola)
    @GetMapping("/importar")
    public ResponseEntity<Map<String, Object>> importarDesdeXml() {
        return ResponseEntity.ok(xmlService.importarInventarioDesdeXml());
    }
}
