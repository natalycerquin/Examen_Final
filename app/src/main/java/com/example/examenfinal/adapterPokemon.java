package com.example.examenfinal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterPokemon extends RecyclerView.Adapter<adapterPokemon.viewPokemon> {
    private final List<pokemon> list;
    private final Context mContext;

    public adapterPokemon(List<pokemon> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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
    public void onBindViewHolder(@NonNull adapterPokemon.viewPokemon holder, int position) {

        pokemon pokemon = list.get(position);
        Picasso.get().load("https://upn.lumenes.tk/" + pokemon.getUrl_imagen()).into(holder.image_anime);
        holder.text_anime.setText(pokemon.getNombre());
        holder.text_description.setText(pokemon.getTipo());

        String ID = String.valueOf(pokemon.getId());
        
        holder.card_click.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, pokemonDetailActivity.class);

            intent.putExtra("ID", ID);
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class viewPokemon extends RecyclerView.ViewHolder {
        ImageView image_anime;
        TextView text_anime, text_description;
        CardView card_click;

        public viewPokemon(@NonNull View itemView) {
            super(itemView);

            image_anime = itemView.findViewById(R.id.image_anime);
            text_anime = itemView.findViewById(R.id.text_anime);
            text_description = itemView.findViewById(R.id.text_description);
            card_click = itemView.findViewById(R.id.card_click);


        }
    }
}
