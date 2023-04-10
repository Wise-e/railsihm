package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends HBox {

    private IJeu jeu;

    public VueAutresJoueurs(IJeu jeu) {
        this.jeu = jeu;
        jeu.joueurCourantProperty();
        this.setSpacing(15);

        for(IJoueur elt : jeu.getJoueurs()){
            if(elt != jeu.joueurCourantProperty().get()) {
                //this.getChildren().add(new Label(elt.getNom()));    code en cours en dessous ...
                Image i = new Image("file:ressources/images/images/avatar-"+elt.getCouleur().toString().toUpperCase()+".png");
                ImageView imageAvatar = new ImageView(i);
                imageAvatar.setPreserveRatio(true);
                imageAvatar.fitHeightProperty().set(150);
                Label nomJoueur = new Label(elt.getNom());
                Label nbWagons = new Label("Wagons : "+elt.getNbWagons());
                nbWagons.setFont(VueDuJeu.policeBase);
                nomJoueur.setFont(VueDuJeu.policeBase);

                VBox cadreProfil = new VBox(nomJoueur, imageAvatar, nbWagons);
                cadreProfil.setAlignment(Pos.CENTER);
                cadreProfil.setStyle("-fx-background-color: "+ IJoueur.CouleurConversionSimple(elt.getCouleur())+"; -fx-border-color: " + IJoueur.CouleurConversionClair(elt.getCouleur()) +"; -fx-border-width: 5;");
                this.getChildren().add(cadreProfil);
            }
        }
    }

    public void creerBindings(){
        ChangeListener<IJoueur> listenerNomJoueurCourant = (observableValue, o, t1) -> {
            Platform.runLater(() -> {
                this.getChildren().removeAll(this.getChildren());
                for(IJoueur elt : jeu.getJoueurs()){
                    if(elt != jeu.joueurCourantProperty().get()) {
                        //this.getChildren().add(new Label(elt.getNom()));    code en cours en dessous ...
                        Image i = new Image("file:ressources/images/images/avatar-"+elt.getCouleur().toString().toUpperCase()+".png");
                        ImageView imageAvatar = new ImageView(i);
                        imageAvatar.setPreserveRatio(true);
                        imageAvatar.fitHeightProperty().set(150);
                        Label nomJoueur = new Label(elt.getNom());
                        Label nbWagons = new Label("Wagons : "+elt.getNbWagons());
                        nbWagons.setFont(VueDuJeu.policeBase);
                        nomJoueur.setFont(VueDuJeu.policeBase);

                        VBox cadreProfil = new VBox(nomJoueur, imageAvatar, nbWagons);
                        cadreProfil.setAlignment(Pos.CENTER);
                        cadreProfil.setStyle("-fx-background-color: "+ IJoueur.CouleurConversionSimple(elt.getCouleur())+"; -fx-border-color: " + IJoueur.CouleurConversionClair(elt.getCouleur()) +"; -fx-border-width: 5;");
                        this.getChildren().add(cadreProfil);
                        //this.getChildren().add(new VBox(new Label(elt.getNom()), new ImageView(new Image("file:ressources/images/images/avatar-"+elt.getCouleur().toString().toUpperCase()+".png"))));
                    }
                }
            });
        };
        jeu.joueurCourantProperty().addListener(listenerNomJoueurCourant);
    }
}
