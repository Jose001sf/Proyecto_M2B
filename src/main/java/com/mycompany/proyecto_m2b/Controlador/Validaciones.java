/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author usuario
 */
public class Validaciones {
    public boolean ValidarCedula(String cedula) {
        if (cedula == null) {
            return false;
        }
        cedula = cedula.trim();
        Pattern pat = Pattern.compile("^\\d{10}$");
        Matcher mat = pat.matcher(cedula);
        if (!mat.matches()) {
            return false;
        }
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        int tercerDI = Character.getNumericValue(cedula.charAt(2));
        if (!((provincia >= 1 && provincia <= 24) || provincia == 30) || tercerDI >= 6) {
            return false;
        }
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            int producto = digito * coeficientes[i];
            if (producto >= 10) {
                producto -= 9;
            }
            suma += producto;
        }
        int digitoVerificador = Character.getNumericValue(cedula.charAt(9));
        int digitoVerificadorObtenido;
        if (suma % 10 == 0) {
            digitoVerificadorObtenido = 0;
        } 
        else {
            digitoVerificadorObtenido = 10 - (suma % 10);
        }
        return digitoVerificador == digitoVerificadorObtenido;
    }
    public boolean ValidarDireccion (String Direccion){
        if (Direccion==null){
            return false;
        }
        return !Direccion.trim().isEmpty();
    }
    public boolean validarNombre1 (String nombre1){                
        if (nombre1==null){
            return false;
        }
        nombre1=nombre1.trim().toUpperCase(); 
        Pattern pat=Pattern.compile("^[A-ZÁÉÍÓÚÑ]+");
        Matcher mat=pat.matcher(nombre1);                
       return mat.matches();                            
    }
    public boolean validarNombre2 (String nombre2){                
        if (nombre2==null){
            return false;
        }
        nombre2=nombre2.trim().toUpperCase(); 
        Pattern pat=Pattern.compile("^[A-ZÁÉÍÓÚÑ]+");
        Matcher mat=pat.matcher(nombre2);                
       return mat.matches();                            
    }
    public boolean validarApellido1 (String apellido1){                
        if (apellido1 == null){
            return false;
        }
        apellido1=apellido1.trim().toUpperCase(); 
        Pattern pat=Pattern.compile("^[A-ZÁÉÍÓÚÑ]+");
        Matcher mat=pat.matcher(apellido1);                
       return mat.matches();                            
    }
    public boolean validarApellido2 (String apellido2){                
        if (apellido2 == null){
            return false;
        }
        apellido2=apellido2.trim().toUpperCase(); 
        Pattern pat=Pattern.compile("^[A-ZÁÉÍÓÚÑ]+");
        Matcher mat=pat.matcher(apellido2);                
       return mat.matches();                            
    }
    public double calcularEdad (Date fecha_nacimiento){
        LocalDate nacimiento= fecha_nacimiento.toLocalDate();
        LocalDate actual = LocalDate.now();
        long dias = ChronoUnit.DAYS.between(nacimiento, actual);
        return dias/365.25;
    }
    public boolean validarCorreo (String Correo){                
        if (Correo == null){
            return false;
        }
        Correo=Correo.trim(); 
        Pattern pat=Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher mat=pat.matcher(Correo);                
       return mat.matches();                            
    }
    public boolean validarCelular (String Celular){                
        if (Celular == null){
            return false;
        }
        Celular=Celular.trim(); 
        Pattern pat=Pattern.compile("^09[0-9]{8}$");
        Matcher mat=pat.matcher(Celular);                
       return mat.matches();                            
    }
    public boolean validarTelefono (String Telefono){                
        if (Telefono == null){
            return false;
        }
        Telefono=Telefono.trim(); 
        Pattern pat=Pattern.compile("^0[2-7][0-9]{7}$");
        Matcher mat=pat.matcher(Telefono);                
       return mat.matches();                            
    }
    public boolean FechaNacimiento (Date fechaNacimiento){
        if (fechaNacimiento== null){
            return false;
        }
        LocalDate FechaNA;
        if (fechaNacimiento instanceof java.sql.Date){
            FechaNA = ((java.sql.Date) fechaNacimiento).toLocalDate();
        }
        else {
            FechaNA = new java.sql.Date(fechaNacimiento.getTime()).toLocalDate();
        }
        LocalDate hoy=LocalDate.now();
        if(FechaNA.isAfter(hoy)){
            return false;
        }
        LocalDate minimo=hoy.minusYears(120);
        if (FechaNA.isBefore(minimo)){
            return false;
        }
        return true;
    }
    public boolean validarCargo (String Cargo){                
        if (Cargo == null){
            return false;
        }
        Cargo=Cargo.trim(); 
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ]{1,30}$");
        Matcher mat=pat.matcher(Cargo);                
       return mat.matches();                            
    }
    public boolean validarDescripcionCargo (String Descripcion){                
        if (Descripcion == null){
            return false;
        }
        Descripcion=Descripcion.trim(); 
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{1,80}$");
        Matcher mat=pat.matcher(Descripcion);                
       return mat.matches();                            
    }
    public boolean validarEspecialidad (String Especialidad){                
        if (Especialidad == null){
            return false;
        }
        Especialidad=Especialidad.trim(); 
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ]{1,30}$");
        Matcher mat=pat.matcher(Especialidad);                
       return mat.matches();                            
    }
    public boolean validarDescripcionEspecialidad (String Descripcion){                
        if (Descripcion == null){
            return false;
        }
        Descripcion=Descripcion.trim(); 
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{1,80}$");
        Matcher mat=pat.matcher(Descripcion);                
       return mat.matches();                            
    }
}
