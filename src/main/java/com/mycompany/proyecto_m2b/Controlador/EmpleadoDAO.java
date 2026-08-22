/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class EmpleadoDAO {
    private static final String INSERTAREMPLEADO =
    "INSERT INTO public.empleado (id_empleado, ced_perso, id_cargo, id_especialidad)" +
    "VALUES (?, ?, ?, ?)";
    
    
     public void insertar(Empleado empleado) {
        String IdEmpe=Generacion_id.generar_id("EMP", "seq_empleado");
        empleado.setId_empleado(IdEmpe);
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(INSERTAREMPLEADO)) {

            ps.setString(1, IdEmpe);
            ps.setString(2, empleado.getCed_perso());
            ps.setString(3, empleado.getId_cargo());
            ps.setString(4, empleado.getId_especialidad());            
            ps.executeUpdate();
            System.out.println("EMPLEADO insertada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar EMPLEADO: " + e.getMessage());
        }
      
    }
     private static final String LISTAREMPLEADO =
            "SELECT * "         
            + "FROM empleado ";
    
    public List<Empleado> listarEmpleados() {
    List<Empleado> lista = new ArrayList<>();
    try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTAREMPLEADO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado e = new Empleado(
                        rs.getString("id_empleado"),
                        rs.getString("apellido"),                
                        rs.getString("genero"),
                        rs.getString("estado_civil")
                        
                );
                lista.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Error> No se puede listar: " + ex.getMessage());
        }

        return lista;
    }
    public boolean existeCedula (String ced_perso){
        String sql = "SELECT 1 FROM persona WHERE ced_perso = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, ced_perso);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar cedula: " + e.getMessage());
            return false;
        }
    }
}
