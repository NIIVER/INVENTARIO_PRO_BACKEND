package com.sistemas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sistemas.model.Categoria;

public interface CategoriaService {
	 List<Categoria> listar();
	    Optional<Categoria> obtenerPorId(Long id);
	    Categoria crear(Categoria categoria);
	    Categoria actualizar(Long id, Categoria categoria);
	    void eliminar(Long id);
	    Page<Categoria> listarPaginado(Pageable pageable);
}
