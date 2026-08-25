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
    private String nombreCompleto;
    private String ced_perso;

    public Propietario() {
    }

    public Propietario(String ID_propietario, String Observaci_propietario, String nombreCompleto, String ced_perso) {
        this.ID_propietario = ID_propietario;
        this.Observaci_propietario = Observaci_propietario;
        this.nombreCompleto = nombreCompleto;
        this.ced_perso = ced_perso;
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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCed_perso() {
        return ced_perso;
    }

    public void setCed_perso(String ced_perso) {
        this.ced_perso = ced_perso;
    }

    @Override
    public String toString() {
        return "Propietario{" + "ID_propietario=" + ID_propietario + ", Observaci_propietario=" + Observaci_propietario + ", nombreCompleto=" + nombreCompleto + ", ced_perso=" + ced_perso + '}';
    }
    
}
