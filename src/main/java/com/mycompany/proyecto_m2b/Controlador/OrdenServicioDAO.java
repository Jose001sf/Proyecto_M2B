
package com.mycompany.proyecto_m2b.Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrdenServicioDAO {
    
    public int contarOrdenes(LocalDate desde, LocalDate hasta) {
        String sql = "SELECT COUNT(*) FROM orden_de_servicio WHERE fecha_ingreso BETWEEN ? AND ?";
        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(desde));
            ps.setDate(2, java.sql.Date.valueOf(hasta));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double sumarIngresos(LocalDate desde, LocalDate hasta) {
        String sql = "SELECT COALESCE(SUM(costo_total), 0) FROM orden_de_servicio WHERE fecha_ingreso BETWEEN ? AND ?";
        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(desde));
            ps.setDate(2, java.sql.Date.valueOf(hasta));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public int contarVehiculosAtendidos(LocalDate Desde, LocalDate Hasta) {
        String sql = "SELECT COUNT(DISTINCT id_vehi) FROM orden_de_servicio WHERE fecha_ingreso BETWEEN ? AND ?";

        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(Desde));
            ps.setDate(2, java.sql.Date.valueOf(Hasta));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public Map<String, Integer> contarOrdenesPorEstado(LocalDate Desde, LocalDate Hasta){
        Map<String, Integer> resultado = new LinkedHashMap<>();
        String sql = "SELECT estado_orden_servi, COUNT(*) AS cantidad " +
                     "FROM orden_de_servicio " +
                     "WHERE fecha_ingreso BETWEEN ? AND ? " +
                     "GROUP BY estado_orden_servi ";
        try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
         ps.setDate(1, java.sql.Date.valueOf(Desde));
         ps.setDate(2, java.sql.Date.valueOf(Hasta));
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("estado_orden_servi"), rs.getInt("cantidad"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resultado;      
    }
    
    public Map<LocalDate, Integer> contarOrdenesPorDia(LocalDate Desde, LocalDate Hasta) {
        Map<LocalDate, Integer> resultado = new LinkedHashMap<>();
        String sql = "SELECT fecha_ingreso, COUNT(*) AS cantidad "
                + "FROM orden_de_servicio "
                + "WHERE fecha_ingreso BETWEEN ? AND ? "
                + "GROUP BY fecha_ingreso "
                + "ORDER BY fecha_ingreso";
        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(Desde));
            ps.setDate(2, java.sql.Date.valueOf(Hasta));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.put(rs.getDate("fecha_ingreso").toLocalDate(), rs.getInt("cantidad"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Map<LocalDate, Double> sumarIngresosPorDia(LocalDate Desde, LocalDate Hasta) {
        Map<LocalDate, Double> resultado = new LinkedHashMap<>();
        String sql = "SELECT fecha_ingreso, SUM(costo_total) AS cantidad "
                + "FROM orden_de_servicio "
                + "WHERE fecha_ingreso BETWEEN ? AND ? "
                + "GROUP BY fecha_ingreso "
                + "ORDER BY fecha_ingreso ";
        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(Desde));
            ps.setDate(2, java.sql.Date.valueOf(Hasta));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.put(rs.getDate("fecha_ingreso").toLocalDate(), rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    
}
    
