    
package com.mycompany.proyecto_m2b.Controlador;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {

    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            
            config.setJdbcUrl("jdbc:postgresql://aws-0-us-east-2.pooler.supabase.com:6543/postgres");
            config.setUsername("postgres.nysvznvdbwwihcqxpcxt");
            config.setPassword("Proyecto_123");
            config.setDriverClassName("org.postgresql.Driver");

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(10000);

            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");

            dataSource = new HikariDataSource(config);
            
            System.out.println("Conectado Chavalin");
            
        } catch (Exception e) {
            System.err.println("Error de conexion: " + e.getMessage());
        }
    }

    public static Connection obtenerConexion() throws SQLException {
        return dataSource.getConnection();
    }
    //Prueba
}