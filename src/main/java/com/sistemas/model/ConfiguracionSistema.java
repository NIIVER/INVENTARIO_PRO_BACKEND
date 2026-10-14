package com.sistemas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "configuracion_sistema")
public class ConfiguracionSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreDelSistema; 
    private String moneda; 
    private String zonaHoraria; 
    private String logoUrl;

    public ConfiguracionSistema() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreDelSistema() { return nombreDelSistema; }
    public void setNombreDelSistema(String nombreDelSistema) { this.nombreDelSistema = nombreDelSistema; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public String getZonaHoraria() { return zonaHoraria; }
    public void setZonaHoraria(String zonaHoraria) { this.zonaHoraria = zonaHoraria; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
}
