package main;

import com.universidad.gestion.controller.ContactoController;
import com.universidad.gestion.model.ContactoDAO;
import com.universidad.gestion.view.VentanaPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal que arranca la aplicación.
 * Coordina la instancia de Vista, Modelo y Controlador.
 */
public class Main {
    public static void main(String[] args) {
        // Establecer el aspecto del sistema operativo (Opcional, mejora la UI)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ejecutar la interfaz en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            // 1. Instanciar el Modelo
            ContactoDAO modelo = new ContactoDAO();

            // 2. Instanciar la Vista
            VentanaPrincipal vista = new VentanaPrincipal();

            // 3. Instanciar el Controlador y pasarle la Vista y el Modelo
            new ContactoController(vista, modelo);

            // 4. Hacer visible la ventana
            vista.setVisible(true);
        });
    }
}