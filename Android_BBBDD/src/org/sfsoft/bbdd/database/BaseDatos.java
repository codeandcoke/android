package org.sfsoft.bbdd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static org.sfsoft.bbdd.database.Constantes.ASIGNATURA_ALUMNO;
import static org.sfsoft.bbdd.database.Constantes.NOMBRE_ALUMNO;
import static org.sfsoft.bbdd.database.Constantes.TABLA_ALUMNOS;

/**
 * Clase a través de la cual se gestiona la Base de Datos
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class BaseDatos extends SQLiteOpenHelper {

	// Nombre de la base de datos
	private static final String BASEDATOS_NOMBRE = "android_bbdd.db";
	// Versión de la base de datos
	private static final int BASEDATOS_VERSION = 1;
	
	// Claúsula FROM y ORDER BY para utilizar a la hora de consultar los datos
	private static String[] FROM_CURSOR = {_ID, NOMBRE_ALUMNO, ASIGNATURA_ALUMNO };
	private static String ORDER_BY = NOMBRE_ALUMNO + " DESC";
	
	public BaseDatos(Context contexto) {
		super(contexto, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
	}
	
	/**
	 * Crea la estructura de las tablas de la Base de Datos
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLA_ALUMNOS + "(" 
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOMBRE_ALUMNO
				+ " TEXT NOT NULL, " + ASIGNATURA_ALUMNO + " TEXT NOT NULL);");
	}
	
	/**
	 * Realiza las operaciones para actualizar la Base de Datos cuando se
	 * actualiza la aplicación
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
		// Sería más conveniente lanzar un ALTER TABLE en vez de eliminar la versión anterior de la tabla
		db.execSQL("DROP TABLE IF EXISTS " + TABLA_ALUMNOS);
		onCreate(db);
	}
	
	/**
	 * Registra un nuevo alumno en la tabla
	 * @param nombre Nombre del alumno
	 * @param asignatura Asignatura que cursa
	 */
	public void nuevoAlumno(String nombre, String asignatura) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues valores = new ContentValues();
    	valores.put(NOMBRE_ALUMNO, nombre);
    	valores.put(ASIGNATURA_ALUMNO, asignatura);
    	db.insertOrThrow(TABLA_ALUMNOS, null, valores);
    }
    
	/**
	 * Obtiene un cursor con todos los alumnos de la tabla
	 * @return Todos los alumnos de la tabla
	 */
    public Cursor getAlumnos() {
    
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	// Lanza una consulta sobre la base de datos con claúsula FROM y ORDER BY
    	Cursor cursor = db.query(TABLA_ALUMNOS, FROM_CURSOR, null, null, null, null, ORDER_BY);
    	
    	return cursor;
    }
}
