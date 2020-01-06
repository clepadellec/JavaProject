import java.util.Iterator;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BaseDeUtilisateurs {

	public TreeSet<utilisateur> collec_utilisateur = new TreeSet<utilisateur>();
	
	public void ajouteUtilisateur(utilisateur utilisateur) {
		collec_utilisateur.add(utilisateur);
	}
	
	public ObservableList<utilisateur>  ajouteUtilisateur() {
		ObservableList<utilisateur> topUsers = FXCollections.observableArrayList();
		//compteur pour réccuperer que les 10 premiers
		for (int i = 0; i < 10; i++) {
			Iterator  it=collec_utilisateur.iterator();
			while (it.hasNext()) {
				utilisateur infoTweet = (utilisateur)it.next();

				topUsers.add(infoTweet);
			}
		}
		
		return topUsers;
	}
	
}
