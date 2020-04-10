package com.example.a03test.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a03test.R;
import com.example.a03test.activity.MainActivity;
import com.example.a03test.model.Fruta;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FrutaAdapter extends RecyclerView.Adapter<FrutaAdapter.ViewHolder> {

    //1. Definimos los atriburos
    private Activity activity;
    private List<Fruta> lstFruta;
    private int layout;
    private OnItemClickListener itemClickListener;

    //4. Creamos el constructor
    public FrutaAdapter(Activity activity, List<Fruta> lstFruta, int layout,
                        OnItemClickListener itemClickListener) {
        this.activity = activity;
        this.lstFruta = lstFruta;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    //5. Implementamos los métodos del Adapter
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(this.layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(lstFruta.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return lstFruta.size();
    }

    //3. Creando el ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder
    implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private ImageView imgFondo;
        private TextView txtNombre;
        private TextView txtDescripcion;
        private TextView txtCantidad;
        private ImageButton btnAgregar;
        private ImageButton btnDisminuir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgFondo = itemView.findViewById(R.id.imgFondo);
            this.txtNombre = itemView.findViewById(R.id.txtNombre);
            this.txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            this.txtCantidad = itemView.findViewById(R.id.txtCantidad);
            this.btnAgregar = itemView.findViewById(R.id.btnAgregar);
            this.btnDisminuir = itemView.findViewById(R.id.btnDisminuir);
            //Habilitando el menú contextual
            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(final Fruta fruta, final OnItemClickListener listener) {
            txtNombre.setText(fruta.getNombre());
            txtDescripcion.setText(fruta.getDescripcion());
            txtCantidad.setText(String.valueOf(fruta.getCantidad()));
            imgFondo.setImageResource(fruta.getImagenFondo());
//            Picasso.with(activity).load(fruta.getImagenFondo()).fit().into(this.imgFondo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(fruta, getAdapterPosition());
                }
            });

            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.agregarItem(fruta, getAdapterPosition());
                }
            });
            btnDisminuir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.disminuirItem(fruta, getAdapterPosition());
                }
            });
        }

        //Creando el menu contextual
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Fruta f = lstFruta.get(this.getAdapterPosition());
            menu.setHeaderTitle(f.getNombre());
            menu.setHeaderIcon(f.getImagenIcono());

            //Inflando el menu contextual
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.fruta_context_menu, menu);

            //Añadimos el listener onMenuclick para las acciones del menu contextual, con el
            //método onContextItemSelected en el activity
            for(int i=0; i<menu.size(); i++){
                menu.getItem(i).setOnMenuItemClickListener(this);
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            MainActivity m = (MainActivity) activity;
            switch (item.getItemId()) {
                case R.id.cmenuInfo:
                    Fruta f = lstFruta.get(getAdapterPosition());
                    m.mostrarInfoFull(f.getNombre(), f.getDescripcion());
                    return true;
                case R.id.cmenuReset:
                    m.resetearCantidad(getAdapterPosition());
                    return true;
                case R.id.cmenuEliminar:
                    m.eliminarFruta(getAdapterPosition());
                    return true;
                default:
                    return false;
            }
        }
    }

    //2. Creando la interface
    public interface OnItemClickListener {
        void onItemClick(Fruta fruta, int posicion);
        void agregarItem(Fruta fruta, int posicion);
        void disminuirItem(Fruta fruta, int posicion);
    }
}
