package fr.umontpellier.iut;

import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public interface IJoueur {

    ObservableList<CouleurWagon> cartesWagonProperty();

    public static enum Couleur {
        JAUNE, ROUGE, BLEU, VERT, ROSE;
    }

    public static ArrayList<String> CouleurList(){
        ArrayList<String> truc = new ArrayList<>();
        truc.add(Couleur.JAUNE.name());
        truc.add(Couleur.ROUGE.name());
        truc.add(Couleur.BLEU.name());
        truc.add(Couleur.VERT.name());
        truc.add(Couleur.ROSE.name());
        return truc;
    }

    public static String CouleurConversionSimple(Couleur c){
        switch(c){
            case JAUNE : return "Gold";
            case ROUGE : return "IndianRed";
            case BLEU : return "SteelBlue";
            case VERT : return "OliveDrab";
            case ROSE : return "LightPink";
        }
        return "";
    }

    public static String CouleurConversionClair(Couleur c){
        switch(c){
            case JAUNE : return "Moccasin";
            case ROUGE : return "LightCoral";
            case BLEU : return "LightSteelBlue";
            case VERT : return "YellowGreen";
            case ROSE : return "Pink";
        }
        return "";
    }

    public static String CouleurConversionDegrade(Couleur c){
        switch(c){
            case JAUNE : return "yellow";
            case ROUGE : return "red";
            case BLEU : return "blue";
            case VERT : return "green";
            case ROSE : return "pink";
        }
        return "";
    }

    List<CouleurWagon> getCartesWagon();
    List<Destination> getDestinations();
    int getNbWagons();
    String getNom();
    Couleur getCouleur();
    int getNbGares();
    int getScore();
}