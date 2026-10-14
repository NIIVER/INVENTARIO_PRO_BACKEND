package com.sistemas.dto;

import java.time.LocalDateTime;

public class MovimientoRequest {
	private Long productoId;
    private Integer cantidad;
    private String motivo; 
    private String destino;
    private String fecha;// Ej: "Compra de lote" (Entrada) o "Merma" (Salida)
    private Long proveedorId; // ID del usuario que realiza la acción

    public MovimientoRequest() {}

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

	public Long getProveedorId() {
		return proveedorId;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

   
}
