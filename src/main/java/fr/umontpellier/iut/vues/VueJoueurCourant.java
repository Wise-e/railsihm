package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends HBox {

    private IJeu jeu;
    private Label labelJoueur;
    private Label scoreJoueur;
    private Label nbGare;
    private ImageView imageAvatar;
    private VBox avatar;
    private VBox carteJoueur;
    private HBox carteJoueurHaut;
    private HBox carteJoueurMilieu;
    private HBox carteJoueurBas;
    private Button destination;
    private HBox listedestination;
    private ScrollPane scrollDestination;
    private Label nbWagons;


    public VueJoueurCourant(IJeu jeu) {
        this.jeu = jeu;

        carteJoueurHaut = new HBox();
        carteJoueurBas = new HBox();
        carteJoueurMilieu = new HBox();
        nbGare = new Label();
        nbGare.setFont(VueDuJeu.policeBase);

        carteJoueur = new VBox(carteJoueurHaut,carteJoueurMilieu,carteJoueurBas,nbGare);
        destination = new Button();
        UpdateWagonsJoueur(jeu);
        carteJoueur.managedProperty().bind(Bindings.notEqual("Choisissez les destinations à défausser",jeu.instructionProperty()));
        carteJoueur.visibleProperty().bind(Bindings.notEqual("Choisissez les destinations à défausser",jeu.instructionProperty()));
        destination.managedProperty().bind(Bindings.notEqual("Choisissez les destinations à défausser",jeu.instructionProperty()));
        destination.visibleProperty().bind(Bindings.notEqual("Choisissez les destinations à défausser",jeu.instructionProperty()));

        /**taille générale de la vue du joueur courant (HBOX qui contient tout)*/
        this.setHeight(130);

        /**couleur de fond  nom du joueur courant et score*/
        labelJoueur = new Label(jeu.joueurCourantProperty().getValue().getNom());
        labelJoueur.setFont(VueDuJeu.policeBase);
        this.setStyle("-fx-background-color: " + IJoueur.CouleurConversionSimple(jeu.joueurCourantProperty().getValue().getCouleur()));
        scoreJoueur = new Label(jeu.joueurCourantProperty().getValue().getScore()+"");
        scoreJoueur.setFont(VueDuJeu.policeBase);
        avatar = new VBox();
        avatar.setStyle("-fx-border-width: 5"+"; -fx-border-style: inset"+"; -fx-border-color: " + IJoueur.CouleurConversionClair(jeu.joueurCourantProperty().get().getCouleur()));
        avatar.getChildren().add(labelJoueur);

        /**liste destination du joueur*/
        ImageView des = new ImageView(new Image("file:ressources/images/iconeliste.png"));
        des.setPreserveRatio(true);
        des.setFitWidth(30);
        destination.setGraphic(des);
        destination.setBackground(null);
        listedestination = new HBox();
        scrollDestination = new ScrollPane();
        scrollDestination.setContent(listedestination);
        scrollDestination.setPrefWidth(500);
        scrollDestination.setManaged(false);
        scrollDestination.setStyle(scrollDestination.getStyle() + "-fx-background: transparent; -fx-background-color: transparent;");
        scrollDestination.setStyle("-fx-background: none;");
        destination.setCursor(Cursor.HAND);
        destination.setTooltip(new Tooltip("Afficher les cartes destinations"));

        destination.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.runLater(()->{
                    if(listedestination.getChildrenUnmodifiable().isEmpty()){
                        scrollDestination.setManaged(true);
                        destination.setRotate(90);
                        for(int i = 0; i<jeu.joueurCourantProperty().get().getDestinations().size();i++){
                            VueDestination dest = new VueDestination(jeu.joueurCourantProperty().get().getDestinations().get(i));
                            dest.setBackground(null);
                            listedestination.getChildren().add(dest);
                        }
                    }
                    else{
                        listedestination.getChildren().clear();
                        destination.setRotate(0);
                        scrollDestination.setManaged(false);
                    }
                });
            }
        });

        /**image du joueur courant*/
        Image av = new Image("file:ressources/images/images/avatar-"+jeu.joueurCourantProperty().get().getCouleur().toString().toUpperCase()+".png");
        imageAvatar = new ImageView(av);
        imageAvatar.setPreserveRatio(true);
        imageAvatar.fitHeightProperty().set(this.heightProperty().getValue());
        imageAvatar.fitWidthProperty().set(this.heightProperty().getValue());
        nbWagons = new Label("Wagons : "+jeu.joueurCourantProperty().get().getNbWagons());
        nbWagons.setFont(VueDuJeu.policeBase);
        avatar.getChildren().addAll(imageAvatar,scoreJoueur, nbWagons);
        this.getChildren().addAll(avatar,carteJoueur, destination, scrollDestination);
        avatar.setAlignment(Pos.CENTER);
        labelJoueur.setStyle("-fx-text-fill: white");
        if(jeu.joueurCourantProperty().getValue().getCouleur().toString().equals("JAUNE") || jeu.joueurCourantProperty().getValue().getCouleur().toString().equals("ROSE")){
            labelJoueur.setStyle("-fx-text-fill: black");
        }
    }

    public void creerBindings() {
        ListChangeListener<CouleurWagon> listenerPioche =  new ListChangeListener<CouleurWagon>() {
            public void onChanged(Change<? extends CouleurWagon> change) {
                Platform.runLater(() -> {
                    change.next();
                    carteJoueurBas.getChildren().removeAll(carteJoueurBas.getChildren());
                    carteJoueurHaut.getChildren().removeAll(carteJoueurHaut.getChildren());
                    carteJoueurMilieu.getChildren().removeAll(carteJoueurMilieu.getChildren());
                    UpdateWagonsJoueur(jeu);
                });
            }
        };

        ChangeListener<IJoueur> listenerNomJoueurCourant = (observableValue, o, t1) -> {
            Platform.runLater(() -> {
                /**nom + couleur de fond change + score*/
                labelJoueur.setText(jeu.joueurCourantProperty().getValue().getNom());
                this.setStyle("-fx-background-color: " + IJoueur.CouleurConversionSimple(jeu.joueurCourantProperty().getValue().getCouleur()));
                scoreJoueur.setText(jeu.joueurCourantProperty().getValue().getScore()+"");
                nbWagons.setText("Wagons : "+jeu.joueurCourantProperty().get().getNbWagons());
                labelJoueur.setStyle("-fx-text-fill: white");
                scoreJoueur.setStyle("-fx-text-fill: white");
                nbWagons.setStyle("-fx-text-fill: white");
                nbGare.setText("Gares: " + jeu.joueurCourantProperty().get().getNbGares());
                avatar.setStyle("-fx-border-width: 5"+";"+"; -fx-border-color: " + IJoueur.CouleurConversionClair(jeu.joueurCourantProperty().get().getCouleur()));
                if(jeu.joueurCourantProperty().getValue().getCouleur().toString().equals("JAUNE") || jeu.joueurCourantProperty().getValue().getCouleur().toString().equals("ROSE")){
                    labelJoueur.setStyle("-fx-text-fill: black");
                    scoreJoueur.setStyle("-fx-text-fill: black");
                    nbWagons.setStyle("-fx-text-fill: black");
                }

                /**imageview du joueur courant change*/
                Image av = new Image("file:ressources/images/images/avatar-"+jeu.joueurCourantProperty().get().getCouleur().toString().toUpperCase()+".png");
                imageAvatar.setPreserveRatio(true);
                imageAvatar.setImage(av);
                carteJoueurBas.getChildren().removeAll(carteJoueurBas.getChildren());
                carteJoueurHaut.getChildren().removeAll(carteJoueurHaut.getChildren());
                carteJoueurMilieu.getChildren().removeAll(carteJoueurMilieu.getChildren());

                /**carte du joueur courant change*/
                UpdateWagonsJoueur(jeu);
                o.cartesWagonProperty().removeListener(listenerPioche);
                jeu.joueurCourantProperty().getValue().cartesWagonProperty().addListener(listenerPioche);
            });
        };
        jeu.joueurCourantProperty().addListener(listenerNomJoueurCourant);
    }

    private void UpdateWagonsJoueur(IJeu jeu) {
        for(CouleurWagon elt : Arrays.stream(CouleurWagon.values()).toList()){
            if(Collections.frequency(jeu.joueurCourantProperty().getValue().getCartesWagon(),elt) > 0){
                Arrays.stream(CouleurWagon.values()).toList().indexOf(elt);
                HBox wagonsCouleur = new HBox();
                wagonsCouleur.setSpacing(-95);

                switch(Arrays.stream(CouleurWagon.values()).toList().indexOf(elt)%3){
                    case 0: if(elt != CouleurWagon.LOCOMOTIVE){
                        carteJoueurHaut.getChildren().add(wagonsCouleur);
                    }else{
                        carteJoueurBas.getChildren().add(wagonsCouleur);
                    }
                        break;
                    case 1: carteJoueurMilieu.getChildren().add(wagonsCouleur);
                        break;
                    case 2: carteJoueurBas.getChildren().add(wagonsCouleur);
                        break;
                }

                //carteJoueur.add(wagonsCouleur,Arrays.stream(CouleurWagon.values()).toList().indexOf(elt)/2,Arrays.stream(CouleurWagon.values()).toList().indexOf(elt)%2,1,1);
                for(int i = 0; i<Collections.frequency(jeu.joueurCourantProperty().getValue().getCartesWagon(),elt); i++){
                    VueCarteWagon truc = new VueCarteWagon(elt, true);
                    truc.setStyle("-fx-view-order: "+ -i);
                    wagonsCouleur.getChildren().add(truc);
                    truc.toBack();
                }
            }
        }
    }
}
