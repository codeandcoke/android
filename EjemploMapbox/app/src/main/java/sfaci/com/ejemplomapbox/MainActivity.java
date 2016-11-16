package sfaci.com.ejemplomapbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

/**
 * Aplicación ejemplo que muestra cómo cargar un Mapa utilizando la librería Mapbox
 * Se debe crear una cuenta en http://www.mapbox.com para obtener un token que permita
 * utilizar el servicio de mapas que proporcionan
 *
 * Se pueden ver ejemplos de código en https://www.mapbox.com/android-sdk/examples/
 *
 * @author Santiago Faci
 * @version curso 2016-2017
 */
public class MainActivity extends AppCompatActivity {

    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapboxAccountManager.start(this, "Pon aqui tu token");

        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.mapa);
        mapView.onCreate(savedInstanceState);
    }
}
