package model.reservation;

import database.GenericRepo;
import model.Avion;
import model.ConfigPromotion;
import model.TypeSiege;
import model.Vol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReservationPlaceInfo {

    private String id_vol;
    private String type_siege;

    private int reserver;

    private int disponible;
    private ConfigPromotion promotion;

    public ReservationPlaceInfo() {
    }

    public ReservationPlaceInfo(String id_vol, String type_siege) {
        this.id_vol = id_vol;
        this.type_siege = type_siege;
    }

    public String getId_vol() {
        return id_vol;
    }

    public void setId_vol(String id_vol) {
        this.id_vol = id_vol;
    }

    public String getType_siege() {
        return type_siege;
    }

    public void setType_siege(String type_siege) {
        this.type_siege = type_siege;
    }

    public int getReserver() {
        return reserver;
    }

    public void setReserver(int reserver) {
        this.reserver = reserver;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public ConfigPromotion getPromotion() {
        return promotion;
    }

    public void setPromotion(ConfigPromotion promotion) {
        this.promotion = promotion;
    }

    public void generatePlaceReserver(Connection connection)throws Exception{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            String requete = "select coalesce(sum(siege), 0) as reserver from reservation where id_vol = ? and type_siege = ? ";
            preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, this.getId_vol());
            preparedStatement.setString(2, this.getType_siege());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                this.setReserver(resultSet.getInt(1));

        }catch (Exception e){
            throw e;
        } finally {
            if(resultSet != null) resultSet.close();
            if(preparedStatement != null) preparedStatement.close();
        }
    }

    public void generatePlaceDisponible()throws Exception{
        Vol vol = GenericRepo.findById(this.getId_vol(), Vol.class);
        Avion avion = GenericRepo.findById(vol.getId_avion(), Avion.class);
        int max = avion.getBusiness();

        if(this.getType_siege().equalsIgnoreCase("TSG00002"))
            max = avion.getEco();

        this.setDisponible(max - this.getReserver());
    }

    public void generatePromotion()throws Exception{
        String afterWhere = " and id_vol = '"+this.getId_vol()+"' and type_siege = '"+this.getType_siege()+"'";
        List<ConfigPromotion> promotion = GenericRepo.findCondition(ConfigPromotion.class, afterWhere);
        ConfigPromotion configPromotion;
        if(promotion.size()>0){
            configPromotion = promotion.get(0);
            int prom = configPromotion.getSiege() - this.getReserver();
            configPromotion.setSiege(prom);

            if(prom <= 0){
                configPromotion.setSiege(0);
                configPromotion.setRemise(0);
            }

        }else {
            configPromotion = new ConfigPromotion();
            configPromotion.setId_vol(this.getId_vol());
            configPromotion.setType_siege(this.getType_siege());
            configPromotion.setSiege(0);
            configPromotion.setRemise(0);
        }
        this.setPromotion(configPromotion);
    }

    public void generatePlaceInfo(Connection connection)throws Exception{
        this.generatePlaceReserver(connection);
        this.generatePlaceDisponible();
        this.generatePromotion();
    }

    public List<ReservationPlaceInfo> generatePlaceInfos(Connection connection, String id_vol)throws Exception{
        List<ReservationPlaceInfo> reservationPlaceInfos = new ArrayList<>();

        List<TypeSiege> typeSieges = GenericRepo.findAll(TypeSiege.class);
        for (int i = 0; i < typeSieges.size(); i++) {
            ReservationPlaceInfo reservationPlaceInfo = new ReservationPlaceInfo(id_vol, typeSieges.get(i).getId());
            reservationPlaceInfo.generatePlaceInfo(connection);

            reservationPlaceInfos.add(reservationPlaceInfo);
        }

        return reservationPlaceInfos;
    }
}
