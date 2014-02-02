package org.sfsoft.tabs.database;

import android.provider.BaseColumns;

/**
 * Clase con las constantes usadas en la aplicación
 * @author Santiago Faci
 *
 */
public interface Constantes extends BaseColumns {
	
	public static final String TABLA_ALUMNOS = "alumnos";
	
	/**
	 * Columnas de la tabla Alumnos
	 */
	public static final String NOMBRE_ALUMNO = "nombre";
	public static final String ASIGNATURA_ALUMNO = "asignatura";

}
