package org.sfsoft.android_listas;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Activity que muestra el uso de una caja de texto autocompletable
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MainActivity extends Activity implements TextWatcher {

	private AutoCompleteTextView autoSpinner;
	private TextView seleccion;
	// Vector con los elementos que se añadirán a la lista
	private String elementos[] = {"uno", "dos", "tres", "cuatro", "cinco", 
			"seis", "siete", "ocho", "nueve", "diez"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Configura el adapter que enlazará los datos con el control Spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, elementos);
		
		autoSpinner = (AutoCompleteTextView) findViewById(R.id.autospinner);
		// Añade el Listener al control Spinner (está definido en la propia clase)
		autoSpinner.addTextChangedListener(this);
		// Enlaza el adapter con el control Spinner
		autoSpinner.setAdapter(adapter);
		
		seleccion = (TextView) findViewById(R.id.seleccion);
	}

	/*
	 * Controla el evento "Después de cambiar el texto" de la caja de texto autocompletable"
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable arg0) {

	}

	/*
	 * Controla el evento "Antes de cambiar el texto" de la caja de texto autocompletable"
	 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void beforeTextChanged(CharSequence arg0, int inicio, int antes,
			int cantidad) {
	}

	/*
	 * Controla el evento "se ha cambiado el texto de la caja de texto autocompletable"
	 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void onTextChanged(CharSequence arg0, int inicio, int antes, int cantidad) {
		seleccion.setText(autoSpinner.getText());
	}
}
