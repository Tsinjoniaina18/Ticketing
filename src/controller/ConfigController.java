package controller;

import database.GenericRepo;
import mg.itu.prom16.annotation.*;
import mg.itu.prom16.mapping.ModelView;
import model.ConfigPromotion;
import model.ConfigReservation;
import model.TypeSiege;
import model.Vol;

import java.util.List;

@Controller @Auth(1)
public class ConfigController {

    @Url("config_reservation") @Get
    public ModelView prepaConfigReservation(){
        ModelView modelView = new ModelView("views/configReservation.jsp");
        try{
            ConfigReservation configReservation = GenericRepo.findById("CFGR00001", ConfigReservation.class);
            System.out.println("Config reservation: "+configReservation.getId()+" - "+configReservation.getHeure_reservation()+" - "+configReservation.getHeure_annulation());
            modelView.addObject("config", configReservation);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }

    @Url("config_reservation") @Post
    public ModelView configReservation(@Param("config") ConfigReservation configReservation){
        ModelView modelView = new ModelView("views/configReservation.jsp");
        try{
            System.out.println("Config reservation: "+configReservation.getId()+" - "+configReservation.getHeure_reservation()+" - "+configReservation.getHeure_annulation());
            GenericRepo.save(configReservation);

            System.out.println("Sucess !!!");

            modelView.addObject("sucess", "Configuration sauvegarder");
            modelView.addObject("config", configReservation);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }

    @Url("config_promotion") @Get
    public ModelView prepaConfigPromotion(){
        ModelView modelView = new ModelView("views/configPromotion.jsp");
        try{
            Vol vol = new Vol();
            List<Vol> vols = vol.listeVol();
            List<TypeSiege> typeSieges = GenericRepo.findAll(TypeSiege.class);

            modelView.addObject("vols", vols);
            modelView.addObject("type", typeSieges);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }

    @Url("config_promotion") @Post
    public ModelView configPromotion(@Param("config") ConfigPromotion configPromotion){
        ModelView modelView = new ModelView("views/configPromotion.jsp");
        try{
            GenericRepo.save(configPromotion);

            System.out.println("Sucess !!!");

            modelView.addObject("sucess", "Configuration sauvegarder");

            List<Vol> vols = GenericRepo.findAll(Vol.class);
            List<TypeSiege> typeSieges = GenericRepo.findAll(TypeSiege.class);

            modelView.addObject("vols", vols);
            modelView.addObject("type", typeSieges);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }
}
