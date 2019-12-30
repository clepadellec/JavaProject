import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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


	
	public Scene construitScene() {

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
		ChoiceBox<String> choiceBox_heure = new ChoiceBox<>();
		choiceBox_heure.getItems().addAll("France","USA","Allemagne");
		choiceBox_heure.setValue("France");
		
	
		ChoiceBox<String> choiceBox_mois = new ChoiceBox<>();
		choiceBox_mois.getItems().addAll("France","USA","Allemagne");
		choiceBox_mois.setValue("France");
		
		ChoiceBox<String> choiceBox_semaine = new ChoiceBox<>();
		choiceBox_semaine.getItems().addAll("France","USA","Allemagne");
		choiceBox_semaine.setValue("France");
		
		ChoiceBox<String> choiceBox_jour = new ChoiceBox<>();
		choiceBox_jour.getItems().addAll("France","USA","Allemagne");
		choiceBox_jour.setValue("France");
		
		
		hbox_filtre_graph.getChildren().add(choiceBox_mois);
		
		hbox_filtre_users.getChildren().add(choiceBox_heure);
		
		
		GridPane.setConstraints(hbox_filtre_users, 0, 0);
		GridPane.setConstraints(userTwitter, 0, 1);
		grid_contenue.getChildren().addAll(userTwitter,hbox_filtre_users);
		
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Utilisateur");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Nombre de Rt");

		BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
		
		choiceBox_mois.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				
				hbox_filtre_graph.getChildren().add(choiceBox_semaine);

				graph(barChart,choiceBox_semaine);
		    }
		});
		
		choiceBox_semaine.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				
				hbox_filtre_graph.getChildren().add(choiceBox_jour);
				graph(barChart,choiceBox_jour);
		    }
		});
		
		choiceBox_jour.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				//a chaque fois modifier les modalité de filtres dynamiquement
				graph(barChart,choiceBox_jour);
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

	
	public void graph(BarChart<String, Number> barChart, ChoiceBox<String> choiceBox_mois) {

		String choix1 = choiceBox_mois.getValue();
		System.out.println(choix1);
		
		switch (choix1) {
		
		case "Allemagne":
			
			XYChart.Series<String, Number> tweet_all = new XYChart.Series<String, Number>();
			tweet_all.setName("Allemagne");
			barChart.getData().clear();
			tweet_all.getData().add(new XYChart.Data<String, Number>("Clement", 15));
			tweet_all.getData().add(new XYChart.Data<String, Number>("Charlie", 13));
			tweet_all.getData().add(new XYChart.Data<String, Number>("Adrien", 8));
			tweet_all.getData().add(new XYChart.Data<String, Number>("Laura", 11));
			barChart.getData().add(tweet_all);
			break;

		case "France":
			XYChart.Series<String, Number> tweet_fra = new XYChart.Series<String, Number>();
			tweet_fra.setName("France");
			barChart.getData().clear();
			tweet_fra.getData().add(new XYChart.Data<String, Number>("Clement", 8));
			tweet_fra.getData().add(new XYChart.Data<String, Number>("Charlie", 19));
			tweet_fra.getData().add(new XYChart.Data<String, Number>("Adrien", 16));
			tweet_fra.getData().add(new XYChart.Data<String, Number>("Laura", 2));
			barChart.getData().add(tweet_fra);
			break;
		case "USA":
			XYChart.Series<String, Number> tweet_usa = new XYChart.Series<String, Number>();
			tweet_usa.setName("USA");
			barChart.getData().clear();
			tweet_usa.getData().add(new XYChart.Data<String, Number>("Clement", 20));
			tweet_usa.getData().add(new XYChart.Data<String, Number>("Charlie", 3));
			tweet_usa.getData().add(new XYChart.Data<String, Number>("Adrien", 18));
			tweet_usa.getData().add(new XYChart.Data<String, Number>("Laura", 10));
			barChart.getData().add(tweet_usa);
			break;

		}
		barChart.setTitle("Some Programming Languages");
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
		/*CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Utilisateur");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Nombre de Rt");

		BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
*/
		Stage myStage = primaryStage;
		primaryStage.setTitle("Ma première fenêtre");
		primaryStage.setScene(construitScene());
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	public static void main(String[] args) throws Exception
	{
		/*System.setProperty( "file.encoding", "UTF-8" );
		BaseDeTweets bdt = new BaseDeTweets();
		int i=0;
		try {
			System.out.println("Ouverture en cours, veuillez patienter quelques instants");
			bdt.ouvrir(i);
			bdt.explore(i);
		}catch(Exception ex){
			System.out.println("Importation et enregistrement de la base en cours, veuillez patienter quelques instants...");
			bdt.initialise();
			bdt.importation("Foot.txt");
			bdt.enregistrer();
			bdt.explore(i);
		}*/

		launch(args);
	}
	
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub

	}

}