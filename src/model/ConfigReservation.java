package model;

import annotation.Colonne;
import annotation.Table;
import mg.itu.prom16.annotation.NameField;

@Table(nom = "config_reservation", prefixe = "CFGR")
public class ConfigReservation {
    @Colonne("id")
    private String id;

    @Colonne("heure_reservation")
    @NameField("heureReservation")
    private int heure_reservation;

    @Colonne("heure_annulation")
    @NameField("heureAnnulation")
    private int heure_annulation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHeure_reservation() {
        return heure_reservation;
    }

    public void setHeure_reservation(int heure_reservation) {
        this.heure_reservation = heure_reservation;
    }

    public int getHeure_annulation() {
        return heure_annulation;
    }

    public void setHeure_annulation(int heure_annulation) {
        this.heure_annulation = heure_annulation;
    }
}
