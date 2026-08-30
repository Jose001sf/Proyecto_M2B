/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.proyecto_m2b.Vista;

import com.mycompany.proyecto_m2b.Controlador.ResiduoDAO;
import com.mycompany.proyecto_m2b.modelo.Residuos;
import java.awt.Frame;
import java.awt.Window;
import java.util.List;
import javax.swing.SwingUtilities;


/**
 *
 * @author jose
 */
public class NuevoResiduo extends javax.swing.JPanel {

    /**
     * Creates new form NuevoResiduo
     */
    public NuevoResiduo() {
        initComponents();
        cargarComboTiposResiduo();
        cargarComboEstados(); 
        ValidarCantidad.setText("");
        
        txtCantidad.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validarCantidadEnTiempoReal();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validarCantidadEnTiempoReal();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validarCantidadEnTiempoReal();
            }
        });
    }
    
    public void validarCantidadEnTiempoReal() {
        String texto = txtCantidad.getText().trim();
        final int CANTIDAD_MAXIMA_PERMITIDA = 1000;

        if (texto.isEmpty()) {
            ValidarCantidad.setText("");
            return;
        }

        try {
            int cantidad = Integer.parseInt(texto);

            if (cantidad < 0) {
                ValidarCantidad.setText("No se permiten números negativos.");
            } else if (cantidad == 0) {
                ValidarCantidad.setText("La cantidad no puede ser 0.");
            } else if (cantidad > CANTIDAD_MAXIMA_PERMITIDA) {
                ValidarCantidad.setText("Excede el límite máximo (" + CANTIDAD_MAXIMA_PERMITIDA + ").");
            } else {
                ValidarCantidad.setText("");
            }

        } catch (NumberFormatException e) {
            ValidarCantidad.setText("Solo se permiten números enteros.");
        }
    }
        
    public void cargarComboTiposResiduo() {
    comboResiduos.removeAllItems();
    comboResiduos.addItem("Seleccione un tipo de residuo");
    
    ResiduoDAO dao = new ResiduoDAO();
    List<String> lista = dao.obtenerNombresTiposResiduo();
    
    for (String nombre : lista) {
        comboResiduos.addItem(nombre);
    }
}
    
    public void cargarComboEstados() {
    comboEstadoResiduo.removeAllItems();
    comboEstadoResiduo.addItem("Seleccione un estado");
    comboEstadoResiduo.addItem("Sólido");
    comboEstadoResiduo.addItem("Líquido");
    comboEstadoResiduo.addItem("Gaseoso");
    comboEstadoResiduo.addItem("Pastoso / Semisólido");
}
    
    
        public void procesarRegistroResiduo() {
        final int CANTIDAD_MAXIMA_PERMITIDA = 1000;

    if (!ValidarCantidad.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Por favor, corrija el error en la cantidad antes de guardar.", 
                "Error de Validación", javax.swing.JOptionPane.WARNING_MESSAGE);
            txtCantidad.requestFocus();
            return;
        }    
        
    if (comboResiduos.getSelectedIndex() <= 0 || comboEstadoResiduo.getSelectedIndex() <= 0) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Debe seleccionar un tipo de residuo y su estado físico.", 
            "Campos Incompletos", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    String tipoSeleccionado = comboResiduos.getSelectedItem().toString();
    String estadoSeleccionado = comboEstadoResiduo.getSelectedItem().toString();
    int cantidadIngresada;

    try {
        cantidadIngresada = Integer.parseInt(txtCantidad.getText().trim());
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Ingrese una cantidad numérica válida.", 
            "Error de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (cantidadIngresada > CANTIDAD_MAXIMA_PERMITIDA) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "La cantidad ingresada (" + cantidadIngresada + ") excede el límite máximo permitido por registro (" + CANTIDAD_MAXIMA_PERMITIDA + ").",
            "Límite Excedido", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    ResiduoDAO dao = new ResiduoDAO();
    String idGenerado = dao.generarIdResiduo();
    String idTipoFk = dao.obtenerIdTipoResiduosPorNombre(tipoSeleccionado);
    
    Residuos nuevoResiduo = new Residuos(
        idGenerado,
        tipoSeleccionado,
        estadoSeleccionado,
        idTipoFk,
        cantidadIngresada,
        CANTIDAD_MAXIMA_PERMITIDA
    );

    if (dao.registrarResiduo(nuevoResiduo)) {
        javax.swing.JOptionPane.showMessageDialog(this, "Residuo registrado correctamente con ID: " + idGenerado);
        txtCantidad.setText("");
        comboResiduos.setSelectedIndex(0);
        comboEstadoResiduo.setSelectedIndex(0);
    } else {
        javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar el residuo en la base de datos.", "Error BD", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Fondo = new javax.swing.JPanel();
        BarraArriba = new javax.swing.JPanel();
        NombreVentanaResiduos = new javax.swing.JLabel();
        TituloFuncion2 = new javax.swing.JLabel();
        Residuo = new javax.swing.JLabel();
        Kilos = new javax.swing.JLabel();
        comboResiduos = new javax.swing.JComboBox<>();
        txtCantidad = new javax.swing.JTextField();
        PanelNuevoTipoResiduo = new javax.swing.JPanel();
        Nuevo1 = new javax.swing.JLabel();
        ImagenADD1 = new javax.swing.JLabel();
        PanelGuardar = new javax.swing.JPanel();
        Guardar = new javax.swing.JLabel();
        ImagenSAVE = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboEstadoResiduo = new javax.swing.JComboBox<>();
        ValidarCantidad = new javax.swing.JLabel();
        btnModificar = new javax.swing.JPanel();
        Editar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        Fondo.setBackground(new java.awt.Color(255, 255, 255));
        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BarraArriba.setBackground(new java.awt.Color(0, 0, 0));

        NombreVentanaResiduos.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        NombreVentanaResiduos.setForeground(new java.awt.Color(255, 255, 255));
        NombreVentanaResiduos.setText("Registrar Nuevo Residuo");

        javax.swing.GroupLayout BarraArribaLayout = new javax.swing.GroupLayout(BarraArriba);
        BarraArriba.setLayout(BarraArribaLayout);
        BarraArribaLayout.setHorizontalGroup(
            BarraArribaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarraArribaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(NombreVentanaResiduos, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        BarraArribaLayout.setVerticalGroup(
            BarraArribaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarraArribaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(NombreVentanaResiduos)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        Fondo.add(BarraArriba, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 690, 60));

        TituloFuncion2.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        TituloFuncion2.setForeground(new java.awt.Color(153, 153, 153));
        TituloFuncion2.setText("REGISTRO DE RESIDUOS A ENTREGAR");
        Fondo.add(TituloFuncion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        Residuo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        Residuo.setForeground(new java.awt.Color(153, 153, 153));
        Residuo.setText("Residuo / Tipo:");
        Fondo.add(Residuo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        Kilos.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        Kilos.setForeground(new java.awt.Color(153, 153, 153));
        Kilos.setText("Cantidad (Kg/L):");
        Fondo.add(Kilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, -1, -1));

        comboResiduos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccion un tipo de residuo", "Item 2", "Item 3", "Item 4" }));
        comboResiduos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboResiduosMouseClicked(evt);
            }
        });
        Fondo.add(comboResiduos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 240, -1));
        Fondo.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 240, -1));

        PanelNuevoTipoResiduo.setBackground(new java.awt.Color(255, 255, 255));
        PanelNuevoTipoResiduo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        PanelNuevoTipoResiduo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelNuevoTipoResiduo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelNuevoTipoResiduoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelNuevoTipoResiduoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelNuevoTipoResiduoMouseExited(evt);
            }
        });

        Nuevo1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Nuevo1.setText("Nuevo");

        ImagenADD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout PanelNuevoTipoResiduoLayout = new javax.swing.GroupLayout(PanelNuevoTipoResiduo);
        PanelNuevoTipoResiduo.setLayout(PanelNuevoTipoResiduoLayout);
        PanelNuevoTipoResiduoLayout.setHorizontalGroup(
            PanelNuevoTipoResiduoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNuevoTipoResiduoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(ImagenADD1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Nuevo1)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        PanelNuevoTipoResiduoLayout.setVerticalGroup(
            PanelNuevoTipoResiduoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNuevoTipoResiduoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelNuevoTipoResiduoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ImagenADD1)
                    .addComponent(Nuevo1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Fondo.add(PanelNuevoTipoResiduo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, -1, -1));

        PanelGuardar.setBackground(new java.awt.Color(242, 101, 34));
        PanelGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        PanelGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelGuardarMouseExited(evt);
            }
        });

        Guardar.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Guardar.setForeground(new java.awt.Color(255, 255, 255));
        Guardar.setText("Guardar");

        ImagenSAVE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save_22dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout PanelGuardarLayout = new javax.swing.GroupLayout(PanelGuardar);
        PanelGuardar.setLayout(PanelGuardarLayout);
        PanelGuardarLayout.setHorizontalGroup(
            PanelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGuardarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(ImagenSAVE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Guardar)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        PanelGuardarLayout.setVerticalGroup(
            PanelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelGuardarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Guardar)
                    .addComponent(ImagenSAVE))
                .addContainerGap())
        );

        Fondo.add(PanelGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, -1, -1));

        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Estado del Residuo");
        Fondo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        comboEstadoResiduo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Fondo.add(comboEstadoResiduo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 240, -1));

        ValidarCantidad.setForeground(new java.awt.Color(255, 51, 51));
        ValidarCantidad.setText("jLabel1");
        Fondo.add(ValidarCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 230, 20));

        btnModificar.setBackground(new java.awt.Color(255, 255, 255));
        btnModificar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarMouseExited(evt);
            }
        });

        Editar.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Editar.setText("Modificar");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout btnModificarLayout = new javax.swing.GroupLayout(btnModificar);
        btnModificar.setLayout(btnModificarLayout);
        btnModificarLayout.setHorizontalGroup(
            btnModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnModificarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Editar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnModificarLayout.setVerticalGroup(
            btnModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnModificarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(btnModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Editar)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        Fondo.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 140, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void PanelGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelGuardarMouseExited
        // TODO add your handling code here:
        PanelGuardar.setBackground(new java.awt.Color(242,101,34));
        Guardar.setForeground(java.awt.Color.white);
    }//GEN-LAST:event_PanelGuardarMouseExited

    private void PanelGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelGuardarMouseEntered
        // TODO add your handling code here:
        PanelGuardar.setBackground(new java.awt.Color(227, 95, 32));
        Guardar.setForeground(new java.awt.Color(217, 217, 192));
    }//GEN-LAST:event_PanelGuardarMouseEntered

    private void PanelGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelGuardarMouseClicked
        // TODO add your handling code here:
           procesarRegistroResiduo();
    }//GEN-LAST:event_PanelGuardarMouseClicked

    private void PanelNuevoTipoResiduoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelNuevoTipoResiduoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_PanelNuevoTipoResiduoMouseExited

    private void PanelNuevoTipoResiduoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelNuevoTipoResiduoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_PanelNuevoTipoResiduoMouseEntered
    private javax.swing.JDialog dialogNuevoTipo = null;
    
    private void PanelNuevoTipoResiduoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelNuevoTipoResiduoMouseClicked
    if (dialogNuevoTipo != null && dialogNuevoTipo.isVisible()) {
        dialogNuevoTipo.dispose();
        dialogNuevoTipo = null;
        return;
    }

    NuevoTipoResiduo panel = new NuevoTipoResiduo();

    java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);

    if (parentWindow instanceof java.awt.Frame) {
        dialogNuevoTipo = new javax.swing.JDialog((java.awt.Frame) parentWindow, false);
    } else if (parentWindow instanceof java.awt.Dialog) {
        dialogNuevoTipo = new javax.swing.JDialog((java.awt.Dialog) parentWindow, false);
    } else {
        dialogNuevoTipo = new javax.swing.JDialog();
        dialogNuevoTipo.setModal(false);
    }

    dialogNuevoTipo.setUndecorated(true);
    dialogNuevoTipo.add(panel);
    dialogNuevoTipo.pack();

    int centerX = (PanelNuevoTipoResiduo.getWidth() - dialogNuevoTipo.getWidth()) / 2;
    java.awt.Point location = new java.awt.Point(centerX, PanelNuevoTipoResiduo.getHeight());
    javax.swing.SwingUtilities.convertPointToScreen(location, PanelNuevoTipoResiduo);

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    int dialogWidth = dialogNuevoTipo.getWidth();
    int dialogHeight = dialogNuevoTipo.getHeight();

    if (location.x + dialogWidth > screenSize.width) {
        location.x = screenSize.width - dialogWidth - 10;
    }
    if (location.x < 0) {
        location.x = 10;
    }

    if (location.y + dialogHeight > screenSize.height) {
        java.awt.Point locationAbove = new java.awt.Point(centerX, -dialogHeight);
        javax.swing.SwingUtilities.convertPointToScreen(locationAbove, PanelNuevoTipoResiduo);
        location.y = locationAbove.y;
    }

    dialogNuevoTipo.setLocation(location);

    dialogNuevoTipo.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            dialogNuevoTipo = null;
        }
    });

    dialogNuevoTipo.setVisible(true);
    }//GEN-LAST:event_PanelNuevoTipoResiduoMouseClicked

    private void comboResiduosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboResiduosMouseClicked
        cargarComboTiposResiduo();
    }//GEN-LAST:event_comboResiduosMouseClicked
    private ListaResiduosDialog dialogListaResiduos = null;
    
    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
       if (dialogListaResiduos != null && dialogListaResiduos.isVisible()) {
            dialogListaResiduos.dispose();
            dialogListaResiduos = null;
            return;
        }

        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        Frame parentFrame = (parentWindow instanceof Frame) ? (Frame) parentWindow : null;
        
        dialogListaResiduos = new ListaResiduosDialog(parentFrame, false, this);
        
        dialogListaResiduos.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                dialogListaResiduos = null;
            }
        });

        dialogListaResiduos.setVisible(true);
    }//GEN-LAST:event_btnModificarMouseClicked

    private void btnModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseEntered
        // TODO add your handling code here:
        btnModificar.setBackground(new java.awt.Color(219,219,219));
        Editar.setForeground(new java.awt.Color(66, 66, 66));
    }//GEN-LAST:event_btnModificarMouseEntered

    private void btnModificarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseExited
        // TODO add your handling code here:
        btnModificar.setBackground(java.awt.Color.white);
        Editar.setForeground(java.awt.Color.black);
    }//GEN-LAST:event_btnModificarMouseExited
    
    public void cargarDatosParaEdicion(String id, String nombre, String estado, int cantidad) {
        txtCantidad.setText(String.valueOf(cantidad));
        comboEstadoResiduo.setSelectedItem(estado);
        comboResiduos.setSelectedItem(nombre);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarraArriba;
    private javax.swing.JLabel Editar;
    private javax.swing.JPanel Fondo;
    private javax.swing.JLabel Guardar;
    private javax.swing.JLabel ImagenADD1;
    private javax.swing.JLabel ImagenSAVE;
    private javax.swing.JLabel Kilos;
    private javax.swing.JLabel NombreVentanaResiduos;
    private javax.swing.JLabel Nuevo1;
    private javax.swing.JPanel PanelGuardar;
    private javax.swing.JPanel PanelNuevoTipoResiduo;
    private javax.swing.JLabel Residuo;
    private javax.swing.JLabel TituloFuncion2;
    private javax.swing.JLabel ValidarCantidad;
    private javax.swing.JPanel btnModificar;
    private javax.swing.JComboBox<String> comboEstadoResiduo;
    private javax.swing.JComboBox<String> comboResiduos;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables
}
