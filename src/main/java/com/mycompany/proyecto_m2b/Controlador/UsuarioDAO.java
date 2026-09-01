/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Cargo;
import com.mycompany.proyecto_m2b.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author usuario
 */
public class UsuarioDAO {
    private  static final String INSERTARUSUARIO=
            "INSERT INTO public.usuario(id_usuario, nombre_usuario, contra_usuario, estado_acti_usuario, id_empleado, id_tip_usuario)" +
            "VALUES (?, ?, crypt(?, gen_salt('bf')), ?, ?, ?)";
    
     public void insertar(Usuario usuario) {
        String IdGenerado=Generacion_id.generar_id("USU", "seq_usuario");
        if (IdGenerado==null){
            System.out.println("Error al generar ID");
            return;
        }
        usuario.setID_usuario(IdGenerado);
         System.out.println(IdGenerado);
         try (Connection conn = ConexionBD.obtenerConexion();
            PreparedStatement ps = conn.prepareStatement(INSERTARUSUARIO)) {            
            ps.setString(1, usuario.getID_usuario());
            ps.setString(2, usuario.getNombre_usuario());
            ps.setString(3, usuario.getContra_usuario());
            ps.setBoolean(4, usuario.isEstado_acti_usuario());
            ps.setString(5, usuario.getId_empleado());
            ps.setString(6, usuario.getTip_usuario());
            ps.executeUpdate();
            System.out.println("Usuario insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar Usuario: " + e.getMessage());
        }
      
    }
    private static final String LISTARUSUARIO = ""
            + "SELECT u.ID_usuario, u.Nombre_usuario, u.Contra_usuario, u.Estado_acti_usuario, u.id_empleado, u.id_tip_usuario, "
            +"p.nom1_person, p.apell1_person, p.ced_perso "
            +"FROM Usuario u "
            +"LEFT JOIN Empleado e ON u.id_empleado = e.id_empleado "
            +"LEFT JOIN Persona p ON p.ced_perso = e.ced_perso";
    
    public List<Usuario> listarUsuario() {
    List<Usuario> lista = new ArrayList<>();
     
    try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARUSUARIO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u= new Usuario(
                        rs.getString("ID_usuario"),
                        rs.getString("Nombre_usuario"),                    
                        rs.getString("Contra_usuario"),
                        rs.getBoolean("Estado_acti_usuario"),
                        rs.getString("id_empleado"),
                        rs.getString("id_tip_usuario")
                );
                String nom=rs.getString("nom1_person");
                String apell=rs.getString("apell1_person");
                String ced=rs.getString("ced_perso");
                if (nom!=null && apell!=null){
                    u.setNombre_completo(nom+" "+apell);
                }
                else if (nom!=null && apell==null){
                    u.setNombre_completo(nom);
                }
                else if (apell!=null && nom==null){
                    u.setNombre_completo(apell);
                }
                else{
                    u.setNombre_completo("No hay");
                }
                u.setCed_perso(ced);
                lista.add(u);
            }

        } catch (SQLException ex) {
            System.out.println("Error> No se puede listar: " + ex.getMessage());
        }

        return lista;
    }
    
    private static final String MODIFICARTIPOUSUARIO =
             "UPDATE usuario u "
            + "SET id_tip_usuario = tu.id_tip_de_usuario "
            + "FROM tipos_de_usuario tu "
            + "WHERE u.id_usuario = ? "
            + "AND tu.nom_tip_usuario = ?";
    public boolean modificarTipoUsuario(String idUsuario, String nombreTipoUsuario) {
        try (Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement ps=conn.prepareStatement(MODIFICARTIPOUSUARIO)) {
            ps.setString(1, idUsuario);
            ps.setString(2, nombreTipoUsuario);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar el tipo de usuario: "+e.getMessage());
            return false;
        }
    }
    
    
    private static final String MODIFICARUSUARIO =
            "UPDATE usuario "
            + "SET nombre_usuario=?, contra_usuario = CASE WHEN ? LIKE '$2a$%' THEN contra_usuario ELSE crypt(?, gen_salt('bf')) END, id_tip_usuario=? "
            + "WHERE id_usuario=?";
        public boolean modificarUsuario (Usuario usuario){
            try (Connection conn=ConexionBD.obtenerConexion();
            PreparedStatement ps=conn.prepareStatement(MODIFICARUSUARIO)){
                ps.setString(1, usuario.getNombre_usuario());
                ps.setString(2, usuario.getContra_usuario());
                ps.setString(3, usuario.getContra_usuario());
                ps.setString(4, usuario.getTip_usuario());
                ps.setString(5, usuario.getID_usuario());                                
                int filas=ps.executeUpdate();
                return filas>0;
            }catch (SQLException e){
                    System.out.println("Error al modificar usuario: "+e.getMessage());
                    return false;
                    }
        }
    
    private static final String DarDeAlta=
            "UPDATE usuario "
            +"SET estado_acti_usuario=true "
            +"WHERE id_usuario=?";
        
    public boolean DarDeAlta (String id_usuario){
        try (Connection conn=ConexionBD.obtenerConexion();
            PreparedStatement ps=conn.prepareStatement(DarDeAlta)){
                ps.setString(1, id_usuario);
                int filas=ps.executeUpdate();
                return filas>0;
            }
        catch (SQLException e){
            System.out.println("Error al cambiar estado del usuario: "+e.getMessage());
            return false;
        }
    }
    
    private static final String DarDeBaja=
            "UPDATE usuario "
            +"SET estado_acti_usuario=false "
            +"WHERE id_usuario=?";
        
    public boolean DarDeBaja (String id_usuario){
        try (Connection conn=ConexionBD.obtenerConexion();
            PreparedStatement ps=conn.prepareStatement(DarDeBaja)){
                ps.setString(1, id_usuario);
                int filas=ps.executeUpdate();
                return filas>0;
            }
        catch (SQLException e){
            System.out.println("Error al cambiar estado del usuario: "+e.getMessage());
            return false;
        }
    }
    public List<Usuario> buscarUsuario(String criterio) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE id_usuario";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, criterio);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario(
                            rs.getString("ID_usuario"),
                            rs.getString("Nombre_usuario"),                    
                            rs.getString("Contra_usuario"),
                            rs.getBoolean("Estado_acti_usuario"),
                            rs.getString("id_empleado"),
                            rs.getString("tip_usuario")  
                    );
                    lista.add(u);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar usuario: " + ex.getMessage());
        }
        return lista;
    }
    public Usuario permiterLogin (String nombre_usuario, String contrasena){
        String sql="SELECT id_usuario, nombre_usuario, contra_usuario, estado_acti_usuario, id_empleado, id_tip_usuario "
                + "FROM usuario "
                + "WHERE nombre_usuario=? AND contra_usuario = crypt(?, contra_usuario) AND estado_acti_usuario=true";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, nombre_usuario);
            ps.setString(2, contrasena);
            try(ResultSet rs=ps.executeQuery()){
                if (rs.next()){
                Usuario u = new Usuario();
                u.setID_usuario(rs.getString("id_usuario"));
                u.setNombre_usuario(rs.getString("nombre_usuario"));
                u.setContra_usuario(rs.getString("contra_usuario"));
                u.setEstado_acti_usuario(rs.getBoolean("estado_acti_usuario"));
                u.setId_empleado(rs.getString("id_empleado"));
                u.setTip_usuario(rs.getString("id_tip_usuario"));
                return u;
                }
            }
        }catch (SQLException e){
            System.out.println("Error al ingresar el usuario: "+nombre_usuario+", error: "+e.getMessage());            
        }
        return null;
    }
    public void cargarIDusuarios (JComboBox Usuarios) {

        String sql = """
            SELECT u.id_usuario, u.nombre_usuario, u.contra_usuario, u.estado_acti_usuario, u.id_empleado, u.id_tip_usuario, e.ced_perso
            FROM usuario u
            INNER JOIN empleado e ON e.id_empleado = u.id_empleado
            ORDER BY u.nombre_usuario
            """;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            Usuarios.removeAllItems();

            while (rs.next()) {
                Usuario u=new Usuario();
                u.setID_usuario(rs.getString("id_usuario"));
                u.setNombre_usuario(rs.getString("nombre_usuario"));
                u.setEstado_acti_usuario(rs.getBoolean("estado_acti_usuario")); 
                u.setTip_usuario(rs.getString("id_tip_usuario")); 
                u.setId_empleado(rs.getString("id_empleado")); 
                u.setContra_usuario(rs.getString("contra_usuario"));
                u.setCed_perso(rs.getString("ced_perso"));
                
                Usuarios.addItem(u);
            }

        } catch (SQLException e) {
            System.err.println("Error al cargar usuario: "+e.getMessage());        
        }
    }
    public String DevolverCorreoPerson (String id_empleado){
        String correo;
        String sql ="""
                    SELECT p.corr_elec_perso 
                    FROM Usuario u 
                    INNER JOIN Empleado e ON e.id_empleado=u.id_empleado 
                    INNER JOIN Persona p ON p.ced_perso=e.ced_perso
                    WHERE u.id_empleado=?
                    """;
           
            try (Connection cn = ConexionBD.obtenerConexion();
            PreparedStatement ps = cn.prepareStatement(sql)) {

           ps.setString(1, id_empleado);

           try (ResultSet rs = ps.executeQuery()) {
               if (rs.next()) {
                  correo = rs.getString("corr_elec_perso");
                  return correo;
               }
           }
       } catch (SQLException e) {
           System.out.println("Error al obtener el correo de la persona: " + e.getMessage());
       }

       return null;
       }
    
    private static final String BUSCARUSUARIOPOREMPLEADO =
            "SELECT u.id_usuario, u.nombre_usuario, u.contra_usuario, "
            + "u.estado_acti_usuario, u.id_empleado, u.id_tip_usuario, tu.nom_tip_usuario "            
            + "FROM usuario u "
            + "LEFT JOIN tipos_de_usuario tu ON tu.id_tip_de_usuario=u.id_tip_usuario "
            + "WHERE u.id_empleado = ?";

    public Usuario buscarPorEmpleado(String idEmpleado) {
        try (Connection conn = ConexionBD.obtenerConexion();
            PreparedStatement ps = conn.prepareStatement(BUSCARUSUARIOPOREMPLEADO)) {
            ps.setString(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Usuario u = new Usuario();
                
                u.setID_usuario(rs.getString("id_usuario"));
                u.setNombre_usuario(rs.getString("nombre_usuario"));
                u.setEstado_acti_usuario(rs.getBoolean("estado_acti_usuario"));
                u.setId_empleado(rs.getString("id_empleado"));
                u.setTip_usuario(rs.getString("id_tip_usuario"));
                u.setNombre_tip_usuario(rs.getString("nom_tip_usuario"));
                
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario por empleado: " + e.getMessage());
        }
        return null;
    }
    
    public boolean ValidarIngresoEmpleados (String clave){
        if (clave==null){
            System.out.println("No hay contraseña");
            return false;
        }
        String sql="""
                   SELECT id_usuario
                   FROM usuario
                   WHERE contra_usuario = crypt(?, contra_usuario) AND id_tip_usuario='TIPUS-0001' AND estado_acti_usuario=true
                   """;
        
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
           ps.setString(1, clave);
           try (ResultSet rs = ps.executeQuery()) {
                  return rs.next();               
           }
       } catch (SQLException e) {
           System.out.println("Error al obtener la contraseña de la persona: " + e.getMessage());
       }
        return false;
    }
    
    private static final String FILTRARUSUARIOS =
            "SELECT u.id_usuario, u.nombre_usuario, u.estado_acti_usuario, u.id_empleado, u.id_tip_usuario, p.ced_perso, p.nom1_person, p.nom2_person, p.apell1_person, p.apell2_person "
            + "FROM usuario u "
            + "INNER JOIN empleado e ON e.id_empleado = u.id_empleado "
            + "INNER JOIN persona p ON p.ced_perso = e.ced_perso "
            + "WHERE p.ced_perso ILIKE ? AND (p.nom1_person ILIKE ? OR p.nom2_person ILIKE ? OR p.apell1_person ILIKE ? OR p.apell2_person ILIKE ?) "
            + "AND u.nombre_usuario ILIKE ? AND u.estado_acti_usuario = ? "
            + "ORDER BY p.nom1_person, p.apell1_person";
    public List<Usuario> filtrarUsuarios(String cedula, String nombreEmpleado, String nombreUsuario, boolean estado) {
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps =conn.prepareStatement(FILTRARUSUARIOS)) {
            ps.setString(1, "%" + cedula.trim() + "%");
            ps.setString(2, "%" + nombreEmpleado.trim() + "%");
            ps.setString(3, "%" + nombreEmpleado.trim() + "%");
            ps.setString(4, "%" + nombreEmpleado.trim() + "%");
            ps.setString(5, "%" + nombreEmpleado.trim() + "%");
            ps.setString(6, "%" + nombreUsuario.trim() + "%");
            ps.setBoolean(7, estado);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setID_usuario(rs.getString("id_usuario"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                usuario.setEstado_acti_usuario(rs.getBoolean("estado_acti_usuario"));
                usuario.setId_empleado(rs.getString("id_empleado"));
                usuario.setTip_usuario(rs.getString("id_tip_usuario"));
                usuario.setCed_perso(rs.getString("ced_perso"));
                String nombreCompleto =rs.getString("nom1_person") + " "+ rs.getString("nom2_person") + " "+ rs.getString("apell1_person") + " "+ rs.getString("apell2_person");                
                usuario.setNombre_completo(nombreCompleto.trim().replaceAll("\\s+", " "));
                lista.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al filtrar usuarios: " + e.getMessage());
        }
        return lista;
    }
    //Valida que no se repita usuario
    private static final String EXISTENOMBREUSUARIO =
            "SELECT 1 "
            + "FROM usuario "
            + "WHERE nombre_usuario ILIKE ?";

    public boolean existeNombreUsuario(String nombreUsuario) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(EXISTENOMBREUSUARIO)) {
            ps.setString(1, nombreUsuario.trim());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(
                "Error al verificar nombre de usuario: "
                + e.getMessage()
            );
            return false;
        }
    }
    private static final String EXISTENOMBREUSUARIOOTRO =
            "SELECT 1 "
            + "FROM usuario "
            + "WHERE nombre_usuario ILIKE ? "
            + "AND id_usuario <> ?";

    public boolean existeNombreUsuarioEnOtro(String nombreUsuario, String idUsuario) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps=conn.prepareStatement(EXISTENOMBREUSUARIOOTRO)) {
            ps.setString(1, nombreUsuario.trim());
            ps.setString(2, idUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error al verificar nombre de usuario: "+ e.getMessage()
            );
            return false;
        }
    }
    
 }