package org.sfaci.agenda.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.sfaci.agenda.R;
import org.sfaci.agenda.base.Amigo;

import java.util.ArrayList;

/**
 * Created by DAM on 27/10/2015.
 */
public class AmigoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Amigo> listaAmigos;
    private LayoutInflater inflater;

    public AmigoAdapter(Activity context, ArrayList<Amigo> listaAmigos) {
        this.context = context;
        this.listaAmigos = listaAmigos;
        inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        ImageView foto;
        TextView nombreApellidos;
        TextView movil;
        TextView fijo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        // Si la View es null se crea de nuevo
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fila, null);

            holder = new ViewHolder();
            holder.foto = (ImageView) convertView.findViewById(R.id.ivFoto);
            holder.nombreApellidos = (TextView) convertView.findViewById(R.id.tvNombreApellidos);
            holder.fijo = (TextView) convertView.findViewById(R.id.tvTelefonoFijo);
            holder.movil = (TextView) convertView.findViewById(R.id.tvTelefonoMovil);

            convertView.setTag(holder);
        }
		/*
		 *  En caso de que la View no sea null se reutilizar� con los
		 *  nuevos valores
		 */
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Amigo amigo = listaAmigos.get(position);
        holder.foto.setImageBitmap(amigo.getFoto());
        holder.nombreApellidos.setText(amigo.getNombreApellidos());
        holder.fijo.setText(amigo.getTelefonoFijo());
        holder.movil.setText(amigo.getTelefonoMovil());

        return convertView;
    }

    @Override
    public int getCount() {
        return listaAmigos.size();
    }

    @Override
    public Object getItem(int posicion) {
        return listaAmigos.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

}
