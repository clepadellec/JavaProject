import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javafx.scene.chart.XYChart;

public class BaseDeNombreTweet {

		//on declare un arraylist de nombre de tweets
		public ArrayList<nombreTweet> collec_nombreTweet = new ArrayList<nombreTweet>();
		//on initialise le titre du graphique
		public String title_chart = "";
		
		//on ne fait que des getters et setters pour le titre car nous n'utiliserons jamais de getters et setters pour l'arraylisy
		public String getTitle_chart() {
			return title_chart;
		}

		public void setTitle_chart(String title_chart) {
			this.title_chart = title_chart;
		}

		//methode d'ajout d'un nombre tweet
		public void ajouteNombreTweet(nombreTweet nt) {
			collec_nombreTweet.add(nt);
		}
		
		//methode qui permet de remplir les donnees du barchart
		public XYChart.Series<String, Number> remplir_donnee(){
			XYChart.Series<String, Number> donnee_Barchart = new XYChart.Series<String, Number>();
			//on boucle sur la collection pour ajouter chaque element au barchart
			for (int i=0; i<collec_nombreTweet.size(); i++){
				//on créé l'objet courrant
				nombreTweet barre = (nombreTweet)(collec_nombreTweet.get(i));
				//on ajoute ces attributs au barchart
				donnee_Barchart.getData().add(new XYChart.Data<String, Number>(barre.getModalite(), barre.getValeur()));
			}
			return donnee_Barchart;
		}

		
}
