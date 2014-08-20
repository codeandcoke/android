package org.sfsoft.bbdd;

import static org.sfsoft.bbdd.database.Constantes.ASIGNATURA_ALUMNO;
import static org.sfsoft.bbdd.database.Constantes.NOMBRE_ALUMNO;

import org.sfsoft.bbdd.database.BaseDatos;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

/**
 * Aplicación que muestra como trabajar con una Base de Datos SQLite en Android
 * Además se han añadido las siguientes características:
 * 	- TabHost para presentar el interfaz en pestañas
 *  - Soporte multilenguaje
 *  
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class GestionAlumnos extends Activity implements OnClickListener, OnTabChangeListener {

	private BaseDatos datos;
	
	private static String[] FROM_SHOW = {NOMBRE_ALUMNO, ASIGNATURA_ALUMNO };
	private static int[] TO = {R.id.nombreAlumno, R.id.asignaturaAlumno};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        
        cargaPestanas();
        
        cargaFormulario();
        
        cargaLista();
    }
    
    /*
     * Carga las pestañas para formar el TabHost
     */
    private void cargaPestanas() {
    	
    	Resources res = getResources();
        
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        
        TabSpec tbListadoAlumnos = tabHost.newTabSpec(res.getString(R.string.listado_alumnos_title));
        	tbListadoAlumnos.setContent(R.id.tab1);
        	tbListadoAlumnos.setIndicator(res.getString(R.string.listado_alumnos_title), res.getDrawable(android.R.drawable.ic_menu_slideshow));
        tabHost.addTab(tbListadoAlumnos);
        
        TabSpec tbNuevoAlumno = tabHost.newTabSpec(res.getString(R.string.nuevo_alumno_title));
	    	tbNuevoAlumno.setContent(R.id.tab2);
	    	tbNuevoAlumno.setIndicator(res.getString(R.string.nuevo_alumno_title), res.getDrawable(android.R.drawable.ic_menu_edit));
    	tabHost.addTab(tbNuevoAlumno);
        
        tabHost.setCurrentTab(0);
        
        tabHost.setOnTabChangedListener(this);
    }
    
    /*
     * Prepara el interfaz de la pestaña del listado
     * de Alumnos
     */
    private void cargaLista() {
    	       
        // Instancia el objeto que permite trabajar con la Base de Datos
        datos = new BaseDatos(this);
        
        verAlumnos();
    }
    
    /*
     * Muestra los alumnos en la pantalla
     */
    private void verAlumnos() {
    	Cursor cursor = datos.getAlumnos();
    	this.cargarAlumnos(cursor);
    }
    
    /*
     * Carga los alumnos de la Base de Datos y enlaza el adaptador con la ListActivity
     * @param cursor
     */
    private void cargarAlumnos(Cursor cursor) {
    	
    	SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.fila_alumno, cursor, FROM_SHOW, TO, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    	((ListView) findViewById(R.id.lvLista)).setAdapter(adaptador);
    }
    
    /*
     * Prepara el interfaz de la pestaña del formulario
     * de Alta
     */
    private void cargaFormulario() {
    	
    	// Instancia el objeto que permite trabajar con la Base de Datos
        datos = new BaseDatos(getApplicationContext());
        
        Button btAlta = (Button) findViewById(R.id.btAlta);
        btAlta.setOnClickListener(this);
        
        setTitle(getResources().getString(R.string.listado_alumnos_title));
    }
    
    /*
     * Método que atiende el evento click sobre el botón de
     * dar de alta un nuevo Alumno
     */
    public void onClick(View view) {
    	
    	EditText etNombre = (EditText) findViewById(R.id.etNombre);
    	EditText etAsignatura = (EditText) findViewById(R.id.etAsignatura);
    	
    	// Registra el alumno en la Base de Datos
    	datos.nuevoAlumno(etNombre.getText().toString(), etAsignatura.getText().toString());
    	
    	etNombre.setText("");
    	etAsignatura.setText("");
    	
    	Toast.makeText(this, getResources().getString(R.string.confirma_alta_message), Toast.LENGTH_SHORT).show();
    }

    /*
     * Método que se ejecuta cuando se cambia de pestaña
     */
	public void onTabChanged(String tabId) {
		
		// Si se cambia a la pestaña del Listado de Alumnos, se refrescan los datos
		if (tabId.equals(getResources().getString(R.string.listado_alumnos_title))) {
			verAlumnos();
		}
		
		setTitle(tabId);
	}
}
