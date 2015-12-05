package org.sfaci.guiarestaurantes.base;

/**
 * Clase que representa a un restaurante
 * @author Santiago Faci
 * @version curso 2015-2016
 */
public class Restaurante {

    private String nombre;
    private String descripcion;
    private String categoria;
    private String link;
    private float latitud;
    private float longitud;

    public Restaurante() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }


}