package com.sistemas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


import com.sistemas.model.Producto;

public interface ProductoService {
	List<Producto> listar();
    Producto guardar(Producto producto);
    Producto obtenerPorId(Long id);
    void eliminar(Long id);
    Page<Producto> listarPaginado(Pageable pageable);
    List<Producto> listarBajoStock();
}
