/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Tipo_de_repuesto {
    private String ID_tip_repuesto;
    private String Nom_tip_repuesto;
    private String Descrip_tip_repuesto;

    public Tipo_de_repuesto() {
    }

    public Tipo_de_repuesto(String ID_tip_repuesto, String Nom_tip_repuesto, String Descrip_tip_repuesto) {
        this.ID_tip_repuesto = ID_tip_repuesto;
        this.Nom_tip_repuesto = Nom_tip_repuesto;
        this.Descrip_tip_repuesto = Descrip_tip_repuesto;
    }

    public String getID_tip_repuesto() {
        return ID_tip_repuesto;
    }

    public void setID_tip_repuesto(String ID_tip_repuesto) {
        this.ID_tip_repuesto = ID_tip_repuesto;
    }

    public String getNom_tip_repuesto() {
        return Nom_tip_repuesto;
    }

    public void setNom_tip_repuesto(String Nom_tip_repuesto) {
        this.Nom_tip_repuesto = Nom_tip_repuesto;
    }

    public String getDescrip_tip_repuesto() {
        return Descrip_tip_repuesto;
    }

    public void setDescrip_tip_repuesto(String Descrip_tip_repuesto) {
        this.Descrip_tip_repuesto = Descrip_tip_repuesto;
    }

    @Override
    public String toString() {
        return "Tipo_de_repuesto{" + "ID_tip_repuesto=" + ID_tip_repuesto + ", Nom_tip_repuesto=" + Nom_tip_repuesto + ", Descrip_tip_repuesto=" + Descrip_tip_repuesto + '}';
    }
    
}
