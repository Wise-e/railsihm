package fr.umontpellier.iut;

import fr.umontpellier.iut.rails.ServiceDuJeu;
import fr.umontpellier.iut.vues.VueChoixJoueurs;
import fr.umontpellier.iut.vues.VueDuJeu;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RailsIHM extends Application {

    private VueDuJeu vueDuJeu;
    private VueChoixJoueurs vueChoixJoueurs;
    private Stage primaryStage;
    private ServiceDuJeu serviceDuJeu;

    private boolean avecVueChoixJoueurs = true;

    private static HostServices hostServices;

    public static HostServices getHost() {
        return hostServices;
    }

    @Override
    public void start(Stage primaryStage) {
        hostServices = getHostServices();
        this.primaryStage = primaryStage;
        this.primaryStage.setWidth(1920);
        this.primaryStage.setHeight(1080);
        if (avecVueChoixJoueurs) {
            vueChoixJoueurs = new VueChoixJoueurs();
            vueChoixJoueurs.setPretACommencerListener(leftClickHandler);
            vueChoixJoueurs.show();
        } else {
            demarrerPartie();
        }
    }

    public void demarrerPartie() {
        List<String> nomsJoueurs;
        List<String> couleursJoueurs = vueChoixJoueurs.getCouleursJoueurs();
        if (avecVueChoixJoueurs){
            nomsJoueurs = vueChoixJoueurs.getNomsJoueurs();
        }
        else {
            nomsJoueurs = new ArrayList<>();
            nomsJoueurs.add("Guybrush");
            nomsJoueurs.add("Largo");
            nomsJoueurs.add("LeChuck");
            nomsJoueurs.add("Elaine");
        }

        serviceDuJeu = new ServiceDuJeu(nomsJoueurs.toArray(new String[0]),couleursJoueurs.toArray(new String[0]));
        vueDuJeu = new VueDuJeu(serviceDuJeu.getJeu());
        Scene scene = new Scene(vueDuJeu); // la scene doit être créée avant de mettre en place les bindings
        vueDuJeu.creerBindings();
        demarrerServiceJeu(); // le service doit être démarré après que les bindings ont été mis en place

        primaryStage.setScene(scene);
        primaryStage.setTitle("Rails");
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> {
            this.onStopGame();
            event.consume();
        });
        primaryStage.show();
    }

    private void demarrerServiceJeu() {
        if (serviceDuJeu.getState() == Worker.State.READY) {
            serviceDuJeu.start();
        }
        primaryStage.show();
    }

    private final ListChangeListener<MouseEvent> quandLesNomsJoueursSontDefinis = change -> {
        if (!vueChoixJoueurs.getNomsJoueurs().isEmpty())
            demarrerPartie();
    };

    private final EventHandler<MouseEvent> leftClickHandler = event -> {
        Platform.runLater(()->{
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                demarrerPartie();
            }
        });
    };

    public void onStopGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("On arrête de jouer ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            serviceDuJeu.getJeu().cancel();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}