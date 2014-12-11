package org.sfsoft.android_mapas2.util;

import com.google.android.gms.maps.model.LatLng;

import android.provider.BaseColumns;

/**
 * Clase con las constantes usadas en la aplicación
 * @author Santiago Faci
 * @version 2014-2015
 */
public interface Constantes extends BaseColumns {
	
	// URL donde se encuentran los datos de las gasolineras (en JSON)
	public static final String URL = "http://www.zaragoza.es/georref/json/hilo/estacionesDeServicio_Equipamiento";
	
	public static final String TABLA = "ubicaciones";
	public static final String NOMBRE = "nombre";
	public static final String LATITUD = "latitud";
	public static final String LONGITUD = "longitud";

	public static final LatLng ZARAGOZA = new LatLng(41.652333, -0.886913);
}
