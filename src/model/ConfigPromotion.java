package model;

import annotation.Colonne;
import annotation.Table;
import mg.itu.prom16.annotation.NameField;

@Table(nom = "config_promotion", prefixe = "CFGP")
public class ConfigPromotion {

    @Colonne("id")
    private String id;

    @Colonne("id_vol")
    @NameField("vol")
    private String id_vol;

    @Colonne("siege")
    private int siege;

    @Colonne("type_siege")
    @NameField("type")
    private String type_siege;

    @Colonne("remise")
    private double remise;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_vol() {
        return id_vol;
    }

    public void setId_vol(String id_vol) {
        this.id_vol = id_vol;
    }

    public int getSiege() {
        return siege;
    }

    public void setSiege(int siege) {
        this.siege = siege;
    }

    public String getType_siege() {
        return type_siege;
    }

    public void setType_siege(String type_siege) {
        this.type_siege = type_siege;
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }
}
