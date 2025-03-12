package model;

import annotation.Colonne;
import annotation.Table;

import java.sql.Date;

@Table(nom = "avion", prefixe = "AVN")
public class Avion {

    @Colonne("id")
    private String id;

    @Colonne("modele")
    private String modele;

    @Colonne("business")
    private int business;

    @Colonne("eco")
    private int eco;

    @Colonne("fabrication")
    private Date fabrication;

    @Colonne("nom")
    private String nom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String setModele() {
        return modele;
    }

    public void setModele(String nom) {
        this.modele = nom;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public int getEco() {
        return eco;
    }

    public void setEco(int eco) {
        this.eco = eco;
    }

    public Date getFabrication() {
        return fabrication;
    }

    public void setFabrication(Date fabrication) {
        this.fabrication = fabrication;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
