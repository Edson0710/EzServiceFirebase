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

public class Fragment_Solicitar extends Fragment {
    View view;
    Button btn_list, btn_cat;

    public Fragment_Solicitar() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_solicitar,container,false);
        btn_list = view.findViewById(R.id.btn_lista);
        btn_cat = view.findViewById(R.id.btn_cat);

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TarjetasServidores.class);
                startActivity(intent);
            }
        });

        btn_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TarjetasCategorias.class);
                startActivity(intent);
            }
        });
        return view;
    }
}