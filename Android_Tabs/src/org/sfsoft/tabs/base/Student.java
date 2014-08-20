package org.sfsoft.tabs.base;

/**
 * Clase que representa un alumno
 *
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Student {
	
	private String name;
	private String subject;

	public Student(String name, String subject) {
		this.name = name;
		this.subject = subject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
