package org.sfsoft.android_mapas2.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import org.sfsoft.android_mapas2.R;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import org.sfsoft.android_mapas2.base.Ubicacion;
import org.sfsoft.android_mapas2.base.UbicacionAdapter;
import org.sfsoft.android_mapas2.database.Database;
import static org.sfsoft.android_mapas2.util.Constantes.*;

import java.util.ArrayList;

/**
 * Fragment de la aplicación que se corresponderá
 * con una de las pestañas de la aplicación
 * En este caso es el formulario donde se registran las ubicaciones
 * 
 * Al tratarse de un Fragment y no una Activity hay que tener en cuenta
 * que para obtener el contexto de la aplicación hay que invocar al método
 * 'getActivity()' que devuelve la Activity a la que pertenece el Fragment
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 *
 * TODO Cuando se seleccione la opción mapa, mostrar todas las ubicaciones en el mismo
 * TODO Cuando se selecciona una ubicación, mostrar dicha ubicación en el mapa
 */
public class MisUbicaciones extends Fragment implements AdapterView.OnItemClickListener, View.OnCreateContextMenuListener {

    private ArrayList<Ubicacion> listaUbicaciones;
    private UbicacionAdapter adapter;

    // Añade el menú de opciones al fragmento
	public void onCreate(Bundle savedInstanceState) {
	    
	    super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.mis_ubicaciones, container, false);

        listaUbicaciones = new ArrayList<>();
        adapter = new UbicacionAdapter(getActivity(), R.layout.item_ubicacion, listaUbicaciones);
        ListView lvListaUbicaciones = (ListView) view.findViewById(R.id.lvListaUbicaciones);
        lvListaUbicaciones.setAdapter(adapter);
        lvListaUbicaciones.setOnItemClickListener(this);
        lvListaUbicaciones.setOnCreateContextMenuListener(this);
        registerForContextMenu(lvListaUbicaciones);
		
    	return view;
	}

    @Override
    public void onResume() {
        super.onResume();

        cargarUbicaciones();
        adapter.notifyDataSetChanged();
    }

    private void cargarUbicaciones() {

        listaUbicaciones.clear();
        Database db = new Database(getActivity());
        Cursor cursor = db.getUbicaciones();
        while (cursor.moveToNext()) {
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setId(cursor.getInt(0));
            ubicacion.setNombre(cursor.getString(1));
            ubicacion.setPosicion(new LatLng(cursor.getDouble(2), cursor.getDouble(3)));
            listaUbicaciones.add(ubicacion);
        }
    }
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.ubicaciones, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.opcion_mapa:
            // Muestra las ubicaciones en el mapa
            Intent i = new Intent(getActivity(), Mapa.class);
            i.putExtra("accion", "ubicaciones");
            i.putParcelableArrayListExtra("ubicaciones", listaUbicaciones);
            startActivity(i);
	    	return true;
	    case R.id.opcion_ubicacion:
            // Muestra el formulario para registrar una nueva Ubicación
            Intent intent = new Intent(getActivity(), NuevaUbicacion.class);
            intent.putExtra("accion", "nuevo");
            startActivity(intent);
	    	return true;
	    default:
	        break;
	    }

	    return false;
	}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {

        // Muestra la Ubicación seleccionada en el mapa
        Ubicacion ubicacion = listaUbicaciones.get(posicion);
        Intent i = new Intent(getActivity(), Mapa.class);
        i.putExtra("accion", "ubicacion");
        i.putExtra("ubicacion", ubicacion);
        startActivity(i);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

		/*
		 *  Contiene información sobre el elemento del menú contextual
		 *  sobre el que se ha pulsado
		 */
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.ctx_editar:
                // Muestra el formulario para modificar la Ubicación
                Ubicacion ubicacion = listaUbicaciones.get(info.position);
                Intent intent = new Intent(getActivity(), NuevaUbicacion.class);
                intent.putExtra("accion", "modificar");
                intent.putExtra("ubicacion", ubicacion);
                startActivity(intent);
                return true;
            case R.id.ctx_eliminar:
                eliminar_ubicacion(info);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void eliminar_ubicacion(AdapterView.AdapterContextMenuInfo info) {

        final int posicion = info.position;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.estaseguro_message)
                .setPositiveButton(R.string.btaceptar_label, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.eliminar(posicion);
                    }
                })
                .setNegativeButton(R.string.btcancelar_label, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // En este caso no hacer nada

                    }
                });
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }
}
