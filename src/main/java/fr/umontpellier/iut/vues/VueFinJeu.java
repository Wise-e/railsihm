package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.*;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 *
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueFinJeu extends Stage {

    private List<Joueur> joueursOrdrePoints;
    private IJeu jeu;
    private Scene scene;

    private BorderPane global;
    private Label finDePartieTexte;
    private VBox finVbox;
    private HBox listeJoueurs;
    private Group fin;

    public VueFinJeu(IJeu jeu) {
        this.setWidth(1280);
        this.setHeight(720);
        this.setTitle("FIN DE PARTIE");

        this.jeu = jeu;
        this.joueursOrdrePoints = jeu.getJoueurs();

        global = new BorderPane();
        finDePartieTexte = new Label("FIN DE LA PARTIE");
        finDePartieTexte.setPrefWidth(700);
        finDePartieTexte.setAlignment(Pos.CENTER);
        finDePartieTexte.setFont(VueDuJeu.policeTresGrand);
        joueursOrdrePoints.sort(Comparator.comparingInt(Joueur::getScore));
        finDePartieTexte.setStyle("-fx-text-fill: "+ IJoueur.CouleurConversionSimple(joueursOrdrePoints.get(0).getCouleur())+";-fx-background-color: black;-fx-background-radius: 5");
        listeJoueurs = new HBox();
        listeJoueurs.setSpacing(15);
        listeJoueurs.setAlignment(Pos.CENTER);
        finVbox = new VBox(finDePartieTexte,listeJoueurs);
        finVbox.setSpacing(30);
        finVbox.setAlignment(Pos.CENTER);
        fin = new Group();


        for(int i = 0;i<joueursOrdrePoints.size();i++){
            Label joueurNom = new Label(joueursOrdrePoints.get(i).getNom());
            Label joueurScore = new Label(joueursOrdrePoints.get(i).getScore() + "");
            ImageView joueurimage = new ImageView(new Image("file:ressources/images/images/avatar-"+jeu.getJoueurs().get((int) i).getCouleur().toString().toUpperCase()+".png"));
            joueurimage.setPreserveRatio(true);
            joueurimage.setFitWidth(100);
            joueurNom.setFont(VueDuJeu.policeGrand);
            joueurScore.setFont(VueDuJeu.policeGrand);
            //elt.getCouleur();
            VBox joueur = new VBox(joueurNom,joueurimage, joueurScore);
            //joueur.setScaleX(joueursOrdrePoints.get(i).getScore()/12);
            //joueur.setScaleY(joueursOrdrePoints.get(i).getScore()/12);
            joueur.setSpacing(15);
            joueur.setAlignment(Pos.CENTER);
            joueur.setStyle("-fx-background-color: " + IJoueur.CouleurConversionSimple(joueursOrdrePoints.get(i).getCouleur()));
            listeJoueurs.getChildren().add(joueur);

        }
        Image background = new Image("file:ressources/images/shop-boardgame-big-Europe.jpg");
        BackgroundImage bgImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(1,1,true,true,false,false));
        global.setBackground(new Background(bgImage));
        fin.getChildren().add(finVbox);
        global.setCenter(fin);
        scene = new Scene(global);
        this.setScene(scene);
    }

}
