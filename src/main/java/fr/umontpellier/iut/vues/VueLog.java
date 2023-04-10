package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class VueLog extends ScrollPane {

    private VBox LogText;
    private IJeu jeu;
    public IJoueur joueurCourant;

    public VueLog(IJeu jeu) {
        this.jeu = jeu;
        joueurCourant = jeu.getJoueurs().get(0);
        this.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-background-image: url(file:src/main/avatar/dialogue-box_Big.png); -fx-background-size: contain; -fx-background-position: left; -fx-background-repeat: no-repeat");
        this.setPadding(new Insets(15,0,15,40));
        this.setPrefSize(400,400);
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        LogText = new VBox();
        LogText.setSpacing(5);
        LogText.toFront();
        LogText.prefWidthProperty().bind(this.widthProperty());

        this.setContent(LogText);

        ListChangeListener<String> listenerTrace = change -> {
            Platform.runLater( () -> {
                change.next();
                if (change.wasAdded()) {
                    for(String elt : change.getAddedSubList()){
                        Label message = new Label(joueurCourant.getNom() + elt);
                        message.setFont(VueDuJeu.policeBase);
                        message.setStyle("-fx-background-color: " + IJoueur.CouleurConversionSimple(joueurCourant.getCouleur()));
                        message.setTextFill(Color.BLACK);
                        LogText.getChildren().add(0,message);
                    }
                }
            });
        };
        jeu.traceProperty().addListener(listenerTrace);
    }
}
