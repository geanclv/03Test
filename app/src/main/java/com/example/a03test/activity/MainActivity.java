package com.example.a03test.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a03test.R;
import com.example.a03test.adapter.FrutaAdapter;
import com.example.a03test.model.Fruta;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;

    private List<Fruta> lstFruta;

    //1. Creamos y definimos el adaptador
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //5. Completamos la funcionalidad
        initControls();
        initLista();

        layoutManager = new LinearLayoutManager(this);
        adapter = new FrutaAdapter(this, lstFruta, R.layout.recycler_view_item,
                new FrutaAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Fruta fruta, int posicion) {
                        mostrarInfoBasica(fruta.getNombre());
                    }

                    @Override
                    public void agregarItem(Fruta fruta, int posicion) {
                        aumentarCantidad(fruta, posicion);
                    }

                    @Override
                    public void disminuirItem(Fruta fruta, int posicion) {
                        disminuirCantidad(fruta, posicion);
                    }
                });
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        //El menú contextual se registra en el adaptador
    }

    //4. Creando el menu de la barra de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAgregar:
                nuevaFruta(new Fruta("Fruta " + (++contador),
                        "Descripcion " + contador,
                        R.drawable.ic_blueberry_512,
                        R.drawable.ic_blueberry_24,
                        0),
                        lstFruta.size());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //3. Definimos las operaciones a realizar
    private void mostrarInfoBasica(String nombre) {
        Toast.makeText(context, "Esta es una " + nombre, Toast.LENGTH_LONG).show();
    }

    public void mostrarInfoFull(String nombre, String descripcion) {
        Toast.makeText(context, nombre + ": " + descripcion, Toast.LENGTH_LONG).show();
    }

    private void aumentarCantidad(Fruta fruta, int posicion){
        if (fruta.getCantidad() >= 10){
            Toast.makeText(context, "Ya no puedes aumentar la cantidad", Toast.LENGTH_LONG).show();
        } else {
            fruta.setCantidad(fruta.getCantidad() + 1);
            this.adapter.notifyItemChanged(posicion);
        }
    }

    private void disminuirCantidad(Fruta fruta, int posicion){
        if(fruta.getCantidad() <= 0) {
            Toast.makeText(context, "Ya no puedes disminuir la cantidad", Toast.LENGTH_LONG).show();
        } else {
            fruta.setCantidad(fruta.getCantidad() - 1);
            this.adapter.notifyItemChanged(posicion);
        }
    }

    public void resetearCantidad(int posicion) {
        lstFruta.get(posicion).setCantidad(0);
        this.adapter.notifyItemChanged(posicion);
    }

    private void nuevaFruta(Fruta fruta, int posicion){
        lstFruta.add(posicion, fruta);
        this.adapter.notifyItemInserted(posicion);
        this.layoutManager.scrollToPosition(posicion);
    }

    public void eliminarFruta(int posicion){
        lstFruta.remove(posicion);
        this.adapter.notifyItemRemoved(posicion);
    }

    //2. Iniciando componentes
    private void initLista() {
        lstFruta = new ArrayList<Fruta>() {{
            add(new Fruta("Blueberry", "Fruta azul del campo", R.drawable.ic_blueberry_512,
                    R.drawable.ic_blueberry_24, 0));
            add(new Fruta("Manzana", "Fruta roja del campo", R.drawable.ic_manzana_512,
                    R.drawable.ic_manzana_24, 0));
            add(new Fruta("Platano", "Fruta amarilla del campo", R.drawable.ic_platano_512,
                    R.drawable.ic_platano_24, 0));
        }};
    }

    private void initControls() {
        recyclerView = findViewById(R.id.recyclerView);
    }
}
