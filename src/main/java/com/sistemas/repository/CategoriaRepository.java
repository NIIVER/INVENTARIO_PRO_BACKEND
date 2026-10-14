package com.sistemas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemas.model.Categoria;

public interface CategoriaRepository  extends JpaRepository<Categoria, Long>{

}