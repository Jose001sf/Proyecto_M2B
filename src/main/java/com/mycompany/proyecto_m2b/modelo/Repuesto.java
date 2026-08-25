
package com.mycompany.proyecto_m2b.modelo;

public class Repuesto {
    private String idRepuestos;
    private String nomRepuesto;
    private int cantidadMaxRepuesto;
    private int cantidadMinRepuesto;
    private int cantidadActualRepuesto;
    private double precioRepuestoUnit;
    private String descripRepuesto;
    private String idTipRepuesto;
    private String idMarcaRepuesto; 

    public Repuesto(String idRepuestos, String nomRepuesto, int cantidadMaxRepuesto, int cantidadMinRepuesto, int cantidadActualRepuesto, double precioRepuestoUnit, String descripRepuesto, String idTipRepuesto, String idMarcaRepuesto) {
        this.idRepuestos = idRepuestos;
        this.nomRepuesto = nomRepuesto;
        this.cantidadMaxRepuesto = cantidadMaxRepuesto;
        this.cantidadMinRepuesto = cantidadMinRepuesto;
        this.cantidadActualRepuesto = cantidadActualRepuesto;
        this.precioRepuestoUnit = precioRepuestoUnit;
        this.descripRepuesto = descripRepuesto;
        this.idTipRepuesto = idTipRepuesto;
        this.idMarcaRepuesto = idMarcaRepuesto;
    }
    
    public Repuesto(String idRepuestos, String nomRepuesto, double precioRepuestoUnit) {
        this.idRepuestos = idRepuestos;
        this.nomRepuesto = nomRepuesto;
        this.precioRepuestoUnit = precioRepuestoUnit;
}
    
    public String getIdRepuestos() {
        return idRepuestos;
    }

    public void setIdRepuestos(String idRepuestos) {
        this.idRepuestos = idRepuestos;
    }

    public String getNomRepuesto() {
        return nomRepuesto;
    }

    public void setNomRepuesto(String nomRepuesto) {
        this.nomRepuesto = nomRepuesto;
    }

    public int getCantidadMaxRepuesto() {
        return cantidadMaxRepuesto;
    }

    public void setCantidadMaxRepuesto(int cantidadMaxRepuesto) {
        this.cantidadMaxRepuesto = cantidadMaxRepuesto;
    }

    public int getCantidadMinRepuesto() {
        return cantidadMinRepuesto;
    }

    public void setCantidadMinRepuesto(int cantidadMinRepuesto) {
        this.cantidadMinRepuesto = cantidadMinRepuesto;
    }

    public int getCantidadActualRepuesto() {
        return cantidadActualRepuesto;
    }

    public void setCantidadActualRepuesto(int cantidadActualRepuesto) {
        this.cantidadActualRepuesto = cantidadActualRepuesto;
    }

    public double getPrecioRepuestoUnit() {
        return precioRepuestoUnit;
    }

    public void setPrecioRepuestoUnit(double precioRepuestoUnit) {
        this.precioRepuestoUnit = precioRepuestoUnit;
    }

    public String getDescripRepuesto() {
        return descripRepuesto;
    }

    public void setDescripRepuesto(String descripRepuesto) {
        this.descripRepuesto = descripRepuesto;
    }

    public String getIdTipRepuesto() {
        return idTipRepuesto;
    }

    public void setIdTipRepuesto(String idTipRepuesto) {
        this.idTipRepuesto = idTipRepuesto;
    }

    public String getIdMarcaRepuesto() {
        return idMarcaRepuesto;
    }

    public void setIdMarcaRepuesto(String idMarcaRepuesto) {
        this.idMarcaRepuesto = idMarcaRepuesto;
    }

    @Override
        public String toString() {
        return nomRepuesto;     
}
    
}
