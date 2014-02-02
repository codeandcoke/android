package org.sfsoft.tabs;

import org.sfsoft.bbdd.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * Activity principal donde se mostrará la aplicación y se presentará
 * la ActionBar con las pestañas
 * 
 * Desde esta Activity se cargan las pestañas. La funcionalidad de cada una de ellas
 * se hará en su correspondiente fichero de código 
 *  
 * @author Santiago Faci
 *
 */
public class GestionAlumnos extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        cargaPestanas();
    }
    
    /*
     * Carga las pestañas para formar el TabHost
     */
    private void cargaPestanas() {
    	
    	Resources res = getResources();
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // Crea las tabs
        ActionBar.Tab tab1 = actionBar.newTab().setText(res.getString(R.string.nuevo_alumno_title));
        ActionBar.Tab tab2 = actionBar.newTab().setText(res.getString(R.string.listado_alumnos_title));
        
        // Crea cada Fragment para luego asociarla con la pestaña que corresponda
        Fragment fragmentoTab1 = new NuevoAlumno();
        Fragment fragmentoTab2 = new ListaAlumnos();
        
        // Asocia cada Fragment con su tab
        tab1.setTabListener(new TabsListener(fragmentoTab1));
        tab2.setTabListener(new TabsListener(fragmentoTab2));
        
        // Añade las tabs a la ActionBar
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
    }
}
