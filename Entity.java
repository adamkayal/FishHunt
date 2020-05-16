//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
*Classe abstraite protegee pour les parametres
*qui definit chaque caracteristiques pour 
*former les poissons
*/
public abstract class Entity {

    protected double largeur, hauteur;
    protected double x, y;//coord(x,y)

    protected double vx, vy;//vitesse des poissons en coord (x,y)
    protected double ay;//acceleration des poissons en y

    protected Color color;
    protected Image image;

    /**
     * Met à jour la position et la vitesse des poissons
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;
    }

    public abstract void draw(GraphicsContext context);
}