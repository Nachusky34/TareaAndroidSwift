package com.example.tareafinal.db;

public class Ordenador {

    private int id;
    private int img;
    private String name;
    private int price;
    private String descripcion;

    public Ordenador(int id, int img, String name, int price, String descripcion) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}