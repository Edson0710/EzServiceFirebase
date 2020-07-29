package com.example.ezservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ezservice.adapters.CategoriaAdapter;
import com.example.ezservice.adapters.TarjetaAdapter;
import com.example.ezservice.models.Categoria;
import com.example.ezservice.models.Tarjeta;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TarjetasCategorias extends AppCompatActivity {

    RecyclerView reciclerView;
    CategoriaAdapter adapter;
    List<Categoria> models = new ArrayList<>();

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas_categorias);

        reference = FirebaseDatabase.getInstance().getReference();

        getDataFromFirebase();
    }

    public void getDataFromFirebase(){
        reference.child("Categorias").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                if (snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        String nombre = ds.child("Nombre").getValue().toString();
                        String imagen=ds. child("Imagen").getValue().toString();
                        models.add(new Categoria(nombre));
                        //setupadapter(models);
                    }
                    setupadapter(models);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void setupadapter(List<Categoria> models) {
        adapter = new CategoriaAdapter(models, this);

        reciclerView = findViewById(R.id.Cat_reciclerView);

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        reciclerView.setLayoutManager(LayoutManager);
        reciclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}