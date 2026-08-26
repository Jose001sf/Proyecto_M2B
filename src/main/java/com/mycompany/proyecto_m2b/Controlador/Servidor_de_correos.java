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
    
    //Proveedores chavales
    public void enviarBorradorCompraProveedor(String idCompra, String fecha, double total, String nombreProveedor, javax.swing.JTable tablaDetalles) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthentication(GMAIL_REMITENTE, GMAIL_APP_PASSWORD);
            email.setStartTLSEnabled(true);
            email.setSSLCheckServerIdentity(false);
            
            email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "*");
            email.getMailSession().getProperties().put("mail.smtp.ssl.checkserveridentity", "false");

            email.setFrom(GMAIL_REMITENTE, " Sistema M&J TALLERES ");
            email.setSubject("[BORRADOR / ORDEN DE COMPRA] - ID: " + idCompra);
            
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Se ha generado una nueva orden de compra: ");
            mensaje.append("--------------------------------------------------");
            mensaje.append("• ID Compra: ").append(idCompra).append(" ");
            mensaje.append("• Proveedor: ").append(nombreProveedor).append(" ");
            mensaje.append("• Fecha: ").append(fecha).append(" ");
            mensaje.append("--------------------------------------------------");
            mensaje.append("DETALLE DE REPUESTOS SOLICITADOS:  ");
            
            javax.swing.table.TableModel modelo = tablaDetalles.getModel();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String idRep = modelo.getValueAt(i, 0) != null ? modelo.getValueAt(i, 0).toString() : "";
                String nombreRep = modelo.getValueAt(i, 1) != null ? modelo.getValueAt(i, 1).toString() : "";
                String cantidad = modelo.getValueAt(i, 2) != null ? modelo.getValueAt(i, 2).toString() : "";
                String pUnit = modelo.getValueAt(i, 3) != null ? modelo.getValueAt(i, 3).toString() : "";
                String subtotal = modelo.getValueAt(i, 4) != null ? modelo.getValueAt(i, 4).toString() : "";
                
                mensaje.append(String.format("- [%s] %s | Cant: %s | P.Unit: $%s | Subtotal: $%s ", 
                        idRep, nombreRep, cantidad, pUnit, subtotal));
            }
            
            mensaje.append("--------------------------------------------------");
            mensaje.append(String.format("TOTAL A PAGAR: $%.2f ", total));
            mensaje.append("--- Mensaje automático generado por el Sistema M&J ---");

            email.setMsg(mensaje.toString());
            email.addTo(GMAIL_REMITENTE);
            email.send();
            
            System.out.println("Correo de borrador/orden enviado exitosamente.");
        } catch (EmailException e) {
            System.out.println("Error al enviar el correo de respaldo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
