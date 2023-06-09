package fr.umontpellier.iut.rails;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ServiceDuJeu extends Service<Void> {
    private Jeu jeu;

    public ServiceDuJeu(String[] nomJoueurs, String[] couleurJoueurs) {
        jeu = new Jeu(nomJoueurs, couleurJoueurs);
    }

    @Override
    protected Task<Void> createTask() {
        return getJeu();
    }

    public Jeu getJeu() {
        return jeu;
    }

}