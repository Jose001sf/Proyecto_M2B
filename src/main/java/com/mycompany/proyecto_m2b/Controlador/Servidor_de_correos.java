/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.Controlador;

import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author usuario
 */
public class Servidor_de_correos {
    private static final String GMAIL_REMITENTE = "talleresmj.latoneriamecanica@gmail.com";
    private static final String GMAIL_APP_PASSWORD = "nzvjhsphojounneb"; 

    public void enviarCorreoUsuario(String nom_usuario, String contra_usuario, String correo_person) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthentication(GMAIL_REMITENTE, GMAIL_APP_PASSWORD);

            email.setStartTLSEnabled(true);
            email.setSSLCheckServerIdentity(false);
            
            email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "*");
            email.getMailSession().getProperties().put("mail.smtp.ssl.checkserveridentity", "false");

            // Cabeceras para el email
            email.setFrom(GMAIL_REMITENTE, "Sistema M&JTALLERES");
            email.setSubject("Bienvenido a M&JTALLERES" + "- CREDENCIALES DE ACCESO");
            
            String mensaje = "Estimado/a\n"
                    + "Estas son su credenciales de usuario\n"
                    + "USUARIO: " + nom_usuario + "\n"
                    + "CONTRASEÑA: " + contra_usuario + "\n"
                    + "Administracion M&JTALLERES";
            email.setMsg(mensaje);
            email.addTo(correo_person);
            email.send();
            System.out.println("Correo enviado exitosamente");
        } catch (EmailException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void enviarCorreoActualizacionUsuario(String nom_usuario, String contra_usuario, String correo_person) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthentication(GMAIL_REMITENTE, GMAIL_APP_PASSWORD);

            email.setStartTLSEnabled(true);
            email.setSSLCheckServerIdentity(false);
            
            email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "*");
            email.getMailSession().getProperties().put("mail.smtp.ssl.checkserveridentity", "false");

            // Cabeceras para el email
            email.setFrom(GMAIL_REMITENTE, "Sistema M&JTALLERES");
            email.setSubject("Actualización de la credenciales de acceso" + "- M&JTALLERES");
            
            String mensaje = "Estimado/a\n"
                    + "Se le informa que sus credenciales de acceso han sido modificadas\n"
                    + "NUEVO USUARIO: " + nom_usuario + "\n"
                    + "NUEVA CONTRASEÑA: " + contra_usuario + "\n"
                    + "Cualquie inconveniente, por favor comunicarse con el administrador o dueño del taller"+"\n"
                    + "Administracion M&JTALLERES";
            email.setMsg(mensaje);
            email.addTo(correo_person);
            email.send();
            System.out.println("Correo enviado exitosamente");
        } catch (EmailException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void InformarBaja (String correo_person) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthentication(GMAIL_REMITENTE, GMAIL_APP_PASSWORD);

            email.setStartTLSEnabled(true);
            email.setSSLCheckServerIdentity(false);
            
            email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "*");
            email.getMailSession().getProperties().put("mail.smtp.ssl.checkserveridentity", "false");

            // Cabeceras para el email
            email.setFrom(GMAIL_REMITENTE, "Sistema M&JTALLERES");
            email.setSubject("Actualización de la credenciales de acceso" + "- M&JTALLERES");
            
            String mensaje = "Estimado/a\n"
                    + "Se le informa que se le ha dado de baja en el sistema";
            email.setMsg(mensaje);
            email.addTo(correo_person);
            email.send();
            System.out.println("Correo enviado exitosamente");
        } catch (EmailException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void InformarAlta (String correo_person) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthentication(GMAIL_REMITENTE, GMAIL_APP_PASSWORD);

            email.setStartTLSEnabled(true);
            email.setSSLCheckServerIdentity(false);
            
            email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "*");
            email.getMailSession().getProperties().put("mail.smtp.ssl.checkserveridentity", "false");

            // Cabeceras para el email
            email.setFrom(GMAIL_REMITENTE, "Sistema M&JTALLERES");
            email.setSubject("Actualización de la credenciales de acceso" + "- M&JTALLERES");
            
            String mensaje = "Estimado/a\n"
                    + "Se le informa que se le ha dado de alta de nuevo en el sistema";
            email.setMsg(mensaje);
            email.addTo(correo_person);
            email.send();
            System.out.println("Correo enviado exitosamente");
        } catch (EmailException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Proveedores chavales
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

            email.setFrom(GMAIL_REMITENTE, "Sistema M&J TALLERES");
            email.setSubject("[BORRADOR / ORDEN DE COMPRA] - ID: " + idCompra);
            
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Se ha generado una nueva orden de compra:\n\n");
            mensaje.append("--------------------------------------------------\n");
            mensaje.append("• ID Compra: ").append(idCompra).append("\n");
            mensaje.append("• Proveedor: ").append(nombreProveedor).append("\n");
            mensaje.append("• Fecha: ").append(fecha).append("\n");
            mensaje.append("--------------------------------------------------\n\n");
            mensaje.append("DETALLE DE REPUESTOS SOLICITADOS:\n");
            
            javax.swing.table.TableModel modelo = tablaDetalles.getModel();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String idRep = modelo.getValueAt(i, 0) != null ? modelo.getValueAt(i, 0).toString() : "";
                String nombreRep = modelo.getValueAt(i, 1) != null ? modelo.getValueAt(i, 1).toString() : "";
                String cantidad = modelo.getValueAt(i, 2) != null ? modelo.getValueAt(i, 2).toString() : "";
                String pUnit = modelo.getValueAt(i, 3) != null ? modelo.getValueAt(i, 3).toString() : "";
                String subtotal = modelo.getValueAt(i, 4) != null ? modelo.getValueAt(i, 4).toString() : "";
                
                mensaje.append(String.format("- [%s] %s | Cant: %s | P.Unit: $%s | Subtotal: $%s\n", 
                        idRep, nombreRep, cantidad, pUnit, subtotal));
            }
            
            mensaje.append("\n--------------------------------------------------\n");
            mensaje.append(String.format("TOTAL A PAGAR: $%.2f\n\n", total));
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
    //Estado del vehiculo cambió a terminado
    public void enviarCorreoEstadoVehiculo(String nom_usuario, String estado, String correo_person) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthentication(GMAIL_REMITENTE, GMAIL_APP_PASSWORD);

            email.setStartTLSEnabled(true);
            email.setSSLCheckServerIdentity(false);
            
            email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "*");
            email.getMailSession().getProperties().put("mail.smtp.ssl.checkserveridentity", "false");

            // Cabeceras para el email
            email.setFrom(GMAIL_REMITENTE, "Sistema M&JTALLERES");
            email.setSubject("M&JTALLERES - ACTUALIZACION DE ESTADO DE SU VEHICULO");
            
            String mensaje = "Estimado/a " + nom_usuario + ",\n\n"
                       + "Le informamos que el estado de su vehículo en nuestro taller ahora es: " 
                       + estado.toUpperCase() + ".\n\n"
                       + "Por favor, acérquese a nuestras instalaciones para proceder con la entrega.\n\n"
                       + "Atentamente,\n"
                       + "Administración M&JTALLERES";
            email.setMsg(mensaje);
            email.addTo(correo_person);
            email.send();
            System.out.println("Correo enviado exitosamente");
        } catch (EmailException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //repuestos
    public void enviarReporteInventarioAuto(java.sql.Connection conexion) {
    new Thread(() -> {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthentication(GMAIL_REMITENTE, GMAIL_APP_PASSWORD);
            email.setStartTLSEnabled(true);
            email.setSSLCheckServerIdentity(false);

            email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "*");
            email.getMailSession().getProperties().put("mail.smtp.ssl.checkserveridentity", "false");

            email.setFrom(GMAIL_REMITENTE, "Sistema M&J TALLERES");
            email.setSubject("[INVENTARIO] - Stock Actual de Repuestos");

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("REPORTE GENERAL DE REPUESTOS Y STOCKS\n");
            mensaje.append("--------------------------------------------------\n\n");

            String sql = "SELECT id_repuestos, nom_repuesto, cantidad_actual_repuesto, "
                       + "cantidad_min_repuesto, cantidad_max_repuesto FROM public.repuestos ORDER BY nom_repuesto ASC";

            try (java.sql.Statement st = conexion.createStatement(); 
                 java.sql.ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    String id = rs.getString("id_repuestos");
                    String nombre = rs.getString("nom_repuesto");
                    int actual = rs.getInt("cantidad_actual_repuesto");
                    int min = rs.getInt("cantidad_min_repuesto");
                    int max = rs.getInt("cantidad_max_repuesto");

                    String alerta = "";
                    if (actual <= min) {
                        alerta = " [¡STOCK BAJO!]";
                    } else if (actual >= max) {
                        alerta = " [¡EXCESO DE STOCK!]";
                    }

                    mensaje.append(String.format("• [%s] %s\n", id, nombre));
                    mensaje.append(String.format("  Stock Actual: %d | Min: %d | Max: %d%s\n\n", actual, min, max, alerta));
                }
            }

            mensaje.append("--------------------------------------------------\n");
            mensaje.append("--- Mensaje automático generado por el Sistema M&J ---");

            email.setMsg(mensaje.toString());
            email.addTo(GMAIL_REMITENTE); 
            email.send();

            System.out.println("Reporte de inventario enviado exitosamente.");

        } catch (Exception e) {
            System.out.println("Error al enviar el reporte de inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }).start();
}
    
    public void enviarBorradorVentaResiduos(String idEncabVenta, String fechaCompra, double totalEncabVenta, String idEmpresaRec, javax.swing.JTable tablaDetalles) {
    try {
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthentication(GMAIL_REMITENTE, GMAIL_APP_PASSWORD);
        email.setStartTLSEnabled(true);
        email.setSSLCheckServerIdentity(false);
        
        email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "*");
        email.getMailSession().getProperties().put("mail.smtp.ssl.checkserveridentity", "false");

        email.setFrom(GMAIL_REMITENTE, "Sistema M&J TALLERES");
        email.setSubject("[BORRADOR / VENTA DE RESIDUOS] - ID: " + idEncabVenta);
        
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Se ha generado un nuevo borrador de venta de residuos:\n\n");
        mensaje.append("--------------------------------------------------\n");
        mensaje.append("• ID Venta: ").append(idEncabVenta).append("\n");
        mensaje.append("• Empresa Recicladora: ").append(idEmpresaRec).append("\n");
        mensaje.append("• Fecha: ").append(fechaCompra).append("\n");
        mensaje.append("--------------------------------------------------\n\n");
        mensaje.append("DETALLE DE RESIDUOS VENDIDOS:\n");
        
        javax.swing.table.TableModel modelo = tablaDetalles.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String idResiduos = modelo.getValueAt(i, 0) != null ? modelo.getValueAt(i, 0).toString().trim() : "";
            String nombreResiduo = modelo.getValueAt(i, 1) != null ? modelo.getValueAt(i, 1).toString().trim() : "";
            String cantVendida = modelo.getValueAt(i, 2) != null ? modelo.getValueAt(i, 2).toString().trim() : "";
            String subtotalResiduos = modelo.getValueAt(i, 4) != null ? modelo.getValueAt(i, 4).toString().trim() : "";
            
            if (!idResiduos.isEmpty()) {
                mensaje.append(String.format("- Residuo: [%s] %s | Cant. Vendida: %s | Subtotal: $%s\n", 
                        idResiduos, nombreResiduo, cantVendida, subtotalResiduos));
            }
        }
        
        mensaje.append("\n--------------------------------------------------\n");
        mensaje.append(String.format("TOTAL VENTA: $%.2f\n\n", totalEncabVenta));
        mensaje.append("--- Mensaje automático generado por el Sistema M&J ---");

        email.setMsg(mensaje.toString());
        email.addTo(GMAIL_REMITENTE);
        email.send();
        
        System.out.println("Correo de borrador de venta de residuos enviado exitosamente.");
    } catch (EmailException e) {
        System.out.println("Error al enviar el correo de respaldo: " + e.getMessage());
        e.printStackTrace();
    }
}
}