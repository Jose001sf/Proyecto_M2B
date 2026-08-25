/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Propietario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author castr
 */
public class PropietarioDAO {
    private  static final String INSERTARPROPIETARIO=
            "INSERT INTO public.propietario(id_propietario, observaci_propietario, ced_perso)" +
            "VALUES (?, ?, ?)";
    
    
     public void insertarPropietario(Propietario propietario) {
         String IdProp=Generacion_id.generar_id("PROP", "seq_propietario");
         propietario.setID_propietario(IdProp);
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(INSERTARPROPIETARIO)) {

            ps.setString(1, IdProp);
            ps.setString(2, propietario.getObservaci_propietario());
            ps.setString(3, propietario.getCed_perso());
            ps.executeUpdate();
            System.out.println("Propietario insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar Propietario: " + e.getMessage());
        }
      
    }
    private static final String LISTARPROPIETARIO =
            "SELECT *"         
            + "FROM propietario ";
    
    public List<Propietario> listarPropietario() {
    List<Propietario> lista = new ArrayList<>();
     
    try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARPROPIETARIO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Propietario p = new Propietario(
                        rs.getString("ID_propietario"),
                        rs.getString("Observaci_propietario"),                    
                        rs.getString("ced_perso")   
                );
                lista.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("Error> No se puede listar: " + ex.getMessage());
        }

        return lista;
    }
    public boolean eliminarPropietario (String id_propietario) {
        String sql="DELETE FROM propietario WHERE id_propietario=?";
        try(Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt=conn.prepareStatement(sql)){
            stmt.setString(1, id_propietario);
            int filas=stmt.executeUpdate();
            return filas>0;
        } catch (Exception e){
            System.out.println("Error al eliminar propietario: "+e.getMessage());
            return false;
        }
    }
    private static final String MODIFICARPROPIETARIO =
            "UPDATE propietario "
            + "SET observaci_propietario=? "
            + "WHERE id_propietario=?";
    public boolean modificarPropietario (Propietario propietario){
        try (Connection conn=ConexionBD.obtenerConexion();
        PreparedStatement ps=conn.prepareStatement(MODIFICARPROPIETARIO)){
            ps.setString(1, propietario.getObservaci_propietario());
            ps.setString(2, propietario.getID_propietario());
            
            int filas=ps.executeUpdate();
            return filas>0;
        }catch (SQLException e){
                System.out.println("Error al modificar propietario: "+e.getMessage());
                return false;
                }
    }
        public List<Propietario> buscarPropietario(String criterio) {
        List<Propietario> lista = new ArrayList<>();
        String sql = "SELECT * FROM propietario WHERE id_propietario";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, criterio);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Propietario p = new Propietario(
                            rs.getString("ID_propietario"),
                            rs.getString("Observaci_propietario"),                    
                            rs.getString("ced_perso")   
                    );
                    lista.add(p);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar propietario: " + ex.getMessage());
        }
        return lista;
    }
        public boolean existeCedula (String ced_perso){
        String sql = "SELECT 1 FROM propietario WHERE ced_perso = ?";
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
        public Propietario buscarPorCedula(String cedula) {
        String sql = "SELECT id_propietario, observaci_propietario, ced_perso "
                   + "FROM propietario WHERE ced_perso = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Propietario p = new Propietario();
                    p.setID_propietario(rs.getString("id_propietario"));
                    p.setObservaci_propietario(rs.getString("observaci_propietario"));
                    p.setCed_perso(rs.getString("ced_perso"));                    
                    return p;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar propietario por cedula: " + ex.getMessage());
        }
        return null;
    }
}
