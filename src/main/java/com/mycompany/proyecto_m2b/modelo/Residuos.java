
package com.mycompany.proyecto_m2b.modelo;

public class Residuos {
    private String ID_resiudos;
    private String Nom_residuo;
    private String Estado_residuo;
    private String ID_tipo_resi;
    private int cantidad_actual;
    private int cantidad_max;

    public Residuos(String ID_resiudos, String Nom_residuo, String Estado_residuo, String ID_tipo_resi, int cantidad_actual, int cantidad_max) {
        this.ID_resiudos = ID_resiudos;
        this.Nom_residuo = Nom_residuo;
        this.Estado_residuo = Estado_residuo;
        this.ID_tipo_resi = ID_tipo_resi;
        this.cantidad_actual = cantidad_actual;
        this.cantidad_max = cantidad_max;
    }

    public String getID_resiudos() {
        return ID_resiudos;
    }

    public void setID_resiudos(String ID_resiudos) {
        this.ID_resiudos = ID_resiudos;
    }

    public String getNom_residuo() {
        return Nom_residuo;
    }

    public void setNom_residuo(String Nom_residuo) {
        this.Nom_residuo = Nom_residuo;
    }

    public String getEstado_residuo() {
        return Estado_residuo;
    }

    public void setEstado_residuo(String Estado_residuo) {
        this.Estado_residuo = Estado_residuo;
    }

    public String getID_tipo_resi() {
        return ID_tipo_resi;
    }

    public void setID_tipo_resi(String ID_tipo_resi) {
        this.ID_tipo_resi = ID_tipo_resi;
    }

    public int getCantidad_actual() {
        return cantidad_actual;
    }

    public void setCantidad_actual(int cantidad_actual) {
        this.cantidad_actual = cantidad_actual;
    }

    public int getCantidad_max() {
        return cantidad_max;
    }

    public void setCantidad_max(int cantidad_max) {
        this.cantidad_max = cantidad_max;
    }

    @Override
    public String toString() {
        return "Residuos{" + "ID_resiudos=" + ID_resiudos + ", Nom_residuo=" + Nom_residuo + ", Estado_residuo=" + Estado_residuo + ", ID_tipo_resi=" + ID_tipo_resi + ", cantidad_actual=" + cantidad_actual + ", cantidad_max=" + cantidad_max + '}';
    }
}
