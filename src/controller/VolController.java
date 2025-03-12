package controller;

import database.GenericRepo;
import mg.itu.prom16.annotation.*;
import mg.itu.prom16.mapping.ModelView;
import model.Avion;
import model.Ville;
import model.Vol;

import java.util.List;

@Controller @Auth(1)
public class VolController {

    @Url("vol") @Get
    public ModelView prepaVol(){
        ModelView modelView = new ModelView("views/vol.jsp");
        try{
            List<Avion> avions = GenericRepo.findAll(Avion.class);
            List<Ville> villes = GenericRepo.findAll(Ville.class);

            modelView.addObject("avions", avions);
            modelView.addObject("villes", villes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }

    @Url("vol") @Post
    public ModelView save(@Param("decollage") String decollage, @Param("vol") Vol vol){
        ModelView modelView = new ModelView("vols");
        try{
            String suite = "mis a jour";
            vol.setDecollage(decollage);
            if(vol.getId() == null){
                vol.setEtat(1);
                suite = "enregistree";
            }

            GenericRepo.save(vol);

            modelView.addObject("sucess", "Vol "+suite);
        } catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }

    @Public @Url("vols") @Get
    public ModelView listeVol(){
        ModelView modelView = new ModelView("views/vols.jsp");
        try{
            Vol vol = new Vol();
            List<Vol> vols = vol.listeVol();
            List<Ville> villes = GenericRepo.findAll(Ville.class);


            modelView.addObject("vols", vols);
            modelView.addObject("villes", villes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }

    @Url("update") @Get
    public ModelView updateVol(@Param("id") String id){
        ModelView modelView = new ModelView("views/vol.jsp");
        try{
            List<Avion> avions = GenericRepo.findAll(Avion.class);
            List<Ville> villes = GenericRepo.findAll(Ville.class);

            modelView.addObject("avions", avions);

            Vol vol = GenericRepo.findById(id, Vol.class);
            modelView.addObject("vol", vol);
            modelView.addObject("villes", villes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }

    @Public @Url("fiche") @Get
    public ModelView ficheVol(@Param("id") String id){
        ModelView modelView = new ModelView("views/volFiche.jsp");
        try{
            Vol vol = new Vol();
            vol.ficheVol(id);
            modelView.addObject("vol", vol);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }

    @Public @Url("rechercher") @Post
    public ModelView rechercher(@Param("depart") String depart, @Param("destination") String destination, @Param("min") String min, @Param("max") String max, @Param("etat") String etat){
        ModelView modelView = new ModelView("views/vols.jsp");
        try{
            Vol vol = new Vol();
            List<Vol> vols = vol.listeVol(depart, destination, min, max, etat);
            List<Ville> villes = GenericRepo.findAll(Ville.class);

            modelView.addObject("vols", vols);
            modelView.addObject("villes", villes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelView;
    }
}
