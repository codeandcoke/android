package org.sfsoft.android_listas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Clase que extiende un ArrayAdapter para convertirlo en un Adapter personalizado
 * para pintar elementos "customizados" en un ListView
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class ImagenAdapter extends ArrayAdapter<String> {
	
	private String[] elementos;
	
	/**
	 * Parámetros necesarios para crear el adapter
	 * @param contexto El contexto de la aplicación
	 * @param idLayout El id del layout que representa una fila de la ListView
	 * @param idEtiqueta El id de la TextView que representa cada fila
	 * @param elementos La lista de elementos de la lista
	 */
	public ImagenAdapter(Context contexto, int idLayout, int idEtiqueta, String[] elementos) {
		super(contexto, idLayout, idEtiqueta, elementos);
		
		this.elementos = elementos;
	}
	
	/**
	 * Este método renderiza cada una de las filas que se pintan en la ListView
	 * En este caso, en función de un criterio determinado, modifico el comportamiento de la imagen
	 * que acompaña al texto
	 * @param posicion La posición de la fila que se está renderizando
	 * @param view La View que representa la fila
	 * @param padre La View padre, que será la ListView
	 * @return La fila modificada que será renderizada por Android
	 */
	@Override
	public View getView(int posicion, View view, ViewGroup padre) {
		
		// Obtiene una referencia a la fila que se va a pintar
		View fila = super.getView(posicion, view, padre);
		ImageView imagen = (ImageView) fila.findViewById(R.id.imagen);
		
		// R.drawable permite hacer referencia a los iconos del proyecto
		if (posicion % 2 == 0) {
			imagen.setImageResource(R.drawable.icono1);
		}
		else {
			imagen.setImageResource(R.drawable.icono2);
		}
		
		return fila;
	}
}
