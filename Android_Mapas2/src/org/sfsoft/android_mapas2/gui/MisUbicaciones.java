package org.sfsoft.android_mapas2.gui;

import org.sfsoft.android_mapas2.R;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
 */
public class MisUbicaciones extends Fragment {

	private GoogleMap mapa;
	
	public void onCreate(Bundle savedInstanceState) {
	    
	    super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.mis_ubicaciones, container, false);
		
		
    	return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.ubicaciones, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.opcion_mapa:
	        
	    	return true;
	    case R.id.opcion_ubicacion:
	    	return true;
	    default:
	        break;
	    }

	    return false;
	}
}
