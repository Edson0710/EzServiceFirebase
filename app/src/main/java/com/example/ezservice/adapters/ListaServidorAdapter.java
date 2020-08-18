package com.example.ezservice.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ezservice.PerfilServidores;
import com.example.ezservice.R;
import com.example.ezservice.TarjetasProfesiones;
import com.example.ezservice.models.ListaServidor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaServidorAdapter extends RecyclerView.Adapter<ListaServidorAdapter.MyViewHolder> {

    private Context mContext;
    private List<ListaServidor> mData;
    private RequestOptions option3;
    private FirebaseUser firebaseUser;

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

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, PerfilServidores.class);
                i.putExtra("id", mData.get(viewHolder.getAdapterPosition()).getId());
                mContext.startActivity(i);

            }
        });
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //holder.tv_id.setText(""+mData2.get(position).getId());
        holder.tv_nombre.setText(mData.get(position).getNombre());
        holder.tv_profesion.setText(mData.get(position).getProfesion());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext, "Funado: " + mData.get(position).getId().toString(), Toast.LENGTH_SHORT).show();
                deleteFromFirebase(position);
            }
        });

        //Load image from Internet

        //Glide.with(mContext).load(mData.get(position).getImagen()).into(holder.imagen);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView tv_nombre;
        TextView tv_profesion;
        ConstraintLayout container;
        CircleImageView imagen;
        Button btn_delete;
        //LinearLayout container3;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            tv_profesion = itemView.findViewById(R.id.tv_profesion);
            imagen = itemView.findViewById(R.id.imagen);
            container = itemView.findViewById(R.id.contenedor_servidor);

            //tv_estado = itemView.findViewById(R.id.lista_estado2);
            /*container3 = itemView.findViewById(R.id.container_lista);
            container3.setOnCreateContextMenuListener(this);*/

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 121, 0, "Delete");
        }

    }

    public void deleteFromFirebase(int position){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Usuarios/"+firebaseUser.getUid()+"/Solicitados");
        DatabaseReference currentUserBD = database.child(mData.get(position).getId());
        currentUserBD.removeValue();
    }

    /*public int obtenerTipo() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int type_preference = preferences.getInt("TIPO", 1);
        return type_preference;
    }*/


}
