
package com.mycompany.proyecto_m2b.modelo;

public class TipoRepuesto {
    private String idTipRepuesto;
    private String nomTipRepuesto;

    public TipoRepuesto(String id, String nombre) {
        this.idTipRepuesto = id;
        this.nomTipRepuesto = nombre;
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
    
    @Override
    public String toString() { return nomTipRepuesto; }
}