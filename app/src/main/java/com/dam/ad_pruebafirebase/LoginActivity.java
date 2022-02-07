package com.dam.ad_pruebafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth fAuth;
    FirebaseUser fUser;

    EditText etEmail;
    EditText etPass;
    Button btnAcceder;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etALoginEmail);
        etPass = findViewById(R.id.etALoginPass);
        btnAcceder = findViewById(R.id.btnALoginAcceder);
        btnRegistrar = findViewById(R.id.btnALoginRegistrar);

        btnAcceder.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);

        //Obtenemos el usuario conectado
        //Si hay un usuario conectado al inicio:
        // deshabilitar el botón registrar y cargar el email
        fUser = fAuth.getCurrentUser();
        if (fUser != null) {
            //Opcion 1:
            //Presentar la pantalla de login con el email cargado
            //etEmail.setText(fUser.getEmail());
            //btnRegistrar.setEnabled(false);

            //opcion 2:
            //Acceder directamente al MainActivity
            enviarIntentAMain();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.equals(btnAcceder)){
            acceder();
        } else if (view.equals(btnRegistrar)){
            registrar();
        }
    }

    private void registrar() {
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty() ){
            //Similar al Toast
            Snackbar.make(btnAcceder, R.string.msj_no_hay_datos,Snackbar.LENGTH_LONG).show();
        } else if (pass.length() < 6 ){
            Snackbar.make(btnAcceder, R.string.msj_pass_incorrecto,Snackbar.LENGTH_LONG).show();
        } else{
            fAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                fUser = fAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, getString(R.string.msj_registrado), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.msj_no_registrado), Toast.LENGTH_SHORT).show();
                            } } });

        }
    }

    private void acceder() {
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()){
            //Similar al Toast
            Snackbar.make(btnAcceder, R.string.msj_no_hay_datos,Snackbar.LENGTH_LONG).show();
        } else{
            fAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                fUser = fAuth.getCurrentUser();
                                enviarIntentAMain();
                            } else {
                                Toast.makeText(LoginActivity.this,getString(R.string.msj_no_accede),
                                        Toast.LENGTH_SHORT).show();
                            } } });
        }
    }

    public void enviarIntentAMain(){
        Intent i = new Intent(LoginActivity.this,
                MainActivity.class);
        i.putExtra("EMAIL", fUser.getEmail());
        startActivity(i);
        finish();
    }
}