package com.example.ezservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistroComun extends AppCompatActivity {

    EditText et_email, et_pass1, et_pass2, et_nombre, et_apellido;
    Button btn_signup;
    String email, pass1, pass2, nombre, apellido, imageProfile;
    ProgressDialog progressDialog;
    CircleImageView imagen;

    private FirebaseAuth auth; //Se pone cada que te vas a meter con autenticacion.
    private FirebaseDatabase database; //Se pone cada que se interactua con la base de datos
    private DatabaseReference reference; //Igual que lo de arriba
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    Uri uri;

    private static final int GALLERY_INTENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comun);
        //----------------Relaciones---------------------------
        et_email = findViewById(R.id.et_email);
        et_pass1 = findViewById(R.id.et_pass1);
        et_pass2 = findViewById(R.id.et_pass2);
        et_nombre = findViewById(R.id.et_nombre);
        et_apellido = findViewById(R.id.et_apellido);
        btn_signup = findViewById(R.id.btn_signup);
        progressDialog = new ProgressDialog(this);
        imagen = findViewById(R.id.imagen);
        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();//-----------------------------------------------------

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminarRegistro();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uri = data.getData();
            imagen.setImageURI(uri);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getApplication().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference storage = storageReference.child("usuarios/" + firebaseUser.getUid() + "/perfil.jpg");
        if (uri != null) {
            uploadTask = storage.putFile(uri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storage.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        imageProfile = downloadUri.toString();
                        String userid = firebaseUser.getUid();
                        reference = FirebaseDatabase.getInstance().getReference("Usuarios").child(userid);
                        storageReference = FirebaseStorage.getInstance().getReference(userid);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", userid);
                        hashMap.put("nombre", nombre);
                        hashMap.put("apellidos", apellido);
                        hashMap.put("correo", email);
                        hashMap.put("imageProfile", imageProfile);

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(RegistroComun.this, Prueba.class);
                                    progressDialog.dismiss();
                                    Toast.makeText(RegistroComun.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegistroComun.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    } else {

                    }
                }
            });

        } else {
            imageProfile = "default";

            String userid = firebaseUser.getUid();
            reference = FirebaseDatabase.getInstance().getReference("Usuarios").child(userid);
            storageReference = FirebaseStorage.getInstance().getReference(userid);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", userid);
            hashMap.put("nombre", nombre);
            hashMap.put("apellidos", apellido);
            hashMap.put("correo", email);
            hashMap.put("imageProfile", imageProfile);

            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(RegistroComun.this, MainFeed.class);
                        intent.putExtra("user_type", 1);
                        progressDialog.dismiss();
                        Toast.makeText(RegistroComun.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegistroComun.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void terminarRegistro() {
        nombre = et_nombre.getText().toString().trim();
        apellido = et_apellido.getText().toString().trim();
        email = et_email.getText().toString().trim();
        pass1 = et_pass1.getText().toString().trim();
        pass2 = et_pass2.getText().toString().trim();

        if (!email.equals("") && !pass1.equals("") && !pass2.equals("") && !nombre.equals("")
                && !apellido.equals("")) {
            if (validarPasswords()) {
                progressDialog.setMessage("Realizando registro...");
                progressDialog.show();
                registrar();
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_LONG).show();
        }


    }

    private void registrar() {
        auth.createUserWithEmailAndPassword(email, pass1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            uploadImage();


                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) { //Si se presenta una colision
                                Toast.makeText(RegistroComun.this, "Este correo ya ha sido registrado", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(RegistroComun.this, "Fallo", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }

                    }
                });
    }

    private boolean validarPasswords() {
        if (pass1.equals(pass2)) {
            return true;
        } else {
            return false;
        }
    }
}
