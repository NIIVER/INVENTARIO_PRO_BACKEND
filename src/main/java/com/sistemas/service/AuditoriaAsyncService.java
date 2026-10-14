package com.sistemas.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sistemas.model.MovimientoInventario;
import com.sistemas.repository.MovimientoRepository;

@Service
public class AuditoriaAsyncService {

    private final MovimientoRepository movimientoRepo;
    // Almacena el estado de procesamiento del hilo para Angular
    private final Map<Long, String> estadosEntregas = new ConcurrentHashMap<>();

    // Inyectamos tu repositorio de movimientos existente
    public AuditoriaAsyncService(MovimientoRepository movimientoRepo) {
        this.movimientoRepo = movimientoRepo;
    }

    @Async("apiExecutor") // Usa el pool de hilos administrado del laboratorio [cite: 54, 61]
    public void verificarEstadoEntregaCliente(Long idMovimiento) {
        String nombreHilo = Thread.currentThread().getName();
        estadosEntregas.put(idMovimiento, "⏳ El hilo " + nombreHilo + " está verificando el comprobante de entrega...");

        try {
            Thread.sleep(4000); // Simula el tiempo de respuesta de la red distribuida 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Buscamos el movimiento real en tu base de datos
        Optional<MovimientoInventario> movOpt = movimientoRepo.findById(idMovimiento);
        
        if (movOpt.isPresent()) {
            MovimientoInventario mov = movOpt.get();
            // Lógica de negocio: Si tiene destino asignado y no está dañado, se asume entregado
            if (mov.getDestino() != null && !mov.getDestino().toLowerCase().contains("dañado")) {
                estadosEntregas.put(idMovimiento, "✅ ENTRÈGADO - Destino: " + mov.getDestino() + " (Verificado por " + nombreHilo + ")");
            } else {
                estadosEntregas.put(idMovimiento, "❌ RECHAZADO / PENDIENTE - Revisar mermas o destino (Verificado por " + nombreHilo + ")");
            }
        } else {
            estadosEntregas.put(idMovimiento, "⚠️ Alerta: El movimiento ID " + idMovimiento + " no existe en el sistema.");
        }
    }

    public Map<Long, String> obtenerEstadosEntregas() {
        return estadosEntregas;
    }
}
