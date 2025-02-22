package com.example.tareafinal.db;

public class Usuario {

    private int id;
    private String username;
    private String pwd;
    private String email;
    private int postalCode;
    private String fotoPerfil;

    public Usuario(int id, String username, String pwd, String email, int postalCode, String fotoPerfil) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.email = email;
        this.postalCode = postalCode;
        this.fotoPerfil = fotoPerfil;
    }

    public Usuario() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", postalCode=" + postalCode +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                '}';
    }
}
