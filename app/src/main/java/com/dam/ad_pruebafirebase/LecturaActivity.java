package com.dam.ad_pruebafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.dam.ad_pruebafirebase.model.Coche;
import com.dam.ad_pruebafirebase.rvutil.CocheAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LecturaActivity extends AppCompatActivity {

    RecyclerView rvCoche;
    LinearLayoutManager llm;
    CocheAdapter cocheAdapter;
    ArrayList<Coche> listaCoches;
    FirebaseDatabase fdb;
    DatabaseReference dbref;
    ValueEventListener vel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura);

        rvCoche = findViewById(R.id.rvCoches);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);//Soluciona un error que se da en

        fdb = FirebaseDatabase.getInstance();
        dbref = fdb.getReference("coches");

        listaCoches = new ArrayList<Coche>();
    }

    @Override
    protected void onResume(){
        addValueEventListener();//Metodo propio
        super.onResume();
    }

    private void addValueEventListener(){
        if (vel == null){
            //*********************************************************
            //Estudiar las diferencias entre ValueEventListener y ChildEvent Listener
            //*********************************************************
            vel = new ValueEventListener() {
                @Override //Si se realiza la lectura, hara lo siguiente:
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Recorremos todos los hijos
                    Coche c;
                    for(DataSnapshot child : snapshot.getChildren()){
                        c = child.getValue(Coche.class);
                        listaCoches.add(c);
                    }
                    cargarRView();//Cargamos la lista de coches despues de ejecutar a lectura
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("LECTURA FIREBASE", "lectura cancelada",error.toException());
                }
            };
        }
        dbref.addValueEventListener(vel);
    }

    private void cargarRView() {
        cocheAdapter = new CocheAdapter(listaCoches);

        rvCoche.setLayoutManager(llm);
        rvCoche.setAdapter(cocheAdapter);
    }

    //Liberamos para que no consuma recursos del dispositivo
    @Override
    protected void onPause() {
        dbref.removeEventListener(vel);
        if (vel != null){
            vel = null;
        }
        super.onPause();
    }
}