
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Servicio;
import com.mycompany.proyecto_m2b.modelo.Tipo_de_servicio;
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
 * @author HP
 */
public class ServicioDAO {
    
    private  static final String INSERTARSERVICIO=
            "INSERT INTO servicio (id_servi, tiempo_est_hor_servi, precio_del_servicio, nom_servicio, id_tipo_servicio ) " +
            "VALUES (?,?,?,?,?)";
    
    public void insertar  (Servicio servicio) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(INSERTARSERVICIO)) {

            ps.setString(1, servicio.getId_servi());
            ps.setInt(2, servicio.getTiempo_est_hor_servi());
            ps.setFloat(3, servicio.getPrecio_del_servicio());
            ps.setString(4, servicio.getNom_servicio());
            ps.setString(5, servicio.getId_tipo_servicio());

            ps.executeUpdate();
            System.out.println("Servicio insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar tipo de servicio: " + e.getMessage());
        }
    }
    
    private static final String LISTARSERVICIO = "SELECT id_servi, tiempo_est_hor_servi, precio_del_servicio, nom_servicio, id_tipo_servicio FROM servicio";
    public List<Servicio> listarServicio() {
        List<Servicio> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARSERVICIO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Servicio servicio = new Servicio(
                    rs.getString("id_servi"),
                    rs.getInt("tiempo_est_hor_servi"),
                    rs.getFloat("precio_del_servicio"),
                    rs.getString("nom_servicio"),
                    rs.getString("id_tipo_servicio")
                );
                lista.add(servicio);
            }

        } catch (SQLException ex) {
            System.out.println("Error> No se puede listar los servicios: " + ex.getMessage());
        }

        return lista;
    }
    
    public boolean eliminarServicio(String id_servi) {
        String sql = "DELETE FROM servicio WHERE id_servi = ?";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id_servi);
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar tipo de servicio: " + e.getMessage());
            return false;
        }
    }
    
    private static final String ACTUALIZARSERVICIO
            = "UPDATE servicio SET  tiempo_est_hor_servi = ?, precio_del_servicio = ?, nom_servicio = ?, id_tipo_servicio = ? WHERE id_servi = ?";

    public boolean actualizarServicio(Servicio servicio) {
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement ps = conn.prepareStatement(ACTUALIZARSERVICIO)) {

            ps.setInt(1, servicio.getTiempo_est_hor_servi());
            ps.setFloat(2, servicio.getPrecio_del_servicio());
            ps.setString(3, servicio.getNom_servicio());
            ps.setString(4, servicio.getId_tipo_servicio());
            ps.setString(5, servicio.getId_servi());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Servicio actualizado correctamente");
                return true;
            } else {
                System.out.println("No se encontro ningún servicio con el ID ingresado");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el servicio: " + e.getMessage());
            return false;
        }
    }
    
    private static final String OBTENERULTIMOID
            = "SELECT id_servi FROM servicio ORDER BY id_servi DESC LIMIT 1";
    
    public String generarNuevoId() {
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement ps = conn.prepareStatement(OBTENERULTIMOID); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String ultimoId = rs.getString("id_servi"); 
                int numero = Integer.parseInt(ultimoId.substring(3));
                numero++;
                return String.format("SRV%03d", numero);
            } else {
                return "SRV001"; 
            }

        } catch (SQLException e) {
            System.out.println("Error al generar nuevo ID: " + e.getMessage());
            return null;
        }
    }
    
    //PARA ESTADISTICA
    public int contarServiciosRealizados(LocalDate desde, LocalDate hasta) {
        String sql = "SELECT COALESCE(SUM(dor.cantidad_servi), 0)"
                + "FROM detalle_de_orden dor "
                + "JOIN orden_de_servicio os ON dor.id_orden_serv = os.id_orden_serv "
                + "WHERE os.fecha_ingreso BETWEEN ? AND ?";
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
    
        public Map<String, Integer> topServiciosMasRealizados(LocalDate Desde, LocalDate Hasta, int limite) {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        String sql = "SELECT s.nom_servicio AS nombre, SUM(d.cantidad_servi) AS cantidad "
                + "FROM detalle_de_orden d "
                + "JOIN orden_de_servicio o ON d.id_orden_serv = o.id_orden_serv "
                + "JOIN servicio s ON d.id_servi = s.id_servi "
                + "WHERE o.fecha_ingreso BETWEEN ? AND ? "
                + "GROUP BY s.nom_servicio "
                + "ORDER BY cantidad DESC "
                + "LIMIT ?";
        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(Desde));
            ps.setDate(2, java.sql.Date.valueOf(Hasta));
            ps.setInt(3, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.put(rs.getString("nombre"), rs.getInt("cantidad"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
