package com.sistemas.service;

import com.sistemas.model.ConfiguracionSistema;

public interface ConfigurarService {
	ConfiguracionSistema obtenerConfiguracion();
    ConfiguracionSistema actualizarConfiguracion(ConfiguracionSistema configuracion);
}
