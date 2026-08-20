/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Persona;
import com.mycompany.proyecto_m2b.modelo.Tipo_de_servicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class TipoServicioDAO {
    
    private  static final String INSERTARTIPOSERVICIO=
            "INSERT INTO tipo_de_servicio (id_tipo_servicio, nom_tipo_servi, desc_tipo_servicio ) " +
            "VALUES (?,?, ?)";
    
    public void insertar(Tipo_de_servicio tipoServicio) {
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
    
}
