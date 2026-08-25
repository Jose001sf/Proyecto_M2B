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

    private String id_empleado;
    private String ced_perso;
    private String id_cargo;
    private String id_especialidad;
    //Auxiliares
    private String nombre_Completo;
    private String nom_cargo;
    private String nom_especialidad;

    public String getNom_cargo() {
        return nom_cargo;
    }

    public void setNom_cargo(String nom_cargo) {
        this.nom_cargo = nom_cargo;
    }

    public String getNom_especialidad() {
        return nom_especialidad;
    }

    public void setNom_especialidad(String nom_especialidad) {
        this.nom_especialidad = nom_especialidad;
    }
    

    public Empleado(String id_empleado, String ced_perso, String id_cargo, String id_especialidad, String nombre_Completo) {
        this.id_empleado = id_empleado;
        this.ced_perso = ced_perso;
        this.id_cargo = id_cargo;
        this.id_especialidad = id_especialidad;
        this.nombre_Completo = nombre_Completo;
    }

    public String getNombre_Completo() {
        return nombre_Completo;
    }

    public void setNombre_Completo(String nombre_Completo) {
        this.nombre_Completo = nombre_Completo;
    }
    
    
    /**
     * @return the ced_perso
     */
    public String getCed_perso() {
        return ced_perso;
    }

    /**
     * @param ced_perso the ced_perso to set
     */
    public void setCed_perso(String ced_perso) {
        this.ced_perso = ced_perso;
    }

    /**
     * @return the id_cargo
     */
    public String getId_cargo() {
        return id_cargo;
    }

    /**
     * @param id_cargo the id_cargo to set
     */
    public void setId_cargo(String id_cargo) {
        this.id_cargo = id_cargo;
    }

    /**
     * @return the id_especialidad
     */
    public String getId_especialidad() {
        return id_especialidad;
    }

    /**
     * @param id_especialidad the id_especialidad to set
     */
    public void setId_especialidad(String id_especialidad) {
        this.id_especialidad = id_especialidad;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Empleado() {
    }

    public Empleado(String id_empleado, String ced_perso, String id_cargo, String id_especialidad) {
        this.id_empleado = id_empleado;
        this.ced_perso = ced_perso;
        this.id_cargo = id_cargo;
        this.id_especialidad = id_especialidad;
    }

    public Empleado(String ced_perso, String id_cargo, String id_especialidad) {
        this.ced_perso = ced_perso;
        this.id_cargo = id_cargo;
        this.id_especialidad = id_especialidad;
    }

    
    @Override
    public String toString() {
        return "Empleado{" + "id_empleado=" + id_empleado + ", ced_perso=" + ced_perso + ", id_cargo=" + id_cargo + ", id_especialidad=" + id_especialidad + '}';
    }
        
}
