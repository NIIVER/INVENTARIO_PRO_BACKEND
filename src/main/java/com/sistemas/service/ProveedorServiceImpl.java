package com.sistemas.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sistemas.model.Proveedor;
import com.sistemas.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl  implements ProveedorService{
	private ProveedorRepository repo;

    public ProveedorServiceImpl(ProveedorRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Proveedor> listar() { return repo.findAll(); }

    @Override
    public Proveedor guardar(Proveedor proveedor) { return repo.save(proveedor); }

    @Override
    public Proveedor obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) { repo.deleteById(id); }
    @Override
    public Page<Proveedor> listarPaginado(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
