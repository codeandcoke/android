package org.sfsoft.android_listas;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity que muestra como realizar cambios en una ListView
 * En este caso se ha diseñado el layout para cada fila
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MainActivity extends Activity implements OnItemLongClickListener, OnItemClickListener {

	private TextView tvSeleccion;
	private ListView lvLista;
	// Vector con los elementos que se añadirán a la lista
	private String elementos[] = {"uno", "dos", "tres", "cuatro", "cinco", 
			"seis", "siete", "ocho", "nueve", "diez"};
	private ArrayList<String> listaElementos;
	private ArrayAdapter<String> adaptador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvSeleccion = (TextView) findViewById(R.id.tvSeleccion);
		lvLista = (ListView) findViewById(R.id.lvLista);
		lvLista.setOnItemClickListener(this);
		lvLista.setOnItemLongClickListener(this);
		
		listaElementos = new ArrayList<String>();
		listaElementos.addAll(Arrays.asList(elementos));
		
		// Configura el adapter que enlazará los datos con la lista
		// En este caso el layout es R.layout.fila, que está diseñado para este proyecto
		adaptador = new ArrayAdapter<String>(this, R.layout.fila, listaElementos);
		lvLista.setAdapter(adaptador);
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> padre, View v, int posicion, long id) {
		
		listaElementos.remove(posicion);
		adaptador.notifyDataSetChanged();
		
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.eliminado), Toast.LENGTH_SHORT).show();
		
		return false;
	}

	/*
	 * 
	 */
	@Override
	public void onItemClick(AdapterView<?> padre, View v, int posicion, long id) {
		
		tvSeleccion.setText(listaElementos.get(posicion));
	}
}