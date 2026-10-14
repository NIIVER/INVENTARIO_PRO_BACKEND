package com.sistemas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemas.model.ConfiguracionSistema;

public interface ConfiguracionRepository extends JpaRepository<ConfiguracionSistema, Long> {

}
