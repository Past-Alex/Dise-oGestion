package com.universidad.gestion.model;

/**
 * Clase Modelo que representa a un Usuario/Contacto.
 * Cumple con el estándar de encapsulamiento para el patrón MVC.
 */
public class Contacto {
    private String cedula;
    private String nombre;
    private String telefono;
    private String correo;
    private boolean favorito;

    /**
     * Constructor completo para inicializar un contacto.
     */
    public Contacto(String cedula, String nombre, String telefono, String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.favorito = false; // Por defecto no es favorito
    }

    // --- Getters y Setters ---

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    /**
     * Genera una línea de texto con formato CSV.
     * Implementa la limpieza de comas para evitar errores en el archivo final.
     * @return String con los datos: Cédula, Nombre, Teléfono, Correo, Favorito
     */
    public String toCSV() {
        String favStatus = favorito ? "SI" : "NO";
        return String.format("%s,%s,%s,%s,%s",
                cedula.replace(",", ""),
                nombre.replace(",", ""),
                telefono.replace(",", ""),
                correo.replace(",", ""),
                favStatus);
    }

    @Override
    public String toString() {
        return nombre + " (" + cedula + ")";
    }
}