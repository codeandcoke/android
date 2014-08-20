package org.sfsoft.comunicaractivities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

/**
 * Clase que recibe información de otra Activity
 * que ha lanzado su ejecución
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class LeerMensaje extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_mensaje);
        
        TextView lbMensaje = (TextView) findViewById(R.id.lbMensaje);
        
        // Recibe la información de la otra Activity
        Bundle bundle = getIntent().getExtras();
        // Muestra el mensaje enviado en una etiqueta de texto
        lbMensaje.setText("Mensaje: " + bundle.getString("mensaje"));
        
        
    }
}
