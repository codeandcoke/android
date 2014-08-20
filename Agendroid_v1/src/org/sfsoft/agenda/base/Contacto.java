package org.sfsoft.agenda.base;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase que representa un contacto de la Agenda
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Contacto implements Parcelable {

	private String nombre;
	private String apellidos;
	private Bitmap imagen;
	private String telefono;
	private String movil;
	private String fax;
	private boolean favorito;

	public Contacto() {}
	
	public Contacto(String nombre, String apellidos, Bitmap imagen, String telefono, String movil, String fax) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.imagen = imagen;
		this.telefono = telefono;
		this.movil = movil;
		this.fax = fax;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public Bitmap getImagen() {
		return imagen;
	}
	
	public void setImagen(Bitmap imagen) {
		this.imagen = imagen;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getMovil() {
		return movil;
	}
	
	public void setMovil(String movil) {
		this.movil = movil;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public boolean esFavorito() {
		return favorito;
	}

	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

	@Override
	public int describeContents() {
		
		return 0;
	}

	/**
	 * Método que será invocado cuando haya que almacenar los datos por el
	 * interfaz Parcelable
	 */
	@Override
	public void writeToParcel(Parcel destino, int flag) {
		
		destino.writeString(nombre);
		destino.writeString(apellidos);
		destino.writeString(telefono);
		destino.writeString(movil);
		destino.writeString(fax);
		destino.writeParcelable(imagen, PARCELABLE_WRITE_RETURN_VALUE);
		destino.writeString(String.valueOf(favorito));
	}
	
	/**
	 * Constructor para recuperar los datos utilizando el interfaz Parcelable
	 * Hay que tener en cuenta que se deben leer en el mismo orden en que se escribieron
	 * @param entrada 
	 */
	private Contacto(Parcel entrada) {
		
		nombre = entrada.readString();
		apellidos = entrada.readString();
		telefono = entrada.readString();
		movil = entrada.readString();
		fax = entrada.readString();
		imagen = (Bitmap) entrada.readParcelable(Bitmap.class.getClassLoader());
		favorito = Boolean.parseBoolean(entrada.readString());
	}
}
