/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Especialidad {
    private String ID_especialidad;
    private String Nom_especialidad;
    private String Descrip_especi;

    public Especialidad() {
    }

    public Especialidad(String ID_especialidad, String Nom_especialidad, String Descrip_especi) {
        this.ID_especialidad = ID_especialidad;
        this.Nom_especialidad = Nom_especialidad;
        this.Descrip_especi = Descrip_especi;
    }

    public String getID_especialidad() {
        return ID_especialidad;
    }

    public void setID_especialidad(String ID_especialidad) {
        this.ID_especialidad = ID_especialidad;
    }

    public String getNom_especialidad() {
        return Nom_especialidad;
    }

    public void setNom_especialidad(String Nom_especialidad) {
        this.Nom_especialidad = Nom_especialidad;
    }

    public String getDescrip_especi() {
        return Descrip_especi;
    }

    public void setDescrip_especi(String Descrip_especi) {
        this.Descrip_especi = Descrip_especi;
    }

    @Override
    public String toString() {
        return Nom_especialidad;
    }
    
}
