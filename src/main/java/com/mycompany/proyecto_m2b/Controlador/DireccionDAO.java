/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Direccion;
import com.mycompany.proyecto_m2b.modelo.Tipos_de_usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author usuario
 */
public class DireccionDAO {
    private  static final String INSERTARDIRECCION=
            "INSERT INTO public.direccion(id_direccion, calle_principal, calle_secundaria, numero_casa, ciudad) " +
            "VALUES (?, ?, ?, ?, ?)";
    
    public void insertar(Direccion direccion) {
        String IDirecc=Generacion_id.generar_id("DIRECC", "public.seq_direccion");
        direccion.setID_direccion(IDirecc);
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(INSERTARDIRECCION)) {

            ps.setString(1, IDirecc);
            ps.setString(2, direccion.getCalle_principal());
            ps.setString(3, direccion.getCalle_secundaria());
            ps.setString(4, direccion.getNumero_casa());
            ps.setString(5, direccion.getCiudad());
            ps.executeUpdate();
            System.out.println("Direccion insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar la Direccion: " + e.getMessage());
        }
    }
    public Direccion buscarIDDir(String id_direccion){        
        String sql= "SELECT * FROM public.direccion WHERE id_direccion=?";
        try(Connection conn=ConexionBD.obtenerConexion();PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setString(1, id_direccion);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                Direccion dir = new Direccion();
                dir.setID_direccion(rs.getString("id_direccion"));
                dir.setCalle_principal(rs.getString("calle_principal"));
                dir.setCalle_secundaria(rs.getString("calle_secundaria"));
                dir.setNumero_casa(rs.getString("numero_casa"));
                dir.setCiudad(rs.getString("ciudad"));
                return dir;
            }
            }
        }catch(SQLException e){
            System.out.println("Error al bucar: "+id_direccion+", ");
        }
        return null;
    }
    
    private static final String MODIFICARDIRECCION =
            "UPDATE direccion "
            + "SET calle_principal = ?, calle_secundaria = ?, numero_casa = ?, ciudad = ? "
            + "WHERE id_direccion=?";
    
    public void modificarDireccion (Direccion direccion){
        try (Connection conn=ConexionBD.obtenerConexion();
        PreparedStatement ps=conn.prepareStatement(MODIFICARDIRECCION)){
            ps.setString(1, direccion.getCalle_principal());
            ps.setString(2, direccion.getCalle_secundaria());
            ps.setString(3, direccion.getNumero_casa());
            ps.setString(4, direccion.getCiudad());
            ps.setString(5, direccion.getID_direccion());            
            ps.executeUpdate();
            System.out.println("Direccion modificada de manera correcta");
        }
    catch (SQLException e){
            System.out.println("Error al modificar direccion: "+e.getMessage());
        }
    }
}
