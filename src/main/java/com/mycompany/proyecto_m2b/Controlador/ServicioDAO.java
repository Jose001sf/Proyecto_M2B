/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Servicio;
import com.mycompany.proyecto_m2b.modelo.Tipo_de_servicio;
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
}
