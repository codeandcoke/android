package org.sfsoft.agenda;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * PreferenceActivity es la pantalla donde el usuario personaliza
 * las preferencias de la aplicación
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Preferencias extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferencias);
    }
}
