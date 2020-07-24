package com.example.ezservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.ezservice.adapters.TarjetaAdapter;
import com.example.ezservice.models.Tarjeta;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TarjetasServidores extends AppCompatActivity {


    ViewPager viewPager;
    TarjetaAdapter adapter;
    List<Tarjeta> models = new ArrayList<>();
    Button solicitar;

    FirebaseUser firebaseUser;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas_servidores);
        solicitar = findViewById(R.id.btn_contratar);

        reference = FirebaseDatabase.getInstance().getReference();

        getDataFromFirebase();



    }

    public void getDataFromFirebase(){
        reference.child("Categorias/Categoria 1/Profesiones/Profesion 1/servidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                if (snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        String id = ds.child("id").getValue().toString();
                        //String nombre = ds.child("nombre").getValue().toString();
                        //models.add(new Tarjeta(nombre));
                        reference.child("Servidores/"+id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String nombre = snapshot.child("nombre").getValue().toString();
                                Toast.makeText(TarjetasServidores.this, ""+nombre, Toast.LENGTH_SHORT).show();
                                    models.add(new Tarjeta(nombre));
                                    setupadapter(models);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        //String imagen = ds.child("imageProfile").getValue().toString();

                    }
                        //setupadapter(models);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void setupadapter(List<Tarjeta> models) {
                adapter = new TarjetaAdapter(models, this);

                viewPager = findViewById(R.id.viewPager);
                viewPager.setAdapter(adapter);
                viewPager.setPadding(130, 0, 130, 0);

                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }


        });
    }

}