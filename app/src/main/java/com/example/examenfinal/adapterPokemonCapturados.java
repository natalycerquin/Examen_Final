package com.example.examenfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterPokemonCapturados extends RecyclerView.Adapter<adapterPokemonCapturados.viewPokemon> {
    private final List<pokemon> list;

    public adapterPokemonCapturados(List<pokemon> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewPokemon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewPokemon(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_pokemon, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull adapterPokemonCapturados.viewPokemon holder, int position) {

        pokemon pokemon = list.get(position);
        Picasso.get().load("https://upn.lumenes.tk/" + pokemon.getUrl_imagen()).into(holder.image_anime);
        holder.text_anime.setText(pokemon.getNombre());
        holder.text_description.setText(pokemon.getTipo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class viewPokemon extends RecyclerView.ViewHolder {
        ImageView image_anime;
        TextView text_anime, text_description;

        public viewPokemon(@NonNull View itemView) {
            super(itemView);

            image_anime = itemView.findViewById(R.id.image_anime);
            text_anime = itemView.findViewById(R.id.text_anime);
            text_description = itemView.findViewById(R.id.text_description);
        }
    }
}
