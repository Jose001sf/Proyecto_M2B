/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
