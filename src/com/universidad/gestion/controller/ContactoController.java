package com.universidad.gestion.controller;

import com.universidad.gestion.model.Contacto;
import com.universidad.gestion.model.ContactoDAO;
import com.universidad.gestion.view.VentanaPrincipal;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ContactoController {
    private final VentanaPrincipal vista;
    private final ContactoDAO modelo;
    private final TableRowSorter<DefaultTableModel> sorter;
    private ResourceBundle rb;

    public ContactoController(VentanaPrincipal vista, ContactoDAO modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.sorter = new TableRowSorter<>(vista.pnlContactos.modeloTabla);
        this.vista.pnlContactos.tablaContactos.setRowSorter(sorter);

        // Carga inicial en Español
        try {
            this.rb = ResourceBundle.getBundle("i18n.mensajes", new Locale("es"));
        } catch (Exception e) {
            System.err.println("Error al cargar i18n/mensajes_es.properties");
        }

        initListeners();
        actualizarTodo();
    }

    private void initListeners() {
        vista.pnlContactos.btnAgregar.addActionListener(e -> guardarContacto());
        vista.pnlContactos.btnModificar.addActionListener(e -> modificarContacto());
        vista.pnlContactos.btnEliminar.addActionListener(e -> eliminarContacto());
        vista.pnlContactos.btnExportar.addActionListener(e -> exportarConProgreso());
        vista.pnlContactos.comboIdiomas.addActionListener(e -> gestionarIdioma());

        vista.pnlContactos.txtFiltro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = vista.pnlContactos.txtFiltro.getText();
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
            }
        });

        vista.pnlContactos.tablaContactos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarFila();
            }
        });

        vista.pnlContactos.modeloTabla.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int fila = e.getFirstRow();
                int columna = e.getColumn();
                if (columna == 4) {
                    Boolean nuevoEstado = (Boolean) vista.pnlContactos.modeloTabla.getValueAt(fila, columna);
                    String cedula = (String) vista.pnlContactos.modeloTabla.getValueAt(fila, 1);
                    actualizarPreferencia(cedula, nuevoEstado);
                }
            }
        });
    }

    private void actualizarTodo() {
        actualizarTabla();
        actualizarDashboard();
    }

    private void actualizarDashboard() {
        List<Contacto> lista = modelo.obtenerTodos();
        vista.pnlEstadisticas.setTotalContactos(lista.size());
        vista.pnlEstadisticas.setTotalFavoritos((int) lista.stream().filter(Contacto::isFavorito).count());
    }

    private void actualizarTabla() {
        DefaultTableModel dt = vista.pnlContactos.modeloTabla;
        dt.setRowCount(0);
        List<Contacto> lista = modelo.obtenerTodos();
        for (Contacto c : lista) {
            dt.addRow(new Object[]{c.getNombre(), c.getCedula(), c.getTelefono(), c.getCorreo(), c.isFavorito()});
        }

        if (rb != null) {
            // Actualizar el recuento
            vista.pnlContactos.lblRecuento.setText(rb.getString("label.recuento") + " [" + lista.size() + "]");

            // ACTUALIZACIÓN DE CABECERAS DE TABLA
            String[] cabeceras = {
                    rb.getString("col.nombres"),
                    rb.getString("col.cedula"),
                    rb.getString("col.telf"),
                    rb.getString("col.email"),
                    rb.getString("col.favorito")
            };
            dt.setColumnIdentifiers(cabeceras);
        }
    }

    private void guardarContacto() {
        String ced = vista.pnlContactos.txtCedula.getText().trim();
        String nom = vista.pnlContactos.txtNombre.getText().trim();
        if (!ced.isEmpty() && !nom.isEmpty()) {
            modelo.agregarContacto(new Contacto(ced, nom, vista.pnlContactos.txtTelefono.getText(), vista.pnlContactos.txtCorreo.getText()));
            actualizarTodo();
            limpiarCampos();
            if (rb != null) JOptionPane.showMessageDialog(vista, rb.getString("msg.guardado"));
        }
    }

    private void modificarContacto() {
        int fila = vista.pnlContactos.tablaContactos.getSelectedRow();
        if (fila != -1) {
            int index = vista.pnlContactos.tablaContactos.convertRowIndexToModel(fila);
            if (modelo.eliminarContacto(vista.pnlContactos.modeloTabla.getValueAt(index, 1).toString())) {
                guardarContacto();
            }
        }
    }

    private void eliminarContacto() {
        int fila = vista.pnlContactos.tablaContactos.getSelectedRow();
        if (fila != -1) {
            int index = vista.pnlContactos.tablaContactos.convertRowIndexToModel(fila);
            String cedula = vista.pnlContactos.modeloTabla.getValueAt(index, 1).toString();
            String confirmMsg = rb != null ? rb.getString("msg.confirmar.eliminar") : "Delete?";
            int confirm = JOptionPane.showConfirmDialog(vista, confirmMsg, "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION && modelo.eliminarContacto(cedula)) {
                actualizarTodo();
                limpiarCampos();
                if (rb != null) JOptionPane.showMessageDialog(vista, rb.getString("msg.eliminado"));
            }
        }
    }

    private void actualizarPreferencia(String cedula, boolean favorito) {
        modelo.cambiarEstadoFavorito(cedula, favorito);
        actualizarDashboard();
        if (rb != null) {
            String key = favorito ? "msg.favorito.si" : "msg.favorito.no";
            JOptionPane.showMessageDialog(vista, rb.getString(key));
        }
    }

    private void exportarConProgreso() {
        new Thread(() -> {
            try {
                vista.pnlContactos.btnExportar.setEnabled(false);
                JProgressBar b1 = vista.pnlContactos.barraProgreso;
                JProgressBar b2 = vista.pnlEstadisticas.barraProgresoEst;

                String textoBase = rb.getString("barra.progreso").replace("0%", "");

                for (int i = 0; i <= 100; i += 10) {
                    final int p = i;
                    SwingUtilities.invokeLater(() -> {
                        b1.setValue(p);
                        b1.setString(p + "%" + textoBase);
                        b2.setValue(p);
                        b2.setString(p + "%" + textoBase);

                        if (p == 100) {
                            b1.setForeground(new Color(34, 139, 34));
                            b1.setString(rb.getString("barra.exito"));
                            b2.setForeground(new Color(34, 139, 34));
                            b2.setString(rb.getString("barra.exito"));
                        }
                    });
                    Thread.sleep(100);
                }

                modelo.exportarCSV("contactos.csv");
                JOptionPane.showMessageDialog(vista, rb.getString("barra.exito"));

                SwingUtilities.invokeLater(() -> {
                    b1.setValue(0);
                    b1.setString(rb.getString("barra.progreso"));
                    b1.setForeground(new Color(200, 200, 200));
                    b2.setValue(0);
                    b2.setString(rb.getString("barra.progreso"));
                    b2.setForeground(new Color(230, 230, 230));
                });
            } catch (Exception ex) { ex.printStackTrace(); } finally { vista.pnlContactos.btnExportar.setEnabled(true); }
        }).start();
    }

    private void gestionarIdioma() {
        String seleccion = (String) vista.pnlContactos.comboIdiomas.getSelectedItem();

        // IMPORTANTE: El texto en 'case' debe ser igual al del JComboBox
        Locale locale = switch (seleccion) {
            case "Ingles" -> new Locale("en");
            case "Portugués" -> new Locale("pt");
            default -> new Locale("es");
        };

        try {
            this.rb = ResourceBundle.getBundle("i18n.mensajes", locale);
            aplicarTraducciones();
            actualizarTodo(); // Esto fuerza la actualización de la tabla y sus cabeceras
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aplicarTraducciones() {
        // Pestañas
        vista.pestañas.setTitleAt(0, rb.getString("tab.gestion"));
        vista.pestañas.setTitleAt(1, rb.getString("tab.estadisticas"));

        // Panel Gestión (Botones y Etiquetas)
        vista.pnlContactos.lblTituloDatos.setText(rb.getString("titulo.datos"));
        vista.pnlContactos.lblCedula.setText(rb.getString("label.cedula"));
        vista.pnlContactos.lblNombre.setText(rb.getString("label.nombre"));
        vista.pnlContactos.lblTelefono.setText(rb.getString("label.telefono"));
        vista.pnlContactos.lblCorreo.setText("<html>" + rb.getString("label.correo").replace(" ", "<br>") + "</html>");

        vista.pnlContactos.btnAgregar.setText(rb.getString("btn.guardar"));
        vista.pnlContactos.btnModificar.setText(rb.getString("btn.modificar"));
        vista.pnlContactos.btnEliminar.setText(rb.getString("btn.eliminar"));
        vista.pnlContactos.btnExportar.setText(rb.getString("btn.exportar"));

        // Barras de progreso
        vista.pnlContactos.barraProgreso.setString(rb.getString("barra.progreso"));
        vista.pnlEstadisticas.barraProgresoEst.setString(rb.getString("barra.progreso"));

        // Dashboard de Estadísticas
        vista.pnlEstadisticas.actualizarTitulos(
                rb.getString("dashboard.titulo"),
                rb.getString("card.total"),
                rb.getString("card.favoritos")
        );
    }

    private void seleccionarFila() {
        int fila = vista.pnlContactos.tablaContactos.getSelectedRow();
        if (fila != -1) {
            int index = vista.pnlContactos.tablaContactos.convertRowIndexToModel(fila);
            vista.pnlContactos.txtNombre.setText(vista.pnlContactos.modeloTabla.getValueAt(index, 0).toString());
            vista.pnlContactos.txtCedula.setText(vista.pnlContactos.modeloTabla.getValueAt(index, 1).toString());
            vista.pnlContactos.txtTelefono.setText(vista.pnlContactos.modeloTabla.getValueAt(index, 2).toString());
            vista.pnlContactos.txtCorreo.setText(vista.pnlContactos.modeloTabla.getValueAt(index, 3).toString());
        }
    }

    private void limpiarCampos() {
        vista.pnlContactos.txtCedula.setText("");
        vista.pnlContactos.txtNombre.setText("");
        vista.pnlContactos.txtTelefono.setText("");
        vista.pnlContactos.txtCorreo.setText("");
        vista.pnlContactos.tablaContactos.clearSelection();
    }
}