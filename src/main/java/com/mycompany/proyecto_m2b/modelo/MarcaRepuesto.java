
package com.mycompany.proyecto_m2b.modelo;

public class MarcaRepuesto {
    private String idMarcaRepuesto;
    private String nombreMarcaRepuesto;

    public MarcaRepuesto(String id, String nombre) {
        this.idMarcaRepuesto = id;
        this.nombreMarcaRepuesto = nombre;
    }

    public String getIdMarcaRepuesto() {
        return idMarcaRepuesto;
    }

    public void setIdMarcaRepuesto(String idMarcaRepuesto) {
        this.idMarcaRepuesto = idMarcaRepuesto;
    }

    public String getNombreMarcaRepuesto() {
        return nombreMarcaRepuesto;
    }

    public void setNombreMarcaRepuesto(String nombreMarcaRepuesto) {
        this.nombreMarcaRepuesto = nombreMarcaRepuesto;
    }
    
    @Override
    public String toString() { return nombreMarcaRepuesto; }
}