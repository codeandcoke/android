package org.sfsoft.agenda;

import java.util.ArrayList;

import org.sfsoft.agenda.base.Contacto;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adaptador para representar cada uno de los contactos en la lista
 * de la Activity principal
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class ContactoAdapter extends BaseAdapter {

	private ArrayList<Contacto> listaContactos;
	private LayoutInflater inflater;
	
	static class ViewHolder {
		
		ImageView imagen;
		TextView nombreApellidos;
		TextView telefono;
		TextView movil;
	}
	
	public ContactoAdapter(Activity contexto, ArrayList<Contacto> listaContactos) {
		
		this.listaContactos = listaContactos;
		inflater = LayoutInflater.from(contexto);
	}

	/*
	 * Se ha empleado el patrón Holder para mejorar el rendimiento de la ListView
	 * http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	 */
	@Override
	public View getView(int posicion, View convertView, ViewGroup padre) {
		
		ViewHolder holder = null;
		
		// Si la View es null se crea de nuevo
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fila, null);
			
			holder = new ViewHolder();
			holder.imagen = (ImageView) convertView.findViewById(R.id.ivImagen);
			holder.nombreApellidos = (TextView) convertView.findViewById(R.id.tvNombreApellidos);
			holder.telefono = (TextView) convertView.findViewById(R.id.tvTelefono);
			holder.movil = (TextView) convertView.findViewById(R.id.tvMovil);
			
			convertView.setTag(holder);
		}
		/*
		 *  En caso de que la View no sea null se reutilizará con los
		 *  nuevos valores
		 */
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Contacto contacto = listaContactos.get(posicion);
		holder.imagen.setImageBitmap(contacto.getImagen());
		holder.nombreApellidos.setText(contacto.getNombre() + " " + contacto.getApellidos());
		holder.telefono.setText(contacto.getTelefono());
		holder.movil.setText(contacto.getMovil());
		
		return convertView;
	}

	@Override
	public int getCount() {

		return listaContactos.size();
	}

	@Override
	public Object getItem(int posicion) {
		
		return listaContactos.get(posicion);
	}

	@Override
	public long getItemId(int posicion) {
		
		return posicion;
	}
}
