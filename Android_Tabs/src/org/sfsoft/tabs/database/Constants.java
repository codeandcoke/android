package org.sfsoft.tabs.database;

import android.provider.BaseColumns;

/**
 * Clase con las constantes usadas en la aplicación
 *
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public interface Constants extends BaseColumns {
	
	public static final String STUDENTS_TABLE = "alumnos";
	
	/**
	 * Columnas de la tabla Alumnos
	 */
	public static final String NAME = "nombre";
	public static final String SUBJECT = "asignatura";

}
