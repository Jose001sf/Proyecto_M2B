
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Tipo_residuo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResiduoDAO {

    public String generarSiguienteID(String tabla, String columnaId, String prefijo) {
        String sql = "SELECT " + columnaId + " FROM " + tabla + " ORDER BY " + columnaId + " DESC LIMIT 1";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String ultimoId = rs.getString(columnaId);
                int num = Integer.parseInt(ultimoId.replace(prefijo, "")) + 1;
                return String.format("%s%03d", prefijo, num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prefijo + "001";
    }

    // Guardar usando el objeto Tipo_residuo
    public boolean guardarTipoResiduo(Tipo_residuo tipo) {
        String sql = "INSERT INTO tipo_residuo (id_tipo_resi, nom_tipo_resi, grado_riesgo, desc_tipo_resi) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipo.getID_tipo_resi());
            ps.setString(2, tipo.getNom_tipo_resi());
            ps.setString(3, tipo.getGrado_riesgo());
            ps.setString(4, tipo.getDesc_tipo_resi());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener la lista de objetos Tipo_residuo para la otra ventana
    public List<Tipo_residuo> obtenerTiposResiduo() {
        List<Tipo_residuo> lista = new ArrayList<>();
        String sql = "SELECT id_tipo_resi, nom_tipo_resi, grado_riesgo, desc_tipo_resi FROM tipo_residuo ORDER BY nom_tipo_resi ASC";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tipo_residuo tipo = new Tipo_residuo(
                    rs.getString("id_tipo_resi"),
                    rs.getString("nom_tipo_resi"),
                    rs.getString("grado_riesgo"),
                    rs.getString("desc_tipo_resi")
                );
                lista.add(tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}