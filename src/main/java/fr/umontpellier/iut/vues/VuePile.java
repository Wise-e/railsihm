package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javafx.util.Duration;
import java.util.Random;


public class VuePile extends HBox {

    private StackPane pileCache;
    private GridPane pileVisible;
    private IJeu jeu;

    private ImageView carteAnim;

    public VuePile(IJeu jeu){
        this.jeu=jeu;
        this.setSpacing(20);

        pileCache = new StackPane();
        pileCache.setCursor(Cursor.HAND);

        //110 cartes pile
        for(int i = 0; i<=jeu.cartesWagonCacheProperty().size()-1;i++){
            int r = new Random().nextInt(20);
            ImageView v = new ImageView(new Image("file:ressources/images/images/carte-wagon.png"));
            v.setRotate(r-10);
            v.setTranslateX((r/2)-5);
            pileCache.getChildren().add(v);
        }

        pileCache.setOnMouseClicked(actionEvent -> {

            if(getChildren().size()> 2){
                getChildren().remove(2);
            }
            getChildren().remove(carteAnim);
            carteAnim = new ImageView(new Image("file:ressources/images/images/carte-wagon-"+jeu.joueurCourantProperty().getValue().getCartesWagon().get(jeu.joueurCourantProperty().getValue().getCartesWagon().size()-1).toString().toUpperCase()+".png"));
            carteAnim.setPreserveRatio(true);
            carteAnim.setFitWidth(10);
            carteAnim.setStyle("-fx-background-color:  red");
            getChildren().add(carteAnim);
            carteAnim.setCursor(Cursor.HAND);
            carteAnim.setX(pileCache.getLayoutX());
            carteAnim.setY(pileCache.getLayoutY());

            TranslateTransition translateAnim = new TranslateTransition(new Duration(1000), carteAnim);
            FadeTransition fadeAnim = new FadeTransition(new Duration(250), carteAnim);
            ScaleTransition scaleAnim = new ScaleTransition(new Duration(500),carteAnim);
            ParallelTransition animation = new ParallelTransition(translateAnim,fadeAnim,scaleAnim);

            scaleAnim.setCycleCount(2);
            carteAnim.setScaleX(15);
            carteAnim.setScaleY(15);
            scaleAnim.setAutoReverse(true);
            scaleAnim.setToX(30);
            scaleAnim.setToY(30);
            carteAnim.setOpacity(0);
            carteAnim.setTranslateX(-200);
            carteAnim.setTranslateY(100);
            fadeAnim.setToValue(1);
            translateAnim.setToX(-700);
            translateAnim.setToY(100);
            animation.playFromStart();
            jeu.uneCarteWagonAEtePiochee();
        });

        pileVisible = new GridPane();

        this.getChildren().addAll(pileVisible, pileCache);
    }


    public void creerBindings(){
        ListChangeListener<CouleurWagon> listenerPiocheCache = new ListChangeListener<CouleurWagon>() {
            @Override
            public void onChanged(Change<? extends CouleurWagon> change) {
                Platform.runLater(()->{
                    change.next();
                    if(change.wasRemoved()){
                        /*getChildren().remove(carteAnim);
                        carteAnim = new ImageView(new Image("file:ressources/images/images/carte-wagon-"+jeu.joueurCourantProperty().getValue().getCartesWagon().get(jeu.joueurCourantProperty().getValue().getCartesWagon().size()-1).toString().toUpperCase()+".png"));
                        carteAnim.setPreserveRatio(true);
                        carteAnim.setFitWidth(10);
                        carteAnim.setStyle("-fx-background-color:  red");
                        getChildren().add(carteAnim);
                        carteAnim.setCursor(Cursor.HAND);
                        carteAnim.setX(pileCache.getLayoutX());
                        carteAnim.setY(pileCache.getLayoutY());

                        TranslateTransition translateAnim = new TranslateTransition(new Duration(1000), carteAnim);
                        FadeTransition fadeAnim = new FadeTransition(new Duration(250), carteAnim);
                        ScaleTransition scaleAnim = new ScaleTransition(new Duration(500),carteAnim);
                        ParallelTransition animation = new ParallelTransition(translateAnim,fadeAnim,scaleAnim);

                        scaleAnim.setCycleCount(2);
                        carteAnim.setScaleX(15);
                        carteAnim.setScaleY(15);
                        scaleAnim.setAutoReverse(true);
                        scaleAnim.setToX(30);
                        scaleAnim.setToY(30);
                        carteAnim.setOpacity(0);
                        carteAnim.setTranslateX(-200);
                        carteAnim.setTranslateY(100);
                        fadeAnim.setToValue(1);
                        translateAnim.setToX(-700);
                        translateAnim.setToY(100);
                        animation.playFromStart();*/
                        if(!jeu.cartesWagonCacheProperty().isEmpty()){
                            pileCache.getChildren().remove(pileCache.getChildren().size()-1);
                        }
                    }
                    if(change.wasAdded()){
                        for(int i = 0; i<=change.getAddedSize()-1;i++){
                            int r = new Random().nextInt(20);
                            ImageView v = new ImageView(new Image("file:ressources/images/images/carte-wagon.png"));
                            v.setRotate(r-10);
                            v.setTranslateX((r/2)-5);
                            pileCache.getChildren().add(v);
                        }
                    }
                });
            }
        };

        ListChangeListener<CouleurWagon> listenerPiocheVisible = new ListChangeListener<CouleurWagon>() {
            @Override
            public void onChanged(Change<? extends CouleurWagon> change) {
                Platform.runLater(()->{
                    change.next();
                    pileVisible.getChildren().removeAll(pileVisible.getChildren());
                    UpdateCarteVisible(jeu);
                });
            }
        };
        jeu.cartesWagonVisiblesProperty().addListener(listenerPiocheVisible);
        jeu.cartesWagonCacheProperty().addListener(listenerPiocheCache);
    }

    private void UpdateCarteVisible(IJeu jeu){
        int k = 0;
        int j = 0;
        for(CouleurWagon c : jeu.cartesWagonVisiblesProperty()) {
            VueCarteWagon b = new VueCarteWagon(c, true);
            pileVisible.add(b, k, j);
            k++;
            if (k == 3 && j == 0) {
                j = 1;
                k = 1;
            }

        }
    }
}
