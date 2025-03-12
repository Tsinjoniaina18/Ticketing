package controller;

import database.GenericRepo;
import mg.itu.prom16.annotation.*;
import mg.itu.prom16.mapping.CustomSession;
import mg.itu.prom16.mapping.ModelView;
import model.Auth;
import model.Utilisateur;
import model.Ville;
import model.Vol;

import java.util.List;

@Controller
public class Authentification {

    private CustomSession session;

    @Public
    @Get @Url("auth")
    public ModelView login(){
        ModelView modelView = new ModelView("views/login.jsp");
        return modelView;
    }

    @Public
    @Post @Url("check")
    public ModelView check(@Param("auth") Auth auth){
        String redirect = "views/login.jsp";
        ModelView modelView = new ModelView();
        modelView.setError("auth");
        try{

            Utilisateur utilisateur = auth.checkLogin();
            if(utilisateur!=null){
                System.out.println("Bienvenue "+utilisateur.getNom()+", role "+utilisateur.getRole()+" !!!");

                this.session.add("isAuthetified", true);
                this.session.add("auth", utilisateur.getRole());
                this.session.add("utilisateur", utilisateur);

                redirect = "vols";
            }else {
                modelView.addObject("fail", "Utilisateur non valide");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        modelView.setUrl(redirect);
        return modelView;
    }

    @Public @Url("logout") @Get
    public ModelView logout(){
        ModelView modelView = new ModelView("views/login.jsp");
        this.session.destroy();
        return modelView;
    }
}
