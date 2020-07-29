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
import android.widget.Button;
import android.widget.Toast;

import com.example.ezservice.adapters.CategoriaAdapter;
import com.example.ezservice.adapters.ProfesionAdapter;
import com.example.ezservice.adapters.TarjetaAdapter;
import com.example.ezservice.models.Categoria;
import com.example.ezservice.models.Profesion;
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

public class TarjetasProfesiones extends AppCompatActivity {

    RecyclerView reciclerView;
    ProfesionAdapter adapter;
    List<Profesion> models = new ArrayList<>();
    String consulta;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas_profesiones);

        String categoria = getIntent().getStringExtra("categoria");
        consulta = "Categorias/"+categoria+"/Profesiones/";
        reference = FirebaseDatabase.getInstance().getReference();
        getDataFromFirebase();
    }

    public void getDataFromFirebase(){
        reference.child(consulta).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                if (snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        String nombre = ds.child("nombre").getValue().toString();
                        models.add(new Profesion(nombre));
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


    public void setupadapter(List<Profesion> models) {
        adapter = new ProfesionAdapter(models, this);

        reciclerView = findViewById(R.id.Prof_reciclerView);
        LinearLayoutManager LayouyManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reciclerView.setLayoutManager(LayouyManager);
        reciclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}