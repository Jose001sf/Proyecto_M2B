
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.Marca;
import com.mycompany.proyecto_m2b.modelo.Modelo;
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
        String sql = "SELECT id_mode, nom_mode, id_mar_mode, id_tipo_mode FROM modelo WHERE id_mar_mode = ? ORDER BY nom_mode ASC";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idMarca);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Modelo mod = new Modelo();
                    mod.setID_mode(rs.getString("id_mode"));
                    mod.setNom_mode(rs.getString("nom_mode"));
                    mod.setID_mar_mode(rs.getString("id_mar_mode"));
                    mod.setID_tipo_mode(rs.getString("id_tipo_mode"));
                    lista.add(mod);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar modelos: " + e.getMessage());
        }
        return lista;
    }
}