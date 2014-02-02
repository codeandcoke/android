package org.sfsoft.android_mapas.base;

import com.google.android.gms.maps.model.LatLng;

public class Gasolinera {

	private String nombre;
	private LatLng posicion;
	
	public Gasolinera() {}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LatLng getPosicion() {
		return posicion;
	}

	public void setPosicion(LatLng posicion) {
		this.posicion = posicion;
	}
	
	
}
