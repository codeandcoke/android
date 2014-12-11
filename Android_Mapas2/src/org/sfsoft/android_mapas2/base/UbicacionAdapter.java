package org.sfsoft.android_mapas2.base;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.sfsoft.android_mapas2.R;
import org.sfsoft.android_mapas2.database.Database;

import java.util.List;

/**
 * Adapter para listar las gasolineras en la pantalla
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class UbicacionAdapter extends ArrayAdapter<Ubicacion> {

	private Context contexto;
	private int layoutId;
	private List<Ubicacion> datos;

	public UbicacionAdapter(Context contexto, int layoutId, List<Ubicacion> datos) {
		super(contexto, layoutId, datos);
		
		this.contexto = contexto;
		this.layoutId = layoutId;
		this.datos = datos;
	}

    public void eliminar(int posicion) {

        Ubicacion ubicacion = datos.remove(posicion);
        Database db = new Database(contexto);
        db.eliminarUbicacion(ubicacion);

        notifyDataSetChanged();
    }

	public View getView(int posicion, View view, ViewGroup padre) {
		
		View fila = view;
		ItemUbicacion item = null;
		
		if (fila == null) {
			LayoutInflater inflater = ((Activity) contexto).getLayoutInflater();
			fila = inflater.inflate(layoutId, padre, false);
			
			item = new ItemUbicacion();
			item.imagen = (ImageView) fila.findViewById(R.id.imagen);
			item.nombre = (TextView) fila.findViewById(R.id.nombre);
			
			fila.setTag(item);
		}
		else {
			item = (ItemUbicacion) fila.getTag();
		}

        Ubicacion ubicacion = datos.get(posicion);
		item.imagen.setImageDrawable(contexto.getResources().getDrawable(R.drawable.ubicacion));
		item.nombre.setText(ubicacion.getNombre());
		
		return fila;
	}
	
	static class ItemUbicacion {
		
		ImageView imagen;
		TextView nombre;
	}
}