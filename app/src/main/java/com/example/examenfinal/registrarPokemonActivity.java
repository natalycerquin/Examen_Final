package com.example.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registrarPokemonActivity extends AppCompatActivity {

    ImageView imagePOKE;
    EditText nombrePokemon, tipoPokemon, latitudePokemon, longitudPokemon;
    Button seleccionarIamgen, registrarPokemon;
    Uri imageUri;
    String imagenString;

    private static final int PICK_IMAGE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_pokemon);

        imagePOKE = findViewById(R.id.imagePOKE);
        nombrePokemon = findViewById(R.id.nombrePoke);
        tipoPokemon = findViewById(R.id.tipoPoke);
        latitudePokemon = findViewById(R.id.latitudePoke);
        longitudPokemon = findViewById(R.id.longitudPoke);
        seleccionarIamgen = findViewById(R.id.imagenBT);
        registrarPokemon = findViewById(R.id.registrarPokemon);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/pokemons/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);

        registrarPokemon.setOnClickListener(v -> {

            String nombre = nombrePokemon.getEditableText().toString().trim();
            String tipo = tipoPokemon.getEditableText().toString().trim();
            String lati = latitudePokemon.getEditableText().toString().trim();
            String longi = longitudPokemon.getEditableText().toString().trim();

            pokemon pokemon = new pokemon(nombre, tipo, imagenString, Float.parseFloat(lati), Float.parseFloat(longi));

            Call<Void> entre = service.postCrearPokemon(pokemon);
            entre.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    String respuesta = String.valueOf(response.code());
                    if (respuesta.equals("200")) {
                        Toast.makeText(getApplicationContext(), "Pokemon Registrado", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), " Pokemon no Registrado", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        seleccionarIamgen.setOnClickListener(v -> cargarImagen());
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imagePOKE.setImageURI(imageUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] image = outputStream.toByteArray();
                String encodedString = Base64.encodeToString(image, Base64.DEFAULT);
                imagenString = encodedString;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}