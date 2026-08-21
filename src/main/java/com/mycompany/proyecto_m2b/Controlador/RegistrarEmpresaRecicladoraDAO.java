package com.mycompany.proyecto_m2b.Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrarEmpresaRecicladoraDAO {

    public boolean guardarEmpresaCompleta(String idDir, String ciudad, String calles,String idTipo, String descTipo,String idEmpresa, String nombreEmpresa, String telefono) {
        Connection conn = null;
        try {
            conn = ConexionBD.obtenerConexion();
            conn.setAutoCommit(false);

            String sqlDir = "INSERT INTO direccion_empresa_recicladora "
                    + "(id_direccion_empresa_recicladora, nom_ciudad_dir_emp, nom_calles_dir_emp) "
                    + "VALUES (?, ?, ?)";
            try (PreparedStatement psDir = conn.prepareStatement(sqlDir)) {
                psDir.setString(1, idDir);
                psDir.setString(2, ciudad);
                psDir.setString(3, calles);
                psDir.executeUpdate();
            }

            String sqlTipo = "INSERT INTO tipo_empresa (id_tipo_emp, desc_emp) VALUES (?, ?)";
            try (PreparedStatement psTipo = conn.prepareStatement(sqlTipo)) {
                psTipo.setString(1, idTipo);
                psTipo.setString(2, descTipo);
                psTipo.executeUpdate();
            }

            String sqlEmp = "INSERT INTO empresa_recicladora "
                    + "(id_empresa_rec, nom_empresa_rec, telf_empresa_rec, id_direccion_empresa_recicladora, id_tipo_emp) "
                    + "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psEmp = conn.prepareStatement(sqlEmp)) {
                psEmp.setString(1, idEmpresa);
                psEmp.setString(2, nombreEmpresa);
                psEmp.setString(3, telefono);
                psEmp.setString(4, idDir);    
                psEmp.setString(5, idTipo);     
                psEmp.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en la transacción: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public String generarSiguienteID(String tabla, String columna, String prefijo) {
    String sql = "SELECT " + columna + " FROM " + tabla + " ORDER BY " + columna + " DESC LIMIT 1";
    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            String ultimoId = rs.getString(1); 
            int numero = Integer.parseInt(ultimoId.substring(prefijo.length()));
            return String.format("%s%07d", prefijo, numero + 1);
        }
    } catch (Exception e) {
        System.err.println("Error al generar ID para " + tabla + ": " + e.getMessage());
    }
    return prefijo + "0000001";
}
}