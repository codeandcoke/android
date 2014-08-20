package org.sfsoft.agenda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import org.sfsoft.agenda.base.Contacto;

import java.util.ArrayList;

/**
 * Agenda Personal para Android 'Agendroid' v2.0
 * Se han añadido las siguientes funcionalidades
 * - Soporte multilenguaje
 * 		Se ha añadido una copia del fichero strings en la carpeta res->values-en
 * 		con la traducción en inglés de todas las cadenas de la aplicación
 * 
 * - ActionBar
 * 		Se ha modificado el estilo por defecto por 'android:Theme.Holo.Light' para dar
 * 		soporte a las nueva 'ActionBar' que sustituyen a los menús de opciones
 *		Además se configuran los elementos del menú de opciones para que aparezcan 
 *		en la nueva ActionBar
 *		También es necesario añadir alguna opción a cada elemento del menú de opciones
 *		que está configurado en res->menu->main.xml
 *		Se puede comprobar también que en versiones de Android anteriores a 3.0
 *		la aplicación se puede ejecutar con normalidad mostrándose en este caso
 *		la ActionBar como los anteriores menús de opciones, pero sin perder funcionalidad
 *		Probar con showAsAction="withText|always|ifRoom"
 * 
 * - Menú de Preferencias
 * 		Se ha definido una PreferenceActivity desde la que el usuario puede personalizar 
 * 		algunos aspectos de la aplicación.
 * 		El menú se define en res.xml.preferencias y se ha utilizado un array de valores en
 * 		res.values.datos
 * 		Además, hay que fijarse bien en como se utiliza el código para usar la configuración
 * 		elegida
 * 
 * - Llamar por teléfono
 * 		Es posible utilizar la función de llamar por teléfono desde el menú contextual
 * 		de la lista de contactos
 * 		Acordarse de dar los permisos necesarios en AndroidManifest.xml
 * 
 * - Diálogos
 * 		Se muestran diálogos de confirmación e información para confirmar el borrado
 * 		de los contactos y mostrar información de la aplicación, respectivamente
 * 
 * Activity principal donde se listan los contactos de la Agenda
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MainActivity extends Activity implements OnCreateContextMenuListener {

	public static ArrayList<Contacto> listaContactos;
	private ContactoAdapter adaptador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*
		 *  Se comprueba si hay datos almacenados de un estado anterior.
		 *  Si los hay se cargaran y sino se inicializa la lista de Contactos
		 */
		if (savedInstanceState == null) {
			listaContactos = new ArrayList<Contacto>();
		}
		else {
			listaContactos = savedInstanceState.getParcelableArrayList("contactos");
		}
		
		adaptador = new ContactoAdapter(this, listaContactos);
		
		ListView lvLista = (ListView) findViewById(R.id.lvLista);
		lvLista.setAdapter(adaptador);
		// Asigna una vista a la ListView cuando no haya datos en ella
		lvLista.setEmptyView(findViewById(R.id.tvSinDatos));
		this.registerForContextMenu(lvLista);
	}

	/*
	 * Este método lo invoca Android antes de que la Activity termine 
	 * para almacenar su estado.
	 * En este caso se aprovecha para almacenar la lista de contactos, de forma
	 * que luego podamos recuperarla en el método onCreate()
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		outState.putParcelableArrayList("contactos", listaContactos);
		super.onSaveInstanceState(outState);
	}

	/*
	 * Siempre que volvamos de segundo plano, notificaremos al adaptador
	 * que hay cambios por si los hubiera habido
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		/*
		 * Comprueba las preferencias del usuario para ver si se deben
		 * mostrar sólo los contactos marcados como favoritos
		 */
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
		boolean verFavoritos = preferencias.getBoolean("opcion_ver_favoritos", false);
		if (verFavoritos)
			adaptador.verFavoritos();
		else
			adaptador.verTodos();
		
		adaptador.notifyDataSetChanged();
	}
	
	/*
	 * Método que carga un menú contextual en pantalla
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		
		getMenuInflater().inflate(R.menu.menu_contextual, menu);
	}
	
	/*
	 * Método invocado cuando se selecciona un elemento del menú contextual
	 * En el caso de las llamadas de teléfono se debería comprobar si se ha
	 * registrado algún número antes de lanzar la llamada en cada caso
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		/*
		 *  Contiene información sobre el elemento del menú contextual
		 *  sobre el que se ha pulsado
		 */
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Contacto contacto = null;
		Intent llamadaTelefono = null;
		
		switch (item.getItemId()) {
			case R.id.ctx_llamar_telefono:
				
				llamadaTelefono = new Intent(Intent.ACTION_CALL);
				contacto = listaContactos.get(info.position);
				llamadaTelefono.setData(Uri.parse("tel:" + contacto.getTelefono()));
				startActivity(llamadaTelefono);
				return true;
			case R.id.ctx_llamar_movil:
				
				llamadaTelefono = new Intent(Intent.ACTION_CALL);
				contacto = listaContactos.get(info.position);
				llamadaTelefono.setData(Uri.parse("tel:" + contacto.getMovil()));
				startActivity(llamadaTelefono);
				return true;
			case R.id.ctx_eliminar:
				
				eliminarContacto(info);
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}
	
	/*
	 * Elimina un contacto
	 */
	private void eliminarContacto(AdapterContextMenuInfo info) {
		
		// Desde el método del evento no tendrá acceso al parámetro
		final int posicion = info.position;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.esta_seguro_message)
				.setPositiveButton(R.string.btaceptar_label, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						adaptador.eliminar(posicion);						
					}
				})
				.setNegativeButton(R.string.btcancelar_label, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// En este caso no hacer nada
						
					}
				});
		AlertDialog dialogo = builder.create();
		dialogo.show();
	}

	/*
	 * Método que muestra el menú de opciones de esta Activity
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
	 * Método que se ejecuta cuando el usuario selecciona una opción
	 * del menú de opciones.
	 * Hay que evaluar que opción ha pulsado y hacer lo que convenga
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent intent = null;
		
		switch (item.getItemId()) {
		
			// Sólo hay una opción, la de lanzar la Activity de dar de alta un contacto
			case R.id.menu_nuevo_contacto:
				
				intent = new Intent(this, NuevoContacto.class);
				startActivity(intent);
				
				return true;
			case R.id.menu_preferencias:
				
				intent = new Intent(MainActivity.this, Preferencias.class);
				startActivity(intent);
				
				return true;
			case R.id.menu_acerca_de:
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(R.string.acerca_de_message)
						.setIcon(R.drawable.ic_contacts)
						.setTitle(R.string.acerca_de_title)
						.setPositiveButton(R.string.btaceptar_label, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();						
							}
						});
				AlertDialog dialogo = builder.create();
				dialogo.show();
			default:
				return super.onOptionsItemSelected(item);
		}
	}	
}
