package com.example.examenfinal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {

    @GET("N000196393")
    Call<Entrenador> getEntrenador();

    @POST("N000196393")
    Call<Void> postCrearEntrenador(@Body Entrenador entrenador);

    @POST("N000196393/crear")
    Call<Void> postCrearPokemon(@Body pokemon pokemon);

    @GET("N000196393")
    Call<List<pokemon>> getPokemones();

    @GET("pokemones/{ID}")
    Call<pokemon> getPokemonID(@Path("ID") String id);

    @POST("entrenador/N000196393/pokemon")
    Call<pokemon> postCapturarPokemon(@Body pokemon cap);

    @GET("entrenador/N000196393/pokemones")
    Call<List<pokemon>> getPokemonesCapturados();


}
