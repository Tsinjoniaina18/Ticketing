package model.reservation;

import annotation.Colonne;
import annotation.Table;
import database.GenericRepo;
import mg.itu.prom16.annotation.NameField;
import model.*;

import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Table(nom = "reservation", prefixe = "RSV")
public class Reservation {
    @Colonne("id")
    private String id;

    @Colonne("id_utilisateur")
    private String id_utilisateur;

    @Colonne("passager")
    private String passager;

    @Colonne("age")
    private int age;

    @Colonne("id_vol")
    private String id_vol;

    @Colonne("date_reservation")
    private LocalDateTime date_reservation;

    @Colonne("siege")
    private int siege;

    @Colonne("type_siege")
    private String type_siege;

    @Colonne("promotion")
    private double promotion;

    @Colonne("prix")
    private double prix;

    @Colonne("passeport")
    @NameField(value = "passeport", file = true)
    private String passeport;

    @Colonne("passeport_bytes")
    @NameField(value = "passeport", file = true)
    private byte[] passeport_bytes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(String id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getPassager() {
        return passager;
    }

    public void setPassager(String passager) {
        this.passager = passager;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId_vol() {
        return id_vol;
    }

    public void setId_vol(String id_vol) {
        this.id_vol = id_vol;
    }

    public LocalDateTime getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(LocalDateTime date_reservation) {
        this.date_reservation = date_reservation;
    }

    public void setDate_reservation(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
        this.setDate_reservation(dateTime);
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

    public double getPromotion() {
        return promotion;
    }

    public void setPromotion(double promotion) {
        this.promotion = promotion;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getPasseport() {
        return passeport;
    }

    public void setPasseport(String passeport) {
        this.passeport = passeport;
    }

    public byte[] getPasseport_bytes() {
        return passeport_bytes;
    }

    public void setPasseport_bytes(byte[] passeportByte) {
        this.passeport_bytes = passeportByte;
    }

    public void checkDateValidation(LocalDateTime d2)throws Exception{
        Duration duration = Duration.between(this.getDate_reservation(), d2);
        long hours = duration.toHours();
        System.out.println(hours);

        ConfigReservation configReservation = GenericRepo.findById("CFGR00001", ConfigReservation.class);
        if(hours > configReservation.getHeure_reservation()){
            System.out.println("Valide");
        }else {
            throw new Exception("La date de reservation a ete depasee");
        }
    }

    public void checkDateAnnulation(LocalDateTime d1, LocalDateTime d2)throws Exception{
        Duration duration = Duration.between(d1, d2);
        long hours = duration.toHours();
        System.out.println(hours);

        ConfigReservation configReservation = GenericRepo.findById("CFGR00001", ConfigReservation.class);
        if(hours > configReservation.getHeure_annulation()){
            System.out.println("Valide");
        }else {
            throw new Exception("La date de reservation a ete depasee");
        }
    }

    public void generatePromotionAndPrix(ConfigPromotion configPromotion, Vol vol)throws Exception{
        double prix = vol.getBusiness();

        if(this.getType_siege().equals("TSG00002")) prix = vol.getEco();

        ConfigEnfant configEnfant = GenericRepo.findById("CFGE00001", ConfigEnfant.class);

        if(this.getAge()<=configEnfant.getAge()){
            prix = (prix * configEnfant.getPourcentage_prix())/100;
        }

        if(configPromotion.getSiege()==0){
            this.setPromotion(0);
        }else {
            double promotion = prix*(configPromotion.getRemise()/100);

            int siege = Math.min(configPromotion.getSiege(), this.getSiege());
            this.setPromotion(promotion*siege);
        }
        this.setPrix((prix*this.getSiege())-this.getPromotion());
    }

    public void reservation(Connection connection)throws Exception{
        Vol vol = GenericRepo.findById(this.getId_vol(), Vol.class);
        this.checkDateValidation(vol.getDecollage());

        ReservationPlaceInfo reservationPlaceInfo = new ReservationPlaceInfo(this.getId_vol(), this.getType_siege());
        reservationPlaceInfo.generatePlaceInfo(connection);
        if(this.getSiege() > reservationPlaceInfo.getDisponible()){
            throw new Exception("Nombre de place insuffisant");
        }

        this.generatePromotionAndPrix(reservationPlaceInfo.getPromotion(), vol);

        GenericRepo.save(this);
    }

    public List<Reservation> reservationList (String id_utilisateur)throws Exception{
        List<Reservation> reservations = GenericRepo.findCondition(Reservation.class, " and id_utilisateur = '"+id_utilisateur+"'");
        for (int i = 0; i < reservations.size(); i++) {

            TypeSiege typeSiege = GenericRepo.findById(reservations.get(i).getType_siege(), TypeSiege.class);

            reservations.get(i).setType_siege(typeSiege.getNom());
        }
        return reservations;
    }

    public String srcToImage()throws Exception{
        String base64Image = "";
        String mimeType = "jpeg";
        if (this.getPasseport_bytes() != null) {
            base64Image = Base64.getEncoder().encodeToString(this.getPasseport_bytes());
            String fileName = this.getPasseport();
            fileName = fileName.replace("\"", "");
            String[] parts = fileName.split("\\.");
            if (parts.length > 1) {
                mimeType = parts[parts.length - 1];
            }
        }
        String src = "data:image/"+mimeType+";base64,"+base64Image;
        return src;
    }

    public String getDate_reservationValue(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String decollageStr = this.getDate_reservation().format(formatter);
        return decollageStr;
    }

    public String formatDate_reservation(){
        String date = "";
        date = this.getDate_reservationValue().replace("T", " a ");
        return date;
    }
}
