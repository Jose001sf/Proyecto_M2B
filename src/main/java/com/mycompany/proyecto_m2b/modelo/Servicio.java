
package com.mycompany.proyecto_m2b.modelo;

public class Servicio {
    private String id_servi;
    private int tiempo_est_hor_servi;
    private float precio_del_servicio;
    private String nom_servicio;
    private String id_tipo_servicio;

    public Servicio(String id_servi, int tiempo_est_hor_servi, float precio_del_servicio, String nom_servicio, String id_tipo_servicio) {
        this.id_servi = id_servi;
        this.tiempo_est_hor_servi = tiempo_est_hor_servi;
        this.precio_del_servicio = precio_del_servicio;
        this.nom_servicio = nom_servicio;
        this.id_tipo_servicio = id_tipo_servicio;
    }

    public Servicio() {
    }

    public String getId_servi() {
        return id_servi;
    }

    public void setId_servi(String id_servi) {
        this.id_servi = id_servi;
    }

    public int getTiempo_est_hor_servi() {
        return tiempo_est_hor_servi;
    }

    public void setTiempo_est_hor_servi(int tiempo_est_hor_servi) {
        this.tiempo_est_hor_servi = tiempo_est_hor_servi;
    }

    public float getPrecio_del_servicio() {
        return precio_del_servicio;
    }

    public void setPrecio_del_servicio(float precio_del_servicio) {
        this.precio_del_servicio = precio_del_servicio;
    }

    public String getNom_servicio() {
        return nom_servicio;
    }

    public void setNom_servicio(String nom_servicio) {
        this.nom_servicio = nom_servicio;
    }

    public String getId_tipo_servicio() {
        return id_tipo_servicio;
    }

    public void setId_tipo_servicio(String id_tipo_servicio) {
        this.id_tipo_servicio = id_tipo_servicio;
    }

    @Override
    public String toString() {
        return "Servicio{" + "id_servi=" + id_servi + ", tiempo_est_hor_servi=" + tiempo_est_hor_servi + ", precio_del_servicio=" + precio_del_servicio + ", nom_servicio=" + nom_servicio + ", id_tipo_servicio=" + id_tipo_servicio + '}';
    }
    
    
}
