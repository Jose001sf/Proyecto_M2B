/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.Normalizer;
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
    if (idOrden == null || idOrden.trim().isEmpty()) {
        return lista; 
    }
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
   public boolean guardarDetallesOrden(String idOrden, List<Object[]> listaServicios, List<Object[]> listaRepuestos) {
    String sqlDeleteServ = "DELETE FROM detalle_de_orden WHERE LOWER(TRIM(id_orden_serv)) = LOWER(TRIM(?))";
    String sqlDeleteRep = "DELETE FROM detalle_repuesto WHERE LOWER(TRIM(id_orden_serv)) = LOWER(TRIM(?))";

    String sqlInsertServ = "INSERT INTO detalle_de_orden (id_detalle_orden, id_orden_serv, id_servi, cantidad_servi, subtotal_orden) VALUES (?, ?, ?, ?, ?)";
    String sqlInsertRep = "INSERT INTO detalle_repuesto (id_detalle_repuesto, cantidad_usar, subtotal_repuesto, id_repuestos, id_orden_serv) VALUES (?, ?, ?, ?, ?)";

    Connection con = null;
    try {
        con = ConexionBD.obtenerConexion();
        con.setAutoCommit(false);

        try (PreparedStatement psDelS = con.prepareStatement(sqlDeleteServ);
             PreparedStatement psDelR = con.prepareStatement(sqlDeleteRep)) {
            psDelS.setString(1, idOrden.trim());
            psDelS.executeUpdate();

            psDelR.setString(1, idOrden.trim());
            psDelR.executeUpdate();
        }

        String numOrden = idOrden.replaceAll("[^0-9]", "");
        if (numOrden.length() > 6) {
            numOrden = numOrden.substring(numOrden.length() - 6);
        }

        try (PreparedStatement psInsS = con.prepareStatement(sqlInsertServ)) {
            for (int i = 0; i < listaServicios.size(); i++) {
                Object[] fila = listaServicios.get(i);
                if (fila == null || fila[0] == null || fila[0].toString().trim().isEmpty()) continue;

                String nombreServicio = fila[0].toString().trim();
                String idServicioReal = obtenerIdServicioPorNombre(nombreServicio);

                int cantidad = 1;
                try {
                    cantidad = (int) Double.parseDouble(fila[2].toString().replace(",", "."));
                } catch (Exception e) {
                    try { cantidad = Integer.parseInt(fila[2].toString().trim()); } catch (Exception ex) {}
                }

                double subtotal = Double.parseDouble(fila[3].toString().replace(",", "."));

                String idDetalleServ = String.format("DTS-%s-%d", numOrden, (i + 1));
                if (idDetalleServ.length() > 15) {
                    idDetalleServ = idDetalleServ.substring(0, 15);
                }

                psInsS.setString(1, idDetalleServ);
                psInsS.setString(2, idOrden.trim());
                psInsS.setString(3, idServicioReal);
                psInsS.setInt(4, cantidad);
                psInsS.setDouble(5, subtotal);
                psInsS.addBatch();
            }
            psInsS.executeBatch();
        }

        try (PreparedStatement psInsR = con.prepareStatement(sqlInsertRep)) {
            for (int i = 0; i < listaRepuestos.size(); i++) {
                Object[] fila = listaRepuestos.get(i);
                if (fila == null || fila[0] == null || fila[0].toString().trim().isEmpty()) continue;

                String idRepuestoReal = fila[0].toString().trim();

                int cantidad = 1;
                try {
                    cantidad = (int) Double.parseDouble(fila[1].toString().replace(",", "."));
                } catch (Exception e) {
                    try { cantidad = Integer.parseInt(fila[1].toString().trim()); } catch (Exception ex) {}
                }

                double subtotal = 0.0;
                try {
                    subtotal = Double.parseDouble(fila[2].toString().replace(",", "."));
                } catch (Exception e) {}

                String idDetalleRep = String.format("DTR-%s-%d", numOrden, (i + 1));
                if (idDetalleRep.length() > 15) {
                    idDetalleRep = idDetalleRep.substring(0, 15);
                }

                psInsR.setString(1, idDetalleRep);
                psInsR.setInt(2, cantidad);
                psInsR.setDouble(3, subtotal);
                psInsR.setString(4, idRepuestoReal);
                psInsR.setString(5, idOrden.trim());
                psInsR.addBatch();
            }
            psInsR.executeBatch();
        }

        con.commit();
        return true;

    } catch (SQLException e) {
        if (con != null) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
        System.err.println("Error al actualizar detalles en la BD: " + e.getMessage());
        return false;
    } finally {
        if (con != null) {
            try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}

private String obtenerIdServicioPorNombre(String nombreServicio) {
    if (nombreServicio == null || nombreServicio.trim().isEmpty()) return nombreServicio;

    if (nombreServicio.toUpperCase().startsWith("SERV") || 
        nombreServicio.toUpperCase().startsWith("SRV") || 
        nombreServicio.length() <= 10) {
        return nombreServicio;
    }

    String nombreLimpio = normalizarTexto(nombreServicio);

    String sql = "SELECT * FROM servicio";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            String idVal = rs.getString(1); 
            for (int i = 2; i <= columnCount; i++) {
                String valColumna = rs.getString(i);
                if (valColumna != null) {
                    String valNormalizado = normalizarTexto(valColumna);
                    
                    if (valNormalizado.contains(nombreLimpio) || nombreLimpio.contains(valNormalizado)) {
                        return idVal.trim();
                    }
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar ID de servicio: " + e.getMessage());
    }

    System.err.println("ADVERTENCIA: No se encontró ID en BD para el servicio: " + nombreServicio);
    return nombreServicio;
}
private String normalizarTexto(String texto) {
    if (texto == null) return "";
    String temp = texto.replace("...", "").trim().toLowerCase();
    temp = Normalizer.normalize(temp, Normalizer.Form.NFD);
    temp = temp.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    return temp.replaceAll("[^a-z0-9]", "");
}
private String obtenerIdRepuestoPorNombre(String nombreRepuesto) {
    if (nombreRepuesto == null || nombreRepuesto.trim().isEmpty()) return nombreRepuesto;

    if (nombreRepuesto.toUpperCase().startsWith("REP") || 
        nombreRepuesto.toUpperCase().startsWith("RPT") || 
        nombreRepuesto.length() <= 10) {
        return nombreRepuesto;
    }

    String cleanName = nombreRepuesto.replace("...", "").trim();

    String[] consultas = {
        "SELECT id_repuestos FROM repuestos WHERE LOWER(TRIM(nom_repuesto)) LIKE LOWER(TRIM(?)) LIMIT 1",
        "SELECT id_repuestos FROM repuestos WHERE LOWER(TRIM(nombre_repuesto)) LIKE LOWER(TRIM(?)) LIMIT 1",
        "SELECT id_repuestos FROM repuestos WHERE LOWER(TRIM(nombre)) LIKE LOWER(TRIM(?)) LIMIT 1"
    };

    for (String sql : consultas) {
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cleanName + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String idEncontrado = rs.getString("id_repuestos");
                    if (idEncontrado != null && !idEncontrado.trim().isEmpty()) {
                        return idEncontrado.trim();
                    }
                }
            }
        } catch (SQLException e) {
        }
    }
    return nombreRepuesto;
}
}