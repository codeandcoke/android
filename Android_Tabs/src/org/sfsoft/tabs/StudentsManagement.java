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
 * En esta aplicación se muestra cómo trabajar con Bases de Datos en Android con SQLite
 * y cómo presentar la información utilizando Tabs
 *  
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class StudentsManagement extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_management);
        
        loadTabs();
    }
    
    /*
     * Carga las pestañas para formar el TabHost
     */
    private void loadTabs() {
    	
    	Resources res = getResources();
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // Crea las tabs
        ActionBar.Tab tab1 = actionBar.newTab().setText(res.getString(R.string.new_student_title));
        ActionBar.Tab tab2 = actionBar.newTab().setText(res.getString(R.string.students_list_title));
        
        // Crea cada Fragment para luego asociarla con la pestaña que corresponda
        Fragment tabFragment1 = new StudentRegistration();
        Fragment tabFragment2 = new StudentsList();
        
        // Asocia cada Fragment con su tab
        tab1.setTabListener(new TabsListener(tabFragment1));
        tab2.setTabListener(new TabsListener(tabFragment2));
        
        // Añade las tabs a la ActionBar
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
    }
}
