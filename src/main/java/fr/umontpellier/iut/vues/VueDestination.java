package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends Button {

    private IDestination destination;

    public VueDestination(IDestination destination) {
        this.destination = destination;
        String truc = "";
        String[] truc2 = destination.getNom().toLowerCase().split(" ");
        for(int i = 0; i<3;i++){
            truc += truc2[i].toLowerCase();
        }
        this.setTooltip(new Tooltip(destination.getNom()));
        this.setCursor(Cursor.HAND);
        Image destinationPNG = new Image("file:ressources/images/missions/eu-"+truc+".png");
        ImageView destinationView = new ImageView(destinationPNG);
        destinationView.setFitHeight(100);
        destinationView.setPreserveRatio(true);
        this.setGraphic(destinationView);
        this.setBackground(null);
        this.setOnMouseEntered(mouseEvent -> {
            Platform.runLater(()->{
                ((VueDuJeu) getScene().getRoot()).destinationaffichage(destination);
            });
        });
        this.setOnMouseExited(mouseEvent -> {
            Platform.runLater(()->{
                ((VueDuJeu) getScene().getRoot()).destinationsupprimer();
            });
        });
    }

    public IDestination getDestination() {
        return destination;
    }


}
