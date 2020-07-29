package com.example.ezservice.models;

public class Categoria {
    private String imagen;
    private String nombre;

    public Categoria(){}

    public Categoria(String nombre){
        this.nombre=nombre;

    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
