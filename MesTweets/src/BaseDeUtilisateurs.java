import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BaseDeUtilisateurs {

	public ArrayList<utilisateur> collec_utilisateur = new ArrayList<utilisateur>();
	
	public void ajouteUtilisateur(utilisateur utilisateur) {
		collec_utilisateur.add(utilisateur);
	}
	
	public ObservableList<utilisateur>  ajouteUtilisateur() {
		ObservableList<utilisateur> topUsers = FXCollections.observableArrayList();
		//compteur pour réccuperer que les 10 premiers
		int i = 0;
		Iterator  it=collec_utilisateur.iterator();
		while (it.hasNext() && i < 10) {
			utilisateur infoTweet = (utilisateur)it.next();
			topUsers.add(infoTweet);
			i +=1;
		}
		return topUsers;
	}
	
	public ArrayList<utilisateur> tri_utilisateur_nombreTweet(){
		Collections.sort(collec_utilisateur,utilisateur.triTweet);
		return collec_utilisateur;
	}
	
	public ArrayList<utilisateur> tri_utilisateur_nombreMention(){
		Collections.sort(collec_utilisateur,utilisateur.triMention);
		return collec_utilisateur;
	}
	
	public ArrayList<utilisateur> tri_utilisateur_nombreRetweet(){
		Collections.sort(collec_utilisateur,utilisateur.triRetweet);
		return collec_utilisateur;
	}
	
	
}
