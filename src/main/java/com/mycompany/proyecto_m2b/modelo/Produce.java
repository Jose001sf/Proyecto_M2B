/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

import java.sql.Date;

/**
 *
 * @author usuario
 */
public class Produce {

    private String id_produccion;
    private String id_orden_serv;
    private int cant_gene;
    private Date fecha_registro;
    private String id_residuos;
    //auxiliar
    private String nom_residuo;

    public String getNom_residuo() {
        return nom_residuo;
    }

    public String getId_residuos() {
        return id_residuos;
    }

    public void setId_residuos(String id_residuos) {
        this.id_residuos = id_residuos;
    }
    
    
    
    public void setNom_residuo(String nom_residuo) {
        this.nom_residuo = nom_residuo;
    }

    public Produce(String id_produccion, String id_orden_serv, int cant_gene, Date fecha_registro, String nom_residuo) {
        this.id_produccion = id_produccion;
        this.id_orden_serv = id_orden_serv;
        this.cant_gene = cant_gene;
        this.fecha_registro = fecha_registro;
        this.nom_residuo = nom_residuo;
    }

    public Produce(String id_produccion, String id_orden_serv, int cant_gene, Date fecha_registro, String id_residuos, String nom_residuo) {
        this.id_produccion = id_produccion;
        this.id_orden_serv = id_orden_serv;
        this.cant_gene = cant_gene;
        this.fecha_registro = fecha_registro;
        this.id_residuos = id_residuos;
        this.nom_residuo = nom_residuo;
    }
    
    
    
    /**
     * @return the id_produccion
     */
    public String getId_produccion() {
        return id_produccion;
    }

    /**
     * @param id_produccion the id_produccion to set
     */
    public void setId_produccion(String id_produccion) {
        this.id_produccion = id_produccion;
    }

    /**
     * @return the id_orden_serv
     */
    public String getId_orden_serv() {
        return id_orden_serv;
    }

    /**
     * @param id_orden_serv the id_orden_serv to set
     */
    public void setId_orden_serv(String id_orden_serv) {
        this.id_orden_serv = id_orden_serv;
    }

    /**
     * @return the cant_gene
     */
    public int getCant_gene() {
        return cant_gene;
    }

    /**
     * @param cant_gene the cant_gene to set
     */
    public void setCant_gene(int cant_gene) {
        this.cant_gene = cant_gene;
    }

    /**
     * @return the fecha_registro
     */
    public Date getFecha_registro() {
        return fecha_registro;
    }

    /**
     * @param fecha_registro the fecha_registro to set
     */
    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
    

    
    public Produce() {
    }
    
    
}
