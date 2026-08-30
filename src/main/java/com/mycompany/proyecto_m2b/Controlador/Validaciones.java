
package com.mycompany.proyecto_m2b.Controlador;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern pat=Pattern.compile("^[A-ZÁÉÍÓÚÑ]{1,50}");
        Matcher mat=pat.matcher(nombre1);                
       return mat.matches();                            
    }
    public boolean validarNombre2 (String nombre2){                
        if (nombre2==null){
            return false;
        }
        nombre2=nombre2.trim().toUpperCase(); 
        Pattern pat=Pattern.compile("^[A-ZÁÉÍÓÚÑ]{1,50}");
        Matcher mat=pat.matcher(nombre2);                
       return mat.matches();                            
    }
    public boolean validarApellido1 (String apellido1){                
        if (apellido1 == null){
            return false;
        }
        apellido1=apellido1.trim().toUpperCase(); 
        Pattern pat=Pattern.compile("^[A-ZÁÉÍÓÚÑ]{1,50}");
        Matcher mat=pat.matcher(apellido1);                
       return mat.matches();                            
    }
    public boolean validarApellido2 (String apellido2){                
        if (apellido2 == null){
            return false;
        }
        apellido2=apellido2.trim().toUpperCase(); 
        Pattern pat=Pattern.compile("^[A-ZÁÉÍÓÚÑ]{1,50}");
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
        Pattern pat=Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,60}$");
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
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]{1,30}$");
        Matcher mat=pat.matcher(Cargo);                
       return mat.matches();                            
    }
    public boolean validarDescripcionCargo (String Descripcion){                
        if (Descripcion == null){
            return false;
        }
        Descripcion=Descripcion.trim(); 
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ0-9\\s.,-]{1,80}$");
        Matcher mat=pat.matcher(Descripcion);                
       return mat.matches();                            
    }
    public boolean validarEspecialidad (String Especialidad){                
        if (Especialidad == null){
            return false;
        }
        Especialidad=Especialidad.trim(); 
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]{1,30}$");
        Matcher mat=pat.matcher(Especialidad);                
       return mat.matches();                            
    }
    public boolean validarDescripcionEspecialidad (String Descripcion){                
        if (Descripcion == null){
            return false;
        }
        Descripcion=Descripcion.trim(); 
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ0-9\\s.,-]{1,80}$");
        Matcher mat=pat.matcher(Descripcion);                
       return mat.matches();                            
    }
    public boolean validarAcceso (String Acceso){                
        if (Acceso == null){
            return false;
        }
        Acceso=Acceso.trim(); 
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s_-]{3,10}$");
        Matcher mat=pat.matcher(Acceso);                
       return mat.matches();                            
    }
    public boolean validarDescripcionAccesos (String Descripcion){                
        if (Descripcion == null){
            return false;
        }
        Descripcion=Descripcion.trim(); 
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ0-9\\s.,-_]{1,80}$");
        Matcher mat=pat.matcher(Descripcion);                
       return mat.matches();                            
    }
    public boolean validarContrasena (String Contrasena){                
        if (Contrasena == null){
            return false;
        }
        Contrasena=Contrasena.trim(); 
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ0-9@$!%*?&._-]{8,12}$");
        Matcher mat=pat.matcher(Contrasena);                
       return mat.matches();                            
    }
    public boolean validarNombreUsuario(String Nombreusuario){
        if(Nombreusuario == null){
            return false;
        }
        Nombreusuario = Nombreusuario.trim();
        Pattern pat = Pattern.compile("[A-Za-zÁÉÍÓÚÑáéíóúñ0-9@$!%*?&._-]{3,60}$");
        Matcher mat = pat.matcher(Nombreusuario);
        return mat.matches();
    }
    public boolean validarCallePrincipal(String CallePr){
        if(CallePr == null){
            return false;
        }
        CallePr = CallePr.trim();
        Pattern pat = Pattern.compile("^[A-Za-z0-9._ #áéíóúÁÉÍÓÚñÑ-]{2,50}$");
        Matcher mat = pat.matcher(CallePr);
        return mat.matches();
    }
    public boolean validarCalleSecundaria(String CalleSe){
        if(CalleSe == null){
            return false;
        }
        CalleSe = CalleSe.trim();
        Pattern pat = Pattern.compile("^[A-Za-z0-9._ #áéíóúÁÉÍÓÚñÑ-]{2,50}$");
        Matcher mat = pat.matcher(CalleSe);
        return mat.matches();
    }
    public boolean validarNumeroCasa(String Numcasa){
        if(Numcasa == null){
            return false;
        }
        Numcasa = Numcasa.trim();
        Pattern pat = Pattern.compile("^[A-Za-z0-9._ #áéíóúÁÉÍÓÚñÑ-]{2,10}$");
        Matcher mat = pat.matcher(Numcasa);
        return mat.matches();
    }
    public boolean validarCiudad(String Ciudad){
        if(Ciudad == null){
            return false;
        }
        Ciudad = Ciudad.trim();
        Pattern pat = Pattern.compile("^[A-Za-z0-9._ #áéíóúÁÉÍÓÚñÑ-]{2,30}$");
        Matcher mat = pat.matcher(Ciudad);
        return mat.matches();
    }
    public boolean validarObservaciones(String observaciones){
        if(observaciones == null){
            return false;
        }
        observaciones = observaciones.trim();
        Pattern pat = Pattern.compile("^[A-Za-z0-9._ #áéíóúÁÉÍÓÚñÑ-]{2,100}$");
        Matcher mat = pat.matcher(observaciones);
        return mat.matches();
    }
    //VALIDACIONES TIPO DE SERVICIO
    public static boolean validarNombreTipoServicio(String cadena){
        if(cadena == null) {
            return false;
        }
        Pattern pat = Pattern.compile("^(?!.*(.)\\1{3,})[A-Za-zÁÉÍÓÚÑáéíóúñ ]{3,60}$");
        return pat.matcher(cadena).matches();
    }
    public static boolean validarDescripTipoServicio(String cadena){
        if(cadena == null){
            return false;
        }
        Pattern pat=Pattern.compile("^[A-Za-zÁÉÍÓÚÑáéíóúñ0-9 .,-]{5,90}$");
        return pat.matcher(cadena).matches();
    }
    
    //validaciones servicios
    public static boolean validarNombreServicio(String cadena){
        if(cadena == null) {
            return false;
        }
        Pattern pat = Pattern.compile("^(?!.*(.)\\1{3,})[A-Za-zÁÉÍÓÚÑáéíóúñ ]{3,60}$");
        return pat.matcher(cadena).matches();
    }
    public static boolean validarPrecio(String cadena){
        if(cadena == null) {
            return false;
        }
        cadena = cadena.trim();
        Pattern pat = Pattern.compile("^\\d{1,6}(\\.\\d{1,2})?$");
        if(!pat.matcher(cadena).matches()) return false;
        float valor = Float.parseFloat(cadena);
        return valor > 0;
    }
    public static boolean validarTiempo(String cadena){
        if(cadena == null) {
            return false;
        }
        cadena = cadena.trim();
        Pattern pat = Pattern.compile("^\\d{1,4}");
        if(!pat.matcher(cadena).matches()) return false;
        int valor = Integer.parseInt(cadena);
        return valor > 0;
    }
    // Validar que no esté vacío
    public static boolean esVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    // Placa Ecuador
    public static boolean esPlacaValida(String placa) {
        return placa != null && placa.trim().toUpperCase().matches("^[A-Z]{3}-?\\d{3,4}$");
    }

    // Solo letras y espacios (para Color)
    public static boolean esTextoSoloLetras(String texto) {
        return texto != null && texto.trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    // Solo letras y números (para Chasis y Motor)
    public static boolean esAlfanumerico(String texto) {
        return texto != null && texto.trim().matches("^[A-Za-z0-9]+$");
    }

    // Entero positivo (Cilindraje, Kilometraje, Puertas)
    public static boolean esNumeroEnteroPositivo(String texto) {
        if (texto == null || texto.trim().isEmpty()) return false;
        try {
            int val = Integer.parseInt(texto.trim());
            return val >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Año dentro de un rango coherente (1900 - Año Actual + 1)
    public static boolean esAnioValido(String textoAnio) {
        try {
            int anio = Integer.parseInt(textoAnio.trim());
            int anioActual = java.time.Year.now().getValue();
            return anio >= 1900 && anio <= (anioActual + 1);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    //validar numero de puertas
    public static boolean esNumeroPuertasValido(String texto) {
    if (texto == null || texto.trim().isEmpty()) {
        return false;
    }
    try {
        int puertas = Integer.parseInt(texto.trim());
        return puertas >= 2 && puertas <= 5; 
    } catch (NumberFormatException e) {
        return false;
    }
}
}
