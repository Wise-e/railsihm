package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.RailsIHM;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 *
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {

    private ObservableList<String> nomsJoueurs = FXCollections.observableArrayList();
    private ObjectProperty<Integer> nbJoueur = new SimpleObjectProperty<>(0);
    private ObservableList<String> couleurJoueursProperty = FXCollections.observableArrayList();
    private ArrayList<String> couleurPasSelec = IJoueur.CouleurList();

    private Scene scene;
    private BorderPane global;
    private Button boutonValidation;
    private Label nbJoueurNombre;
    private Label nbJoueurTexte;
    private Button JoueurMoins;
    private Button JoueurPlus;
    private HBox nbJoueurSelection;
    private VBox nbJoueurSection;
    private VBox liste;
    private VBox joueursListe;
    private Group ChoixJoueurs;

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }
    public List<String> getCouleursJoueurs() {
        return couleurJoueursProperty;
    }

    public VueChoixJoueurs() {

        this.setWidth(1280);
        this.setHeight(720);
        this.setMinHeight(675);
        this.setMinWidth(450);
        this.setTitle("Aventurier du rail - Choix des joueurs");
        Image background = new Image("file:ressources/images/shop-boardgame-big-Europe.jpg");
        BackgroundImage bgImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(1,1,true,true,false,false));
        global = new BorderPane();
        global.setBackground(new Background(bgImage));
        nbJoueurNombre = new Label("2");
        JoueurMoins = new Button("-");
        JoueurPlus = new Button("+");
        nbJoueurSelection = new HBox(JoueurMoins,nbJoueurNombre, JoueurPlus);
        joueursListe = new VBox();
        nbJoueurTexte = new Label("Nombre de joueurs");
        nbJoueurSection = new VBox(nbJoueurTexte,nbJoueurSelection, joueursListe);
        boutonValidation = new Button("COMMENCER");
        liste = new VBox(nbJoueurSection, boutonValidation);
        ChoixJoueurs = new Group(liste);
        nbJoueurNombre.textProperty().bind(nbJoueur.asString());
        liste.setStyle("-fx-background-color: GoldenRod; -fx-background-radius: 50; -fx-border-width: 10; -fx-border-color: Gold; -fx-border-radius: 45; -fx-border-insets: 5; -fx-effect: dropshadow( three-pass-box , black , 25, 0.2 , 0 , 1 );");
        global.setCenter(ChoixJoueurs);

        boutonValidation.setStyle("-fx-background-color: \n" +
                "linear-gradient(DarkGoldenRod 0%, Sienna 50%);\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: SaddleBrown;\n" +
                "-fx-border-radius: 10;\n" +
                "-fx-text-fill: black;");

        boutonValidation.setCursor(Cursor.HAND);
        boutonValidation.setOnMousePressed( e ->
        {
            boutonValidation.setStyle("-fx-background-color: \n" +
                    "linear-gradient(Sienna 0%, DarkGoldenRod 50%);\n" +
                    "-fx-background-radius: 10;\n" +
                    "-fx-border-width: 3;\n" +
                    "-fx-border-color: SaddleBrown;\n" +
                    "-fx-border-radius: 10;\n" +
                    "-fx-text-fill: black;");
            System.out.println("lezgo");
        });

        boutonValidation.setOnMouseReleased(e ->
        {
            boutonValidation.setStyle("-fx-background-color: \n" +
                    "linear-gradient(DarkGoldenRod 0%, Sienna 50%);\n" +
                    "-fx-background-radius: 10;\n" +
                    "-fx-border-width: 3;\n" +
                    "-fx-border-color: SaddleBrown;\n" +
                    "-fx-border-radius: 10;\n" +
                    "-fx-text-fill: black;");
            System.out.println("lezgo");
        });

        JoueurMoins.setFont(VueDuJeu.policeGrand);
        JoueurPlus.setFont(VueDuJeu.policeGrand);
        JoueurPlus.setPadding(new Insets(5));
        JoueurPlus.setCursor(Cursor.HAND);
        JoueurMoins.setCursor(Cursor.HAND);
        JoueurMoins.setPadding(new Insets(5));
        JoueurPlus.setBackground(null);
        JoueurMoins.setBackground(null);
        nbJoueurNombre.setFont(VueDuJeu.policeGrand);
        nbJoueurTexte.setFont(VueDuJeu.policeGrand);
        boutonValidation.setFont(VueDuJeu.policeBase);

        liste.setSpacing(25);
        liste.setAlignment(Pos.TOP_CENTER);
        nbJoueurSection.setPrefHeight(360);
        nbJoueurSection.setAlignment(Pos.TOP_CENTER);
        nbJoueurSection.setSpacing(10);
        nbJoueurSection.setPadding(new Insets(10));
        liste.setAlignment(Pos.CENTER);
        liste.setPadding(new Insets(15));
        nbJoueurSelection.setAlignment(Pos.CENTER);
        nbJoueurSelection.setSpacing(8);
        BorderPane.setMargin(ChoixJoueurs,new Insets(0,0,0,0));

        Button test = new Button("REGLES");
        test.setFont(VueDuJeu.policeGrand);
        BorderPane.setAlignment(test,Pos.CENTER_RIGHT);
        BorderPane.setMargin(test,new Insets(0,50,50,0));
        global.setBottom(test);
        liste.setAlignment(Pos.CENTER);
        test.setCache(false);
        test.setCursor(Cursor.HAND);
        test.setOnAction((e) -> {
            RailsIHM.getHost().showDocument("https://www.jeuxavolonte.asso.fr/regles/les_aventuriers_du_rail.pdf");
        });

        //Action du bouton pour reduire le nb de joueurs
        JoueurMoins.setOnAction(actionEvent -> {
            if(nbJoueur.get() > 2){
                nbJoueur.set(nbJoueur.get()-1);
                JoueurPlus.setVisible(true);
            }
        });

        //Action du bouton pour augmenter le nb de joueurs
        JoueurPlus.setOnAction(actionEvent -> {
            if(nbJoueur.get() < 5){
                nbJoueur.set(nbJoueur.get()+1);
                JoueurMoins.setVisible(true);
            }
        });

        //Quand on change le nom d'un joueur
        ChangeListener<String> listenerNomJoueur = (observableValue, o, t1) -> {
            Platform.runLater(() -> {
                nomsJoueurs.set(nomsJoueurs.indexOf(o),t1);
            });
        };

        /** Quand on clique sur un bouton couleur */
        EventHandler<ActionEvent> changementCouleur = (event) -> {
            Platform.runLater(() -> {
                Button boutonClicker = (Button) event.getSource();
                int joueurIndex = joueursListe.getChildren().indexOf(boutonClicker.getParent());
                joueursListe.getChildren().indexOf(boutonClicker.getParent());

                couleurPasSelec = IJoueur.CouleurList();
                for (String c : couleurJoueursProperty) {
                    couleurPasSelec.set(IJoueur.CouleurList().indexOf(c), null);
                }
                int i = IJoueur.CouleurList().indexOf(couleurJoueursProperty.get((joueurIndex)));
                while (couleurPasSelec.get(i) == null && Collections.frequency(couleurPasSelec, null) != 5) {
                    if (i < couleurPasSelec.size() - 1) {
                        i++;
                    } else {
                        i = 0;
                    }
                }
                couleurJoueursProperty.set(joueurIndex, IJoueur.CouleurList().get(i));
                ((ImageView) ((HBox) joueursListe.getChildren().get(joueurIndex)).getChildren().get(0)).setImage(new Image(CouleurAvatar(IJoueur.Couleur.valueOf(couleurJoueursProperty.get(joueurIndex)))));
                boutonClicker.setStyle("-fx-background-color: " + CouleurBouton(IJoueur.Couleur.valueOf(couleurJoueursProperty.get(joueurIndex))));
            });
        };

        //Quand le nombre de joueurs change
        ChangeListener<Integer> listenerNombreJoueurs = (observableValue, o, t1) -> {
            Platform.runLater(() -> {
                int nbJoueurChanger = t1-o; //différence entre ancienne valeur et nouvelle
                if(nbJoueur.get() == 5){
                    JoueurPlus.setVisible(false);
                }
                if(nbJoueur.get() == 2){
                    JoueurMoins.setVisible(false);
                }
                if(nbJoueurChanger > 0){
                    for(int i = 0;i<nbJoueurChanger;i++){
                        TextField nomJoueurEditable = new TextField("Joueur " + (nomsJoueurs.size()+1));
                        nomJoueurEditable.textProperty().addListener(listenerNomJoueur);

                        nomJoueurEditable.setFont(VueDuJeu.policeBase);
                        Button couleurJoueur = new Button();
                        couleurJoueur.setCursor(Cursor.HAND);
                        couleurJoueur.setOnAction(changementCouleur);
                        couleurJoueur.setPrefSize(30,30);

                        couleurPasSelec = IJoueur.CouleurList();
                        for(String c : couleurJoueursProperty){
                            couleurPasSelec.set(IJoueur.CouleurList().indexOf(c),null);
                        }
                        int j = 0;
                        while(couleurPasSelec.get(j) == null){
                            if(j < couleurPasSelec.size()-1){
                                j++;
                            }else{
                                break;
                            }
                        }
                        couleurJoueursProperty.add(IJoueur.CouleurList().get(j));
                        nomsJoueurs.add("Joueur " + (nomsJoueurs.size()+1));
                        IJoueur.Couleur color = IJoueur.Couleur.valueOf(couleurJoueursProperty.get(couleurJoueursProperty.size()-1));
                        couleurJoueur.setStyle("-fx-background-color: " + CouleurBouton(color));
                        ImageView avatarJoueur = new ImageView(new Image(CouleurAvatar(color)));
                        HBox joueurHbox = new HBox(avatarJoueur,nomJoueurEditable, couleurJoueur);
                        //new Label("Joueur " + (nomsJoueurs.size()))
                        joueurHbox.setAlignment(Pos.CENTER);
                        avatarJoueur.setFitHeight(55);
                        avatarJoueur.setPreserveRatio(true);
                        joueurHbox.setSpacing(10);

                        joueursListe.getChildren().add(joueurHbox);
                    }
                }
                if(nbJoueurChanger < 0){
                    for(int i = 0;i>nbJoueurChanger;i--){
                        joueursListe.getChildren().remove(joueursListe.getChildren().size()-1);
                        nomsJoueurs.remove(nomsJoueurs.size()-1);
                        couleurJoueursProperty.remove(couleurJoueursProperty.size()-1);
                    }
                }
            });
        };
        nbJoueur.addListener(listenerNombreJoueurs);
        nbJoueur.set(2);
        scene = new Scene(global);
        this.setScene(scene);
    }

    //Convertir la couleur du joueur en beau dégradé pour les boutons
    private String CouleurBouton(IJoueur.Couleur c){
        switch (c){
            case JAUNE :
                return "linear-gradient(yellow,gold)";
            case ROUGE :
                return "linear-gradient(red,firebrick)";
            case BLEU :
                return "linear-gradient(DodgerBlue,SteelBlue)";
            case VERT :
                return "linear-gradient(SpringGreen,MediumSeaGreen)";
            case ROSE :
                return "linear-gradient(pink,PaleVioletRed)";
        }
        return "";
    }

    private String CouleurAvatar(IJoueur.Couleur c){
        return "file:src/main/avatar/avatar-" + c.name() + ".png";
    }

    public void setPretACommencerListener(EventHandler<MouseEvent> quandLesNomsDesJoueursSontDefinis) {
        boutonValidation.setOnMouseClicked(quandLesNomsDesJoueursSontDefinis);
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return nomsJoueurs.get(playerNumber);
    }

}
