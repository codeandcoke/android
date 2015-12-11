package org.sfaci.guiagasolineras2016.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * Métodos de utilidad para el proyecto
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Util {
	
	public static uk.me.jstott.jcoord.LatLng DeUMTSaLatLng(double este, double oeste, char zonaLat, int zonaLong) {
		
		uk.me.jstott.jcoord.UTMRef utm = new uk.me.jstott.jcoord.UTMRef(este, oeste, 'N', 30);
		
		return utm.toLatLng();
	}
	
	/**
	 * Convierte las coordenadas tal y como vienen en el JSON en coordenadas 
	 * segín el formato de Google Maps (LatLng)
	 * @param localizacion
	 * @return Un objeto LatLng de Google Maps con las coordenadas 'en bruto' del JSON. En caso de error devuelve null
	 */
	public static LatLng parseCoordenadas(String localizacion) {
		
		// Comprueba que el elemento seleccionado sean unas coordenadas
		if ((!localizacion.startsWith("[")) || (!localizacion.endsWith("]")))
			return null;
		
		// Lee y extrae la coordenada del elemento seleccionado por el usuario
		localizacion = localizacion.substring(1, localizacion.length() - 1);
		String coordenadas[] = localizacion.split(",");
		
		// Convierte las coordenadas de UTM a Latitud y Longitud de la librería jcoord
		uk.me.jstott.jcoord.LatLng ubicacion = Util.DeUMTSaLatLng(Double.parseDouble(coordenadas[0]), 
				Double.parseDouble(coordenadas[1]), 'N', 30);
		
		// Devuelve finalmente las coordenadas como un objeto LatLng de Google Maps
		return new LatLng(ubicacion.getLat(), ubicacion.getLng());
	}
}
