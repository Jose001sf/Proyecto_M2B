/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Marca_repuesto {
    private String ID_marca_repuesto;
    private String Nombre_marca_repuesto;

    public Marca_repuesto() {
    }

    public Marca_repuesto(String ID_marca_repuesto, String Nombre_marca_repuesto) {
        this.ID_marca_repuesto = ID_marca_repuesto;
        this.Nombre_marca_repuesto = Nombre_marca_repuesto;
    }

    public String getID_marca_repuesto() {
        return ID_marca_repuesto;
    }

    public void setID_marca_repuesto(String ID_marca_repuesto) {
        this.ID_marca_repuesto = ID_marca_repuesto;
    }

    public String getNombre_marca_repuesto() {
        return Nombre_marca_repuesto;
    }

    public void setNombre_marca_repuesto(String Nombre_marca_repuesto) {
        this.Nombre_marca_repuesto = Nombre_marca_repuesto;
    }

    @Override
    public String toString() {
        return "Marca_repuesto{" + "ID_marca_repuesto=" + ID_marca_repuesto + ", Nombre_marca_repuesto=" + Nombre_marca_repuesto + '}';
    }
    
}
