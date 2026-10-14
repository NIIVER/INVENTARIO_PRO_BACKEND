package com.sistemas.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistemas.service.AuditoriaAsyncService;

@RestController
@RequestMapping("/api/socket")
@CrossOrigin(origins = "http://localhost:4200")
public class SocketController {

	@Autowired
	private AuditoriaAsyncService auditoriaService;
	
    @GetMapping("/enviar")
    public ResponseEntity<String> enviarMensajeASocket(@RequestParam String mensaje) {
        String host = "localhost";
        int puerto = 12345;
        String respuestaServidor = "";
    
        try (Socket socket = new Socket(host, puerto);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.write(mensaje + "\n");
            out.flush();

            respuestaServidor = in.readLine();

        } catch (IOException e) {
            return ResponseEntity.ok("🚨 Servidor de Sockets inactivo en puerto 12345. Reinicia tu backend.");
        }

        return ResponseEntity.ok(respuestaServidor);
    }

    @GetMapping("/estado/monitoreo")
    public ResponseEntity<Map<String, Object>> obtenerMonitoreo() {
        Map<String, Object> estado = new HashMap<>();
        
        File archivo = new File("productos_live_backup.dat");
        if (archivo.exists()) {
            estado.put("backupExiste", true);
            estado.put("backupTamano", archivo.length() + " bytes");
            estado.put("backupRuta", archivo.getName());
        } else {
            estado.put("backupExiste", false);
            estado.put("backupTamano", "0 bytes");
            estado.put("backupRuta", "Pendiente de primer registro");
        }

        estado.put("socketPuerto", "12345");
        estado.put("socketEstado", "Activo y Escuchando");

        return ResponseEntity.ok(estado);
    }

    @GetMapping("/tareas/ejecutar")
    public ResponseEntity<String> ejecutarTarea(@RequestParam Long id) {
        auditoriaService.verificarEstadoEntregaCliente(id); 
        return ResponseEntity.ok("Solicitud de auditoría para el movimiento ID " + id + " recibida."); 
    }

    @GetMapping("/tareas/estados")
    public ResponseEntity<Map<Long, String>> consultarEstados() {
        // 🟢 CORREGIDO: Llamada al nombre exacto de tu método del servicio
        return ResponseEntity.ok(auditoriaService.obtenerEstadosEntregas());
    }
}