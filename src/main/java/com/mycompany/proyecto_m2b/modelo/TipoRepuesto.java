
package com.mycompany.proyecto_m2b.modelo;

public class TipoRepuesto {
    
    private String idTipRepuesto;
    private String nomTipRepuesto;
    private String descripTipRepuesto; 

    public TipoRepuesto() {
    }

    public TipoRepuesto(String idTipRepuesto, String nomTipRepuesto) {
        this.idTipRepuesto = idTipRepuesto;
        this.nomTipRepuesto = nomTipRepuesto;
    }

    public TipoRepuesto(String idTipRepuesto, String nomTipRepuesto, String descripTipRepuesto) {
        this.idTipRepuesto = idTipRepuesto;
        this.nomTipRepuesto = nomTipRepuesto;
        this.descripTipRepuesto = descripTipRepuesto;
    }

    public String getIdTipRepuesto() {
        return idTipRepuesto;
    }

    public void setIdTipRepuesto(String idTipRepuesto) {
        this.idTipRepuesto = idTipRepuesto;
    }

    public String getNomTipRepuesto() {
        return nomTipRepuesto;
    }

    public void setNomTipRepuesto(String nomTipRepuesto) {
        this.nomTipRepuesto = nomTipRepuesto;
    }

    public String getDescripTipRepuesto() {
        return descripTipRepuesto;
    }

    public void setDescripTipRepuesto(String descripTipRepuesto) {
        this.descripTipRepuesto = descripTipRepuesto;
    }

    @Override
    public String toString() {
        return nomTipRepuesto; 
    }
}