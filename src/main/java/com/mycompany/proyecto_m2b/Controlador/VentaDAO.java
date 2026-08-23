
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.DetalleVenta;
import com.mycompany.proyecto_m2b.modelo.EncabezadoVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VentaDAO {

    public boolean registrarVenta(EncabezadoVenta enc, List<DetalleVenta> detalles) {
        String sqlEncab = "INSERT INTO encabezado_venta (id_encab_venta, id_empresa_rec, fecha_compra, total_encab_venta) VALUES (?, ?, ?, ?)";
        String sqlDet = "INSERT INTO detalle_venta (id_detalle_venta, cant_vendida, subtotal_residuos, id_encab_venta, id_residuos) VALUES (?, ?, ?, ?, ?)";
        
        String sqlStock = "UPDATE residuos SET cantidad_actual = cantidad_actual - ? WHERE id_residuos = ?";

        Connection con = null;
        PreparedStatement psEncab = null;
        PreparedStatement psDet = null;
        PreparedStatement psStock = null;

        try {
            con = ConexionBD.obtenerConexion();
            con.setAutoCommit(false); 

            psEncab = con.prepareStatement(sqlEncab);
            psEncab.setString(1, enc.getIdEncabVenta()); 
            psEncab.setString(2, enc.getIdEmpresaRec());
            psEncab.setDate(3, enc.getFechaCompra());
            psEncab.setDouble(4, enc.getTotalEncabVenta()); 
            psEncab.executeUpdate();

            psDet = con.prepareStatement(sqlDet);
            psStock = con.prepareStatement(sqlStock);

            for (DetalleVenta det : detalles) {
                psDet.setString(1, det.getIdDetalleVenta());
                psDet.setInt(2, det.getCantVendida()); 
                psDet.setDouble(3, det.getSubtotalResiduos()); 
                psDet.setString(4, enc.getIdEncabVenta()); 
                psDet.setString(5, det.getIdResiduos()); 
                psDet.addBatch(); 

                psStock.setInt(1, det.getCantVendida());
                psStock.setString(2, det.getIdResiduos());
                psStock.addBatch();
            }

            psDet.executeBatch(); 
            psStock.executeBatch();

            con.commit(); 
            return true; 

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); 
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace(); 
            return false;
        } finally {
            try {
                if (psEncab != null) psEncab.close(); 
                if (psDet != null) psDet.close(); 
                if (psStock != null) psStock.close();
                if (con != null) con.close(); 
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }
    }
    
    public String obtenerSiguienteNumeroFactura() {
    String sql = "SELECT MAX(id_encab_venta) FROM encabezado_venta";
    String siguiente = "FAC-000001";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next() && rs.getString(1) != null) {
            String ultimoId = rs.getString(1); 
            int numero = Integer.parseInt(ultimoId.replace("FAC-", ""));
            numero++;
            siguiente = String.format("FAC-%06d", numero);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return siguiente;
}
}