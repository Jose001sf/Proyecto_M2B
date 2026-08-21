/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;
import java.sql.*;
import com.mycompany.proyecto_m2b.Controlador.ConexionBD;
import java.sql.SQLException;
/**
 *
 * @author usuario
 */
public class Generacion_id {
    public static String generar_id(String prefijo, String secuencia){
        String sql= "SELECT nextval(?)";
        
        try(Connection con = ConexionBD.obtenerConexion();PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, secuencia);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    //Variable que puede almacenar una cantidad alta de numero entreos hasta 64 bits
                    long id=rs.getLong(1);
                    return String.format("%s-%04d", prefijo, id);
                }
            }
        }catch(SQLException e){
            System.out.println("ERROR al generar la id con secuencia: "+secuencia+",: "+e.getMessage());
        }        
        return null;
    }
}
