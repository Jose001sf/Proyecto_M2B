/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Tipo {
    private String ID_tipo;
    private String Nom_tipo;
    private String Desc_tipo;

    public Tipo() {
    }

    public Tipo(String ID_tipo, String Nom_tipo, String Desc_tipo) {
        this.ID_tipo = ID_tipo;
        this.Nom_tipo = Nom_tipo;
        this.Desc_tipo = Desc_tipo;
    }

    public String getID_tipo() {
        return ID_tipo;
    }

    public void setID_tipo(String ID_tipo) {
        this.ID_tipo = ID_tipo;
    }

    public String getNom_tipo() {
        return Nom_tipo;
    }

    public void setNom_tipo(String Nom_tipo) {
        this.Nom_tipo = Nom_tipo;
    }

    public String getDesc_tipo() {
        return Desc_tipo;
    }

    public void setDesc_tipo(String Desc_tipo) {
        this.Desc_tipo = Desc_tipo;
    }

    @Override
    public String toString() {
        return "Tipo{" + "ID_tipo=" + ID_tipo + ", Nom_tipo=" + Nom_tipo + ", Desc_tipo=" + Desc_tipo + '}';
    }
    
}
