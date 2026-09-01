/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

public class DetalleRepuesto {
    private String idRepuesto;
    private int cantidad;
    private double subtotal;

    public DetalleRepuesto(String idRepuesto, int cantidad, double subtotal) {
        this.idRepuesto = idRepuesto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String getIdRepuesto() { return idRepuesto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return subtotal; }
}