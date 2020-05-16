//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Crabe extends Poisson {

    boolean vitInverse = false;

    /**
     *Constructeur Crabe
     */
    public Crabe(int level) {
        super(level);
        this.image = new Image("images/crabe.png");
        this.vx = 1.3*this.vx;
        this.vy = 0;
        this.ay = 0;
    }

    /**
    *Redefinir la fonction update
    *Physique du crab(vitesse/temps)
    *Faire avancer le crabe en oscillant
    */
    @Override
    public void update(double dt) {
        
        super.update(dt);
        time += dt;

        if (!vitInverse && time > 0.5) {
            this.vx *= -1;
            vitInverse = !vitInverse;
            time = 0;
        }

        else if (vitInverse && time > 0.25) {
            this.vx *= -1;
            vitInverse = !vitInverse;
            time = 0;
        }
    }
}