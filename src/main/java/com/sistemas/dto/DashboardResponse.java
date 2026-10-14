package com.sistemas.dto;

import java.util.Map;
import java.util.List;
import com.sistemas.model.MovimientoInventario; 

public class DashboardResponse {
    private Long totalProductos;       
    private Long totalProveedores;     
    private Integer stockTotal;        
    private Long pedidosRealizados;   
    private Long totalUsuarios; 
    private Long totalCategorias; 
    
    private List<MovimientoInventario> ultimosMovimientos; 
    
    private Map<String, Integer> resumenMovimientosMensuales; 
    private Map<String, Integer> resumenEntradasMensuales; 
    private Map<String, Integer> resumenSalidasMensuales;


    public DashboardResponse() {}

    public DashboardResponse(Long totalProductos, Long totalProveedores, Integer stockTotal, 
                             Long pedidosRealizados, Long totalUsuarios, Long totalCategorias,
                             List<MovimientoInventario> ultimosMovimientos,
                             Map<String, Integer> resumenEntradasMensuales, 
                             Map<String, Integer> resumenSalidasMensuales) {
        this.totalProductos = totalProductos;
        this.totalProveedores = totalProveedores;
        this.stockTotal = stockTotal;
        this.pedidosRealizados = pedidosRealizados;
        this.totalUsuarios = totalUsuarios;
        this.totalCategorias = totalCategorias;
        this.ultimosMovimientos = ultimosMovimientos;
        this.resumenEntradasMensuales = resumenEntradasMensuales;
        this.resumenSalidasMensuales = resumenSalidasMensuales;
    }

    
    public Long getTotalProductos() { return totalProductos; }
    public void setTotalProductos(Long totalProductos) { this.totalProductos = totalProductos; }

    public Long getTotalProveedores() { return totalProveedores; }
    public void setTotalProveedores(Long totalProveedores) { this.totalProveedores = totalProveedores; }

    public Integer getStockTotal() { return stockTotal; }
    public void setStockTotal(Integer stockTotal) { this.stockTotal = stockTotal; }

    public Long getPedidosRealizados() { return pedidosRealizados; }
    public void setPedidosRealizados(Long pedidosRealizados) { this.pedidosRealizados = pedidosRealizados; }

    public Long getTotalUsuarios() { return totalUsuarios; }
    public void setTotalUsuarios(Long totalUsuarios) { this.totalUsuarios = totalUsuarios; }

    public Long getTotalCategorias() { return totalCategorias; }
    public void setTotalCategorias(Long totalCategorias) { this.totalCategorias = totalCategorias; }

    public List<MovimientoInventario> getUltimosMovimientos() { return ultimosMovimientos; }
    public void setUltimosMovimientos(List<MovimientoInventario> ultimosMovimientos) { this.ultimosMovimientos = ultimosMovimientos; }

    public Map<String, Integer> getResumenEntradasMensuales() { return resumenEntradasMensuales; }
    public void setResumenEntradasMensuales(Map<String, Integer> resumenEntradasMensuales) { this.resumenEntradasMensuales = resumenEntradasMensuales; }

    public Map<String, Integer> getResumenSalidasMensuales() { return resumenSalidasMensuales; }
    public void setResumenSalidasMensuales(Map<String, Integer> resumenSalidasMensuales) { this.resumenSalidasMensuales = resumenSalidasMensuales; }

    public Map<String, Integer> getResumenMovimientosMensuales() { return resumenMovimientosMensuales; }
    public void setResumenMovimientosMensuales(Map<String, Integer> resumenMovimientosMensuales) { this.resumenMovimientosMensuales = resumenMovimientosMensuales; }
}