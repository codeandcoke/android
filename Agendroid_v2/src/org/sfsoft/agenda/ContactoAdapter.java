package org.sfsoft.agenda;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.sfsoft.agenda.base.Contacto;

import java.util.ArrayList;

/**
 * Adaptador para representar cada uno de los contactos en la lista
 * de la Activity principal
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class ContactoAdapter extends BaseAdapter {

	// Lista de contactos de la aplicación
	private ArrayList<Contacto> listaContactos;
	// Lista de contactos que se deben mostrar en cada momento
	private ArrayList<Contacto> listaActual;
	private LayoutInflater inflater;
	private Context contexto;
	
	static class ViewHolder {
		
		ImageView imagen;
		TextView nombreApellidos;
		TextView telefono;
		TextView movil;
	}
	
	public ContactoAdapter(Activity contexto, ArrayList<Contacto> listaContactos) {
		
		this.contexto = contexto;
		this.listaContactos = listaContactos;
		listaActual = new ArrayList<Contacto>();
		inflater = LayoutInflater.from(contexto);
	}
	
	/*
	 * Sólo mantiene en lista a los contactos marcados
	 * como favoritos
	 */
	public void verFavoritos() {
		
		listaActual.clear();
		for (Contacto contacto : listaContactos) {
			if (contacto.esFavorito())
				listaActual.add(contacto);
		}
	}
	
	/*
	 * Muestra todos los contactos de la aplicación
	 */
	public void verTodos() {
		
		listaActual.clear();
		listaActual.addAll(listaContactos); 
	}
	
	public void eliminar(int posicion) {
		listaActual.remove(posicion);
		notifyDataSetChanged();
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
		
		Contacto contacto = listaActual.get(posicion);
		
		/*
		 *  Comprueba, según las prerencias de la aplicación, cómo
		 *  se deben mostrar los datos del contacto en pantalla
		 */
		String nombreApellidos = null;
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(contexto);
		String opcionDatos = preferencias.getString("opcion_datos", "Nombre");
		if (opcionDatos.equals(contexto.getResources().getStringArray(R.array.datos)[0]))
			nombreApellidos = contacto.getNombre() + " " + contacto.getApellidos();
		else
			nombreApellidos = contacto.getApellidos() + " " + contacto.getNombre();
		
		holder.imagen.setImageBitmap(contacto.getImagen());
		holder.nombreApellidos.setText(nombreApellidos);
		holder.telefono.setText(contacto.getTelefono());
		holder.movil.setText(contacto.getMovil());
		
		return convertView;
	}

	@Override
	public int getCount() {

		return listaActual.size();
	}

	@Override
	public Object getItem(int posicion) {
		
		return listaActual.get(posicion);
	}

	@Override
	public long getItemId(int posicion) {
		
		return posicion;
	}
}
