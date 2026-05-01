package com.universidad.gestion.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactoDAO {
    private final List<Contacto> listaContactos;

    public ContactoDAO() {
        this.listaContactos = new ArrayList<>();
    }

    public void agregarContacto(Contacto contacto) {
        if (contacto != null) {
            listaContactos.add(contacto);
        }
    }

    public List<Contacto> obtenerTodos() {
        return new ArrayList<>(listaContactos);
    }

    public boolean eliminarContacto(String cedula) {
        return listaContactos.removeIf(c -> c.getCedula().equalsIgnoreCase(cedula.trim()));
    }

    // CORRECCIÓN: Ahora acepta la cédula Y el nuevo estado
    public void cambiarEstadoFavorito(String cedula, boolean nuevoEstado) {
        for (Contacto c : listaContactos) {
            if (c.getCedula().equals(cedula)) {
                c.setFavorito(nuevoEstado);
                break;
            }
        }
    }

    public boolean exportarCSV(String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("Cedula,Nombres,Telf,Email,Favorito");
            writer.newLine();
            for (Contacto c : listaContactos) {
                writer.write(c.toCSV());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}