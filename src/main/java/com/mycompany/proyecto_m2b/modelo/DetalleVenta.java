
package com.mycompany.proyecto_m2b.modelo;


public class DetalleVenta {
    private String idDetalleVenta;
    private String idEncabVenta;
    private String idResiduos;
    private int cantVendida;
    private double subtotalResiduos;

    public DetalleVenta() {}

    public DetalleVenta(String idDetalleVenta, String idEncabVenta, String idResiduos, int cantVendida, double subtotalResiduos) {
        this.idDetalleVenta = idDetalleVenta;
        this.idEncabVenta = idEncabVenta;
        this.idResiduos = idResiduos;
        this.cantVendida = cantVendida;
        this.subtotalResiduos = subtotalResiduos;
    }

    public String getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(String idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public String getIdEncabVenta() {
        return idEncabVenta;
    }

    public void setIdEncabVenta(String idEncabVenta) {
        this.idEncabVenta = idEncabVenta;
    }

    public String getIdResiduos() {
        return idResiduos;
    }

    public void setIdResiduos(String idResiduos) {
        this.idResiduos = idResiduos;
    }

    public int getCantVendida() {
        return cantVendida;
    }

    public void setCantVendida(int cantVendida) {
        this.cantVendida = cantVendida;
    }

    public double getSubtotalResiduos() {
        return subtotalResiduos;
    }

    public void setSubtotalResiduos(double subtotalResiduos) {
        this.subtotalResiduos = subtotalResiduos;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" + "idDetalleVenta=" + idDetalleVenta + ", idEncabVenta=" + idEncabVenta + ", idResiduos=" + idResiduos + ", cantVendida=" + cantVendida + ", subtotalResiduos=" + subtotalResiduos + '}';
    }
    
}