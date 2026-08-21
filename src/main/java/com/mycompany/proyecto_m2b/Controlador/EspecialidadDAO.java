/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Especialidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

/**
 *
 * @author usuario
 */
public class EspecialidadDAO {
    private static final String INSERTESPECIALIDAD =
    "INSERT INTO public.especialidad(id_especialidad, nom_especialidad, descrip_especi)" +
    "VALUES (?, ?, ?)";
    
    
    public void insertarEspecialidad(Especialidad especialidad) {
            String IdEspe=Generacion_id.generar_id("ESP", "seq_cargo");
            especialidad.setID_especialidad(IdEspe);
            try (Connection conn = ConexionBD.obtenerConexion();
                 PreparedStatement ps = conn.prepareStatement(INSERTESPECIALIDAD)) {

                ps.setString(1, IdEspe);
                ps.setString(2, especialidad.getNom_especialidad());
                ps.setString(3, especialidad.getDescrip_especi());
                ps.executeUpdate();
                System.out.println("ESPECIALIDAD insertado correctamente");

            } catch (SQLException e) {
                System.out.println("Error al insertar ESPECIALIDAD: " + e.getMessage());
            }      
        }
    public void cargarEspecialidades(JComboBox comboEspecialidades) {
        String sql = """
            SELECT id_especialidad, nom_especialidad, descrip_especi
            FROM especialidad
            ORDER BY nom_especialidad
            """;
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            comboEspecialidades.removeAllItems();
            while (rs.next()) {
                String id = rs.getString("id_especialidad");
                String nombre = rs.getString("nom_especialidad");
                String descripcion = rs.getString("descrip_especi");
                Especialidad especialidad = new Especialidad(id, nombre, descripcion);
                comboEspecialidades.addItem(especialidad);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar especialidades: " + e.getMessage());
        }
    }
}
