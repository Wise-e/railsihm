package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends Button {

    private ICouleurWagon couleurWagon;

    private boolean clique;

    public VueCarteWagon(ICouleurWagon couleurWagon, boolean clique) {
        this.couleurWagon = couleurWagon;
        Image i = new Image("file:ressources/images/images/carte-wagon-"+getCouleurWagon().toString().toUpperCase()+".png");
        ImageView imageCarteWagon = new ImageView(i);
        imageCarteWagon.setPreserveRatio(true);
        imageCarteWagon.setFitHeight(60);
        this.setGraphic(imageCarteWagon);
        this.setBackground(null);

        if(clique) { // si cliquable
            this.setCursor(Cursor.HAND);
            setOnAction(actionEvent -> {
                ((VueDuJeu) getScene().getRoot()).getJeu().uneCarteWagonAEteChoisie(couleurWagon);
            });
        }
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }

}
