package com.sistemas.dto;

import com.sistemas.model.Producto;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "catalogos_inventario") 
public class ListaProductosXml {

    private List<Producto> productos;

    public ListaProductosXml() {}

    public ListaProductosXml(List<Producto> productos) {
        this.productos = productos;
    }

    @XmlElement(name = "producto") 
    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
