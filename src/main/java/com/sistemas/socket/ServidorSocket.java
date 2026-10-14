package com.sistemas.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sistemas.model.Producto;
import com.sistemas.repository.ProductoRepository;

@Component
public class ServidorSocket implements CommandLineRunner {
    private final ProductoRepository productoRepo;

    public ServidorSocket(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        int puerto = 12345; 
        
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(puerto)) {
                System.out.println("🔌 [SERVER SOCKET] Escuchando consultas en puerto " + puerto);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    // Ejecución en hilos concurrentes para cumplir con la guía de laboratorio
                    new Thread(() -> manejarConsultasEmpleado(clientSocket)).start();
                }
            } catch (IOException e) {
                System.err.println("⚠️ [SERVER SOCKET] El puerto " + puerto + " ya está en uso o el servidor se reinició.");
            }
        }).start();
    }

    private void manejarConsultasEmpleado(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String consulta = inputLine.toLowerCase().trim();
                String respuesta = "";

                // 🟢 TEMA 2 Y 3: Intercepción de consultas distribuidas por Streams
                if (consulta.contains("stock bajo") || consulta.contains("alertas")) {
                    try {
                        List<Producto> stockBajo = productoRepo.findAll(); 
                        StringBuilder alertas = new StringBuilder("🚨 ALERTAS DE STOCK BAJO EN ALMACÉN: ");
                        boolean hayAlertas = false;

                        for (Producto p : stockBajo) {
                            if (p.getStock() <= 5) {
                                alertas.append("[").append(p.getNombre()).append(": ").append(p.getStock()).append(" und] ");
                                hayAlertas = true;
                            }
                        }
                        respuesta = hayAlertas ? alertas.toString() : "✅ Todo en orden. No hay productos con stock bajo.";
                    } catch (Exception e) {
                        // Captura si el contexto de la Base de Datos se cierra durante el reinicio masivo
                        respuesta = "⚠️ Servidor central restableciendo conexión. Intente de nuevo en unos instantes.";
                    }

                } else if (consulta.contains("buscar") || consulta.contains("stock de")) {
                    try {
                        String nombreBuscar = consulta.replace("buscar", "").replace("stock de", "").trim();
                        List<Producto> encontrados = productoRepo.findAll();
                        Producto productoEncontrado = null;

                        for (Producto p : encontrados) {
                            if (p.getNombre().toLowerCase().contains(nombreBuscar)) {
                                productoEncontrado = p;
                                break;
                            }
                        }

                        if (productoEncontrado != null) {
                            respuesta = "📦 " + productoEncontrado.getNombre() + " -> Stock actual: " + productoEncontrado.getStock() + " unidades.";
                        } else {
                            respuesta = "❌ No encontré ningún producto que coincida con '" + nombreBuscar + "'.";
                        }
                    } catch (Exception e) {
                        respuesta = "⚠️ Error de lectura asíncrona en el repositorio de datos.";
                    }

                } else {
                    respuesta = "🤖 Servidor central: Consulta recibida. Intente usando palabras clave como 'stock bajo' o 'buscar [nombre_producto]'.";
                }

                out.write(respuesta + "\n");
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("💻 Terminal del empleado desconectada del stream.");
        }
    }
}