
package com.mycompany.proyecto_m2b.modelo;


import java.sql.Date;

public class EncabezadoVenta {
    private String idEncabVenta;
    private String idEmpresaRec;
    private Date fechaCompra;
    private double totalEncabVenta;

    public EncabezadoVenta() {}

    public EncabezadoVenta(String idEncabVenta, String idEmpresaRec, Date fechaCompra, double totalEncabVenta) {
        this.idEncabVenta = idEncabVenta;
        this.idEmpresaRec = idEmpresaRec;
        this.fechaCompra = fechaCompra;
        this.totalEncabVenta = totalEncabVenta;
    }

    public String getIdEncabVenta() {
        return idEncabVenta;
    }

    public void setIdEncabVenta(String idEncabVenta) {
        this.idEncabVenta = idEncabVenta;
    }

    public String getIdEmpresaRec() {
        return idEmpresaRec;
    }

    public void setIdEmpresaRec(String idEmpresaRec) {
        this.idEmpresaRec = idEmpresaRec;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotalEncabVenta() {
        return totalEncabVenta;
    }

    public void setTotalEncabVenta(double totalEncabVenta) {
        this.totalEncabVenta = totalEncabVenta;
    }

    @Override
    public String toString() {
        return "EncabezadoVenta{" + "idEncabVenta=" + idEncabVenta + ", idEmpresaRec=" + idEmpresaRec + ", fechaCompra=" + fechaCompra + ", totalEncabVenta=" + totalEncabVenta + '}';
    }
    
}
