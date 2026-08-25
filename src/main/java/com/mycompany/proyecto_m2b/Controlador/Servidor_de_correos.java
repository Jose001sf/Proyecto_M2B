/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.*;
/**
 *
 * @author usuario
 */
public class Servidor_de_correos {
    public void enviarCorreoUsuario (String nom_usuario, String contra_usuario, String correo_person){
        try{
            SimpleEmail email=new SimpleEmail();
            email.setHostName("localhost");

            email.setSmtpPort(587);
            email.setAuthentication("admin@mail.mjtalleres.com", "moohoo");

            email.setStartTLSEnabled(true);
            email.setSSLCheckServerIdentity(true);

            //Cabeceras para el email

            email.setFrom("admin@mail.mjtalleres.com", "Sistema M&JTALLERES");
            email.setSubject("Bienvenido a M&JTALLERES"+"- CREDENCIALES DE ACCESO");
            
            String mensaje= "Estimado/a"+"\n"
                    + "Estas son su credenciales de usuario\n"
                    + "USUARIO: "+nom_usuario+"\n"
                    + "CONTRASEÑA: "+contra_usuario+"\n"
                    + "Administracion M&JTALLERES";
            email.setMsg(mensaje);
            email.addTo(correo_person);
            email.send();
            System.out.println("Correo enviado exitosamente");
        }catch(EmailException e){
            System.out.println("Error al enviar el correo: "+e.getMessage());
            e.printStackTrace();
        }
        
    }
}
