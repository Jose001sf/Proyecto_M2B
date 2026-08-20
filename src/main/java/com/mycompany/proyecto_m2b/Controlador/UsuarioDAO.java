/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Usuario;
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
public class UsuarioDAO {
    private  static final String INSERTARUSUARIO=
            "INSERT INTO public.usuario(id_usuario, nombre_usuario, contra_usuario, estado_acti_usuario, id_empleado, id_tip_usuario)" +
            "VALUES (?, ?, ?, ?, ?, ?)";
    
     public void insertar(Usuario usuario) {
        String IdGenerado=Generacion_id.generar_id("USU", "seq_usuario");
        if (IdGenerado==null){
            System.out.println("Error al generar ID");
            return;
        }
         try (Connection conn = ConexionBD.obtenerConexion();
            PreparedStatement ps = conn.prepareStatement(INSERTARUSUARIO)) {            
            ps.setString(1, usuario.getContra_usuario());
            ps.setString(2, IdGenerado);
            ps.setString(3, usuario.getId_empleado());
            ps.setString(4, usuario.getNombre_usuario());
            ps.setString(5, usuario.getTip_usuario());
            ps.setBoolean(6, usuario.isEstado_acti_usuario());
            ps.executeUpdate();
            System.out.println("Usuario insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar Usuario: " + e.getMessage());
        }
      
    }
    private static final String LISTARUSUARIO =
            "SELECT *"         
            + "FROM usuario ";
    
    public List<Usuario> listarUsuario() {
    List<Usuario> lista = new ArrayList<>();
     
    try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARUSUARIO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u= new Usuario(
                        rs.getString("ID_usuario"),
                        rs.getString("Nombre_usuario"),                    
                        rs.getString("Contra_usuario"),
                        rs.getBoolean("Estado_acti_usuario"),
                        rs.getString("id_empleado"),
                        rs.getString("tip_usuario")
                );
                lista.add(u);
            }

        } catch (SQLException ex) {
            System.out.println("Error> No se puede listar: " + ex.getMessage());
        }

        return lista;
    }
    public boolean eliminarUsuario (String id_usuario) {
        String sql="DELETE FROM usuario WHERE id_usuario=?";
        try(Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt=conn.prepareStatement(sql)){
            stmt.setString(1, id_usuario);
            int filas=stmt.executeUpdate();
            return filas>0;
        } catch (Exception e){
            System.out.println("Error al eliminar usuario: "+e.getMessage());
            return false;
        }
    }
    private static final String MODIFICARUSUARIO =
            "UPDATE usuario "
            + "SET nombre_usuario=?, contra_usuario=?, estado_acti_usuario=?, id_empleado=?, id_tip_usuario=?"
            + "WHERE id_usuario=?";
    public boolean modificarUsuario (Usuario usuario){
        try (Connection conn=ConexionBD.obtenerConexion();
        PreparedStatement ps=conn.prepareStatement(MODIFICARUSUARIO)){
            ps.setString(1, usuario.getContra_usuario());
            ps.setString(2, usuario.getID_usuario());
            ps.setString(3, usuario.getId_empleado());
            ps.setString(4, usuario.getNombre_usuario());
            ps.setString(5, usuario.getTip_usuario());
            ps.setBoolean(6, usuario.isEstado_acti_usuario());
            int filas=ps.executeUpdate();
            return filas>0;
        }catch (SQLException e){
                System.out.println("Error al modificar usuario: "+e.getMessage());
                return false;
                }
    }
    public List<Usuario> buscarUsuario(String criterio) {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT * FROM usuario WHERE id_usuario";

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "%" + criterio + "%");
        ps.setString(2, criterio);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getString("ID_usuario"),
                        rs.getString("Nombre_usuario"),                    
                        rs.getString("Contra_usuario"),
                        rs.getBoolean("Estado_acti_usuario"),
                        rs.getString("id_empleado"),
                        rs.getString("tip_usuario")  
                );
                lista.add(u);
            }
        }
    } catch (SQLException ex) {
        System.out.println("Error al buscar usuario: " + ex.getMessage());
    }
    return lista;
}
}