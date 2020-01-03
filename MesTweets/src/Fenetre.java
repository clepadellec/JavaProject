import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/* clic droit mes news/properties/ java build path/ libraries/ access rules / edit / javafx.** puis accessible /apply */

public class Fenetre extends Application {
	public tweetPeriode t1 = new tweetPeriode(6,26,21,14);
	public tweetPeriode t2 = new tweetPeriode(6,26,22,16);
	public tweetPeriode t3 = new tweetPeriode(6,26,22,12);
	public tweetPeriode t4 = new tweetPeriode(7,27,2,13);
	public tweetPeriode t5 = new tweetPeriode(7,27,4,13);
	public tweetPeriode t6 = new tweetPeriode(7,28,12,13);
	public tweetPeriode t7 = new tweetPeriode(7,28,12,18);
	public tweetPeriode t8 = new tweetPeriode(7,28,13,14);
	public tweetPeriode t9 = new tweetPeriode(7,28,13,14);
	public tweetPeriode t10 = new tweetPeriode(7,28,13,13);
	public ArrayList<tweetPeriode> l = new ArrayList<tweetPeriode>();

	public nombreTweet barre1 = new nombreTweet("26",3);
	public nombreTweet barre2 = new nombreTweet("27",2);
	public nombreTweet barre3 = new nombreTweet("28",5);
	
	public BaseDeNombreTweet bar;
	
	static BaseDeTweets bdt = new BaseDeTweets();
	
	
	public Scene construitScene() {

		l.add(t1);
		l.add(t2);
		l.add(t3);
		l.add(t4);
		l.add(t5);
		l.add(t6);
		l.add(t7);
		l.add(t7);
		l.add(t9);
		l.add(t10);
		
		
	    GridPane grid = new GridPane();
		GridPane grid_contenue = new GridPane();
		MenuBar menuBar = new MenuBar();
		HBox hbox = new HBox();
		HBox hbox_filtre_graph = new HBox();
		HBox hbox_filtre_users = new HBox();
		
		//création du menu
		Menu menu_edition = new Menu("Edition");
		menuBar.getMenus().addAll(menu_edition);
		
		//On ajoute des sous menu
		MenuItem menuItem_tweet = new MenuItem("Tweet");
		MenuItem menuItem_utilisateur = new MenuItem("Utilisateur");
		MenuItem menuItem_hashtag = new MenuItem("Hashtags");
		menu_edition.getItems().addAll(menuItem_tweet, menuItem_utilisateur, menuItem_hashtag);

		hbox.getChildren().add(menuBar);
		
		
		
		
		
		/* Tableau */
		TableView<utilisateur> userTwitter = new TableView<>();
		
		TableColumn<utilisateur, String> pseudoColumn = new TableColumn<>("Utilisateur");
		pseudoColumn.setCellValueFactory(new PropertyValueFactory<>("u_pseudo_users"));
		TableColumn<utilisateur, Integer> nombreRTColumn = new TableColumn<>("Nombre de Rt");
		nombreRTColumn.setCellValueFactory(new PropertyValueFactory<>("u_nombre_retweet"));
		TableColumn<utilisateur, Integer> nombreMentionColumn = new TableColumn<>("Nombre de mention");
		nombreMentionColumn.setCellValueFactory(new PropertyValueFactory<>("u_nombre_mention"));
		
		userTwitter.setItems(getUsers());
		userTwitter.getColumns().addAll(pseudoColumn, nombreRTColumn, nombreMentionColumn);
		
		
		
		
		
		/*  ChoiceBox  */
		ChoiceBox<Integer> choiceBox_heure = new ChoiceBox<>();
		choiceBox_heure.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);
		choiceBox_heure.setValue(0);
		
		ChoiceBox<Integer> choiceBox_mois = new ChoiceBox<>();
		choiceBox_mois.getItems().addAll(6,7);
		//choiceBox_mois.setValue();
		
		ChoiceBox<Integer> choiceBox_semaine = new ChoiceBox<>();
		choiceBox_semaine.getItems().addAll(26,27,28);
		choiceBox_semaine.setValue(26);
		
		ChoiceBox<Integer> choiceBox_jour = new ChoiceBox<>();
		choiceBox_jour.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
		choiceBox_jour.setValue(1);
		
		
		hbox_filtre_users.getChildren().add(choiceBox_heure);
		

		
		
		hbox_filtre_graph.getChildren().add(choiceBox_mois);
		
				
		GridPane.setConstraints(hbox_filtre_users, 0, 0);
		GridPane.setConstraints(userTwitter, 0, 1);
		grid_contenue.getChildren().addAll(userTwitter,hbox_filtre_users);
		
		// barchart
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Utilisateur");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Nombre de Rt");

		BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
		
		choiceBox_mois.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				
				hbox_filtre_graph.getChildren().clear();
				hbox_filtre_graph.getChildren().add(choiceBox_mois);
				hbox_filtre_graph.getChildren().add(choiceBox_semaine);
				choiceBox_semaine.setValue(0);
				
				for (int i=0; i<l.size(); i++){
					tweetPeriode t = (tweetPeriode)(l.get(i));
					if(choiceBox_mois.getValue() == t.getMois() ) {
						System.out.println(t.getMois());
					}
				}
				bar = bdt.creer_donnee_barchart();
				
				graph(barChart,bar);
		    }
		});
		
		choiceBox_semaine.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				hbox_filtre_graph.getChildren().clear();
				hbox_filtre_graph.getChildren().add(choiceBox_mois);
				hbox_filtre_graph.getChildren().add(choiceBox_semaine);
				hbox_filtre_graph.getChildren().add(choiceBox_jour);
				choiceBox_jour.setValue(0);
				for (int i=0; i<l.size(); i++){
					tweetPeriode t = (tweetPeriode)(l.get(i));
					if(choiceBox_semaine.getValue() == t.getSemaine() ) {
						System.out.println(t.getSemaine());
					}
				}
				//graph(barChart,choiceBox_jour);
		    }
		});
		
		choiceBox_jour.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				for (int i=0; i<l.size(); i++){
					tweetPeriode t = (tweetPeriode)(l.get(i));
					if(choiceBox_jour.getValue() == t.getJour() ) {
						System.out.println(t.getJour());
					}
				}
				//graph(barChart,choiceBox_jour);
		    }
		});
		
		
		
		menuItem_tweet.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				grid_contenue.getChildren().clear();
				
				GridPane.setConstraints(hbox_filtre_graph, 0, 0);
				grid_contenue.getChildren().add(hbox_filtre_graph);

				GridPane.setConstraints(barChart, 0, 1);
				grid_contenue.getChildren().add(barChart);
			}
		});
		
		menuItem_utilisateur.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				grid_contenue.getChildren().clear();

				GridPane.setConstraints(hbox_filtre_users, 0, 0);
				grid_contenue.getChildren().add(hbox_filtre_users);

				GridPane.setConstraints(userTwitter, 0, 1);
				grid_contenue.getChildren().add(userTwitter);
			}
		});
		
		menuItem_hashtag.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				grid_contenue.getChildren().clear();
				GridPane.setConstraints(userTwitter, 0, 0);
				grid_contenue.getChildren().add(userTwitter);
			}
		});
		
		
		//On affiche l'entete et le contenue
		GridPane.setConstraints(hbox, 0, 0);
		GridPane.setConstraints(grid_contenue, 0, 1);
		grid.getChildren().addAll(hbox, grid_contenue);
		
		//root prend tout les element de grid
		StackPane root = new StackPane();
		root.getChildren().add(grid);
		
		//la fenetre avec ses dimensions
		Scene scene = new Scene(root, 500, 300);
		
		return scene;
	}

	public ChoiceBox<String> setChoiceBox(){
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		
		choiceBox.getItems().addAll("France","USA","Allemagne");
		choiceBox.setValue("France");
		
		return choiceBox;
	}
		
	/*public Integer compteOccurence(ArrayList<Integer> filtres, ArrayList<nombreTweet> nombreTweet){
		Integer cpt = 0;
		
		for (int i=0; i<nombreTweet.size(); i++) {
			nombreTweet tweet = (nombreTweet)(nombreTweet.get(i));
			
			for (int j=0; j<filtres.size(); j++){
				Integer filtre = (Integer)(filtres.get(j));
				
				if(filtre.contains(tweet.getMois())) {
					System.out.println(tweet.getSemaine());
					cpt+=1;
				}
			}
			
		}
		return cpt;
	}*/

	
	public void graph(BarChart<String, Number> barChart, BaseDeNombreTweet donnee) {
		
		/*Objet permettant d'alimenter les données du barchart*/
		barChart.getData().clear();
		barChart.getData().add(donnee.remplir_donnee());
		barChart.setTitle("Nombre de tweet par semaines");
	}
	

	
	
	
	public ObservableList<utilisateur> getUsers(){
		ObservableList<utilisateur> topUsers = FXCollections.observableArrayList();
		utilisateur utilisateur1 = new utilisateur("chachalartiste",19,20);
		utilisateur utilisateur2 = new utilisateur("charpogo",77,5);
		utilisateur utilisateur3 = new utilisateur("mathieuVDP",15,23);
		topUsers.addAll(utilisateur1,utilisateur2,utilisateur3);
		return topUsers;
	}
	
	
	public void start(Stage primaryStage)
	{
		
		Stage myStage = primaryStage;
		primaryStage.setTitle("Ma première fenêtre");
		primaryStage.setScene(construitScene());
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	public static void main(String[] args) throws Exception
	{
		System.setProperty( "file.encoding", "UTF-8" );
	
		int i=0;
		//try {
			System.out.println("Ouverture en cours, veuillez patienter quelques instants");
			bdt.ouvrir(i);
			//bdt.explore(i);
			//bdt.moisDate();
		//}catch(Exception ex){
		//	System.out.println("Importation et enregistrement de la base en cours, veuillez patienter quelques instants...");
			//bdt.initialise();
			//bdt.importation("Foot.txt");
			//bdt.enregistrer();
			//bdt.explore(i);
		//}

		launch(args);
	}
	
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub

	}

}