
package com.mycompany.proyecto_m2b.Vista;

import com.mycompany.proyecto_m2b.Controlador.ResiduoDAO;
import com.mycompany.proyecto_m2b.modelo.Residuos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ListaResiduosDialog extends JDialog {
    private JTable tablaResiduos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnModificarSeleccionada;
    private JButton btnEliminarSeleccionada;
    private ResiduoDAO residuoDAO;
    private NuevoResiduo panelPadre; 

    public ListaResiduosDialog(Frame parent, boolean modal, NuevoResiduo panelPadre) {
        super(parent, "Lista de Residuos", modal);
        this.panelPadre = panelPadre;
        this.residuoDAO = new ResiduoDAO();
        
        setSize(750, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setUndecorated(false);

        initUI();
        cargarDatosTabla("");
    }

    private void initUI() {
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelNorte.add(new JLabel("Buscar (ID o Nombre):"));
        txtBuscar = new JTextField(25);
        panelNorte.add(txtBuscar);
        add(panelNorte, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Estado", "Tipo ID", "Cant. Actual", "Cant. Máx"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tablaResiduos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaResiduos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnModificarSeleccionada = new JButton("Modificar Seleccionada");
        btnEliminarSeleccionada = new JButton("Eliminar Seleccionada");
        
        panelSur.add(btnModificarSeleccionada);
        panelSur.add(btnEliminarSeleccionada);
        add(panelSur, BorderLayout.SOUTH);

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarDatosTabla(txtBuscar.getText().trim());
            }
        });

        btnModificarSeleccionada.addActionListener(e -> seleccionarParaModificar());

        btnEliminarSeleccionada.addActionListener(e -> eliminarResiduoSeleccionado());
    }

    private void cargarDatosTabla(String filtro) {
        modeloTabla.setRowCount(0);
        List<Residuos> lista = residuoDAO.listarResiduosDetallados(filtro); 
        for (Residuos r : lista) {
            modeloTabla.addRow(new Object[]{
                r.getID_resiudos(),
                r.getNom_residuo(),
                r.getEstado_residuo(),
                r.getID_tipo_resi(),
                r.getCantidad_actual(),
                r.getCantidad_max()
            });
        }
    }

    private void seleccionarParaModificar() {
        int filaSeleccionada = tablaResiduos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un residuo de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
        String nombre = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
        String estado = modeloTabla.getValueAt(filaSeleccionada, 2).toString();
        int cantidad = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 4).toString());

        panelPadre.cargarDatosParaEdicion(id, nombre, estado, cantidad);
        dispose();
    }

    private void eliminarResiduoSeleccionado() {
        int filaSeleccionada = tablaResiduos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un residuo para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idResiduo = modeloTabla.getValueAt(filaSeleccionada, 0).toString();

        if (residuoDAO.estaResiduoEnVenta(idResiduo)) {
            JOptionPane.showMessageDialog(this, 
                "No se puede eliminar el residuo porque está en uso en una o más transacciones de venta.", 
                "Operación Denegada", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el residuo con ID: " + idResiduo + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);        if (confirmacion == JOptionPane.YES_OPTION) {
            if (residuoDAO.eliminarResiduo(idResiduo)) {
                JOptionPane.showMessageDialog(this, "Residuo eliminado correctamente.");
                cargarDatosTabla("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el residuo de la base de datos.", "Error BD", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}