package com.sistemas.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "productos")
@XmlRootElement(name = "producto")
@XmlAccessorType(XmlAccessType.FIELD) 
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id") 
    private Long id;
    
    @XmlElement(name = "nombre")
    private String nombre;
    
    @XmlElement(name = "descripcion")
    private String descripcion;
    
    @XmlElement(name = "precio")
    private Double precio;
    
    @XmlElement(name = "stock")
    private Integer stock;
    
    @XmlElement(name = "stockMinimo")
    private Integer stockMinimo; 
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    @XmlElement(name = "imagenUrl")
    private String imagenUrl;    

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    @XmlTransient 
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @XmlTransient 
    private Categoria categoria;

    public Producto() {}

    public String getEstadoStock() {
        if (this.stock == 0) {
            return "Crítico";
        } else if (this.stock <= this.stockMinimo) {
            return "Bajo stock";
        }
        return "Normal";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}