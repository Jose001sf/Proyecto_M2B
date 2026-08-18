/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Propietario {
    private String ID_propietario;
    private String Observaci_propietario;

    public Propietario() {
    }

    public Propietario(String ID_propietario, String Observaci_propietario) {
        this.ID_propietario = ID_propietario;
        this.Observaci_propietario = Observaci_propietario;
    }

    public String getID_propietario() {
        return ID_propietario;
    }

    public void setID_propietario(String ID_propietario) {
        this.ID_propietario = ID_propietario;
    }

    public String getObservaci_propietario() {
        return Observaci_propietario;
    }

    public void setObservaci_propietario(String Observaci_propietario) {
        this.Observaci_propietario = Observaci_propietario;
    }

    @Override
    public String toString() {
        return "Propietario{" + "ID_propietario=" + ID_propietario + ", Observaci_propietario=" + Observaci_propietario + '}';
    }
    
}
