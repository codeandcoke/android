package org.sfsoft.android_mapas;

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
import org.sfsoft.android_mapas.base.Gasolinera;
import org.sfsoft.android_mapas.base.GasolineraAdapter;
import org.sfsoft.android_mapas.util.Util;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * - Aplicación que muestra en un Mapa la localización de una serie de lugares
 * - Se ha utilizado la librería jcoord para convertir las coordenadas de UTM a Latitud-Longitud
 * 		Hay que tener cuidado porque utiliza una clase LatLng que coincide en nombre con la que se usa
 * 		en la API de Google Maps para ubicar las posiciones
 * 
 * - El destino del proyecto debe ser un móvil con Google API como sistema
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Android_Mapas extends FragmentActivity implements OnClickListener {
	
	private GoogleMap mapa;
	
	private Spinner spGasolineras;
	private GasolineraAdapter adapter;
	private ArrayList<Gasolinera> listaGasolineras;
	private Button btVerGasolinera;
	private Button btLimpiar;
	
	// URL donde se encuentran los datos de las gasolineras (en JSON)
	private static final String URL = "http://www.zaragoza.es/georref/json/hilo/estacionesDeServicio_Equipamiento";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android__mapas);
        
        listaGasolineras = new ArrayList<Gasolinera>();
        adapter = new GasolineraAdapter(this, R.layout.item, listaGasolineras);
        
        spGasolineras = (Spinner) findViewById(R.id.spGasolineras);
        spGasolineras.setAdapter(adapter);
        
        btVerGasolinera = (Button) findViewById(R.id.btVerGasolinera);
        btVerGasolinera.setOnClickListener(this);
        btLimpiar = (Button) findViewById(R.id.btLimpiar);
        btLimpiar.setOnClickListener(this);
        
        // Inicializa el sistema de mapas de Google
        //try {
            MapsInitializer.initialize(this);
        /*} catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }*/
        
        // Obtiene una referencia al objeto que permite "manejar" el mapa
        mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        
        cargarListaGasolineras();
    }
    
    private void cargarListaGasolineras() {
    	
    	TareaDescargaDatos tarea = new TareaDescargaDatos();
    	tarea.execute(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_android__mapas, menu);
        return true;
    }
	
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.btVerGasolinera:
			localizarGasolinera();
			break;
		case R.id.btLimpiar:
			limpiarMapa();
			break;
		default:
			break;
		}
	}
	
	/*
	 * Localiza una gasolinera en el mapa haciendo una marca en su posición
	 */
	private void localizarGasolinera() {
		
		if (spGasolineras.getSelectedItemPosition() == Spinner.INVALID_POSITION) {
			return;
		}
		
		Gasolinera gasolinera = listaGasolineras.get(spGasolineras.getSelectedItemPosition());
		
		// Prepara y añade una nueva marca al mapa
		mapa.addMarker(new MarkerOptions()
			.position(gasolinera.getPosicion())
			.title(gasolinera.getNombre()));
		
		// Posiciona la vista del usuario en el punto que se acaba de agregar
    	CameraUpdate camara =
    			CameraUpdateFactory.newLatLng(gasolinera.getPosicion());
        	 
    	// Coloca la vista del mapa sobre la posición del restaurante
    	// y activa el zoom para verlo de cerca
    	mapa.moveCamera(camara);
    	mapa.animateCamera(CameraUpdateFactory.zoomTo(12.0f)); 
	}
	
	/*
	 * Elimina las marcas del mapa
	 */
	private void limpiarMapa() {
		
		mapa.clear();
	}
	
	private class TareaDescargaDatos extends AsyncTask<String, Void, Void> {

    	private boolean error = false;
    	
    	// Este método no puede acceder a la interfaz, puesto que se ejecuta en segundo plano
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
		    		// Posici�n de la gasolinera
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
		protected void onProgressUpdate(Void... progreso) {
			super.onProgressUpdate(progreso);
			
			adapter.notifyDataSetChanged();
		}
		
		@Override
		protected void onPostExecute(Void resultado) {
			super.onPostExecute(resultado);
			
			if (error) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
				return;
			}
			
			adapter.notifyDataSetChanged();
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.datos_message), Toast.LENGTH_SHORT).show();
		}
    }
}
