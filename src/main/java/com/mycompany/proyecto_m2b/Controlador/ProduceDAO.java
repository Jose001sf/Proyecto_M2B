/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Produce;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author usuario
 */
public class ProduceDAO {
    
    private static final String ACTUALIZARPRODUCE =
            "UPDATE produce SET cant_gene = cant_gene + ?, fecha_registro = CURRENT_DATE "
            + "WHERE id_orden_serv = ? AND id_residuos = ?";

    private static final String AUMENTARRESIDUO =
            "UPDATE residuos SET cantidad_actual = cantidad_actual + ? "
            + "WHERE id_residuos = ? AND (cantidad_actual + ?) <= cantidad_max";

    private static final String INSERTARPRODUCE =
            "INSERT INTO produce (id_produccion, id_orden_serv, cant_gene, fecha_registro, id_residuos) "
            + "VALUES (?, ?, ?, CURRENT_DATE, ?)";

    private static final String LISTARPORORDEN = 
            "SELECT p.id_produccion, p.id_orden_serv, p.id_residuos, r.nom_residuo, p.cant_gene, p.fecha_registro " 
            + "FROM produce p "
            + "INNER JOIN residuos r ON p.id_residuos = r.id_residuos "
            + "WHERE p.id_orden_serv = ?";

    private static final String AJUSTARINVENTARIORESIDUO = 
            "UPDATE residuos SET cantidad_actual = cantidad_actual + ? "
            + "WHERE id_residuos = ? AND (cantidad_actual + ?) <= cantidad_max AND (cantidad_actual + ?) >= 0";

    private static final String EDITARCANTIDADPRODUCE = 
            "UPDATE produce SET cant_gene = ? "
            + "WHERE id_produccion = ?";

    public boolean guardarListaTemporal(List<Produce> lista) {
        if (lista == null || lista.isEmpty()){
            return false;
        }
        try (Connection conn = ConexionBD.obtenerConexion()) {
            conn.setAutoCommit(false); 
            try {
                for (Produce p : lista) {                    
                    try (PreparedStatement ps = conn.prepareStatement(AUMENTARRESIDUO)) {
                        ps.setInt(1, p.getCant_gene()); 
                        ps.setString(2, p.getId_residuos());
                        ps.setInt(3, p.getCant_gene()); 
                        if (ps.executeUpdate() == 0) {
                            throw new SQLException("Se supera cantidad máxima o residuo no existe");
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement(ACTUALIZARPRODUCE)) {
                        ps.setInt(1, p.getCant_gene());
                        ps.setString(2, p.getId_orden_serv());
                        ps.setString(3, p.getId_residuos());
                        int filasActualizadas = ps.executeUpdate();
                        if (filasActualizadas == 0) {
                            String idProduccion = Generacion_id.generar_id("PRO", "seq_produce");
                            try (PreparedStatement psInsertar = conn.prepareStatement(INSERTARPRODUCE)) {
                                psInsertar.setString(1, idProduccion);
                                psInsertar.setString(2, p.getId_orden_serv());
                                psInsertar.setInt(3, p.getCant_gene());
                                psInsertar.setString(4, p.getId_residuos());
                                psInsertar.executeUpdate();
                            }
                        }
                    }
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error en transacción Produce: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la base " + e.getMessage());
            return false;
        }
    }
    
    public boolean editarProduceAnterior(String idProduccion, String idResiduo, int cantidadAnterior, int cantidadNueva) {
        int diferencia = cantidadNueva - cantidadAnterior;
        if (diferencia == 0){
            return true;
        }        
        try (Connection conn = ConexionBD.obtenerConexion()) {
            conn.setAutoCommit(false);
            try {                
                try (PreparedStatement ps = conn.prepareStatement(AJUSTARINVENTARIORESIDUO)) {
                    ps.setInt(1, diferencia);
                    ps.setString(2, idResiduo);
                    ps.setInt(3, diferencia);
                    ps.setInt(4, diferencia);
                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas==0){
                        throw new SQLException("Se supera la cantidad máxima");
                    }
                }
                try (PreparedStatement ps = conn.prepareStatement(EDITARCANTIDADPRODUCE)) {
                    ps.setInt(1, cantidadNueva);
                    ps.setString(2, idProduccion);
                    ps.executeUpdate();
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error al editar Produce: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al quere conectarse en la base de datos: "+e.getMessage());
            return false;
        }
    }

    public List<Produce> listarProducePorOrden(String idOrdenServicio) {
        List<Produce> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARPORORDEN)) {            
            ps.setString(1, idOrdenServicio);
            ResultSet rs = ps.executeQuery();            
            while (rs.next()) {
                Produce p = new Produce();
                p.setId_produccion(rs.getString("id_produccion"));
                p.setId_orden_serv(rs.getString("id_orden_serv"));
                p.setId_residuos(rs.getString("id_residuos"));
                p.setNom_residuo(rs.getString("nom_residuo")); 
                p.setCant_gene(rs.getInt("cant_gene"));
                p.setFecha_registro(rs.getDate("fecha_registro"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Produce: " + e.getMessage());
        }
        return lista;
    }
    
    private static final String ELIMINARPRODUCE =
            "DELETE FROM produce "
            + "WHERE id_produccion = ?";
    public boolean eliminarProduce(String idProduccion, String idResiduo, int cantidadGenerada) {
        int diferencia = -cantidadGenerada;
        try (Connection conn = ConexionBD.obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps=conn.prepareStatement(AJUSTARINVENTARIORESIDUO)) {
                    ps.setInt(1, diferencia);
                    ps.setString(2, idResiduo);
                    ps.setInt(3, diferencia);
                    ps.setInt(4, diferencia);
                    int filaAfectada=ps.executeUpdate();
                    if (filaAfectada == 0) {
                        throw new SQLException("No se pudo restar el inventario");
                    }
                }
                try (PreparedStatement ps=conn.prepareStatement(ELIMINARPRODUCE)) {
                    ps.setString(1, idProduccion);
                    int filaEliminada=ps.executeUpdate();
                    if (filaEliminada == 0) {
                        throw new SQLException("No existe el registro");
                    }
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error al eliminar Produce: "+ e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }
    
    
    //sql para datos
    public Map<String, Integer> sumarResiduosPorTipo(LocalDate Desde, LocalDate Hasta) {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        String sql = "SELECT r.nom_residuo, SUM(p.cant_gene) AS total "
                + "FROM produce p "
                + "INNER JOIN residuos r ON p.id_residuos = r.id_residuos "
                + "WHERE p.fecha_registro BETWEEN ? AND ? "
                + "GROUP BY r.nom_residuo ORDER BY total DESC";
        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(Desde));
            ps.setDate(2, java.sql.Date.valueOf(Hasta));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.put(rs.getString("nom_residuo"), rs.getInt("total"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al sumar residuos por tipo: "+e.getMessage());
        }
        return resultado;
    }
}
