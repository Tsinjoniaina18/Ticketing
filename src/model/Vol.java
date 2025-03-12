package model;

import annotation.Colonne;
import annotation.Table;
import database.GenericRepo;
import mg.itu.prom16.annotation.NameField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Table(nom = "vol", prefixe = "VOL")
public class Vol {

    @Colonne("id")
    private String id;

    @Colonne("id_avion")
    @NameField("avion")
    private String id_avion;

    @Colonne("decollage")
    private LocalDateTime decollage;

    @Colonne("depart")
    private String depart;

    @Colonne("destination")
    private String destination;

    @Colonne("business")
    private double business;

    @Colonne("eco")
    private double eco;

    @Colonne("etat")
    private int etat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_avion() {
        return id_avion;
    }

    public void setId_avion(String id_avion) {
        this.id_avion = id_avion;
    }

    public LocalDateTime getDecollage() {
        return decollage;
    }

    public void setDecollage(LocalDateTime decollage) {
        this.decollage = decollage;
    }

    public void setDecollage(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
        this.setDecollage(dateTime);
    }

    public String formatDecollage(){
        String date = "";
        date = this.getDecollageValue().replace("T", " a ");
        return date;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getBusiness() {
        return business;
    }

    public void setBusiness(double business) {
        this.business = business;
    }

    public double getEco() {
        return eco;
    }

    public void setEco(double eco) {
        this.eco = eco;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void viewVol() throws Exception{
        String avion = GenericRepo.findById(this.getId_avion(), Avion.class).getNom();
        String depart = GenericRepo.findById(this.getDepart(), Ville.class).getNom();
        String destination = GenericRepo.findById(this.getDestination(), Ville.class).getNom();

        this.setId_avion(avion);
        this.setDepart(depart);
        this.setDestination(destination);
    }

    public List<Vol> listeVol()throws Exception{
        List<Vol> vols = GenericRepo.findCondition(Vol.class, " order by decollage asc");
        for (int i = 0; i < vols.size(); i++) {
            vols.get(i).viewVol();
        }
        return vols;
    }

    public List<Vol> listeVol(String depart, String destination, String min, String max, String etat)throws Exception{
        String afterWhere = "";
        afterWhere = this.generateAfterWhere(depart, destination, min, max, etat);
        System.out.println(afterWhere);
        List<Vol> vols = GenericRepo.findCondition(Vol.class, afterWhere);
        for (int i = 0; i < vols.size(); i++) {
            vols.get(i).viewVol();
        }
        return vols;
    }

    public String generateAfterWhere(String depart, String destination, String min, String max, String etat){
        String val = "";
        if (!depart.equals("")){
            val += " and depart = '"+depart+"'";
        }
        if (!destination.equals("")){
            val += " and destination = '"+destination+"'";
        }
        if(!min.equals("")){
            val += " and decollage >= '"+min+"'";
        }
        if(!max.equals("")){
            val += " and decollage < '"+max+"'";
        }
        if(!etat.equals("")){
            val += " and etat = "+etat+"";
        }
        return val;
    }

    public void ficheVol(String id)throws Exception{
        Vol vol = GenericRepo.findById(id, Vol.class);
        this.setId(vol.getId());
        this.setId_avion(vol.getId_avion());
        this.setDecollage(vol.getDecollage());
        this.setDepart(vol.getDepart());
        this.setDestination(vol.getDestination());
        this.setBusiness(vol.getBusiness());
        this.setEco(vol.getEco());

        this.viewVol();
    }

    public String getDecollageValue(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String decollageStr = this.getDecollage().format(formatter);
        return decollageStr;
    }

    public String isSelected(String s1, String s2){
        if(s1.equals(s2)){
            return "selected";
        }
        return "";
    }

    public String isSelected(int s1, int s2){
        if(s1 == s2){
            return "selected";
        }
        return "";
    }
}
