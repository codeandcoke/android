package org.sfsoft.android_mapas.base;

import java.util.List;

import org.sfsoft.android_mapas.R;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter para listar los restaurantes en la pantalla
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class GasolineraAdapter extends ArrayAdapter<Gasolinera> {
	
	private Context contexto;
	private int layoutId;
	private List<Gasolinera> datos;

	public GasolineraAdapter(Context contexto, int layoutId, List<Gasolinera> datos) {
		super(contexto, layoutId, datos);
		
		this.contexto = contexto;
		this.layoutId = layoutId;
		this.datos = datos;
	}
	
	 @Override
     public View getDropDownView(int position, View convertView,ViewGroup parent) {
         return getFila(position, convertView, parent);
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         return getFila(position, convertView, parent);
     }

	public View getFila(int posicion, View view, ViewGroup padre) {
		
		View fila = view;
		ItemGasolinera item = null;
		
		if (fila == null) {
			LayoutInflater inflater = ((FragmentActivity) contexto).getLayoutInflater();
			fila = inflater.inflate(layoutId, padre, false);
			
			item = new ItemGasolinera();
			item.imagen = (ImageView) fila.findViewById(R.id.imagen);
			item.nombre = (TextView) fila.findViewById(R.id.nombre);
			
			fila.setTag(item);
		}
		else {
			item = (ItemGasolinera) fila.getTag();
		}
		
		Gasolinera gasolinera = datos.get(posicion);
		item.imagen.setImageDrawable(contexto.getResources().getDrawable(R.drawable.gasolinera));
		item.nombre.setText(gasolinera.getNombre());
		
		return fila;
	}
	
	static class ItemGasolinera {
		
		ImageView imagen;
		TextView nombre;
	}
}