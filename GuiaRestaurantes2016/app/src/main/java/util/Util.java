package util;

import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

/**
 * Métodos de utilidad
 * @author Santiago Faci
 * @version curso 2015-2016
 */
public class Util {

    /**
     * Transforma las coordenadas del sistema UMT que el ayuntamiento utiliza
     * al sistema LatLng que es con lo que trabaja Google Maps
     * Hay que tener en cuenta que hace falta la librería jcoord que viene con el proyecto
     * @param este
     * @param oeste
     * @param zonaLat
     * @param zonaLong
     * @return
     */
    public static LatLng DeUMTSaLatLng(double este, double oeste, char zonaLat, int zonaLong) {

        UTMRef utm = new UTMRef(este, oeste, 'N', 30);

        return utm.toLatLng();
    }
}