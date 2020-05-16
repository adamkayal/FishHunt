//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.*;

/**
 * Parametres principaux du jeu
 */
public class Jeu {

    public static final int WIDTH = 640, HEIGHT = 480;//taille max de la fenetre
    private Bulles[] bulles;//tableau qui va genere les bulles en paquet
    private ArrayList<Poisson> poissons = new ArrayList<>();
    private Cible cible; 
    private int level, score, vies, scoreLevel;
    private double time, timeNormal = 0, timeSpecial = 0;//initialise temps a 0
    private boolean afficheLevel;//bool:affiche le niveau du jeu lorsque voulu

    /**
     *Constructeur Jeu
     *gere les parametres principaux du jeu
     */
    public Jeu() {

    	this.level = 1;//commence au niveau 1
    	this.score = 0;//le score commence a 0(nbre de poissons clickés)
    	this.vies = 3;//3 vies maximum par nouvelle partie
    	this.scoreLevel = 0;
    	this.afficheLevel = false;
        this.time = 0;

        bulles = new Bulles[15];//genere les bulles en paquet
        bulles = bullesTab();

        poissons.add(new Poisson(this.level));//nouveau poisson par niveau 

        cible = new Cible();
    }

    /**
     *Faire un paquet de bulles aleatoire pour rendre l'effet plus realistqiue
     */
    public Bulles[] bullesTab() {
        for (int i = 0; i < 3; i++) {
            double baseX = 350*Math.random();

            for (int j = 0; j < 5; j++)
                bulles[5*i + j] = new Bulles(baseX);
        }

        return bulles;
    }

    /**
    *position de la cible
    */
	void setXYCible(double x, double y) {
        cible.setXYCible(x,y);
    }

    /**
    *initialiser la cible avec le click de la souris
    */
    void click() {
        cible.click();
    }

    /*
	*Getter pour le niveau
    */
    public int getLevel() {
    	return this.level;
    }

    /*
	*Getter pour le score
    */
    public int getScore() {
    	return this.score;
    }

    /*
	*Reinitialiser le score pour nouvelle partie
    */
    public void resetScore() {
        this.score = 0;
    }
    
    /*
    *Getter pour le niveau actuel
    */
    public boolean getAfficheLevel() {
    	return this.afficheLevel;
    }

    /*
	*Setter pour le niveau
    */
    public void setAfficheLevel(boolean bool) {
        this.afficheLevel = bool;
    }

    /*
	*Getter pour le temps
    */
    public double getTime() {
        return this.time;
    }

    /*
	*Reinitialiser le temps
    */
    public void resetTime() {
        this.time = 0;
    }

    /*
	*Getter pour le nombre de vie
    */
    public int getVies() {
        return this.vies;
    }

    /*
	*Afficher le nombre de vies 
    */
    public void monterVies() {
        if (this.vies < 3)
            this.vies++;
    }

    /*
	*Setter pour les vies
    */
    public void setVies(int vies) {
        this.vies = vies;
    }

    /*
	*Afficher le score
    */
    public void monterScore() {
        this.score++;
        this.scoreLevel++;
    }

    /*
	*Monter de niveau +1
    */
    public void monterNiveau() {
        this.level++;
        this.afficheLevel = true;
    }

    /**
     *Methode qui update la partie
     */
    public void update(double dt) {

    	timeNormal += dt;
    	timeSpecial += dt;
    	//Si temps normal alors poissons normal
    	if (timeNormal > 3 ) {
    		poissons.add(new Poisson(this.level));
    		timeNormal = 0;
    	}
    	//si temps Special alors afficher crabe et etoile
    	if (timeSpecial > 5 && this.level > 1) {
    		double rand = Math.random();

    		if (rand < 0.5)
    			poissons.add(new Crabe(this.level));
    		else
    			poissons.add(new Etoile(this.level));

    		timeSpecial = 0;
    	}

    	//A chaque 5 poissons touches, on monte le niveau de +1
    	if (this.scoreLevel == 5) {
    		this.level++;
    		this.scoreLevel = 0;
    		this.afficheLevel = true;
    	}
    	//Enlever une vie si poisson n'est pas touche avant de sortir de l'ecran
        if (this.vies > 0 && poissons.size() != 0 && (poissons.get(0).getX() < 
        	-100 || poissons.get(0).getX() > WIDTH)) {
        	poissons.remove(0);
        	this.vies--;
        }
        //initialise les parametres lorsque le jeu est termine
        if (this.vies == 0) {
            this.level = 1;
            this.scoreLevel = 0;
            this.time += dt;
            timeNormal = 0;
            timeSpecial = 0;
            poissons.clear();
        }

        if (bulles[0].getY() < -300)
            bulles = bullesTab();

        for (Bulles b : bulles)
            b.update(dt);

		for (int i=0; i<poissons.size(); i++) {
			poissons.get(i).update(dt);

			//lorsque la balle touche le poisson
        	if (cible.getRayonBalle() < 0 && poissons.get(i).intersects
        		(cible.getXCentre(),cible.getYCentre())) {
        		this.score++;
        		this.scoreLevel++;
        		poissons.remove(i);
        	}
		}

        cible.update(dt);

        if (this.afficheLevel) {
        	timeNormal = 2.5;  //pour que les poissons apparaissent
        	timeSpecial = 2.5; //plus rapidement au prochain niveau
            this.time += dt;
            poissons.clear();//effacer l'interface du jeu pour changer de niveau
        }
    }

    /**
     *Methode draw qui servira a dessiner les elements
     */
    public void draw(GraphicsContext context) {
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        for (Bulles b : bulles)
            b.draw(context);

        for (Poisson p : poissons)
        	p.draw(context);

        cible.draw(context);
    }
}
