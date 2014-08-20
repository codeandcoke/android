package org.sfsoft.tabs.database;

import org.sfsoft.tabs.base.Student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static org.sfsoft.tabs.database.Constants.SUBJECT;
import static org.sfsoft.tabs.database.Constants.NAME;
import static org.sfsoft.tabs.database.Constants.STUDENTS_TABLE;

/**
 * Clase a través de la cual se gestiona la Base de Datos
 *
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Database extends SQLiteOpenHelper {

	// Nombre de la base de datos
	private static final String DATABASE_NAME = "falton.db";
	// Versión de la base de datos
	private static final int DATABASE_VERSION = 1;
	
	// Claúsula FROM y ORDER BY para utilizar a la hora de consultar los datos
	private static String[] FROM_CURSOR = {_ID, NAME, SUBJECT};
	private static String ORDER_BY = NAME + " DESC";
	
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
	 * Crea la estructura de las tablas de la Base de Datos
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + STUDENTS_TABLE + "("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME
				+ " TEXT NOT NULL, " + SUBJECT + " TEXT NOT NULL);");
	}
	
	/**
	 * Realiza las operaciones para actualizar la Base de Datos cuando se
	 * actualiza la aplicación
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int previousVersion, int nextVersion) {
		// Sería más conveniente lanzar un ALTER TABLE en vez de eliminar la versión anterior de la tabla
		db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE);
		onCreate(db);
	}
	
	/**
	 * Registra un nuevo student en la tabla
	 * @student
	 */
	public void newStudent(Student student) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(NAME, student.getName());
    	values.put(SUBJECT, student.getSubject());
    	db.insertOrThrow(STUDENTS_TABLE, null, values);
    }
    
	/**
	 * Obtiene un cursor con todos los alumnos de la tabla
	 * @return Todos los alumnos de la tabla
	 */
    public Cursor getStudents() {
    
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	// Lanza una consulta sobre la base de datos con claúsula FROM y ORDER BY
    	Cursor cursor = db.query(STUDENTS_TABLE, FROM_CURSOR, null, null, null, null, ORDER_BY);
    	
    	return cursor;
    }
}