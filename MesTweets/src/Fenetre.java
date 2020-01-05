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

	public BaseDeNombreTweet bar;
	static BaseDeTweets bdt = new BaseDeTweets();

	public static void main(String[] args) throws Exception
	{
		System.setProperty( "file.encoding", "UTF-8" );
		/*
		 * truc a faire :
		 * Variable semaine
		 * Gerer les types month et day of week pour eviter de mettre des String 
		 * Gerer l'affichage des filtre avec les choiceBox de merde
		 * Reli les données a l'affichage de tableau
		 * Améliorer l'interface parce que c'ets moche
		 * Trouver des idées pour les hashtag
		 * Ajouter des petits truc sympa sur l'affichage des graphiques
		 * Tester sur les deux jeux de données
		 * 
		 */



		/*
		int i=0;
		try {
			System.out.println("Ouverture en cours, veuillez patienter quelques instants");
			bdt.ouvrir();
			bdt.explore(i);
			System.out.println("finis !");
		}catch(Exception ex){*/
		System.out.println("Importation de la base en cours, veuillez patienter quelques instants...");
		bdt.initialise();
		bdt.importation("climat.txt");
		//bdt.enregistrer();
		System.out.println("finis !");
		//bdt.explore(i);
		//}

		launch(args);
	}

	public void start(Stage primaryStage)
	{

		Stage myStage = primaryStage;
		primaryStage.setTitle("Ma première fenêtre");
		primaryStage.setScene(construitScene());
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	public Scene construitScene() {

		GridPane grid = new GridPane();
		GridPane grid_contenue = new GridPane();
		MenuBar menuBar = new MenuBar();

		HBox hbox_menu = new HBox();
		VBox vbox_filtre_graph = new VBox();
		VBox vbox_filtre_users = new VBox();


		HBox hbox_filtre_graph_mois = new HBox();
		HBox hbox_filtre_graph_semaine = new HBox();
		HBox hbox_filtre_graph_jour = new HBox();


		//création du menu
		Menu menu_edition = new Menu("Edition");
		menuBar.getMenus().addAll(menu_edition);

		//On ajoute des sous menu
		MenuItem menuItem_tweet = new MenuItem("Tweet");
		MenuItem menuItem_utilisateur = new MenuItem("Utilisateur");
		MenuItem menuItem_hashtag = new MenuItem("Hashtags");
		menu_edition.getItems().addAll(menuItem_tweet, menuItem_utilisateur, menuItem_hashtag);

		hbox_menu.getChildren().add(menuBar);





		/*************** Tableau ********************/

		TableView<utilisateur> userTwitter = new TableView<>();

		TableColumn<utilisateur, String> pseudoColumn = new TableColumn<>("Utilisateur");
		pseudoColumn.setCellValueFactory(new PropertyValueFactory<>("u_pseudo_users"));
		TableColumn<utilisateur, Integer> nombreRTColumn = new TableColumn<>("Nombre de Rt");
		nombreRTColumn.setCellValueFactory(new PropertyValueFactory<>("u_nombre_retweet"));
		TableColumn<utilisateur, Integer> nombreMentionColumn = new TableColumn<>("Nombre de mention");
		nombreMentionColumn.setCellValueFactory(new PropertyValueFactory<>("u_nombre_mention"));

		userTwitter.setItems(getUsers());
		userTwitter.getColumns().addAll(pseudoColumn, nombreRTColumn, nombreMentionColumn);





		/****************** ChoiceBox ********************/
		//Rendre tout ca dynamique 
		ChoiceBox<String> choiceBox_heure = new ChoiceBox<>();
		choiceBox_heure.getItems().addAll("Aucun","0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
		//choiceBox_heure.setValue(0);

		ChoiceBox<String> choiceBox_mois = new ChoiceBox<>();
		choiceBox_mois.getItems().addAll("Aucun","06","07");
		choiceBox_mois.setValue("Aucun");

		ChoiceBox<String> choiceBox_semaine = new ChoiceBox<>();
		choiceBox_semaine.getItems().addAll("Aucun","26","27","28");

		ChoiceBox<String> choiceBox_jour = new ChoiceBox<>();
		choiceBox_jour.getItems().addAll("Aucun","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");

		Button button_supprimer_mois = new Button("Supprimer");
		Button button_supprimer_semaine = new Button("Supprimer");
		Button button_supprimer_jour = new Button("Supprimer");
		Label label_mois = new Label("Mois : ");
		Label label_semaine = new Label("Semaine : ");
		Label label_jour = new Label("Jour : ");
		hbox_filtre_graph_mois.getChildren().addAll(label_mois,choiceBox_mois);
		hbox_filtre_graph_semaine.getChildren().addAll();
		hbox_filtre_graph_jour.getChildren().addAll();

		vbox_filtre_graph.getChildren().addAll(hbox_filtre_graph_mois, hbox_filtre_graph_semaine, hbox_filtre_graph_jour);


		vbox_filtre_users.getChildren().add(choiceBox_heure);	
		GridPane.setConstraints(vbox_filtre_users, 0, 0);
		GridPane.setConstraints(userTwitter, 0, 1);
		grid_contenue.getChildren().addAll(userTwitter,vbox_filtre_users);

		// barchart
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Utilisateur");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Nombre de Rt");

		BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

		choiceBox_mois.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent m) {

				//mois
				if (hbox_filtre_graph_mois.getChildren().contains(button_supprimer_mois) == false) {
					hbox_filtre_graph_mois.getChildren().add(button_supprimer_mois);
				}

				//semaine

				hbox_filtre_graph_semaine.getChildren().clear();
				hbox_filtre_graph_semaine.getChildren().addAll(label_semaine,choiceBox_semaine,button_supprimer_semaine);
				choiceBox_semaine.setValue("Aucun");

				//jour
				hbox_filtre_graph_jour.getChildren().clear();


				//ici on applique les filtres
				bdt.setF_jour("");
				bdt.setF_semaine("");
				bdt.setF_mois(choiceBox_mois.getValue());
				//ici on créé l'objet barchart et on affiche le graphiques
				//graph(barChart);
			}
		});

		choiceBox_semaine.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent s) {

				hbox_filtre_graph_jour.getChildren().clear();
				hbox_filtre_graph_jour.getChildren().addAll(label_jour, choiceBox_jour, button_supprimer_jour);
				choiceBox_jour.setValue("Aucun");

				bdt.setF_jour("");
				bdt.setF_semaine(choiceBox_semaine.getValue());
				//graph(barChart);
			}
		});

		choiceBox_jour.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent j) {				
				bdt.setF_jour(choiceBox_jour.getValue());
				//graph(barChart);
			}
		});



		button_supprimer_mois.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent s_m) {

				hbox_filtre_graph_mois.getChildren().clear();
				hbox_filtre_graph_semaine.getChildren().clear();
				hbox_filtre_graph_jour.getChildren().clear();

				hbox_filtre_graph_mois.getChildren().addAll(label_mois,choiceBox_mois);
				choiceBox_mois.setValue("Aucun");

				bdt.setF_mois("");
				bdt.setF_semaine("");
				bdt.setF_jour("");
			}
		});

		button_supprimer_semaine.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent s_s) {
				hbox_filtre_graph_semaine.getChildren().clear();
				hbox_filtre_graph_jour.getChildren().clear();

				hbox_filtre_graph_semaine.getChildren().addAll(label_semaine,choiceBox_semaine);
				choiceBox_semaine.setValue("Aucun");
				//vbox_filtre_graph.getChildren().addAll(hbox_filtre_graph_mois,hbox_filtre_graph_semaine,hbox_filtre_graph_jour);

				bdt.setF_semaine("");
				bdt.setF_jour("");
			}
		});

		button_supprimer_jour.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent s_j) {

				hbox_filtre_graph_jour.getChildren().clear();
				hbox_filtre_graph_jour.getChildren().addAll(label_jour,choiceBox_jour);
				choiceBox_jour.setValue("Aucun");

				bdt.setF_jour("");
			}
		});



		/**************MENU**************/

		menuItem_tweet.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				grid_contenue.getChildren().clear();

				GridPane.setConstraints(vbox_filtre_graph, 0, 0);
				grid_contenue.getChildren().add(vbox_filtre_graph);

				GridPane.setConstraints(barChart, 0, 1);
				grid_contenue.getChildren().add(barChart);

				//graph(barChart);
			}
		});

		menuItem_utilisateur.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				grid_contenue.getChildren().clear();

				GridPane.setConstraints(vbox_filtre_users, 0, 0);
				grid_contenue.getChildren().add(vbox_filtre_users);

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
		GridPane.setConstraints(hbox_menu, 0, 0);
		GridPane.setConstraints(grid_contenue, 0, 1);
		grid.getChildren().addAll(hbox_menu, grid_contenue);

		//root prend tout les element de grid
		StackPane root = new StackPane();
		root.getChildren().add(grid);

		//la fenetre avec ses dimensions
		Scene scene = new Scene(root, 500, 300);

		return scene;
	}

	/*ca peut etre utile pour generer dynamiquement les choicebox*/
	public ChoiceBox<String> setChoiceBox(){
		ChoiceBox<String> choiceBox = new ChoiceBox<>();

		choiceBox.getItems().addAll("France","USA","Allemagne");
		choiceBox.setValue("France");

		return choiceBox;
	}


	/*Fonction pour les graphiques*/
	public void graph(BarChart<String, Number> barChart) {
		bar = bdt.creer_donnee_barchart();
		/*Objet permettant d'alimenter les données du barchart*/
		barChart.getData().clear();
		barChart.getData().add(bar.remplir_donnee());
		barChart.setTitle("Nombre de tweet par semaines");
	}


	/*Fonction pour les tableaus*/
	public ObservableList<utilisateur> getUsers(){
		ObservableList<utilisateur> topUsers = FXCollections.observableArrayList();
		utilisateur utilisateur1 = new utilisateur("chachalartiste",19,20);
		utilisateur utilisateur2 = new utilisateur("charpogo",77,5);
		utilisateur utilisateur3 = new utilisateur("mathieuVDP",15,23);
		topUsers.addAll(utilisateur1,utilisateur2,utilisateur3);
		return topUsers;
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

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub

	}

}