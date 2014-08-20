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
 * En esta aplicación se listan una serie de gasolineras cuya información se ha leído de un fichero
 * JSON en Internet
 * Además, permite que el usuario registre ubicaciones propias de otros lugares en una base de datos
 *
 * Activity principal donde se mostrará la aplicación y se presentará
 * la ActionBar con las pestañas
 * 
 * Desde esta Activity se cargan las pestañas. La funcionalidad de cada una de ellas
 * se hará en su correspondiente fichero de código
 *  
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class ActivityMain extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        cargaPestanas();
    }
    
    /*
     * Carga las pestañas
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
