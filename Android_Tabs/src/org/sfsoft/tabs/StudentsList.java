package org.sfsoft.tabs;

import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import org.sfsoft.bbdd.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import org.sfsoft.tabs.database.Database;
import static org.sfsoft.tabs.database.Constants.*;

/**
 * Fragment de la aplicación que se corresponderá
 * con una de las pestañas de la aplicación
 * En este caso es la lista donde se muestran los alumnos
 * 
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class StudentsList extends Fragment {

    private Database db;

    private String[] FROM_SHOW = {NAME, SUBJECT };
    private int[] TO = {R.id.studentName, R.id.studentSubject};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.students_list, container, false);

        db = new Database(getActivity());
        // Obtiene el cursor con el listado de alumnos de la Base de Datos
        Cursor cursor = db.getStudents();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.student_row, cursor, FROM_SHOW, TO,
            SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		ListView lvStudentsList = (ListView) view.findViewById(R.id.lvStudentsList);
        lvStudentsList.setAdapter(adapter);
		
		return view;
	}
}
