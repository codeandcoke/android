package org.sfaci.guiarestaurantes;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sfaci.guiarestaurantes.adapters.RestauranteAdapter;
import org.sfaci.guiarestaurantes.base.Restaurante;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Activity que lista los restaurantes de la ciudad de Zaragoza
 * Obtiene los datos de la web de OpenData de Zaragoza en formato JSON
 *
 * Recordar que para poder lanzar la app en el emulador es necesario prepararlo con
 * la imagen x86 o arm de Google APIs, que está disponible a partir de la versión 4.4.2
 * En el caso de lanzarlo sobre un móvil no hace falta tenerlo en cuenta puesto que todos
 * cuentan con soporte para las APIs de Google
 *
 * @author Santiago Faci
 * @version curso 2015-2016
 */
public class ListadoRestaurantes extends Activity implements AdapterView.OnItemClickListener {

    private ListView lista;
    private ArrayList<Restaurante> listaRestaurantes;
    private RestauranteAdapter adapter;

    private static final String URL = "http://www.zaragoza.es/georref/json/hilo/ver_Restaurante";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_restaurantes);

        lista = (ListView) findViewById(R.id.lvRestaurantes);
        lista.setOnItemClickListener(this);
        listaRestaurantes = new ArrayList<Restaurante>();
        adapter = new RestauranteAdapter(this, R.layout.fila, listaRestaurantes);
        lista.setAdapter(adapter);

        cargarRestaurantes();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private class TareaDescargaDatos extends AsyncTask<String, Void, Void> {

        private boolean error = false;

        // Este método no puede acceder a la interfaz
        @Override
        protected Void doInBackground(String... urls) {

            InputStream is = null;
            String resultado = null;
            JSONObject json = null;
            JSONArray jsonArray = null;

            try {
                // Conecta con la URL y obtenemos el fichero con los datos
                HttpClient clienteHttp = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urls[0]);
                HttpResponse respuesta = clienteHttp.execute(httpPost);
                HttpEntity entity = respuesta.getEntity();
                is = entity.getContent();

                // Lee el fichero de datos y genera una cadena de texto como resultado
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String linea = null;

                while ((linea = br.readLine()) != null)
                    sb.append(linea + "\n");

                is.close();
                resultado = sb.toString();

                json = new JSONObject(resultado);
                jsonArray = json.getJSONArray("features");

                String nombre = null;
                String descripcion = null;
                String categoria = null;
                String link = null;
                String coordenadas = null;
                Restaurante restaurante = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    nombre = jsonArray.getJSONObject(i).getJSONObject("properties").getString("title");
                    descripcion = jsonArray.getJSONObject(i).getJSONObject("properties").getString("description");
                    categoria = jsonArray.getJSONObject(i).getJSONObject("properties").getString("category");
                    link = jsonArray.getJSONObject(i).getJSONObject("properties").getString("link");
                    coordenadas = jsonArray.getJSONObject(i).getJSONObject("geometry").getString("coordinates");
                    coordenadas = coordenadas.substring(1, coordenadas.length() - 1);
                    String latlong[] = coordenadas.split(",");

                    restaurante = new Restaurante();
                    restaurante.setNombre(nombre);
                    restaurante.setDescripcion(descripcion);
                    restaurante.setCategoria(categoria);
                    restaurante.setLink(link);
                    restaurante.setLatitud(Float.parseFloat(latlong[0]));
                    restaurante.setLongitud(Float.parseFloat(latlong[1]));
                    listaRestaurantes.add(restaurante);
                }

            } catch (ClientProtocolException cpe) {
                cpe.printStackTrace();
                error = true;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                error = true;
            } catch (JSONException jse) {
                jse.printStackTrace();
                error = true;
            }

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            adapter.clear();
            listaRestaurantes = new ArrayList<Restaurante>();
        }

        @Override
        protected void onProgressUpdate(Void... progreso) {
            super.onProgressUpdate(progreso);

            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void resultado) {
            super.onPostExecute(resultado);

            if (error) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                return;
            }

            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.datos_message), Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Carga los restaurantes en el ListView
     */
    private void cargarRestaurantes() {

        TareaDescargaDatos tarea = new TareaDescargaDatos();
        tarea.execute(URL);
    }

    public void onItemClick(AdapterView<?> arg0, View view, int posicion, long id) {

        if (posicion == ListView.INVALID_POSITION)
            return;

        Restaurante restaurante = listaRestaurantes.get(posicion);

        Intent i = new Intent(this, Mapa.class);
        i.putExtra("latitud", restaurante.getLatitud());
        i.putExtra("longitud", restaurante.getLongitud());
        i.putExtra("nombre", restaurante.getNombre());
        startActivity(i);

    }

}
