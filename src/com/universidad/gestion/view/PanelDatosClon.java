package com.universidad.gestion.view;

import javax.swing.*;
import java.awt.*;

/**
 * Clon Exacto del Box de Datos de image_5.png usando GridBagLayout.
 * Distribución compacta y profesional.
 */
public class PanelDatosClon extends JPanel {
    // Labels para traducción
    public JLabel lblTitulo, lblCedula, lblNombre, lblTelefono, lblCorreo;
    // Inputs y Botón
    public JTextField txtCedula, txtNombre, txtTelefono, txtCorreo;
    public JButton btnGuardar;

    public PanelDatosClon() {
        // --- PALETA DE COLORES GRIS PLOMO PROFESIONAL ---
        Color colorFondoCuadro = new Color(255, 255, 255); // Blanco como en la imagen
        Color colorBorde = new Color(200, 200, 200);      // Gris suave para el borde
        Color colorTexto = new Color(50, 50, 50);          // Gris oscuro para labels

        // Configuración del Panel contenedor
        setBackground(colorFondoCuadro);
        setLayout(new GridBagLayout()); // El layout clave para el clonado

        // Borde redondeado y sombreado sutil (aproximación visual de image_5.png)
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBorde, 1, true), // Borde gris redondeado
                BorderFactory.createEmptyBorder(25, 25, 25, 25)      // Padding interno
        ));

        // Objeto de restricciones para controlar la posición exacta
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Estirar horizontalmente
        gbc.insets = new Insets(10, 5, 10, 10);   // Margen entre componentes (top, left, bottom, right)

        // --- 1. TÍTULO "DATOS" (Fila 0) ---
        lblTitulo = new JLabel("DATOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Grande y negrita
        lblTitulo.setForeground(colorTexto);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0; gbc.gridy = 0; // Columna 0, Fila 0
        gbc.gridwidth = 2;            // Ocupa 2 columnas
        gbc.insets = new Insets(0, 0, 30, 0); // Mucho margen abajo para separar del formulario
        add(lblTitulo, gbc);

        // --- Restablecer restricciones básicas para el formulario ---
        gbc.gridwidth = 1; // Vuelve a ocupar 1 columna
        gbc.weightx = 0.0;  // No se estira
        gbc.insets = new Insets(10, 5, 10, 10); // Margen normal

        // --- 2. FILA CÉDULA (Fila 1) ---
        lblCedula = new JLabel("Cedula:");
        lblCedula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblCedula.setForeground(colorTexto);
        gbc.gridx = 0; gbc.gridy = 1; // Columna 0, Fila 1
        add(lblCedula, gbc);

        txtCedula = crearInputEstilizado();
        gbc.gridx = 1; gbc.weightx = 1.0; // Columna 1, Fila 1, se estira
        add(txtCedula, gbc);

        // --- 3. FILA NOMBRE (Fila 2) ---
        gbc.weightx = 0.0; // Restablecer weightx para el label
        lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNombre.setForeground(colorTexto);
        gbc.gridx = 0; gbc.gridy = 2; // Columna 0, Fila 2
        add(lblNombre, gbc);

        txtNombre = crearInputEstilizado();
        gbc.gridx = 1; gbc.weightx = 1.0; // Columna 1, Fila 2
        add(txtNombre, gbc);

        // --- 4. FILA TELÉFONO (Fila 3) ---
        gbc.weightx = 0.0;
        lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTelefono.setForeground(colorTexto);
        gbc.gridx = 0; gbc.gridy = 3; // Columna 0, Fila 3
        add(lblTelefono, gbc);

        txtTelefono = crearInputEstilizado();
        gbc.gridx = 1; gbc.weightx = 1.0; // Columna 1, Fila 3
        add(txtTelefono, gbc);

        // --- 5. FILA CORREO (Fila 4) ---
        gbc.weightx = 0.0;
        // Uso de HTML para el salto de línea exacto como en image_5.png
        lblCorreo = new JLabel("<html>Correo<br>Electrónico:</html>");
        lblCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblCorreo.setForeground(colorTexto);
        gbc.gridx = 0; gbc.gridy = 4; // Columna 0, Fila 4
        gbc.insets = new Insets(10, 5, 20, 10); // Más margen abajo antes del botón
        add(lblCorreo, gbc);

        txtCorreo = crearInputEstilizado();
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0; // Columna 1, Fila 4
        add(txtCorreo, gbc);

        // --- 6. BOTÓN GUARDAR (Fila 5) ---
        btnGuardar = new JButton("GUARDAR");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setBackground(new Color(220, 220, 220)); // Gris claro como en image_5.png
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new Dimension(150, 40)); // Tamaño fijo y compacto

        // Borde redondeado sutil para el botón
        btnGuardar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBorde, 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        gbc.gridx = 0; gbc.gridy = 5; // Columna 0, Fila 5
        gbc.gridwidth = 2;            // Ocupa las 2 columnas
        gbc.weightx = 0.0;            // No se estira horizontalmente
        gbc.fill = GridBagConstraints.NONE; // No llena el espacio
        gbc.anchor = GridBagConstraints.CENTER; // Se centra en el panel
        gbc.insets = new Insets(20, 0, 0, 0);   // Margen superior
        add(btnGuardar, gbc);
    }

    private JTextField crearInputEstilizado() {
        JTextField tf = new JTextField(15);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Borde simple y limpio como en la imagen
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return tf;
    }
}
