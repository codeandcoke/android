package org.sfsoft.tabs;

import org.sfsoft.bbdd.R;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Fragment de la aplicación que se corresponderá
 * con una de las pestañas de la aplicación
 * En este caso es el formulario donde se registran los alumnos
 * 
 * Al tratarse de un Fragment y no una Activity hay que tener en cuenta
 * que para obtener el contexto de la aplicación hay que invocar al método
 * 'getActivity()' que devuelve la Activity a la que pertenece el Fragment
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class NuevoAlumno extends Fragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		// Al tratarse de un fragment el layout se infla creando una view que hay que devolver
		// y que se debe usar para obtener las referencias a los elementos del layout
		
		View view = inflater.inflate(R.layout.nuevo_alumno, container, false);
		Button btAlta = (Button) view.findViewById(R.id.btAlta);
    	btAlta.setOnClickListener(this);
    	
    	return view;
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btAlta:
			Toast.makeText(getActivity(), "boton", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}	
}
