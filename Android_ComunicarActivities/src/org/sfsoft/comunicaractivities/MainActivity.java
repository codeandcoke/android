package org.sfsoft.comunicaractivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Aplicación que muestra como comunicar dos Activities
 * En este caso la activity principal envía un mensaje a la 
 * otra activity
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MainActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	Button btEnviar = (Button) findViewById(R.id.btEnviar);
    	btEnviar.setOnClickListener(this);
    }

	public void onClick(View view) {
		
		EditText txtMensaje = (EditText) findViewById(R.id.txtMensaje); 
		
		/*
		 * Prepara una operación que utilizaremos para lanzar la Activity 'LeerMensaje'
		 */
		Intent intent = new Intent(MainActivity.this, LeerMensaje.class);
		/*
		 * Dentro del objeto bundle se envía la información a la otra
		 * Activity
		 */
		Bundle bundle = new Bundle();
		bundle.putString("mensaje", txtMensaje.getText().toString());
		/*
		 * Añade la información al objeto Intent que lanzará la nueva Activity
		 */
		intent.putExtras(bundle);
		
		/*
		 * Lanza la Activity
		 */
		startActivity(intent);
	}
}
