import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;

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

import javax.swing.JOptionPane;

public class Fenetre extends Application {
	public BaseDeNombreTweet bar;
	public BaseDeUtilisateurs tab_uti;
	public BaseDeHashtag tab_hash;
	static BaseDeTweets bdt = new BaseDeTweets();
	
	
	public static void main(String[] args) throws Exception
	{
		System.setProperty( "file.encoding", "UTF-8" );
		//On initialise la base
		bdt.initialise();

		/*-----------------------------------------------------------------------------------------------------*/
		// Cette partie du code a ete inspire d'internet
		// lien de la source :  https://openclassrooms.com/fr/courses/26832-apprenez-a-programmer-en-java/24770-gerez-des-menus-et-des-boites-de-dialogue
		
		//on cree deux 
	    JOptionPane input_choix_fichier = new JOptionPane(), input_fichier_imprte = new JOptionPane();
	    String fichier = input_choix_fichier.showInputDialog(null, "Entrez le nom de fichier que vous souhaitez importer (ex : climat )", JOptionPane.QUESTION_MESSAGE);
	    // Import du fichier 
	    bdt.importation(fichier + ".txt");
	    //Fenetre qui annonce que l'importation s'est bien passe
	    input_fichier_imprte.showMessageDialog(null, "Les donnees ont ete importees avec succes");
	    
	    /*-------------------------------------------------------------------------------------------------------*/
	    
	    
	    //on appelle la fonction "start" qui lance l'interface
		launch(args);
	}
	
	
	public void start(Stage primaryStage)
	{
		Stage myStage = primaryStage;
		primaryStage.setTitle("Projet JAVA : Charlie Camenen et Clement Le Padellec");
		primaryStage.setScene(construitScene_interface());
		primaryStage.sizeToScene();
		primaryStage.show();
		
	}
	
	public Scene construitScene_interface() {
		
		//gridPane generale dans lequelle le contenue et le menu seront integre
		GridPane grid = new GridPane();
		//gridPane du contenue de la fenetre courrante
		GridPane grid_contenue = new GridPane();
		//Hbox qui contient le menu bar afin de rendre le menu independant du contenue
		HBox hbox_menu = new HBox();
		
		//bar de menu 
		MenuBar menuBar = new MenuBar();
		//menu permettant de naviguer entre les diffrentes interfaces de l'application
		Menu menu_edition = new Menu("Edition");
		//on ajoute un menu a la barre de menu
		menuBar.getMenus().addAll(menu_edition);
		//on ajoute la bare de menu au hbox de menu
		hbox_menu.getChildren().add(menuBar);
		
		//Label qui annonces les liste deroulantes de filtres
		Label label_filtre_graph = new Label("Filtres :  ");
		Label label_filtre_users = new Label("Filtres :  ");
		//On ajoute des sous menu
		MenuItem menuItem_tweet = new MenuItem("Tweet");
		MenuItem menuItem_utilisateur = new MenuItem("Utilisateur");
		MenuItem menuItem_hashtag = new MenuItem("Hashtags");
		menu_edition.getItems().addAll(menuItem_tweet, menuItem_utilisateur, menuItem_hashtag);
		
		// On cree des boites qui vont contenir les differents criteres de filtres
		HBox Hbox_filtre_graph = new HBox();
		HBox Hbox_filtre_users = new HBox();
		
		// Instanciation et creation dynamique des listes deroulante grace a la fonction setChoiceBox() 
		ChoiceBox<String> choiceBox_mois = bdt.setChoiceBox("mois");
		ChoiceBox<String> choiceBox_semaine =bdt.setChoiceBox("semaine");
		ChoiceBox<String> choiceBox_jour = bdt.setChoiceBox("jour");
		//Ajout du filtre mois aux filtre du graphiques
		Hbox_filtre_graph.getChildren().clear();
		Hbox_filtre_graph.getChildren().addAll(label_filtre_graph, choiceBox_mois);

		// Creation de la listes deroulante de filtre sur les utilisateur 
		ChoiceBox<String> choiceBox_utilisateur = new ChoiceBox<>();
		choiceBox_utilisateur.getItems().addAll("Nombre de tweet","Nombre de mentions","Nombre de retweet");
		choiceBox_utilisateur.setValue("Nombre de tweet");
		Hbox_filtre_users.getChildren().clear();
		//Ajout du filtre aux filtre des tableau d'utilisateur
		Hbox_filtre_users.getChildren().addAll(label_filtre_users,choiceBox_utilisateur);
		
		// Creation des axes d'abscies et d'ordone du barchart
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Temps");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Nombre de tweet");

		//Instanciation et creation du Barchart 
		BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
		//Appel a la fonction de l'affichage du barchart 
		this.print_barchart(barChart);
		
		//Instanciation et creation du tableau des top utilisateurs
		TableView<utilisateur> tableview_userTwitter = new TableView<>();
		// appel a la fonction de l'affichage du tableau d'utilisateur
		this.tableau_utilisateur(tableview_userTwitter,choiceBox_utilisateur.getValue());
		
		// Instanciation et creation du tableau des top hashtag
		TableView<hashtag> tableview_hashtag = new TableView<>();
		// appel a la fonction de l'affichage du tableau de hashtag
		tableau_hashtag(tableview_hashtag);
		
		
		
		/*************************** Barchart ********************************/
		
		
		//Action effectue lors de la selection d'un filtre sur le choiceBox_mois
		choiceBox_mois.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent m) {
				//si le filtre des semaine est affiche
				if (Hbox_filtre_graph.getChildren().contains(choiceBox_semaine)) {
					//alors on le reinitialise a "Aucun" car on a modifi les filtres de mois
					choiceBox_semaine.setValue("Aucun");
					Hbox_filtre_graph.getChildren().remove(choiceBox_jour);
				} else {
					// Sinon on ajoute le filtre mois avec la valeur par defaut "Aucun"
					Hbox_filtre_graph.getChildren().add(choiceBox_semaine);
					choiceBox_semaine.setValue("Aucun");
				}
				
				//On met a jour les filtres de le base de tweet
				bdt.setF_jour("Aucun");
				bdt.setF_semaine("Aucun");
				bdt.setF_mois(choiceBox_mois.getValue());
				//on cree l'objet barchart et on affiche le graphiques
				print_barchart(barChart);
			}
		});
		
		//Action effectue lors de la selection d'un filtre sur le choiceBox_semaine
		choiceBox_semaine.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent s) {
				//si le filtre des jour est affiche
				if (Hbox_filtre_graph.getChildren().contains(choiceBox_jour)) {
					//alors on le reinitialise a "Aucun" car on a modifi les filtres de semaine
					choiceBox_jour.setValue("Aucun");
				} else {
					// Sinon on ajoute le filtre jour avec la valeur par defaut "Aucun"
					Hbox_filtre_graph.getChildren().add(choiceBox_jour);
					choiceBox_jour.setValue("Aucun");
				}
				//On met a jour les filtres de le base de tweet
				bdt.setF_jour("Aucun");
				bdt.setF_semaine(choiceBox_semaine.getValue());
				//on cree l'objet barchart et on affiche le graphiques
				print_barchart(barChart);
			}
		});

		//Action effectue lors de la selection d'un filtre sur le choiceBox_jour
		choiceBox_jour.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent j) {
				//On met a jour les filtres de le base de tweet
				bdt.setF_jour(choiceBox_jour.getValue());
				//on cree l'objet barchart et on affiche le graphiques
				print_barchart(barChart);
			}
		});
			
		
		/*************************** Tableau ********************************/
		
		//Action effectue lors de la selection d'un filtre sur le choiceBox_utilisateur
		choiceBox_utilisateur.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent j) {
				//On vide dans un premier temps le contenue de la fenetre
				grid_contenue.getChildren().clear();
				//On cree le tableau en fonction du nouveau tri applique
				tableau_utilisateur(tableview_userTwitter, choiceBox_utilisateur.getValue());
				//On ajoute le nouveau tableau et liste deroulante dans le contenu de la fenetre
				GridPane.setConstraints(Hbox_filtre_users, 0, 0);
				grid_contenue.getChildren().add(Hbox_filtre_users);
				GridPane.setConstraints(tableview_userTwitter, 0, 1);
				grid_contenue.getChildren().add(tableview_userTwitter);
			}
		});

	
		/****************************** MENU *******************************/

		//Action effectue lors de la selection du menu tweet
		menuItem_tweet.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				//On vide le contenu
				grid_contenue.getChildren().clear();
				//On ajoute les filtres
				GridPane.setConstraints(Hbox_filtre_graph, 0, 0);
				grid_contenue.getChildren().add(Hbox_filtre_graph);
				// on ajoute le barchart
				GridPane.setConstraints(barChart, 0, 1);
				grid_contenue.getChildren().add(barChart);

			}
		});
		
		//Action effectue lors de la selection du menu utilisateur
		menuItem_utilisateur.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				//On vide le contenu
				grid_contenue.getChildren().clear();
				//On ajoute les filtres
				GridPane.setConstraints(Hbox_filtre_users, 0, 0);
				grid_contenue.getChildren().add(Hbox_filtre_users);
				//on ajoute le tebleau
				GridPane.setConstraints(tableview_userTwitter, 0, 1);
				grid_contenue.getChildren().add(tableview_userTwitter);
			}
		});
		
		//Action effectue lors de la selection du menu hashtag
		menuItem_hashtag.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent me) {
				//On vide le contenu
				grid_contenue.getChildren().clear();
				//on ajoute le tebleau
				GridPane.setConstraints(tableview_hashtag, 0, 0);
				grid_contenue.getChildren().add(tableview_hashtag);
			}
		});

		
		/********************* Affichage de l'interface *********************/
		
		//Creation du contenue de l'interface par defaut lors du lancement du programme
		//Lors de l'execution du programme l'utilisateur est donc dirige directement vers la page de graphiques
		grid_contenue.getChildren().clear();
		GridPane.setConstraints(Hbox_filtre_graph, 0, 0);
		GridPane.setConstraints(barChart, 0, 1);
		grid_contenue.getChildren().addAll(Hbox_filtre_graph,barChart);


		//On ajoute au gridpane le menu et le contenue
		GridPane.setConstraints(hbox_menu, 0, 0);
		GridPane.setConstraints(grid_contenue, 0, 1);
		grid.getChildren().addAll(hbox_menu, grid_contenue);
		//root prend tout les element de grid
		StackPane root = new StackPane();
		root.getChildren().add(grid);
		//la fenetre avec ses dimensions
		Scene scene = new Scene(root, 600, 300);

		return scene;
	}

	
	/*Fonction permettant la creation des graphiques*/
	/*barchart : diagramme en bar a afficher sur l'interface*/
	public void print_barchart(BarChart<String, Number> barChart) {
		//objet de type baseDeNombreTweet contenant toutes les donnees necessaire a la creation du graphique
		bar = bdt.creer_donnee_barchart();
		barChart.getData().clear();
		//On ajoute les donnees pour la creation du barchart
		barChart.getData().add(bar.remplir_donnee());
		//On ajoute un titre au graphique, genere dynamiquement en fonction des filtres avec la fonction getTitle_chart();
		barChart.setTitle(bar.getTitle_chart());
	}


	/*Fonction permettant la creation du tableau des top Utilisateurs*/
	@SuppressWarnings("unchecked")
	public void tableau_utilisateur(TableView<utilisateur> tableview, String option_de_tri){
		//on reinitialise les donnees et les nomn de colonne du tableau
		tableview.getItems().clear();
		tableview.getColumns().clear();
		
		//Cration des noms de colonnes
		TableColumn<utilisateur, String> pseudoColumn = new TableColumn<>("Utilisateur");
		pseudoColumn.setCellValueFactory(new PropertyValueFactory<>("u_pseudo_users"));
		TableColumn<utilisateur, Integer> nombre_retweet = new TableColumn<>("Nombre d'occurence");
		nombre_retweet.setCellValueFactory(new PropertyValueFactory<>("u_nombre_tweet"));

		//Objet de type BaseDeUtilisateur contenant toutes les informations necessaire a la creation du tableau
		tab_uti = bdt.creer_donnee_tableau_utilisateurs(option_de_tri);
		//on ajoute les donnees au tableau
		tableview.setItems(tab_uti.ajouteUtilisateur_tableau());
		//ajout des noms de colonnes
		tableview.getColumns().addAll(pseudoColumn, nombre_retweet);

	}

	/*
	 * Fonction permettant la creation du tableau des top hashtag
	 */
	@SuppressWarnings("unchecked")
	public void tableau_hashtag(TableView<hashtag> tableview) {
		//on reinitialise les donnees et les nomn de colonne du tableau
		tableview.getItems().clear();
		tableview.getColumns().clear();
		
		//Cration des noms de colonnes
		TableColumn<hashtag, String> libele_hashtag = new TableColumn<>("Hashtag");
		libele_hashtag.setCellValueFactory(new PropertyValueFactory<>("h_libele"));
		TableColumn<hashtag, Integer> nombre_hashtag = new TableColumn<>("Nombre d'occurence");
		nombre_hashtag.setCellValueFactory(new PropertyValueFactory<>("h_nombre_occurence"));

		//Objet de type BaseDeHashtag contenant toutes les informations necessaire a la creation du tableau
		tab_hash = bdt.creer_donnee_tableau_hashtag(bdt.intermediaire_rempli_bdh());
		//on ajoute les donnees au tableau
		tableview.setItems(tab_hash.ajouteHashtag_tableau());
		//ajout des noms de colonnes
		tableview.getColumns().addAll(libele_hashtag, nombre_hashtag);
	}
}