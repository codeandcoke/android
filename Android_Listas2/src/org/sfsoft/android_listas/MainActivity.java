package org.sfsoft.android_listas;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Activity de tipo Lista que lista una serie de elementos, entre los que se pueden seleccionar varios
 * @author Santiago Faci
 *
 */
public class MainActivity extends ListActivity {

	// Vector con los elementos que se añadirán a la lista
	private String elementos[] = {"uno", "dos", "tres", "cuatro", "cinco", 
			"seis", "siete", "ocho", "nueve", "diez"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Configura el adaptador que enlaza los datos con la view (en este caso la propia activity, this)
		// La lista permitirá seleccionar varios elementos, puesto que usamos el layout "item_multiple_choice"
		// Si escogemos el layout "item_single_choice" la lista sólo permitirá seleccionar un elemento
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, elementos));
	}
}
