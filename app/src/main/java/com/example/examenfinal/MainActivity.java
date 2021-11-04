package com.example.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView imagen;
    TextView nombres;
    TextView pueblo;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen = findViewById(R.id.imgaen);
        nombres = findViewById(R.id.nombres);
        pueblo = findViewById(R.id.pueblo);
        registrar = findViewById(R.id.registrar);
        registrar.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/entrenador/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service entrenador = retrofit.create(Service.class);
        Call<Entrenador> getEntrenador = entrenador.getEntrenador();

        getEntrenador.enqueue(new Callback<Entrenador>() {
            @Override
            public void onResponse(Call<Entrenador> call, Response<Entrenador> response) {

                Entrenador entrenador = response.body();
                assert entrenador != null;
                if (entrenador.getNombres() == null) {
                    registrar.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "ENTRENADOR NO ENCONTRADO, REGISTRAR UNO", Toast.LENGTH_SHORT).show();
                } else {
                    registrar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "ENTRENADOR ENCONTRADO", Toast.LENGTH_SHORT).show();
                    Picasso.get()
                            .load(entrenador.getImagen())
                            .into(imagen);
                    nombres.setText("Nombre: " + entrenador.getNombres());
                    pueblo.setText("Pueblo: " + entrenador.getPueblo());
                }
            }

            @Override
            public void onFailure(Call<Entrenador> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        registrar.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), registrarEntrenadorActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.CrearPokemon).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), registrarPokemonActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.verPokemon).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), pokemonsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.capturarPokemon).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), pokemonsCapturadosActivity.class);
            startActivity(intent);
        });
    }
}