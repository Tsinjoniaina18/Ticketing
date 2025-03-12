package controller;

import connection.PGConnect;
import database.GenericRepo;
import mg.itu.prom16.annotation.*;
import mg.itu.prom16.mapping.CustomSession;
import mg.itu.prom16.mapping.ModelView;
import model.Utilisateur;
import model.Vol;
import model.reservation.Reservation;
import model.reservation.ReservationPlaceInfo;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

@Controller @Auth(2)
public class ReservationController {

    private CustomSession session;

    @Url("info") @Get
    public ModelView infoVol(@Param("id") String id_vol){
        ModelView modelView = new ModelView("views/volFiche.jsp");
        Connection connection = null;
        try {
            Vol vol = new Vol();
            vol.ficheVol(id_vol);
            modelView.addObject("vol", vol);

            connection = PGConnect.getInstance().getConnection();

            ReservationPlaceInfo reservationPlaceInfo = new ReservationPlaceInfo();
            List<ReservationPlaceInfo> reservationPlaceInfos = reservationPlaceInfo.generatePlaceInfos(connection, id_vol);
            modelView.addObject("infos", reservationPlaceInfos);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }

    @Url("reservation") @Post
    public ModelView reservation(@Param("date") String date, @Param("res") Reservation reservation){
        ModelView modelView = new ModelView("reservations");
        Connection connection = null;
        try {
            Utilisateur utilisateur = (Utilisateur) this.session.get("utilisateur");

            connection = PGConnect.getInstance().getConnection();

            reservation.setId_utilisateur(utilisateur.getId());
            reservation.setDate_reservation(date);

            reservation.reservation(connection);

            modelView.addObject("sucess", "Reservation enregistree");
        }catch (Exception e){
            e.printStackTrace();
            modelView.addObject("sucess", e.getMessage());
        }
        return modelView;
    }

    @Url("reservations") @Get
    public ModelView reservationList(){
        ModelView modelView = new ModelView("views/reservations.jsp");
        try {
            Utilisateur utilisateur = (Utilisateur) this.session.get("utilisateur");
            Reservation reservation = new Reservation();
            List<Reservation> reservations = reservation.reservationList(utilisateur.getId());
            modelView.addObject("reservations", reservations);
        }catch (Exception e){
            e.printStackTrace();

        }
        return modelView;
    }

    @Url("annulation") @Get
    public ModelView annulation(@Param("id") String id){
        ModelView modelView = new ModelView("views/reservations.jsp");
        try {
            LocalDateTime localDateTime = LocalDateTime.now();

            Reservation r = GenericRepo.findById(id, Reservation.class);
            Vol vol = GenericRepo.findById(r.getId_vol(), Vol.class);

            r.checkDateAnnulation(localDateTime, vol.getDecollage());

            GenericRepo.remove(r);

            Utilisateur utilisateur = (Utilisateur) this.session.get("utilisateur");
            Reservation reservation = new Reservation();
            List<Reservation> reservations = reservation.reservationList(utilisateur.getId());
            modelView.addObject("reservations", reservations);
            modelView.addObject("sucess", "La reservation a ete annulee");
        }catch (Exception e){
            modelView.addObject("sucess", "La date d'annuation ete depasee");
            e.printStackTrace();
        }
        return modelView;
    }
}
