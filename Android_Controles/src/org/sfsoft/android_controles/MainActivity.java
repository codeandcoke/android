package org.sfsoft.android_controles;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ToggleButton;

/**
 * Clase que muestra algunos de los principales controles
 * para el diseño de interfaces de usuario
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final EditText editText = (EditText) findViewById(R.id.editText);
		
		// Botón interruptor
		final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		// Evento que se produce cuando cambia su estado (encendido/apagado)
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				if (isChecked) 
					// Muestra un mensaje emergente en la parte baja de la pantalla durante un tiempo determinado (LONG, SHORT)
					Toast.makeText(getApplicationContext(), "Encendido", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Apagado", Toast.LENGTH_SHORT).show();
			}
			
		});
		
		// CheckBox
		final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
		// Evento que se produce cuando cambia su estado (marcado/no marcado)
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if (isChecked)
					Toast.makeText(getApplicationContext(), "El texto se eliminará", Toast.LENGTH_SHORT).show();
				else 
					Toast.makeText(getApplicationContext(), "El texto no se eliminará", Toast.LENGTH_SHORT).show();
			}
			
		});
		
		// Grupo de botones radio
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		// Evento que se produce cuando se modifica la selección del grupo de botones
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				// Evalua qué botón del grupo está seleccionado
				switch(checkedId) {
					case R.id.radioButton1:
						Toast.makeText(getApplicationContext(), "Has elegido la primera opción", Toast.LENGTH_SHORT).show();
						break;
					case R.id.radioButton2:
						Toast.makeText(getApplicationContext(), "Has elegido la segunda opción", Toast.LENGTH_SHORT).show();
						break;
					case R.id.radioButton3:
						Toast.makeText(getApplicationContext(), "Has elegido la tercera opción", Toast.LENGTH_SHORT).show();
						break;
					default:
						break;
				}
			}
			
		});
		
		// Texto + CheckBox
		final CheckedTextView checkedTextView = (CheckedTextView) findViewById(R.id.checkedTextView);
		// Evento que se produce cuando se hace click
		checkedTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// Cambia el estado del CheckedTextView
				checkedTextView.setChecked(!checkedTextView.isChecked());
			}
		});

		// Botón
		Button button = (Button) findViewById(R.id.button);
		// Evento que se produce cuando se hace click en él
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// Si se ha marcado asi, elimina el texto de la caja de texto
				if (checkBox.isChecked())
					editText.setText("");
				
				RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
				RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
				RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
				// Comprueba la opción seleccionada entre los botones radio
				if (radioButton1.isChecked())
					Toast.makeText(getApplicationContext(), "Has elegido la primera opción", Toast.LENGTH_SHORT).show();
				else if (radioButton2.isChecked())
					Toast.makeText(getApplicationContext(), "Has elegido la segunda opción", Toast.LENGTH_SHORT).show();
				else if (radioButton3.isChecked())
					Toast.makeText(getApplicationContext(), "Has elegido la tercera opción", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "No has elegido ninguna opción", Toast.LENGTH_SHORT).show();
				
				// Comprueba el estado del CheckedTextView
				if (checkedTextView.isChecked())
					Toast.makeText(getApplicationContext(), "CheckedTextView está marcado", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "CheckedTextView no está marcado", Toast.LENGTH_SHORT).show();
				
				// Comprueba el estado del interruptor
				if (toggleButton.isChecked())
					Toast.makeText(getApplicationContext(), "El interruptor está encendido", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "El interruptor está apagado", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
