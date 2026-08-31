/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;
import java.sql.*;
import com.mycompany.proyecto_m2b.modelo.Accesos;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author usuario
 */
public class AccesosDAO {
    public static final String INSERTARACCESOS = "INSERT INTO accesos (id_accesos, accesos, desc_permisos, id_tip_de_usuario, estado_acti_acceso) "
            + "VALUES (?, ?, ?, ?, ?)";
    public void insertarAccesos (Accesos acceso){
        String idGenerado = Generacion_id.generar_id("ACC", "seq_accesos");
        acceso.setId_accesos(idGenerado);
        try (Connection conn = ConexionBD.obtenerConexion(); 
                PreparedStatement ps=conn.prepareStatement(INSERTARACCESOS)){
            ps.setString(1, idGenerado);
            ps.setString(2, acceso.getAccesos());
            ps.setString(3, acceso.getDesc_persmisos());
            ps.setString(4, acceso.getId_tip_usuario());
            ps.setBoolean(5, acceso.isEstado_acti_acceso());
            ps.executeUpdate();
            System.out.println("Acceso insertado correctamente");
        }
        catch(SQLException e){
            System.out.println("Error al insertar acceso: "+e.getMessage());
        }
    }
    
    public static final String LISTARACCESOS= "SELECT id_accesos, accesos, desc_permisos, id_tip_de_usuario, estado_acti_acceso"
            + " FROM accesos "
            + "WHERE estado_acti_acceso = true "
            +"ORDER BY id_tip_de_usuario";
    public List<Accesos> listarAccesos (){
        List<Accesos> lista=new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement ps=conn.prepareStatement(LISTARACCESOS)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Accesos ac=new Accesos(rs.getString("id_accesos"),
                rs.getString("accesos"),
                rs.getString("desc_permisos"),
                rs.getString("id_tip_de_usuario"),
                rs.getBoolean("estado_acti_acceso"));                
                lista.add(ac);
            }
        }
        catch (SQLException e){
            System.out.println("Error al lista accesos: "+e.getMessage());
        }
        return lista;
    }
    
    private String ACTUALIZARACCESOS = """
                                       UPDATE accesos
                                       SET accesos=?, desc_permisos=?
                                       WHERE id_accesos=?
                                       """;
    public void actualizar(Accesos acceso) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(ACTUALIZARACCESOS)) {
 
            ps.setString(1, acceso.getAccesos());
            ps.setString(2, acceso.getDesc_persmisos());
            ps.setString(3, acceso.getId_accesos());
            ps.executeUpdate();
            System.out.println("Acceso actualizado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar acceso: " + e.getMessage());
        }
    }
    
    private static final String BUSCARPORID =
            "SELECT id_accesos, accesos, desc_permisos, id_tip_de_usuario, estado_acti_acceso "
            + "FROM accesos WHERE id_accesos = ?";
 
    public Accesos buscarPorId(String idAccesos) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(BUSCARPORID)) {
 
            ps.setString(1, idAccesos);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Accesos ac=new Accesos(rs.getString("id_accesos"),
                    rs.getString("accesos"),
                    rs.getString("desc_permisos"),
                    rs.getString("id_tip_de_usuario"),
                    rs.getBoolean("estado_acti_acceso"));
                    return ac;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar acceso: " + e.getMessage());
        }
        return null;
    }
    
    private static final String LISTARPORTIPO =
            "SELECT id_accesos, accesos, desc_permisos, id_tip_de_usuario, estado_acti_acceso "
          + "FROM accesos "
          + "WHERE id_tip_de_usuario = ? AND estado_acti_acceso = true";
 
    public List<Accesos> obtenerPermisosPorTipoUsuario(String idTipoUsuario) {
        List<Accesos> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARPORTIPO)) {
 
            ps.setString(1, idTipoUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Accesos ac=new Accesos(rs.getString("id_accesos"),
                    rs.getString("accesos"),
                    rs.getString("desc_permisos"),
                    rs.getString("id_tip_de_usuario"),
                    rs.getBoolean("estado_acti_acceso"));
                    lista.add(ac);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener permisos: " + e.getMessage());
        }
        return lista;
    }
    
    private static final String ELIMINAR = "UPDATE accesos "
            + "SET estado_acti_acceso = false "
            + "WHERE id_accesos = ?"; 
    public boolean eliminar(String idAccesos) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(ELIMINAR)) {
            ps.setString(1, idAccesos);
            int filasAfectadas = ps.executeUpdate();            
            System.out.println("Acceso dado de baja correctamente");
            return filasAfectadas >0;
        } catch (SQLException e) {
            System.out.println("Error al dar de baja acceso: " + e.getMessage());
            return false;
        }
    }
    
    private static final String REACTIVAR = "UPDATE accesos "
            + "SET estado_acti_acceso = true "
            + "WHERE id_accesos = ?"; 
    public boolean reactivar(String idAccesos) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(REACTIVAR)) {
            ps.setString(1, idAccesos);
            int filasAfectadas=ps.executeUpdate();
            return filasAfectadas>0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar acceso: " + e.getMessage());
            return false;
        }
    }
    
    public boolean existeAcceso(String codigoPermiso, String idTipoUsuario) {
        String sql = "SELECT 1 FROM accesos "
                + "WHERE accesos = ? AND id_tip_de_usuario = ? AND estado_acti_acceso=true";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoPermiso);
            ps.setString(2, idTipoUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar acceso: " + e.getMessage());
            return false;
        }
    }
    
    private static final String LISTARCODIGOSUNICOS =
        "SELECT DISTINCT ON (accesos) accesos, desc_permisos "
        + "FROM accesos WHERE estado_acti_acceso = true "
        + "ORDER BY accesos";

    public List<Accesos> listarCodigosUnicos() {
        List<Accesos> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARCODIGOSUNICOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Accesos ac = new Accesos();
                ac.setAccesos(rs.getString("accesos"));
                ac.setDesc_persmisos(rs.getString("desc_permisos"));
                lista.add(ac);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar codigos unicos: " + e.getMessage());
        }
        return lista;
    }
    
    private static final String ELIMINARPORCODIGOYTIPO =
            "UPDATE accesos "
            + "SET estado_acti_acceso = false "
            + "WHERE accesos = ? AND id_tip_de_usuario = ?";
 
    public void eliminarPorCodigoYTipo(String codigoPermiso, String idTipoUsuario) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(ELIMINARPORCODIGOYTIPO)) {

            ps.setString(1, codigoPermiso);
            ps.setString(2, idTipoUsuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al dar de baja acceso: " + e.getMessage());
        }
    }
    
    public Accesos buscarAccesoEspecifico(String nombreAcceso, String idTipoUsuario) {
        String sql = "SELECT id_accesos, accesos, desc_permisos, id_tip_de_usuario, estado_acti_acceso "
                   + "FROM accesos WHERE accesos = ? AND id_tip_de_usuario = ?";
                   
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, nombreAcceso);
            ps.setString(2, idTipoUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Accesos(
                        rs.getString("id_accesos"),
                        rs.getString("accesos"),
                        rs.getString("desc_permisos"),
                        rs.getString("id_tip_de_usuario"),
                        rs.getBoolean("estado_acti_acceso")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar específico: " + e.getMessage());
        }
        return null;
    }
    
    private static final String ACTUALIZARCONESTADO =
        "UPDATE accesos SET desc_permisos = ?, estado_acti_acceso = ? "
            + "WHERE id_accesos = ?";
 
    public void actualizarConEstado(Accesos acceso) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(ACTUALIZARCONESTADO)) {

            ps.setString(1, acceso.getDesc_persmisos());
            ps.setBoolean(2, acceso.isEstado_acti_acceso());
            ps.setString(3, acceso.getId_accesos());
            ps.executeUpdate();
            System.out.println("Acceso actualizado (descripcion + estado)");
        } catch (SQLException e) {
            System.out.println("Error al actualizar acceso: " + e.getMessage());
        }
    }
    
    public void cargarAccesos(JComboBox comboBoxAccesos) {
        String sql = """
            SELECT DISTINCT ON (accesos) id_accesos, accesos, desc_permisos, id_tip_de_usuario, estado_acti_acceso 
            FROM accesos 
            ORDER BY accesos
            """;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            comboBoxAccesos.removeAllItems();
            Accesos opcionPorDefecto = new Accesos();
            opcionPorDefecto.setId_accesos("");
            opcionPorDefecto.setAccesos("Seleccione una opción"); 
            comboBoxAccesos.addItem(opcionPorDefecto);
            int i=0;
            while (rs.next()) {
                Accesos acce = new Accesos();
                acce.setId_accesos(rs.getString("id_accesos"));
                acce.setAccesos(rs.getString("accesos"));
                acce.setDesc_persmisos(rs.getString("desc_permisos"));
                acce.setId_tip_usuario(rs.getString("id_tip_de_usuario"));
                acce.setEstado_acti_acceso(rs.getBoolean("estado_acti_acceso"));
                comboBoxAccesos.addItem(acce);
                i++;
            }
            System.out.println("Hay un total de "+i+" registros");
        } catch (SQLException e) {
            System.err.println("Error al cargar los accesos: " + e.getMessage());
        }
    }
}







