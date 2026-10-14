package com.sistemas.service;

import java.time.LocalDateTime;
import java.util.List;

import com.sistemas.model.MovimientoInventario;

public interface ReporteService {
	List<MovimientoInventario> generarReporteMovimientos(LocalDateTime inicio, LocalDateTime fin);
}
