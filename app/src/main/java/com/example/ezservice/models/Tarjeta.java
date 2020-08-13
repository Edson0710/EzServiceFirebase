package com.example.ezservice.models;

public class Tarjeta {
    private String imagen;
    private String nombre;
    private String id;

    public Tarjeta(){}

    public Tarjeta(String nombre, String id) {
        //this.imagen = imagen;
        this.nombre = nombre;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
