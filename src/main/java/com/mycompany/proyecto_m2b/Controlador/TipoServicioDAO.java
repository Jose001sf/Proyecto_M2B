
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Persona;
import com.mycompany.proyecto_m2b.modelo.Tipo_de_servicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoServicioDAO {
    
    private  static final String INSERTARTIPOSERVICIO=
            "INSERT INTO tipo_de_servicio (id_tipo_servicio, nom_tipo_servi, desc_tipo_servicio ) " +
            "VALUES (?,?, ?)";
    
    public void insertarTipoServicio(Tipo_de_servicio tipoServicio) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(INSERTARTIPOSERVICIO)) {

            ps.setString(1, tipoServicio.getID_tipo_servicio());
            ps.setString(2, tipoServicio.getNom_tipo_servi());
            ps.setString(3, tipoServicio.getDesc_tipo_servicio());

            ps.executeUpdate();
            System.out.println("Tipo de servicio insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar tipo de servicio: " + e.getMessage());
        }
    }
    
    private static final String LISTARTIPOSERVICIO = "SELECT id_tipo_servicio, nom_tipo_servi, desc_tipo_servicio FROM tipo_de_servicio";
    public List<Tipo_de_servicio> listarTipoServicio() {
        List<Tipo_de_servicio> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARTIPOSERVICIO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tipo_de_servicio servicio = new Tipo_de_servicio(
                    rs.getString("id_tipo_servicio"),
                    rs.getString("nom_tipo_servi"),
                    rs.getString("desc_tipo_servicio")
                );
                lista.add(servicio);
            }

        } catch (SQLException ex) {
            System.out.println("Error> No se puede listar los tipos de servicio: " + ex.getMessage());
        }

        return lista;
    }
    
    public boolean eliminarTipoServicio(String idTipoServicio) {
        String sql = "DELETE FROM tipo_de_servicio WHERE id_tipo_servicio = ?";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idTipoServicio);
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("No se puede eliminar el tipo de servicio porque tiene servicios asociados. Elimine o reasigne esos servicios antes de continuar. " + e.getMessage());
            return false;
        }
    }
    
    private static final String ACTUALIZARTIPOSERVICIO
            = "UPDATE tipo_de_servicio SET nom_tipo_servi = ?, desc_tipo_servicio = ? WHERE id_tipo_servicio = ?";

    public boolean actualizarTipoServicio(Tipo_de_servicio tipoServicio) {
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement ps = conn.prepareStatement(ACTUALIZARTIPOSERVICIO)) {

            ps.setString(1, tipoServicio.getNom_tipo_servi());
            ps.setString(2, tipoServicio.getDesc_tipo_servicio());
            ps.setString(3, tipoServicio.getID_tipo_servicio());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Tipo de servicio actualizado correctamente");
                return true;
            } else {
                System.out.println("No se encontro ningún tipo de servicio con el ID ingresado");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el tipo de servicio: " + e.getMessage());
            return false;
        }
    }
    
    private static final String OBTENERULTIMOID
            = "SELECT id_tipo_servicio FROM tipo_de_servicio ORDER BY id_tipo_servicio DESC LIMIT 1";

    public String generarNuevoId() {
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement ps = conn.prepareStatement(OBTENERULTIMOID); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String ultimoId = rs.getString("id_tipo_servicio"); 
                int numero = Integer.parseInt(ultimoId.substring(2));
                numero++;
                return String.format("TS%03d", numero);
            } else {
                return "TS001"; 
            }

        } catch (SQLException e) {
            System.out.println("Error al generar nuevo ID: " + e.getMessage());
            return null;
        }
    }
}
