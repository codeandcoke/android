package org.sfsoft.android_sonido;

import java.util.ArrayList;
import java.util.List;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

/**
 * - Ejemplo que reproduce sonidos
 * - Parece ser que no todos los formatos funcionan correctamente en el emulador. Mejor utilizar mp3
 * - Los ficheros de las canciones se tienen que copia en res.raw
 * - La aplicación lista una serie de sonidos y el usuario puede escoger cual reproducir y pararlo en 
 * cualquier momento
 * @author Santiago Faci
 *
 */
public class Sonido extends Activity implements OnClickListener {
	
	private Spinner spCanciones;
	private Button btPlay;
	private Button btStop;
	private MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonido);
        
        // El sonido multimedia es el que se utilizará para la aplicación
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        spCanciones = (Spinner) findViewById(R.id.spCanciones);
        btPlay = (Button) findViewById(R.id.btPlay);
        btPlay.setOnClickListener(this);
        btStop = (Button) findViewById(R.id.btStop);
        btStop.setOnClickListener(this);
        
        listarCanciones();
    }
    
    public void listarCanciones() {
    	
    	List<String> canciones = new ArrayList<String>();
        canciones.add("night");
        canciones.add("thunder");
        spCanciones.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, canciones));
    }
    
    public void onClick(View view) {
    	
    	int recursoId = 0;
    	
    	Toast.makeText(this, spCanciones.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
    	
    	// Asigna el id del recurso de sonido seleccionado
    	if (spCanciones.getSelectedItem().toString().equals("thunder")) {
    		recursoId = R.raw.track1;
    	}
    	else {
    		recursoId = R.raw.track2;
    	}
    	
    	switch (view.getId()) {
    	case R.id.btPlay:
    		// Reproduce el sonido
    		mp = MediaPlayer.create(this, recursoId);
    		mp.setLooping(true);
    		mp.start();
    		break;
    	case R.id.btStop:
    		// Detiene la reproducción en curso
    		if (mp != null) {
    			mp.stop();
    			mp.release();
    			mp = null;
    		}
    		break;
    	default:
    		break;
    	}
    }    
}
