
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Marca;
import com.mycompany.proyecto_m2b.modelo.Modelo;
import com.mycompany.proyecto_m2b.modelo.Propietario;
import com.mycompany.proyecto_m2b.modelo.Tipo;
import com.mycompany.proyecto_m2b.modelo.Usuario;
import com.mycompany.proyecto_m2b.modelo.Vehiculos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculosDAO {

    private static final String INSERTARVEHICULO =
            "INSERT INTO vehiculo (id_vehi, anio_sal_vehi, num_chasis_vehi, color_vehi, cilindraje_vehi, transmision_vehi, num_puertas_vehi, kilometraje_vehi, num_motor_vehi, placa_carro, id_propietario, id_mode) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

    public void insertarVehiculos(Vehiculos vehiculo) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(INSERTARVEHICULO)) {

            ps.setString(1, vehiculo.getID_vehi());
            ps.setDate(2, new java.sql.Date(vehiculo.getAnio_sal_vehi().getTime()));
            ps.setString(3, vehiculo.getNum_chasis_vehi());
            ps.setString(4, vehiculo.getColor_vehi());
            ps.setString(5, vehiculo.getCilindraje_vehi());
            ps.setString(6, vehiculo.getTransmision_vehi());     
            ps.setInt(7, vehiculo.getNum_puertas_vehi());
            ps.setInt(8, vehiculo.getKilometraje_vehi());
            ps.setString(9, vehiculo.getNum_motor_vehi());
            ps.setString(10, vehiculo.getPlaca_carro());
            ps.setString(11, vehiculo.getID_propietario_vehi());
            ps.setString(12, vehiculo.getID_mode_vehi());
            
            ps.executeUpdate();
            System.out.println("Datos guardados correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar vehículo: " + e.getMessage());
        }
    }
    private static final String LISTARVEHICULO = 
            "SELECT * FROM vehiculo ORDER BY id_vehi ASC";

    public List<Vehiculos> listarVehiculo() {
        List<Vehiculos> lista = new ArrayList<>();
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(LISTARVEHICULO);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehiculos v = new Vehiculos(
                        rs.getString("id_vehi"),
                        rs.getDate("anio_sal_vehi"),                    
                        rs.getString("num_chasis_vehi"),
                        rs.getString("color_vehi"),
                        rs.getString("cilindraje_vehi"),
                        rs.getString("transmision_vehi"),
                        rs.getInt("num_puertas_vehi"),
                        rs.getInt("kilometraje_vehi"),
                        rs.getString("num_motor_vehi"),
                        rs.getString("placa_carro"),
                        rs.getString("id_propietario"),
                        rs.getString("id_mode")
                );
                lista.add(v);
            }

        } catch (SQLException ex) {
            System.out.println("Error al listar vehículos: " + ex.getMessage());
        }

        return lista;
    }

    public boolean eliminarVehiculo(String id_vehi) {
        String sql = "DELETE FROM vehiculo WHERE id_vehi=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id_vehi);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            System.out.println("Error al eliminar vehículo: " + e.getMessage());
            return false;
        }
    }
    private static final String MODIFICARVEHICULO =
            "UPDATE vehiculo " +
            "SET anio_sal_vehi=?, num_chasis_vehi=?, color_vehi=?, cilindraje_vehi=?, transmision_vehi=?, num_puertas_vehi=?, kilometraje_vehi=?, num_motor_vehi=?, placa_carro=?, id_propietario=?, id_mode=? " +
            "WHERE id_vehi=?";
    public boolean modificarVehiculo(Vehiculos vehiculo) {
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(MODIFICARVEHICULO)) {
            
            ps.setDate(1, new java.sql.Date(vehiculo.getAnio_sal_vehi().getTime()));
            ps.setString(2, vehiculo.getNum_chasis_vehi());
            ps.setString(3, vehiculo.getColor_vehi());
            ps.setString(4, vehiculo.getCilindraje_vehi());
            ps.setString(5, vehiculo.getTransmision_vehi());     
            ps.setInt(6, vehiculo.getNum_puertas_vehi());
            ps.setInt(7, vehiculo.getKilometraje_vehi());
            ps.setString(8, vehiculo.getNum_motor_vehi());
            ps.setString(9, vehiculo.getPlaca_carro());
            ps.setString(10, vehiculo.getID_propietario_vehi());
            ps.setString(11, vehiculo.getID_mode_vehi());
            ps.setString(12, vehiculo.getID_vehi()); 

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar vehículo: " + e.getMessage());
            return false;
        }
    }

    public List<Vehiculos> buscarVehiculo(String criterio) {
        List<Vehiculos> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo WHERE id_vehi ILIKE ? OR placa_carro ILIKE ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + criterio + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vehiculos v = new Vehiculos(
                            rs.getString("id_vehi"),
                            rs.getDate("anio_sal_vehi"),                    
                            rs.getString("num_chasis_vehi"),
                            rs.getString("color_vehi"),
                            rs.getString("cilindraje_vehi"),
                            rs.getString("transmision_vehi"),
                            rs.getInt("num_puertas_vehi"),
                            rs.getInt("kilometraje_vehi"),
                            rs.getString("num_motor_vehi"),
                            rs.getString("placa_carro"),
                            rs.getString("id_propietario"),
                            rs.getString("id_mode")  
                    );
                    lista.add(v);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar vehículo: " + ex.getMessage());
        }
        return lista;
    }
    public boolean insertarMarca(Marca m) {
    String sql = "INSERT INTO marca (id_mar, nom_mar, pais_origen_mar, empresa_mar) VALUES (?, ?, ?, ?)";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, m.getID_mar());
        ps.setString(2, m.getNom_mar());
        ps.setString(3, m.getPais_origen_mar());
        ps.setString(4, m.getEmpresa_mar());
        
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al insertar marca: " + e.getMessage());
        return false;
    }
}

    public boolean insertarModelo(Modelo mod) {
    String sql = "INSERT INTO modelo (id_mode, nom_mode, id_mar, id_tipo) VALUES (?, ?, ?, ?)";
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, mod.getID_mode());
        ps.setString(2, mod.getNom_mode());
        ps.setString(3, mod.getID_mar_mode());
        ps.setString(4, mod.getID_tipo_mode());
        
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al insertar modelo: " + e.getMessage());
        return false;
    }
}
    // Obtener Marcas
    public List<Marca> listarMarcas() {
        List<Marca> lista = new ArrayList<>();
        String sql = "SELECT id_mar, nom_mar, pais_origen_mar, empresa_mar FROM marca ORDER BY nom_mar ASC";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Marca m = new Marca();
                m.setID_mar(rs.getString("id_mar"));
                m.setNom_mar(rs.getString("nom_mar"));
                m.setPais_origen_mar(rs.getString("pais_origen_mar"));
                m.setEmpresa_mar(rs.getString("empresa_mar"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar marcas: " + e.getMessage());
        }
        return lista;
    }

    // Obtener Modelos filtrados según la Marca seleccionada
    public List<Modelo> listarModelosPorMarca(String idMarca) {
        List<Modelo> lista = new ArrayList<>();
        String sql = "SELECT id_mode, nom_mode, id_mar, id_tipo FROM modelo WHERE id_mar = ? ORDER BY nom_mode ASC";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idMarca);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Modelo mod = new Modelo();
                    mod.setID_mode(rs.getString("id_mode"));
                    mod.setNom_mode(rs.getString("nom_mode"));
                    mod.setID_mar_mode(rs.getString("id_mar"));
                    mod.setID_tipo_mode(rs.getString("id_tipo"));
                    lista.add(mod);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar modelos: " + e.getMessage());
        }
        return lista;
    }
    public List<String> obtenerNombresTipos() {
    List<String> listaTipos = new ArrayList<>();
    String sql = "SELECT nom_tipo FROM tipo ORDER BY nom_tipo ASC";
    
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            listaTipos.add(rs.getString("nom_tipo"));
        }
    } catch (SQLException e) {
        System.err.println("Error al cargar tipos: " + e.getMessage());
    }
    return listaTipos;
}
    public List<Tipo> listarTipos() {
    List<Tipo> lista = new ArrayList<>();
    String sql = "SELECT id_tipo, nom_tipo FROM tipo ORDER BY nom_tipo ASC";
    
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            Tipo tipo = new Tipo();
            tipo.setID_tipo(rs.getString("id_tipo"));
            tipo.setNom_tipo(rs.getString("nom_tipo"));
            lista.add(tipo);
        }
    } catch (SQLException e) {
        System.err.println("Error al listar tipos de vehículo: " + e.getMessage());
    }
    return lista;
}
    public List<Propietario> listarPropietarios() {
    List<Propietario> lista = new ArrayList<>();
    String sql = "SELECT pr.id_propietario, pe.nom1_person, pe.apell1_person " +
                 "FROM propietario pr " +
                 "INNER JOIN persona pe ON pr.ced_perso = pe.ced_perso " +
                 "ORDER BY pe.apell1_person ASC";
    
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            Propietario P = new Propietario();
            P.setID_propietario(rs.getString("id_propietario"));
            String nombre = rs.getString("nom1_person") + " " + rs.getString("apell1_person");
            P.setNombreCompleto(nombre);
            lista.add(P);
        }
    } catch (SQLException e) {
        System.err.println("Error al listar propietarios: " + e.getMessage());
    }
    return lista;
}
    public String obtenerIdTipoPorNombre(String nombreTipo) {
    String sql = "SELECT id_tipo FROM tipo WHERE UPPER(nom_tipo) = UPPER(?)";
    
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        
        ps.setString(1, nombreTipo.trim());
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("id_tipo");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar ID del tipo: " + e.getMessage());
    }
    return null;
}
    public String obtenerIdMarcaPorNombre(String nombreMarca) {
    String id = "";
    String sql = "SELECT ID_mar FROM marca WHERE Nom_mar = ?";
    
    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, nombreMarca);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            id = rs.getString("ID_mar");
        }
    } catch (SQLException e) {
        System.out.println("Error al buscar ID de la marca: " + e.getMessage());
    }
    return id;
}
    public String obtenerSiguienteIdMarca() {
    String nuevoId = "MAR-001";
    String sql = "SELECT COUNT(*) FROM marca"; 

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            int siguienteNumero = rs.getInt(1) + 1;
            nuevoId = String.format("MAR-%03d", siguienteNumero);
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ID de Marca: " + e.getMessage());
    }
    return nuevoId;
}

public String obtenerSiguienteIdModelo() {
    String nuevoId = "MOD-001";
    String sql = "SELECT COUNT(*) FROM modelo"; 

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            int siguienteNumero = rs.getInt(1) + 1;
            nuevoId = String.format("MOD-%03d", siguienteNumero);
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ID de Modelo: " + e.getMessage());
    }
    return nuevoId;
}
public String obtenerSiguienteIdTipo() {
    String sql = "SELECT id_tipo FROM tipo ORDER BY id_tipo DESC LIMIT 1";
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            String ultimoId = rs.getString("id_tipo"); 
            int num = Integer.parseInt(ultimoId.split("-")[1]);
            return String.format("TIP-%03d", num + 1);
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ID Tipo: " + e.getMessage());
    }
    return "TIP-001";
}

public boolean insertarTipo(String idTipo, String nomTipo, String descTipo) {
    String sql = "INSERT INTO tipo (id_tipo, nom_tipo, desc_tipo) VALUES (?, ?, ?)";
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, idTipo);
        ps.setString(2, nomTipo);
        ps.setString(3, descTipo);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al insertar tipo: " + e.getMessage());
        return false;
    }
}
public boolean insertarModelo(String idMarca, String nombreModelo, String idTipo) {
    String nuevoIdModelo = generarNuevoIdModelo();
    
    String sql = "INSERT INTO modelo (id_mode, nom_mode, id_mar, id_tipo) VALUES (?, ?, ?, ?)";
    
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        
        ps.setString(1, nuevoIdModelo);
        ps.setString(2, nombreModelo.trim().toUpperCase());
        ps.setString(3, idMarca);
        ps.setString(4, idTipo);
              
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;
        
    } catch (SQLException e) {
        System.err.println("Error al insertar el modelo: " + e.getMessage());
        return false;
    }
}
public boolean insertarPropietario(String idPropietario, String nomPropietario, String descPropietario) {
    String sql = "INSERT INTO propietario(id_propietario, observaci_propietario, ced_perso) VALUES (?, ?, ?)";
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, idPropietario);
        ps.setString(2, nomPropietario);
        ps.setString(3, descPropietario);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al insertar tipo: " + e.getMessage());
        return false;
    }
}
public String generarNuevoIdModelo() {
    String sql = "SELECT id_mode FROM modelo ORDER BY id_mode DESC LIMIT 1";
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        if (rs.next()) {
            String ultimoId = rs.getString("id_mode"); 
            int numero = Integer.parseInt(ultimoId.replaceAll("[^0-9]", ""));
            return String.format("MOD-%03d", numero + 1);
        }
    } catch (Exception e) {
        System.err.println("Error al generar ID de modelo: " + e.getMessage());
    }
    return "MOD-001"; 
}
public String generarSiguienteIdVehiculo() {
    String sql = "SELECT id_vehi FROM vehiculo ORDER BY id_vehi DESC LIMIT 1";
    String nuevoId = "VEH-001"; // Valor por defecto si la tabla está vacía

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            String ultimoId = rs.getString("id_vehi"); // Ej: "VEH-005"
            if (ultimoId != null && ultimoId.startsWith("VEH-")) {
                // Extrae el número tras el guion ("005" -> 5)
                int numero = Integer.parseInt(ultimoId.substring(4));
                numero++; // Incrementa a 6
                // Formatea de nuevo con ceros a la izquierda (Ej: "VEH-006")
                nuevoId = String.format("VEH-%03d", numero);
            }
        }
    } catch (SQLException | NumberFormatException e) {
        System.err.println("Error al generar ID de vehículo: " + e.getMessage());
    }
    return nuevoId;
}
public String obtenerIdPropietarioPorNombre(String nombreCompleto) {
    String sql = "SELECT pr.id_propietario " +
                 "FROM propietario pr " +
                 "INNER JOIN persona pe ON pr.ced_perso = pe.ced_perso " +
                 "WHERE UPPER(CONCAT(pe.nom1_person, ' ', pe.apell1_person)) = UPPER(?) " +
                 "   OR UPPER(CONCAT(pe.nom1_person, ' ', pe.nom2_person, ' ', pe.apell1_person, ' ', pe.apell2_person)) = UPPER(?)";
    
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        
        String nombreLimpio = nombreCompleto.trim().replaceAll("\\s+", " ");
        ps.setString(1, nombreLimpio);
        ps.setString(2, nombreLimpio);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("id_propietario");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ID del propietario: " + e.getMessage());
    }
    return null;
}
public String obtenerIdModeloPorNombre(String nombreModelo) {
    String sql = "SELECT id_mode FROM modelo WHERE UPPER(nom_mode) = UPPER(?)";
    
    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, nombreModelo.trim());
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("id_mode");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener ID del modelo: " + e.getMessage());
    }
    return null;
}
public Vehiculos buscarPorPlaca(String placa) {
    Vehiculos v = null;
    String sql = "SELECT * FROM vehiculo WHERE \"placa_carro\" = ?"; // Ajustado a tu nombre exacto de columna

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, placa);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            v = new Vehiculos();
            v.setID_vehi(rs.getString("id_vehi"));
            v.setAnio_sal_vehi(rs.getDate("anio_sal_vehi"));
            v.setNum_chasis_vehi(rs.getString("num_chasis_vehi"));
            v.setColor_vehi(rs.getString("Color_vehi"));
            v.setCilindraje_vehi(rs.getString("cilindraje_vehi"));
            v.setTransmision_vehi(rs.getString("transmision_vehi"));
            v.setNum_puertas_vehi(rs.getInt("num_puertas_vehi"));
            v.setKilometraje_vehi(rs.getInt("kilometraje_vehi"));
            v.setNum_motor_vehi(rs.getString("num_motor_vehi"));
            v.setID_propietario_vehi(rs.getString("id_propietario"));
            v.setID_mode_vehi(rs.getString("id_mode"));
            v.setPlaca_carro(rs.getString("placa_carro"));
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar por placa: " + e.getMessage());
    }
    return v;
}
public String obtenerNombrePropietario(String idPropietario) {
    String nombre = null;
    // Hacemos JOIN entre propietarios y personas usando la llave foránea
    String sql = "SELECT p.\"nom1_person\", p.\"apell1_person\" " +
                 "FROM propietario pr " +
                 "JOIN persona p ON pr.\"ced_perso\" = p.\"ced_perso\" " +
                 "WHERE pr.\"id_propietario\" = ?";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, idPropietario);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            nombre = rs.getString("nom1_person") + " " + rs.getString("apell1_person");
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener nombre de propietario: " + e.getMessage());
    }
    return nombre;
}

public String obtenerNombreModelo(String idModelo) {
    String nombre = null;
    String sql = "SELECT \"nom_mode\" FROM modelo WHERE \"id_mode\" = ?";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, idModelo);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            nombre = rs.getString("nom_mode");
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener nombre de modelo: " + e.getMessage());
    }
    return nombre;
}
public String obtenerNombreMarcaPorModelo(String idModelo) {
    String marca = null;
    String sql = "SELECT m.\"nom_mar\" " +
                 "FROM modelo mo " +
                 "JOIN marca m ON mo.\"id_mar\" = m.\"id_mar\" " +
                 "WHERE mo.\"id_mode\" = ?";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, idModelo);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            marca = rs.getString("nom_mar");
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener marca del modelo: " + e.getMessage());
    }
    return marca;
}
public boolean actualizarVehiculo(Vehiculos v) {
    String sql = "UPDATE vehiculo SET " +
                 "\"anio_sal_vehi\" = ?, " +
                 "\"num_chasis_vehi\" = ?, " +
                 "\"color_vehi\" = ?, " +
                 "\"cilindraje_vehi\" = ?, " +
                 "\"transmision_vehi\" = ?, " +
                 "\"num_puertas_vehi\" = ?, " +
                 "\"kilometraje_vehi\" = ?, " +
                 "\"num_motor_vehi\" = ?, " +
                 "\"id_propietario\" = ?, " +
                 "\"id_mode\" = ? " +
                 "WHERE \"placa_carro\" = ?";

    try (Connection con = ConexionBD.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDate(1, (Date) v.getAnio_sal_vehi());
        ps.setString(2, v.getNum_chasis_vehi());
        ps.setString(3, v.getColor_vehi());
        ps.setString(4, v.getCilindraje_vehi());
        ps.setString(5, v.getTransmision_vehi());
        ps.setInt(6, v.getNum_puertas_vehi());
        ps.setInt(7, v.getKilometraje_vehi());
        ps.setString(8, v.getNum_motor_vehi());
        ps.setString(9, v.getID_propietario_vehi());
        ps.setString(10, v.getID_mode_vehi());
        ps.setString(11, v.getPlaca_carro()); 

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("Error al actualizar vehículo: " + e.getMessage());
        return false;
    }
}
}