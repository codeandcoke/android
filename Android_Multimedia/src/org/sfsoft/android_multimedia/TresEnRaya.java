package org.sfsoft.android_multimedia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class TresEnRaya extends Activity {

	private Tablero tablero;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        tablero = new Tablero(this);
        setContentView(tablero);
        tablero.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tres_en_raya, menu);
        return true;
    }

    
}
