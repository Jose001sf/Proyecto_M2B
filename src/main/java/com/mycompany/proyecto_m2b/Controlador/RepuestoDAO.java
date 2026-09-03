
package com.mycompany.proyecto_m2b.Controlador;

import com.mycompany.proyecto_m2b.modelo.MarcaRepuesto;
import com.mycompany.proyecto_m2b.modelo.Repuesto;
import com.mycompany.proyecto_m2b.modelo.TipoRepuesto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RepuestoDAO {

    public boolean guardarRepuesto(Repuesto repuesto, Connection conexion) {
    String sql = "INSERT INTO public.repuestos ("
               + "id_repuestos, nom_repuesto, cantidad_max_repuesto, cantidad_min_repuesto, "
               + "cantidad_actual_repuesto, precio_repuesto_unit, descrip_repuesto, "
               + "id_tip_repuesto, id_marca_repuesto) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, repuesto.getIdRepuestos());
        ps.setString(2, repuesto.getNomRepuesto());
        ps.setInt(3, repuesto.getCantidadMaxRepuesto());          
        ps.setInt(4, repuesto.getCantidadMinRepuesto());
        ps.setInt(5, repuesto.getCantidadActualRepuesto());
        ps.setFloat(6, (float) repuesto.getPrecioRepuestoUnit()); 
        ps.setString(7, repuesto.getDescripRepuesto());
        ps.setString(8, repuesto.getIdTipRepuesto());
        ps.setString(9, repuesto.getIdMarcaRepuesto());

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al guardar repuesto: " + e.getMessage());
        return false;
    }
    }

    public boolean guardarMarca(MarcaRepuesto marca, Connection conexion) {
        String sql = "INSERT INTO public.marca_repuesto (id_marca_repuesto, nombre_marca_repuesto) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, marca.getIdMarcaRepuesto());
            ps.setString(2, marca.getNombreMarcaRepuesto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar marca: " + e.getMessage());
            return false;
        }
    }

    public boolean guardarTipo(TipoRepuesto tipo, Connection conexion) {
    String sql = "INSERT INTO public.tipo_de_repuesto (id_tip_repuesto, nom_tip_repuesto, descrip_tip_repuesto) VALUES (?, ?, ?)";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, tipo.getIdTipRepuesto());
        ps.setString(2, tipo.getNomTipRepuesto());
        ps.setString(3, tipo.getDescripTipRepuesto()); 
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al guardar tipo: " + e.getMessage());
        return false;
    }
}
    
    public List<TipoRepuesto> obtenerTipos(Connection conexion) {
    List<TipoRepuesto> lista = new ArrayList<>();
    String sql = "SELECT id_tip_repuesto, nom_tip_repuesto FROM public.tipo_de_repuesto";
    try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(new TipoRepuesto(rs.getString("id_tip_repuesto"), rs.getString("nom_tip_repuesto")));
        }
    } catch (SQLException e) {
        System.err.println("Error al cargar tipos: " + e.getMessage());
    }
    return lista;
}

    public List<MarcaRepuesto> obtenerMarcas(Connection conexion) {
    List<MarcaRepuesto> lista = new ArrayList<>();
    String sql = "SELECT id_marca_repuesto, nombre_marca_repuesto FROM public.marca_repuesto";
    try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(new MarcaRepuesto(rs.getString("id_marca_repuesto"), rs.getString("nombre_marca_repuesto")));
        }
    } catch (SQLException e) {
        System.err.println("Error al cargar marcas: " + e.getMessage());
    }
    return lista;
}
    
    public String obtenerSiguienteIdRepuesto(Connection conexion) {
    String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(id_repuestos FROM 5) AS INTEGER)), 0) + 1 AS siguiente "
               + "FROM public.repuestos WHERE id_repuestos LIKE 'REP-%'";
    
    try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        if (rs.next()) {
            int siguienteNum = rs.getInt("siguiente");
            return String.format("REP-%03d", siguienteNum);
        }
    } catch (SQLException e) {
        System.err.println("Error al generar siguiente ID: " + e.getMessage());
    }
    
    return "REP-001"; 
}
    
    public List<Repuesto> obtenerRepuestos(Connection con) {
    List<Repuesto> lista = new ArrayList<>();
    String sql = "SELECT id_repuestos, nom_repuesto, precio_repuesto_unit FROM public.repuestos";
    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(new Repuesto(
                rs.getString("id_repuestos"),
                rs.getString("nom_repuesto"),
                rs.getDouble("precio_repuesto_unit")
            ));
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener repuestos: " + e.getMessage());
    }
    return lista;
}
    
    public String obtenerSiguienteIdMarca(Connection conexion) {
    String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(id_marca_repuesto FROM 5) AS INTEGER)), 0) + 1 AS siguiente "
               + "FROM public.marca_repuesto WHERE id_marca_repuesto LIKE 'MAR-%'";
    
    try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        if (rs.next()) {
            int siguienteNum = rs.getInt("siguiente");
            return String.format("MAR-%07d", siguienteNum);
        }
    } catch (SQLException e) {
        System.err.println("Error al generar siguiente ID de marca: " + e.getMessage());
    }
        return "MAR-0000001";
}
        
    public List<Repuesto> buscarRepuestosPorNombre(Connection con, String filtro) {
    List<Repuesto> lista = new ArrayList<>();
    String sql = "SELECT id_repuestos, nom_repuesto, cantidad_max_repuesto, cantidad_min_repuesto, "
               + "cantidad_actual_repuesto, precio_repuesto_unit, descrip_repuesto, "
               + "id_tip_repuesto, id_marca_repuesto "
               + "FROM public.repuestos WHERE nom_repuesto ILIKE ?";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, "%" + filtro + "%");
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Repuesto r = new Repuesto();
                r.setIdRepuestos(rs.getString("id_repuestos"));
                r.setNomRepuesto(rs.getString("nom_repuesto"));
                r.setCantidadMaxRepuesto(rs.getInt("cantidad_max_repuesto"));
                r.setCantidadMinRepuesto(rs.getInt("cantidad_min_repuesto"));
                r.setCantidadActualRepuesto(rs.getInt("cantidad_actual_repuesto"));
                r.setPrecioRepuestoUnit(rs.getDouble("precio_repuesto_unit"));
                r.setDescripRepuesto(rs.getString("descrip_repuesto"));
                r.setIdTipRepuesto(rs.getString("id_tip_repuesto"));
                r.setIdMarcaRepuesto(rs.getString("id_marca_repuesto"));
                
                lista.add(r);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar repuestos: " + e.getMessage());
    }
    return lista;
}
    
    public boolean actualizarRepuesto(Repuesto repuesto, Connection conexion) {
    String sql = "UPDATE public.repuestos SET "
               + "nom_repuesto = ?, cantidad_max_repuesto = ?, cantidad_min_repuesto = ?, "
               + "cantidad_actual_repuesto = ?, precio_repuesto_unit = ?, descrip_repuesto = ?, "
               + "id_tip_repuesto = ?, id_marca_repuesto = ? "
               + "WHERE id_repuestos = ?";

    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, repuesto.getNomRepuesto());
        ps.setInt(2, repuesto.getCantidadMaxRepuesto());          
        ps.setInt(3, repuesto.getCantidadMinRepuesto());
        ps.setInt(4, repuesto.getCantidadActualRepuesto());
        ps.setFloat(5, (float) repuesto.getPrecioRepuestoUnit()); 
        ps.setString(6, repuesto.getDescripRepuesto());
        ps.setString(7, repuesto.getIdTipRepuesto());
        ps.setString(8, repuesto.getIdMarcaRepuesto());
        ps.setString(9, repuesto.getIdRepuestos()); 

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al actualizar repuesto: " + e.getMessage());
        return false;
    }
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
    
    //sql para estadisticas
    public int contarServiciosRealizados(LocalDate desde, LocalDate hasta) {
        String sql = "SELECT COALESCE(SUM(dr.cantidad_usar), 0)"
                + "FROM detalle_repuesto dr "
                + "JOIN orden_de_servicio os ON dr.id_orden_serv = os.id_orden_serv "
                + "WHERE os.fecha_ingreso BETWEEN ? AND ?";
        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(desde));
            ps.setDate(2, java.sql.Date.valueOf(hasta));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public Map<String, Integer> topRepuestosMasUtilizados(LocalDate Desde, LocalDate Hasta, int limite) {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        String sql = "SELECT r.nom_repuesto AS nombre, SUM(d.cantidad_usar) AS cantidad "
                + "FROM detalle_repuesto d "
                + "JOIN orden_de_servicio o ON d.id_orden_serv = o.id_orden_serv "
                + "JOIN repuestos r ON d.id_repuestos = r.id_repuestos "
                + "WHERE o.fecha_ingreso BETWEEN ? AND ? "
                + "GROUP BY r.nom_repuesto "
                + "ORDER BY cantidad DESC "
                + "LIMIT ?";
        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(Desde));
            ps.setDate(2, java.sql.Date.valueOf(Hasta));
            ps.setInt(3, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.put(rs.getString("nombre"), rs.getInt("cantidad"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    //Servicios
    public List<Repuesto> obtenerRepuestosParaTabla(Connection con) {
    List<Repuesto> lista = new ArrayList<>();
    String sql = "SELECT id_repuestos, nom_repuesto, cantidad_actual_repuesto, precio_repuesto_unit FROM public.repuestos";
    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            Repuesto r = new Repuesto();
            r.setIdRepuestos(rs.getString("id_repuestos"));
            r.setNomRepuesto(rs.getString("nom_repuesto"));
            r.setCantidadActualRepuesto(rs.getInt("cantidad_actual_repuesto"));
            r.setPrecioRepuestoUnit(rs.getDouble("precio_repuesto_unit"));
            lista.add(r);
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener repuestos para la tabla: " + e.getMessage());
    }
    return lista;
}
    
    public List<Repuesto> buscarRepuestosPorFiltro(Connection con, String filtro) {
    List<Repuesto> lista = new ArrayList<>();
    String sql = "SELECT id_repuestos, nom_repuesto, cantidad_max_repuesto, cantidad_min_repuesto, "
               + "cantidad_actual_repuesto, precio_repuesto_unit, descrip_repuesto, "
               + "id_tip_repuesto, id_marca_repuesto "
               + "FROM public.repuestos "
               + "WHERE nom_repuesto ILIKE ? OR id_repuestos ILIKE ?";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        String patron = "%" + filtro + "%";
        ps.setString(1, patron);
        ps.setString(2, patron);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Repuesto r = new Repuesto();
                r.setIdRepuestos(rs.getString("id_repuestos"));
                r.setNomRepuesto(rs.getString("nom_repuesto"));
                r.setCantidadMaxRepuesto(rs.getInt("cantidad_max_repuesto"));
                r.setCantidadMinRepuesto(rs.getInt("cantidad_min_repuesto"));
                r.setCantidadActualRepuesto(rs.getInt("cantidad_actual_repuesto"));
                r.setPrecioRepuestoUnit(rs.getDouble("precio_repuesto_unit"));
                r.setDescripRepuesto(rs.getString("descrip_repuesto"));
                r.setIdTipRepuesto(rs.getString("id_tip_repuesto"));
                r.setIdMarcaRepuesto(rs.getString("id_marca_repuesto"));
                
                lista.add(r);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al filtrar repuestos: " + e.getMessage());
    }
    return lista;
}
    
    public int obtenerStockActual(Connection con, String idRepuesto) {
    String sql = "SELECT cantidad_actual_repuesto FROM public.repuestos WHERE id_repuestos = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, idRepuesto);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("cantidad_actual_repuesto");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al consultar stock actual: " + e.getMessage());
    }
    return -1; 
}
    
public boolean guardarDetallesYDescontarStock(Connection con, List<Object[]> listaRepuestos, String idOrdenServicio) {
    if (listaRepuestos == null || listaRepuestos.isEmpty()) {
        return true; 
    }

    String sqlDetalle = "INSERT INTO public.detalle_repuesto (id_detalle_repuesto, cantidad_usar, subtotal_repuesto, id_repuestos, id_orden_serv) VALUES (?, ?, ?, ?, ?)";
    String sqlStock = "UPDATE public.repuestos SET cantidad_actual_repuesto = cantidad_actual_repuesto - ? WHERE id_repuestos = ? AND cantidad_actual_repuesto >= ?";

    boolean estadoOriginalAutoCommit = true;

    try {
        estadoOriginalAutoCommit = con.getAutoCommit();
        con.setAutoCommit(false);

        try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
             PreparedStatement psStock = con.prepareStatement(sqlStock)) {

            for (Object[] fila : listaRepuestos) { 
                String idRepuesto = fila[0].toString().trim();
                int cantidadUsar = Integer.parseInt(fila[1].toString().trim());
                double subtotal = Double.parseDouble(fila[2].toString().replace(",", ".").trim());

                String idDetalle = "DR-" + java.util.UUID.randomUUID().toString().substring(0, 8);

                psDetalle.setString(1, idDetalle);
                psDetalle.setInt(2, cantidadUsar);
                psDetalle.setDouble(3, subtotal);
                psDetalle.setString(4, idRepuesto);
                psDetalle.setString(5, idOrdenServicio);
                psDetalle.addBatch();

                psStock.setInt(1, cantidadUsar);
                psStock.setString(2, idRepuesto);
                psStock.setInt(3, cantidadUsar);
                psStock.addBatch();
            }

            psDetalle.executeBatch();
            int[] resultadosStock = psStock.executeBatch();

            for (int res : resultadosStock) {
                if (res == 0) { 
                    con.rollback();
                    return false;
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            con.rollback();
            System.err.println("Error al procesar la transacción de repuestos: " + e.getMessage());
            return false;
        } finally {
            con.setAutoCommit(estadoOriginalAutoCommit);
        }

    } catch (SQLException e) {
        System.err.println("Error al gestionar el AutoCommit: " + e.getMessage());
        return false;
    }
}
    
    public boolean enviarReporteGeneralStock(Connection conexion, String correoDestino) {
    String sql = "SELECT id_repuestos, nom_repuesto, cantidad_actual_repuesto, "
               + "cantidad_min_repuesto, cantidad_max_repuesto, precio_repuesto_unit "
               + "FROM public.repuestos ORDER BY nom_repuesto ASC";

    try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            String idRepuesto = rs.getString("id_repuestos");
            String nomRepuesto = rs.getString("nom_repuesto");
            int actual = rs.getInt("cantidad_actual_repuesto");
            int min = rs.getInt("cantidad_min_repuesto");
            int max = rs.getInt("cantidad_max_repuesto");

            String estado;
            if (actual <= min) {
                estado = "BAJO STOCK";
            } else if (actual >= max) {
                estado = "EXCESO DE STOCK";
            } else {
                estado = "OK";
            }

        }
        Servidor_de_correos servidor = new Servidor_de_correos();
        servidor.enviarReporteInventarioAuto(conexion);
        return true;

    } catch (SQLException e) {
        System.err.println("Error al generar reporte de stock: " + e.getMessage());
        return false;
    }
}
}