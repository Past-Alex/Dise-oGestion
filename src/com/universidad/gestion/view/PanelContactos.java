package com.universidad.gestion.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

/**
 * Panel de Gestión de Contactos.
 * Incluye una columna de Checkbox para "Favorito" y botones de colores sólidos.
 */
public class PanelContactos extends JPanel {
    // Componentes accesibles para el controlador
    public JLabel lblTituloDatos, lblCedula, lblNombre, lblTelefono, lblCorreo, lblRecuento, lblBuscar;
    public JTextField txtCedula, txtNombre, txtTelefono, txtCorreo, txtFiltro;
    public JButton btnAgregar, btnModificar, btnEliminar, btnExportar;
    public JComboBox<String> comboIdiomas;
    public JTable tablaContactos;
    public DefaultTableModel modeloTabla;
    public JProgressBar barraProgreso;

    public PanelContactos() {
        // --- CONFIGURACIÓN ESTÉTICA GENERAL ---
        Color fondoGrisApp = new Color(210, 210, 210);
        setBackground(fondoGrisApp);
        setLayout(new BorderLayout(15, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- 1. CABECERA (BUSCADOR E IDIOMAS) ---
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);

        JPanel pBusq = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pBusq.setOpaque(false);
        lblBuscar = new JLabel("🔍");
        txtFiltro = new JTextField(30);
        txtFiltro.setPreferredSize(new Dimension(30, 35));
        pBusq.add(lblBuscar);
        pBusq.add(txtFiltro);

        JPanel pIdiomas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pIdiomas.setOpaque(false);
        comboIdiomas = new JComboBox<>(new String[]{"Español", "Ingles", "Portugués"});
        pIdiomas.add(comboIdiomas);
        pIdiomas.add(new JLabel("🌐"));

        panelNorte.add(pBusq, BorderLayout.WEST);
        panelNorte.add(pIdiomas, BorderLayout.EAST);

        lblRecuento = new JLabel("RECUENTO CONTACTOS [0]");
        lblRecuento.setFont(new Font("Segoe UI", Font.BOLD, 11));

        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setOpaque(false);
        pHeader.add(panelNorte, BorderLayout.NORTH);
        pHeader.add(lblRecuento, BorderLayout.SOUTH);

        // --- 2. CUERPO CENTRAL (DATOS Y TABLA) ---
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 5, 10, 5);

        // PANEL DE DATOS (Recuadro Blanco)
        JPanel pDatos = new JPanel(new GridBagLayout());
        pDatos.setBackground(Color.WHITE);
        pDatos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        GridBagConstraints d = new GridBagConstraints();
        d.insets = new Insets(10, 5, 10, 5);
        d.fill = GridBagConstraints.HORIZONTAL;

        lblTituloDatos = new JLabel("DATOS");
        lblTituloDatos.setFont(new Font("Segoe UI", Font.BOLD, 22));
        d.gridx = 0; d.gridy = 0; d.gridwidth = 2;
        d.insets = new Insets(0, 0, 30, 0);
        d.anchor = GridBagConstraints.CENTER;
        pDatos.add(lblTituloDatos, d);

        // Campos del Formulario
        d.gridwidth = 1; d.insets = new Insets(10, 5, 10, 5);
        lblCedula = new JLabel("Cedula:"); d.gridy = 1; d.gridx = 0; pDatos.add(lblCedula, d);
        txtCedula = new JTextField(15); d.gridx = 1; pDatos.add(txtCedula, d);

        lblNombre = new JLabel("Nombre:"); d.gridy = 2; d.gridx = 0; pDatos.add(lblNombre, d);
        txtNombre = new JTextField(15); d.gridx = 1; pDatos.add(txtNombre, d);

        lblTelefono = new JLabel("Teléfono:"); d.gridy = 3; d.gridx = 0; pDatos.add(lblTelefono, d);
        txtTelefono = new JTextField(15); d.gridx = 1; pDatos.add(txtTelefono, d);

        lblCorreo = new JLabel("<html>Correo<br>Electrónico:</html>"); d.gridy = 4; d.gridx = 0; pDatos.add(lblCorreo, d);
        txtCorreo = new JTextField(15); d.gridx = 1; pDatos.add(txtCorreo, d);

        btnAgregar = crearBotonSolido("GUARDAR", new Color(235, 235, 235), Color.BLACK);
        d.gridy = 5; d.gridx = 0; d.gridwidth = 2; d.fill = GridBagConstraints.NONE;
        d.anchor = GridBagConstraints.CENTER; d.insets = new Insets(25, 0, 10, 0);
        pDatos.add(btnAgregar, d);

        // --- CONFIGURACIÓN DE LA TABLA CON CHECKBOX (Requisito Especial) ---
        String[] columnas = {"Nombres", "Cedula", "Telf", "Email", "Favorito"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // La columna 4 manejará Booleanos para mostrar el Checkbox
                if (columnIndex == 4) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo permitimos editar la columna del checkbox directamente
                return column == 4;
            }
        };

        tablaContactos = new JTable(modeloTabla);
        tablaContactos.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(tablaContactos);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        gbc.gridx = 0; gbc.weightx = 0.38; gbc.weighty = 1.0;
        panelCentral.add(pDatos, gbc);
        gbc.gridx = 1; gbc.weightx = 0.62;
        panelCentral.add(scroll, gbc);

        // --- 3. PIE DE PÁGINA (BOTONES SÓLIDOS Y BARRA ÚNICA) ---
        JPanel panelSur = new JPanel(new BorderLayout(0, 15));
        panelSur.setOpaque(false);

        JPanel pBtnsAccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 0));
        pBtnsAccion.setOpaque(false);

        btnModificar = crearBotonSolido("MODIFICAR", new Color(236, 212, 68), Color.BLACK); // Amarillo
        btnEliminar = crearBotonSolido("ELIMINAR", new Color(178, 0, 0), Color.WHITE);      // Rojo
        btnExportar = crearBotonSolido("EXPORTAR CSV", new Color(137, 255, 129), Color.BLACK); // Verde

        pBtnsAccion.add(btnModificar);
        pBtnsAccion.add(btnEliminar);
        pBtnsAccion.add(btnExportar);

        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);
        barraProgreso.setString("0% DE EXPORTACIÓN");
        barraProgreso.setPreferredSize(new Dimension(0, 35));
        barraProgreso.setBackground(Color.WHITE);
        barraProgreso.setForeground(new Color(200, 200, 200));
        barraProgreso.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        panelSur.add(pBtnsAccion, BorderLayout.NORTH);
        panelSur.add(barraProgreso, BorderLayout.SOUTH);

        add(pHeader, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }

    private JButton crearBotonSolido(String texto, Color fondo, Color textoColor) {
        JButton b = new JButton(texto);
        b.setUI(new BasicButtonUI()); // Asegura color sólido
        b.setBackground(fondo);
        b.setForeground(textoColor);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setPreferredSize(new Dimension(185, 45));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}