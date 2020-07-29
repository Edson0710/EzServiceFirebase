package com.example.ezservice.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.ezservice.R;
import com.example.ezservice.TarjetasProfesiones;
import com.example.ezservice.models.Categoria;


import java.text.DecimalFormat;
import java.util.List;


public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyViewHolder>{

    private List<Categoria> models;
    private LayoutInflater layoutInflater;
    private Context context;
    private RequestOptions option;
    DecimalFormat format1 = new DecimalFormat("#.##");

    public CategoriaAdapter(List<Categoria> models, Context context){
        this.models = models;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View view;
        view = View.inflate(context, R.layout.item_categoria, null);


        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, TarjetasProfesiones.class);
                i.putExtra("categoria", models.get(viewHolder.getAdapterPosition()).getNombre());
                context.startActivity(i);

            }
        });


        return viewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout container;
        TextView nombre;

        public MyViewHolder(View itemView) {
            super(itemView);

            container=itemView.findViewById(R.id.container);
            nombre=itemView.findViewById(R.id.CatNombre);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.MyViewHolder holder, int position) {
        holder.nombre.setText(models.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
