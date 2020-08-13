package com.example.ezservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezservice.models.ListaServidor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilServidores extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String id, nombre, profesion;
    TextView tv_nombre,tv_descripcion,tv_profesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_servidores);

        reference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        tv_nombre=findViewById(R.id.nombreServidor);
        tv_profesion=findViewById(R.id.trabajoServidor);
        tv_descripcion=findViewById(R.id.descripcionServidor);

        id = getIntent().getStringExtra("id");

        getDataFromFirebase();

    }

    public void getDataFromFirebase(){
        reference.child("Servidores/"+id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nombre = snapshot.child("nombre").getValue().toString();
                profesion = snapshot.child("profesion").getValue().toString();

                //imagen = ds.child("imageProfile").getValue().toString();

                tv_nombre.setText(nombre);
                tv_profesion.setText(profesion);
                //tv_descripcion.setText(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}