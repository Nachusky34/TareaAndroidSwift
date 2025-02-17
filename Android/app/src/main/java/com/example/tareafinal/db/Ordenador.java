package com.example.tareafinal.db;

public class Ordenador {

    private int id;
    private int img;
    private String name;
    private int price;
    private String descripcion;

    public Ordenador(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
