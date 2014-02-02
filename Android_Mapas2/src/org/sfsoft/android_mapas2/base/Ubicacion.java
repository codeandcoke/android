package org.sfsoft.android_mapas2.base;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Ubicacion implements Parcelable {

	private String nombre;
	private LatLng posicion;
	
	public Ubicacion() {}
	
	public Ubicacion(Parcel entrada) {
        
        nombre = entrada.readString();
        posicion = entrada.readParcelable(LatLng.class.getClassLoader());
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LatLng getPosicion() {
		return posicion;
	}

	public void setPosicion(LatLng posicion) {
		this.posicion = posicion;
	}
	
	@Override
	public int describeContents() {
	    return 0;
	}
	
	@Override
	public void writeToParcel(Parcel destino, int flags) {
	    destino.writeString(nombre);
	    destino.writeParcelable(posicion, flags);
	}
	
	public static final Parcelable.Creator<Ubicacion> CREATOR = new Parcelable.Creator<Ubicacion>() {
		
		public Ubicacion createFromParcel(Parcel in) {
		    return new Ubicacion(in);
		}
		
		public Ubicacion[] newArray(int size) {
		    return new Ubicacion[size];
		}
	};
}
