/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Tipo_residuo {
    private String ID_tipo_resi;
    private String Nom_tipo_resi;
    private String grado_riesgo;
    private String Desc_tipo_resi;

    public Tipo_residuo() {
    }

    public Tipo_residuo(String ID_tipo_resi, String Nom_tipo_resi, String grado_riesgo, String Desc_tipo_resi) {
        this.ID_tipo_resi = ID_tipo_resi;
        this.Nom_tipo_resi = Nom_tipo_resi;
        this.grado_riesgo = grado_riesgo;
        this.Desc_tipo_resi = Desc_tipo_resi;
    }

    public String getID_tipo_resi() {
        return ID_tipo_resi;
    }

    public void setID_tipo_resi(String ID_tipo_resi) {
        this.ID_tipo_resi = ID_tipo_resi;
    }

    public String getNom_tipo_resi() {
        return Nom_tipo_resi;
    }

    public void setNom_tipo_resi(String Nom_tipo_resi) {
        this.Nom_tipo_resi = Nom_tipo_resi;
    }

    public String getGrado_riesgo() {
        return grado_riesgo;
    }

    public void setGrado_riesgo(String grado_riesgo) {
        this.grado_riesgo = grado_riesgo;
    }

    public String getDesc_tipo_resi() {
        return Desc_tipo_resi;
    }

    public void setDesc_tipo_resi(String Desc_tipo_resi) {
        this.Desc_tipo_resi = Desc_tipo_resi;
    }

    @Override
    public String toString() {
        return "Tipo_residuo{" + "ID_tipo_resi=" + ID_tipo_resi + ", Nom_tipo_resi=" + Nom_tipo_resi + ", grado_riesgo=" + grado_riesgo + ", Desc_tipo_resi=" + Desc_tipo_resi + '}';
    }
    
}
