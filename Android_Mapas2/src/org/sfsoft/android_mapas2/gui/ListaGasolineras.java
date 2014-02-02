package org.sfsoft.android_mapas2.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sfsoft.android_mapas2.R;
import org.sfsoft.android_mapas2.base.Gasolinera;
import org.sfsoft.android_mapas2.base.GasolineraAdapter;
import org.sfsoft.android_mapas2.util.Util;

import static org.sfsoft.android_mapas2.util.Constantes.URL;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Fragment de la aplicaci�n que se corresponder�
 * con una de las pesta�as de la aplicaci�n
 * En este caso es la lista donde se muestran los alumnos
 * 
 * @author Santiago Faci
 *
 */
public class ListaGasolineras extends Fragment implements OnItemClickListener {

	private ArrayList<Gasolinera> listaGasolineras;
	private GasolineraAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
	    
	    super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.lista_gasolineras, container, false);
		
		listaGasolineras = new ArrayList<Gasolinera>();
		ListView lvListaGasolineras = (ListView) view.findViewById(R.id.lvListaGasolineras);
		adapter = new GasolineraAdapter(getActivity(), R.layout.item, listaGasolineras);
		lvListaGasolineras.setAdapter(adapter);
		lvListaGasolineras.setOnItemClickListener(this);
		
		cargarListaGasolineras();
		
		return view;
	}
	
	private void cargarListaGasolineras() {
		
		TareaDescargaDatos tarea = new TareaDescargaDatos();
		tarea.execute(URL);
	}
	
	private class TareaDescargaDatos extends AsyncTask<String, Void, Void> {

    	private boolean error = false;
    	
    	// Este m�todo no puede acceder a la interfaz
		@Override
		protected Void doInBackground(String... urls) {
			
			InputStream is = null;
	    	String resultado = null;
	    	JSONObject json = null;
	    	JSONArray jsonArray = null;
	    	
	    	try {
	    		// Conecta con la URL y obtenemos el fichero con los datos
		    	HttpClient clienteHttp = new DefaultHttpClient();
		    	HttpPost httpPost = new HttpPost(URL);
		    	HttpResponse respuesta = clienteHttp.execute(httpPost);
		    	HttpEntity entity = respuesta.getEntity();
		    	is = entity.getContent();
		    	
		    	// Lee el fichero de datos y genera una cadena de texto como resultado
		    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    	StringBuilder sb = new StringBuilder();
		    	String linea = null;
		    	
		    	while ((linea = br.readLine()) != null) {
		    		sb.append(linea + "\n");
		    	}
		    	
		    	is.close();
		    	resultado = sb.toString();
		    	
		    	// La cadena de texto resultante es un objeto JSON
		    	json = new JSONObject(resultado);
		    	// Obtiene el objeto JSON como un array de datos
		    	jsonArray = json.getJSONArray("features");
		    	
		    	Gasolinera gasolinera = null;
		    	String localizacion = null;
		    	// Recorre el array JSON para mostrar los datos que interesen
		    	for (int i = 0; i < jsonArray.length(); i++) {
		    		gasolinera = new Gasolinera();
		    		// Nombre de la gasolinera
		    		gasolinera.setNombre(jsonArray.getJSONObject(i).getJSONObject("properties").getString("title"));
		    		// Posición de la gasolinera
		    		localizacion = jsonArray.getJSONObject(i).getJSONObject("geometry").getString("coordinates");
		    		gasolinera.setPosicion(Util.parseCoordenadas(localizacion));
		    		
		    		listaGasolineras.add(gasolinera);
		    	}
		    	  	
	    	} catch (ClientProtocolException cpe) {
	    		cpe.printStackTrace();
	    	} catch (IOException ioe) {
	    		ioe.printStackTrace();
	    	} catch (JSONException jse) {
	    		jse.printStackTrace();
	    	}
	    	
	    	return null;
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
			adapter.clear();
			listaGasolineras = new ArrayList<Gasolinera>();
		}
		
		@Override
		protected void onPostExecute(Void resultado) {
			super.onPostExecute(resultado);
			
			if (error) {
				Toast.makeText(getActivity(), getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
				return;
			}
			
			adapter.notifyDataSetChanged();
		}
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int posicion, long id) {
		
		Gasolinera gasolinera = listaGasolineras.get(posicion);
		Intent i = new Intent(getActivity(), Mapa.class);
		i.putExtra("accion", "gasolinera");
		i.putExtra("gasolinera", gasolinera);
		startActivity(i);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.opcion_mapa:
	        Intent i = new Intent(getActivity(), Mapa.class);
	        i.putExtra("accion", "gasolineras");
	        i.putParcelableArrayListExtra("gasolineras", listaGasolineras);
	        startActivity(i);
	        return true;
	    
	    default:
	        break;
	    }

	    return false;
	}
}
