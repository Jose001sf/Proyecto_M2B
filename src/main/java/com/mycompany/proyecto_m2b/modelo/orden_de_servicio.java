/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

import java.util.Date;

/**
 *
 * @author castr
 */
public class orden_de_servicio {
    private String id_orden_serv;
    private String estadoorden_servi;
    private Date fecha_entrega;
    private double costo_total;
    private Date fecha_ingreso;
    private String id_vehi;
    private String id_empleado;
    //
    private String placaVehiculo;
    private String nombrePropietario;

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }
    

    public orden_de_servicio() {
    }

    public orden_de_servicio(String id_orden_serv, String estadoorden_servi, Date fecha_entrega, double costo_total, Date fecha_ingreso, String id_vehi, String id_empleado) {
        this.id_orden_serv = id_orden_serv;
        this.estadoorden_servi = estadoorden_servi;
        this.fecha_entrega = fecha_entrega;
        this.costo_total = costo_total;
        this.fecha_ingreso = fecha_ingreso;
        this.id_vehi = id_vehi;
        this.id_empleado = id_empleado;
    }

    public String getId_orden_serv() {
        return id_orden_serv;
    }

    public void setId_orden_serv(String id_orden_serv) {
        this.id_orden_serv = id_orden_serv;
    }

    public String getEstadoorden_servi() {
        return estadoorden_servi;
    }

    public void setEstadoorden_servi(String estadoorden_servi) {
        this.estadoorden_servi = estadoorden_servi;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public double getCosto_total() {
        return costo_total;
    }

    public void setCosto_total(double costo_total) {
        this.costo_total = costo_total;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getId_vehi() {
        return id_vehi;
    }

    public void setId_vehi(String id_vehi) {
        this.id_vehi = id_vehi;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    @Override
    public String toString() {
        return id_orden_serv+" | "+placaVehiculo+" | "+nombrePropietario;
    }
    
}
