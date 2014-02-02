package org.sfsoft.android_listas;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Activity que muestra una serie de elementos en una ListActivity
 * En este caso se ha diseñado el layout para cada fila
 * @author Santiago Faci
 *
 */
public class MainActivity extends Activity implements OnItemClickListener {

	private TextView seleccion;
	// Vector con los elementos que se añadirán a la lista
	private String elementos[] = {"uno", "dos", "tres", "cuatro", "cinco", 
			"seis", "siete", "ocho", "nueve", "diez"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lvLista = (ListView) findViewById(R.id.lvLista);
		// Configura el adapter que enlazará los datos con la lista
		// En este caso el layout es R.layout.fila, que está diseñado para este proyecto
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.fila, elementos);
		lvLista.setAdapter(adapter);
		lvLista.setOnItemClickListener(this);
		
		seleccion = (TextView) findViewById(R.id.seleccion);
	}

	/*
	 * Controla el evento "Hacer click en un elemento de la lista"
	 */
	@Override
	public void onItemClick(AdapterView<?> padre, View view, int posicion, long id) {
		seleccion.setText(elementos[posicion]);
		
	}
}