import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BaseDeUtilisateurs {

	//on déclare un array list d'utilisateurs 
	public ArrayList<utilisateur> collec_utilisateur = new ArrayList<utilisateur>();
	
	//creation de la méthode d'ajout
	public void ajouteUtilisateur(utilisateur utilisateur) {
		collec_utilisateur.add(utilisateur);
	}
	
	
	//cette méthode parcours base de utlisateurs et réupère les 10 premiers
	//on aura, au préalable trié le tableau selon le souhait de l'utilisateur
	public ObservableList<utilisateur>  ajouteUtilisateur_tableau() {
		ObservableList<utilisateur> topUsers = FXCollections.observableArrayList();
		//compteur pour recuperer que les 10 premiers
		int i = 0;
		Iterator  it=collec_utilisateur.iterator();
		while (it.hasNext() && i < 10) {
			utilisateur infoTweet = (utilisateur)it.next();
			topUsers.add(infoTweet);
			i +=1;
		}
		return topUsers;
	}
	
	//tri de la collection selon le nombre de tweets
	public ArrayList<utilisateur> tri_utilisateur_nombreTweet(){
		Collections.sort(collec_utilisateur,utilisateur.triTweet);
		return collec_utilisateur;
	}
	
	//tri de la collection selon le nombre de mentions
	public ArrayList<utilisateur> tri_utilisateur_nombreMention(){
		Collections.sort(collec_utilisateur,utilisateur.triMention);
		return collec_utilisateur;
	}
	
	//tri de la collection selon le nombre de retweets
	public ArrayList<utilisateur> tri_utilisateur_nombreRetweet(){
		Collections.sort(collec_utilisateur,utilisateur.triRetweet);
		return collec_utilisateur;
	}
	
	
}
