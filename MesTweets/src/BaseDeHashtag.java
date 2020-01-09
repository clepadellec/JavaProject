import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BaseDeHashtag {

	public ArrayList<hashtag> collec_utilisateur = new ArrayList<hashtag>();
	public void ajouteHashtag(hashtag hashtag) {
		collec_utilisateur.add(hashtag);
	}

	public ObservableList<utilisateur>  ajouteHashtag() {
		ObservableList<utilisateur> topUsers = FXCollections.observableArrayList();
		//compteur pour r�ccuperer que les 10 premiers
		int i = 0;
		Iterator  it=collec_utilisateur.iterator();
		while (it.hasNext() && i < 10) {
			utilisateur infoTweet = (utilisateur)it.next();
			topUsers.add(infoTweet);
			i +=1;
		}
		return topUsers;
	}
	

	
	
}
