
package com.mycompany.proyecto_m2b.Vista;

import com.mycompany.proyecto_m2b.Controlador.ConexionBD;
import com.mycompany.proyecto_m2b.Controlador.RepuestoDAO;
import com.mycompany.proyecto_m2b.Controlador.Servidor_de_correos;
import com.mycompany.proyecto_m2b.modelo.MarcaRepuesto;
import com.mycompany.proyecto_m2b.modelo.Repuesto;
import com.mycompany.proyecto_m2b.modelo.TipoRepuesto;
import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class PanelRepuestos extends javax.swing.JPanel {
private java.sql.Connection miConexion;
private int filaSeleccionadaAnterior = -1;

    
public PanelRepuestos() {
    try {
        this.miConexion = ConexionBD.obtenerConexion();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos: " + e.getMessage());
    }
    initComponents();
    cargarCombos();
    txtIdRepuesto.setEditable(false); 
    txtIdRepuesto.setEnabled(false);
    generarIdAutomatico();
    
    cargarTablaRepuestos("");
    
    txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyReleased(java.awt.event.KeyEvent evt) {
            String textoBusqueda = txtBuscar.getText().trim();
            cargarTablaRepuestos(textoBusqueda);
        }
    }); 
    
    tblModificar.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int filaSeleccionada = tblModificar.getSelectedRow();
            
            if (filaSeleccionada == filaSeleccionadaAnterior && filaSeleccionada != -1) {
                tblModificar.clearSelection();
                limpiarCampos();              
                generarIdAutomatico();        
                filaSeleccionadaAnterior = -1; 
            } else if (filaSeleccionada != -1) {
                filaSeleccionadaAnterior = filaSeleccionada;
                
                txtIdRepuesto.setText(tblModificar.getValueAt(filaSeleccionada, 0).toString());
                txtNomRepuesto.setText(tblModificar.getValueAt(filaSeleccionada, 1).toString());
                txtCantidadMaxima.setText(tblModificar.getValueAt(filaSeleccionada, 2).toString());
                txtCantidadMinima.setText(tblModificar.getValueAt(filaSeleccionada, 3).toString());
                txtStockActual.setText(tblModificar.getValueAt(filaSeleccionada, 4).toString());
                txtPrecioBase.setText(tblModificar.getValueAt(filaSeleccionada, 5).toString());
                txtDescripRepuesto.setText(tblModificar.getValueAt(filaSeleccionada, 6).toString());
                
                String idTipo = tblModificar.getValueAt(filaSeleccionada, 7).toString();
                for (int i = 0; i < cbxTipoRepuesto.getItemCount(); i++) {
                    Object item = cbxTipoRepuesto.getItemAt(i);
                    if (item instanceof TipoRepuesto && ((TipoRepuesto) item).getIdTipRepuesto().equals(idTipo)) {
                        cbxTipoRepuesto.setSelectedIndex(i);
                        break;
                    }
                }
                
                String idMarca = tblModificar.getValueAt(filaSeleccionada, 8).toString();
                for (int i = 0; i < cbxMarcaRepuesto.getItemCount(); i++) {
                    Object item = cbxMarcaRepuesto.getItemAt(i);
                    if (item instanceof MarcaRepuesto && ((MarcaRepuesto) item).getIdMarcaRepuesto().equals(idMarca)) {
                        cbxMarcaRepuesto.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    });

    
    
        txtNomRepuesto.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validarNombreRepuesto(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validarNombreRepuesto(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validarNombreRepuesto(); }
        });
        
        txtStockActual.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validarStockActual(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validarStockActual(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validarStockActual(); }
        });

        txtCantidadMinima.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validarStockMinimo(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validarStockMinimo(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validarStockMinimo(); }
        });

        txtCantidadMaxima.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validarStockMaximo(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validarStockMaximo(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validarStockMaximo(); }
        });

        txtPrecioBase.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validarPrecioBase(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validarPrecioBase(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validarPrecioBase(); }
        });
        
        lblErrorTipoRepuesto.setText("");
        lblErrorStockActual.setText("");
        lblErrorStockMinimo.setText("");
        lblErrorStockMaximo.setText("");
        lblErrorPrecioBase.setText("");
    
}
    private void cargarCombos() {
        cbxTipoRepuesto.removeAllItems();
        cbxMarcaRepuesto.removeAllItems();

        RepuestoDAO dao = new RepuestoDAO();
        for (TipoRepuesto t : dao.obtenerTipos(miConexion)) {
            cbxTipoRepuesto.addItem(t);
        }
        for (MarcaRepuesto m : dao.obtenerMarcas(miConexion)) {
            cbxMarcaRepuesto.addItem(m);
        }
    }
    
    private void limpiarCampos() {
    txtIdRepuesto.setText("");
    txtNomRepuesto.setText("");
    txtStockActual.setText("");
    txtCantidadMinima.setText("");
    txtCantidadMaxima.setText("");
    txtPrecioBase.setText("");
    txtDescripRepuesto.setText("");
    generarIdAutomatico();

    if (cbxTipoRepuesto.getItemCount() > 0) {
        cbxTipoRepuesto.setSelectedIndex(0);
    }
    if (cbxMarcaRepuesto.getItemCount() > 0) {
        cbxMarcaRepuesto.setSelectedIndex(0);
    }

    txtIdRepuesto.requestFocus();
}
    
    private void generarIdAutomatico() {
    RepuestoDAO dao = new RepuestoDAO();
    String nuevoId = dao.obtenerSiguienteIdRepuesto(miConexion);
    txtIdRepuesto.setText(nuevoId);
}
    private boolean validarNombreRepuesto() {
        String texto = txtNomRepuesto.getText().trim();
        
        if (texto.isEmpty()) {
            lblErrorTipoRepuesto.setText("El campo no puede estar vacío.");
            return false;
        }
        
        if (texto.length() > 30) {
            lblErrorTipoRepuesto.setText("Máximo 30 caracteres.");
            return false;
        }
        
        if (!Character.isUpperCase(texto.charAt(0))) {
            lblErrorTipoRepuesto.setText("Debe iniciar con mayúscula.");
            return false;
        }
        
        if (texto.matches("\\d+")) {
            lblErrorTipoRepuesto.setText("No puede contener solo números.");
            return false;
        }
        
        if (texto.matches(".*(.)\\1{3,}.*")) {
            lblErrorTipoRepuesto.setText("Demasiadas letras repetidas.");
            return false;
        }
        
        String textoMinus = texto.toLowerCase();
        if (textoMinus.contains("asdf") || textoMinus.contains("ghjk") || textoMinus.contains("qwer") || textoMinus.contains("zxcv")) {
            lblErrorTipoRepuesto.setText("Texto no válido o aleatorio.");
            return false;
        }
        
        lblErrorTipoRepuesto.setText("");
        return true;
    }

    private boolean validarStockActual() {
        String texto = txtStockActual.getText().trim();
        if (texto.isEmpty()) {
            lblErrorStockActual.setText("El campo no puede estar vacío.");
            return false;
        }
        if (!texto.matches("\\d+")) {
            lblErrorStockActual.setText("Solo se permiten números enteros.");
            return false;
        }
        
        int stockActual = Integer.parseInt(texto);
        if (stockActual < 0) {
            lblErrorStockActual.setText("El stock no puede ser negativo.");
            return false;
        }
        
        lblErrorStockActual.setText("");
        return true;
    }

    private boolean validarStockMinimo() {
        String texto = txtCantidadMinima.getText().trim();
        if (texto.isEmpty()) {
            lblErrorStockMinimo.setText("El campo no puede estar vacío.");
            return false;
        }
        if (!texto.matches("\\d+")) {
            lblErrorStockMinimo.setText("Solo se permiten números enteros.");
            return false;
        }
        
        int stockMin = Integer.parseInt(texto);
        if (stockMin < 0) {
            lblErrorStockMinimo.setText("El stock mínimo no puede ser negativo.");
            return false;
        }

        String textoMax = txtCantidadMaxima.getText().trim();
        if (!textoMax.isEmpty() && textoMax.matches("\\d+")) {
            int stockMax = Integer.parseInt(textoMax);
            if (stockMin >= stockMax) {
                lblErrorStockMinimo.setText("Debe ser menor al stock máximo.");
                return false;
            }
        }

        lblErrorStockMinimo.setText("");
        return true;
    }

    private boolean validarStockMaximo() {
        String texto = txtCantidadMaxima.getText().trim();
        if (texto.isEmpty()) {
            lblErrorStockMaximo.setText("El campo no puede estar vacío.");
            return false;
        }
        if (!texto.matches("\\d+")) {
            lblErrorStockMaximo.setText("Solo se permiten números enteros.");
            return false;
        }
        
        int stockMax = Integer.parseInt(texto);
        if (stockMax <= 0) {
            lblErrorStockMaximo.setText("El stock máximo debe ser mayor a 0.");
            return false;
        }

        String textoMin = txtCantidadMinima.getText().trim();
        if (!textoMin.isEmpty() && textoMin.matches("\\d+")) {
            int stockMin = Integer.parseInt(textoMin);
            if (stockMax <= stockMin) {
                lblErrorStockMaximo.setText("Debe ser mayor al stock mínimo.");
                return false;
            }
        }

        lblErrorStockMaximo.setText("");
        return true;
    }

    private boolean validarPrecioBase() {
        String texto = txtPrecioBase.getText().trim().replace(',', '.');
        if (texto.isEmpty()) {
            lblErrorPrecioBase.setText("El campo no puede estar vacío.");
            return false;
        }
        if (!texto.matches("^\\d+(\\.\\d+)?$")) {
            lblErrorPrecioBase.setText("Ingrese un número válido (ej. 12.50).");
            return false;
        }
        
        double precio = Double.parseDouble(texto);
        if (precio <= 0) {
            lblErrorPrecioBase.setText("El precio debe ser mayor a 0.");
            return false;
        }

        lblErrorPrecioBase.setText("");
        return true;
    }
    
    private void cargarTablaRepuestos(String filtro) {
        try (Connection con = ConexionBD.obtenerConexion()) {
        RepuestoDAO dao = new RepuestoDAO();
        List<Repuesto> lista = dao.buscarRepuestosPorNombre(con, filtro);
        
        String[] columnas = {
            "ID", "Nombre", "Stock Máx", "Stock Mín", 
            "Stock Actual", "Precio Base", "Descripción", "Tipo", "Marca"
        };
        
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Repuesto r : lista) {
            Object[] fila = new Object[9];
            fila[0] = r.getIdRepuestos();
            fila[1] = r.getNomRepuesto();
            fila[2] = r.getCantidadMaxRepuesto();
            fila[3] = r.getCantidadMinRepuesto();
            fila[4] = r.getCantidadActualRepuesto();
            fila[5] = r.getPrecioRepuestoUnit();
            fila[6] = r.getDescripRepuesto();
            fila[7] = r.getIdTipRepuesto();
            fila[8] = r.getIdMarcaRepuesto();
            
            modelo.addRow(fila);
        }
        
        tblModificar.setModel(modelo);
                
        tblModificar.getColumnModel().getColumn(0).setMinWidth(0);
        tblModificar.getColumnModel().getColumn(0).setMaxWidth(0);
        tblModificar.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblModificar.getColumnModel().getColumn(0).setResizable(false);
        
        tblModificar.getColumnModel().getColumn(7).setMinWidth(0);
        tblModificar.getColumnModel().getColumn(7).setMaxWidth(0);
        tblModificar.getColumnModel().getColumn(7).setPreferredWidth(0);
        tblModificar.getColumnModel().getColumn(7).setResizable(false);

        tblModificar.getColumnModel().getColumn(8).setMinWidth(0);
        tblModificar.getColumnModel().getColumn(8).setMaxWidth(0);
        tblModificar.getColumnModel().getColumn(8).setPreferredWidth(0);
        tblModificar.getColumnModel().getColumn(8).setResizable(false);
        
    } catch (SQLException e) {
        System.err.println("Error al cargar todos los datos en la tabla: " + e.getMessage());
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtDescripRepuesto = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JPanel();
        Guardar = new javax.swing.JLabel();
        ImagenSAVE = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIdRepuesto = new javax.swing.JTextField();
        txtNomRepuesto = new javax.swing.JTextField();
        cbxTipoRepuesto = new javax.swing.JComboBox<>();
        cbxMarcaRepuesto = new javax.swing.JComboBox<>();
        txtStockActual = new javax.swing.JTextField();
        txtCantidadMinima = new javax.swing.JTextField();
        txtCantidadMaxima = new javax.swing.JTextField();
        txtPrecioBase = new javax.swing.JTextField();
        btnAgregarMarca = new javax.swing.JPanel();
        Nuevo2 = new javax.swing.JLabel();
        ImagenADD2 = new javax.swing.JLabel();
        btnAgregarTipo = new javax.swing.JPanel();
        Nuevo3 = new javax.swing.JLabel();
        ImagenADD3 = new javax.swing.JLabel();
        lblErrorTipoRepuesto = new javax.swing.JLabel();
        lblErrorStockActual = new javax.swing.JLabel();
        lblErrorStockMinimo = new javax.swing.JLabel();
        lblErrorStockMaximo = new javax.swing.JLabel();
        lblErrorPrecioBase = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblModificar = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JPanel();
        Editar3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnEnviarReporte = new javax.swing.JButton();

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

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("GESTIÓN DE REPUESTOS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1032, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, 50));

        jLabel5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("ID Repuesto:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Nombre Repuesto:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Tipo:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Marca:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 100, -1, -1));

        jLabel10.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Buscar");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        jLabel11.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("Cantidad Mínima:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 160, -1, 20));

        jLabel12.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setText("Descripción del repuesto:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jLabel13.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Cantidad Máxima:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 160, -1, -1));

        txtDescripRepuesto.setForeground(new java.awt.Color(153, 153, 153));
        txtDescripRepuesto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescripRepuestoFocusGained(evt);
            }
        });
        txtDescripRepuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtDescripRepuestoMousePressed(evt);
            }
        });
        jPanel1.add(txtDescripRepuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 690, 70));

        btnGuardar.setBackground(new java.awt.Color(242, 101, 34));
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });

        Guardar.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Guardar.setForeground(new java.awt.Color(255, 255, 255));
        Guardar.setText("Guardar");

        ImagenSAVE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save_22dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout btnGuardarLayout = new javax.swing.GroupLayout(btnGuardar);
        btnGuardar.setLayout(btnGuardarLayout);
        btnGuardarLayout.setHorizontalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGuardarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(ImagenSAVE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Guardar)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        btnGuardarLayout.setVerticalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnGuardarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Guardar)
                    .addComponent(ImagenSAVE))
                .addContainerGap())
        );

        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 320, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Precio Base:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        txtIdRepuesto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtIdRepuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 160, -1));

        txtNomRepuesto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtNomRepuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 160, -1));

        cbxTipoRepuesto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxTipoRepuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxTipoRepuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 70, 170, -1));

        cbxMarcaRepuesto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxMarcaRepuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxMarcaRepuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 100, 170, -1));

        txtStockActual.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtStockActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 170, -1));

        txtCantidadMinima.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtCantidadMinima, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 170, -1));

        txtCantidadMaxima.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtCantidadMaxima, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 160, 170, -1));

        txtPrecioBase.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txtPrecioBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 170, -1));

        btnAgregarMarca.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregarMarca.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        btnAgregarMarca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarMarcaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarMarcaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarMarcaMouseExited(evt);
            }
        });

        Nuevo2.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Nuevo2.setText("Nuevo");

        ImagenADD2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        ImagenADD2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImagenADD2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout btnAgregarMarcaLayout = new javax.swing.GroupLayout(btnAgregarMarca);
        btnAgregarMarca.setLayout(btnAgregarMarcaLayout);
        btnAgregarMarcaLayout.setHorizontalGroup(
            btnAgregarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAgregarMarcaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ImagenADD2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Nuevo2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnAgregarMarcaLayout.setVerticalGroup(
            btnAgregarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAgregarMarcaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnAgregarMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ImagenADD2)
                    .addComponent(Nuevo2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(btnAgregarMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 100, -1, -1));

        btnAgregarTipo.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregarTipo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        btnAgregarTipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarTipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarTipoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarTipoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarTipoMouseExited(evt);
            }
        });

        Nuevo3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Nuevo3.setText("Nuevo");

        ImagenADD3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout btnAgregarTipoLayout = new javax.swing.GroupLayout(btnAgregarTipo);
        btnAgregarTipo.setLayout(btnAgregarTipoLayout);
        btnAgregarTipoLayout.setHorizontalGroup(
            btnAgregarTipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAgregarTipoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ImagenADD3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Nuevo3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnAgregarTipoLayout.setVerticalGroup(
            btnAgregarTipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAgregarTipoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnAgregarTipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ImagenADD3)
                    .addComponent(Nuevo3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(btnAgregarTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 60, -1, -1));

        lblErrorTipoRepuesto.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        lblErrorTipoRepuesto.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorTipoRepuesto.setText("jLabel1");
        jPanel1.add(lblErrorTipoRepuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, 220, -1));

        lblErrorStockActual.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        lblErrorStockActual.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorStockActual.setText("jLabel1");
        jPanel1.add(lblErrorStockActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, -1, -1));

        lblErrorStockMinimo.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        lblErrorStockMinimo.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorStockMinimo.setText("jLabel2");
        jPanel1.add(lblErrorStockMinimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 190, -1, -1));

        lblErrorStockMaximo.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        lblErrorStockMaximo.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorStockMaximo.setText("jLabel9");
        jPanel1.add(lblErrorStockMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 190, -1, -1));

        lblErrorPrecioBase.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        lblErrorPrecioBase.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorPrecioBase.setText("jLabel14");
        jPanel1.add(lblErrorPrecioBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, -1, -1));

        tblModificar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tblModificar.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblModificar);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 1290, 370));

        jLabel14.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setText("Stock Actual:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 190, -1));

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarMouseExited(evt);
            }
        });

        Editar3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Editar3.setText("X  Eliminar");

        javax.swing.GroupLayout btnEliminarLayout = new javax.swing.GroupLayout(btnEliminar);
        btnEliminar.setLayout(btnEliminarLayout);
        btnEliminarLayout.setHorizontalGroup(
            btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnEliminarLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(Editar3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btnEliminarLayout.setVerticalGroup(
            btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEliminarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Editar3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, 120, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cute_mechanic_holding_wrench_cartoon_icon_illustration___Premium_Vector-removebg-preview.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 280, 110, 110));

        btnEnviarReporte.setText("Reporte Inventario");
        btnEnviarReporte.addActionListener(this::btnEnviarReporteActionPerformed);
        jPanel1.add(btnEnviarReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 420, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged

    }//GEN-LAST:event_jPanel2MouseDragged

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed

    }//GEN-LAST:event_jPanel2MousePressed

    private void txtDescripRepuestoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescripRepuestoFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDescripRepuestoFocusGained

    private void txtDescripRepuestoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDescripRepuestoMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDescripRepuestoMousePressed

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        boolean v1 = validarNombreRepuesto();
        boolean v2 = validarStockActual();
        boolean v3 = validarStockMinimo();
        boolean v4 = validarStockMaximo();
        boolean v5 = validarPrecioBase();

        if (!v1 || !v2 || !v3 || !v4 || !v5) {
            JOptionPane.showMessageDialog(this, "Por favor corrija los errores marcados en rojo.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return; 
        }
        
        try {
            String id = txtIdRepuesto.getText().trim();
            String nombre = txtNomRepuesto.getText().trim();
            
            TipoRepuesto tipoSel = (TipoRepuesto) cbxTipoRepuesto.getSelectedItem();
            MarcaRepuesto marcaSel = (MarcaRepuesto) cbxMarcaRepuesto.getSelectedItem();

            if (nombre.isEmpty() || tipoSel == null || marcaSel == null) {
                javax.swing.JOptionPane.showMessageDialog(
                    this, 
                    "Por favor complete el nombre, tipo y marca del repuesto.", 
                    "Campos Incompletos", 
                    javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int stockActual = Integer.parseInt(txtStockActual.getText().trim());
            int stockMin = Integer.parseInt(txtCantidadMinima.getText().trim());
            int stockMax = Integer.parseInt(txtCantidadMaxima.getText().trim());
            
            String textoPrecio = txtPrecioBase.getText().trim().replace(',', '.');
            double precio = Double.parseDouble(textoPrecio);

            String descripcion = txtDescripRepuesto.getText().trim();

            Repuesto repuesto = new Repuesto(
                id, 
                nombre, 
                stockMax, 
                stockMin, 
                stockActual, 
                precio, 
                descripcion, 
                tipoSel.getIdTipRepuesto(), 
                marcaSel.getIdMarcaRepuesto()
            );

            RepuestoDAO dao = new RepuestoDAO();
            
            boolean esActualizacion = false;
            List<Repuesto> todos = dao.obtenerRepuestos(miConexion);
            for (Repuesto r : todos) {
                if (r.getIdRepuestos().equals(id)) {
                    esActualizacion = true;
                    break;
                }
            }

            boolean operacionExitosa = false;
            if (esActualizacion) {
                operacionExitosa = dao.actualizarRepuesto(repuesto, miConexion);
            } else {
                operacionExitosa = dao.guardarRepuesto(repuesto, miConexion);
            }

            if (operacionExitosa) {
                javax.swing.JOptionPane.showMessageDialog(
                    this, 
                    esActualizacion ? "¡Repuesto actualizado exitosamente!" : "¡Repuesto registrado exitosamente!", 
                    "Éxito", 
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
                
                limpiarCampos(); 
                cargarTablaRepuestos("");
                txtBuscar.setText("");
            } else {
                javax.swing.JOptionPane.showMessageDialog(
                    this, 
                    "No se pudo completar la operación en la base de datos.", 
                    "Error de Operación", 
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(
                this, 
                "Asegúrese de ingresar números enteros en los Stocks y un valor numérico/decimal válido en el Precio (ej. 12.50).", 
                "Error de Formato Numérico", 
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        // TODO add your handling code here:
        btnGuardar.setBackground(new Color(227, 95, 32));
        Guardar.setForeground(new Color(217, 217, 192));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        // TODO add your handling code here:
        btnGuardar.setBackground(new Color(242,101,34));
        Guardar.setForeground(Color.white);
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnAgregarMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMarcaMouseClicked
        javax.swing.JTextField txtNombreMarca = new javax.swing.JTextField(20);

        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setBackground(java.awt.Color.WHITE);
        panel.setLayout(new java.awt.GridLayout(0, 1, 5, 5));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        panel.add(new javax.swing.JLabel("Ingrese el nombre de la nueva marca:"));
        panel.add(txtNombreMarca);

        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) null, "Nueva Marca", true);
        dialog.setUndecorated(true);
        dialog.setBackground(java.awt.Color.WHITE);

        javax.swing.JButton btnAceptar = new javax.swing.JButton("Aceptar");
        javax.swing.JButton btnCancelar = new javax.swing.JButton("Cancelar");
        
        final boolean[] confirmado = {false};

        btnAceptar.addActionListener(e -> {
            confirmado[0] = true;
            dialog.dispose();
        });

        btnCancelar.addActionListener(e -> {
            confirmado[0] = false;
            dialog.dispose();
        });

        javax.swing.JPanel panelBotones = new javax.swing.JPanel();
        panelBotones.setBackground(java.awt.Color.WHITE);
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        dialog.setLayout(new java.awt.BorderLayout());
        dialog.add(panel, java.awt.BorderLayout.CENTER);
        dialog.add(panelBotones, java.awt.BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (confirmado[0]) {
            String nombreMarca = txtNombreMarca.getText().trim();
            
            if (!nombreMarca.isEmpty()) {
                String idMarca = "MAR-" + (System.currentTimeMillis() % 10000);
                MarcaRepuesto nuevaMarca = new MarcaRepuesto(idMarca, nombreMarca);

                RepuestoDAO dao = new RepuestoDAO();
                if (dao.guardarMarca(nuevaMarca, miConexion)) {
                    cbxMarcaRepuesto.addItem(nuevaMarca);
                    cbxMarcaRepuesto.setSelectedItem(nuevaMarca);
                    JOptionPane.showMessageDialog(this, "Marca registrada con éxito.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "El nombre de la marca no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAgregarMarcaMouseClicked

    private void btnAgregarMarcaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMarcaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarMarcaMouseEntered

    private void btnAgregarMarcaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMarcaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarMarcaMouseExited

    private void btnAgregarTipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarTipoMouseClicked
        javax.swing.JTextField txtNombre = new javax.swing.JTextField(15);
        javax.swing.JTextField txtDescripcion = new javax.swing.JTextField(15);

        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridBagLayout());
        panel.setBackground(java.awt.Color.WHITE);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 15, 20));

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = java.awt.GridBagConstraints.WEST;
        javax.swing.JLabel lblNombre = new javax.swing.JLabel("Nombre del Tipo:");
        lblNombre.setForeground(java.awt.Color.DARK_GRAY);
        panel.add(lblNombre, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        javax.swing.JLabel lblDesc = new javax.swing.JLabel("Descripción:");
        lblDesc.setForeground(java.awt.Color.DARK_GRAY);
        panel.add(lblDesc, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtDescripcion, gbc);

        javax.swing.JButton btnAceptar = new javax.swing.JButton("Aceptar");
        javax.swing.JButton btnCancelar = new javax.swing.JButton("Cancelar");

        final int[] opcion = {javax.swing.JOptionPane.CANCEL_OPTION};

        javax.swing.JDialog dialogo;
        java.awt.Window ventanaPrincipal = javax.swing.SwingUtilities.getWindowAncestor(this);
        
        if (ventanaPrincipal instanceof java.awt.Frame) {
            dialogo = new javax.swing.JDialog((java.awt.Frame) ventanaPrincipal, "Registrar Nuevo Tipo de Repuesto", true);
        } else {
            dialogo = new javax.swing.JDialog((java.awt.Dialog) ventanaPrincipal, "Registrar Nuevo Tipo de Repuesto", true);
        }

        dialogo.setUndecorated(true); 
        
        dialogo.getRootPane().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180), 1));

        btnAceptar.addActionListener(e -> {
            opcion[0] = javax.swing.JOptionPane.OK_OPTION;
            dialogo.dispose();
        });

        btnCancelar.addActionListener(e -> {
            opcion[0] = javax.swing.JOptionPane.CANCEL_OPTION;
            dialogo.dispose();
        });

        javax.swing.JPanel panelBotones = new javax.swing.JPanel();
        panelBotones.setBackground(java.awt.Color.WHITE);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnAceptar);

        dialogo.setLayout(new java.awt.BorderLayout());
        dialogo.add(panel, java.awt.BorderLayout.CENTER);
        dialogo.add(panelBotones, java.awt.BorderLayout.SOUTH);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);

        dialogo.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                txtNombre.requestFocusInWindow();
            }
        });

        dialogo.setVisible(true);

        if (opcion[0] == javax.swing.JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();

            if (!nombre.isEmpty()) {
                String idTipo = "TIP-" + (System.currentTimeMillis() % 10000);
                
                if (descripcion.isEmpty()) {
                    descripcion = "Sin descripción";
                }

                TipoRepuesto nuevoTipo = new TipoRepuesto(idTipo, nombre, descripcion);

                RepuestoDAO dao = new RepuestoDAO();
                if (dao.guardarTipo(nuevoTipo, miConexion)) {
                    cbxTipoRepuesto.addItem(nuevoTipo);
                    cbxTipoRepuesto.setSelectedItem(nuevoTipo);
                    javax.swing.JOptionPane.showMessageDialog(this, "Tipo guardado exitosamente.");
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "El nombre del tipo no puede estar vacío.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAgregarTipoMouseClicked

    private void btnAgregarTipoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarTipoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarTipoMouseEntered

    private void btnAgregarTipoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarTipoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarTipoMouseExited

    private void ImagenADD2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImagenADD2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ImagenADD2KeyPressed

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        int filaSeleccionada = tblModificar.getSelectedRow();
    
    if (filaSeleccionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(
            this, 
            "Por favor, seleccione de la tabla el repuesto que desea eliminar.", 
            "Ninguna fila seleccionada", 
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }
        String idRepuesto = tblModificar.getValueAt(filaSeleccionada, 0).toString();
    String nombreRepuesto = tblModificar.getValueAt(filaSeleccionada, 1).toString();
    
    int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
        this, 
        "¿Está seguro de que desea eliminar el repuesto: " + nombreRepuesto + " (" + idRepuesto + ")?", 
        "Confirmar Eliminación", 
        javax.swing.JOptionPane.YES_NO_OPTION,
        javax.swing.JOptionPane.QUESTION_MESSAGE
    );
    
    if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
        RepuestoDAO dao = new RepuestoDAO();
        boolean eliminado = dao.eliminarRepuesto(idRepuesto, miConexion);
        
        if (eliminado) {
            javax.swing.JOptionPane.showMessageDialog(
                this, 
                "¡Repuesto eliminado exitosamente!", 
                "Éxito", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
            limpiarCampos();
            cargarTablaRepuestos("");
            txtBuscar.setText("");
        } else {
            javax.swing.JOptionPane.showMessageDialog(
                this, 
                "No se puede eliminar este repuesto porque ya está registrado en transacciones (como compras o ventas).", 
                "Operación Denegada", 
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        // TODO add your handling code here:
        btnEliminar.setBackground(new Color(219,219,219));
        btnEliminar.setForeground(new Color(66, 66, 66));
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        // TODO add your handling code here:
        btnEliminar.setBackground(Color.white);
        btnEliminar.setForeground(Color.black);
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnEnviarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarReporteActionPerformed
        try {
        java.sql.Connection con = ConexionBD.obtenerConexion();
        
        Servidor_de_correos servidor = new Servidor_de_correos();
        servidor.enviarReporteInventarioAuto(con);

        javax.swing.JOptionPane.showMessageDialog(this, 
            "El reporte de inventario se está guardando", 
            "Reporte en proceso", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);

    } catch (java.sql.SQLException e) {
        System.err.println("Error de conexión a la BD: " + e.getMessage());
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Error al conectar con la base de datos.", 
            "Error", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEnviarReporteActionPerformed
    
    public void LimpiarDatos (){

    }
    public void GuardarRepuestos (){

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Editar3;
    private javax.swing.JLabel Guardar;
    private javax.swing.JLabel ImagenADD2;
    private javax.swing.JLabel ImagenADD3;
    private javax.swing.JLabel ImagenSAVE;
    private javax.swing.JLabel Nuevo2;
    private javax.swing.JLabel Nuevo3;
    private javax.swing.JPanel btnAgregarMarca;
    private javax.swing.JPanel btnAgregarTipo;
    private javax.swing.JPanel btnEliminar;
    private javax.swing.JButton btnEnviarReporte;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.JComboBox<Object> cbxMarcaRepuesto;
    private javax.swing.JComboBox<Object> cbxTipoRepuesto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblErrorPrecioBase;
    private javax.swing.JLabel lblErrorStockActual;
    private javax.swing.JLabel lblErrorStockMaximo;
    private javax.swing.JLabel lblErrorStockMinimo;
    private javax.swing.JLabel lblErrorTipoRepuesto;
    private javax.swing.JTable tblModificar;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCantidadMaxima;
    private javax.swing.JTextField txtCantidadMinima;
    private javax.swing.JTextField txtDescripRepuesto;
    private javax.swing.JTextField txtIdRepuesto;
    private javax.swing.JTextField txtNomRepuesto;
    private javax.swing.JTextField txtPrecioBase;
    private javax.swing.JTextField txtStockActual;
    // End of variables declaration//GEN-END:variables
}
