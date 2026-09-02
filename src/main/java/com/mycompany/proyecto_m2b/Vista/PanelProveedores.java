
package com.mycompany.proyecto_m2b.Vista;

import com.mycompany.proyecto_m2b.Controlador.CompraDAO;
import com.mycompany.proyecto_m2b.Controlador.ConexionBD;
import com.mycompany.proyecto_m2b.Controlador.ProveedorDAO;
import com.mycompany.proyecto_m2b.Controlador.RepuestoDAO;
import com.mycompany.proyecto_m2b.Controlador.Servidor_de_correos;
import com.mycompany.proyecto_m2b.modelo.Proveedor;
import com.mycompany.proyecto_m2b.modelo.Repuesto;
import com.mycompany.proyecto_m2b.modelo.TipoProveedor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PanelProveedores extends javax.swing.JPanel {
    private Connection miConexion;
    private DefaultTableModel modeloTabla;
    private CompraDAO compraDAO;
    private ProveedorDAO proveedorDAO;
    private RepuestoDAO repuestoDAO;

    public PanelProveedores() {
        try {
        this.miConexion = ConexionBD.obtenerConexion();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos: " + e.getMessage());
    }

    this.compraDAO = new CompraDAO();
    this.proveedorDAO = new ProveedorDAO();
    this.repuestoDAO = new RepuestoDAO();
    initComponents();
    txtFechaCompra.setEnabled(false);
    txtTotalCompra.setEditable(false);
    txtFechaCompra.setDate(new Date());
    txtPrecioUnitarioCompra.setEditable(false);
    txtPrecioUnitarioCompra.setEnabled(false);
    txtIdCompra.setEditable(false); 
    txtIdCompra.setEnabled(false);

    inicializarTabla();
    cargarCombos();
    generarSiguienteIdCompra();
    
            txtCantidadCompra.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validarCantidadCompra(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validarCantidadCompra(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validarCantidadCompra(); }
        });
            lblErrorCantidadProveedor.setText("");

    
    }
    
    private boolean esCedulaOrRucValido(String numero) {
    if (numero == null || !numero.matches("\\d{10}|\\d{13}")) {
        return false;
    }

    if (numero.length() == 13) {
        if (numero.substring(10, 13).equals("000")) return false;
    }

    int provincia = Integer.parseInt(numero.substring(0, 2));
    if (!((provincia >= 1 && provincia <= 24) || provincia == 30)) {
        return false;
    }

    char tercerDigito = numero.charAt(2);
    
    if (tercerDigito >= '0' && tercerDigito <= '5') {
        String cedula = numero.substring(0, 10);
        if (cedula.matches("^(0{10}|1{10}|2{10}|3{10}|4{10}|5{10}|6{10}|7{10}|8{10}|9{10})$")) {
            return false;
        }
        return validarModulo10(cedula);
    }
    
    else if (tercerDigito == '9') {
        String rucPrivada = numero.substring(0, 13);
        return validarModulo11Privada(rucPrivada);
    }
    
    else if (tercerDigito == '6') {
        String rucPublica = numero.substring(0, 13);
        return validarModulo11Publica(rucPublica);
    }

    return false;
}

private boolean validarModulo10(String digitos) {
    int suma = 0;
    int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
    int verificador = Character.getNumericValue(digitos.charAt(9));

    for (int i = 0; i < 9; i++) {
        int valor = Character.getNumericValue(digitos.charAt(i)) * coeficientes[i];
        suma += (valor >= 10) ? (valor - 9) : valor;
    }

    int resultado = suma % 10 == 0 ? 0 : 10 - (suma % 10);
    return resultado == verificador;
}

private boolean validarModulo11Privada(String ruc) {
    int[] coeficientes = {4, 3, 2, 7, 6, 5, 4, 3, 2};
    int suma = 0;
    int verificador = Character.getNumericValue(ruc.charAt(9));

    for (int i = 0; i < 9; i++) {
        suma += Character.getNumericValue(ruc.charAt(i)) * coeficientes[i];
    }

    int resultado = suma % 11 == 0 ? 0 : 11 - (suma % 11);
    return resultado == verificador;
}

private boolean validarModulo11Publica(String ruc) {
    int[] coeficientes = {3, 2, 7, 6, 5, 4, 3, 2};
    int suma = 0;
    int verificador = Character.getNumericValue(ruc.charAt(8));

    for (int i = 0; i < 8; i++) {
        suma += Character.getNumericValue(ruc.charAt(i)) * coeficientes[i];
    }

    int resultado = suma % 11 == 0 ? 0 : 11 - (suma % 11);
    return resultado == verificador;
}

private boolean esNombreValido(String nombre) {
    if (nombre == null || nombre.trim().length() < 3) return false;

    char primeraLetra = nombre.trim().charAt(0);
    if (!Character.isUpperCase(primeraLetra)) {
        return false;
    }

    if (nombre.matches("(?i).*([a-zñ])\\1{3,}.*")) {
        return false; 
    }

    String limpio = nombre.toLowerCase().replaceAll("[^a-z]", "");
    if (limpio.isEmpty()) return false;

    return true;
}

private boolean esTelefonoEcuatorianoValido(String telefono) {
    if (telefono == null) return false;
    
    telefono = telefono.replaceAll("[\\s\\-\\(\\)]", "");

    if (telefono.matches("^09\\d{8}$")) {
        return true;
    }

    if (telefono.matches("^0[2-7]\\d{7}$")) {
        return true;
    }

    return false;
}
    
    private void inicializarTabla() {
    modeloTabla = new DefaultTableModel(
        new Object[]{"ID Repuesto", "Nombre Repuesto", "Cantidad", "Precio Unit.", "Subtotal"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tblDetalleCompra.setModel(modeloTabla);
}

public void cargarCombos() {
    cbxProveedor.removeAllItems();
    List<Proveedor> proveedores = proveedorDAO.obtenerProveedores(miConexion);
    for (Proveedor p : proveedores) {
        cbxProveedor.addItem(p);
    }

    cbxRepuesto.removeAllItems();
    List<Repuesto> repuestos = repuestoDAO.obtenerRepuestos(miConexion);
    for (Repuesto r : repuestos) {
        cbxRepuesto.addItem(r);
    }
}

private void generarSiguienteIdCompra() {
    txtIdCompra.setText(compraDAO.obtenerSiguienteIdCompra(miConexion));
}
    private void limpiarFormulario() {
    modeloTabla.setRowCount(0);
    txtTotalCompra.setText("");
    txtCantidadCompra.setText("");
    txtPrecioUnitarioCompra.setText("");
    generarSiguienteIdCompra();
}
    
    private void validarCantidadCompra() {
    String texto = txtCantidadCompra.getText().trim();
    
    if (texto.isEmpty()) {
        lblErrorCantidadProveedor.setText("La cantidad es obligatoria."); 
        return;
    }
    
    try {
        int cantidad = Integer.parseInt(texto);
        
        if (cantidad == 0) {
            lblErrorCantidadProveedor.setText("La cantidad no puede ser 0.");
        } else if (cantidad < 0) {
            lblErrorCantidadProveedor.setText("La cantidad no puede ser negativa.");
        } else {
            lblErrorCantidadProveedor.setText(""); 
        }
        
    } catch (NumberFormatException e) {
        lblErrorCantidadProveedor.setText("Ingrese solo números enteros válidos.");
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
        NombreVentanaProveedores = new javax.swing.JLabel();
        TituloFuncion1 = new javax.swing.JLabel();
        Proveedor = new javax.swing.JLabel();
        Factura = new javax.swing.JLabel();
        Fecha = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxProveedor = new javax.swing.JComboBox<>();
        TituloFuncion2 = new javax.swing.JLabel();
        Repuesto = new javax.swing.JLabel();
        Cantidad = new javax.swing.JLabel();
        Precio = new javax.swing.JLabel();
        txtCantidadCompra = new javax.swing.JTextField();
        cbxRepuesto = new javax.swing.JComboBox<>();
        txtIdCompra = new javax.swing.JTextField();
        txtFechaCompra = new com.toedter.calendar.JDateChooser();
        txtPrecioUnitarioCompra = new javax.swing.JTextField();
        btnAgregarItem = new javax.swing.JPanel();
        Nuevo = new javax.swing.JLabel();
        ImagenADD = new javax.swing.JLabel();
        btnGuardarCompra = new javax.swing.JPanel();
        Guardar = new javax.swing.JLabel();
        ImagenSAVE = new javax.swing.JLabel();
        btnEliminarFila = new javax.swing.JPanel();
        Buscar = new javax.swing.JLabel();
        btnAgregarProveedor = new javax.swing.JPanel();
        Nuevo1 = new javax.swing.JLabel();
        ImagenADD1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleCompra = new javax.swing.JTable();
        txtTotalCompra = new javax.swing.JTextField();
        lblErrorCantidadProveedor = new javax.swing.JLabel();
        Precio1 = new javax.swing.JLabel();

        Fondo.setBackground(new java.awt.Color(255, 255, 255));
        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BarraArriba.setBackground(new java.awt.Color(0, 0, 0));

        NombreVentanaProveedores.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        NombreVentanaProveedores.setForeground(new java.awt.Color(255, 255, 255));
        NombreVentanaProveedores.setText("GESTIÓN DE PROVEEDORES Y COMPRA DE REPUESTOS");

        javax.swing.GroupLayout BarraArribaLayout = new javax.swing.GroupLayout(BarraArriba);
        BarraArriba.setLayout(BarraArribaLayout);
        BarraArribaLayout.setHorizontalGroup(
            BarraArribaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarraArribaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(NombreVentanaProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(700, Short.MAX_VALUE))
        );
        BarraArribaLayout.setVerticalGroup(
            BarraArribaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarraArribaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(NombreVentanaProveedores)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        Fondo.add(BarraArriba, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, 60));

        TituloFuncion1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        TituloFuncion1.setForeground(new java.awt.Color(153, 153, 153));
        TituloFuncion1.setText("ENCABEZADO DEL ENVÍO / COMPRA");
        Fondo.add(TituloFuncion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        Proveedor.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        Proveedor.setForeground(new java.awt.Color(153, 153, 153));
        Proveedor.setText("Proveedor (RUC / Nombre)::");
        Fondo.add(Proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        Factura.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        Factura.setForeground(new java.awt.Color(153, 153, 153));
        Factura.setText("N° Factura / Referencia:");
        Fondo.add(Factura, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, -1, -1));

        Fecha.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        Fecha.setForeground(new java.awt.Color(153, 153, 153));
        Fecha.setText("Fecha de Envío:");
        Fondo.add(Fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 120, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/images (2).jpg"))); // NOI18N
        Fondo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 60, -1, -1));

        cbxProveedor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 2", "Item 3", "Item 4" }));
        cbxProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxProveedorMouseClicked(evt);
            }
        });
        Fondo.add(cbxProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 240, -1));

        TituloFuncion2.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        TituloFuncion2.setForeground(new java.awt.Color(153, 153, 153));
        TituloFuncion2.setText("DETALLE DE REPUESTOS SOLICITADOS");
        Fondo.add(TituloFuncion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, -1));

        Repuesto.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        Repuesto.setForeground(new java.awt.Color(153, 153, 153));
        Repuesto.setText("Repuesto:");
        Fondo.add(Repuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        Cantidad.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        Cantidad.setForeground(new java.awt.Color(153, 153, 153));
        Cantidad.setText("Cantidad:");
        Fondo.add(Cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, -1, -1));

        Precio.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        Precio.setForeground(new java.awt.Color(153, 153, 153));
        Precio.setText("Total:");
        Fondo.add(Precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 410, -1, -1));

        txtCantidadCompra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCantidadCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadCompraFocusLost(evt);
            }
        });
        Fondo.add(txtCantidadCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 350, 270, -1));

        cbxRepuesto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxRepuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 2", "Item 3", "Item 4" }));
        cbxRepuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxRepuestoMouseClicked(evt);
            }
        });
        cbxRepuesto.addActionListener(this::cbxRepuestoActionPerformed);
        Fondo.add(cbxRepuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 240, -1));

        txtIdCompra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdCompra.addActionListener(this::txtIdCompraActionPerformed);
        Fondo.add(txtIdCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, 200, -1));
        Fondo.add(txtFechaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 160, 200, -1));

        txtPrecioUnitarioCompra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPrecioUnitarioCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrecioUnitarioCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecioUnitarioCompraFocusLost(evt);
            }
        });
        Fondo.add(txtPrecioUnitarioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 350, 270, -1));

        btnAgregarItem.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregarItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        btnAgregarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarItemMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarItemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarItemMouseExited(evt);
            }
        });

        Nuevo.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Nuevo.setText("Agregar");

        ImagenADD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout btnAgregarItemLayout = new javax.swing.GroupLayout(btnAgregarItem);
        btnAgregarItem.setLayout(btnAgregarItemLayout);
        btnAgregarItemLayout.setHorizontalGroup(
            btnAgregarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAgregarItemLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(ImagenADD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Nuevo)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        btnAgregarItemLayout.setVerticalGroup(
            btnAgregarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAgregarItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnAgregarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Nuevo)
                    .addComponent(ImagenADD))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        Fondo.add(btnAgregarItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 340, -1, -1));

        btnGuardarCompra.setBackground(new java.awt.Color(242, 101, 34));
        btnGuardarCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        btnGuardarCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarCompraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarCompraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarCompraMouseExited(evt);
            }
        });

        Guardar.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Guardar.setForeground(new java.awt.Color(255, 255, 255));
        Guardar.setText("Guardar");

        ImagenSAVE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save_22dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout btnGuardarCompraLayout = new javax.swing.GroupLayout(btnGuardarCompra);
        btnGuardarCompra.setLayout(btnGuardarCompraLayout);
        btnGuardarCompraLayout.setHorizontalGroup(
            btnGuardarCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGuardarCompraLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(ImagenSAVE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Guardar)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        btnGuardarCompraLayout.setVerticalGroup(
            btnGuardarCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnGuardarCompraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(btnGuardarCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Guardar)
                    .addComponent(ImagenSAVE))
                .addContainerGap())
        );

        Fondo.add(btnGuardarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));

        btnEliminarFila.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminarFila.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        btnEliminarFila.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarFila.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarFilaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarFilaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarFilaMouseExited(evt);
            }
        });

        Buscar.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Buscar.setForeground(new java.awt.Color(255, 0, 0));
        Buscar.setText("X  Eliminar");

        javax.swing.GroupLayout btnEliminarFilaLayout = new javax.swing.GroupLayout(btnEliminarFila);
        btnEliminarFila.setLayout(btnEliminarFilaLayout);
        btnEliminarFilaLayout.setHorizontalGroup(
            btnEliminarFilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEliminarFilaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Buscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnEliminarFilaLayout.setVerticalGroup(
            btnEliminarFilaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnEliminarFilaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Buscar)
                .addContainerGap())
        );

        Fondo.add(btnEliminarFila, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 750, 110, -1));

        btnAgregarProveedor.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregarProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));
        btnAgregarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarProveedorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarProveedorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarProveedorMouseExited(evt);
            }
        });

        Nuevo1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        Nuevo1.setText("Nuevo");

        ImagenADD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout btnAgregarProveedorLayout = new javax.swing.GroupLayout(btnAgregarProveedor);
        btnAgregarProveedor.setLayout(btnAgregarProveedorLayout);
        btnAgregarProveedorLayout.setHorizontalGroup(
            btnAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAgregarProveedorLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(ImagenADD1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Nuevo1)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        btnAgregarProveedorLayout.setVerticalGroup(
            btnAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAgregarProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ImagenADD1)
                    .addComponent(Nuevo1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Fondo.add(btnAgregarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        tblDetalleCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Repuesto", "Nombre Repuesto", "Cantidad", "Precio Unit.", "Subtotal"
            }
        ));
        jScrollPane1.setViewportView(tblDetalleCompra);

        Fondo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 1310, 210));

        txtTotalCompra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Fondo.add(txtTotalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 440, 110, -1));

        lblErrorCantidadProveedor.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        lblErrorCantidadProveedor.setForeground(new java.awt.Color(255, 51, 51));
        lblErrorCantidadProveedor.setText("jLabel1");
        Fondo.add(lblErrorCantidadProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 390, -1, -1));

        Precio1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        Precio1.setForeground(new java.awt.Color(153, 153, 153));
        Precio1.setText("Precio Unitario:");
        Fondo.add(Precio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadCompraFocusGained
        if (txtCantidadCompra.getText().equals("Ingrese la cantidad a solicitar")) {
            txtCantidadCompra.setText("");
            txtCantidadCompra.setForeground(java.awt.Color.BLACK);
        }
    }//GEN-LAST:event_txtCantidadCompraFocusGained

    private void txtCantidadCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadCompraFocusLost
        if (txtCantidadCompra.getText().trim().isEmpty()) {
            txtCantidadCompra.setText("Ingrese la cantidad a solicitar");
            txtCantidadCompra.setForeground(java.awt.Color.GRAY);
        }
    }//GEN-LAST:event_txtCantidadCompraFocusLost

    private void txtIdCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCompraActionPerformed

    private void txtPrecioUnitarioCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioUnitarioCompraFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioUnitarioCompraFocusGained

    private void txtPrecioUnitarioCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioUnitarioCompraFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioUnitarioCompraFocusLost

    private void btnAgregarItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarItemMouseClicked
        // TODO add your handling code here:
        try {
        Repuesto repuestoSel = (Repuesto) cbxRepuesto.getSelectedItem();
        int cantidad = Integer.parseInt(txtCantidadCompra.getText().trim());
        double precio = Double.parseDouble(txtPrecioUnitarioCompra.getText().trim().replace(',', '.'));

        if (repuestoSel == null || cantidad <= 0 || precio <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un repuesto e ingrese valores válidos.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double subtotal = cantidad * precio;

        modeloTabla.addRow(new Object[]{
            repuestoSel.getIdRepuestos(),
            repuestoSel.getNomRepuesto(),
            cantidad,
            precio,
            subtotal
        });

        calcularTotal();

        txtCantidadCompra.setText("");
        txtPrecioUnitarioCompra.setText("");

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Ingrese una cantidad y precio válidos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void calcularTotal() {
    double total = 0.0;
    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
        total += (double) modeloTabla.getValueAt(i, 4);
    }
    txtTotalCompra.setText(String.format("%.2f", total).replace(',', '.'));
    }//GEN-LAST:event_btnAgregarItemMouseClicked

    private void btnAgregarItemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarItemMouseEntered
        // TODO add your handling code here:
        btnAgregarItem.setBackground(new java.awt.Color(219,219,219));
        Nuevo.setForeground(new java.awt.Color(66, 66, 66));
    }//GEN-LAST:event_btnAgregarItemMouseEntered

    private void btnAgregarItemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarItemMouseExited
        // TODO add your handling code here:
        btnAgregarItem.setBackground(java.awt.Color.white);
        Nuevo.setForeground(java.awt.Color.black);
    }//GEN-LAST:event_btnAgregarItemMouseExited

    private void btnGuardarCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCompraMouseClicked
        if (modeloTabla.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Debe agregar al menos un repuesto a la compra.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Proveedor provSel = (Proveedor) cbxProveedor.getSelectedItem();
    if (provSel == null) {
        JOptionPane.showMessageDialog(this, "Seleccione un proveedor.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String idCompra = txtIdCompra.getText().trim();
    Date fechaSeleccionada = txtFechaCompra.getDate();
    String fecha = "";
    if (fechaSeleccionada != null) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        fecha = sdf.format(fechaSeleccionada);
    }
    double total = Double.parseDouble(txtTotalCompra.getText().trim().replace(',', '.'));

    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
        String idRepuesto = (String) modeloTabla.getValueAt(i, 0);
        int cantidadAComprar = (int) modeloTabla.getValueAt(i, 2);
        String nombreRepuesto = (String) modeloTabla.getValueAt(i, 1);

        boolean stockValido = compraDAO.validarStockMaximo(idRepuesto, cantidadAComprar, miConexion);

        if (!stockValido) {
            JOptionPane.showMessageDialog(this, 
                "La cantidad a comprar del repuesto '" + nombreRepuesto + "' excede el stock máximo permitido en el inventario.", 
                "Límite de Stock Superado", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    if (compraDAO.guardarEncabezadoCompra(idCompra, fecha, total, provSel.getIdProveedor(), miConexion)) {

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String idDetalle = "DET-" + (int)(Math.random() * 90000 + 10000); 
            String idRepuesto = (String) modeloTabla.getValueAt(i, 0);
            int cantidad = (int) modeloTabla.getValueAt(i, 2);
            double subtotal = (double) modeloTabla.getValueAt(i, 4);

            compraDAO.guardarDetalleCompra(idDetalle, cantidad, subtotal, idRepuesto, idCompra, miConexion);
            compraDAO.actualizarStockRepuesto(idRepuesto, cantidad, miConexion);
        }

        Servidor_de_correos servidorCorreos = new Servidor_de_correos();
        servidorCorreos.enviarBorradorCompraProveedor(idCompra, fecha, total, provSel.getNomEmpresa(), tblDetalleCompra);

        JOptionPane.showMessageDialog(this, "¡Compra registrada, stock actualizado y correo enviado exitosamente!");
        limpiarFormulario();
    } else {
        JOptionPane.showMessageDialog(this, "Error al guardar la compra.", "Error SQL", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnGuardarCompraMouseClicked

    private void btnGuardarCompraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCompraMouseEntered
        // TODO add your handling code here:
        btnGuardarCompra.setBackground(new java.awt.Color(227, 95, 32));
        Guardar.setForeground(new java.awt.Color(217, 217, 192));
    }//GEN-LAST:event_btnGuardarCompraMouseEntered

    private void btnGuardarCompraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCompraMouseExited
        // TODO add your handling code here:
        btnGuardarCompra.setBackground(new java.awt.Color(242,101,34));
        Guardar.setForeground(java.awt.Color.white);
    }//GEN-LAST:event_btnGuardarCompraMouseExited

    private void btnEliminarFilaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarFilaMouseEntered
        // TODO add your handling code here:
        btnEliminarFila.setBackground(new java.awt.Color(219,219,219));
        Buscar.setForeground(new java.awt.Color(66, 66, 66));
    }//GEN-LAST:event_btnEliminarFilaMouseEntered

    private void btnEliminarFilaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarFilaMouseExited
        // TODO add your handling code here:
        btnEliminarFila.setBackground(java.awt.Color.white);
        Buscar.setForeground(java.awt.Color.black);
    }//GEN-LAST:event_btnEliminarFilaMouseExited

    private void btnAgregarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProveedorMouseClicked
        JTextField txtRuc = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtTelefono = new JTextField();
        JComboBox<TipoProveedor> cbxTipoProvPop = new JComboBox<>();

    Runnable cargarTipos = () -> {
        cbxTipoProvPop.removeAllItems();
        for (TipoProveedor tp : proveedorDAO.obtenerTiposProveedor(miConexion)) {
            cbxTipoProvPop.addItem(tp);
        }
    };

    cargarTipos.run();

    JLabel lblErrorRuc = new JLabel(" ");
    lblErrorRuc.setForeground(Color.RED);
    lblErrorRuc.setFont(new Font("Arial", Font.PLAIN, 11));

    JLabel lblErrorNombre = new JLabel(" ");
    lblErrorNombre.setForeground(Color.RED);
    lblErrorNombre.setFont(new Font("Arial", Font.PLAIN, 11));

    JLabel lblErrorTelefono = new JLabel(" ");
    lblErrorTelefono.setForeground(Color.RED);
    lblErrorTelefono.setFont(new Font("Arial", Font.PLAIN, 11));

    JPanel panel = new JPanel(new GridLayout(8, 2, 5, 2));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    panel.setPreferredSize(new Dimension(640, 320));

    JLabel lblRuc = new JLabel("RUC / Identificación:");
    lblRuc.setForeground(Color.BLACK);
    panel.add(lblRuc);
    panel.add(txtRuc);
    panel.add(new JLabel("")); 
    panel.add(lblErrorRuc);

    JLabel lblNombre = new JLabel("Nombre Empresa:");
    lblNombre.setForeground(Color.BLACK);
    panel.add(lblNombre);
    panel.add(txtNombre);
    panel.add(new JLabel("")); 
    panel.add(lblErrorNombre);

    JLabel lblTelefono = new JLabel("Teléfono:");
    lblTelefono.setForeground(Color.BLACK);
    panel.add(lblTelefono);
    panel.add(txtTelefono);
    panel.add(new JLabel("")); 
    panel.add(lblErrorTelefono);

    JLabel lblTipo = new JLabel("Tipo de Proveedor:");
    lblTipo.setForeground(Color.BLACK);
    panel.add(lblTipo);

    JPanel panelTipo = new JPanel(new BorderLayout(5, 0));
    panelTipo.setBackground(Color.WHITE);
    panelTipo.add(cbxTipoProvPop, BorderLayout.CENTER);

    JPanel panelBotonesTipoAcciones = new JPanel(new GridLayout(1, 2, 4, 0));
    panelBotonesTipoAcciones.setBackground(Color.WHITE);

    JButton btnNuevoTipo = new JButton("+ Tipo");
    JButton btnModificarTipo = new JButton("Modificar");
    
    panelBotonesTipoAcciones.add(btnNuevoTipo);
    panelBotonesTipoAcciones.add(btnModificarTipo);
    
    panelTipo.add(panelBotonesTipoAcciones, BorderLayout.EAST);
    panel.add(panelTipo);
    
    panel.add(new JLabel(""));
    panel.add(new JLabel(""));

    JDialog dialogProveedor = new JDialog((java.awt.Frame) null, "Gestionar Proveedor", true);
    dialogProveedor.setUndecorated(true);
    dialogProveedor.setBackground(Color.WHITE);

    final Proveedor[] proveedorEnEdicion = {null};

    JButton btnModificar = new JButton("Modificar Proveedor");
    JButton btnAceptarProv = new JButton("Aceptar");
    JButton btnCancelarProv = new JButton("Cancelar");
    final boolean[] confirmadoProv = {false};

    txtRuc.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { validar(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { validar(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { validar(); }
        private void validar() {
            String texto = txtRuc.getText().trim();
            if (texto.isEmpty()) {
                lblErrorRuc.setText("El RUC/Cédula no puede estar vacío.");
            } else if (!texto.matches("\\d+")) {
                lblErrorRuc.setText("Solo se permiten números.");
            } else if (texto.length() != 10 && texto.length() != 13) {
                lblErrorRuc.setText("Debe tener 10 (cédula) o 13 (RUC) dígitos.");
            } else {
                lblErrorRuc.setText(" "); 
            }
        }
    });

    txtNombre.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { validar(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { validar(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { validar(); }
        private void validar() {
            String texto = txtNombre.getText().trim();
            boolean tieneLetrasRepetidas = texto.matches(".*(.)\\1{3,}.*");

            if (texto.isEmpty()) {
                lblErrorNombre.setText("El nombre de la empresa es obligatorio.");
            } else if (texto.length() < 3) {
                lblErrorNombre.setText("El nombre es muy corto.");
            } else if (tieneLetrasRepetidas) {
                lblErrorNombre.setText("Ingrese un nombre de empresa válido.");
            } else {
                lblErrorNombre.setText(" "); 
            }
        }
    });

    txtTelefono.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { validar(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { validar(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { validar(); }
        private void validar() {
            String texto = txtTelefono.getText().trim();
            if (texto.isEmpty()) {
                lblErrorTelefono.setText("El teléfono es obligatorio.");
            } else if (!texto.matches("[0-9+\\-\\s]+")) {
                lblErrorTelefono.setText("Solo números o formato telefónico válido.");
            } else if (texto.replaceAll("[^0-9]", "").length() < 7) {
                lblErrorTelefono.setText("Número de teléfono incompleto.");
            } else {
                lblErrorTelefono.setText(" "); 
            }
        }
    });

    btnModificar.addActionListener(e -> {
        JDialog dialogSelector = new JDialog(dialogProveedor, "Lista de Proveedores", true);
        dialogSelector.setSize(700, 350);
        dialogSelector.setLocationRelativeTo(dialogProveedor);
        dialogSelector.setLayout(new BorderLayout());

        final Runnable[] cargarTablaSelector = new Runnable[1];

        cargarTablaSelector[0] = () -> {
            dialogSelector.getContentPane().removeAll();
            
            List<Proveedor> listaProveedores = proveedorDAO.obtenerProveedores(miConexion);
            String[] columnas = {"ID", "RUC", "Nombre Empresa", "Teléfono", "Tipo ID"};
            Object[][] datos = new Object[listaProveedores.size()][5];

            for (int i = 0; i < listaProveedores.size(); i++) {
                Proveedor p = listaProveedores.get(i);
                datos[i][0] = p.getIdProveedor();
                datos[i][1] = p.getRucProveedor();
                datos[i][2] = p.getNomEmpresa();
                datos[i][3] = p.getNumTelelEmpresa();
                datos[i][4] = p.getIdTipoProveedor();
            }

            javax.swing.JTable tabla = new javax.swing.JTable(datos, columnas);
            javax.swing.table.TableRowSorter<javax.swing.table.TableModel> sorter = new javax.swing.table.TableRowSorter<>(tabla.getModel());
            tabla.setRowSorter(sorter);

            JPanel panelBusqueda = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));
            panelBusqueda.setBackground(Color.WHITE);
            JLabel lblBuscar = new JLabel("Buscar (ID o Nombre):");
            JTextField txtBuscar = new JTextField(25);
            panelBusqueda.add(lblBuscar);
            panelBusqueda.add(txtBuscar);

            txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
                private void filtrar() {
                    String texto = txtBuscar.getText().trim();
                    if (texto.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + texto, 0, 2));
                    }
                }
            });

            dialogSelector.add(panelBusqueda, BorderLayout.NORTH);
            dialogSelector.add(new javax.swing.JScrollPane(tabla), BorderLayout.CENTER);

            JButton btnSeleccionar = new JButton("Modificar Seleccionada");
            btnSeleccionar.addActionListener(ex -> {
                int filaVisual = tabla.getSelectedRow();
                if (filaVisual >= 0) {
                    int filaReal = tabla.convertRowIndexToModel(filaVisual);
                    proveedorEnEdicion[0] = listaProveedores.get(filaReal);
                    
                    txtRuc.setText(proveedorEnEdicion[0].getRucProveedor());
                    txtRuc.setEnabled(false);
                    
                    txtNombre.setText(proveedorEnEdicion[0].getNomEmpresa());
                    txtTelefono.setText(proveedorEnEdicion[0].getNumTelelEmpresa());

                    for (int i = 0; i < cbxTipoProvPop.getItemCount(); i++) {
                        TipoProveedor tp = cbxTipoProvPop.getItemAt(i);
                        if (tp.getIdTipoProveedor().equals(proveedorEnEdicion[0].getIdTipoProveedor())) {
                            cbxTipoProvPop.setSelectedIndex(i);
                            break;
                        }
                    }
                    dialogSelector.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialogSelector, "Seleccione una fila de la tabla.");
                }
            });

            JButton btnEliminarProv = new JButton("Eliminar Seleccionada");
            btnEliminarProv.addActionListener(ex -> {
                int filaVisual = tabla.getSelectedRow();
                if (filaVisual >= 0) {
                    int filaReal = tabla.convertRowIndexToModel(filaVisual);
                    Proveedor pSel = listaProveedores.get(filaReal);
                    int confirm = JOptionPane.showConfirmDialog(dialogSelector, "¿Está seguro de eliminar este proveedor?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean eliminado = proveedorDAO.eliminarProveedor(pSel.getIdProveedor(), miConexion);
                        if (eliminado) {
                            JOptionPane.showMessageDialog(dialogSelector, "Proveedor eliminado correctamente.");
                            cargarTablaSelector[0].run();
                        } else {
                            JOptionPane.showMessageDialog(dialogSelector, "No se puede eliminar porque está registrado en compras.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(dialogSelector, "Seleccione una fila de la tabla.");
                }
            });

            JPanel panelSel = new JPanel();
            panelSel.setBackground(Color.WHITE);
            panelSel.add(btnSeleccionar);
            panelSel.add(btnEliminarProv);
            dialogSelector.add(panelSel, BorderLayout.SOUTH);
            dialogSelector.revalidate();
            dialogSelector.repaint();
        };

        cargarTablaSelector[0].run();
        dialogSelector.setVisible(true);
    });

    btnNuevoTipo.addActionListener(e -> {
        String nuevoId = proveedorDAO.obtenerSiguienteIdTipoProveedor(miConexion);
        JTextField txtIdTipo = new JTextField(nuevoId);
        txtIdTipo.setEnabled(false);
        JTextField txtNomTip = new JTextField();
        JTextField txtDescripTip = new JTextField();

        JLabel lblErrorNomTip = new JLabel(" ");
        lblErrorNomTip.setForeground(Color.RED);
        lblErrorNomTip.setFont(new Font("Arial", Font.PLAIN, 11));

        JLabel lblErrorDescripTip = new JLabel(" ");
        lblErrorDescripTip.setForeground(Color.RED);
        lblErrorDescripTip.setFont(new Font("Arial", Font.PLAIN, 11));

        txtNomTip.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
            private void validar() {
                String texto = txtNomTip.getText().trim();
                boolean repetidas = texto.matches(".*(.)\\1{3,}.*");
                if (texto.isEmpty()) {
                    lblErrorNomTip.setText("El nombre del tipo es obligatorio.");
                } else if (texto.length() < 3) {
                    lblErrorNomTip.setText("El nombre es muy corto.");
                } else if (repetidas) {
                    lblErrorNomTip.setText("Ingrese un nombre válido.");
                } else {
                    lblErrorNomTip.setText(" ");
                }
            }
        });

        txtDescripTip.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
            private void validar() {
                String texto = txtDescripTip.getText().trim();
                boolean repetidas = texto.matches(".*(.)\\1{3,}.*");
                if (!texto.isEmpty() && texto.length() < 3) {
                    lblErrorDescripTip.setText("La descripción es muy corta.");
                } else if (repetidas) {
                    lblErrorDescripTip.setText("Ingrese una descripción válida.");
                } else {
                    lblErrorDescripTip.setText(" ");
                }
            }
        });

        JPanel panelTipoDialog = new JPanel(new GridLayout(0, 1, 5, 2));
        panelTipoDialog.setBackground(Color.WHITE);
        panelTipoDialog.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelTipoDialog.add(new JLabel("ID Tipo Proveedor:"));
        panelTipoDialog.add(txtIdTipo);
        panelTipoDialog.add(new JLabel("Nombre del Tipo:"));
        panelTipoDialog.add(txtNomTip);
        panelTipoDialog.add(lblErrorNomTip);
        panelTipoDialog.add(new JLabel("Descripción:"));
        panelTipoDialog.add(txtDescripTip);
        panelTipoDialog.add(lblErrorDescripTip);

        JDialog dialogTipo = new JDialog(dialogProveedor, "Registrar Nuevo Tipo de Proveedor", true);
        dialogTipo.setUndecorated(true);
        dialogTipo.setBackground(Color.WHITE);

        JButton btnAceptarTipo = new JButton("Aceptar");
        JButton btnCancelarTipo = new JButton("Cancelar");
        final boolean[] confirmadoTipo = {false};

        btnAceptarTipo.addActionListener(ex -> {
            if (!lblErrorNomTip.getText().trim().isEmpty() || !lblErrorDescripTip.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogTipo, "Por favor, corrija los errores marcados en rojo.", "Campos Inválidos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            confirmadoTipo[0] = true;
            dialogTipo.dispose();
        });

        btnCancelarTipo.addActionListener(ex -> {
            confirmadoTipo[0] = false;
            dialogTipo.dispose();
        });

        JPanel panelBotonesTipo = new JPanel();
        panelBotonesTipo.setBackground(Color.WHITE);
        panelBotonesTipo.add(btnAceptarTipo);
        panelBotonesTipo.add(btnCancelarTipo);

        dialogTipo.setLayout(new BorderLayout());
        dialogTipo.add(panelTipoDialog, BorderLayout.CENTER);
        dialogTipo.add(panelBotonesTipo, BorderLayout.SOUTH);
        dialogTipo.pack();
        dialogTipo.setLocationRelativeTo(dialogProveedor);
        dialogTipo.setVisible(true);

        if (confirmadoTipo[0]) {
            String idTipo = txtIdTipo.getText().trim();
            String nomTip = txtNomTip.getText().trim();
            String descrip = txtDescripTip.getText().trim();

            if (!idTipo.isEmpty() && !nomTip.isEmpty()) {
                TipoProveedor nuevoTp = new TipoProveedor(idTipo, nomTip, descrip);
                if (proveedorDAO.guardarTipoProveedor(nuevoTp, miConexion)) {
                    JOptionPane.showMessageDialog(dialogTipo, "Tipo de proveedor guardado con éxito.");
                    cargarTipos.run();
                    cbxTipoProvPop.setSelectedItem(nuevoTp);
                } else {
                    JOptionPane.showMessageDialog(dialogTipo, "Error al guardar el tipo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    });

    btnModificarTipo.addActionListener(e -> {
        JDialog dialogSelectorTipo = new JDialog(dialogProveedor, "Lista de Tipos de Proveedor", true);
        dialogSelectorTipo.setSize(600, 350);
        dialogSelectorTipo.setLocationRelativeTo(dialogProveedor);
        dialogSelectorTipo.setLayout(new BorderLayout());

        List<TipoProveedor> listaTipos = proveedorDAO.obtenerTiposProveedor(miConexion);
        String[] columnasTipos = {"ID Tipo", "Nombre", "Descripción"};
        Object[][] datosTipos = new Object[listaTipos.size()][3];

        for (int i = 0; i < listaTipos.size(); i++) {
            TipoProveedor tp = listaTipos.get(i);
            datosTipos[i][0] = tp.getIdTipoProveedor();
            datosTipos[i][1] = tp.getNomTipProveedor();
            datosTipos[i][2] = tp.getDescripTipoProveedor();
        }

        javax.swing.JTable tablaTipos = new javax.swing.JTable(datosTipos, columnasTipos);
        javax.swing.table.TableRowSorter<javax.swing.table.TableModel> sorterTipos = new javax.swing.table.TableRowSorter<>(tablaTipos.getModel());
        tablaTipos.setRowSorter(sorterTipos);

        JPanel panelBusquedaTipo = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));
        panelBusquedaTipo.setBackground(Color.WHITE);
        JLabel lblBuscarTipo = new JLabel("Buscar (ID o Nombre):");
        JTextField txtBuscarTipo = new JTextField(20);
        panelBusquedaTipo.add(lblBuscarTipo);
        panelBusquedaTipo.add(txtBuscarTipo);

        txtBuscarTipo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent ev) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent ev) { filtrar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent ev) { filtrar(); }
            private void filtrar() {
                String texto = txtBuscarTipo.getText().trim();
                if (texto.length() == 0) {
                    sorterTipos.setRowFilter(null);
                } else {
                    sorterTipos.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + texto, 0, 1));
                }
            }
        });

        JButton btnSeleccionarTipoTabla = new JButton("Cargar y Modificar");
        JButton btnEliminarTipoTabla = new JButton("Eliminar Seleccionada");

        btnSeleccionarTipoTabla.addActionListener(ex -> {
            int filaVisual = tablaTipos.getSelectedRow();
            if (filaVisual >= 0) {
                int filaReal = tablaTipos.convertRowIndexToModel(filaVisual);
                TipoProveedor tpSeleccionado = listaTipos.get(filaReal);
                
                dialogSelectorTipo.dispose();

                JTextField txtIdTipo = new JTextField(tpSeleccionado.getIdTipoProveedor());
                txtIdTipo.setEnabled(false);
                JTextField txtNomTip = new JTextField(tpSeleccionado.getNomTipProveedor());
                JTextField txtDescripTip = new JTextField(tpSeleccionado.getDescripTipoProveedor());

                JLabel lblErrorNomTip = new JLabel(" ");
                lblErrorNomTip.setForeground(Color.RED);
                lblErrorNomTip.setFont(new Font("Arial", Font.PLAIN, 11));

                JLabel lblErrorDescripTip = new JLabel(" ");
                lblErrorDescripTip.setForeground(Color.RED);
                lblErrorDescripTip.setFont(new Font("Arial", Font.PLAIN, 11));

                txtNomTip.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    public void insertUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
                    public void changedUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
                    private void validar() {
                        String texto = txtNomTip.getText().trim();
                        boolean repetidas = texto.matches(".*(.)\\1{3,}.*");
                        if (texto.isEmpty()) {
                            lblErrorNomTip.setText("El nombre del tipo es obligatorio.");
                        } else if (texto.length() < 3) {
                            lblErrorNomTip.setText("El nombre es muy corto.");
                        } else if (repetidas) {
                            lblErrorNomTip.setText("Ingrese un nombre válido.");
                        } else {
                            lblErrorNomTip.setText(" ");
                        }
                    }
                });

                txtDescripTip.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    public void insertUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
                    public void changedUpdate(javax.swing.event.DocumentEvent ev) { validar(); }
                    private void validar() {
                        String texto = txtDescripTip.getText().trim();
                        boolean repetidas = texto.matches(".*(.)\\1{3,}.*");
                        if (!texto.isEmpty() && texto.length() < 3) {
                            lblErrorDescripTip.setText("La descripción es muy corta.");
                        } else if (repetidas) {
                            lblErrorDescripTip.setText("Ingrese una descripción válida.");
                        } else {
                            lblErrorDescripTip.setText(" ");
                        }
                    }
                });

                JPanel panelTipoDialog = new JPanel(new GridLayout(0, 1, 5, 2));
                panelTipoDialog.setBackground(Color.WHITE);
                panelTipoDialog.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                panelTipoDialog.add(new JLabel("ID Tipo Proveedor:"));
                panelTipoDialog.add(txtIdTipo);
                panelTipoDialog.add(new JLabel("Nombre del Tipo:"));
                panelTipoDialog.add(txtNomTip);
                panelTipoDialog.add(lblErrorNomTip);
                panelTipoDialog.add(new JLabel("Descripción:"));
                panelTipoDialog.add(txtDescripTip);
                panelTipoDialog.add(lblErrorDescripTip);

                JDialog dialogEditarTipo = new JDialog(dialogProveedor, "Modificar Tipo de Proveedor", true);
                dialogEditarTipo.setUndecorated(true);
                dialogEditarTipo.setBackground(Color.WHITE);

                JButton btnAceptarEditTipo = new JButton("Guardar Cambios");
                JButton btnCancelarEditTipo = new JButton("Cancelar");
                final boolean[] confirmadoEditTipo = {false};

                btnAceptarEditTipo.addActionListener(eEdit -> {
                    if (!lblErrorNomTip.getText().trim().isEmpty() || !lblErrorDescripTip.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(dialogEditarTipo, "Por favor, corrija los errores marcados en rojo.", "Campos Inválidos", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    confirmadoEditTipo[0] = true;
                    dialogEditarTipo.dispose();
                });

                btnCancelarEditTipo.addActionListener(eEdit -> {
                    confirmadoEditTipo[0] = false;
                    dialogEditarTipo.dispose();
                });

                JPanel panelBotonesEditTipo = new JPanel();
                panelBotonesEditTipo.setBackground(Color.WHITE);
                panelBotonesEditTipo.add(btnAceptarEditTipo);
                panelBotonesEditTipo.add(btnCancelarEditTipo);

                dialogEditarTipo.setLayout(new BorderLayout());
                dialogEditarTipo.add(panelTipoDialog, BorderLayout.CENTER);
                dialogEditarTipo.add(panelBotonesEditTipo, BorderLayout.SOUTH);
                dialogEditarTipo.pack();
                dialogEditarTipo.setLocationRelativeTo(dialogProveedor);
                dialogEditarTipo.setVisible(true);

                if (confirmadoEditTipo[0]) {
                    String nuevoNombre = txtNomTip.getText().trim();
                    String nuevaDesc = txtDescripTip.getText().trim();

                    if (!nuevoNombre.isEmpty()) {
                        tpSeleccionado.setNomTipProveedor(nuevoNombre);
                        tpSeleccionado.setDescripTipoProveedor(nuevaDesc);

                        boolean actualizado = proveedorDAO.actualizarTipoProveedor(tpSeleccionado, miConexion);
                        if (actualizado) {
                            JOptionPane.showMessageDialog(dialogProveedor, "Tipo de proveedor actualizado con éxito.");
                            cargarTipos.run();
                            cbxTipoProvPop.setSelectedItem(tpSeleccionado);
                        } else {
                            JOptionPane.showMessageDialog(dialogProveedor, "Error al actualizar el tipo.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(dialogProveedor, "El nombre no puede estar vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }

            } else {
                JOptionPane.showMessageDialog(dialogSelectorTipo, "Por favor, seleccione un tipo de proveedor de la tabla.");
            }
        });

        btnEliminarTipoTabla.addActionListener(ex -> {
            int filaVisual = tablaTipos.getSelectedRow();
            if (filaVisual >= 0) {
                int filaReal = tablaTipos.convertRowIndexToModel(filaVisual);
                TipoProveedor tpSel = listaTipos.get(filaReal);
                
                int confirm = JOptionPane.showConfirmDialog(dialogSelectorTipo, "¿Está seguro de eliminar este tipo de proveedor?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean eliminado = proveedorDAO.eliminarTipoProveedor(tpSel.getIdTipoProveedor(), miConexion);
                    if (eliminado) {
                        JOptionPane.showMessageDialog(dialogSelectorTipo, "Tipo de proveedor eliminado correctamente.");
                        if (cargarTipos != null) cargarTipos.run();
                        dialogSelectorTipo.dispose(); 
                    } else {
                        JOptionPane.showMessageDialog(dialogSelectorTipo, "No se puede eliminar porque está asignado a uno o más proveedores.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(dialogSelectorTipo, "Por favor, seleccione un tipo de proveedor de la tabla para eliminar.");
            }
        });

        JPanel panelBotSelTipo = new JPanel();
        panelBotSelTipo.setBackground(Color.WHITE);
        panelBotSelTipo.add(btnSeleccionarTipoTabla);
        panelBotSelTipo.add(btnEliminarTipoTabla);

        dialogSelectorTipo.add(panelBusquedaTipo, BorderLayout.NORTH);
        dialogSelectorTipo.add(new javax.swing.JScrollPane(tablaTipos), BorderLayout.CENTER);
        dialogSelectorTipo.add(panelBotSelTipo, BorderLayout.SOUTH);

        dialogSelectorTipo.setVisible(true);
    });
    
    btnAceptarProv.addActionListener(e -> {
        if (!lblErrorRuc.getText().trim().isEmpty() || 
            !lblErrorNombre.getText().trim().isEmpty() || 
            !lblErrorTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialogProveedor, "Por favor, corrija los errores marcados en rojo antes de continuar.", "Campos Inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        confirmadoProv[0] = true;
        dialogProveedor.dispose();
    });

    btnCancelarProv.addActionListener(e -> {
        confirmadoProv[0] = false;
        dialogProveedor.dispose();
    });

    JPanel panelBotonesProv = new JPanel();
    panelBotonesProv.setBackground(Color.WHITE);
    panelBotonesProv.add(btnModificar);
    panelBotonesProv.add(btnAceptarProv);
    panelBotonesProv.add(btnCancelarProv);

    dialogProveedor.setLayout(new BorderLayout());
    dialogProveedor.add(panel, BorderLayout.CENTER);
    dialogProveedor.add(panelBotonesProv, BorderLayout.SOUTH);
    dialogProveedor.pack();
    dialogProveedor.setLocationRelativeTo(this);
    dialogProveedor.setVisible(true);

    if (confirmadoProv[0]) {
        String ruc = txtRuc.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        TipoProveedor tipoSel = (TipoProveedor) cbxTipoProvPop.getSelectedItem();

        if (proveedorEnEdicion[0] != null) {
            proveedorEnEdicion[0].setNomEmpresa(nombre);
            proveedorEnEdicion[0].setNumTelelEmpresa(telefono);
            proveedorEnEdicion[0].setIdTipoProveedor(tipoSel.getIdTipoProveedor());

            if (proveedorDAO.actualizarProveedor(proveedorEnEdicion[0], miConexion)) {
                if (cbxProveedor != null) {
                    cbxProveedor.removeItem(proveedorEnEdicion[0]);
                    cbxProveedor.addItem(proveedorEnEdicion[0]);
                    cbxProveedor.setSelectedItem(proveedorEnEdicion[0]);
                }
                JOptionPane.showMessageDialog(this, "Proveedor actualizado exitosamente.");
            }
        } else {
            String idProv = proveedorDAO.obtenerSiguienteIdProveedor(miConexion);
            Proveedor nuevoProv = new Proveedor(idProv, ruc, nombre, telefono, tipoSel.getIdTipoProveedor());

            if (proveedorDAO.guardarProveedor(nuevoProv, miConexion)) {
                if (cbxProveedor != null) {
                    cbxProveedor.addItem(nuevoProv);
                    cbxProveedor.setSelectedItem(nuevoProv);
                }
                JOptionPane.showMessageDialog(this, "Proveedor guardado exitosamente.");
            }
        }
    }
    }//GEN-LAST:event_btnAgregarProveedorMouseClicked

    private void btnAgregarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProveedorMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProveedorMouseEntered

    private void btnAgregarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProveedorMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProveedorMouseExited

    private void cbxRepuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxRepuestoActionPerformed
        // TODO add your handling code here:
    Repuesto repuestoSel = (Repuesto) cbxRepuesto.getSelectedItem();
    if (repuestoSel != null) {
        txtPrecioUnitarioCompra.setText(String.valueOf(repuestoSel.getPrecioRepuestoUnit()));
    } else {
        txtPrecioUnitarioCompra.setText("");
    }

    }//GEN-LAST:event_cbxRepuestoActionPerformed

    private void btnEliminarFilaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarFilaMouseClicked
        int filaSeleccionada = tblDetalleCompra.getSelectedRow(); 
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione de la tabla el repuesto que desea quitar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DefaultTableModel modelo = (DefaultTableModel) tblDetalleCompra.getModel();
        modelo.removeRow(filaSeleccionada);
        
        calcularTotalCompra();
    }//GEN-LAST:event_btnEliminarFilaMouseClicked

    private void cbxRepuestoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxRepuestoMouseClicked
        // TODO add your handling code here:
          cargarCombos(); 

    }//GEN-LAST:event_cbxRepuestoMouseClicked

    private void cbxProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxProveedorMouseClicked
        // TODO add your handling code here:
        cargarCombos();
    }//GEN-LAST:event_cbxProveedorMouseClicked
    private void calcularTotalCompra() {
        double sumaTotal = 0.0;
        DefaultTableModel modelo = (DefaultTableModel) tblDetalleCompra.getModel();
        
        for (int i = 0; i < modelo.getRowCount(); i++) {
            double subtotalFila = Double.parseDouble(modelo.getValueAt(i, 4).toString());
            sumaTotal += subtotalFila;
        }
        
        txtTotalCompra.setText(String.format("%.2f", sumaTotal));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarraArriba;
    private javax.swing.JLabel Buscar;
    private javax.swing.JLabel Cantidad;
    private javax.swing.JLabel Factura;
    private javax.swing.JLabel Fecha;
    private javax.swing.JPanel Fondo;
    private javax.swing.JLabel Guardar;
    private javax.swing.JLabel ImagenADD;
    private javax.swing.JLabel ImagenADD1;
    private javax.swing.JLabel ImagenSAVE;
    private javax.swing.JLabel NombreVentanaProveedores;
    private javax.swing.JLabel Nuevo;
    private javax.swing.JLabel Nuevo1;
    private javax.swing.JLabel Precio;
    private javax.swing.JLabel Precio1;
    private javax.swing.JLabel Proveedor;
    private javax.swing.JLabel Repuesto;
    private javax.swing.JLabel TituloFuncion1;
    private javax.swing.JLabel TituloFuncion2;
    private javax.swing.JPanel btnAgregarItem;
    private javax.swing.JPanel btnAgregarProveedor;
    private javax.swing.JPanel btnEliminarFila;
    private javax.swing.JPanel btnGuardarCompra;
    private javax.swing.JComboBox<Object> cbxProveedor;
    private javax.swing.JComboBox<Object> cbxRepuesto;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblErrorCantidadProveedor;
    private javax.swing.JTable tblDetalleCompra;
    private javax.swing.JTextField txtCantidadCompra;
    private com.toedter.calendar.JDateChooser txtFechaCompra;
    private javax.swing.JTextField txtIdCompra;
    private javax.swing.JTextField txtPrecioUnitarioCompra;
    private javax.swing.JTextField txtTotalCompra;
    // End of variables declaration//GEN-END:variables
}
