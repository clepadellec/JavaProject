import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BaseDeHashtag {
	
	//on déclare un array list de hashtags
	public ArrayList<hashtag> collec_hashtag = new ArrayList<hashtag>();
	
	//méthode d'ajout
	public void ajouteHashtag(hashtag hashtag) {
		collec_hashtag.add(hashtag);
	}
	
	
	//cette méthode parcours base de hahstags et réupère les 10 premiers
	//on aura, au préalable trié le tableau selon le souhait de l'utilisateur
	public ObservableList<hashtag>  ajouteHashtag_tableau() {
		ObservableList<hashtag> topHashtag = FXCollections.observableArrayList();
		//compteur pour réccuperer que les 10 premiers
		int i = 0;
		Iterator  it=collec_hashtag.iterator();
		while (it.hasNext() && i < 10) {
			hashtag infoHashtag = (hashtag)it.next();
			System.out.println(infoHashtag.getH_libele() + " : " + infoHashtag.getH_nombre_occurence());
			topHashtag.add(infoHashtag);
			i +=1;
		}
		return topHashtag;
	}
	
	//on tri les hashtags selon le nombre d'occurence 
	public ArrayList<hashtag> tri_hashtag_occurence(){
		Collections.sort(collec_hashtag,hashtag.triHashtag);
		return collec_hashtag;
	}
	
}
