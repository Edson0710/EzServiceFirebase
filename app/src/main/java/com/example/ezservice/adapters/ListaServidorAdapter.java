package com.example.ezservice.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ezservice.R;
import com.example.ezservice.models.ListaServidor;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaServidorAdapter extends RecyclerView.Adapter<ListaServidorAdapter.MyViewHolder> {

    private Context mContext;
    private List<ListaServidor> mData;
    private RequestOptions option3;

    public ListaServidorAdapter(Context mContext, List<ListaServidor> mData2) {
        this.mContext = mContext;
        this.mData = mData2;
        //Request option for Glide

        //option3 = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        final View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_servidor, parent, false);


        final MyViewHolder viewHolder = new MyViewHolder(view);
        /*viewHolder.container3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prueba = mData2.get(viewHolder.getAdapterPosition()).getEstado();
                String id_firebase = mData2.get(viewHolder.getAdapterPosition()).getId_firebase();
                String imagenURL = mData2.get(viewHolder.getAdapterPosition()).getImagen();
                int chat = mData2.get(viewHolder.getAdapterPosition()).getChat();
                double telefono = mData2.get(viewHolder.getAdapterPosition()).getTelefono();
                int id_us = mData2.get(viewHolder.getAdapterPosition()).getId_us();
                if (obtenerTipo() == 1) {
                    if (prueba.equals("Aceptado") && chat == 0) {
                        Intent intent = new Intent(mContext2, PrimerMensaje.class);
                        intent.putExtra("userid", id_firebase);
                        intent.putExtra("imagenURL", imagenURL);
                        intent.putExtra("telefono", telefono);
                        intent.putExtra("id_uc", id_uc);
                        intent.putExtra("id_us", id_us);
                        intent.putExtra("chat", chat);
                        mContext2.startActivity(intent);
                    }
                    if (prueba.equals("Aceptado") && chat == 1) {
                        Intent intent = new Intent(mContext2, MessageActivity.class);
                        intent.putExtra("userid", id_firebase);
                        intent.putExtra("imagenURL", imagenURL);
                        intent.putExtra("telefono", telefono);
                        intent.putExtra("id_uc", id_uc);
                        intent.putExtra("id_us", id_us);
                        intent.putExtra("chat", chat);
                        mContext2.startActivity(intent);
                    }
                }
                if (obtenerTipo() == 3){
                    Intent intent = new Intent(mContext2, MessageActivity.class);
                    intent.putExtra("userid", id_firebase);
                    intent.putExtra("imagenURL", imagenURL);
                    intent.putExtra("telefono", telefono);
                    intent.putExtra("id_uc", id_uc);
                    intent.putExtra("id_us", id_us);
                    intent.putExtra("chat", chat);
                    mContext2.startActivity(intent);
                }
            }
        });*/


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //holder.tv_id.setText(""+mData2.get(position).getId());
        holder.tv_nombre.setText(mData.get(position).getNombre());
        holder.tv_profesion.setText(mData.get(position).getProfesion());
        /*holder.tv_estado.setText(mData2.get(position).getEstado());
        if (obtenerTipo() == 3){
            holder.tv_estado.setVisibility(View.INVISIBLE);
            holder.tv_estado2.setVisibility(View.INVISIBLE);
        }*/

        //Load image from Internet

        //Glide.with(mContext2).load(mData2.get(position).getImagen()).apply(option3).into(holder.iv_imagen);
        Glide.with(mContext).load(mData.get(position).getImagen()).into(holder.imagen);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView tv_nombre;
        TextView tv_profesion;
        TextView tv_estado;
        CircleImageView imagen;
        //LinearLayout container3;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_profesion = itemView.findViewById(R.id.tv_profesion);
            imagen = itemView.findViewById(R.id.imagen);
            //tv_estado = itemView.findViewById(R.id.lista_estado2);
            /*container3 = itemView.findViewById(R.id.container_lista);
            container3.setOnCreateContextMenuListener(this);*/

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 121, 0, "Delete");
        }

    }

    /*public int obtenerTipo() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int type_preference = preferences.getInt("TIPO", 1);
        return type_preference;
    }*/


}
