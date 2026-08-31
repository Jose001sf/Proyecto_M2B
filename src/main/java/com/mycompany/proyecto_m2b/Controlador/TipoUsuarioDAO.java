/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Tipo_de_servicio;
import com.mycompany.proyecto_m2b.modelo.Tipos_de_usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author usuario
 */
public class TipoUsuarioDAO {
    private  static final String INSERTARTIPOUSUARIO=
            "INSERT INTO public.tipos_de_usuario(id_tip_de_usuario, nom_tip_usuario) " +
            "VALUES (?, ?)";
    
    public void insertarTiposUsuario(Tipos_de_usuario tipoUsuario) {
        String IdTip=Generacion_id.generar_id("TIPUS", "public.seq_tipo_usuario");
        tipoUsuario.setId_tip_de_usuario(IdTip);
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(INSERTARTIPOUSUARIO)) {

            ps.setString(1, IdTip);
            ps.setString(2, tipoUsuario.getNom_tip_de_usuario());
            ps.executeUpdate();
            System.out.println("Tipo de usuario insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar tipo de usuario: " + e.getMessage());
        }
    }
    public void cargarTiposUsuario(JComboBox TiposUsuarios) {
        String sql = """
            SELECT id_tip_de_usuario, nom_tip_usuario
            FROM tipos_de_usuario
            ORDER BY nom_tip_usuario
            """;
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            TiposUsuarios.removeAllItems();
            while (rs.next()) {
                String id = rs.getString("id_tip_de_usuario");
                String nombre = rs.getString("nom_tip_usuario");
                Tipos_de_usuario tipo = new Tipos_de_usuario(id, nombre);
                TiposUsuarios.addItem(tipo);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar tipos de usuario: " + e.getMessage());
        }
    }    
    
        public List<Tipos_de_usuario> listarTodos() {
        List<Tipos_de_usuario> lista = new ArrayList<>();
        String sql = "SELECT id_tip_de_usuario, nom_tip_usuario "
                + "FROM tipos_de_usuario "
                + "ORDER BY nom_tip_usuario";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tipos_de_usuario t = new Tipos_de_usuario();
                t.setId_tip_de_usuario(rs.getString("id_tip_de_usuario"));
                t.setNom_tip_de_usuario(rs.getString("nom_tip_usuario"));
                lista.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar tipos de usuario: " + e.getMessage());
        }
        return lista;
    }
}




