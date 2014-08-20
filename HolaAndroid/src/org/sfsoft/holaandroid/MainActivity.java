package org.sfsoft.holaandroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity principal
 * En el método onCreate es donde se fija el layout de esta Activity
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MainActivity extends Activity {

	/*
	 * Este método se ejecuta cuando se crea por primera vez la Activity
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Fija el layout para esta activity
		setContentView(R.layout.activity_main);
		
		// Obtiene la referencia a la caja de texto (EditText)
		final EditText txtMensaje = (EditText) findViewById(R.id.txtMensaje);
		
		// Obtiene la referencia al botón (Button)
		Button btMensaje = (Button) findViewById(R.id.btMensaje);
		// Asigna un Listener de eventos click al botón
		btMensaje.setOnClickListener(new OnClickListener() {

			// En este método se escribe el código que atenderá al evento 'click' sobre el botón
			// El parámetro del método almacena una referencia al control que lo ha generado (en este caso el botón)
			@Override
			public void onClick(View arg0) {
							
				// En este caso se coloca un texto dentro de la caja de texto
				txtMensaje.setText("Hola Android");
				TextView tvSaludo = (TextView) findViewById(R.id.tvSaludo);
				tvSaludo.setText("");
			}
		});
	}
	
	/*
	 * Este método se ejecuta cada vez que la Activity vuelva del segundo plano y también la primera vez que ésta sea creada
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		
		super.onResume();
		
		// Muestra un mensaje en una etiqueta de texto definida en el layout de la activity
		TextView tvSaludo = (TextView) findViewById(R.id.tvSaludo);
		tvSaludo.setText("Hola de nuevo");
	}

	/*
	 * Este método se ejecuta cuando el usuario pulsar el botón de menú en esta Activity
	 * Para configurar dicho menú hay que trabajar con R.menu.main tal y como se indica en la primera línea de código del mismo
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
