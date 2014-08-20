package org.sfsoft.android_listas;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Activity de tipo Lista que lista una serie de elementos
 * Al pinchar en uno de ellos se muestra en una caja de texto
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MainActivity extends ListActivity {

	private TextView seleccion;
	// Vector con los elementos que se añadirán a la lista
	private String elementos[] = {"uno", "dos", "tres", "cuatro", "cinco", 
			"seis", "siete", "ocho", "nueve", "diez"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Configura el adaptador que enlaza los datos con la view (en este caso la propia activity, this)
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elementos));
		
		seleccion = (TextView) findViewById(R.id.seleccion);
	}

	/*
	 * Gestiona el evento click sobre algún elemento de la lista
	 * Muestra el elemento seleccionado en la caja de texto
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	public void onListItemClick(ListView padre, View v, int posicion, long id) {
		
		seleccion.setText(elementos[posicion]);
	}

}
