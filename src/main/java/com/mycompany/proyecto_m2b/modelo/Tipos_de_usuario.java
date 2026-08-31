/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author usuario
 */
public class Tipos_de_usuario {

    private String id_tip_de_usuario;
    private String nom_tip_de_usuario;
    /**
     * @return the id_tip_de_usuario
     */
    public String getId_tip_de_usuario() {
        return id_tip_de_usuario;
    }

    /**
     * @param id_tip_de_usuario the id_tip_de_usuario to set
     */
    public void setId_tip_de_usuario(String id_tip_de_usuario) {
        this.id_tip_de_usuario = id_tip_de_usuario;
    }

    /**
     * @return the nom_tip_de_usuario
     */
    public String getNom_tip_de_usuario() {
        return nom_tip_de_usuario;
    }

    /**
     * @param nom_tip_de_usuario the nom_tip_de_usuario to set
     */
    public void setNom_tip_de_usuario(String nom_tip_de_usuario) {
        this.nom_tip_de_usuario = nom_tip_de_usuario;
    }
    

    public Tipos_de_usuario(String id_tip_de_usuario, String nom_tip_de_usuario) {
        this.id_tip_de_usuario = id_tip_de_usuario;
        this.nom_tip_de_usuario = nom_tip_de_usuario;
    }

    public Tipos_de_usuario() {
    }

    public Tipos_de_usuario(String nom_tip_de_usuario) {
        this.nom_tip_de_usuario = nom_tip_de_usuario;
    }

    
    @Override
    public String toString() {
        return id_tip_de_usuario+"-"+nom_tip_de_usuario;
    }
    
}
