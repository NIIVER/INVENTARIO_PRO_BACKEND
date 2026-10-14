package com.sistemas.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemas.dto.MovimientoRequest;
import com.sistemas.model.MovimientoInventario;
import com.sistemas.model.Producto;
import com.sistemas.model.Proveedor;
import com.sistemas.model.TipoMovimiento;
import com.sistemas.repository.MovimientoRepository;
import com.sistemas.repository.ProductoRepository;
import com.sistemas.repository.ProveedorRepository;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final MovimientoRepository movimientoRepo;
    private final ProductoRepository productoRepo;
    private final ProveedorRepository proveedorRepository;

    public InventarioServiceImpl(MovimientoRepository movimientoRepo,
                                 ProductoRepository productoRepo,
                                 ProveedorRepository proveedorRepository) {
        this.movimientoRepo = movimientoRepo;
        this.productoRepo = productoRepo;
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    @Transactional
    public MovimientoInventario registrarEntrada(MovimientoRequest request) {
        Producto producto = productoRepo.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        // Aumentar stock
        producto.setStock(producto.getStock() + request.getCantidad());
        productoRepo.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setProveedor(proveedor);
        movimiento.setCantidad(request.getCantidad());
        movimiento.setMotivo(request.getMotivo());
        movimiento.setTipo(TipoMovimiento.ENTRADA);

        // 🟢 PROCESAMIENTO CORREGIDO DE FECHA
        if (request.getFecha() != null && !request.getFecha().trim().isEmpty()) {
            String fechaTexto = request.getFecha();
            
            if (fechaTexto.contains("T")) {
                fechaTexto = fechaTexto.substring(0, 10); 
            } // 👈 LLAVE CERRADA CORRECTAMENTE AQUÍ

            java.time.LocalDate fechaCalendario = java.time.LocalDate.parse(fechaTexto);
            movimiento.setFecha(fechaCalendario.atTime(java.time.LocalTime.now())); 
        } else {
            movimiento.setFecha(LocalDateTime.now());
        }

        return movimientoRepo.save(movimiento);
    }

    @Override
    @Transactional
    public MovimientoInventario registrarSalida(MovimientoRequest request) {
        Producto producto = productoRepo.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < request.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para realizar la salida");
        }

        // Disminuir stock
        producto.setStock(producto.getStock() - request.getCantidad());
        productoRepo.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setProveedor(null);
        movimiento.setDestino(request.getDestino());
        movimiento.setCantidad(request.getCantidad());
        movimiento.setMotivo(request.getMotivo());
        movimiento.setTipo(TipoMovimiento.SALIDA);
        
        // 🟢 PROCESAMIENTO CORREGIDO DE FECHA
        if (request.getFecha() != null && !request.getFecha().trim().isEmpty()) {
            String fechaTexto = request.getFecha();
            
            if (fechaTexto.contains("T")) {
                fechaTexto = fechaTexto.substring(0, 10); 
            } // 👈 LLAVE CERRADA CORRECTAMENTE AQUÍ

            java.time.LocalDate fechaCalendario = java.time.LocalDate.parse(fechaTexto);
            movimiento.setFecha(fechaCalendario.atTime(java.time.LocalTime.now())); 
        } else {
            movimiento.setFecha(LocalDateTime.now());
        }

        return movimientoRepo.save(movimiento);
    }

    @Override
    public List<MovimientoInventario> listarHistorial() {
        return movimientoRepo.findAllByOrderByFechaDesc();
    }
}