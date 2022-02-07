package com.dam.ad_pruebafirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.ad_pruebafirebase.model.Coche;
import com.dam.ad_pruebafirebase.model.Coche2;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LecturaOrdenActivity extends AppCompatActivity {

    TextView tvLectura;
    FirebaseDatabase fdb;
    DatabaseReference dbRef;
    //ValueEventListener vel;
    ChildEventListener cel;//Detecta elemento a elemento
    Query consulta;

    ArrayList<Coche> listaCoches;
    String texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura_orden);

        tvLectura = findViewById(R.id.tvActLecOrdLectura);
        listaCoches = new ArrayList<Coche>();
        texto = "";

        fdb = FirebaseDatabase.getInstance();
        dbRef = fdb.getReference("coches2");


    }

    @Override
    protected void onResume() {
        super.onResume();
        addListener();
    }

    private void addListener() {
       /* if (vel == null) {
            vel = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Coche2 c;
                    for (DataSnapshot dss : dataSnapshot.getChildren()) {
                        c = dss.getValue(Coche2.class);
                        listaCoches.add(c.getCoche());
                    }

                    cargarTexto();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LecturaOrdenActivity.this,
                            R.string.msj_error_lectura, Toast.LENGTH_LONG).show();
                }
            };
            dbRef.addValueEventListener(vel);
        }*/

        //establecemos el orden en la consulta, descendiendo hasta
        // el atributo que queremos ordenar
        consulta = dbRef.orderByChild("coche/marca");
        if (cel == null) {
            cel = new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    listaCoches.add(snapshot.getValue(Coche2.class).getCoche());
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            };
            consulta.addChildEventListener(cel);
        }

    }

    private void cargarTexto() {
        for (Coche c: listaCoches) {
            texto += "\nMarca: " + c.getMarca() + " - Modelo: " + c.getModelo();
        }

        tvLectura.setText(texto);
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeListener();

    }

    private void removeListener() {
        /*if (vel != null) {
            dbRef.removeEventListener(vel);
            //consulta.removeEventListener(vel);
            vel = null;
        }*/
        if (cel != null) {
            consulta.removeEventListener(cel);
            cel = null;
        }
    }

}