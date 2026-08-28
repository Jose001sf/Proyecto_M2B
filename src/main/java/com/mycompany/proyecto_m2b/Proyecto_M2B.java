/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto_m2b;
import com.mycompany.proyecto_m2b.Vista.Login;

/**
 *
 * @author usuario
 */
public class Proyecto_M2B {

    public static void main(String[] args) {        
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("No se pudo inicializar FlatLaf");
        }
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
        System.out.println("Funciona Pibe");
        //siso
        //good
        //Jovenes trabajen
    }
}
