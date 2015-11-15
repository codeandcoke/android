package org.sfsoft.guiarestaurantes.base;

/**
 * Clase que representa a cada uno de los restaurantes
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Restaurante {

	private String nombre;
	private String descripcion;
	private String categoria;
	private float latitud;
	private float longitud;
	
	public Restaurante(String nombre, String descripcion, String categoria, float latitud, float longitud) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.categoria = categoria;
		this.latitud = latitud;
		this.longitud = longitud;
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
