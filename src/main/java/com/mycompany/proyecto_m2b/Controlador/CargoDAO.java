/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Cargo;
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
public class CargoDAO {
    

    private static final String INSERTARCARGO =
    "INSERT INTO public.cargo(id_cargo, nom_cargo, descrip_cargo)" +
    "VALUES (?, ?, ?)";
    
    
    public void insertarCargo(Cargo cargo) {
            String IdCar=Generacion_id.generar_id("CAR", "seq_cargo");
            cargo.setID_cargo(IdCar);
            try (Connection conn = ConexionBD.obtenerConexion();
                 PreparedStatement ps = conn.prepareStatement(INSERTARCARGO)) {

                ps.setString(1, IdCar);
                ps.setString(2, cargo.getNom_cargo());
                ps.setString(3, cargo.getDescrip_cargo());
                ps.executeUpdate();
                System.out.println("CARGO insertado correctamente");

            } catch (SQLException e) {
                System.out.println("Error al insertar CARGO: " + e.getMessage());
            }      
        }
        public void cargarCargos(JComboBox Cargos) {

        String sql = """
            SELECT id_cargo, nom_cargo, descrip_cargo
            FROM cargo
            ORDER BY nom_cargo
            """;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            Cargos.removeAllItems();

            while (rs.next()) {

                String id_cargo = rs.getString("id_cargo");
                String Nom_cargo = rs.getString("nom_cargo");
                String Descrip_cargo= rs.getString("descrip_cargo");
                Cargo cargo=new Cargo(id_cargo, Nom_cargo, Descrip_cargo);

                Cargos.addItem(cargo);
            }

        } catch (SQLException e) {
            System.err.println("Error");
        
    
        }
    }
        private static final String MODIFICARCARGO =
            "UPDATE cargo "
            + "SET nom_cargo = ?, descrip_cargo = ? "
            + "WHERE id_cargo=?";
    
    public boolean modificarCargo (Cargo cargo){
        try (Connection conn=ConexionBD.obtenerConexion();
        PreparedStatement ps=conn.prepareStatement(MODIFICARCARGO)){
            ps.setString(1, cargo.getNom_cargo());
            ps.setString(2, cargo.getDescrip_cargo());
            return ps.executeUpdate() > 0;
        }
    catch (SQLException e){
            System.out.println("Error al actualizar cargo: " + e.getMessage());
            return false;
        }
    }
}
