package org.sfsoft.bbdd.base;

/**
 * Clase que representa un alumno
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Alumno {
	
	private String nombre;
	private String asignatura;
	
	public Alumno() {}
	
	public Alumno(String nombre, String asignatura) {
		this.nombre = nombre;
		this.asignatura = asignatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}
}
