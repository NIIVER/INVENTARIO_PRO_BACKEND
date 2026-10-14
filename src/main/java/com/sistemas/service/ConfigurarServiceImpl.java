package com.sistemas.service;

import org.springframework.stereotype.Service;

import com.sistemas.model.ConfiguracionSistema;
import com.sistemas.repository.ConfiguracionRepository;
@Service
public class ConfigurarServiceImpl implements ConfigurarService {
	private final ConfiguracionRepository repo;

    public ConfigurarServiceImpl(ConfiguracionRepository repo) {
        this.repo = repo;
    }

    @Override
    public ConfiguracionSistema obtenerConfiguracion() {
        
        return repo.findAll().stream().findFirst().orElseGet(() -> {
            ConfiguracionSistema defecto = new ConfiguracionSistema();
            defecto.setNombreDelSistema("InventarioPro");
            defecto.setMoneda("Soles (S/.)");
            defecto.setZonaHoraria("(UTC-05:00) Lima");
            return repo.save(defecto);
        });
    }

    @Override
    public ConfiguracionSistema actualizarConfiguracion(ConfiguracionSistema configuracion) {
        ConfiguracionSistema existente = obtenerConfiguracion();
        existente.setNombreDelSistema(configuracion.getNombreDelSistema());
        existente.setMoneda(configuracion.getMoneda());
        existente.setZonaHoraria(configuracion.getZonaHoraria());
        existente.setLogoUrl(configuracion.getLogoUrl());
        return repo.save(existente);
    }

}
