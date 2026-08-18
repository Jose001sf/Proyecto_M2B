/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Direccion_empresa_recicladora {
    private String ID_direccion_empresa_recicladora;
    private String Nom_ciudad_dir_emp;
    private String Nom_calles_dir_emp;

    public Direccion_empresa_recicladora() {
    }

    public Direccion_empresa_recicladora(String ID_direccion_empresa_recicladora, String Nom_ciudad_dir_emp, String Nom_calles_dir_emp) {
        this.ID_direccion_empresa_recicladora = ID_direccion_empresa_recicladora;
        this.Nom_ciudad_dir_emp = Nom_ciudad_dir_emp;
        this.Nom_calles_dir_emp = Nom_calles_dir_emp;
    }

    public String getID_direccion_empresa_recicladora() {
        return ID_direccion_empresa_recicladora;
    }

    public void setID_direccion_empresa_recicladora(String ID_direccion_empresa_recicladora) {
        this.ID_direccion_empresa_recicladora = ID_direccion_empresa_recicladora;
    }

    public String getNom_ciudad_dir_emp() {
        return Nom_ciudad_dir_emp;
    }

    public void setNom_ciudad_dir_emp(String Nom_ciudad_dir_emp) {
        this.Nom_ciudad_dir_emp = Nom_ciudad_dir_emp;
    }

    public String getNom_calles_dir_emp() {
        return Nom_calles_dir_emp;
    }

    public void setNom_calles_dir_emp(String Nom_calles_dir_emp) {
        this.Nom_calles_dir_emp = Nom_calles_dir_emp;
    }

    @Override
    public String toString() {
        return "Direccion_empresa_recicladora{" + "ID_direccion_empresa_recicladora=" + ID_direccion_empresa_recicladora + ", Nom_ciudad_dir_emp=" + Nom_ciudad_dir_emp + ", Nom_calles_dir_emp=" + Nom_calles_dir_emp + '}';
    }
    
}
