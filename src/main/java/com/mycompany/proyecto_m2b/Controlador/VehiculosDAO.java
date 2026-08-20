
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Vehiculos;
import java.sql.*;

public class VehiculosDAO {
        private static final String INSERTARVEHICULO =
            "INSERT INTO vehiculo (id_vehi, anio_sal_vehi, num_chasis_vehi, color_vehi, cilindraje_vehi, transmision_vehi, num_puertas_vehi, kilometraje_vehi, num_motor_vehi, placa_carro, id_propietario, id_mode) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

    public void insertarVehiculos(Vehiculos vehiculos) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(INSERTARVEHICULO)) {

            ps.setString(1, vehiculos.getID_vehi());
            ps.setDate(2, new java.sql.Date(vehiculos.getAnio_sal_vehi().getTime()));
            ps.setString(3, vehiculos.getNum_chasis_vehi());
            ps.setString(4, vehiculos.getColor_vehi());
            ps.setString(5, vehiculos.getCilindraje_vehi());
            ps.setString(6, vehiculos.getTransmision_vehi());     
            ps.setInt(7, vehiculos.getNum_puertas_vehi());
            ps.setInt(8, vehiculos.getKilometraje_vehi());
            ps.setString(9, vehiculos.getNum_motor_vehi());
            ps.setString(10, vehiculos.getPlaca_carro());
            ps.setString(11, vehiculos.getID_propietario_vehi());
            ps.setString(12, vehiculos.getID_mode_vehi());
            
            ps.executeUpdate();
            System.out.println("Datos guardados correctamente");

        } catch (SQLException e) {
            System.out.println("Error 404: " + e.getMessage());
        }
    }
}
