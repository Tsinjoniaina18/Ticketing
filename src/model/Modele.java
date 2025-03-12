package model;

import annotation.Colonne;
import annotation.Table;

@Table(nom = "modele", prefixe = "MOD")
public class Modele {
    @Colonne("id")
    private String id;

    @Colonne("nom")
    private String nom;

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
}
