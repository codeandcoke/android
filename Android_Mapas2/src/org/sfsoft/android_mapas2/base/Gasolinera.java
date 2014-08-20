package org.sfsoft.android_mapas2.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Representa a las gasolineras del mapa
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Gasolinera implements Parcelable {

	private String nombre;
	private LatLng posicion;
	
	public Gasolinera() {}
	
	public Gasolinera(Parcel entrada) {
        
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
	
	public static final Parcelable.Creator<Gasolinera> CREATOR = new Parcelable.Creator<Gasolinera>() {
		
		public Gasolinera createFromParcel(Parcel in) {
		    return new Gasolinera(in);
		}
		
		public Gasolinera[] newArray(int size) {
		    return new Gasolinera[size];
		}
	};
}
