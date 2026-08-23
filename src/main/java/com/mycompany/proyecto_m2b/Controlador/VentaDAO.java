
package com.mycompany.proyecto_m2b.Controlador;


import com.mycompany.proyecto_m2b.modelo.DetalleVenta;
import com.mycompany.proyecto_m2b.modelo.EncabezadoVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class VentaDAO {

    public boolean registrarVenta(EncabezadoVenta enc, List<DetalleVenta> detalles) {
        String sqlEncab = "INSERT INTO encabezado_venta (id_encab_venta, id_empresa_rec, fecha_compra, total_encab_venta) VALUES (?, ?, ?, ?)";
        String sqlDet = "INSERT INTO detalle_venta (id_detalle_venta, cant_vendida, subtotal_residuos, id_encab_venta, id_residuos) VALUES (?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement psEncab = null;
        PreparedStatement psDet = null;

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
            for (DetalleVenta det : detalles) {
                psDet.setString(1, det.getIdDetalleVenta());
                psDet.setInt(2, det.getCantVendida());
                psDet.setDouble(3, det.getSubtotalResiduos());
                psDet.setString(4, enc.getIdEncabVenta());
                psDet.setString(5, det.getIdResiduos());
                psDet.addBatch();
            }
            psDet.executeBatch();

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
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}