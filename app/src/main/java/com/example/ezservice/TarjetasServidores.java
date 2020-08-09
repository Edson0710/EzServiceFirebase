package com.example.ezservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ezservice.adapters.TarjetaAdapter;
import com.example.ezservice.models.Tarjeta;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.HashMap;
import java.util.List;

public class TarjetasServidores extends AppCompatActivity {


    ViewPager viewPager;
    TarjetaAdapter adapter;
    List<Tarjeta> models = new ArrayList<>();
    Button solicitar;
    String categoria, profesion;
    FirebaseUser firebaseUser;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas_servidores);
        solicitar = findViewById(R.id.btn_contratar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        categoria = getIntent().getStringExtra("categoria");
        profesion = getIntent().getStringExtra("profesion");


        getDataFromFirebase();

        solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarUser();
            }
        });


    }

    public void solicitarUser() {
        String id_servidor = models.get(viewPager.getCurrentItem()).getId();
        reference = FirebaseDatabase.getInstance().getReference("Usuarios/" + firebaseUser.getUid() + "/Solicitados").child(id_servidor);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id_servidor);
        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TarjetasServidores.this, "Agregado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TarjetasServidores.this, "Fallo al agregar, intente de nuevo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getDataFromFirebase() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Categorias/" + categoria + "/Profesiones/" + profesion + "/servidores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String id = ds.child("id").getValue().toString();
                        //String nombre = ds.child("nombre").getValue().toString();
                        //models.add(new Tarjeta(nombre));
                        reference.child("Servidores/" + id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String nombre = snapshot.child("nombre").getValue().toString();
                                String id = snapshot.child("id").getValue().toString();
                                models.add(new Tarjeta(nombre, id));
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