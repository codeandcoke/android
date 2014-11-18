package org.sfsfot.android_ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase de ejemplo para cargar y parsear datos JSON
 * Carga el listado de gasolineras y su posición utilizando como fuente de datos
 * los que proporciona el Ayuntamiento de Zaragoza
 * 
 * También permite comprobar cómo capturar la posición
 * del dispositivo con el GPS
 * 
 * En este caso la carga de datos se realiza como una tarea en segundo plano
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class AndroidWS extends Activity implements OnClickListener, LocationListener {

	// URL donde se encuentra el fichero JSON con toda la información
	private static final String URL = "http://www.zaragoza.es/georref/json/hilo/estacionesDeServicio_Equipamiento";
	
	private Button btCargar;
	private ListView lvLista;
	private TextView lbPosicion;
	private ArrayAdapter<String> adaptador;
	private ArrayList<String> listaDatos;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_ws);
        
        btCargar = (Button) findViewById(R.id.btCargar);
        btCargar.setOnClickListener(this);
        
        lbPosicion = (TextView) findViewById(R.id.lbPosicion);
        
        lvLista = (ListView) findViewById(R.id.lvLista);
        listaDatos = new ArrayList<String>();
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaDatos);
        lvLista.setAdapter(adaptador);
        
        calculaMiPosicion();
    }
    
    private void calculaMiPosicion() {
    	
    	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    
    public void onLocationChanged(Location loc) {
    	
    	lbPosicion.setText(loc.getLatitude() + ":" + loc.getAltitude());
    }
    
    public void onProviderDisabled(String provider) {
    	
    	Toast.makeText(this, "GPS Desactivado", Toast.LENGTH_SHORT).show();
    }
    
    public void onProviderEnabled(String provider) {
    	
    	Toast.makeText(this, "GPS Activado", Toast.LENGTH_SHORT).show();
    }
    
    public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
    
    public void onClick(View view) {
    	
    	TareaDescargaDatos tarea = new TareaDescargaDatos();
    	tarea.execute(URL);
    }
    
    /**
     * Clase privada que define la tarea que descarga y pinta los datos
     *
     * - Es posible invocar a publishPogress() para notificar el avance de la tarea, lo que
     *   hace invocar al método onProgressUpdate
     * - También es posible llamar al método isCancelled() para comprobar si se ha
     *   cancelado la tarea
     * 
     * @author Santiago Faci
     * @version curso 2014-2015
     */
    private class TareaDescargaDatos extends AsyncTask<String, Void, Void> {

    	private boolean error = false;
    	
    	// Este método no puede acceder a la interfaz
		@Override
		protected Void doInBackground(String... urls) {
			
			InputStream is = null;
	    	String resultado = null;
	    	JSONObject json = null;
	    	JSONArray jsonArray = null;
	    	
	    	try {
	    		// Conecta con la URL y obtenemos el fichero con los datos
		    	HttpClient clienteHttp = new DefaultHttpClient();
		    	HttpPost httpPost = new HttpPost(urls[0]);
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
		    	
		    	// Recorre el array JSON para mostrar los datos que interesen
		    	for (int i = 0; i < jsonArray.length(); i++) {
		    		// Nombre de la gasolinera
		    		listaDatos.add(jsonArray.getJSONObject(i).getJSONObject("properties").getString("title"));
		    		// Posición de la gasolinera
		    		listaDatos.add(jsonArray.getJSONObject(i).getJSONObject("geometry").getString("coordinates"));
		    		// TODO Mostrar otros campos
		    	}
		    	
	    	} catch (ClientProtocolException cpe) {
	    		cpe.printStackTrace();
	    		error = true;
	    	} catch (IOException ioe) {
	    		ioe.printStackTrace();
	    		error = true;
	    	} catch (JSONException jse) {
	    		jse.printStackTrace();
	    		error = true;
	    	}
	    	
	    	return null;
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
			adaptador.clear();
			listaDatos = new ArrayList<>();
		}
		
		@Override
		protected void onProgressUpdate(Void... progreso) {
			super.onProgressUpdate(progreso);
			
			adaptador.notifyDataSetChanged();
		}
		
		@Override
		protected void onPostExecute(Void resultado) {
			super.onPostExecute(resultado);
			
			if (error) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
				return;
			}
			
			adaptador.notifyDataSetChanged();
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.datos_message), Toast.LENGTH_SHORT).show();
		}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_android_ws, menu);
        return true;
    }
}
