package com.example.ezservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ezservice.adapters.ListaServidorAdapter;
import com.example.ezservice.adapters.ProfesionAdapter;
import com.example.ezservice.adapters.TarjetaAdapter;
import com.example.ezservice.models.ListaServidor;
import com.example.ezservice.models.Profesion;
import com.example.ezservice.models.Tarjeta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Lista extends Fragment {
    View view;
    RecyclerView recyclerView;
    ListaServidorAdapter adapter;
    List<ListaServidor> models = new ArrayList<>();
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    public Fragment_Lista() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_lista,container,false);

        reference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        getDataFromFirebase();


        return view;
    }

    public void getDataFromFirebase(){
        reference.child("Usuarios/"+firebaseUser.getUid()+"/Solicitados").addValueEventListener(new ValueEventListener() {
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
                                String profesion = snapshot.child("profesion").getValue().toString();
                                String id = snapshot.child("id").getValue().toString();
                                models.add(new ListaServidor(nombre, profesion, id));
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

    public void setupadapter(List<ListaServidor> models) {
        adapter = new ListaServidorAdapter(getContext(), models);

        recyclerView = view.findViewById(R.id.recyclerview_lista);
        LinearLayoutManager LayouyManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(LayouyManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}