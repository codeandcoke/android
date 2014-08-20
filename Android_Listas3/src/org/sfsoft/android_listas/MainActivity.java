package org.sfsoft.android_listas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Activity que muestra una serie de elementos en un control Spinner
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MainActivity extends Activity implements OnItemSelectedListener {

	private TextView seleccion;
	// Vector con los elementos que se añadirán a la lista
	private String elementos[] = {"uno", "dos", "tres", "cuatro", "cinco", 
			"seis", "siete", "ocho", "nueve", "diez"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Configura el adapter que enlazará los datos con el control Spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elementos);
		// Configura el layout para el desplegable del control Spinner
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		// Añade el Listener al control Spinner (está definido en la propia clase)
		spinner.setOnItemSelectedListener(this);
		// Enlaza el adapter con el control Spinner
		spinner.setAdapter(adapter);
		
		seleccion = (TextView) findViewById(R.id.seleccion);
	}

	/*
	 * Controla el evento "seleccionar un elemento del Spinner"
	 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int posicion,
			long id) {
		seleccion.setText(elementos[posicion]);
	}

	/*
	 * Controla el evento "No hay nada seleccionado"
	 * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android.widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		seleccion.setText("");
	}

}
