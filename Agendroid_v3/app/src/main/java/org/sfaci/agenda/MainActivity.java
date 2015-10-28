package org.sfaci.agenda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.sfaci.agenda.base.Amigo;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {

    public static ArrayList<Amigo> listaAmigos = new ArrayList<Amigo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btRegistro = (Button) findViewById(R.id.btRegistro);
        btRegistro.setOnClickListener(this);
        Button btListado = (Button) findViewById(R.id.btListado);
        btListado.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btRegistro:
                // El usuario ha pulsado el botón de Registro. Se lanza la Activity para registrar un nuevo Amigo
                startActivity(new Intent(this, Registro.class));
                break;
            case R.id.btListado:
                // El usuario ha pulsado el botón de Listado. Se mostrará el listado de amigos registrados
                startActivity(new Intent(this, Listado.class));
                break;
            default:
                break;
        }
    }
}
