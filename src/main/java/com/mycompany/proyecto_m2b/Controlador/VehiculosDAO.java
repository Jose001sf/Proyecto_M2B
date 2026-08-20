
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Vehiculos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VehiculosDAO {
        private static final String INSERTARVEHICULO =
            "INSERT INTO vehiculo (cedula, nombre, apellido, direccion, estadoCivil, genero, fechaNacimiento, codigopais) " +
            "VALUES (?,?,?,?,?,?,?,?)";

    public void insertar(Vehiculos vehiculos) {
        try (Connection conn = ConexionBD.getConnection();
                
             PreparedStatement ps = conn.prepareStatement(INSERTARVEHICULO)) {

            ps.setString(1, persona.getCedula());
            ps.setString(2, persona.getNombre());
            ps.setString(3, persona.getApellido());
            ps.setString(4, persona.getDireccion());
            ps.setString(5, persona.getEstadoCivil());
            ps.setString(6, persona.getGenero());
            ps.setDate(7, Date.valueOf(persona.getFechaNacimiento()));
            ps.setString(8, persona.getCodigoPais());

            ps.executeUpdate();
            System.out.println("Datos guardados correctamente");

        } catch (SQLException e) {
            System.out.println("Error 404: " + e.getMessage());
        }
    }
}
