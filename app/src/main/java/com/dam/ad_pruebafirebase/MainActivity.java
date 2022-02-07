package com.dam.ad_pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.AttributionSource;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dam.ad_pruebafirebase.model.Coche;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Despues de enlazar firebase, vamos a autenticar
    // con FirebaseAuth
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseDatabase fbDB;
    DatabaseReference dbRef;


    TextView tvSaludo;
    Button btnDesconectar;
    Button btnSetValue;
    Button btnProbarPush;
    Button btnUpdate;
    Button btnSetValueO;
    Button btnUpdateChilObj;
    Button btnSetValueModif;
    Button btnDelete;
    Button btnPruebaLeer;
    Button btnLeerOrden;
    Button btnStorage;

    int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();//Cogemos el usuario de fAuth

        fbDB = FirebaseDatabase.getInstance();//Instanciamos FirebaseDataBase (es decir new)
        dbRef = fbDB.getReference();//Con getreference se apunta a toda la base de datos.
        //dbRef = fbDB.getReference().child("animales");//Desendemos

        tvSaludo = findViewById(R.id.tvSaludo);
        btnDesconectar = findViewById(R.id.btnDesconectar);
        btnSetValue = findViewById(R.id.btnSetValue);
        btnProbarPush = findViewById(R.id.btnProbarPudh);
        btnUpdate = findViewById(R.id.btnUpdateC);
        btnSetValueO = findViewById(R.id.btnSetValueO);
        btnUpdateChilObj = findViewById(R.id.btnUpdateChildObj);
        btnSetValueModif = findViewById(R.id.btnSetValueModif);
        btnDelete = findViewById(R.id.btnRemove);
        btnPruebaLeer = findViewById(R.id.btnPruebaLeer);
        btnLeerOrden = findViewById(R.id.btnLeerOrdenado);
        btnStorage = findViewById(R.id.btnStorage);

        tvSaludo.setText(String.format(getResources().getString(R.string.tv_saludo), user.getEmail()));

        btnDesconectar.setOnClickListener(this);
        btnSetValue.setOnClickListener(this);
        btnProbarPush.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnSetValueO.setOnClickListener(this);
        btnUpdateChilObj.setOnClickListener(this);
        btnSetValueModif.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnPruebaLeer.setOnClickListener(this);
        btnLeerOrden.setOnClickListener(this);
        btnStorage.setOnClickListener(this);
        contador = 1;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnDesconectar){
            desconectar();
        } else if (view.getId() == R.id.btnSetValue){
            probarSetValue();
        } else if (view.getId() == R.id.btnProbarPudh){
            probarPush();
        } else if (view.getId() == R.id.btnUpdateC){
            probarUpdateChildren();
        } else if (view.getId() == R.id.btnSetValueO) {
            probarSetValueObj();
        } else if (view.getId() == R.id.btnUpdateChildObj){
            probarUpdateChildObj();
        } else if (view.getId() == R.id.btnSetValueModif){
            probarSetValueModif();
        } else if (view.getId() == R.id.btnRemove){
            probarDelete();
        } else if (view.getId() == R.id.btnPruebaLeer){
            probarLeer();
        } else if (view.getId() == R.id.btnLeerOrdenado){
            probarLeerOrdenado();
        } else if (view.getId() == R.id.btnStorage){
            Intent i = new Intent(this, StorageActivity.class);
            startActivity(i);
        }
    }

    private void probarLeerOrdenado() {
        Intent i = new Intent(this, LecturaActivity.class);
        startActivity(i);
    }

    private void probarLeer(){
        Intent i = new Intent(this, LecturaActivity.class);
        startActivity(i);
    }


    private void probarDelete() {
        dbRef.child("coches/coche2").removeValue();//
        dbRef.child("paises/pais2").setValue(null);

        Map<String, Object> estructuras = new HashMap<String, Object>();
        estructuras.put("animal2", null);
        estructuras.put("animal3", null);
        dbRef.child("animales").updateChildren(estructuras);

    }

    private void probarSetValueModif() {
        //Usamos SetValue para modificar el valor de uno de los atributos
        //de un objeto.
            //Desendemos hasta el modelo para cambiar su valor
        dbRef.child("coches").child("coche2/modelo").setValue("ibiza");
    }

    private void probarUpdateChildObj() {
        Map<String, Object> estructuras = new HashMap<String,Object>();
        estructuras.put("coche " + (contador + 10), new Coche("Audi","A1"));
        estructuras.put("coche " + (contador + 11), new Coche("Seat","Leon"));
        estructuras.put("coche " + (contador + 12), new Coche("Peugeot","206"));
        dbRef.child("coches").updateChildren(estructuras);
    }

    private void probarSetValueObj() {
        dbRef.child("coches").child("coche" + contador).setValue(new Coche("Seat","Leon"));
        contador ++;
    }

    private void probarUpdateChildren() {
        Map<String, Object> estructuras = new HashMap<String,Object>();
        estructuras.put("pais1", "España");
        estructuras.put("pais2", "Francia");
        estructuras.put("pais3", "Italia");
        dbRef.child("paises").updateChildren(estructuras);
    }

    private void probarPush() {
        String clave = dbRef.push().getKey();
        //dbRef.child(clave).setValue("Elefante");
        dbRef.child("colores").child(clave).setValue("Rojo");
        //dbRef.push().setValue("Elefante"); //Las dos cosas en una misma operacion
    }

    private void probarSetValue() {
        String clave = dbRef.push().getKey();
        dbRef.child("animales").child(clave).setValue("Mono");
        contador ++;
    }

    private void desconectar() {
        fAuth.signOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}