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
     private static final String LISTAREMPLEADO = ""
            +"SELECT e.id_cargo, e.id_especialidad, p.ced_perso, "
            +"p.nom1_person, p.apell1_person, c.nom_cargo, es.nom_especialidad "
            +"FROM Empleado e "
            +"LEFT JOIN Persona p ON p.ced_perso = e.ced_perso "
            +"LEFT JOIN Cargo c ON c.id_cargo = e.id_cargo "
            +"LEFT JOIN Especialidad es ON es.id_especialidad = e.id_especialidad";
    
    public List<Empleado> listarEmpleado() {
    List<Empleado> lista = new ArrayList<>();
     
    try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTAREMPLEADO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado e= new Empleado();
                                
                String nom=rs.getString("nom1_person");
                String apell=rs.getString("apell1_person");
                String ced=rs.getString("ced_perso");
                if (nom!=null && apell!=null){
                    e.setNombre_Completo(nom+" "+apell);
                }
                else if (nom!=null && apell==null){
                    e.setNombre_Completo(nom);
                }
                else if (apell!=null && nom==null){
                    e.setNombre_Completo(apell);
                }
                else{
                    e.setNombre_Completo("No hay");
                }
                e.setCed_perso(ced);
                e.setId_cargo(rs.getString("id_cargo"));
                e.setId_especialidad(rs.getString("id_especialidad"));
                e.setNom_cargo(rs.getString("nom_cargo"));
                e.setNom_especialidad(rs.getString("nom_especialidad"));
                lista.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Error> No se puede listar: " + ex.getMessage());
        }

        return lista;
    }
    public boolean existeCedula (String ced_perso){
        String sql = "SELECT 1 FROM empleado WHERE ced_perso = ?";
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
    private static final String MODIFICAREMPLEADO =
            "UPDATE empleado "
            + "SET id_cargo = ?, id_especialidad = ? "
            + "WHERE id_empleado=?";
    
    public boolean modificarEmpleado (Empleado empleado){
            try (Connection conn=ConexionBD.obtenerConexion();
            PreparedStatement ps=conn.prepareStatement(MODIFICAREMPLEADO)){
                ps.setString(1, empleado.getId_cargo());
                ps.setString(2, empleado.getId_especialidad());
                ps.setString(3, empleado.getId_empleado());
                return ps.executeUpdate() > 0;
            }
        catch (SQLException e){
                System.out.println("Error al actualizar empleado: " + e.getMessage());
                return false;
            }
        }
        public Empleado buscarPorCedula(String cedula) {
        String sql = "SELECT e.id_empleado, p.ced_perso, c.id_cargo, es.id_especialidad, c.nom_cargo, es.nom_especialidad "
                + "FROM empleado e "
                + "LEFT JOIN persona p ON p.ced_perso=e.ced_perso "
                + "LEFT JOIN cargo c ON c.id_cargo=e.id_cargo "
                + "LEFT JOIN especialidad es ON es.id_especialidad = e.id_especialidad "
                + "WHERE e.ced_perso = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Empleado e = new Empleado();
                    e.setId_empleado(rs.getString("id_empleado"));
                    e.setCed_perso(rs.getString("ced_perso"));
                    e.setId_cargo(rs.getString("id_cargo"));
                    e.setId_especialidad(rs.getString("id_especialidad"));
                    e.setNom_cargo(rs.getString("nom_cargo"));
                    e.setNom_especialidad(rs.getString("nom_especialidad"));
                    return e;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar empleado por cedula: " + ex.getMessage());
        }
        return null;
    }
        
        private static final String FILTRAREMPLEADOS =
            "SELECT e.id_empleado, e.ced_perso, e.id_cargo, e.id_especialidad, p.nom1_person, p.nom2_person, p.apell1_person, p.apell2_person, c.nom_cargo, es.nom_especialidad "
            + "FROM empleado e "
            + "INNER JOIN persona p ON p.ced_perso = e.ced_perso "
            + "INNER JOIN cargo c ON c.id_cargo = e.id_cargo "
            + "INNER JOIN especialidad es ON es.id_especialidad = e.id_especialidad "
            + "WHERE p.ced_perso ILIKE ? AND (p.nom1_person ILIKE ? OR p.nom2_person ILIKE ? OR p.apell1_person ILIKE ? OR p.apell2_person ILIKE ?) "
            + "AND c.nom_cargo ILIKE ? "
            + "AND es.nom_especialidad ILIKE ? "
            + "ORDER BY p.nom1_person, p.apell1_person";
        
        public List<Empleado> filtrarEmpleados(String cedula, String nombre, String cargo, String especialidad) {
        List<Empleado> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps =conn.prepareStatement(FILTRAREMPLEADOS)) {
            ps.setString(1, "%" + cedula.trim() + "%");
            ps.setString(2, "%" + nombre.trim() + "%");
            ps.setString(3, "%" + nombre.trim() + "%");
            ps.setString(4, "%" + nombre.trim() + "%");
            ps.setString(5, "%" + nombre.trim() + "%");
            ps.setString(6, "%" + cargo.trim() + "%");
            ps.setString(7, "%" + especialidad.trim() + "%");
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Empleado empleado = new Empleado();
                
                empleado.setId_empleado(rs.getString("id_empleado"));
                empleado.setCed_perso(rs.getString("ced_perso"));
                empleado.setId_cargo(rs.getString("id_cargo"));
                empleado.setId_especialidad(rs.getString("id_especialidad"));
                String nombreCompleto = rs.getString("nom1_person") + " "+ rs.getString("nom2_person") + " "+ rs.getString("apell1_person") + " "+ rs.getString("apell2_person");
                empleado.setNombre_Completo(nombreCompleto.trim().replaceAll("\\s+", " "));
                empleado.setNom_cargo(rs.getString("nom_cargo"));
                empleado.setNom_especialidad(rs.getString("nom_especialidad"));
                lista.add(empleado);
            }
        } catch (SQLException e) {
            System.out.println("Error al filtrar empleados: " + e.getMessage());
        }
        return lista;
    }
}
