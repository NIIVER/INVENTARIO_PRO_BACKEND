package com.sistemas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemas.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

}
