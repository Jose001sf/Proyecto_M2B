/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Cargo {
    private String ID_cargo;
    private String Nom_cargo;
    private String Descrip_cargo;

    public Cargo() {
    }

    public Cargo(String ID_cargo, String Nom_cargo, String Descrip_cargo) {
        this.ID_cargo = ID_cargo;
        this.Nom_cargo = Nom_cargo;
        this.Descrip_cargo = Descrip_cargo;
    }

    public String getID_cargo() {
        return ID_cargo;
    }

    public void setID_cargo(String ID_cargo) {
        this.ID_cargo = ID_cargo;
    }

    public String getNom_cargo() {
        return Nom_cargo;
    }

    public void setNom_cargo(String Nom_cargo) {
        this.Nom_cargo = Nom_cargo;
    }

    public String getDescrip_cargo() {
        return Descrip_cargo;
    }

    public void setDescrip_cargo(String Descrip_cargo) {
        this.Descrip_cargo = Descrip_cargo;
    }

    @Override
    public String toString() {
        return  Nom_cargo;
    }
    
}
