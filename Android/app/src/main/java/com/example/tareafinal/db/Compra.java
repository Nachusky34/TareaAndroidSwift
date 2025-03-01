package com.example.tareafinal.db;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Compra implements Serializable {

    private String idUsuario;
    private String idProducto;
    private String fecha;
    private String hora;
    private String cantidad;
    private boolean comprado;

    public Compra(){}

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isComprado() {return comprado;}

    public void setComprado(boolean comprado) {this.comprado = comprado;}

}
