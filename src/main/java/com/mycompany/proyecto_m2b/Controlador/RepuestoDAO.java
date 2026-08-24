
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Repuesto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RepuestoDAO {

    public boolean guardarRepuesto(Repuesto repuesto, Connection conexion) {
        String sql = "INSERT INTO public.repuestos ("
                   + "id_repuestos, nom_repuesto, cantidad_max_repuesto, cantidad_min_repuesto, "
                   + "cantidad_actual_repuesto, precio_repuesto_unit, descrip_repuesto, "
                   + "id_tip_repuesto, id_marca_repuesto) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, repuesto.getIdRepuestos());
            ps.setString(2, repuesto.getNomRepuesto());
            ps.setInt(3, repuesto.getCantidadMaxRepuesto());
            ps.setInt(4, repuesto.getCantidadMinRepuesto());
            ps.setInt(5, repuesto.getCantidadActualRepuesto());
            ps.setDouble(6, repuesto.getPrecioRepuestoUnit());
            ps.setString(7, repuesto.getDescripRepuesto());
            ps.setString(8, repuesto.getIdTipRepuesto());
            ps.setString(9, repuesto.getIdMarcaRepuesto());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar en la base de datos: " + e.getMessage());
            return false;
        }
    }
}