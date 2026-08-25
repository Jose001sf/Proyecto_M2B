
package com.mycompany.proyecto_m2b.modelo;

public class Tipo_de_servicio {
    private String ID_tipo_servicio;
    private String Nom_tipo_servi;
    private String Desc_tipo_servicio;

    public Tipo_de_servicio() {
    }

    public Tipo_de_servicio(String ID_tipo_servicio, String Nom_tipo_servi, String Desc_tipo_servicio) {
        this.ID_tipo_servicio = ID_tipo_servicio;
        this.Nom_tipo_servi = Nom_tipo_servi;
        this.Desc_tipo_servicio = Desc_tipo_servicio;
    }

    public String getID_tipo_servicio() {
        return ID_tipo_servicio;
    }

    public void setID_tipo_servicio(String ID_tipo_servicio) {
        this.ID_tipo_servicio = ID_tipo_servicio;
    }

    public String getNom_tipo_servi() {
        return Nom_tipo_servi;
    }

    public void setNom_tipo_servi(String Nom_tipo_servi) {
        this.Nom_tipo_servi = Nom_tipo_servi;
    }

    public String getDesc_tipo_servicio() {
        return Desc_tipo_servicio;
    }

    public void setDesc_tipo_servicio(String Desc_tipo_servicio) {
        this.Desc_tipo_servicio = Desc_tipo_servicio;
    }

    @Override
    public String toString() {
        return  this.Nom_tipo_servi;
    }
    
    
}
