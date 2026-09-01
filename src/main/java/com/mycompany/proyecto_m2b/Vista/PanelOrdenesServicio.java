/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.proyecto_m2b.Vista;

import com.mycompany.proyecto_m2b.Controlador.ConexionBD;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.proyecto_m2b.Controlador.OrdenServicioDAO;
import com.mycompany.proyecto_m2b.Controlador.RepuestoDAO;
import com.mycompany.proyecto_m2b.Controlador.ResiduoDAO;
import com.mycompany.proyecto_m2b.Controlador.Servidor_de_correos;
import com.mycompany.proyecto_m2b.Controlador.Validaciones;
import com.mycompany.proyecto_m2b.Controlador.VehiculosDAO;
import com.mycompany.proyecto_m2b.Controlador.DetalleOrdenServicioDAO;
import com.mycompany.proyecto_m2b.modelo.DetalleRepuesto;
import com.mycompany.proyecto_m2b.modelo.Produce;
import com.mycompany.proyecto_m2b.modelo.Repuesto;
import com.mycompany.proyecto_m2b.modelo.Servicio;
import com.mycompany.proyecto_m2b.modelo.orden_de_servicio;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class PanelOrdenesServicio extends javax.swing.JPanel {

    /**
     * Creates new form PanelOrdenesServicio
     */
    private String idOrdenCargada = null;
    private boolean esModoEdicion = false;
    public PanelOrdenesServicio() {
        initComponents();
        cargarCombosIniciales();
        calcularTotal();
        tblRepuestosUsados.getModel().addTableModelListener(e->{calcularTotal();});
        tblDetalleServicio.getModel().addTableModelListener(e->{calcularTotal();});
        CalendarioFechaIngreso.setDate(new java.util.Date());
        CalendarioFechaIngreso.setEnabled(false);
        ((JTextFieldDateEditor) CalendarioFechaIngreso.getDateEditor()).setEditable(false);
        inicializarTablaDetalleServicio();

        configurarModeloTablaRepuestos();
        cargarTablaRepuestos("");
        LimpiarDatos();

        txtBuscarRepueseto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarRepuesetoKeyReleased(evt);
            }
        });
        configurarModeloTablaRepuestosUsados();
    }
    private List<Produce> listaTemporal=new ArrayList<>();
    
    private void calcularSubtotalRepuestos() {
    double totalRepuestos = 0.0;
    DefaultTableModel modeloUsados = (DefaultTableModel) tblRepuestosUsados.getModel();
    
    for (int i = 0; i < modeloUsados.getRowCount(); i++) {
        Object valorSubtotal = modeloUsados.getValueAt(i, 4); 
        if (valorSubtotal != null) {
            try {
                totalRepuestos += Double.parseDouble(valorSubtotal.toString());
            } catch (NumberFormatException e) {
            }
        }
    }
    
    txtSubtotalRepuestos.setText(String.format("%.2f", totalRepuestos));
}

    private void inicializarTablaDetalleServicio() {
        modeloTablaServicios = (DefaultTableModel) tblDetalleServicio.getModel();
        modeloTablaServicios.setRowCount(0);
    }

    private void configurarModeloTablaRepuestos() {
        String[] titulos = {"ID", "Nombre", "Cantidad Actual", "Precio"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tblDetalleRepuestos.setModel(modelo);
    }

    private void txtBuscarRepuesetoKeyReleased(java.awt.event.KeyEvent evt) {
        String textoBusqueda = txtBuscarRepueseto.getText();
        cargarTablaRepuestos(textoBusqueda);
    }
   
    public void cargarTablaRepuestos(String filtro) {
        DefaultTableModel modelo = (DefaultTableModel) tblDetalleRepuestos.getModel();
        modelo.setRowCount(0);

        RepuestoDAO dao = new RepuestoDAO();
        
        try (Connection con = ConexionBD.obtenerConexion()) {
            List<Repuesto> lista;
            
            if (filtro == null || filtro.trim().isEmpty()) {
                lista = dao.obtenerRepuestosParaTabla(con);  
            } else {
                lista = dao.buscarRepuestosPorFiltro(con, filtro);
            }
            
            for (Repuesto r : lista) {
                Object[] fila = new Object[4];
                fila[0] = r.getIdRepuestos();
                fila[1] = r.getNomRepuesto();
                fila[2] = r.getCantidadActualRepuesto();
                fila[3] = r.getPrecioRepuestoUnit();
                
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar los repuestos en la tabla: " + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        TXTcostoTotal = new javax.swing.JTextField();
        PanelNuevo = new javax.swing.JPanel();
        Nuevo = new javax.swing.JLabel();
        ImagenADD = new javax.swing.JLabel();
        PanelGuardar = new javax.swing.JPanel();
        Guardar = new javax.swing.JLabel();
        ImagenSAVE = new javax.swing.JLabel();
        PanelEditar = new javax.swing.JPanel();
        Editar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PanelBuscar = new javax.swing.JPanel();
        Buscar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Estados = new javax.swing.JComboBox<>();
        CalendarioFechaIngreso = new com.toedter.calendar.JDateChooser();
        CalendarioFechaEntrega = new com.toedter.calendar.JDateChooser();
        comboPlacas = new javax.swing.JComboBox<>();
        comboCliente = new javax.swing.JComboBox<>();
        comboEmpleado = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleServicio = new javax.swing.JTable();
        lblSubTotalServicios = new javax.swing.JLabel();
        PanelAgregarServicio = new javax.swing.JPanel();
        Nuevo1 = new javax.swing.JLabel();
        ImagenADD1 = new javax.swing.JLabel();
        PanelEliminarServicio = new javax.swing.JPanel();
        DarDeBaja = new javax.swing.JLabel();
        ImagenDarBaja = new javax.swing.JLabel();
        Visualizar = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtBuscarRepueseto = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalleRepuestos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRepuestosUsados = new javax.swing.JTable();
        btnAgregarRepuestos = new javax.swing.JButton();
        txtSubtotalRepuestos = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnEliminarRepuesto = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(238, 238, 238));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(153, 153, 153));
        jLabel21.setText("<- REGRESAR");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ORDENES DE SERVICIO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 946, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 60));

        jLabel5.setText("Placa del vehiculo:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        jLabel6.setText("Cedula del cliente:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        jLabel7.setText("Empleado asignado:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jLabel8.setText("Estado de Orden:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel10.setText("Fecha de ingreso:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, -1, -1));

        jLabel11.setText("Costo Total:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, 20));

        TXTcostoTotal.setForeground(new java.awt.Color(153, 153, 153));
        TXTcostoTotal.setText("0.00");
        TXTcostoTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TXTcostoTotalFocusGained(evt);
            }
        });
        TXTcostoTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TXTcostoTotalMousePressed(evt);
            }
        });
        TXTcostoTotal.addActionListener(this::TXTcostoTotalActionPerformed);
        TXTcostoTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TXTcostoTotalKeyPressed(evt);
            }
        });
        jPanel1.add(TXTcostoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, 250, -1));

        PanelNuevo.setBackground(new java.awt.Color(255, 255, 255));
        PanelNuevo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        PanelNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelNuevoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelNuevoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelNuevoMouseExited(evt);
            }
        });

        Nuevo.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Nuevo.setText("Nuevo");

        ImagenADD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout PanelNuevoLayout = new javax.swing.GroupLayout(PanelNuevo);
        PanelNuevo.setLayout(PanelNuevoLayout);
        PanelNuevoLayout.setHorizontalGroup(
            PanelNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNuevoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(ImagenADD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Nuevo)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        PanelNuevoLayout.setVerticalGroup(
            PanelNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNuevoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ImagenADD)
                    .addComponent(Nuevo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PanelNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, -1, -1));

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

        jPanel1.add(PanelGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, -1, -1));

        PanelEditar.setBackground(new java.awt.Color(255, 255, 255));
        PanelEditar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        PanelEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelEditarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelEditarMouseExited(evt);
            }
        });

        Editar.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Editar.setText("Editar");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout PanelEditarLayout = new javax.swing.GroupLayout(PanelEditar);
        PanelEditar.setLayout(PanelEditarLayout);
        PanelEditarLayout.setHorizontalGroup(
            PanelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Editar)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        PanelEditarLayout.setVerticalGroup(
            PanelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEditarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Editar)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        jPanel1.add(PanelEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 550, -1, -1));

        PanelBuscar.setBackground(new java.awt.Color(255, 255, 255));
        PanelBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        PanelBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelBuscarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelBuscarMouseExited(evt);
            }
        });

        Buscar.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Buscar.setText("Buscar");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/person_search_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout PanelBuscarLayout = new javax.swing.GroupLayout(PanelBuscar);
        PanelBuscar.setLayout(PanelBuscarLayout);
        PanelBuscarLayout.setHorizontalGroup(
            PanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBuscarLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Buscar)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        PanelBuscarLayout.setVerticalGroup(
            PanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Buscar)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PanelBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 550, -1, -1));

        jLabel3.setText("Fecha de entrega:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, -1, -1));

        Estados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione uno", "Ingresado", "En Proceso", "Terminado", "Entregado" }));
        jPanel1.add(Estados, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, -1, -1));
        jPanel1.add(CalendarioFechaIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 250, -1));
        jPanel1.add(CalendarioFechaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 250, -1));

        comboPlacas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboPlacas.addActionListener(this::comboPlacasActionPerformed);
        jPanel1.add(comboPlacas, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 210, -1));

        comboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(comboCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 210, -1));

        comboEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(comboEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 210, -1));

        tblDetalleServicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre del Servicio", "Precio Unitario", "Cantidad", "Subtotal Servicio"
            }
        ));
        jScrollPane1.setViewportView(tblDetalleServicio);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 750, 90));

        lblSubTotalServicios.setText(" ");
        jPanel1.add(lblSubTotalServicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 320, 70, -1));

        PanelAgregarServicio.setBackground(new java.awt.Color(255, 255, 255));
        PanelAgregarServicio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        PanelAgregarServicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelAgregarServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelAgregarServicioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelAgregarServicioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelAgregarServicioMouseExited(evt);
            }
        });

        Nuevo1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Nuevo1.setText("Agregar Servicio");

        ImagenADD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout PanelAgregarServicioLayout = new javax.swing.GroupLayout(PanelAgregarServicio);
        PanelAgregarServicio.setLayout(PanelAgregarServicioLayout);
        PanelAgregarServicioLayout.setHorizontalGroup(
            PanelAgregarServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAgregarServicioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ImagenADD1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Nuevo1)
                .addGap(16, 16, 16))
        );
        PanelAgregarServicioLayout.setVerticalGroup(
            PanelAgregarServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAgregarServicioLayout.createSequentialGroup()
                .addGroup(PanelAgregarServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Nuevo1)
                    .addComponent(ImagenADD1))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel1.add(PanelAgregarServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 180, 30));

        PanelEliminarServicio.setBackground(new java.awt.Color(255, 255, 255));
        PanelEliminarServicio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 106, 106)));
        PanelEliminarServicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelEliminarServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelEliminarServicioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelEliminarServicioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelEliminarServicioMouseExited(evt);
            }
        });

        DarDeBaja.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        DarDeBaja.setForeground(new java.awt.Color(215, 106, 106));
        DarDeBaja.setText("Eliminar");

        ImagenDarBaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/person_cancel_22dp_EA3323_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout PanelEliminarServicioLayout = new javax.swing.GroupLayout(PanelEliminarServicio);
        PanelEliminarServicio.setLayout(PanelEliminarServicioLayout);
        PanelEliminarServicioLayout.setHorizontalGroup(
            PanelEliminarServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelEliminarServicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ImagenDarBaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(DarDeBaja)
                .addGap(30, 30, 30))
        );
        PanelEliminarServicioLayout.setVerticalGroup(
            PanelEliminarServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEliminarServicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEliminarServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DarDeBaja)
                    .addComponent(ImagenDarBaja))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PanelEliminarServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, -1, -1));

        Visualizar.setBackground(new java.awt.Color(0, 0, 0));
        Visualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Visualizar.setText("VISUALIZAR DETALLES DE RESIDUOS");
        Visualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Visualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VisualizarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                VisualizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                VisualizarMouseExited(evt);
            }
        });
        jPanel1.add(Visualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 170, -1, -1));

        jLabel15.setText("Buscar ID/Nombre:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 380, -1, 20));
        jPanel1.add(txtBuscarRepueseto, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 380, 220, -1));

        tblDetalleRepuestos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblDetalleRepuestos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 410, -1, 80));

        tblRepuestosUsados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane3.setViewportView(tblRepuestosUsados);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 510, 110));

        btnAgregarRepuestos.setText("+  Agregar");
        btnAgregarRepuestos.addActionListener(this::btnAgregarRepuestosActionPerformed);
        jPanel1.add(btnAgregarRepuestos, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 380, -1, -1));
        jPanel1.add(txtSubtotalRepuestos, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 500, 120, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Subtotal Servicios: $");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 320, -1, -1));

        btnEliminarRepuesto.setText("X  Eliminar");
        btnEliminarRepuesto.addActionListener(this::btnEliminarRepuestoActionPerformed);
        jPanel1.add(btnEliminarRepuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 500, -1, -1));

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("DETALLE DE REPUESTOS");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Subtotal Repuestos:  $");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 500, -1, -1));

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("DETALLE DE SERVICIO");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarRepuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarRepuestoActionPerformed
        int filaSeleccionada = tblRepuestosUsados.getSelectedRow();

        if (filaSeleccionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Por favor seleccione un repuesto de la tabla para eliminar.",
                "Atención",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tblRepuestosUsados.getModel();

        modeloTabla.removeRow(filaSeleccionada);

        double nuevoSubtotalRepuestos = 0.0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Object valorSubtotal = modeloTabla.getValueAt(i, 4);
            if (valorSubtotal != null) {
                nuevoSubtotalRepuestos += Double.parseDouble(valorSubtotal.toString());
            }
        }

        txtSubtotalRepuestos.setText(String.format("%.2f", nuevoSubtotalRepuestos));
    }//GEN-LAST:event_btnEliminarRepuestoActionPerformed

    private void btnAgregarRepuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarRepuestosActionPerformed
        int filaSeleccionada = tblDetalleRepuestos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un repuesto de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idRepuesto = tblDetalleRepuestos.getValueAt(filaSeleccionada, 0).toString();
        String nombreRepuesto = tblDetalleRepuestos.getValueAt(filaSeleccionada, 1).toString();
        double precioUnitario = Double.parseDouble(tblDetalleRepuestos.getValueAt(filaSeleccionada, 3).toString());

        String inputCantidad = (String) JOptionPane.showInputDialog(
            this,
            "Repuesto: " + nombreRepuesto + "\nIngrese la cantidad a usar:",
            "Cantidad de Repuesto",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            ""
        );

        if (inputCantidad == null) {
            return;
        }

        try {
            int cantidadIngresada = Integer.parseInt(inputCantidad.trim());

            if (cantidadIngresada <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int stockRealEnBD = 0;
            try (Connection con = ConexionBD.obtenerConexion()) {
                RepuestoDAO dao = new RepuestoDAO();
                stockRealEnBD = dao.obtenerStockActual(con, idRepuesto);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos para verificar el stock.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (stockRealEnBD == -1) {
                JOptionPane.showMessageDialog(this, "No se pudo verificar el stock actual.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cantidadIngresada > stockRealEnBD) {
                JOptionPane.showMessageDialog(this, "No hay suficiente stock en la base de datos. Stock actual: " + stockRealEnBD, "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double subtotal = cantidadIngresada * precioUnitario;

            DefaultTableModel modeloUsados = (DefaultTableModel) tblRepuestosUsados.getModel();

            boolean repuestoExiste = false;
            for (int i = 0; i < modeloUsados.getRowCount(); i++) {
                Object valorCelda = modeloUsados.getValueAt(i, 0);
                if (valorCelda == null) {
                    continue;
                }

                String idExistente = valorCelda.toString();
                if (idExistente.equals(idRepuesto)) {
                    int cantidadAnterior = Integer.parseInt(modeloUsados.getValueAt(i, 2).toString());
                    int nuevaCantidad = cantidadAnterior + cantidadIngresada;

                    if (nuevaCantidad > stockRealEnBD) {
                        JOptionPane.showMessageDialog(this, "La cantidad total excede el stock disponible en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double nuevoSubtotal = nuevaCantidad * precioUnitario;

                    modeloUsados.setValueAt(nuevaCantidad, i, 2);
                    modeloUsados.setValueAt(nuevoSubtotal, i, 4);
                    repuestoExiste = true;
                    break;
                }
            }

            if (!repuestoExiste) {
                Object[] nuevaFila = new Object[5];
                nuevaFila[0] = idRepuesto;
                nuevaFila[1] = nombreRepuesto;
                nuevaFila[2] = cantidadIngresada;
                nuevaFila[3] = precioUnitario;
                nuevaFila[4] = subtotal;

                modeloUsados.insertRow(0, nuevaFila);
            }
            calcularSubtotalRepuestos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número entero válido.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
        }
        calcularTotal();
    }//GEN-LAST:event_btnAgregarRepuestosActionPerformed

    private void PanelEliminarServicioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelEliminarServicioMouseExited
        // TODO add your handling code here:
        PanelEliminarServicio.setBackground(Color.white);
        DarDeBaja.setForeground(new Color(215, 106, 106));
    }//GEN-LAST:event_PanelEliminarServicioMouseExited

    private void PanelEliminarServicioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelEliminarServicioMouseEntered
        // TODO add your handling code here:
        PanelEliminarServicio.setBackground(new Color (252, 168, 168));
        DarDeBaja.setForeground(Color.white);
    }//GEN-LAST:event_PanelEliminarServicioMouseEntered

    private void PanelEliminarServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelEliminarServicioMouseClicked
        // TODO add your handling code here:
        int fila = tblDetalleServicio.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla para eliminar.");
            return;
        }
        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Seguro que deseas quitar este servicio de la orden?", "Confirmar",
            JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        detalleServicios.remove(fila);
        actualizarTablaDetalleServicio();
    }//GEN-LAST:event_PanelEliminarServicioMouseClicked

    private void PanelAgregarServicioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelAgregarServicioMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_PanelAgregarServicioMouseExited

    private void PanelAgregarServicioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelAgregarServicioMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_PanelAgregarServicioMouseEntered

    private void PanelAgregarServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelAgregarServicioMouseClicked
        // TODO add your handling code here:
        PanelServiciosOrden panel = new PanelServiciosOrden();
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        javax.swing.JDialog dialog = (parentWindow instanceof java.awt.Frame)
        ? new javax.swing.JDialog((java.awt.Frame) parentWindow, "Agregar Servicio", true)
        : new javax.swing.JDialog();
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true); // se detiene aquí (es modal) hasta que cierres el diálogo

        if (panel.isAceptado()) {
            agregarServicioADetalle(panel.getServicioSeleccionado(), panel.getCantidadSeleccionada());
        }
        calcularTotal();
    }//GEN-LAST:event_PanelAgregarServicioMouseClicked

    private void comboPlacasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPlacasActionPerformed
        // TODO add your handling code here:
        if (comboPlacas.getSelectedIndex() > 0) { // Ignora el índice 0 ("Seleccione...")
            String placaSeleccionada = comboPlacas.getSelectedItem().toString();

            OrdenServicioDAO dao = new OrdenServicioDAO();
            String datosCliente = dao.obtenerCedulaPorPlaca(placaSeleccionada);

            if (datosCliente != null) {
                comboCliente.removeAllItems();
                comboCliente.addItem(datosCliente);
                comboCliente.setSelectedIndex(0);
            }
        } else {
            comboCliente.removeAllItems();
        }
    }//GEN-LAST:event_comboPlacasActionPerformed

    private void PanelBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelBuscarMouseExited
        // TODO add your handling code here:
        PanelBuscar.setBackground(Color.white);
        Buscar.setForeground(Color.black);
    }//GEN-LAST:event_PanelBuscarMouseExited

    private void PanelBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelBuscarMouseEntered
        // TODO add your handling code here:
        PanelBuscar.setBackground(new Color(219,219,219));
        Buscar.setForeground(new Color(66, 66, 66));
    }//GEN-LAST:event_PanelBuscarMouseEntered
public void cargarTablaRepuestosPorOrden(List<Object[]> listaRepuestos) {
    DefaultTableModel modeloIzquierda = (DefaultTableModel) tblRepuestosUsados.getModel();
    modeloIzquierda.setRowCount(0);

    if (listaRepuestos == null || listaRepuestos.isEmpty()) {
        return;
    }

    for (Object[] fila : listaRepuestos) {

        modeloIzquierda.addRow(fila);
    }
}
public void cargarTablaServiciosPorOrden(List<Object[]> listaServicios) {
    DefaultTableModel modeloServicios = (DefaultTableModel) tblDetalleServicio.getModel();
    modeloServicios.setRowCount(0);

    if (listaServicios == null || listaServicios.isEmpty()) {
        return;
    }

    for (Object[] fila : listaServicios) {
        modeloServicios.addRow(fila);
    }
    tblDetalleServicio.revalidate();
    tblDetalleServicio.repaint();
}
    private void PanelBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelBuscarMouseClicked
        // TODO add your handling code here:
        if (comboPlacas.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione una placa de la lista para buscar su orden.",
                "Atención",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String placaSeleccionada = comboPlacas.getSelectedItem().toString().trim();
        OrdenServicioDAO dao = new OrdenServicioDAO();
        orden_de_servicio orden = dao.buscarOrdenPorPlaca(placaSeleccionada);
        DetalleOrdenServicioDAO detalle = new DetalleOrdenServicioDAO();
        if (orden != null) {
            this.idOrdenCargada = orden.getId_orden_serv();

            Estados.setSelectedItem(orden.getEstadoorden_servi());
            CalendarioFechaIngreso.setDate(orden.getFecha_ingreso());
            CalendarioFechaEntrega.setDate(orden.getFecha_entrega());
            TXTcostoTotal.setText(String.format(java.util.Locale.US, "%.2f", orden.getCosto_total()));
            List<Object[]> listaRepuestos = detalle.obtenerRepuestosPorOrden(this.idOrdenCargada);
            cargarTablaRepuestosPorOrden(listaRepuestos);
            List<Object[]> listaServicios = detalle.obtenerServiciosPorOrden(this.idOrdenCargada);
            cargarTablaServiciosPorOrden(listaServicios);
            JOptionPane.showMessageDialog(this, "Datos de la orden cargados correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "El vehículo no tiene una orden de servicio registrada.");
        }
    }//GEN-LAST:event_PanelBuscarMouseClicked

    private void PanelEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelEditarMouseExited
        // TODO add your handling code here:
        PanelEditar.setBackground(Color.white);
        Editar.setForeground(Color.black);
    }//GEN-LAST:event_PanelEditarMouseExited

    private void PanelEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelEditarMouseEntered
        // TODO add your handling code here:
        PanelEditar.setBackground(new Color(219,219,219));
        Editar.setForeground(new Color(66, 66, 66));
    }//GEN-LAST:event_PanelEditarMouseEntered

    private void PanelEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelEditarMouseClicked
        // TODO add your handling code here:
        EditarOrdenDeServicio();
        LimpiarDatos();
        LimpiarDatosTablaDetalles();
    }//GEN-LAST:event_PanelEditarMouseClicked

    private void PanelGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelGuardarMouseExited
        // TODO add your handling code here:
        PanelGuardar.setBackground(new Color(242,101,34));
        Guardar.setForeground(Color.white);
    }//GEN-LAST:event_PanelGuardarMouseExited

    private void PanelGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelGuardarMouseEntered
        // TODO add your handling code here:
        PanelGuardar.setBackground(new Color(227, 95, 32));
        Guardar.setForeground(new Color(217, 217, 192));
    }//GEN-LAST:event_PanelGuardarMouseEntered

    private void PanelGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelGuardarMouseClicked
        // TODO add your handling code here:
        GuardarOrdenDeServicio();
        LimpiarDatos();
        LimpiarDatosTablaDetalles();
    }//GEN-LAST:event_PanelGuardarMouseClicked

    private void PanelNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelNuevoMouseExited
        // TODO add your handling code here:
        PanelNuevo.setBackground(Color.white);
        Nuevo.setForeground(Color.black);
    }//GEN-LAST:event_PanelNuevoMouseExited

    private void PanelNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelNuevoMouseEntered
        // TODO add your handling code here:
        PanelNuevo.setBackground(new Color(219,219,219));
        Nuevo.setForeground(new Color(66, 66, 66));
    }//GEN-LAST:event_PanelNuevoMouseEntered

    private void PanelNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelNuevoMouseClicked
        // TODO add your handling code here:
        LimpiarDatos();
        LimpiarDatosTablaDetalles();
        JOptionPane.showMessageDialog(this, "Datos limpiados correctamente"+"\n"
            +"Ingrese los datos");
    }//GEN-LAST:event_PanelNuevoMouseClicked

    private void TXTcostoTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TXTcostoTotalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TXTcostoTotalKeyPressed

    private void TXTcostoTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXTcostoTotalActionPerformed
        // TODO add your handling code here:
        OrdenServicioDAO dao=new OrdenServicioDAO();
        dao.sumarIngresosPorDia(LocalDate.MIN, LocalDate.MAX);
        calcularTotal();
    }//GEN-LAST:event_TXTcostoTotalActionPerformed

    private void TXTcostoTotalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TXTcostoTotalMousePressed
        // TODO add your handling code here:
        if (TXTcostoTotal.getText().equals("0.00")){
            TXTcostoTotal.setText("");
            TXTcostoTotal.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_TXTcostoTotalMousePressed

    private void TXTcostoTotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TXTcostoTotalFocusGained
        // TODO add your handling code here:
        if (TXTcostoTotal.getText().equals("0.00")){
            TXTcostoTotal.setText("");
            TXTcostoTotal.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_TXTcostoTotalFocusGained

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel2MousePressed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged

    }//GEN-LAST:event_jPanel2MouseDragged

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21MouseClicked

    private void VisualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VisualizarMouseEntered
        // TODO add your handling code here:
        Visualizar.setForeground(Color.white);
    }//GEN-LAST:event_VisualizarMouseEntered

    private void VisualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VisualizarMouseExited
        // TODO add your handling code here:
        Visualizar.setForeground(Color.black);
    }//GEN-LAST:event_VisualizarMouseExited

    private void VisualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VisualizarMouseClicked
        // TODO add your handling code here:
        PanelProduce pr=new PanelProduce();
        if (pr.isVisible()){
            pr.setVisible(false);
        }
        else{
            pr.setVisible(true);
        }
    }//GEN-LAST:event_VisualizarMouseClicked
public void cargarCombosIniciales() {
    OrdenServicioDAO dao = new OrdenServicioDAO();

    comboPlacas.removeAllItems();
    comboPlacas.addItem("Seleccione una placa");
    for (String placa : dao.obtenerPlacas()) {
        comboPlacas.addItem(placa);
    }
    comboEmpleado.removeAllItems();
    comboEmpleado.addItem("Seleccione un empleado");
    for (String emp : dao.obtenerEmpleadosConEspecialidad()) {
        comboEmpleado.addItem(emp);
    }
}private void EditarOrdenDeServicio() {
    try {
        
        if (idOrdenCargada == null || idOrdenCargada.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione o busque primero la orden que desea editar.", 
                "Atención", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (Estados == null || Estados.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un estado de orden válido.", 
                "Atención", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String estadoSeleccionado = Estados.getSelectedItem().toString().trim();

        OrdenServicioDAO dao = new OrdenServicioDAO();
        orden_de_servicio orden = new orden_de_servicio();
        
        orden.setId_orden_serv(idOrdenCargada);
        orden.setEstadoorden_servi(estadoSeleccionado);

        orden.setFecha_ingreso(CalendarioFechaIngreso.getDate());
        if (CalendarioFechaEntrega != null && CalendarioFechaEntrega.getDate() != null) {
            orden.setFecha_entrega(CalendarioFechaEntrega.getDate());
        } else {
            orden.setFecha_entrega(null);
        }

        double costoTotal = 0.0;
        if (TXTcostoTotal != null && !TXTcostoTotal.getText().trim().isEmpty()) {
            try {
                costoTotal = Double.parseDouble(TXTcostoTotal.getText().trim());
            } catch (NumberFormatException nfe) {
                costoTotal = 0.0;
            }
        }
        orden.setCosto_total(costoTotal);

        if (comboPlacas != null && comboPlacas.getSelectedIndex() > 0) {
            String placa = comboPlacas.getSelectedItem().toString().trim();
            orden.setId_vehi(dao.obtenerIdVehiculoPorPlaca(placa));
        }

        if (comboEmpleado != null && comboEmpleado.getSelectedIndex() > 0) {
            String empleadoNombre = comboEmpleado.getSelectedItem().toString().trim();
            orden.setId_empleado(dao.obtenerIdEmpleadoPorNombre(empleadoNombre));
        }

        boolean actualizado = dao.actualizarOrdenServicio(orden);

        if (actualizado) {
            JOptionPane.showMessageDialog(this, 
                "La Orden de Servicio (" + idOrdenCargada + ") ha sido actualizada con éxito.", 
                "Actualización Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
            if (estadoSeleccionado.equalsIgnoreCase("Terminado") || estadoSeleccionado.equalsIgnoreCase("Finalizado")) {
                new Thread(() -> {
                    String[] datosPropietario = dao.obtenerDatosPropietarioPorVehiculo(orden.getId_vehi());
                    String nombrePropietario = datosPropietario[0];
                    String correoPropietario = datosPropietario[1];
                    Servidor_de_correos correo=new Servidor_de_correos();
                    if (correoPropietario != null && !correoPropietario.trim().isEmpty()) {
                        correo.enviarCorreoEstadoVehiculo(nombrePropietario, estadoSeleccionado, correoPropietario);
                    } else {
                        System.out.println("No se envió correo: El propietario no registra un correo válido.");
                    }
                }).start();
            }
            LimpiarDatos();

        } else {
            JOptionPane.showMessageDialog(this, 
                "No se pudo actualizar la orden de servicio en la base de datos.", 
                "Error de Actualización", 
                JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error inesperado al editar: " + e.getMessage(), 
            "Error General", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}    private void configurarModeloTablaRepuestosUsados() {
    DefaultTableModel modeloUsados = new DefaultTableModel(
        new Object [][] {},
        new String [] {
            "ID", "Nombre", "Cantidad", "Precio Unit.", "Subtotal"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    };
    tblRepuestosUsados.setModel(modeloUsados);
}
   

    public void LimpiarDatos() {
    if (TXTcostoTotal != null) {
        TXTcostoTotal.setText("0.00");
        TXTcostoTotal.setForeground(new Color(94, 94, 94));
    }
    if (Estados != null && Estados.getItemCount() > 0) Estados.setSelectedIndex(0);
    if (comboPlacas != null && comboPlacas.getItemCount() > 0) comboPlacas.setSelectedIndex(0);
    if (comboCliente != null && comboCliente.getItemCount() > 0) comboCliente.setSelectedIndex(0);
    if (comboEmpleado != null && comboEmpleado.getItemCount() > 0) comboEmpleado.setSelectedIndex(0);
    if (CalendarioFechaEntrega != null) CalendarioFechaEntrega.setDate(null);
    this.idOrdenCargada = null; 
    
    LimpiarDatosTablaDetalles();
}
    private void GuardarOrdenDeServicio() {
        try {
        if (comboPlacas.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione una placa de vehículo.", 
                "Atención", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (CalendarioFechaIngreso.getDate() == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione la fecha de ingreso.", 
                "Atención", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        OrdenServicioDAO dao = new OrdenServicioDAO();
        orden_de_servicio orden = new orden_de_servicio();
        String nuevoId = dao.generarSiguienteIdOrden();
        orden.setId_orden_serv(nuevoId);
        
        if (Estados != null && Estados.getSelectedItem() != null) {
            orden.setEstadoorden_servi(Estados.getSelectedItem().toString());
        } else {
            orden.setEstadoorden_servi("Ingresado"); 
        }
        orden.setFecha_ingreso(CalendarioFechaIngreso.getDate());
        
        if (CalendarioFechaEntrega != null && CalendarioFechaEntrega.getDate() != null) {
            orden.setFecha_entrega(CalendarioFechaEntrega.getDate());
        } else {
            orden.setFecha_entrega(null);
        }
        double costoTotal = 0.0;
        String textoCosto = TXTcostoTotal.getText().trim();
        if (!textoCosto.isEmpty()) {
            costoTotal = Double.parseDouble(textoCosto);
        }
        orden.setCosto_total(costoTotal);

        String placaSeleccionada = comboPlacas.getSelectedItem().toString().trim().toUpperCase();
        String idVehiculo = dao.obtenerIdVehiculoPorPlaca(placaSeleccionada);

        if (idVehiculo == null) {
            JOptionPane.showMessageDialog(this, 
                "No se encontró el registro del vehículo seleccionado en la base de datos.", 
                "Error de Referencia", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        orden.setId_vehi(idVehiculo);
        
        if (comboEmpleado != null && comboEmpleado.getSelectedIndex() > 0) {
            String empleadoTexto = comboEmpleado.getSelectedItem().toString().toUpperCase();
            String idEmpleado = dao.obtenerIdEmpleadoPorNombre(empleadoTexto);
        
            if (idEmpleado == null) {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró el ID del empleado seleccionado en la base de datos.", 
                    "Error de Empleado", 
                    JOptionPane.WARNING_MESSAGE);
                return; 
            }
        
            orden.setId_empleado(idEmpleado);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un empleado asignado.", 
                "Atención", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (comboPlacas == null || comboPlacas.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una placa de vehículo.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (comboEmpleado == null || comboEmpleado.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un empleado asignado.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (Estados == null || Estados.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un estado para la orden.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (CalendarioFechaIngreso == null || CalendarioFechaIngreso.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione la fecha de ingreso.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<DetalleRepuesto> listaDetalles = new ArrayList<>();
        javax.swing.table.DefaultTableModel modeloTablaRepuestos = (javax.swing.table.DefaultTableModel) tblRepuestosUsados.getModel();

        for (int i = 0; i < modeloTablaRepuestos.getRowCount(); i++) {
            String idRepuesto = modeloTablaRepuestos.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(modeloTablaRepuestos.getValueAt(i, 2).toString());
            double subtotal = Double.parseDouble(modeloTablaRepuestos.getValueAt(i, 4).toString());
            
            listaDetalles.add(new DetalleRepuesto(idRepuesto, cantidad, subtotal));
        }

            boolean guardadoExitoso = dao.guardarOrdenConDetalles(orden, listaDetalles);

            if (guardadoExitoso) {
                boolean detalleOk = guardarDetalleServicios(nuevoId);

                if (detalleOk) {
                    JOptionPane.showMessageDialog(this,
                            "Orden de Servicio y repuestos registrados con éxito.\nCódigo: " + nuevoId,
                            "Registro Exitoso",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "La orden y los repuestos se guardaron, pero hubo un problema al guardar los servicios.",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }

                LimpiarDatos();
                modeloTablaRepuestos.setRowCount(0);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo guardar la orden de servicio. Verifique la conexión o los datos.",
                        "Error de Guardado",
                        JOptionPane.ERROR_MESSAGE);
            }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "El costo total o los valores numéricos de los repuestos deben ser válidos.", 
            "Error de Formato", 
            JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Ocurrió un error inesperado: " + e.getMessage(), 
            "Error General", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }
    private final List<ItemServicioOrden> detalleServicios = new ArrayList<>();
    private DefaultTableModel modeloTablaServicios;

    private static class ItemServicioOrden {

        String idServicio;
        String nombre;
        float precioUnitario;
        int cantidad;

        ItemServicioOrden(String idServicio, String nombre, float precioUnitario, int cantidad) {
            this.idServicio = idServicio;
            this.nombre = nombre;
            this.precioUnitario = precioUnitario;
            this.cantidad = cantidad;
        }

        double getSubtotal() {
            return cantidad * precioUnitario;
        }
    }
    
    private void agregarServicioADetalle(Servicio s, int cantidadNueva) {
        if (s == null) {
            return;
        }
        ItemServicioOrden existente = null;
        for (ItemServicioOrden item : detalleServicios) {
            if (item.idServicio.equals(s.getId_servi())) {
                existente = item;
                break;
            }
        }
        if (existente != null) {
            existente.cantidad += cantidadNueva;
        } else {
            detalleServicios.add(new ItemServicioOrden(
                    s.getId_servi(), s.getNom_servicio(), s.getPrecio_del_servicio(), cantidadNueva));
        }
        actualizarTablaDetalleServicio();
    }

    private void actualizarTablaDetalleServicio() {
        modeloTablaServicios.setRowCount(0);
        for (ItemServicioOrden item : detalleServicios) {
            modeloTablaServicios.addRow(new Object[]{
                item.nombre, item.precioUnitario, item.cantidad, item.getSubtotal()
            });
        }
        recalcularSubtotalServicios();
    }
    private void LimpiarDatosTablaDetalles() {
        detalleServicios.clear();
        if (modeloTablaServicios != null) {
            actualizarTablaDetalleServicio();
        }
    }

    private void recalcularSubtotalServicios() {
        double total = 0;
        for (ItemServicioOrden item : detalleServicios) {
            total += item.getSubtotal();
        }
        lblSubTotalServicios.setText(String.format("%.2f", total));
    }
    private boolean guardarDetalleServicios(String idOrden) {
        DetalleOrdenServicioDAO dao = new DetalleOrdenServicioDAO();
        for (ItemServicioOrden item : detalleServicios) {
            String idDetalle = dao.generarNuevoId();
            boolean ok = dao.insertarDetalle(idDetalle, item.cantidad, item.getSubtotal(), item.idServicio, idOrden);
            if (!ok) {
                return false;
            }
        }
        return true;
    }
    public void calcularTotal() {
    try {
        String textoRepuestos = lblSubTotalServicios.getText().trim();
        String textoServicios = txtSubtotalRepuestos.getText().trim();
        textoRepuestos = textoRepuestos.replace("$", "").trim();
        textoServicios = textoServicios.replace("$", "").trim();

        double subtotalRepuestos = textoRepuestos.isEmpty() ? 0.0 : Double.parseDouble(textoRepuestos);
        double subtotalServicios = textoServicios.isEmpty() ? 0.0 : Double.parseDouble(textoServicios);

        double totalGeneral = subtotalRepuestos + subtotalServicios;

        TXTcostoTotal.setText(String.format(java.util.Locale.US, "%.2f", totalGeneral));

    } catch (NumberFormatException e) {
        TXTcostoTotal.setText("0.00");
    }
}

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Buscar;
    private com.toedter.calendar.JDateChooser CalendarioFechaEntrega;
    private com.toedter.calendar.JDateChooser CalendarioFechaIngreso;
    private javax.swing.JLabel DarDeBaja;
    private javax.swing.JLabel Editar;
    private javax.swing.JComboBox<String> Estados;
    private javax.swing.JLabel Guardar;
    private javax.swing.JLabel ImagenADD;
    private javax.swing.JLabel ImagenADD1;
    private javax.swing.JLabel ImagenDarBaja;
    private javax.swing.JLabel ImagenSAVE;
    private javax.swing.JLabel Nuevo;
    private javax.swing.JLabel Nuevo1;
    private javax.swing.JPanel PanelAgregarServicio;
    private javax.swing.JPanel PanelBuscar;
    private javax.swing.JPanel PanelEditar;
    private javax.swing.JPanel PanelEliminarServicio;
    private javax.swing.JPanel PanelGuardar;
    private javax.swing.JPanel PanelNuevo;
    private javax.swing.JTextField TXTcostoTotal;
    private javax.swing.JLabel Visualizar;
    private javax.swing.JButton btnAgregarRepuestos;
    private javax.swing.JButton btnEliminarRepuesto;
    private javax.swing.JComboBox<String> comboCliente;
    private javax.swing.JComboBox<String> comboEmpleado;
    private javax.swing.JComboBox<String> comboPlacas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblSubTotalServicios;
    private javax.swing.JTable tblDetalleRepuestos;
    private javax.swing.JTable tblDetalleServicio;
    private javax.swing.JTable tblRepuestosUsados;
    private javax.swing.JTextField txtBuscarRepueseto;
    private javax.swing.JTextField txtSubtotalRepuestos;
    // End of variables declaration//GEN-END:variables
}
