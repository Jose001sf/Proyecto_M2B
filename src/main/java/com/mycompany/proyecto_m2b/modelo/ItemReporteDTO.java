
package com.mycompany.proyecto_m2b.modelo;

public class ItemReporteDTO {
    private String categoria;
    private String nombre;
    private int cantidad;

    public ItemReporteDTO(String categoria, String nombre, int cantidad) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
