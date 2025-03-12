package database;

public class Column {

    private String nomColonneClasse;
    private String nomColonneBase;
    private String typeColonne;
    
    public String getNomColonneBase() {
        return nomColonneBase;
    }

    public void setNomColonneBase(String nomColonne) {
        this.nomColonneBase = nomColonne;
    }

    public String getTypeColonne() {
        return typeColonne;
    }

    public void setTypeColonne(String typeColonne) {
        this.typeColonne = typeColonne;
    }

    public String getNomColonneClasse() {
        return nomColonneClasse;
    }
    
    public void setNomColonneClasse(String nomColonneClasse) {
        this.nomColonneClasse = nomColonneClasse;
    }
    
}
