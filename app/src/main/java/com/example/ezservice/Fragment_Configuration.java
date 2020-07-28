package com.example.ezservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Configuration extends Fragment {
    View view;
    Button signout;
    TextView tv_nombre;
    CircleImageView imagen;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;

    public Fragment_Configuration() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_configuration, container, false);

        //Relaciones------------
        signout = view.findViewById(R.id.btn_signout);
        tv_nombre = view.findViewById(R.id.tv_nombre);
        imagen = view.findViewById(R.id.imagen);
        reference = FirebaseDatabase.getInstance().getReference();


        getDataFromFirebase();


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getDataFromFirebase(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Usuarios").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Obtiene datos del usuario----------
                String nombre = dataSnapshot.child("nombre").getValue().toString();
                String imagenURL = dataSnapshot.child("imageProfile").getValue().toString();

                //Cambia etiquetas-------------------
                tv_nombre.setText(nombre);

                if (!imagenURL.equals("default")) {
                    Glide.with(getContext()).load(imagenURL).into(imagen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}