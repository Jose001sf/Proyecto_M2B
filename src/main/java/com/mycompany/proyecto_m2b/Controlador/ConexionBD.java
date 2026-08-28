
package com.mycompany.proyecto_m2b.Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    private static final String URL = "jdbc:postgresql://aws-0-us-east-2.pooler.supabase.com:5432/postgres";
    private static final String USER = "postgres.nysvznvdbwwihcqxpcxt"; 
    private static final String PASSWORD = "Proyecto_123";

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base" + e.getMessage());
        }
        return conexion;
    }
}