package com.sistemas.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemas.model.MovimientoInventario;
import com.sistemas.repository.MovimientoRepository;

@Service
public class ReporteServiceImpl implements ReporteService {

    private final MovimientoRepository movimientoRepo;

    public ReporteServiceImpl(MovimientoRepository movimientoRepo) {
        this.movimientoRepo = movimientoRepo;
    }

    @Override
    public List<MovimientoInventario> generarReporteMovimientos(LocalDateTime inicio, LocalDateTime fin) {
        return movimientoRepo.findByFechaBetween(inicio, fin);
    }
}