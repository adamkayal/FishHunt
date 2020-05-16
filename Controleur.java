//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;

/**
 * Classe qui indique au programme quelle methode utilisee au bon moment
 */
public class Controleur {

    static Jeu jeu;
    /**
    *commencer nouvelle partie
    */
    public Controleur() {
        jeu = new Jeu();
    }

    /**
    *dessine les elements voulus en appelant la methode "draw"
    */
    void draw(GraphicsContext context) {
        jeu.draw(context);
    }

    /**
    *mettre a jour le temps du jeu
    */
    void update(double dt) {
        jeu.update(dt);
    }

    /**
    *Setter des paramatres x et y de la cible
    */
    void setXYCible(double x, double y) {
        jeu.setXYCible(x,y);
    }

    /**
    *Click de la sourie
    */
    void click() {
        jeu.click();
    }

    /**
    *Getter pour afficher le niveau
    */
    boolean getAfficheLevel() {
        return jeu.getAfficheLevel();
    }

    /**
    *Setteur pour le niveau
    */
    void setAfficheLevel(boolean bool) {
        jeu.setAfficheLevel(bool);
    }

    /**
    *Getter du niveau actuel
    */
    int getLevel() {
        return jeu.getLevel();
    }

    /**
    *Getter du score actuel
    */
    int getScore() {
        return jeu.getScore();
    }

    /**
    *Reinitialise le score a 0
    */
    void resetScore() {
        jeu.resetScore();
    }

    /**
    *Getter pour le temps dans le jeu
    */
    double getTime() {
        return jeu.getTime();
    }

    /**
    *Reinitialise le temps a 0
    */
    void resetTime() {
        jeu.resetTime();
    }

    /**
    *Getter pour le nombre de vies pendant le jeu
    */
    int getVies() {
        return jeu.getVies();
    }

    /**
    *Afficher les vies sur l'ecran du jeu
    */
    void monterVies() {
        jeu.monterVies();
    }

    /**
    *Setter pour les vies
    */
    void setVies(int vies) {
        jeu.setVies(vies);
    }

    /**
    *Afficher le score sur l'ecran du jeu
    */
    void monterScore() {
        jeu.monterScore();
    }

    /**
    *Afficher le niveau atteint au debut d'un niveau
    */
    void monterNiveau() {
        jeu.monterNiveau();
    }
}