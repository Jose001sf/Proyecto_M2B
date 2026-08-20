/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto_m2b;
import com.mycompany.proyecto_m2b.Controlador.TipoServicioDAO;
import com.mycompany.proyecto_m2b.Vista.Login;
import com.mycompany.proyecto_m2b.modelo.Tipo_de_servicio;

/**
 *
 * @author usuario
 */
public class Proyecto_M2B {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Login L=new Login();
        L.setVisible(true);
        /*
        ghgvhhububyuhbuugbuy
        */
        
        
        //HOla
        Tipo_de_servicio nuevoServicio = new Tipo_de_servicio("TS001", "Mantenimiento", "Servicio de mantenimiento preventivo");

        // 3. Crear una instancia del DAO
        TipoServicioDAO servicioDAO = new TipoServicioDAO();

        // 4. Llamar al método para insertar el registro en la base de datos
        servicioDAO.insertar(nuevoServicio);
    }
}
