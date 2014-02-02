package org.sfsoft.android_mapas2.gui;

import java.util.ArrayList;

import org.sfsoft.android_mapas2.R;
import org.sfsoft.android_mapas2.base.Gasolinera;
import org.sfsoft.android_mapas2.base.Ubicacion;
import org.sfsoft.android_mapas2.database.Database;
import org.sfsoft.android_mapas2.util.Constantes;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Activity que representa el mapa de la aplicación
 * 
 * @author Santiago Faci
 *
 */

public class Mapa extends FragmentActivity implements OnClickListener, OnMarkerClickListener, 
	ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

	private GoogleMap mapa;
	private Database database;
	private Marker marker;
	private LocationClient locationClient;
	
	private static final LocationRequest LOC_REQUEST = LocationRequest.create()
            .setInterval(5000)         
            .setFastestInterval(16)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
		
		Button btGuardarPosicion = (Button) findViewById(R.id.btGuardarPosicion);
		btGuardarPosicion.setOnClickListener(this);
		Button btVerMiPosicion = (Button) findViewById(R.id.btVerMiPosicion);
		btVerMiPosicion.setOnClickListener(this);
		Button btDistancia = (Button) findViewById(R.id.btDistancia);
		btDistancia.setOnClickListener(this);
		
		// Inicializa el sistema de mapas de Google
        try {
            MapsInitializer.initialize(this);
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        
        // Obtiene una referencia al objeto que permite "manejar" el mapa
        mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mapa.setOnMarkerClickListener(this);
        
        // Comprueba si hay que mostrar una gasolinera o toda la lista
        String accion = getIntent().getStringExtra("accion");
        if (accion.equals("gasolinera")) {
	        Gasolinera gasolinera = (Gasolinera) getIntent().getParcelableExtra("gasolinera");
	        if (gasolinera != null)
	        	marcarGasolinera(gasolinera);
        }
        else if (accion.equals("gasolineras")){
        	ArrayList<Gasolinera> gasolineras = getIntent().getParcelableArrayListExtra("gasolineras");
        	if (gasolineras != null) {
        		marcarGasolineras(gasolineras);
        	}
        }
        else {
        	// Sólo mostrar mapa
        }
        
        // Activa el botón para localizar mi posición
        mapa.setMyLocationEnabled(true);
        
        database = new Database(this);
        
        // Configura el gestor de localizaciones
        configuraLocalizador();
	}
	
	/**
	 * Se muestra la Activity
	 */
	@Override
	protected void onStart() {
		
		super.onStart();
		locationClient.connect();
	}


	/**
	 * Se oculta la Activity
	 */
	@Override
    protected void onStop() {
   
        locationClient.disconnect();
        super.onStop();
    }

	/**
	 * Configura el gestor de localizaciones
	 */
	private void configuraLocalizador() {
        if (locationClient == null) {
            locationClient = new LocationClient(this, this, this);
        }
    }

	/**
	 * Añade la marca de una gasolinera
	 */
	private void marcarGasolinera(Gasolinera gasolinera) {
		
		// Prepara y añade una nueva marca al mapa
		mapa.addMarker(new MarkerOptions()
			.position(gasolinera.getPosicion())
			.title(gasolinera.getNombre()));
		
		// Posiciona la vista del usuario en el punto que se acaba de agregar
    	CameraUpdate camara =
    			CameraUpdateFactory.newLatLng(gasolinera.getPosicion());
        	 
    	// Coloca la vista del mapa sobre la posición de la gasolinera 
    	// y activa el zoom para verlo de cerca
    	mapa.moveCamera(camara);
    	mapa.animateCamera(CameraUpdateFactory.zoomTo(12.0f), 2000, null);
	}
	
	/**
	 * Añade las marcas de todas las gasolineras
	 * @param gasolineras
	 */
	private void marcarGasolineras(ArrayList<Gasolinera> gasolineras) {
		
		if (gasolineras.size() > 0) {
			for (Gasolinera gasolinera : gasolineras) {
				
				marcarGasolinera(gasolinera);
			}
		}
		
		// Posiciona la vista del usuario en Zaragoza
    	CameraUpdate camara =
    			CameraUpdateFactory.newLatLng(Constantes.ZARAGOZA);
        	 
    	// Coloca la vista del mapa sobre la posición de la ciudad 
    	// y activa el zoom para verlo de cerca
    	mapa.moveCamera(camara);
    	mapa.animateCamera(CameraUpdateFactory.zoomTo(9.0f), 2000, null);
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btGuardarPosicion:
			// Guarda la posición actual en la Base de Datos
			Location location = mapa.getMyLocation();
			Ubicacion ubicacion = new Ubicacion();
			ubicacion.setNombre("");
			ubicacion.setPosicion(new LatLng(location.getLatitude(), location.getLongitude()));
			database.nuevaUbicacion(ubicacion);
			break;
		case R.id.btVerMiPosicion:
			
			break;
		case R.id.btDistancia:
			// Calcula la distancia en metros entre la posición actual y la última marca pulsada
			Location yo = locationClient.getLastLocation();
			if (marker != null) {
				Location loc = new Location(marker.getTitle());
				loc.setLatitude(marker.getPosition().latitude);
				loc.setLongitude(marker.getPosition().longitude);
				
				Toast.makeText(this, String.valueOf(yo.distanceTo(loc)), Toast.LENGTH_LONG).show();
			}
			
			break;
		default:
			break;
		}
	}

	/**
	 * Se pulsa una marca en el mapa
	 */
	@Override
	public boolean onMarkerClick(Marker marker) {
		
		this.marker = marker;
		
		return false;
	}

	/**
	 * Falla la conexión con el GPS
	 */
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
	}

	/**
	 * Se conecta el GPS
	 */
	@Override
	public void onConnected(Bundle arg0) {
		
		// Solicitará actualizaciones de localización según el objeto LOC_REQUEST definido más arriba
		locationClient.requestLocationUpdates(LOC_REQUEST, this);
	}

	
	/**
	 * Se desconecta el GPS
	 */
	@Override
	public void onDisconnected() {
	}

	/**
	 * Cambia la ubicación del dispositivo
	 */
	@Override
	public void onLocationChanged(Location arg0) {
	}
}
