
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.orden_de_servicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
                     "GROUP BY estado_orden_servi";
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
                + "GROUP BY fecha_ingreso"
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
        String sql = "SELECT fecha_ingreso, SUM(costo_total) AS total "
                + "FROM orden_de_servicio "
                + "WHERE fecha_ingreso BETWEEN ? AND ? "
                + "GROUP BY fecha_ingreso"
                + "ORDER BY fecha_ingreso";
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
    public List<String> obtenerPlacas() {
    List<String> lista = new ArrayList<>();
    String sql = "SELECT \"placa_carro\" FROM vehiculo ORDER BY \"placa_carro\" ASC";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            lista.add(rs.getString("placa_carro"));
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener placas: " + e.getMessage());
    }
    return lista;
}
public String obtenerCedulaPorPlaca(String placa) {
    String cedula = null;
    String sql = "SELECT p.\"ced_perso\", p.\"nom1_person\", p.\"apell1_person\" " +
                 "FROM vehiculo v " +
                 "JOIN propietario pr ON v.\"id_propietario\" = pr.\"id_propietario\" " +
                 "JOIN persona p ON pr.\"ced_perso\" = p.\"ced_perso\" " +
                 "WHERE v.\"placa_carro\" = ?";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, placa);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            cedula = rs.getString("ced_perso") + " - " + rs.getString("nom1_person") + " " + rs.getString("apell1_person");
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener cliente por placa: " + e.getMessage());
    }
    return cedula;
}
public List<String> obtenerEmpleadosConEspecialidad() {
    List<String> lista = new ArrayList<>();
    String sql = "SELECT p.\"nom1_person\", p.\"apell1_person\", e.\"nom_especialidad\" " +
                 "FROM empleado emp " +
                 "JOIN persona p ON emp.\"ced_perso\" = p.\"ced_perso\" " +
                 "JOIN especialidad e ON emp.\"id_especialidad\" = e.\"id_especialidad\"";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            String item = rs.getString("nom1_person") + " " + 
                          rs.getString("apell1_person") + " (" + 
                          rs.getString("nom_especialidad") + ")";
            lista.add(item);
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener empleados: " + e.getMessage());
    }
    return lista;
}
public boolean guardarOrdenServicio(orden_de_servicio orden) {
    String sql = "INSERT INTO orden_de_servicio (" +
                 "\"id_orden_serv\", \"estado_orden_servi\", \"fecha_entrega\", " +
                 "\"costo_total\", \"fecha_ingreso\", \"id_vehi\", \"id_empleado\") " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, orden.getId_orden_serv());
        ps.setString(2, orden.getEstadoorden_servi());
        
        if (orden.getFecha_entrega() != null) {
            ps.setDate(3, new java.sql.Date(orden.getFecha_entrega().getTime()));
        } else {
            ps.setNull(3, java.sql.Types.DATE);
        }

        ps.setDouble(4, orden.getCosto_total());
        
        if (orden.getFecha_ingreso() != null) {
            ps.setDate(5, new java.sql.Date(orden.getFecha_ingreso().getTime()));
        } else {
            ps.setDate(5, new java.sql.Date(System.currentTimeMillis())); // Fecha actual por defecto
        }

        ps.setString(6, orden.getId_vehi());
        ps.setString(7, orden.getId_empleado());

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("Error al guardar la orden de servicio: " + e.getMessage());
        return false;
    }
}
public String obtenerIdVehiculoPorPlaca(String placa) {
    String id = null;
    String sql = "SELECT \"id_vehi\" FROM vehiculo WHERE \"placa_carro\" = ?";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, placa);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) id = rs.getString("id_vehi");
    } catch (SQLException e) {
        System.err.println("Error al obtener ID vehículo: " + e.getMessage());
    }
    return id;
}
public String obtenerIdEmpleadoPorNombre(String nombreCompleto) {
    String id = null;
    String sql = "SELECT e.\"id_empleado\" " +
                 "FROM empleado e " +
                 "JOIN persona p ON e.\"ced_perso\" = p.\"ced_perso\" " +
                 "WHERE (p.\"nom1_person\" || ' ' || p.\"apell1_person\") = ?";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nombreCompleto);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) id = rs.getString("id_empleado");
    } catch (SQLException e) {
        System.err.println("Error al obtener ID empleado: " + e.getMessage());
    }
    return id;
}
}