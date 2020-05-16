//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import java.io.*;
import java.util.*;

public class FishHunt extends Application {

    public static final int WIDTH = 640, HEIGHT = 480;
    private int scoreMin;
    private String min;
    private ArrayList<String> scores = new ArrayList<String>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * configuer l'affichage de la fenetre du jeu
     */
    @Override
    public void start(Stage primaryStage) {

        Controleur controleur = new Controleur();

        primaryStage.setTitle("Fish Hunt"); //saisir le titre de la fenetre
        primaryStage.setResizable(false);   //taille de la fenetre ne peut pas etre redimensionne
        primaryStage.sizeToScene();

        //Accueil
        StackPane rootAccueil = new StackPane();
        Scene accueil = new Scene(rootAccueil,WIDTH,HEIGHT);
        Button nouvPartie = new Button();
        Button meilleursScores = new Button();
        nouvPartie.setText("Nouvelle partie!");
        meilleursScores.setText("Meilleurs Scores");
        nouvPartie.setTranslateY(145);
        meilleursScores.setTranslateY(180);
        Image logo = new Image("images/logo.png",359,245,false,false);
        ImageView logoView = new ImageView(logo);
        logoView.setTranslateY(-50);
        rootAccueil.getChildren().addAll(logoView,nouvPartie,meilleursScores);
        rootAccueil.setStyle("-fx-background-color: darkblue");

        //Jeu
        StackPane rootJeu = new StackPane();
        Scene jeu = new Scene(rootJeu,WIDTH,HEIGHT);
        Text gameOver = new Text();
        Text level = new Text();
        Text score = new Text();
        gameOver.setFont(new Font(60));
        gameOver.setFill(Color.RED);
        level.setFont(new Font(60));
        level.setFill(Color.WHITE);
        score.setFont(new Font(32));
        score.setFill(Color.WHITE);
        Image vies = new Image("images/fish/00.png",40,40,false,false);
        ImageView viesView1 = new ImageView(vies);
        ImageView viesView2 = new ImageView(vies);
        ImageView viesView3 = new ImageView(vies);
        viesView1.setTranslateX(-50);
        viesView1.setTranslateY(-150);
        viesView2.setTranslateY(-150);
        viesView3.setTranslateX(50);
        viesView3.setTranslateY(-150);
        StackPane.setAlignment(gameOver,Pos.CENTER);
        StackPane.setAlignment(level,Pos.CENTER);
        StackPane.setAlignment(score,Pos.TOP_CENTER);
        score.setTranslateY(10);
        Canvas canvas = new Canvas(WIDTH,HEIGHT);
        rootJeu.getChildren().addAll(canvas,gameOver,level,score,viesView1,viesView2,viesView3);

        //Tableau de scores
        StackPane rootScores = new StackPane();
        Scene tableauScores = new Scene(rootScores,WIDTH,HEIGHT);
        ListView<String> table = new ListView<String>();
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(50,30,85,30));
        vBox.getChildren().add(table);
		Text titreScore = new Text("Meilleurs scores");
		Text nom = new Text("Votre nom : ");
        Text points = new Text();
        titreScore.setFont(Font.font("Verdana",30));
        titreScore.setY(150);
        titreScore.setX(150);
        nom.setTranslateY(180);
        nom.setTranslateX(-200);
        nom.setFont(Font.font(13));
        points.setTranslateY(180);
        points.setTranslateX(100);
        points.setFont(Font.font(13));
        StackPane.setAlignment(titreScore,Pos.TOP_CENTER);
        TextField tf = new TextField();
        tf.setTranslateY(180);
        tf.setTranslateX(-50);
        tf.setPrefWidth(180);
        tf.setMaxWidth(180);
        Button ajouter = new Button("Ajouter!");
        ajouter.setTranslateY(180);
        ajouter.setTranslateX(200);
        Button menu = new Button();
        menu.setText("Menu");
        menu.setTranslateY(215);
        rootScores.getChildren().addAll(vBox,menu,titreScore);

        GraphicsContext context = canvas.getGraphicsContext2D();

        //copie des informations du fichier scores.txt à la liste scores
        try {
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream("scores.txt"),"UTF-8");
            BufferedReader br = new BufferedReader(fileReader);
            String ligne,subLigne;

            while((ligne = br.readLine()) != null) 
            	scores.add(ligne);

            br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//on ajoute le classement
		for (int i=0; i<scores.size(); i++)
			scores.set(i,"#"+(i+1)+" - "+scores.get(i));

		table.getItems().setAll(scores);

		//on enlève le classement
		for (int i=0; i<scores.size(); i++)
			scores.set(i,scores.get(i).substring(scores.get(i).indexOf("-")+2));

		//seuil auquel on ajoutera un score dans le top 10
		if (scores.size() > 9) {
			min = scores.get(scores.size()-1);
			scoreMin = Integer.parseInt(min.substring(min.indexOf("-")+2));
		}
		else
			scoreMin = -1;

        //Animation du timer(Score du jeu:combien de metre parcourus)
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0 || (now - lastTime) * 1e-9 > 0.4) { //si dt est trop grand
                    lastTime = now;
                    return;
                }

                double dt = (now - lastTime) * 1e-9;

                controleur.update(dt);
                controleur.draw(context);

                lastTime = now;

                score.setText(""+controleur.getScore()); //on update le score

                if (controleur.getVies() == 3) {
                	viesView3.setVisible(true);
                	viesView2.setVisible(true);
                	viesView1.setVisible(true);
                }

                else if (controleur.getVies() == 2) {
                	viesView3.setVisible(false);
                	viesView2.setVisible(true);
                	viesView1.setVisible(true);
                }

                else if (controleur.getVies() == 1) {
                	viesView3.setVisible(false);
                	viesView2.setVisible(false);
                	viesView1.setVisible(true);
                }

                else {
                	viesView1.setVisible(false);
                	viesView2.setVisible(false);
                	viesView3.setVisible(false);
                	gameOver.setText("GAME OVER");

                	if (controleur.getTime() > 3) {
                		this.stop();	//on arrête le timer quand la partie est terminée
                		gameOver.setText("");
						controleur.setVies(3);
                		viesView1.setVisible(true);
                		viesView2.setVisible(true);
                		viesView3.setVisible(true);
                		if (controleur.getScore() > scoreMin) { //si nouveau score dans le top 10
                			points.setText("a fait "+controleur.getScore()+" points!");
                			if (rootScores.getChildren().size() == 3)	//on ajoute les éléments nécessaires
                				rootScores.getChildren().addAll(nom,points,tf,ajouter);	//pour rajouter un score

                			primaryStage.setScene(tableauScores);
                		}
                		else {
                			primaryStage.setScene(accueil);
                		}
                	}
                }

                if (controleur.getAfficheLevel()) {
                	level.setText("Level "+controleur.getLevel());

                	if (controleur.getTime() > 3) {
                		controleur.setAfficheLevel(false);
                		level.setText("");
                		controleur.resetTime();
                	}
                }
            }
        };

        jeu.setOnKeyPressed((value) -> {
            if (value.getCode() == KeyCode.H)
                controleur.monterNiveau();

            if (value.getCode() == KeyCode.J)
                controleur.monterScore();

            if (value.getCode() == KeyCode.K)
                controleur.monterVies();

            if (value.getCode() == KeyCode.L)
                controleur.setVies(0);
        });

        nouvPartie.setOnAction((event) -> {
			controleur.resetScore();
        	controleur.setAfficheLevel(true);
			controleur.resetTime();
        	primaryStage.setScene(jeu);
			timer.start();
        });

        meilleursScores.setOnAction((event) -> {
        	if (rootScores.getChildren().size() == 7)	//on ne veut plus les éléments qui
				rootScores.getChildren().removeAll(nom,points,tf,ajouter);	//permettent d'ajouter un score

        	primaryStage.setScene(tableauScores);
        });

        menu.setOnAction((event) -> {
        	primaryStage.setScene(accueil);
        });

        ajouter.setOnAction((event) -> {

        	scores.add(tf.getText()+" - "+controleur.getScore()); //ajout du score
        	scores = trier(scores);	//on trie

        	//on ne garde que 10 éléments
        	if (scores.size() > 10)
        		scores.subList(10, scores.size()).clear();

        	//on ajoute les classements
        	for (int i=0; i<scores.size(); i++)
				scores.set(i,"#"+(i+1)+" - "+scores.get(i));

			table.getItems().setAll(scores);

			//on enlève les classements
			for (int i=0; i<scores.size(); i++)
				scores.set(i,scores.get(i).substring(scores.get(i).indexOf("-")+2));

			//on récrit le fichier scores.txt
        	PrintWriter out = null;
			try {
				PrintWriter writer = new PrintWriter("scores.txt", "UTF-8");
				for (String s : scores)
					writer.println(s);
				writer.close();
			} catch (IOException e) {
			    System.err.println(e);
			}

			//on recalcule le seuil pour rentrer dans le top 10
			if (scores.size() > 9) {
				min = scores.get(scores.size()-1);
				scoreMin = Integer.parseInt(min.substring(min.indexOf("-")+2));
			}

	        primaryStage.setScene(accueil);
        });

        rootJeu.setOnMouseMoved((event) -> {
        	controleur.setXYCible(event.getX()-25,event.getY()-25);  //position du curseur pour le centre de la cible
        });

        rootJeu.setOnMouseClicked((event) -> {
        	if (event.getButton() == MouseButton.PRIMARY)
        		controleur.click();
        });

        primaryStage.setScene(accueil);
        primaryStage.show();
    }

    /**
     * trier la liste scores selon le pointage sur chaque ligne
     */
    public ArrayList<String> trier(ArrayList<String> scores) {

		for (int i = 0; i < scores.size()-1; i++) {
			for (int j = 0; j < scores.size()-i-1; j++) {

				int scoreJ = Integer.parseInt(scores.get(j).substring(scores.get(j).indexOf("-")+2));
				int scoreJp1 = Integer.parseInt(scores.get(j+1).substring(scores.get(j+1).indexOf("-")+2));

				if (scoreJ > scoreJp1) {
					String temp = scores.get(j);
					scores.set(j,scores.get(j+1));
					scores.set(j+1,temp);
				}
			}
		}
		Collections.reverse(scores);
		return scores;
    }
}