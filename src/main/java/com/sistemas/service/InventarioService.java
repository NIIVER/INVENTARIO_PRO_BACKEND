package com.sistemas.service;

import java.util.List;

import com.sistemas.dto.MovimientoRequest;
import com.sistemas.model.MovimientoInventario;

public interface InventarioService {
	MovimientoInventario registrarEntrada(MovimientoRequest request);
    MovimientoInventario registrarSalida(MovimientoRequest request);
    List<MovimientoInventario> listarHistorial();
}
