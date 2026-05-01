package com.universidad.gestion.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Panel de Estadísticas (Dashboard).
 * Implementa soporte para internacionalización y diseño de tarjetas redondeadas.
 */
public class PanelEstadisticas extends JPanel {
    // Componentes que cambian de idioma
    private JLabel lblTituloPrincipal;
    private JLabel lblTituloTarjetaTotal;
    private JLabel lblTituloTarjetaFavs;

    // Componentes que muestran datos reales
    private JLabel lblTotalContactos;
    private JLabel lblTotalFavoritos;

    // Barra de progreso sincronizada
    public JProgressBar barraProgresoEst;

    public PanelEstadisticas() {
        // --- COLORES SEGÚN DISEÑO ---
        Color grisFondo = new Color(210, 210, 210);

        setBackground(grisFondo);
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // 1. TÍTULO SUPERIOR (DASHBOARD)
        lblTituloPrincipal = new JLabel("DASHBOARD DE REPORTES Y ESTADÍSTICAS", SwingConstants.CENTER);
        lblTituloPrincipal.setFont(new Font("Segoe UI", Font.PLAIN, 26));
        lblTituloPrincipal.setForeground(Color.BLACK);
        add(lblTituloPrincipal, BorderLayout.NORTH);

        // 2. PANEL CENTRAL DE TARJETAS
        JPanel panelTarjetas = new JPanel(new GridBagLayout());
        panelTarjetas.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 15, 0, 15);
        gbc.fill = GridBagConstraints.BOTH;

        // --- TARJETA: TOTAL CONTACTOS ---
        lblTituloTarjetaTotal = new JLabel("<html><center>CANTIDAD DE CONTACTOS REGISTRADOS</center></html>", SwingConstants.CENTER);
        JPanel cardTotal = crearTarjetaRedondeada(lblTituloTarjetaTotal);
        lblTotalContactos = new JLabel("0");
        estilizarNumero(lblTotalContactos);
        cardTotal.add(lblTotalContactos, BorderLayout.CENTER);

        // --- TARJETA: FAVORITOS ---
        lblTituloTarjetaFavs = new JLabel("<html><center>CONTACTOS FAVORITOS</center></html>", SwingConstants.CENTER);
        JPanel cardFavs = crearTarjetaRedondeada(lblTituloTarjetaFavs);
        lblTotalFavoritos = new JLabel("0");
        estilizarNumero(lblTotalFavoritos);
        cardFavs.add(lblTotalFavoritos, BorderLayout.CENTER);

        gbc.gridx = 0; panelTarjetas.add(cardTotal, gbc);
        gbc.gridx = 1; panelTarjetas.add(cardFavs, gbc);
        add(panelTarjetas, BorderLayout.CENTER);

        // 3. BARRA DE PROGRESO INFERIOR
        barraProgresoEst = new JProgressBar(0, 100);
        barraProgresoEst.setStringPainted(true);
        barraProgresoEst.setString("0% DE EXPORTACIÓN");
        barraProgresoEst.setPreferredSize(new Dimension(0, 35));
        barraProgresoEst.setBackground(Color.WHITE);
        barraProgresoEst.setForeground(new Color(230, 230, 230));
        barraProgresoEst.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(barraProgresoEst, BorderLayout.SOUTH);
    }

    /**
     * Crea un panel con fondo blanco y bordes redondeados.
     */
    private JPanel crearTarjetaRedondeada(JLabel labelTitulo) {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 30, 30));
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(320, 350));
        p.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        labelTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelTitulo.setForeground(Color.BLACK);
        p.add(labelTitulo, BorderLayout.NORTH);

        return p;
    }

    private void estilizarNumero(JLabel lbl) {
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 110));
        lbl.setForeground(Color.BLACK);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // --- MÉTODOS PARA EL CONTROLADOR (SINCRONIZACIÓN) ---

    public void setTotalContactos(int total) {
        lblTotalContactos.setText(String.valueOf(total));
    }

    public void setTotalFavoritos(int total) {
        lblTotalFavoritos.setText(String.valueOf(total));
    }

    /**
     * Actualiza los textos del panel según el idioma cargado por ResourceBundle.
     * @param principal Título del Dashboard.
     * @param t1 Título tarjeta contactos.
     * @param t2 Título tarjeta favoritos.
     */
    public void actualizarTitulos(String principal, String t1, String t2) {
        lblTituloPrincipal.setText(principal);
        lblTituloTarjetaTotal.setText("<html><center>" + t1 + "</center></html>");
        lblTituloTarjetaFavs.setText("<html><center>" + t2 + "</center></html>");
    }
}