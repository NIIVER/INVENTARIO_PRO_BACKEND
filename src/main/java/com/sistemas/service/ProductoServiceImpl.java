package com.sistemas.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sistemas.model.Producto;
import com.sistemas.repository.ProductoRepository;



@Service
public class ProductoServiceImpl implements ProductoService {
    
    private final ProductoRepository repo;

    public ProductoServiceImpl(ProductoRepository repo) {
        this.repo = repo;
    }
    
    @Override
    public Page<Producto> listarPaginado(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public List<Producto> listar() { return repo.findAll(); }

    @Override
    public Producto guardar(Producto producto) { return repo.save(producto); }

    @Override
    public Producto obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) { repo.deleteById(id); }

    @Override
    public List<Producto> listarBajoStock() {
        return repo.findProductosBajoStock();
    }
}
