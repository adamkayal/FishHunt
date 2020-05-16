//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * animation des bulles
*/
public class Bulles extends Entity {

    /**
     *Constructeur bulles
     */
    public Bulles(double baseX) {
        this.largeur = 30*Math.random() + 10;
        this.y = 1000;
        this.vy = -100*Math.random() - 350;
        this.color = Color.rgb(0, 0, 255, 0.4); //couleur bleu fonce
        this.x = baseX + 40*Math.random() - 20;
    }

    /**
     *getter pour y
     */
    public double getY() {
        return this.y;
    }

    /**
     *dt Temps écoulé depuis le dernier update() en secondes
     */
    @Override
    public void update(double dt) {
        y += dt * vy;
    }

    /**
     *Override la methode pour dessiner les bulles
     */
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(color);
        context.fillOval(x, y, largeur, largeur);
    }
}