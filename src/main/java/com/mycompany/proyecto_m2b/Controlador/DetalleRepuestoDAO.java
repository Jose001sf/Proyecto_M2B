
package com.mycompany.proyecto_m2b.Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DetalleRepuestoDAO {

    public boolean guardarDetallesRepuestos(Connection con, JTable tablaRepuestosUsados, String idOrdenServicio) {
       
        String sql = "INSERT INTO public.detalle_repuesto (id_detalle_repuesto, cantidad_usar, subtotal_repuesto, id_repuestos, id_orden_serv) VALUES (?, ?, ?, ?, ?)";
        
        DefaultTableModel modelo = (DefaultTableModel) tablaRepuestosUsados.getModel();
        int totalFilas = modelo.getRowCount();
        
        if (totalFilas == 0) {
            return true; 
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < totalFilas; i++) {
                Object valId = modelo.getValueAt(i, 0);
                if (valId == null) continue; 

                String idRepuesto = valId.toString();
                int cantidadUsar = Integer.parseInt(modelo.getValueAt(i, 2).toString());
                double subtotal = Double.parseDouble(modelo.getValueAt(i, 4).toString());

                String idDetalle = "DR-" + System.currentTimeMillis() + "-" + i;

                ps.setString(1, idDetalle);
                ps.setInt(2, cantidadUsar);
                ps.setDouble(3, subtotal);
                ps.setString(4, idRepuesto);
                ps.setString(5, idOrdenServicio);

                ps.addBatch(); 
            }
            
            ps.executeBatch();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al guardar los detalles de repuestos: " + e.getMessage());
            return false;
        }
    }
}