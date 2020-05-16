//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Cible extends Entity {

    private double rayonBalle, xBalle, yBalle, xCurseur, yCurseur;
    private boolean clicked;

    /**
     *Constructeur Cible
     */
    public Cible() {
        this.image = new Image("images/cible.png");
        this.largeur = 50;
        this.hauteur = 50;
        this.x = 320;
        this.y = 240;
        this.xBalle = 320;
        this.yBalle = 240;
        this.rayonBalle = 50;
        this.color = Color.BLACK;
        this.clicked = false;
    }

    /**
    *Redefinir la fonction update
    *Physique de la balle(rayon/temps)
    *Donne l'effet que la balle s'eloigne de la cible
    */
    @Override
    public void update(double dt) {
        if (rayonBalle < 0) {
            clicked = false;
            this.rayonBalle = 50;
        }

        if (clicked) {
            rayonBalle -= 300*dt;
            this.xBalle += 300*dt;
            this.yBalle += 300*dt;
        }
    }

    /**
    *Getter de la position x du curseur
    */
    public double getXCentre() {
        return this.xCurseur;
    }

    /**
    *Getter de la position y du curseur
    */
    public double getYCentre() {
        return this.yCurseur;
    }

    /**
    *Setter pour x et y de la cible
    */
    public void setXYCible(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
	*Fonction qui initialise les parametres de la balle
	*au click de la souris
    */
    public void click() {
        this.clicked = true;
        this.xBalle = x - 25;
        this.yBalle = y - 25;
        this.xCurseur = x + 25;
        this.yCurseur = y + 25;
        this.rayonBalle = 50;
    }

    /**
    *Getter pour le rayon de la balle
    */
    public double getRayonBalle() {
        return this.rayonBalle;
    }

    /**
     *Override la methode pour dessiner la balle de la cible
     */
    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(image, x, y, largeur, hauteur);

        if (clicked) {
            context.setFill(color);
            context.fillOval(xBalle, yBalle, 2*rayonBalle, 2*rayonBalle);
        }
    }
}