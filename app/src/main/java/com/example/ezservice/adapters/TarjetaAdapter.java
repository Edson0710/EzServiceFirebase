package com.example.ezservice.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ezservice.R;
import com.example.ezservice.models.Tarjeta;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TarjetaAdapter extends PagerAdapter {

    private List<Tarjeta> models;
    private LayoutInflater layoutInflater;
    private Context context;
    private RequestOptions option;
    DecimalFormat format1 = new DecimalFormat("#.##");



    public TarjetaAdapter(List<Tarjeta> models, Context context) {
        this.models = models;
        this.context = context;

        //Request option for Glide

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.item_cardview, container, false);

        CircleImageView imagen;
        TextView nombre;
        Button comentarios, documentos;
        TextView contador;

        imagen = view.findViewById(R.id.imagen_cardview);
        nombre = view.findViewById(R.id.nombre_cardview);
        comentarios = view.findViewById(R.id.btn_comentarios);
        contador = view.findViewById(R.id.contador);
        documentos = view.findViewById(R.id.btn_documentos);

        contador.setText(position + 1 + " de " + models.size());
        nombre.setText(models.get(position).getNombre());
        //Load image from Internet

        //Glide.with(context).load(models.get(position).getImagen()).into(imagen);

        /*comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Comentarios.class);
                i.putExtra("id", models.get(position).getId());
                context.startActivity(i);


            }
        });*/

        /*documentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Documentos.class);
                intent.putExtra("id",models.get(position).getId());
                context.startActivity(intent);
            }
        });*/

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

