package org.sfaci.guiagasolineras2016.gui;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;

import org.sfaci.guiagasolineras2016.R;
import org.sfaci.guiagasolineras2016.base.Ubicacion;
import org.sfaci.guiagasolineras2016.database.Database;


/**
 * Activity que muestra el formulario para registrar una nueva Activity
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class NuevaUbicacion extends Activity implements View.OnClickListener {

    private String accion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_ubicacion);

        findViewById(R.id.btAceptar).setOnClickListener(this);
        findViewById(R.id.btCancelar).setOnClickListener(this);

        accion = getIntent().getStringExtra("accion");
        // Si se va a modificar una Ubicación existente, se cargan sus datos
        if (accion.equals("modificar")) {

            Ubicacion ubicacion = getIntent().getParcelableExtra("ubicacion");

            ((EditText) findViewById(R.id.etNombreUbicacion)).setText(ubicacion.getNombre());
            ((EditText) findViewById(R.id.etLatitud)).setText(String.valueOf(ubicacion.getPosicion().latitude));
            ((EditText) findViewById(R.id.etLongitud)).setText(String.valueOf(ubicacion.getPosicion().longitude));
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btAceptar:
                // Registra o modificar la Ubicación
                String nombre = ((EditText) findViewById(R.id.etNombreUbicacion)).getText().toString();
                double latitud = Double.parseDouble(((EditText) findViewById(R.id.etLatitud)).getText().toString());
                double longitud = Double.parseDouble(((EditText) findViewById(R.id.etLongitud)).getText().toString());

                Ubicacion ubicacion = null;
                if (accion.equals("nuevo"))
                    ubicacion = new Ubicacion();
                else if (accion.equals("modificar"))
                    ubicacion = getIntent().getParcelableExtra("ubicacion");

                ubicacion.setNombre(nombre);
                ubicacion.setPosicion(new LatLng(latitud, longitud));

                Database db = new Database(this);

                if (accion.equals("nuevo"))
                    db.nuevaUbicacion(ubicacion);
                else if (accion.equals("modificar"))
                    db.modificarUbicacion(ubicacion);

                Toast.makeText(this, R.string.nueva_ubicacion_message, Toast.LENGTH_SHORT).show();

                break;
            case R.id.btCancelar:
                finish();
                break;
            default:
                break;
        }
    }
}