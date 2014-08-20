package org.sfsoft.android_listas;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Activity que muestra una serie de elementos en una ListActivity
 * En este caso se ha diseñado el layout para cada fila y además éste puede cambiar
 * en tiempo de ejecución
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MainActivity extends Activity {

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
		ImagenAdapter adapter = new ImagenAdapter(this, R.layout.fila, R.id.tvElemento, elementos);
		lvLista.setAdapter(adapter);
	}
}