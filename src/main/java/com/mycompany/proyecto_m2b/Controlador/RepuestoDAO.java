
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.MarcaRepuesto;
import com.mycompany.proyecto_m2b.modelo.Repuesto;
import com.mycompany.proyecto_m2b.modelo.TipoRepuesto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            System.err.println("Error al guardar repuesto: " + e.getMessage());
            return false;
        }
    }

    public boolean guardarMarca(MarcaRepuesto marca, Connection conexion) {
        String sql = "INSERT INTO public.marca_repuesto (id_marca_repuesto, nombre_marca_repuesto) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, marca.getIdMarcaRepuesto());
            ps.setString(2, marca.getNombreMarcaRepuesto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar marca: " + e.getMessage());
            return false;
        }
    }

    public boolean guardarTipo(TipoRepuesto tipo, Connection conexion) {
        String sql = "INSERT INTO public.tipo_de_repuesto (id_tip_repuesto, nom_tip_repuesto, descrip_tip_repuesto) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, tipo.getIdTipRepuesto());
            ps.setString(2, tipo.getNomTipRepuesto());
            ps.setString(3, "Registrado desde interfaz");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar tipo: " + e.getMessage());
            return false;
        }
    }
    
    public List<TipoRepuesto> obtenerTipos(Connection conexion) {
    List<TipoRepuesto> lista = new ArrayList<>();
    String sql = "SELECT id_tip_repuesto, nom_tip_repuesto FROM public.tipo_de_repuesto";
    try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(new TipoRepuesto(rs.getString("id_tip_repuesto"), rs.getString("nom_tip_repuesto")));
        }
    } catch (SQLException e) {
        System.err.println("Error al cargar tipos: " + e.getMessage());
    }
    return lista;
}

    public List<MarcaRepuesto> obtenerMarcas(Connection conexion) {
    List<MarcaRepuesto> lista = new ArrayList<>();
    String sql = "SELECT id_marca_repuesto, nombre_marca_repuesto FROM public.marca_repuesto";
    try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(new MarcaRepuesto(rs.getString("id_marca_repuesto"), rs.getString("nombre_marca_repuesto")));
        }
    } catch (SQLException e) {
        System.err.println("Error al cargar marcas: " + e.getMessage());
    }
    return lista;
}
}