package org.sfsoft.tabs;

import org.sfsoft.bbdd.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Fragment de la aplicación que se corresponderá
 * con una de las pestañas de la aplicación
 * En este caso es la lista donde se muestran los alumnos
 * 
 * @author Santiago Faci
 *
 */
public class ListaAlumnos extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.lista_alumnos, container, false);
		
		ListView lvListaAlumnos = (ListView) view.findViewById(R.id.lvListaAlumnos);
		// TODO Terminar de implementar la carga de la ListView
		
		return view;
	}
}
