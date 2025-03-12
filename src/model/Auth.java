package model;

import database.GenericRepo;
import mg.itu.prom16.annotation.checker.EMail;
import mg.itu.prom16.annotation.checker.Required;

import java.util.List;

public class Auth {
    @Required @EMail
    private String email;

    @Required
    private String password;

    public Utilisateur checkLogin()throws Exception{
        System.out.println("Email: "+this.getEmail());
        System.out.println("Password: "+this.getPassword());

        Utilisateur utilisateur = null;

        String afterWhere = " and email = '"+this.getEmail()+"' and password = '"+this.getPassword()+"'";
        List<Utilisateur> utilisateurs = GenericRepo.findCondition(Utilisateur.class, afterWhere);
        if(utilisateurs.size()>0){
            utilisateur = utilisateurs.get(0);
        }

        return utilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
