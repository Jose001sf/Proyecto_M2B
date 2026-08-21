/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Persona;
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
public class PersonaDAO {
    private static final String INSERTARPERSONA =
    "INSERT INTO public.persona (ced_perso, nom1_person, nom2_person, apell1_person, apell2_person, num_celu_person, num_tel_person, gene_person, fech_nac_perso, corr_elec_perso, fech_registro_person, id_direccion)" +
    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    
     public void insertar(Persona persona) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(INSERTARPERSONA)) {
            ps.setString(1, persona.getCed_perso());
            ps.setString(2, persona.getNom1_person());
            ps.setString(3, persona.getNom2_person());
            ps.setString(4, persona.getApell1_person());
            ps.setString(5, persona.getApell2_person());
            ps.setString(6, persona.getNum_celu_person());
            ps.setString(7, persona.getNum_tel_person());
            ps.setString(8, persona.getGene_person());
            ps.setDate(9, persona.getFech_nac_perso());
            ps.setString(10, persona.getCorr_elec_perso());
            ps.setDate(11, persona.getFech_registro_person());
            ps.setString(12, persona.getId_direccion());
            ps.executeUpdate();
            System.out.println("PERSONA insertada correctamente");
        } catch (SQLException e) {
            System.out.println("Error al insertar PERSONA: " + e.getMessage());
        }
      
    }
     private static final String LISTARPERSONA =
            "SELECT * "         
            + "FROM persona ";
    
    public List<Persona> listarPersonas() {
    List<Persona> lista = new ArrayList<>();
    try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARPERSONA);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Persona p = new Persona(
                        rs.getString("ced_person"),
                        rs.getString("nom1_person"),                
                        rs.getString("nom2_person"),
                        rs.getString("apell1_person"),
                        rs.getString("apell2_person"),
                        rs.getString("num_cel_person"),
                        rs.getString("num_tel_person"),
                        rs.getString("gene_person"),
                        rs.getDate("fecha_nac_perso"),
                        rs.getString("corr_elec_perso"),
                        rs.getDate("fech_registro_person"),
                        rs.getString("id_direccion")                        
                );
                lista.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("Error> No se puede listar: " + ex.getMessage());
        }

        return lista;
    }
    public Persona buscarPorCedula(String cedula) {
        String sql = "SELECT ced_perso, nom1_person, nom2_person, apell1_person, apell2_person, num_celu_person, num_tel_person, gene_person, fech_nac_perso, corr_elec_perso, fech_registro_person, id_direccion "
                   + "FROM persona WHERE ced_perso = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona p = new Persona();
                    p.setCed_perso(rs.getString("ced_perso"));
                    p.setNom1_person(rs.getString("nom1_person"));
                    p.setNom2_person(rs.getString("nom2_person"));
                    p.setApell1_person(rs.getString("apell1_person"));
                    p.setApell2_person(rs.getString("apell2_person"));
                    p.setNum_celu_person(rs.getString("num_celu_person"));
                    p.setNum_tel_person(rs.getString("num_tel_person"));
                    p.setGene_person(rs.getString("gene_person"));
                    p.setFech_nac_perso(rs.getDate("fech_nac_perso"));
                    p.setCorr_elec_perso(rs.getString("corr_elec_perso"));
                    p.setFech_registro_person(rs.getDate("fech_registro_person"));
                    p.setId_direccion(rs.getString("id_direccion"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar persona por cedula: " + e.getMessage());
        }
        return null;
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
