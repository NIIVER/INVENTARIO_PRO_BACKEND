package com.sistemas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemas.model.MovimientoInventario;
import com.sistemas.model.TipoMovimiento;

public interface MovimientoRepository extends JpaRepository<MovimientoInventario, Long> {
	
    List<MovimientoInventario> findAllByOrderByFechaDesc();

    
    List<MovimientoInventario> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    
    long countByTipo(TipoMovimiento tipo);
}
