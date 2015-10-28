package org.sfaci.agenda.base;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by DAM on 16/10/2015.
 */
public class Amigo {

    public Amigo() {

    }

    private String nombreApellidos;
    private String email;
    private String telefonoFijo;
    private String telefonoMovil;
    private Bitmap foto;
    private Date fechaNacimiento;
    private float deudas;

    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public float getDeudas() {
        return deudas;
    }

    public void setDeudas(float deudas) {
        this.deudas = deudas;
    }

    @Override
    public String toString() {
        return nombreApellidos;
    }
}
