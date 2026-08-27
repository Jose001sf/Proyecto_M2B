
package com.mycompany.proyecto_m2b.Controlador;

import java.sql.*;

public class CompraDAO {

    public String obtenerSiguienteIdCompra(Connection con) {
        String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(id_enca_compra FROM 5) AS INTEGER)), 0) + 1 AS sig FROM public.encabezado_compra WHERE id_enca_compra LIKE 'CMP-%'";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return String.format("CMP-%03d", rs.getInt("sig"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID de compra: " + e.getMessage());
        }
        return "CMP-001";
    }

    public boolean guardarEncabezadoCompra(String idCompra, String fecha, double total, String idProveedor, Connection con) {
        String sql = "INSERT INTO public.encabezado_compra (id_enca_compra, fecha_compra, total_encab_compra, id_proveedor) VALUES (?, CAST(? AS DATE), ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idCompra);
            ps.setString(2, fecha);
            ps.setDouble(3, total);
            ps.setString(4, idProveedor);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar encabezado de compra: " + e.getMessage());
            return false;
        }
    }

    public boolean guardarDetalleCompra(String idDetalle, int cantidad, double subtotal, String idRepuesto, String idCompra, Connection con) {
        String sql = "INSERT INTO public.detalle_compra (id_detall_compra, cantidad_enviada, subtotal_compra, id_repuestos, id_enca_compra) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idDetalle);
            ps.setInt(2, cantidad);
            ps.setDouble(3, subtotal);
            ps.setString(4, idRepuesto);
            ps.setString(5, idCompra);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar detalle de compra: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarStockRepuesto(String idRepuesto, int cantidadComprada, Connection con) {
        String sql = "UPDATE public.repuestos SET cantidad_actual_repuesto = cantidad_actual_repuesto + ? WHERE id_repuestos = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidadComprada);
            ps.setString(2, idRepuesto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar inventario: " + e.getMessage());
            return false;
        }
    }
    
    public boolean validarStockMaximo(String idRepuesto, int cantidadAComprar, Connection con) {
    String sql = "SELECT cantidad_actual_repuesto, cantidad_max_repuesto FROM public.repuestos WHERE id_repuestos = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, idRepuesto);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int actual = rs.getInt("cantidad_actual_repuesto");
                int maximo = rs.getInt("cantidad_max_repuesto");
              
                if ((actual + cantidadAComprar) > maximo) {
                    return false; 
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al validar stock máximo: " + e.getMessage());
    }
    return true;
}
    
    public boolean eliminarRepuesto(String idRepuesto, Connection conexion) {
        String sqlVerificar = "SELECT COUNT(*) FROM public.detalle_compra WHERE id_repuestos = ?";
        try (PreparedStatement psVerificar = conexion.prepareStatement(sqlVerificar)) {
            psVerificar.setString(1, idRepuesto);
            try (ResultSet rs = psVerificar.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; 
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar uso del repuesto: " + e.getMessage());
            return false;
        }

        String sqlEliminar = "DELETE FROM public.repuestos WHERE id_repuestos = ?";
        try (PreparedStatement psEliminar = conexion.prepareStatement(sqlEliminar)) {
            psEliminar.setString(1, idRepuesto);
            return psEliminar.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar repuesto: " + e.getMessage());
            return false;
        }
    }
}