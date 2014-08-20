package org.sfsoft.tabs;

import android.widget.EditText;
import android.widget.Toast;
import org.sfsoft.bbdd.R;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import org.sfsoft.tabs.base.Student;
import org.sfsoft.tabs.database.Database;

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
public class StudentRegistration extends Fragment implements OnClickListener {

    private EditText etName, etSubject;
    private Database db;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		// Al tratarse de un fragment el layout se infla creando una view que hay que devolver
		// y que se debe usar para obtener las referencias a los elementos del layout
		
		View view = inflater.inflate(R.layout.student_registration, container, false);
		Button btAlta = (Button) view.findViewById(R.id.btRegister);
    	btAlta.setOnClickListener(this);

        etName = (EditText) view.findViewById(R.id.etName);
        etSubject = (EditText) view.findViewById(R.id.etSubject);

        db = new Database(getActivity());
    	
    	return view;
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btRegister:
			Student student = new Student(etName.getText().toString(), etSubject.getText().toString());
            db.newStudent(student);
            Toast.makeText(getActivity(), R.string.registration_message, Toast.LENGTH_LONG).show();
            break;
		default:
			break;
		}
	}	
}
