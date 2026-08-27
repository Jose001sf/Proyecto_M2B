
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Proveedor;
import com.mycompany.proyecto_m2b.modelo.TipoProveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    public List<TipoProveedor> obtenerTiposProveedor(Connection con) {
        List<TipoProveedor> lista = new ArrayList<>();
        String sql = "SELECT id_tipo_proveedor, nom_tip_proveedor, descrip_tipo_proveedor FROM public.tipos_de_proveedores";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new TipoProveedor(
                    rs.getString("id_tipo_proveedor"),
                    rs.getString("nom_tip_proveedor"),
                    rs.getString("descrip_tipo_proveedor")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar tipos de proveedor: " + e.getMessage());
        }
        return lista;
    }

    public List<Proveedor> obtenerProveedores(Connection con) {
    List<Proveedor> lista = new ArrayList<>();
    String sql = "SELECT id_proveedor, ruc_proveedor, nom_empresa, num_telel_empresa, id_tipo_proveedor FROM public.proveedor";
    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(new Proveedor(
                rs.getString("id_proveedor"),
                rs.getString("ruc_proveedor"),
                rs.getString("nom_empresa"),
                rs.getString("num_telel_empresa"),
                rs.getString("id_tipo_proveedor")
            ));
        }
    } catch (SQLException e) {
        System.err.println("Error al cargar proveedores: " + e.getMessage());
    }
    return lista;
}

    public boolean guardarProveedor(Proveedor p, Connection con) {
        String sql = "INSERT INTO public.proveedor (id_proveedor, ruc_proveedor, nom_empresa, num_telel_empresa, id_tipo_proveedor) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getIdProveedor());
            ps.setString(2, p.getRucProveedor());
            ps.setString(3, p.getNomEmpresa());
            ps.setString(4, p.getNumTelelEmpresa());
            ps.setString(5, p.getIdTipoProveedor());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar proveedor: " + e.getMessage());
            return false;
        }
    }

    public String obtenerSiguienteIdProveedor(Connection con) {
        String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(id_proveedor FROM 5) AS INTEGER)), 0) + 1 AS sig FROM public.proveedor WHERE id_proveedor LIKE 'PRO-%'";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return String.format("PRO-%03d", rs.getInt("sig"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID de proveedor: " + e.getMessage());
        }
        return "PRO-001";
    }
   
    public boolean guardarTipoProveedor(TipoProveedor tipo, Connection con) {
    String sql = "INSERT INTO public.tipos_de_proveedores (id_tipo_proveedor, nom_tip_proveedor, descrip_tipo_proveedor) VALUES (?, ?, ?)";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, tipo.getIdTipoProveedor());
        ps.setString(2, tipo.getNomTipProveedor());
        ps.setString(3, tipo.getDescripTipoProveedor());
        ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        System.err.println("Error al guardar tipo de proveedor: " + e.getMessage());
        return false;
    }
}
    public boolean actualizarProveedor(Proveedor p, Connection con) {
    String sql = "UPDATE public.proveedor SET ruc_proveedor = ?, nom_empresa = ?, num_telel_empresa = ?, id_tipo_proveedor = ? WHERE id_proveedor = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, p.getRucProveedor());
        ps.setString(2, p.getNomEmpresa());
        ps.setString(3, p.getNumTelelEmpresa());
        ps.setString(4, p.getIdTipoProveedor());
        ps.setString(5, p.getIdProveedor());
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al actualizar proveedor: " + e.getMessage());
        return false;
    }
}
    
    public boolean actualizarTipoProveedor(TipoProveedor tipo, Connection con) {
    String sql = "UPDATE public.tipos_de_proveedores SET nom_tip_proveedor = ?, descrip_tipo_proveedor = ? WHERE id_tipo_proveedor = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, tipo.getNomTipProveedor());
        ps.setString(2, tipo.getDescripTipoProveedor());
        ps.setString(3, tipo.getIdTipoProveedor());
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al actualizar tipo de proveedor: " + e.getMessage());
        return false;
    }
}
    
    public boolean eliminarProveedor(String idProveedor, Connection conexion) {
    String sqlVerificar = "SELECT COUNT(*) FROM public.encabezado_compra WHERE id_proveedor = ?";
    try (PreparedStatement psVerificar = conexion.prepareStatement(sqlVerificar)) {
        psVerificar.setString(1, idProveedor);
        try (ResultSet rs = psVerificar.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return false; 
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al verificar uso del proveedor: " + e.getMessage());
        return false;
    }

    String sqlEliminar = "DELETE FROM public.proveedor WHERE id_proveedor = ?";
    try (PreparedStatement psEliminar = conexion.prepareStatement(sqlEliminar)) {
        psEliminar.setString(1, idProveedor);
        return psEliminar.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al eliminar proveedor: " + e.getMessage());
        return false;
    }
}
    
}