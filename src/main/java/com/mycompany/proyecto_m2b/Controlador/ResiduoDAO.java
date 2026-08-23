
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Residuos;
import com.mycompany.proyecto_m2b.modelo.Tipo_residuo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResiduoDAO {

    public String generarSiguienteID(String tabla, String columnaId, String prefijo) {
        String sql = "SELECT " + columnaId + " FROM " + tabla + " WHERE " + columnaId + " LIKE ? ORDER BY " + columnaId + " DESC LIMIT 1";
        int siguienteNumero = 1;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, prefijo + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String ultimoId = rs.getString(columnaId);
                String numeroStr = ultimoId.replace(prefijo, "").trim();
                siguienteNumero = Integer.parseInt(numeroStr) + 1;
            }

        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error al obtener ID secuencial: " + e.getMessage());
        }

        return String.format("%s%03d", prefijo, siguienteNumero);
    }

    public boolean registrarResiduo(Residuos residuo, Tipo_residuo tipo) {
        String sqlTipo = "INSERT INTO tipo_residuo (id_tipo_resi, nom_tipo_resi, grado_riesgo, desc_tipo_resi) VALUES (?, ?, ?, ?)";
        String sqlResiduo = "INSERT INTO residuos (id_residuos, nom_residuo, estado_residuo, cantidad_actual, cantidad_max, id_tipo_resi) VALUES (?, ?, ?, ?, ?, ?)";

        Connection con = null;

        try {
            con = ConexionBD.obtenerConexion();
            con.setAutoCommit(false);

            try (PreparedStatement psTipo = con.prepareStatement(sqlTipo)) {
                psTipo.setString(1, tipo.getID_tipo_resi());
                psTipo.setString(2, tipo.getNom_tipo_resi());
                psTipo.setString(3, tipo.getGrado_riesgo());
                psTipo.setString(4, tipo.getDesc_tipo_resi());
                psTipo.executeUpdate();
            }

            try (PreparedStatement psResiduo = con.prepareStatement(sqlResiduo)) {
                psResiduo.setString(1, residuo.getID_resiudos());
                psResiduo.setString(2, residuo.getNom_residuo());
                psResiduo.setString(3, residuo.getEstado_residuo());
                psResiduo.setInt(4, residuo.getCantidad_actual());
                psResiduo.setInt(5, residuo.getCantidad_max());
                psResiduo.setString(6, residuo.getID_tipo_resi());
                psResiduo.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error Rollback: " + ex.getMessage());
                }
            }
            System.err.println("Error al registrar: " + e.getMessage());
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar conexion: " + e.getMessage());
                }
            }
        }
    }
}