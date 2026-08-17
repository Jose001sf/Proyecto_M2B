/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;
import java.security.SecureRandom;
/**
 *
 * @author usuario
 */
public class CreacionCredenciales {
    /*
    Se crea un metodo que va a contener los caracteres de la contraseña
    */
    private static final String Caracteres= "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "0123456789";
    /*
    Se crea un metodo que va a generar numeros aleatorias mas seguros que un random, mediante los indices de CARACTERES
    */
    private static final SecureRandom  ra = new SecureRandom();
    /*
    Generar usuarios automaticamente
    */
    public static String GenerarUsuario (String Nombre1, String Apellido1){
        char inicialNombre = Character.toUpperCase(Nombre1.charAt(0));
        char inicialApellido = Character.toUpperCase(Apellido1.charAt(0));
        int num=ra.nextInt(9000)+1000;
        return "" + inicialNombre+inicialApellido+num;
    }
    /*
    Generar contraseñas automaticamente
    */
    public static String GenerarContraseña (){
        StringBuilder contrasena = new StringBuilder();
        for (int i=0; i<12; i++){
            int indice = ra.nextInt(Caracteres.length());
            contrasena.append(Caracteres.charAt(indice));
        }
        return contrasena.toString();
    }
}
