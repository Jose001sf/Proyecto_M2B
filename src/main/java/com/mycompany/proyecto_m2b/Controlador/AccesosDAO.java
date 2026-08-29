/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;
import java.sql.*;
import com.mycompany.proyecto_m2b.modelo.Accesos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class AccesosDAO {
    public static final String INSERTARACCESOS = "INSER INTO accesos (id_accesos, accesos, desc_permisos, id_tip_usuario) "
            + "VALUES (?, ?, ?, ?)";
    public void insertarAccesos (Accesos acceso){
        String idGenerado = Generacion_id.generar_id("ACC", "seq_accesos");
        acceso.setId_accesos(idGenerado);
        try (Connection conn = ConexionBD.obtenerConexion(); 
                PreparedStatement ps=conn.prepareStatement(INSERTARACCESOS)){
            ps.setString(1, idGenerado);
            ps.setString(2, acceso.getAccesos());
            ps.setString(3, acceso.getDesc_persmisos());
            ps.setString(4, acceso.getId_tip_usuario());
            ps.executeUpdate();
            System.out.println("Acceso insertado correctamente");
        }
        catch(SQLException e){
            System.out.println("Error al insertar acceso: "+e.getMessage());
        }
    }
    
    public static final String LISTARACCESOS= "SELECT id_accesos, accesos, desc_permisos, id_tip_de_usuario"
            + " FROM accesos "
            +"ORDER BY id_tip_usuario";
    public List<Accesos> listarAccesos (){
        List<Accesos> lista=new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement ps=conn.prepareStatement(LISTARACCESOS)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Accesos ac=new Accesos(rs.getString("id_accesos"),
                rs.getString("accesos"),
                rs.getString("desc_permisos"),
                rs.getString("id_tip_usuario"));
                lista.add(ac);
            }
        }
        catch (SQLException e){
            System.out.println("Error al lista accesos: "+e.getMessage());
        }
        return lista;
    }
    
    private String ACTUALIZARACCESOS = """
                                       UPDATE accesos
                                       SET accesos=?, desc_permisos=?
                                       WHERE id_acceso=?
                                       """;
    public void actualizar(Accesos acceso) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(ACTUALIZARACCESOS)) {
 
            ps.setString(1, acceso.getAccesos());
            ps.setString(2, acceso.getDesc_persmisos());
            ps.setString(3, acceso.getId_accesos());
            ps.executeUpdate();
            System.out.println("Acceso actualizado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar acceso: " + e.getMessage());
        }
    }
    
    private static final String BUSCARPORID =
            "SELECT id_accesos, accesos, desc_permisos, id_tip_usuario "
            + "FROM accesos WHERE id_accesos = ?";
 
    public Accesos buscarPorId(String idAccesos) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(BUSCARPORID)) {
 
            ps.setString(1, idAccesos);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Accesos ac=new Accesos(rs.getString("id_accesos"),
                    rs.getString("accesos"),
                    rs.getString("desc_permisos"),
                    rs.getString("id_tip_usuario"));
                    return ac;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar acceso: " + e.getMessage());
        }
        return null;
    }
    
    private static final String LISTARPORTIPO =
            "SELECT id_accesos, accesos, desc_permisos, id_tip_usuario "
          + "FROM accesos WHERE id_tip_usuario = ?";
 
    public List<Accesos> obtenerPermisosPorTipoUsuario(String idTipoUsuario) {
        List<Accesos> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARPORTIPO)) {
 
            ps.setString(1, idTipoUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Accesos ac=new Accesos(rs.getString("id_accesos"),
                    rs.getString("accesos"),
                    rs.getString("desc_permisos"),
                    rs.getString("id_tip_usuario"));
                    lista.add(ac);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener permisos: " + e.getMessage());
        }
        return lista;
    }
}







