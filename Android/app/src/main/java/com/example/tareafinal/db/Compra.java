package com.example.tareafinal.db;

import java.sql.Time;
import java.util.Date;

public class Compra {

    private int idUsuario;
    private int idProducto;
    private Date fecha;
    private Time hora;
    private int cantidad;
    private boolean comprado;

    public Compra(){}

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isComprado() {return comprado;}

    public void setComprado(boolean comprado) {this.comprado = comprado;}

}
