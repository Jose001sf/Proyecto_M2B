
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Residuos;
import com.mycompany.proyecto_m2b.modelo.Tipo_residuo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class ResiduoDAO {

    public String generarSiguienteID(String tabla, String columnaId, String prefijo) {
        String sql = "SELECT " + columnaId + " FROM " + tabla + " ORDER BY " + columnaId + " DESC LIMIT 1";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String ultimoId = rs.getString(columnaId);
                int num = Integer.parseInt(ultimoId.replace(prefijo, "")) + 1;
                return String.format("%s%03d", prefijo, num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prefijo + "001";
    }

    public boolean guardarTipoResiduo(Tipo_residuo tipo) {
        String sql = "INSERT INTO tipo_residuo (id_tipo_resi, nom_tipo_resi, grado_riesgo, desc_tipo_resi) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipo.getID_tipo_resi());
            ps.setString(2, tipo.getNom_tipo_resi());
            ps.setString(3, tipo.getGrado_riesgo());
            ps.setString(4, tipo.getDesc_tipo_resi());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Tipo_residuo> obtenerTiposResiduo() {
        List<Tipo_residuo> lista = new ArrayList<>();
        String sql = "SELECT id_tipo_resi, nom_tipo_resi, grado_riesgo, desc_tipo_resi FROM tipo_residuo ORDER BY nom_tipo_resi ASC";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tipo_residuo tipo = new Tipo_residuo(
                    rs.getString("id_tipo_resi"),
                    rs.getString("nom_tipo_resi"),
                    rs.getString("grado_riesgo"),
                    rs.getString("desc_tipo_resi")
                );
                lista.add(tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<String> obtenerNombresTiposResiduo() {
    List<String> lista = new ArrayList<>();
    String sql = "SELECT nom_tipo_resi FROM tipo_residuo ORDER BY nom_tipo_resi ASC";
    
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            lista.add(rs.getString("nom_tipo_resi"));
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener tipos de residuos: " + e.getMessage());
    }
    return lista;
}
    
    public String generarIdResiduo() {
    String sql = "SELECT id_residuos FROM residuos ORDER BY id_residuos DESC LIMIT 1";
    String nuevoId = "RES-0001";
    
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        if (rs.next()) {
            String ultimoId = rs.getString("id_residuos"); // Ej: "RES-0005"
            int numero = Integer.parseInt(ultimoId.substring(4)) + 1;
            nuevoId = String.format("RES-%04d", numero);
        }
    } catch (SQLException e) {
        System.err.println("Error al generar ID: " + e.getMessage());
    }
    return nuevoId;
}
    
    public String obtenerIdTipoResiduosPorNombre(String nomTipoResi) {
    String idTipo = "";
    String sql = "SELECT id_tipo_resi FROM tipo_residuo WHERE nom_tipo_resi = ?";
    
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, nomTipoResi);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idTipo = rs.getString("id_tipo_resi");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ID del tipo: " + e.getMessage());
    }
    return idTipo;
}
    
    public boolean registrarResiduo(Residuos r) {
        String sql = "INSERT INTO residuos (id_residuos, nom_residuo, estado_residuo, id_tipo_resi, cantidad_actual, cantidad_max) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getID_resiudos());
            ps.setString(2, r.getNom_residuo());
            ps.setString(3, r.getEstado_residuo());
            ps.setString(4, r.getID_tipo_resi());
            ps.setInt(5, r.getCantidad_actual());
            ps.setInt(6, r.getCantidad_max());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar residuo: " + e.getMessage());
            return false;
        }
    }
    
    public List<Residuos> listarResiduos() {
        List<Residuos> lista = new ArrayList<>();
        String sql = "SELECT id_residuos, nom_residuo FROM residuos ORDER BY nom_residuo ASC";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Residuos r = new Residuos();
                r.setID_resiudos(rs.getString("id_residuos"));
                r.setNom_residuo(rs.getString("nom_residuo"));
                lista.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar residuos: " + e.getMessage());
        }
        return lista;
    }
    
    public double obtenerStockActual(String idResiduo) {
    String sql = "SELECT cantidad_actual FROM residuos WHERE id_residuos = ?";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, idResiduo);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("cantidad_actual");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al consultar el stock: " + e.getMessage());
    }
    return 0.0;
}
    
    public List<Residuos> listarResiduosDetallados(String filtro) {
        List<Residuos> lista = new ArrayList<>();
        String sql = "SELECT id_residuos, nom_residuo, estado_residuo, id_tipo_resi, cantidad_actual, cantidad_max " +
                     "FROM residuos WHERE id_residuos ILIKE ? OR nom_residuo ILIKE ? ORDER BY nom_residuo ASC";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "%" + filtro + "%");
            ps.setString(2, "%" + filtro + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Residuos r = new Residuos(
                        rs.getString("id_residuos"),
                        rs.getString("nom_residuo"),
                        rs.getString("estado_residuo"),
                        rs.getString("id_tipo_resi"),
                        rs.getInt("cantidad_actual"),
                        rs.getInt("cantidad_max")
                    );
                    lista.add(r);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar residuos detallados: " + e.getMessage());
        }
        return lista;
    }

    public boolean estaResiduoEnVenta(String idResiduo) {
        String sql = "SELECT COUNT(*) FROM detalle_venta WHERE id_residuos = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idResiduo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar uso del residuo en ventas: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarResiduo(String idResiduo) {
        String sql = "DELETE FROM residuos WHERE id_residuos = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idResiduo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar residuo: " + e.getMessage());
            return false;
        }
    }
    
    public void cargarResiduos (JComboBox ComboResiduos) {

        String sql = """
            SELECT id_residuos, nom_residuo, estado_residuo, cantidad_max
            FROM residuos                      
            ORDER BY nom_residuo
            """;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ComboResiduos.removeAllItems();
            int i=0;
            while (rs.next()) {
                Residuos r=new Residuos();
                r.setID_resiudos(rs.getString("id_residuos"));
                r.setNom_residuo(rs.getString("nom_residuo"));
                r.setEstado_residuo(rs.getString("estado_residuo"));
                r.setCantidad_max(rs.getInt("cantidad_max"));
                
                ComboResiduos.addItem(r);
                i++;
            }
            System.out.println("Hay "+i+" registros de residuos");
        } catch (SQLException e) {
            System.err.println("Error al cargar residuos: "+e.getMessage());        
        }
    }
}