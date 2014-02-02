package org.sfsoft.android_mapas2.gui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.sfsoft.android_mapas2.R;

/**
 * Activity principal donde se mostrar� la aplicaci�n y se presentar�
 * la ActionBar con las pesta�as
 * 
 * Desde esta Activity se cargan las pesta�as. La funcionalidad de cada una de ellas
 * se har� en su correspondiente fichero de c�digo 
 *  
 * @author Santiago Faci
 *
 */
public class ActivityMain extends Activity {
	
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
        ActionBar.Tab tab1 = actionBar.newTab().setText(res.getString(R.string.listado_gasolineras_title));
        ActionBar.Tab tab2 = actionBar.newTab().setText(res.getString(R.string.misubicaciones_title));
        
        // Crea cada Fragment para luego asociarla con la pestaña que corresponda
        Fragment fragmentoTab1 = new ListaGasolineras();
        Fragment fragmentoTab2 = new MisUbicaciones();
        
        // Asocia cada Fragment con su tab
        tab1.setTabListener(new TabsListener(fragmentoTab1));
        tab2.setTabListener(new TabsListener(fragmentoTab2));
        
        // Añade las tabs a la ActionBar
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
    }
}
