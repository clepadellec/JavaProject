import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javafx.scene.chart.XYChart;

public class BaseDeNombreTweet {

		public ArrayList<nombreTweet> collec_nombreTweet = new ArrayList<nombreTweet>();
		
		public void ajouteNombreTweet(nombreTweet nt) {
			collec_nombreTweet.add(nt);
		}
		
		public XYChart.Series<String, Number> remplir_donnee(){
			XYChart.Series<String, Number> donnee_Barchart = new XYChart.Series<String, Number>();
			
			for (int i=0; i<collec_nombreTweet.size(); i++){
				nombreTweet barre = (nombreTweet)(collec_nombreTweet.get(i));
				donnee_Barchart.getData().add(new XYChart.Data<String, Number>(barre.getModalite(), barre.getValeur()));
			}
			return donnee_Barchart;
		}
}
