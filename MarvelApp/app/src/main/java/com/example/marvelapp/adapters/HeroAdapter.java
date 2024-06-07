package com.example.marvelapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvelapp.R;
import com.example.marvelapp.classes.Hero;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.ViewHolder>{

    private List<Hero> datos;
    private List<Hero> original;
    final HeroAdapter.OnItemClickListener listener;
    private static final String TAG = "HeroAdapter";

    public interface OnItemClickListener {
        void onItemClick(Hero dato);
    }


    public HeroAdapter(List<Hero> datos, HeroAdapter.OnItemClickListener listener) {
        this.datos = datos;
        this.listener = listener;
        this.original = new ArrayList<>(datos);
    }
    @NonNull
    @Override
    public HeroAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hero,parent,false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroAdapter.ViewHolder holder, int position) {
        Hero dato = datos.get(position);
        holder.bind(dato);
    }

    public void filter(String buscar) {
        if (buscar == null || buscar.isEmpty()) {
            datos.clear();
            datos.addAll(original);
        } else {
            List<Hero> filteredList = original.stream()
                    .filter(i -> i.getName().toUpperCase().contains(buscar.toUpperCase()))
                    .collect(Collectors.toList());
            datos.clear();
            datos.addAll(filteredList);
        }
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_hero,txt_descripcion,txt_modified;
        ImageView img_hero;
        HeroAdapter.OnItemClickListener listener;
        public ViewHolder(@NonNull View itemView, HeroAdapter.OnItemClickListener listener) {
            super(itemView);
            txt_hero = itemView.findViewById(R.id.txt_hero);
            txt_descripcion = itemView.findViewById(R.id.txt_descripcion);
            txt_modified = itemView.findViewById(R.id.txt_modified);
            img_hero = itemView.findViewById(R.id.img_hero);
            this.listener = listener;
        }

        public void bind(Hero dato){
            txt_hero.setText(dato.getName());
            txt_descripcion.setText(dato.getDescription());
            txt_modified.setText(dato.getModified());

            String imagenF= dato.getImage().trim();
            Log.i(TAG,"es igual: "+ imagenF.equals("https://i.annihil.us/u/prod/marvel/i/mg/a/f0/5202887448860.jpg"));
            Picasso.get()
                    .load(imagenF)
                    .error(R.drawable.error_image)  // Imagen de error si falla la carga
                    .into(img_hero, new Callback() {
                        @Override
                        public void onSuccess() {
                            // Carga exitosa
                            Log.d(TAG, "Image loaded successfully: " + dato.getImage());
                        }

                        @Override
                        public void onError(Exception e) {
                            // Error al cargar la imagen
                            Log.e(TAG, "Error loading image: " + dato.getImage(), e);
                        }
                    });
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(dato);
                    }
                }
            });
        }
    }
}
