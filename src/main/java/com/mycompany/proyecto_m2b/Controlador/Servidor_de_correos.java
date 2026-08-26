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
    private static final String GMAIL_REMITENTE = "talleresmj.latoneriamecanica@gmail.com";
    private static final String GMAIL_APP_PASSWORD = "nzvjhsphojounneb"; 
    public void enviarCorreoUsuario (String nom_usuario, String contra_usuario, String correo_person){
        try{
            SimpleEmail email=new SimpleEmail();
            email.setHostName("smtp.gmail.com");

            email.setSmtpPort(587);
            email.setAuthentication(GMAIL_REMITENTE, GMAIL_APP_PASSWORD);

            email.setStartTLSEnabled(true);
            email.setSSLCheckServerIdentity(false);
            
            email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "*");
            email.getMailSession().getProperties().put("mail.smtp.ssl.checkserveridentity", "false");

            //Cabeceras para el email

            email.setFrom(GMAIL_REMITENTE, "Sistema M&JTALLERES");
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
