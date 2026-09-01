
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.DetalleRepuesto;
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
import javax.swing.JComboBox;

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
        String sql = "SELECT fecha_ingreso, SUM(costo_total) AS total "
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
public String obtenerIdEmpleadoPorNombre(String textoEmpleado) {
    if (textoEmpleado == null || textoEmpleado.trim().isEmpty() || textoEmpleado.startsWith("Seleccione")) {
        return null;
    }
    if (textoEmpleado.contains("(")) {
        textoEmpleado = textoEmpleado.substring(0, textoEmpleado.indexOf("(")).trim();
    }

    String id = null;
    String sql = "SELECT e.\"id_empleado\" " +
                 "FROM empleado e " +
                 "JOIN persona p ON e.\"ced_perso\" = p.\"ced_perso\" " +
                 "WHERE TRIM(p.\"nom1_person\" || ' ' || p.\"apell1_person\") = ?";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, textoEmpleado.trim());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            id = rs.getString("id_empleado");
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ID de empleado: " + e.getMessage());
    }
    return id;
    
}public String generarSiguienteIdOrden() {
    String nuevoId = "ODS-001"; 
    String sql = "SELECT \"id_orden_serv\" FROM orden_de_servicio " +
                 "WHERE \"id_orden_serv\" LIKE 'ODS-%' " +
                 "ORDER BY \"id_orden_serv\" DESC LIMIT 1";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            String ultimoId = rs.getString("id_orden_serv"); 
            String numeroStr = ultimoId.replace("ODS-", "").trim();
            int siguienteNumero = Integer.parseInt(numeroStr) + 1;
            nuevoId = String.format("ODS-%03d", siguienteNumero);
        }
    } catch (SQLException e) {
        System.err.println("Error al generar ID de orden: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.err.println("Error al parsear el número del ID: " + e.getMessage());
    }
    return nuevoId;
}
public String[] obtenerDatosPropietarioPorVehiculo(String idVehi) {
    String[] datos = new String[2]; 
    
    String sql = "SELECT (p.\"nom1_person\" || ' ' || p.\"apell1_person\") AS nombre_completo, p.\"corr_elec_perso\" " +
                 "FROM vehiculo v " +
                 "JOIN propietario prop ON v.\"id_propietario\" = prop.\"id_propietario\" " +
                 "JOIN persona p ON prop.\"ced_perso\" = p.\"ced_perso\" " +
                 "WHERE v.\"id_vehi\" = ?";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, idVehi);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                datos[0] = rs.getString("nombre_completo");
                datos[1] = rs.getString("corr_elec_perso");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener datos del propietario: " + e.getMessage());
    }
    return datos;
}
public boolean actualizarOrdenServicio(orden_de_servicio orden) {
    String sql = "UPDATE orden_de_servicio SET " +
                 "\"estado_orden_servi\" = ?, " +
                 "\"fecha_entrega\" = ?, " +
                 "\"costo_total\" = ?, " +
                 "\"id_vehi\" = ?, " +
                 "\"id_empleado\" = ? " +
                 "WHERE \"id_orden_serv\" = ?";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, orden.getEstadoorden_servi());

        // Manejo de fecha de entrega
        if (orden.getFecha_entrega() != null) {
            ps.setDate(2, new java.sql.Date(orden.getFecha_entrega().getTime()));
        } else {
            ps.setNull(2, java.sql.Types.DATE);
        }

        ps.setDouble(3, orden.getCosto_total());
        ps.setString(4, orden.getId_vehi());
        ps.setString(5, orden.getId_empleado());
        ps.setString(6, orden.getId_orden_serv());

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("Error al actualizar la orden de servicio: " + e.getMessage());
        return false;
    }
}
public orden_de_servicio buscarOrdenPorPlaca(String placa) {
    orden_de_servicio orden = null;
    String sql = "SELECT o.* FROM orden_de_servicio o " +
                 "JOIN vehiculo v ON o.\"id_vehi\" = v.\"id_vehi\" " +
                 "WHERE UPPER(v.\"placa_carro\") = UPPER(?) " +
                 "ORDER BY o.\"fecha_ingreso\" DESC LIMIT 1";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, placa.trim());
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                orden = new orden_de_servicio();
                orden.setId_orden_serv(rs.getString("id_orden_serv"));
                orden.setEstadoorden_servi(rs.getString("estado_orden_servi"));
                orden.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                orden.setFecha_entrega(rs.getDate("fecha_entrega"));
                orden.setCosto_total(rs.getDouble("costo_total"));
                orden.setId_vehi(rs.getString("id_vehi"));
                orden.setId_empleado(rs.getString("id_empleado"));
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar por placa: " + e.getMessage());
    }
    return orden;
}

    public boolean guardarOrdenConDetalles(orden_de_servicio orden, List<DetalleRepuesto> listaDetalles) {
    String sqlOrden = "INSERT INTO orden_de_servicio (\"id_orden_serv\", \"estado_orden_servi\", \"fecha_entrega\", \"costo_total\", \"fecha_ingreso\", \"id_vehi\", \"id_empleado\") VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    String sqlDetalle = "INSERT INTO detalle_repuesto (\"id_detalle_repuesto\", \"cantidad_usar\", \"subtotal_repuesto\", \"id_repuestos\", \"id_orden_serv\") VALUES (?, ?, ?, ?, ?)";
    
    String sqlStock = "UPDATE repuestos SET \"cantidad_actual_repuesto\" = \"cantidad_actual_repuesto\" - ? WHERE \"id_repuestos\" = ?";

    Connection con = null;
    try {
        con = ConexionBD.obtenerConexion();
        con.setAutoCommit(false); 

        try (PreparedStatement psOrden = con.prepareStatement(sqlOrden)) {
            psOrden.setString(1, orden.getId_orden_serv());
            psOrden.setString(2, orden.getEstadoorden_servi());
            if (orden.getFecha_entrega() != null) {
                psOrden.setDate(3, new java.sql.Date(orden.getFecha_entrega().getTime()));
            } else {
                psOrden.setNull(3, java.sql.Types.DATE);
            }
            psOrden.setDouble(4, orden.getCosto_total());
            psOrden.setDate(5, new java.sql.Date(orden.getFecha_ingreso().getTime()));
            psOrden.setString(6, orden.getId_vehi());
            psOrden.setString(7, orden.getId_empleado());
            psOrden.executeUpdate();
        }

        int contadorDetalle = 1;
        for (DetalleRepuesto det : listaDetalles) {
            String idDetalle = String.format("DTR-%s-%03d", orden.getId_orden_serv(), contadorDetalle++);

            try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
                 PreparedStatement psStock = con.prepareStatement(sqlStock)) {
                
                psDetalle.setString(1, idDetalle);
                psDetalle.setInt(2, det.getCantidad());
                psDetalle.setDouble(3, det.getSubtotal());
                psDetalle.setString(4, det.getIdRepuesto());
                psDetalle.setString(5, orden.getId_orden_serv());
                psDetalle.executeUpdate();

                psStock.setInt(1, det.getCantidad());
                psStock.setString(2, det.getIdRepuesto());
                psStock.executeUpdate();
            }
        }

        con.commit(); 
        return true;

    } catch (SQLException e) {
        if (con != null) {
            try {
                con.rollback(); 
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.err.println("Error en transacción de orden y repuestos: " + e.getMessage());
        return false;
    } finally {
        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
    public void cargarOrdenServicio (JComboBox ComboOrdonesServicio) {

        String sql = """
            SELECT id_orden_serv
            FROM orden_de_servicio                      
            ORDER BY id_orden_serv
            """;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ComboOrdonesServicio.removeAllItems();
            int i=0;
            while (rs.next()) {
                orden_de_servicio r=new orden_de_servicio();
                r.setId_orden_serv(rs.getString("id_orden_serv"));
                
                
                ComboOrdonesServicio.addItem(r);
                i++;
            }
            System.out.println("Hay "+i+" registros de orden de servicio");
        } catch (SQLException e) {
            System.err.println("Error al cargar orden de servicio: "+e.getMessage());        
        }
    }
}