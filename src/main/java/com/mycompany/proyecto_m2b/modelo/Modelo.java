
package com.mycompany.proyecto_m2b.modelo;

public class Modelo {
    private String ID_mode;
    private String Nom_mode;
    private String ID_mar_mode;
    private String ID_tipo_mode;

    public Modelo() {
    }
    
    public Modelo(String ID_mode, String Nom_mode, String ID_mar_mode, String ID_tipo_mode) {
        this.ID_mode = ID_mode;
        this.Nom_mode = Nom_mode;
        this.ID_mar_mode = ID_mar_mode;
        this.ID_tipo_mode = ID_tipo_mode;
    }

    public String getID_mode() {
        return ID_mode;
    }

    public void setID_mode(String ID_mode) {
        this.ID_mode = ID_mode;
    }

    public String getNom_mode() {
        return Nom_mode;
    }

    public void setNom_mode(String Nom_mode) {
        this.Nom_mode = Nom_mode;
    }

    public String getID_mar_mode() {
        return ID_mar_mode;
    }

    public void setID_mar_mode(String ID_mar_mode) {
        this.ID_mar_mode = ID_mar_mode;
    }

    public String getID_tipo_mode() {
        return ID_tipo_mode;
    }

    public void setID_tipo_mode(String ID_tipo_mode) {
        this.ID_tipo_mode = ID_tipo_mode;
    }

    @Override
    public String toString() {
        return this.Nom_mode;
    }
}
