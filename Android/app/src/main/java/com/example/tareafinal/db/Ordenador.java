package com.example.tareafinal.db;

public class Ordenador {

    private String id;
    private String img;
    private String nombre;
    private String precio;
    private String descripcion;

    public Ordenador(String id, String img, String name, String price, String descripcion) {
        this.id = id;
        this.img = img;
        this.nombre = name;
        this.precio = price;
        this.descripcion = descripcion;
    }

    public Ordenador() {} // firebase necesita un constructor vacio

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
