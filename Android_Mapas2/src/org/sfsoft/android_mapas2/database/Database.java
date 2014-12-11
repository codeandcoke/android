package org.sfsoft.android_mapas2.database;

import org.sfsoft.android_mapas2.base.Ubicacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static org.sfsoft.android_mapas2.util.Constantes.TABLA;
import static org.sfsoft.android_mapas2.util.Constantes.NOMBRE;
import static org.sfsoft.android_mapas2.util.Constantes.LATITUD;
import static org.sfsoft.android_mapas2.util.Constantes.LONGITUD;


/**
 * Clase a través de la cual se gestiona la Base de Datos
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Database extends SQLiteOpenHelper {

	// Nombre de la base de datos
	private static final String BASEDATOS_NOMBRE = "android_mapas2.db";
	// Versión de la base de datos
	private static final int BASEDATOS_VERSION = 1;
	
	// Claúsula FROM y ORDER BY para utilizar a la hora de consultar los datos
	private static String[] FROM_CURSOR = {_ID, NOMBRE, LATITUD, LONGITUD };
	private static String ORDER_BY = NOMBRE + " DESC";
	
	public Database(Context contexto) {
		super(contexto, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
	}
	
	/**
	 * Crea la estructura de las tablas de la Base de Datos
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLA + "(" 
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOMBRE
				+ " TEXT NOT NULL, " + LATITUD + " REAL DEFAULT 0," +
				LONGITUD + " REAL DEFAULT 0)");
	}
	
	/**
	 * Realiza las operaciones para actualizar la Base de Datos cuando se
	 * actualiza la aplicación
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
		// Sería más conveniente lanzar un ALTER TABLE en vez de eliminar la versión anterior de la tabla
		db.execSQL("DROP TABLE IF EXISTS " + TABLA);
		onCreate(db);
	}
	
	/**
	 * Registra una ubicación en la Base de Datos
	 */
	public void nuevaUbicacion(Ubicacion ubicacion) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues valores = new ContentValues();
    	valores.put(NOMBRE, ubicacion.getNombre());
    	valores.put(LATITUD, ubicacion.getPosicion().latitude);
    	valores.put(LONGITUD, ubicacion.getPosicion().longitude);
    	db.insertOrThrow(TABLA, null, valores);
    }

    /**
     * Modifica una Ubicación en la base de datos
     * @param ubicacion
     */
    public void modificarUbicacion(Ubicacion ubicacion) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(NOMBRE, ubicacion.getNombre());
        valores.put(LATITUD, ubicacion.getPosicion().latitude);
        valores.put(LONGITUD, ubicacion.getPosicion().longitude);
        db.update(TABLA, valores, _ID + " = " + ubicacion.getId(), null);
    }

    /**
     * Elimina una Ubicación de la base de datos
     * @param ubicacion
     */
    public void eliminarUbicacion(Ubicacion ubicacion) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA, _ID + " = " + ubicacion.getId(), null);
    }
    
	/**
	 * Obtiene un cursor con todas las ubicaciones de la tabla
	 * @return
	 */
    public Cursor getUbicaciones() {
    
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	// Lanza una consulta sobre la base de datos con claúsula FROM y ORDER BY
    	Cursor cursor = db.query(TABLA, FROM_CURSOR, null, null, null, null, ORDER_BY);
    	
    	return cursor;
    }
}
