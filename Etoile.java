//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Etoile extends Poisson {

    /**
     *Constructeur Etoile
     */
    public Etoile(int level) {
        super(level);
        this.image = new Image("images/star.png");
        this.vy = 0;
        this.ay = 0;
    }

    /**
    *Redefinir la fonction update
    *Physique de l'etoile(vitesse/temps)
    *Faire osciller l'etoile
    */
    @Override
    public void update(double dt) {
        super.update(dt);
        time += dt;
        this.vy = 100*Math.PI*Math.cos(2*Math.PI*time);
    }
}