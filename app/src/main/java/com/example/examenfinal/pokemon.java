package com.example.examenfinal;

import java.io.Serializable;

public class pokemon implements Serializable {

    String nombre;
    String tipo;
    String imagen;
    String url_imagen;
    float latitude;
    float longitude;
    Integer esta_atrapado;
    String pokemon_id;
    Integer id;

    public pokemon() {
    }

    public pokemon(String nombre, String tipo, String imagen, String url_imagen, float latitude, float longitude, Integer esta_atrapado, String pokemon_id, Integer id) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.imagen = imagen;
        this.url_imagen = url_imagen;
        this.latitude = latitude;
        this.longitude = longitude;
        this.esta_atrapado = esta_atrapado;
        this.pokemon_id = pokemon_id;
        this.id = id;
    }

    public pokemon(String nombre, String tipo, String imagen, float latitude, float longitude) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.imagen = imagen;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Integer getEsta_atrapado() {
        return esta_atrapado;
    }

    public void setEsta_atrapado(Integer esta_atrapado) {
        this.esta_atrapado = esta_atrapado;
    }

    public String getPokemon_id() {
        return pokemon_id;
    }

    public void setPokemon_id(String pokemon_id) {
        this.pokemon_id = pokemon_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
