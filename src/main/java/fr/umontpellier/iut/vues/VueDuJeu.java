package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.Random;


/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends GridPane {

    private IJeu jeu;

    private VuePlateau vuePlateau;
    private VueJoueurCourant vueJoueur;
    private VuePile pile;
    private VueAutresJoueurs vueAutresJoueurs;
    private GridPane listeDestination;
    private VueDestination boutonsDestination;
    private Button passerBouton;

    private VueLog vueLog;
    private VueFinJeu vueFin;

    /** police d'écriture */
    public static Font policeBase = Font.loadFont("file:src/main/resources/fonts/IMFeENsc28P.ttf", 18);
    public static Font policeGrand = Font.loadFont("file:src/main/resources/fonts/IMFeENsc28P.ttf", 24);
    public static Font policeTresGrand = Font.loadFont("file:src/main/resources/fonts/IMFeENsc28P.ttf", 48);

    private EventHandler<ActionEvent> gestionDestination;

    public VueDuJeu(IJeu jeu) {

        /**Selection du joueur courant (temporaire pour teste)*/
        jeu.joueurCourantProperty().set(jeu.getJoueurs().get(0));

        /**Initialisation des éléments*/
        this.jeu = jeu;
        this.vuePlateau = new VuePlateau();
        this.setStyle("-fx-background-image: url('https://img.freepik.com/photos-gratuite/materiau-fond-texture-planche-bois_53876-65133.jpg?w=2000'); -fx-background-size: cover");

        listeDestination = new GridPane();
        HBox hBoxBouton = new HBox();
        vueJoueur = new VueJoueurCourant(jeu);
        passerBouton = new Button("passer");
        passerBouton.setCursor(Cursor.HAND);
        pile = new VuePile(jeu);
        vueLog = new VueLog(jeu);
        vueLog.setPrefHeight(300);

        vueAutresJoueurs = new VueAutresJoueurs(jeu);
        vueAutresJoueurs.setMaxHeight(200);

        vueAutresJoueurs.setAlignment(Pos.CENTER);

        //this.setGridLinesVisible(true);

        Label instruction = new Label(jeu.instructionProperty().getValue());
        instruction.setStyle("-fx-background-image: url('file:src/main/resources/images/fondinstruction.jpg');-fx-background-size: contain; -fx-border-width: 4 ; -fx-border-color: black;");
        instruction.setFont(policeBase);
        instruction.textProperty().bind(jeu.instructionProperty());
        instruction.setFont(policeGrand);
        GridPane.setMargin(instruction,new Insets(30));
        instruction.setTextAlignment(TextAlignment.CENTER);
        instruction.prefWidthProperty().bind(new DoubleBinding() {
            {
                super.bind(instruction.textProperty());
            }
            @Override
            protected double computeValue() {
                GridPane.setMargin(instruction,new Insets(10,0,10,Math.max(((getCellBounds(1,1).getWidth() - (instruction.getText().length()*16))/2),0)));
                return instruction.getText().length()*16;
            }
        });
        instruction.setAlignment(Pos.CENTER);

        HBox dest = new HBox();
        Label pioche = new Label("Pioche");
        pioche.setFont(policeGrand);
        pioche.setStyle("-fx-text-fill: white");
        pioche.setPadding(new Insets(5, 5, 5, 5));

        StackPane pioched = new StackPane();
        for(int i = 0; i<3 ; i++){
            ImageView destinationpioche = new ImageView(new Image("file:ressources/images/images/destination.jpg"));
            destinationpioche.setPreserveRatio(true);
            destinationpioche.setFitWidth(300);
            int r = new Random().nextInt(20);
            destinationpioche.setRotate(r-10);
            destinationpioche.setTranslateX((r/2)-5);
            pioched.getChildren().add(destinationpioche);
        }

        pioched.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()->{
                jeu.uneDestinationAEtePiochee();
            });
        });
        dest.getChildren().addAll(pioched);
        dest.setCursor(Cursor.HAND);

        passerBouton.setFont(policeGrand);
        passerBouton.setStyle("-fx-background-color: \n" +
                "linear-gradient(#692c03 0%, #a14608 50%);\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #000000;\n" +
                "-fx-border-radius: 10;\n" +
                "-fx-text-fill: white;");

        //Placer tout les éléments dans la vue
        hBoxBouton.getChildren().addAll(listeDestination, passerBouton);
        vueJoueur.getChildren().add(hBoxBouton);
        this.add(vuePlateau, 0, 0,1,4);
        this.add(vueJoueur, 0, 4,1,1);
        this.add(vueAutresJoueurs,1,0,1,1);
        this.add(instruction,1,1,1,1);
        this.add(dest, 1, 3, 1, 1);
        this.add(vueLog,1,2,1,1);
        //colonneDroite.getChildren().add(vueLog);
        //this.add(colonneDroite, 1, 0,1,1);
        GridPane.setMargin(vueLog,new Insets(0,60,0,50));
        this.add(pile, 1, 4,1,1);
        /* Insets swag = new Insets(50);
        this.setPadding(swag); */

        //Action du bouton passer
        passerBouton.setOnAction(actionEvent -> {
            jeu.passerAEteChoisi();
        });

        jeu.joueurCourantProperty().addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(()-> {
                    vueLog.joueurCourant = t1;
                });
            }
        });


/*
            VueCarteWagon carte = new VueCarteWagon(jeu.joueurCourantProperty().getValue().getCartesWagon().get(0));
            this.add(carte, 1, 1);
            TranslateTransition animation = new TranslateTransition(new Duration(500), carte);
            animation.setToX(-500);
            animation.playFromStart();
*/


        //Action des cartes destination
        gestionDestination = (event) -> {
            boutonsDestination = (VueDestination) event.getSource();
            jeu.uneDestinationAEteChoisie(boutonsDestination.getDestination().getNom());
        };

        //Action lors d'un changement dans la liste des destinations
        ListChangeListener<IDestination> listenerDestination = change -> {
            Platform.runLater( () -> {
                change.next();
                if (change.wasRemoved()) {
                    for (IDestination elt : change.getRemoved()) {
                        destinationDisparition(trouveBoutonDestination(elt));
                    }
                }
                if (change.wasAdded()) {
                    for (IDestination elt : change.getAddedSubList()) {
                        VueDestination truc = new VueDestination(elt);
                        truc.setOpacity(0);
                        listeDestination.add(truc,change.getList().indexOf(elt)/2,change.getList().indexOf(elt)%2);
                        destinationApparition(truc,listeDestination.getChildren().size()-1);
                        System.out.println(listeDestination.getChildren().size()-1);
                        truc.setOnAction(gestionDestination);
                    }
                }
            });
        };
        jeu.destinationsInitialesProperty().addListener(listenerDestination);

        //Action quand l'instruction change (pour fin du jeu)
        ChangeListener<String> listenerInstruction = (observableValue, o, t1) -> {
            Platform.runLater(() -> {
                if(observableValue.getValue().equals("Fin de la partie.")){
                    vueFin = new VueFinJeu(jeu);
                    vueFin.show();
                    this.setOpacity(0.5);
                    this.setDisable(true);
                }
                vuePlateau.setDisable(!observableValue.getValue().equals("Début du tour"));
                pile.setDisable(!(observableValue.getValue().equals("Début du tour") || observableValue.getValue().equals("Vous pouvez prendre une autre carte wagon")));
            });
        };
        jeu.instructionProperty().addListener(listenerInstruction);

        /*ChangeListener<Object> responsive = new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observableValue, Object o, Object t1) {
                Platform.runLater( () -> {
                    System.out.println("oui");
                    dest.prefWidth(Math.max(0, Math.min(Double.MAX_VALUE,(VueDuJeu.super.widthProperty().getValue()/7))));
                    dest.prefHeight(Math.max(0, Math.min(Double.MAX_VALUE,(VueDuJeu.super.heightProperty().getValue()/7))));
                    //GridPane.setMargin(vuePlateau, new Insets(0, VueDuJeu.super.widthProperty().getValue() - vuePlateau.getBoundsInLocal().getWidth() - getCellBounds(1,4).getWidth(),0,0));
                });
            }
        };
        super.widthProperty().addListener(responsive);
        super.heightProperty().addListener(responsive);
        this.boundsInParentProperty().addListener(responsive);*/
    }

    public IJeu getJeu() {
        return jeu;
    }

    private VueDestination trouveBoutonDestination(IDestination destination){
        for(Node elt : listeDestination.getChildren()){
            VueDestination ButtonElt = (VueDestination) elt;
            if(destination.equals(ButtonElt.getDestination())){
                return ButtonElt;
            }
        }
        return null;
    }

    public void creerBindings() {
        vueJoueur.creerBindings();
        vueAutresJoueurs.creerBindings();
        pile.creerBindings();
        vuePlateau.creerBindings();
    }

    // ANIMATIONS

    private void destinationApparition(VueDestination destination, int index){
            ScaleTransition test1 = new ScaleTransition(new Duration(1000),destination);
            FadeTransition test2 = new FadeTransition(new Duration(500),destination);
            ParallelTransition test = new ParallelTransition(test1,test2);
            test.setDelay(new Duration((index%4)*500));
            destination.setOpacity(0);
            destination.setScaleX(5);
            destination.setScaleY(5);
            test1.setToY(1);
            test1.setToX(1);
            test2.setToValue(1);
            test.playFromStart();
    }

    private void destinationDisparition(VueDestination destination){
        ScaleTransition test1 = new ScaleTransition(new Duration(500),destination);
        FadeTransition test2 = new FadeTransition(new Duration(500),destination);
        ParallelTransition test = new ParallelTransition(test1,test2);
        destination.setOpacity(1);
        destination.setScaleX(1);
        destination.setScaleY(1);
        test1.setToY(0.1);
        test1.setToX(0.1);
        test2.setToValue(0);
        test.playFromStart();
        test.setOnFinished((event) -> {
            listeDestination.getChildren().remove(destination);
        });
    }

    public void destinationaffichage(IDestination d){
        vuePlateau.destinationmontrer(d);
    }

    public void destinationsupprimer(){
        vuePlateau.destinationsortir();
    }

}