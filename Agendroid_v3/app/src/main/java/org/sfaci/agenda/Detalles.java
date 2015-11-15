package org.sfaci.agenda;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.sfaci.agenda.base.Amigo;

import java.text.SimpleDateFormat;


public class Detalles extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        int posicion = getIntent().getIntExtra("posicion", -1);
        if (posicion != -1)
            cargar(posicion);
    }

    private void cargar(int posicion) {

        ImageView ivFoto = (ImageView) findViewById(R.id.ivFoto);
        TextView tvNombreApellidos = (TextView) findViewById(R.id.tvNombreApellidos);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        TextView tvTelefonoFijo = (TextView) findViewById(R.id.tvTelefonoFijo);
        TextView tvTelefonoMovil = (TextView) findViewById(R.id.tvTelefonoMovil);
        TextView tvFechaNacimiento = (TextView) findViewById(R.id.tvFechaNacimiento);
        TextView tvDeudas = (TextView) findViewById(R.id.tvDeudas);

        Amigo amigo = MainActivity.listaAmigos.get(posicion);
        ivFoto.setImageBitmap(amigo.getFoto());
        tvNombreApellidos.setText(amigo.getNombreApellidos());
        tvEmail.setText(amigo.getEmail());
        tvTelefonoFijo.setText(amigo.getTelefonoFijo());
        tvTelefonoMovil.setText(amigo.getTelefonoMovil());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tvFechaNacimiento.setText(sdf.format(amigo.getFechaNacimiento()));
        tvDeudas.setText(String.valueOf(amigo.getDeudas()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
