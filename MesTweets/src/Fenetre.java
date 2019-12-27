import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/* clic droit mes news/properties/ java build path/ libraries/ access rules / edit / javafx.** puis accessible /apply */

public class Fenetre extends Application {

//


	public Scene construitScene() {

		GridPane grid = new GridPane();
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("Menu1");
		Menu menu2 = new Menu("Menu2");
		menuBar.getMenus().addAll(menu1,menu2);
		MenuItem menuItem1 = new MenuItem("MenuItem1");
		menu1.getItems().addAll(menuItem1,new MenuItem("MenuItem2"));

		Label label = new Label("Label : ");
		Label label2 = new Label("Label2 : ");

		TextField textField = new TextField();
		textField.setText("champ texte");
		TextField textField2 = new TextField();
		textField2.setText("champ texte 2");

		Button creer = new Button("Cr�er");
		creer.setText("Cr�er");
		HBox hBox = new HBox(5);
		hBox.getChildren().addAll(label, textField, creer);

		Button sauvegarder = new Button("sauvegarder");
		sauvegarder.setText("sauvegarder");
		HBox hBox2 = new HBox(10);
		hBox2.getChildren().addAll(label2, textField2, sauvegarder);

		Text text = new Text("Mon premier texte");
		Separator separator = new Separator();
		GridPane.setConstraints(menuBar, 0, 0);
		grid.getChildren().add(menuBar);
		GridPane.setConstraints(hBox, 0, 1);
		grid.getChildren().add(hBox);
		GridPane.setConstraints(hBox2, 0, 3);
		grid.getChildren().add(hBox2);



		GridPane.setConstraints(separator, 0, 2);
		grid.getChildren().add(separator);
		GridPane.setConstraints(text, 0, 4);
		grid.getChildren().add(text);
		StackPane root = new StackPane();
		root.getChildren().addAll(grid);
		Scene scene = new Scene(root, 500, 300);
		// Scene scene = new Scene(root);
		return scene;

	}
	public void start(Stage primaryStage)
	{
		Stage myStage = primaryStage;
		primaryStage.setTitle("Ma premiere fenetre");
		primaryStage.setScene(construitScene());
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	public static void main(String[] args) throws Exception
	{
		System.setProperty( "file.encoding", "UTF-8" );
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
		}

		launch(args);
	}
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub

	}

}