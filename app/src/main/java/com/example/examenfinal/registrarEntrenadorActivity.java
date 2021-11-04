package com.example.examenfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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

public class registrarEntrenadorActivity extends AppCompatActivity {

    ImageView imagen;
    EditText nombres;
    EditText pueblo;
    Button registrar;
    Button subirGaleria;
    Button subirCamara;
    String imagenString;
    Uri imageUri;

    static final int REQUEST_IMAGE_CAPTURE = 10;
    private static final int PICK_IMAGE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_entrenador);

        imagen = findViewById(R.id.imgaen);
        nombres = findViewById(R.id.nombres);
        pueblo = findViewById(R.id.pueblo);
        registrar = findViewById(R.id.registrar);
        subirGaleria = findViewById(R.id.subirGaleria);
        subirCamara = findViewById(R.id.subirCamara);

        checkPermisos();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/entrenador/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service crearEntrenador = retrofit.create(Service.class);

        registrar.setOnClickListener(v -> {

            String nombresEntr = nombres.getEditableText().toString().trim();
            String puebloEntr = pueblo.getEditableText().toString().trim();

            Entrenador entrenador = new Entrenador(nombresEntr, puebloEntr, imagenString);

            Log.e("Nombre ", nombresEntr);
            Log.e("Pueblo ", puebloEntr);

            if (!nombresEntr.equals("") && !puebloEntr.equals("") && imagenString != null && !imagenString.equals("")) {
                Call<Void> entre = crearEntrenador.postCrearEntrenador(entrenador);
                entre.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        String respons = String.valueOf(response.code());
                        if (respons.equals("200")) {
                            Toast.makeText(getApplicationContext(), "Entrenador Registrado con exito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("error  ", respons);
                            Toast.makeText(getApplicationContext(), "Entrenador no registrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("CODIGO DE ERROR", t.getMessage());
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "comprobar campos", Toast.LENGTH_SHORT).show();
            }
        });

        subirGaleria.setOnClickListener(v -> cargarImagen());

        subirCamara.setOnClickListener(v -> abrirCamara());
    }

    private void abrirCamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imageBitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imagen = stream.toByteArray();
            imagenString = Base64.encodeToString(imagen, Base64.DEFAULT);

        }

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imagen.setImageURI(imageUri);
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

    private void checkPermisos() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(registrarEntrenadorActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
    }
}