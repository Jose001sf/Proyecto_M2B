/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Marca {
    private String ID_mar;
    private String Nom_mar;
    private String Pais_origen_mar;
    private String Empresa_mar;

    public Marca() {
    }

    public Marca(String ID_mar, String Nom_mar, String Pais_origen_mar, String Empresa_mar) {
        this.ID_mar = ID_mar;
        this.Nom_mar = Nom_mar;
        this.Pais_origen_mar = Pais_origen_mar;
        this.Empresa_mar = Empresa_mar;
    }

    public String getID_mar() {
        return ID_mar;
    }

    public void setID_mar(String ID_mar) {
        this.ID_mar = ID_mar;
    }

    public String getNom_mar() {
        return Nom_mar;
    }

    public void setNom_mar(String Nom_mar) {
        this.Nom_mar = Nom_mar;
    }

    public String getPais_origen_mar() {
        return Pais_origen_mar;
    }

    public void setPais_origen_mar(String Pais_origen_mar) {
        this.Pais_origen_mar = Pais_origen_mar;
    }

    public String getEmpresa_mar() {
        return Empresa_mar;
    }

    public void setEmpresa_mar(String Empresa_mar) {
        this.Empresa_mar = Empresa_mar;
    }

    @Override
    public String toString() {
        return "Marca{" + "ID_mar=" + ID_mar + ", Nom_mar=" + Nom_mar + ", Pais_origen_mar=" + Pais_origen_mar + ", Empresa_mar=" + Empresa_mar + '}';
    }
    
}
