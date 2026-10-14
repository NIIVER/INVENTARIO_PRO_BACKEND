package com.sistemas.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sistemas.model.Proveedor;

public interface ProveedorService {
	 List<Proveedor> listar();
	    Proveedor guardar(Proveedor proveedor);
	    Proveedor obtenerPorId(Long id);
	    void eliminar(Long id);
	    Page<Proveedor> listarPaginado(Pageable pageable);
}
