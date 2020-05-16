//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Poisson extends Entity {

    protected double time = 0;	//utile pour Crabe et Etoile

    /**
     *Constructeur Poisson
     */
    public Poisson(int level) {
    	double rand = Math.random();

    	if (rand < 0.5)
    		this.x = -100;
    	else
    		this.x = 640;

        this.y = 96 + 288*Math.random();	//entre 1/5 et 4/5 de la hauteur
        this.largeur = 80 + 40*Math.random();	//largeur aleatoire
        this.hauteur = this.largeur;
        this.vx = -100*Math.pow((double)level,1.0/3) - 200;
        //changer la vitesse des poissons aleatoirement en x 
        if (rand < 0.5)
        	this.vx *= -1;

        this.vy = -100 - 100*Math.random();	//vitesse en y aleatoire
        this.ay = 100;	//Acceleration en y

        //Couleur aleatoire assignee aux poissons
        this.color = Color.rgb((int)(255*Math.random()),
        	(int)(255*Math.random()),(int)(255*Math.random()));

		int rand2 = (int)(8*Math.random());
		this.image = new Image("images/fish/0"+rand2+".png");
		
		if (rand >= 0.5)
			this.image = ImageHelpers.flop(this.image);
		
		this.image = ImageHelpers.colorize(this.image,this.color);
    }
   
    /**
    *Getter vitesse x
    */
    public double getVx() {return this.vx;}

    /**
    *Getter x
    */
    public double getX() {
        return this.x;
    }

    /**
    *Bool pour definir si la balle intersecte un poisson 
    */
    public boolean intersects(double x, double y) {
        return !(this.x + largeur < x || x < this.x
        	|| this.y + hauteur < y || y < this.y);
    }

    /**
     * Redefinir la methode draw
     */
    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(image, x, y, largeur, hauteur);
    }
}