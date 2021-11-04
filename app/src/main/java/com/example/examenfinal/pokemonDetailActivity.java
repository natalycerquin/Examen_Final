package com.example.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class pokemonDetailActivity extends AppCompatActivity {

    ImageView image_pokemon;
    EditText nombre, tipo;
    private pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        image_pokemon = findViewById(R.id.image_poke);
        nombre = findViewById(R.id.namePoke);
        tipo = findViewById(R.id.typePoke);

        String ID = getIntent().getStringExtra("ID");
        Log.e("ID DET ", ID);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);

        Call<pokemon> getDetalle = service.getPokemonID(ID);

        getDetalle.enqueue(new Callback<pokemon>() {
            @Override
            public void onResponse(Call<pokemon> call, Response<pokemon> response) {
                pokemon = response.body();

                String im = "https://upn.lumenes.tk" + pokemon.getUrl_imagen();
                Picasso.get()
                        .load(im)
                        .into(image_pokemon);
                nombre.setText(pokemon.getNombre());
                tipo.setText(pokemon.getTipo());
            }

            @Override
            public void onFailure(Call<pokemon> call, Throwable t) {

            }
        });

        findViewById(R.id.capt_poke).setOnClickListener(v -> {
            pokemon pokemon = new pokemon();
            pokemon.setPokemon_id(ID);

            Call<pokemon> capP = service.postCapturarPokemon(pokemon);

            capP.enqueue(new Callback<pokemon>() {
                @Override
                public void onResponse(Call<pokemon> call, Response<pokemon> response) {
                    String respuesta = String.valueOf(response.code());
                    if (respuesta.equals("200")) {
                        Toast.makeText(getApplicationContext(), "Pokemon Capturado", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "Pokemon no Capturado", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<pokemon> call, Throwable t) {

                }
            });
        });

        findViewById(R.id.location_poke).setOnClickListener(view -> {

            String lati = String.valueOf(pokemon.getLatitude());
            String longi = String.valueOf(pokemon.getLongitude());

            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("lati", lati);
            intent.putExtra("longi", longi);

            startActivity(intent);
        });
    }
}