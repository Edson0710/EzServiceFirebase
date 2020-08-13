package com.example.ezservice.models;

public class ListaServidor {
    String nombre, id, imagen, profesion;

    public ListaServidor() {

    }

    public ListaServidor(String nombre, String profesion){
        this.nombre = nombre;
        this.id = id;
        //this.imagen = imagen;
        this.profesion = profesion;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
}
