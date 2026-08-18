/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Empleado {
    private String ID_empleado;

    public Empleado() {
    }

    public Empleado(String ID_empleado) {
        this.ID_empleado = ID_empleado;
    }

    public String getID_empleado() {
        return ID_empleado;
    }

    public void setID_empleado(String ID_empleado) {
        this.ID_empleado = ID_empleado;
    }

    @Override
    public String toString() {
        return "Empleado{" + "ID_empleado=" + ID_empleado + '}';
    }
    
}
