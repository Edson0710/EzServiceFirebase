package com.example.ezservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView tv_registro;
    EditText et_email, et_password;
    String email, password;
    Button btn_signin;
    Intent intent;
    ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(getApplication(), Prueba.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //---------relaciones vista
        tv_registro = findViewById(R.id.tv_registro);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_signin = findViewById(R.id.btn_signin);
        progressDialog = new ProgressDialog(this);
        //----------firebase
        auth = FirebaseAuth.getInstance();


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });

        tv_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, RegistrosOpciones.class);
                startActivity(intent);
            }
        });



    }

    private void validarDatos () {
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (!email.equals("") && !password.equals("")) {
            progressDialog.setMessage("Iniciando sesion...");
            progressDialog.show();
            login();
        } else {
            Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    private void login () {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //Si el login es exitoso
                            Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplication(), Prueba.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "El usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });

    }

}
