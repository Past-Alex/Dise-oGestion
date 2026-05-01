package com.universidad.gestion.view;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana Principal - Versión Final Corregida.
 * Se ha eliminado la barra de progreso de la zona SOUTH para evitar duplicidad
 * con la barra interna del PanelContactos.
 */
public class VentanaPrincipal extends JFrame {

    // Paneles que representan las pestañas
    public PanelContactos pnlContactos;
    public PanelEstadisticas pnlEstadisticas;

    // Componentes globales
    public JTabbedPane pestañas;

    // Elementos del menú contextual (Clic derecho)
    public JMenuItem itemExportar, itemEliminar;

    public VentanaPrincipal() {
        // --- CONFIGURACIÓN BÁSICA DE LA VENTANA ---
        setTitle("Sistema de Gestión de Contactos - Gabriel Monteros - UPS");
        setSize(1000, 750); // Tamaño ajustado para mejor visibilidad
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. INICIALIZACIÓN DE COMPONENTES MODULARES
        pnlContactos = new PanelContactos();
        pnlEstadisticas = new PanelEstadisticas();

        // 2. IMPLEMENTACIÓN DE JTABBEDPANE (Organización por pestañas)
        pestañas = new JTabbedPane();
        pestañas.addTab("Gestión de Usuarios", pnlContactos);
        pestañas.addTab("Estadísticas y Reportes", pnlEstadisticas);

        // 3. CONFIGURACIÓN DE MENÚ CONTEXTUAL (Acciones Clic Derecho)
        JPopupMenu menuContextual = new JPopupMenu();
        itemEliminar = new JMenuItem("Eliminar Contacto Seleccionado");
        itemExportar = new JMenuItem("Exportar Lista Completa a CSV");

        // Estilo para el ítem de eliminar
        itemEliminar.setForeground(Color.RED);

        menuContextual.add(itemEliminar);
        menuContextual.add(new JPopupMenu.Separator());
        menuContextual.add(itemExportar);

        // Asociar el menú a la tabla de contactos
        pnlContactos.tablaContactos.setComponentPopupMenu(menuContextual);

        // --- IMPORTANTE: ELIMINACIÓN DE LA BARRA DUPLICADA ---
        /*
           Se eliminó la barra de progreso que estaba aquí (BorderLayout.SOUTH)
           porque ahora el diseño utiliza exclusivamente la barra que está
           dentro de pnlContactos para un acabado más limpio y profesional.
        */

        // 4. ENSAMBLAJE FINAL
        add(pestañas, BorderLayout.CENTER);
    }
}