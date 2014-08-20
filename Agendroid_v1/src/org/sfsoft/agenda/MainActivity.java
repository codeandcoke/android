package org.sfsoft.agenda;

import java.util.ArrayList;

import org.sfsoft.agenda.base.Contacto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity principal donde se listan los contactos de la Agenda
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
	 * Lo invoca Android antes de que la Activity termine
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
		
		adaptador.notifyDataSetChanged();
	}
	
	/*
	 * Carga un menú contextual en pantalla
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		
		getMenuInflater().inflate(R.menu.menu_contextual, menu);
	}
	
	/*
	 * Se invoca cuando se selecciona un elemento del menú contextual
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		/*
		 *  Contiene información sobre el elemento del menú contextual
		 *  sobre el que se ha pulsado
		 */
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		switch (item.getItemId()) {
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
		
		listaContactos.remove(info.position);
		adaptador.notifyDataSetChanged();
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
		
		switch (item.getItemId()) {
		
			// Sólo hay una opción, la de lanzar la Activity de dar de alta un contacto
			case R.id.menu_nuevo_contacto:
				
				Intent intent = new Intent(this, NuevoContacto.class);
				startActivity(intent);
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}	
}