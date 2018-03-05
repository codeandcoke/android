package org.sfaci.guiarestaurantes;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import util.Util;


/**
 * Activity que muestra la ubicación de un restaurante en el Mapa
 * @author Santiago Faci
 * @version curso 2015-2016
 */
public class Mapa extends Activity implements LocationListener {

    private GoogleMap mapa;

    private double latitud;
    private double longitud;
    private String nombre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Recoge los datos enviados por la Activity que la invoca
        Intent i = getIntent();
        latitud = i.getFloatExtra("latitud", 0);
        longitud = i.getFloatExtra("longitud", 0);
        nombre = i.getStringExtra("nombre");

        // Transforma las coordenadas al sistema LatLng y las almacena
        uk.me.jstott.jcoord.LatLng ubicacion = Util.DeUMTSaLatLng(latitud, longitud, 'N', 30);
        this.latitud = ubicacion.getLat();
        this.longitud = ubicacion.getLng();

        // Inicializa el sistema de mapas de Google
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtiene una referencia al objeto que permite "manejar" el mapa
        mapa = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    }

    @Override
    public void onResume() {
        super.onResume();

        ubicarRestaurante();
    }

    /**
     * Marca el restaurante elegido en el mapa
     */
    private void ubicarRestaurante() {

        // Obtiene una vista de cámara
        CameraUpdate camara =
                CameraUpdateFactory.newLatLng(new LatLng(latitud, longitud));

        // Coloca la vista del mapa sobre la posición del restaurante
        // y activa el zoom para verlo de cerca
        mapa.moveCamera(camara);
        mapa.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

        // Añade una marca en la posición del restaurante con el nombre de éste
        mapa.addMarker(new MarkerOptions()
                .position(new LatLng(latitud, longitud))
                .title(nombre));

        mapa.setMyLocationEnabled(true);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}