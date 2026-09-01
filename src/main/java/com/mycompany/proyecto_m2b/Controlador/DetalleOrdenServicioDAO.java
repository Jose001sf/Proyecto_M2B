/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class DetalleOrdenServicioDAO {
    private static final String INSERTAR
            = "INSERT INTO detalle_de_orden (id_detalle_orden, cantidad_servi, subtotal_orden, id_servi, id_orden_serv) "
            + "VALUES (?,?,?,?,?)";

    public boolean insertarDetalle(String idDetalle, int cantidad, double subtotal, String idServicio, String idOrden) {
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement ps = conn.prepareStatement(INSERTAR)) {
            ps.setString(1, idDetalle);
            ps.setInt(2, cantidad);
            ps.setDouble(3, subtotal);
            ps.setString(4, idServicio);
            ps.setString(5, idOrden);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar detalle de orden: " + e.getMessage());
            return false;
        }
    }

    private static final String OBTENERULTIMOID
            = "SELECT id_detalle_orden FROM detalle_de_orden ORDER BY id_detalle_orden DESC LIMIT 1";

    public String generarNuevoId() {
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement ps = conn.prepareStatement(OBTENERULTIMOID); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int numero = Integer.parseInt(rs.getString("id_detalle_orden").substring(3));
                return String.format("DET%03d", numero + 1);
                }
            return "DET001";
        } catch (SQLException e) {
            System.out.println("Error al generar id de detalle: " + e.getMessage());
            return null;
        }
    }
    public List<Object[]> obtenerRepuestosPorOrden(String idOrden) {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT dr.id_detalle_repuesto, r.nom_repuesto, dr.cantidad_usar, r.precio_repuesto_unit, dr.subtotal_repuesto "
               + "FROM detalle_repuesto dr "
               + "INNER JOIN repuestos r ON dr.id_repuestos = r.id_repuestos "
               + "WHERE TRIM(dr.id_orden_serv) = TRIM(?)";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, idOrden.trim());
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("id_detalle_repuesto"),
                    rs.getString("nom_repuesto"),
                    rs.getInt("cantidad_usar"),
                    rs.getDouble("precio_repuesto_unit"),
                    rs.getDouble("subtotal_repuesto")
                });
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener detalle con nombres: " + e.getMessage());
    }
    return lista;
}
    public List<Object[]> obtenerServiciosPorOrden(String idOrden) {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT ds.id_servicio, s.nombre_servicio, s.precio_servicio, ds.cantidad, ds.subtotal_servicio "
               + "FROM detalle_servicio ds "
               + "INNER JOIN servicios s ON ds.id_servicio = s.id_servicio "
               + "WHERE TRIM(ds.id_orden_serv) = TRIM(?)";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, idOrden.trim());
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("nombre_servicio"),
                    rs.getDouble("precio_servicio"),
                    rs.getInt("cantidad"),
                    rs.getDouble("subtotal_servicio")
                });
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener detalle de servicios: " + e.getMessage());
    }
    return lista;
}
}