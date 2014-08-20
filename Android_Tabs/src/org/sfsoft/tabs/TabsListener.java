package org.sfsoft.tabs;

import org.sfsoft.bbdd.R;

import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.ActionBar;
import android.app.FragmentTransaction;

/**
 * Listener para las tabs de la aplicación
 * Atiende los eventos que se producen cuando se 'navega' por las 
 * pestañas de la aplicación
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class TabsListener implements ActionBar.TabListener {
	 
    private Fragment fragment;
 
    public TabsListener(Fragment fragment)
    {
        this.fragment = fragment;
    }

    /**
     * La pestaña se ha re-seleccionado
     */
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
		// Aquí por ejemplo se podría refrescar la vista
	}

	/**
	 * Se ha seleccionado una pestaña. Se carga en la pantalla
	 */
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		ft.replace(R.id.container, fragment);
	}

	/**
	 * Se ha deseleccionado una pestaña. Se elimina de la pantalla
	 */
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
		ft.remove(fragment);
	}
}
