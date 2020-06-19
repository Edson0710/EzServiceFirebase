package com.example.ezservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrosOpciones extends AppCompatActivity {

    Button btn_comun;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros_opciones);
        btn_comun = findViewById(R.id.btn_registro_comun);


        btn_comun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), RegistroComun.class);
                startActivity(intent);
            }
        });




    }
}
