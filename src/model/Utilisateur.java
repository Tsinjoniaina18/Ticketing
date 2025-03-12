package model;

import annotation.Colonne;
import annotation.Table;

@Table(nom = "utilisateur", prefixe = "UTR")
public class Utilisateur {
    @Colonne("id")
    private String id;
    @Colonne("nom")
    private String nom;
    @Colonne("email")
    private String email;
    @Colonne("password")
    private String password;

    @Colonne("role")
    private int role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
