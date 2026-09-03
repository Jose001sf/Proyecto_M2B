
package com.mycompany.proyecto_m2b.Vista;

import com.mycompany.proyecto_m2b.Controlador.ServicioDAO;
import com.mycompany.proyecto_m2b.Controlador.TipoServicioDAO;
import com.mycompany.proyecto_m2b.Controlador.Validaciones;
import com.mycompany.proyecto_m2b.modelo.Servicio;
import com.mycompany.proyecto_m2b.modelo.Tipo_de_servicio;
import java.awt.Color;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author HP
 */
public class PanelCatalogoServicio extends javax.swing.JPanel {
    
    private TableRowSorter<DefaultTableModel> sortear;
    
    public PanelCatalogoServicio() {
        initComponents();
        cargarCombotipos();
        cargarTabla();
        
        tblCatalogo.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                int fila = tblCatalogo.getSelectedRow();
                if (fila >= 0 && fila < lista1.size()) {
                    Servicio seleccionado = lista1.get(fila);
                    idSeleccionado = seleccionado.getId_servi();
                    txtServicio.setText(seleccionado.getNom_servicio());
                    txtPrecioBase.setText(String.format("%.2f", seleccionado.getPrecio_del_servicio()));
                    txtTiempoEstimado.setText(String.valueOf(seleccionado.getTiempo_est_hor_servi()));
                    idSeleccionado2 = seleccionado.getId_tipo_servicio();
                }
            }
        });
        initSorter();
        
        sortear = new TableRowSorter<>((DefaultTableModel) tblCatalogo.getModel());
        tblCatalogo.setRowSorter(sortear);
        
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {filtrar();}
            @Override
            public void removeUpdate(DocumentEvent e) {filtrar();}
            @Override
            public void changedUpdate(DocumentEvent e) {filtrar();}
        });
        
        validarEnTiempoReal(txtServicio, txtErrorServicio, Validaciones::validarNombreServicio, "Nombre invalido (Ej: Cambio de aceite)");
        validarEnTiempoReal(txtPrecioBase, txtErrorPrecioBase, Validaciones::validarPrecio, "Precio invalido (Ej: 45.50)");
        validarEnTiempoReal(txtTiempoEstimado, txtErrorTiempoEstimado, Validaciones::validarTiempo, "Tiempo en horas invalido (Ej: 4)");
        limpiarDatos();
    }
    
    TipoServicioDAO dao = new TipoServicioDAO();
    List<Tipo_de_servicio> lista = dao.listarTipoServicio();
    ServicioDAO dao1 = new ServicioDAO();
    List<Servicio> lista1 = dao1.listarServicio();
    private String idSeleccionado = null;
    private String idSeleccionado2 = null;
 
    private void cargarCombotipos(){
        DefaultComboBoxModel<Tipo_de_servicio> modelo = new DefaultComboBoxModel();
        for (Tipo_de_servicio t :lista){
            modelo.addElement(t);
        }
        comboTiposServicios.setModel(modelo);
    }
    
    private void validarEnTiempoReal(JTextField campo, JLabel labelError, Predicate<String> metodoValidacion, String mensajeError) {
        labelError.setText("");
        labelError.setForeground(Color.RED);

        campo.getDocument().addDocumentListener(new DocumentListener() {
            private void evaluar() {
                String texto = campo.getText().trim();
                if (texto.isEmpty()) {
                    labelError.setText("Campo obligatorio");
                } else if (!metodoValidacion.test(texto)) {
                    labelError.setText(mensajeError);
                } else {
                    labelError.setText(""); 
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) { evaluar(); }

            @Override
            public void removeUpdate(DocumentEvent e) { evaluar(); }

            @Override
            public void changedUpdate(DocumentEvent e) { evaluar(); }
        });
    
    }
    private void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblCatalogo.getModel();
        modelo.setRowCount(0);

        lista1 = dao1.listarServicio();
        for (Servicio t : lista1) {
            Object[] fila = {
                t.getNom_servicio(),
                t.getPrecio_del_servicio(),
                t.getTiempo_est_hor_servi()

            };
            modelo.addRow(fila);
        }
    }
    
    private void limpiarDatos(){
        txtServicio.setText(" ");
        txtPrecioBase.setText(" ");
        txtTiempoEstimado.setText(" ");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtServicio = new javax.swing.JTextField();
        txtPrecioBase = new javax.swing.JTextField();
        txtTiempoEstimado = new javax.swing.JTextField();
        TxtCatalogoServicios = new javax.swing.JLabel();
        TxtBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCatalogo = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboTiposServicios = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        PanelGuardar = new javax.swing.JPanel();
        Guardar = new javax.swing.JLabel();
        ImagenSAVE = new javax.swing.JLabel();
        PanelEditar = new javax.swing.JPanel();
        Editar = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        PanelDarBaja = new javax.swing.JPanel();
        DarDeBaja = new javax.swing.JLabel();
        ImagenDarBaja = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Talleres = new javax.swing.JLabel();
        MJ = new javax.swing.JLabel();
        txtErrorServicio = new javax.swing.JLabel();
        txtErrorPrecioBase = new javax.swing.JLabel();
        txtErrorTiempoEstimado = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0)));
        jPanel2.setForeground(new java.awt.Color(255, 153, 102));

        jLabel3.setText("Tiempo estimado:");

        jLabel2.setText("Precio Base:");

        jLabel1.setText("Servicio:");

        txtServicio.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtServicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtServicioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtServicioFocusLost(evt);
            }
        });
        txtServicio.addActionListener(this::txtServicioActionPerformed);

        txtPrecioBase.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtPrecioBase.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrecioBaseFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecioBaseFocusLost(evt);
            }
        });
        txtPrecioBase.addActionListener(this::txtPrecioBaseActionPerformed);

        txtTiempoEstimado.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtTiempoEstimado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTiempoEstimadoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTiempoEstimadoFocusLost(evt);
            }
        });

        TxtCatalogoServicios.setFont(new java.awt.Font("Segoe UI", 1, 34)); // NOI18N
        TxtCatalogoServicios.setForeground(new java.awt.Color(120, 120, 120));
        TxtCatalogoServicios.setText("Catalogo Servicios");

        TxtBuscar.setBackground(new java.awt.Color(0, 0, 0));
        TxtBuscar.setText("Buscar:");

        txtBuscar.setSelectionColor(new java.awt.Color(102, 102, 102));

        tblCatalogo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Servicio", "Precio base", "Tiempo estimado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCatalogo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tblCatalogo);

        jLabel5.setText("Tipo de servicio:");

        jButton1.setText("Agregar servicio");
        jButton1.addActionListener(this::jButton1ActionPerformed);

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

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit_22dp_000000_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout PanelEditarLayout = new javax.swing.GroupLayout(PanelEditar);
        PanelEditar.setLayout(PanelEditarLayout);
        PanelEditarLayout.setHorizontalGroup(
            PanelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEditarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
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
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        PanelDarBaja.setBackground(new java.awt.Color(255, 255, 255));
        PanelDarBaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 106, 106)));
        PanelDarBaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelDarBaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelDarBajaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PanelDarBajaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelDarBajaMouseExited(evt);
            }
        });

        DarDeBaja.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        DarDeBaja.setForeground(new java.awt.Color(215, 106, 106));
        DarDeBaja.setText("Eliminar");

        ImagenDarBaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/person_cancel_22dp_EA3323_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout PanelDarBajaLayout = new javax.swing.GroupLayout(PanelDarBaja);
        PanelDarBaja.setLayout(PanelDarBajaLayout);
        PanelDarBajaLayout.setHorizontalGroup(
            PanelDarBajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDarBajaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ImagenDarBaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(DarDeBaja)
                .addGap(30, 30, 30))
        );
        PanelDarBajaLayout.setVerticalGroup(
            PanelDarBajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDarBajaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDarBajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DarDeBaja)
                    .addComponent(ImagenDarBaja))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(413, 103));

        Talleres.setBackground(new java.awt.Color(152, 75, 45));
        Talleres.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Talleres.setForeground(new java.awt.Color(174, 63, 54));
        Talleres.setText("TALLERES");
        Talleres.setPreferredSize(new java.awt.Dimension(190, 47));

        MJ.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        MJ.setForeground(new java.awt.Color(152, 75, 45));
        MJ.setText("M&J");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MJ)
                    .addComponent(Talleres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1340, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(MJ, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Talleres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(TxtBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtErrorServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TxtCatalogoServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jLabel1)
                                                        .addGap(177, 177, 177))
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(txtServicio)
                                                        .addGap(70, 70, 70)))
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel2)
                                                    .addComponent(txtPrecioBase, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtErrorPrecioBase, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(61, 61, 61)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtErrorTiempoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel3)
                                                    .addComponent(txtTiempoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(PanelGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(PanelEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(PanelDarBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(49, 49, 49)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton1)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(comboTiposServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(694, 694, 694))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtCatalogoServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecioBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTiempoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTiposServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtErrorServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PanelGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtErrorPrecioBase, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtErrorTiempoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PanelEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelDarBaja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1326, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtServicioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtServicioFocusGained
        // TODO add your handling code here:
        if (txtServicio.getText().equals("Ingrese el servicio")) {
            txtServicio.setText("");
            txtServicio.setForeground(java.awt.Color.BLACK);
        }
    }//GEN-LAST:event_txtServicioFocusGained

    private void txtServicioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtServicioFocusLost
        // TODO add your handling code here:
        if (txtServicio.getText().trim().isEmpty()) {
            txtServicio.setText("Ingrese el servicio");
            txtServicio.setForeground(java.awt.Color.GRAY);
        }
    }//GEN-LAST:event_txtServicioFocusLost

    private void txtServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtServicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtServicioActionPerformed

    private void txtPrecioBaseFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioBaseFocusGained
        // TODO add your handling code here:
        if (txtPrecioBase.getText().equals("Ingrese el precio base")) {
            txtPrecioBase.setText("");
            txtPrecioBase.setForeground(java.awt.Color.BLACK);
        }
    }//GEN-LAST:event_txtPrecioBaseFocusGained

    private void txtPrecioBaseFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioBaseFocusLost
        // TODO add your handling code here:
        if (txtPrecioBase.getText().trim().isEmpty()) {
            txtPrecioBase.setText("Ingrese el precio base");
            txtPrecioBase.setForeground(java.awt.Color.GRAY);
        }
    }//GEN-LAST:event_txtPrecioBaseFocusLost

    private void txtPrecioBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioBaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioBaseActionPerformed

    private void txtTiempoEstimadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTiempoEstimadoFocusGained
        // TODO add your handling code here:
        if (txtPrecioBase.getText().equals("Ingrese el tiempo estimado")) {
            txtPrecioBase.setText("");
            txtPrecioBase.setForeground(java.awt.Color.BLACK);
        }
    }//GEN-LAST:event_txtTiempoEstimadoFocusGained

    private void txtTiempoEstimadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTiempoEstimadoFocusLost
        // TODO add your handling code here:
        if (txtPrecioBase.getText().trim().isEmpty()) {
            txtPrecioBase.setText("Ingrese el tiempo estimado");
            txtPrecioBase.setForeground(java.awt.Color.GRAY);
        }
    }//GEN-LAST:event_txtTiempoEstimadoFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        TipoServicio ts = new TipoServicio();
        ts.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void PanelGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelGuardarMouseClicked
        // TODO add your handling code here:
        String nombre = txtServicio.getText().trim();
        String precioBase = txtPrecioBase.getText().trim().replace(",", ".");
        String hora = txtTiempoEstimado.getText().trim();
        Tipo_de_servicio tipoSeleccionado = (Tipo_de_servicio) comboTiposServicios.getSelectedItem();
        String idTipoServicio = tipoSeleccionado.getID_tipo_servicio();

        if (nombre.isEmpty() || precioBase.isEmpty() || hora.isEmpty() || idTipoServicio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }
        
        float preciosBase = Float.parseFloat(precioBase);
        int horaEstimada = Integer.parseInt(hora);
        
        ServicioDAO dao = new ServicioDAO();
        String id = dao.generarNuevoId();
        if (id == null) {
            JOptionPane.showMessageDialog(this, "No se pudo generar un nuevo ID.");
            return;
        }
        Servicio nuevo = new Servicio(id, horaEstimada, preciosBase, nombre, idTipoServicio);
        dao.insertar(nuevo);
        limpiarDatos();
        cargarTabla();
            
    }//GEN-LAST:event_PanelGuardarMouseClicked

    private void PanelGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelGuardarMouseEntered
        // TODO add your handling code here:
        PanelGuardar.setBackground(new Color(227, 95, 32));
        Guardar.setForeground(new Color(217, 217, 192));
    }//GEN-LAST:event_PanelGuardarMouseEntered

    private void PanelGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelGuardarMouseExited
        // TODO add your handling code here:
        PanelGuardar.setBackground(new Color(242,101,34));
        Guardar.setForeground(Color.white);
    }//GEN-LAST:event_PanelGuardarMouseExited

    private void PanelEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelEditarMouseEntered
        // TODO add your handling code here:
        PanelEditar.setBackground(new Color(219,219,219));
        Editar.setForeground(new Color(66, 66, 66));
    }//GEN-LAST:event_PanelEditarMouseEntered

    private void PanelEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelEditarMouseExited
        // TODO add your handling code here:
        PanelEditar.setBackground(Color.white);
        Editar.setForeground(Color.black);
    }//GEN-LAST:event_PanelEditarMouseExited

    private void PanelDarBajaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelDarBajaMouseEntered
        // TODO add your handling code here:
        PanelDarBaja.setBackground(new Color (252, 168, 168));
        DarDeBaja.setForeground(Color.white);
    }//GEN-LAST:event_PanelDarBajaMouseEntered

    private void PanelDarBajaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelDarBajaMouseExited
        // TODO add your handling code here:
        PanelDarBaja.setBackground(Color.white);
        DarDeBaja.setForeground(new Color(215, 106, 106));
    }//GEN-LAST:event_PanelDarBajaMouseExited

    private void PanelEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelEditarMouseClicked
        // TODO add your handling code here:
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione algun tipo de servicio.");
            return;
        }
        String nombre = txtServicio.getText().trim();
        String precioBase = txtPrecioBase.getText().trim().replace(",", ".");
        String hora = txtTiempoEstimado.getText().trim();
        Tipo_de_servicio tipoSeleccionado = (Tipo_de_servicio) comboTiposServicios.getSelectedItem();
        String idTipoServicio = tipoSeleccionado.getID_tipo_servicio();

        if (nombre.isEmpty() || precioBase.isEmpty() || hora.isEmpty() || idTipoServicio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }
        float preciosBase = Float.parseFloat(precioBase);
        int horaEstimada = Integer.parseInt(hora);
        
        ServicioDAO dao = new ServicioDAO();

        Servicio actualizado = new Servicio(idSeleccionado, horaEstimada, preciosBase, nombre, idTipoServicio);
        dao.actualizarServicio(actualizado);
        limpiarDatos();
        cargarTabla();
    }//GEN-LAST:event_PanelEditarMouseClicked

    private void PanelDarBajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelDarBajaMouseClicked
        // TODO add your handling code here:
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione algun tipo de servicio.");
            return;
        }
        int Ysisi = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar este servicio?", "Confirmar eleccion", JOptionPane.YES_NO_OPTION);
        if (Ysisi != JOptionPane.YES_OPTION) {
            return;
        }
        boolean logro = dao1.eliminarServicio(idSeleccionado);

        if (logro) {
            JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
            limpiarDatos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se ha eliminado correctamente.");
        }
    }//GEN-LAST:event_PanelDarBajaMouseClicked
    
    private void initSorter(){
        TableRowSorter<DefaultTableModel> sorter
                = new TableRowSorter<>((DefaultTableModel) tblCatalogo.getModel());
        tblCatalogo.setRowSorter(sorter);

        txtBuscar.addActionListener(e -> {
            String texto = txtBuscar.getText().trim();
            sorter.setRowFilter(texto.isEmpty() ? null : RowFilter.regexFilter("(?i)" + texto));
        });
    }
    
    private void filtrar() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            sortear.setRowFilter(null);
        } else {
            sortear.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0));
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DarDeBaja;
    private javax.swing.JLabel Editar;
    private javax.swing.JLabel Guardar;
    private javax.swing.JLabel ImagenDarBaja;
    private javax.swing.JLabel ImagenSAVE;
    private javax.swing.JLabel MJ;
    private javax.swing.JPanel PanelDarBaja;
    private javax.swing.JPanel PanelEditar;
    private javax.swing.JPanel PanelGuardar;
    private javax.swing.JLabel Talleres;
    private javax.swing.JLabel TxtBuscar;
    private javax.swing.JLabel TxtCatalogoServicios;
    private javax.swing.JComboBox<Tipo_de_servicio> comboTiposServicios;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCatalogo;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JLabel txtErrorPrecioBase;
    private javax.swing.JLabel txtErrorServicio;
    private javax.swing.JLabel txtErrorTiempoEstimado;
    private javax.swing.JTextField txtPrecioBase;
    private javax.swing.JTextField txtServicio;
    private javax.swing.JTextField txtTiempoEstimado;
    // End of variables declaration//GEN-END:variables
}
