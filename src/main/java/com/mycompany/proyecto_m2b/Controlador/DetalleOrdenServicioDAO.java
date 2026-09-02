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
    String sql = "SELECT COUNT(*) FROM detalle_repuesto";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            int count = rs.getInt(1) + 1;
            return String.format("DTR-%04d", count); 
        }
    } catch (SQLException e) {
        System.err.println("Error generando ID: " + e.getMessage());
    }
    return "DTR-0001";
}
    public List<Object[]> obtenerServiciosPorOrden(String idOrden) {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT s.nom_servicio, s.precio_del_servicio, d.cantidad_servi, d.subtotal_orden "
               + "FROM detalle_de_orden d "
               + "INNER JOIN servicio s ON d.id_servi = s.id_servi "
               + "WHERE LOWER(TRIM(d.id_orden_serv)) = LOWER(TRIM(?))";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, idOrden.trim());
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("nom_servicio"),         
                    rs.getDouble("precio_del_servicio"),  
                    rs.getInt("cantidad_servi"),          
                    rs.getDouble("subtotal_orden")        
                });
            }
        }
    } catch (SQLException e) {
        System.err.println("Error SQL: " + e.getMessage());
    }
    return lista;
}
    public void eliminarDetallesPorOrden(String idOrden) {
    String sqlServicios = "DELETE FROM detalle_de_orden WHERE TRIM(LOWER(id_orden_serv)) = TRIM(LOWER(?))";
    String sqlRepuestos = "DELETE FROM detalle_repuesto WHERE TRIM(LOWER(id_orden_serv)) = TRIM(LOWER(?))";

    try (Connection con = ConexionBD.obtenerConexion()) {
        try (PreparedStatement ps1 = con.prepareStatement(sqlServicios)) {
            ps1.setString(1, idOrden.trim());
            ps1.executeUpdate();
        }
        try (PreparedStatement ps2 = con.prepareStatement(sqlRepuestos)) {
            ps2.setString(1, idOrden.trim());
            ps2.executeUpdate();
        }

        System.out.println("-> Detalles limpios en BD para la orden: [" + idOrden + "]");

    } catch (SQLException e) {
        System.err.println("Error al eliminar detalles de la orden: " + e.getMessage());
    }
}
    public String obtenerIdPorNombre(String nombreServicio) {
    String idServicio = null;
    
    
    String sql = "SELECT id_servi FROM servicio WHERE LOWER(TRIM(nom_servicio)) = LOWER(TRIM(?)) LIMIT 1";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, nombreServicio);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idServicio = rs.getString("id_servi");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ID del servicio por nombre: " + e.getMessage());
    }
    
    return idServicio;
}
    public String obtenerIdRepuestoPorNombre(String nombreRepuesto) {
    String idRepuesto = null;
    String sql = "SELECT id_repuestos FROM repuestos WHERE LOWER(TRIM(nom_repuesto)) = LOWER(TRIM(?)) LIMIT 1";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, nombreRepuesto);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idRepuesto = rs.getString("id_repuestos");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ID del repuesto por nombre: " + e.getMessage());
    }
    
    return idRepuesto;
}
    public boolean insertarDetalleRepuesto(String idDetalle, int cantidad, double subtotal, String idRepuesto, String idOrden) {
    String sql = "INSERT INTO detalle_repuesto (id_detalle_repuesto, cantidad_usar, subtotal_repuesto, id_repuestos, id_orden_serv) "
               + "VALUES (?, ?, ?, ?, ?)";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, idDetalle);
        ps.setInt(2, cantidad);
        ps.setDouble(3, subtotal);
        ps.setString(4, idRepuesto);
        ps.setString(5, idOrden);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("Error al insertar detalle de repuesto: " + e.getMessage());
        return false;
    }
}
public List<Object[]> obtenerRepuestosPorOrden(String idOrden) {
    List<Object[]> lista = new ArrayList<>();
    
    String sql = "SELECT d.id_repuestos, r.nom_repuesto, d.cantidad_usar, r.precio_repuesto_unit, d.subtotal_repuesto "
               + "FROM detalle_repuesto d "
               + "INNER JOIN repuestos r ON d.id_repuestos = r.id_repuestos "
               + "WHERE LOWER(TRIM(d.id_orden_serv)) = LOWER(TRIM(?))";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, idOrden.trim());

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("id_repuestos"),
                    rs.getString("nom_repuesto"),
                    rs.getInt("cantidad_usar"),
                    rs.getDouble("precio_repuesto_unit"),
                    rs.getDouble("subtotal_repuesto")
                });
            }
        }
    } catch (SQLException e) {
        System.err.println("Error SQL al obtener repuestos: " + e.getMessage());
    }
    
    return lista;
}

}