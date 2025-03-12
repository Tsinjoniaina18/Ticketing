package model;

import annotation.Colonne;
import annotation.Table;

@Table(nom = "config_enfant", prefixe = "CFGE")
public class ConfigEnfant {

    @Colonne("id")
    private String id;

    @Colonne("age")
    private int age;

    @Colonne("pourcentage_prix")
    private double pourcentage_prix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPourcentage_prix() {
        return pourcentage_prix;
    }

    public void setPourcentage_prix(double pourcentage_prix) {
        this.pourcentage_prix = pourcentage_prix;
    }
}
