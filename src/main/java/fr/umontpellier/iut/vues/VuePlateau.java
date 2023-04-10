package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.rails.Route;
import fr.umontpellier.iut.rails.Ville;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 *
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront ?à jour le plateau après la prise d'une route ou d'une ville par un joueur
 */
public class VuePlateau extends Group {

    public VuePlateau() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void choixRouteOuVille(MouseEvent m) {
        ((VueDuJeu) getScene().getRoot()).getJeu().uneVilleOuUneRouteAEteChoisie(((Node) m.getSource()).getId());
    }

    @FXML
    ImageView image;
    @FXML
    private Group villes;
    @FXML
    private Group routes;

    public void creerBindings() {

        bindRedimensionPlateau();
        for(Object v : ((VueDuJeu) getScene().getRoot()).getJeu().getVilles()){
            Ville ville = (Ville) v;
            ville.proprietaireProperty().addListener(ChangeListener -> {
                Platform.runLater(()->{
                    Circle villeplateau = getVillePlateau(((Ville) v).getNom());
                    ImageView i = new ImageView(new Image("file:ressources/images/images/gare-"+ville.getProprietaire().getCouleur().toString().toUpperCase()+".png"));

                    i.yProperty().bind(new DoubleBinding() {
                        {
                            super.bind(villeplateau.layoutYProperty());
                        }
                        @Override
                        protected double computeValue() {
                            return villeplateau.getLayoutY()-25;
                        }
                    });

                    i.xProperty().bind(new DoubleBinding() {
                        {
                            super.bind(villeplateau.layoutXProperty());
                        }
                        @Override
                        protected double computeValue() {
                            return villeplateau.getLayoutX()-25;
                        }
                    });


                     /*

                    Marche bizarrement.

                    i.fitHeightProperty().bind(new DoubleBinding() {
                        {
                            super.bind(villeplateau.radiusProperty());
                        }
                        @Override
                        protected double computeValue() {
                            System.out.println(villeplateau.getRadius());
                            return villeplateau.getRadius()*5;
                        }
                    });

                     */

                    i.fitHeightProperty().set(45);
                    i.setPreserveRatio(true);
                    this.getChildren().add(i);
                    imageApparition(i);
                });
            });
        }

        for(Object r :((VueDuJeu) getScene().getRoot()).getJeu().getRoutes()){
            Route route = (Route) r;
            route.proprietaireProperty().addListener(ChangeListener -> {
                Platform.runLater(()->{
                    Group routeplateau = getRoutePlateau(route.getNom());
                    for(Node rec : routeplateau.getChildren()){
                        ImageView i = new ImageView(new Image("file:ressources/images/images/image-wagon-"+route.getProprietaire().getCouleur().toString().toUpperCase()+".png"));
                        i.setRotate(rec.getRotate());
                        i.yProperty().bind(new DoubleBinding() {
                            {
                                super.bind(rec.layoutYProperty());
                            }
                            @Override
                            protected double computeValue() {
                                return rec.getLayoutY()-25;
                            }
                        });
                        i.xProperty().bind(new DoubleBinding() {
                            {
                                super.bind(rec.layoutXProperty());
                            }
                            @Override
                            protected double computeValue() {
                                return rec.getLayoutX()-25;
                            }
                        });
                        i.setPreserveRatio(true);
                        i.fitHeightProperty().set(50);
                        this.getChildren().add(i);
                        imageApparition(i);
                    }
                });
            }); //hit the road jack
        }
    }

    private Circle getVillePlateau(String s){
        for(Node c :((Group) this.getChildren().get(2)).getChildren()){
            if(Objects.equals(s, c.getId())){
                return (Circle) c;
            }
        }
        return null;
    }

    private Group getRoutePlateau(String s){
        for(Node r : ((Group) this.getChildren().get(1)).getChildren()){
            if(Objects.equals(s, r.getId())){
                return (Group) r;
            }
        }
        return null;
    }

    private void imageApparition(ImageView i){
        FadeTransition test2 = new FadeTransition(new Duration(500),i);
        i.setOpacity(0);
        test2.setToValue(1);
        test2.playFromStart();
    }

    private void imagedisparition(ImageView i){
        FadeTransition test2 = new FadeTransition(new Duration(500),i);
        i.setOpacity(100);
        test2.setToValue(0);
        test2.playFromStart();
    }


    public void destinationmontrer(IDestination d){
        String[] nom = d.getNom().split(" ");

        Circle ville1 = getVillePlateau(nom[0]);
        Circle ville2 = getVillePlateau(nom[2]);

        ImageView fl1 = new ImageView(new Image("file:ressources/images/fleche.png"));
        ImageView fl2 = new ImageView(new Image("file:ressources/images/fleche.png"));

        fl1.setPreserveRatio(true);
        fl2.setPreserveRatio(true);

        ColorAdjust blackout = new ColorAdjust();
        blackout.setSaturation(1.0);

        fl1.setEffect(blackout);
        fl2.setEffect(blackout);

        fl1.setLayoutX(ville1.getLayoutX()-50);
        fl1.setLayoutY(ville1.getLayoutY()-22);
        fl2.setLayoutX(ville2.getLayoutX()-50);
        fl2.setLayoutY(ville2.getLayoutY()-22);

        fl1.setFitWidth(50);
        fl2.setFitWidth(50);

        this.getChildren().add(fl1);
        this.getChildren().add(fl2);

        imageApparition(fl1);
        imageApparition(fl2);

        TranslateTransition translateTransition1 = new TranslateTransition(new Duration(1000),fl1);
        TranslateTransition translateTransition2 = new TranslateTransition(new Duration(1000),fl2);
        translateTransition1.setToX(-25);
        translateTransition2.setToX(-25);
        translateTransition2.setAutoReverse(true);
        translateTransition1.setAutoReverse(true);
        translateTransition2.setCycleCount(-1);
        translateTransition1.setCycleCount(-1);
        translateTransition1.playFromStart();
        translateTransition2.playFromStart();
    }

    public void destinationsortir(){
        imagedisparition((ImageView) this.getChildren().get(this.getChildren().size()-1));
        this.getChildren().remove(this.getChildren().size()-1);
        imagedisparition((ImageView) this.getChildren().get(this.getChildren().size()-1));
        this.getChildren().remove(this.getChildren().size()-1);
    }


    private void bindRedimensionPlateau() {
        bindRoutes();
        bindVilles();

        //Les dimensions de l'image varient avec celle de la scène
        image.fitWidthProperty().bind(new DoubleBinding() {
            {
                Platform.runLater( () -> {
                    super.bind(getScene().widthProperty(), getScene().heightProperty());
                });
            }
            @Override
            protected double computeValue() {
                return DonneesPlateau.largeurInitialePlateau*getScene().getWidth()/DonneesPlateau.largeurInitialePlateau-(((GridPane) getParent()).getCellBounds(1,4).getWidth());
            }
        });

        image.fitHeightProperty().bind(new DoubleBinding() {
            {
                super.bind(getScene().widthProperty(), getScene().heightProperty());
            }
            @Override
            protected double computeValue() {
                return DonneesPlateau.hauteurInitialePlateau*getScene().getHeight()/DonneesPlateau.hauteurInitialePlateau-210;
            }
        });
    }

    private void bindRectangle(Rectangle rect, double layoutX, double layoutY) {
        /***/
        rect.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitHeightProperty(), image.fitWidthProperty());
            }
            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth()/ DonneesPlateau.largeurInitialePlateau;
            }
        });

        rect.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitHeightProperty(), image.fitWidthProperty());
            }

            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });

        rect.widthProperty().bind(new DoubleBinding() {
            {super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesPlateau.largeurRectangle*image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });

        rect.heightProperty().bind(new DoubleBinding() {
            {super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesPlateau.hauteurRectangle*image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });

        rect.xProperty().bind(new DoubleBinding() {
            {super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesPlateau.xInitial*image.getLayoutBounds().getWidth() /DonneesPlateau.largeurInitialePlateau;
            }
        });

        rect.yProperty().bind(new DoubleBinding() {
            {super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesPlateau.yInitial*image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });

    }

    private void bindRoutes() {
        for (Node nRoute : routes.getChildren()) {
            Group gRoute = (Group) nRoute;
            int numRect = 0;
            for (Node nRect : gRoute.getChildren()) {
                Rectangle rect = (Rectangle) nRect;
                Tooltip t = new Tooltip(gRoute.getId());
                t.setShowDelay(Duration.millis(300));
                t.setHideDelay(Duration.millis(750));
                Tooltip.install(rect, t);
                bindRectangle(rect, DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutX(), DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutY());
                numRect++;
            }
        }
    }

    private void bindVilles() {
        for (Node nVille : villes.getChildren()) {
            Circle ville = (Circle) nVille;
            Tooltip t = new Tooltip(ville.getId());
            t.setShowDelay(Duration.millis(300));
            t.setHideDelay(Duration.millis(750));
            Tooltip.install(ville, t);
            bindVille(ville, DonneesPlateau.getVille(ville.getId()).getLayoutX(), DonneesPlateau.getVille(ville.getId()).getLayoutY());
        }
    }

    private void bindVille(Circle ville, double layoutX, double layoutY) {
        ville.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth()/ DonneesPlateau.largeurInitialePlateau;
            }
        });
        ville.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight()/ DonneesPlateau.hauteurInitialePlateau;
            }
        });
        ville.radiusProperty().bind(new DoubleBinding() {
            { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesPlateau.rayonInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
    }

}
