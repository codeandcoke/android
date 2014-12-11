package org.sfsoft.android_mapas2.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Guarda información sobre las ubicaciones que guarda el usuario en el mapa
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Ubicacion implements Parcelable {

    private int id;
	private String nombre;
	private LatLng posicion;
	
	public Ubicacion() {}
	
	public Ubicacion(Parcel entrada) {

        id = entrada.readInt();
        nombre = entrada.readString();
        posicion = entrada.readParcelable(LatLng.class.getClassLoader());
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

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
        destino.writeInt(id);
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
