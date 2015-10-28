package org.sfaci.agenda;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.sfaci.agenda.base.Amigo;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Registro extends Activity implements View.OnClickListener {

    private int posicion;
    private final int RESULTADO_CARGA_IMAGEN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        posicion = getIntent().getIntExtra("posicion", -1);
        if (posicion != -1)
            cargar(posicion);

        Button btGuardar = (Button) findViewById(R.id.btGuardar);
        btGuardar.setOnClickListener(this);
        Button btCancelar = (Button) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(this);
        ImageButton ibFoto = (ImageButton) findViewById(R.id.ibFoto);
        ibFoto.setOnClickListener(this);
    }

    private void cargar(int posicion) {

        Amigo amigo = MainActivity.listaAmigos.get(posicion);

        EditText etNombreApellidos = (EditText) findViewById(R.id.etNombreApellidos);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        EditText etTelefonoFijo = (EditText) findViewById(R.id.etTelefonoFijo);
        EditText etFechaNacimiento = (EditText) findViewById(R.id.etFechaNacimiento);
        EditText etTelefonoMovil = (EditText) findViewById(R.id.etTelefonoMovil);
        EditText etDedudas = (EditText) findViewById(R.id.etDeudas);
        ImageButton ibFoto = (ImageButton) findViewById(R.id.ibFoto);

        etNombreApellidos.setText(amigo.getNombreApellidos());
        etEmail.setText(amigo.getEmail());
        etTelefonoFijo.setText(amigo.getTelefonoFijo());
        etFechaNacimiento.setText(String.valueOf(amigo.getFechaNacimiento()));
        etTelefonoMovil.setText(amigo.getTelefonoMovil());
        etDedudas.setText(String.valueOf(amigo.getDeudas()));
        ibFoto.setImageBitmap(amigo.getFoto());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if ((requestCode == RESULTADO_CARGA_IMAGEN) &&
                (resultCode == RESULT_OK) && (data != null)) {
            // Obtiene el Uri de la imagen seleccionada por el usuario
            Uri imagenSeleccionada = data.getData();
            String[] ruta = {MediaStore.Images.Media.DATA};

            // Realiza una consulta a la galería de imágenes solicitando la imagen seleccionada
            Cursor cursor = getContentResolver().query(imagenSeleccionada,
                    ruta, null, null, null);
            cursor.moveToFirst();

            // Obtiene la ruta a la imagen
            int indice = cursor.getColumnIndex(ruta[0]);
            String picturePath = cursor.getString(indice);
            cursor.close();

            // Carga la imagen en el botón ImageButton
            ImageButton ibFoto = (ImageButton)
                    findViewById(R.id.ibFoto);
            ibFoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btGuardar:
                EditText etNombreApellidos = (EditText) findViewById(R.id.etNombreApellidos);
                EditText etEmail = (EditText) findViewById(R.id.etEmail);
                EditText etTelefonoFijo = (EditText) findViewById(R.id.etTelefonoFijo);
                EditText etFechaNacimiento = (EditText) findViewById(R.id.etFechaNacimiento);
                EditText etDireccionPostal = (EditText) findViewById(R.id.etTelefonoMovil);
                EditText etDeudas = (EditText) findViewById(R.id.etDeudas);
                ImageButton ibFoto = (ImageButton) findViewById(R.id.ibFoto);

                Amigo amigo = null;
                if (posicion == -1)
                    amigo = new Amigo();
                else
                    amigo = MainActivity.listaAmigos.get(posicion);

                try {
                    amigo.setNombreApellidos(etNombreApellidos.getText().toString());
                    amigo.setEmail(etEmail.getText().toString());
                    amigo.setTelefonoFijo(etTelefonoFijo.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    amigo.setFechaNacimiento(sdf.parse(etFechaNacimiento.getText().toString()));
                    amigo.setTelefonoMovil(etDireccionPostal.getText().toString());
                    if (etDeudas.getText().toString().equals(""))
                        etDeudas.setText("0");
                    amigo.setDeudas(Float.parseFloat(etDeudas.getText().toString()));
                    amigo.setFoto(((BitmapDrawable) ibFoto.getDrawable()).getBitmap());

                    if (posicion == -1)
                        MainActivity.listaAmigos.add(amigo);

                    Toast.makeText(this, R.string.lb_guardado, Toast.LENGTH_LONG).show();
                } catch (ParseException pe) {
                    Toast.makeText(this, R.string.error_fecha, Toast.LENGTH_SHORT).show();
                }

                // TODO Limpiar el contenido de las cajas de texto

                break;
            case R.id.btCancelar:

                break;
            case R.id.ibFoto:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULTADO_CARGA_IMAGEN);
                break;
            default:
                break;
        }
    }
}
