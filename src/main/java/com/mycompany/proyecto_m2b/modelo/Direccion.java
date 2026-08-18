/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Direccion {
    private String ID_direccion;
    private String Calle_principal;
    private String Calle_secundaria;
    private String Numero_casa;
    private String Ciudad;

    public Direccion() {
    }

    public Direccion(String ID_direccion, String Calle_principal, String Calle_secundaria, String Numero_casa, String Ciudad) {
        this.ID_direccion = ID_direccion;
        this.Calle_principal = Calle_principal;
        this.Calle_secundaria = Calle_secundaria;
        this.Numero_casa = Numero_casa;
        this.Ciudad = Ciudad;
    }

    public String getID_direccion() {
        return ID_direccion;
    }

    public void setID_direccion(String ID_direccion) {
        this.ID_direccion = ID_direccion;
    }

    public String getCalle_principal() {
        return Calle_principal;
    }

    public void setCalle_principal(String Calle_principal) {
        this.Calle_principal = Calle_principal;
    }

    public String getCalle_secundaria() {
        return Calle_secundaria;
    }

    public void setCalle_secundaria(String Calle_secundaria) {
        this.Calle_secundaria = Calle_secundaria;
    }

    public String getNumero_casa() {
        return Numero_casa;
    }

    public void setNumero_casa(String Numero_casa) {
        this.Numero_casa = Numero_casa;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String Ciudad) {
        this.Ciudad = Ciudad;
    }

    @Override
    public String toString() {
        return "Direccion{" + "ID_direccion=" + ID_direccion + ", Calle_principal=" + Calle_principal + ", Calle_secundaria=" + Calle_secundaria + ", Numero_casa=" + Numero_casa + ", Ciudad=" + Ciudad + '}';
    }
    
}
