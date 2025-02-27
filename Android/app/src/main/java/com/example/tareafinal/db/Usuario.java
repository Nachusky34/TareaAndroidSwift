package com.example.tareafinal.db;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String id;
    private String username;
    private String password;
    private String email;
    private String postalCode;
    private String fotoPerfil;
    private boolean newsletter;

    public Usuario(String id, String username, String pwd, String email, String postalCode, String fotoPerfil, boolean newsletter) {
        this.id = id;
        this.username = username;
        this.password = pwd;
        this.email = email;
        this.postalCode = postalCode;
        this.fotoPerfil = fotoPerfil;
        this.newsletter = newsletter;
    }

    public Usuario() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {  // ✅ Ahora coincide con la variable
        return password;
    }

    public void setPassword(String password) {  // ✅ Setter también corregido
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                ", newsletter=" + newsletter +
                '}';
    }
}
