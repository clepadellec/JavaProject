import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.io.Serializable;


public class BaseDeTweets{

	public ArrayList<tweet> maCollec;
	public String f_mois = "" ;
	public String f_semaine = "" ;
	public String f_jour = "" ;
	public String f_joursem="";

	public void initialise() {
		maCollec = new ArrayList<tweet>();
	}



	public String trouvejour(LocalDate date ) {
		Date datee = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Calendar calendarNomJourSemaine = Calendar.getInstance();
		calendarNomJourSemaine.setTime(datee);
		int semaine = calendarNomJourSemaine.get(Calendar.DAY_OF_WEEK);
		String nomjourSemaine = "";
		switch (semaine) {
		case 1:  nomjourSemaine = "Dimanche";
		break;
		case 2:  nomjourSemaine = "Lundi";
		break;
		case 3:  nomjourSemaine = "Mardi";
		break;
		case 4:  nomjourSemaine = "Mercredi";
		break;
		case 5:  nomjourSemaine = "Jeudi";
		break;
		case 6:  nomjourSemaine = "Vendredi";
		break;
		case 7:  nomjourSemaine = "Samedi";
		break;
		}

		return nomjourSemaine;
	}


	public String trouvemois(LocalDate date ) {
		Date datee = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Calendar calendarNomJourSemaine = Calendar.getInstance();
		calendarNomJourSemaine.setTime(datee);
		int semaine = calendarNomJourSemaine.get(Calendar.MONTH);
		String nomMois = "";
		switch (semaine) {
		case 0:  nomMois = "Janvier";
		break;
		case 1:  nomMois = "Février";
		break;
		case 2:  nomMois = "Mars";
		break;
		case 3:  nomMois = "Avril";
		break;
		case 4:  nomMois = "Mai";
		break;
		case 5:  nomMois = "Juin";
		break;
		case 6:  nomMois = "Juillet";
		break;
		case 7:  nomMois = "Août";
		break;
		case 8:  nomMois = "Septembre";
		break;
		case 9:  nomMois = "Octobre";
		break;
		case 10:  nomMois = "Novembre";
		break;
		case 11:  nomMois = "Décembre";
		break;
		}

		return nomMois;
	}


	public String trouvenomsemaine(LocalDate date, String joursem, int jour) {
		String nomsemaine="";
		Date datee = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Calendar calendarNomJourSemaine = Calendar.getInstance();
		calendarNomJourSemaine.setTime(datee);
		int nb_jour_mois = calendarNomJourSemaine.getActualMaximum(Calendar.DAY_OF_MONTH);
		//System.out.println(nb_jour_mois);
		int num_mois_dep = calendarNomJourSemaine.get(Calendar.MONTH);
		int num_mois_fin = calendarNomJourSemaine.get(Calendar.MONTH);
		int deb=0;
		int fin=0;
		int diff=0;
		switch (joursem) {
		case "Dimanche":
			deb= jour-6;
			fin= jour;
			break;
		case "Lundi":
			deb= jour;
			fin= jour+6;
			break;
		case "Mardi":
			deb= jour-1;
			fin= jour+5;
			break;
		case "Mercredi":
			deb= jour-2;
			fin= jour+4;
			break;
		case "Jeudi":
			deb= jour-3;
			fin= jour+3;
			break;
		case "Vendredi":
			deb= jour-4;
			fin= jour+2;
			break;
		case "Samedi":
			deb= jour-5;
			fin= jour+1;
			break;
		}

		if (fin>nb_jour_mois) {
			diff= fin-nb_jour_mois;
			fin=0+diff;
			num_mois_fin=num_mois_fin+1;
		}

		if (deb<=0) {
			num_mois_dep = calendarNomJourSemaine.get(Calendar.MONTH)-1;
			if(nb_jour_mois==31) {
				deb=30-Math.abs(deb);
			}else {
				deb=31-Math.abs(deb);
			}

		}
		num_mois_dep=num_mois_dep+1;
		num_mois_fin=num_mois_fin+1;
		nomsemaine= "Semaine du Lundi "+deb+"/"+num_mois_dep+" au Dimanche "+fin+"/"+num_mois_fin;
		return nomsemaine;
	}



	public String getF_mois() {
		return f_mois;
	}

	public void setF_mois(String f_mois) {
		this.f_mois = f_mois;
	}

	public String getF_semaine() {
		return f_semaine;
	}

	public void setF_semaine(String f_semaine) {
		this.f_semaine = f_semaine;
	}

	public String getF_jour() {
		return f_jour;
	}

	public void setF_jour(String f_jour) {
		this.f_jour = f_jour;
	}

	public void ajoute(tweet t) {
		maCollec.add(t);
	}
	public String getF_joursem() {
		return f_joursem;
	}

	public void setF_joursem(String f_joursem) {
		this.f_joursem = f_joursem;
	}

	/*ca peut etre utile pour generer dynamiquement les choicebox*/
	public ChoiceBox<String> setChoiceBox(String variable_tri){

		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		Iterator  it= maCollec.iterator();
		Object modalite = "";
		choiceBox.getItems().add("Aucun");

		switch (variable_tri) {

		case "mois":
			while (it.hasNext()) {

				tweet infoTweet = (tweet)it.next();
				if (modalite.equals(infoTweet.getT_mois()) == false || modalite.equals("") ){
					choiceBox.getItems().add(infoTweet.getT_mois());
					modalite = infoTweet.getT_mois();
					System.out.println(infoTweet.getT_mois());
				}

			}
		case "semaine":
			while (it.hasNext()) {

				tweet infoTweet = (tweet)it.next();
				if (modalite.equals(infoTweet.getT_semaine()) == false || modalite.equals("") ){
					choiceBox.getItems().add(infoTweet.getT_semaine());
					modalite = infoTweet.getT_semaine();
					System.out.println(infoTweet.getT_semaine());
				}

			}

		case "jour":
			while (it.hasNext()) {

				tweet infoTweet = (tweet)it.next();
				if (modalite.equals(infoTweet.getT_jour()) == false || modalite.equals("") ){
					choiceBox.getItems().add(infoTweet.getT_jour());
					modalite = infoTweet.getT_jour();
					System.out.println(infoTweet.getT_jour());
				}

			}
		}

		choiceBox.setValue("Aucun");
		return choiceBox;
	}

	//fonction qui permet de gerer les filtres a appliquer syr le barchart
	public BaseDeNombreTweet creer_donnee_barchart() {
		maCollec = tri_tweet_date();
		//Iterateur qui permet de parcourir le treeset
		Iterator  it=maCollec.iterator();
		//La base de nombreTweet contient toutes les données necessaire a la construction du barchart
		BaseDeNombreTweet bdnt = new BaseDeNombreTweet();
		// Le couple modalité/compteur correspond a 
		Integer compteur = 0;
		String modalite = "";

		/*si il y a un filtre sur les jours */
		if (f_jour.equals("") == false) {
			// Boucle sur les element du treeset


			while (it.hasNext()) {
				//l'element du treeset est stocké dans un objet de type tweet
				tweet infoTweet = (tweet)it.next();

				// On test si le tweet correspond aux conditions de tri (jour,semaine,mois)

				if (f_jour.equals(infoTweet.getT_jour()) && f_mois.equals(infoTweet.getT_mois())) {

					//On réccupère l'heure du tweet

//test
					String[] split_h = infoTweet.getT_heure().split(":");
					String heure = split_h[0];

					/*le treeset est trié par ordre chronologique 
					 * donc si on observe un changement entre deux valeur
					 * on créé une nouvelle modalité
					 */
					//si l'heure du nouveau tweet est identique a celle du tweet précédent
					// ou si il s'agit du premier tweet

					if (modalite.equals(heure) || modalite.equals("")) {
						//on incrémente de 1 le compteur correspondant a la valeur de la modalité
						compteur +=1;
						//la variable modalité prend la valeur de la nouvelle heure
						modalite = heure;
					} else {
						// On créé un objet nombreTweet
						nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
						//on ajoute l'objet a la base de nombreTweet
						bdnt.ajouteNombreTweet(donnee_barchart);
						//la variable modalité prend la valeur de la nouvelle heure
						modalite = heure;
						//on reinitialise le compteur de modalité a 1
						compteur = 1;
					}
				}
			}

		} else {
			/*si il y a un filtre sur les Semaines */

			if (f_semaine.equals("") == false) {

				while (it.hasNext()) {
					tweet infoTweet = (tweet)it.next();
					if (f_semaine.equals(infoTweet.getT_semaine())){
						if (modalite.equals(infoTweet.getT_jour()) || modalite.equals("")) {
							compteur +=1;
							modalite = infoTweet.getT_jour();
						} else {
							nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
							bdnt.ajouteNombreTweet(donnee_barchart);
							modalite = infoTweet.getT_jour();
							compteur = 1;
						}
					}
				}


			} else {


				/*si il y a un filtre sur les mois */
				if (f_mois.equals("") == false) {
					// Boucle sur les element du treeset
					while (it.hasNext()) {
						//l'element du treeset est stocké dans un objet de type tweet
						tweet infoTweet = (tweet)it.next();
						// On test si le tweet correspond aux conditions de tri (mois)
						if (f_mois.equals(infoTweet.getT_mois())){
							/*le treeset est trié par ordre chronologique 
							 * donc si on observe un changement entre deux valeur
							 * on créé une nouvelle modalité
							 */
							//si le jour du nouveau tweet est identique a celui du tweet précédent
							// ou si il s'agit du premier tweet
							if (modalite.equals(infoTweet.getT_jour()) || modalite.equals("")) {
								//on incrémente de 1 le compteur correspondant a la valeur de la modalité
								compteur +=1;
								//la variable modalité prend la valeur du nouveau jour
								modalite = infoTweet.getT_jour();
							} else {
								// On créé un objet nombreTweet
								nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
								//on ajoute l'objet a la base de nombreTweet
								bdnt.ajouteNombreTweet(donnee_barchart);
								//la variable modalité prend la valeur de la nouvelle heure
								modalite = infoTweet.getT_jour();
								//on reinitialise le compteur de modalité a 1
								compteur = 1;
							}
						}
					}

				} else {
					/*si il n'y a pas de filtres */
					// Boucle sur les element du treeset
					while (it.hasNext()) {
						//l'element du treeset est stocké dans un objet de type tweet
						tweet infoTweet = (tweet)it.next();
						/*le treeset est trié par ordre chronologique 
						 * donc si on observe un changement entre deux valeur
						 * on créé une nouvelle modalité
						 */
						//si le jour du nouveau tweet est identique a celui du tweet précédent
						// ou si il s'agit du premier tweet

						if (modalite.equals(infoTweet.getT_jour()) || modalite.equals("")) {//3
							//on incrémente de 1 le compteur correspondant a la valeur de la modalité
							compteur +=1;
							//la variable modalité prend la valeur du nouveau jour
							modalite = infoTweet.getT_jour();
						} else {
							// On créé un objet nombreTweet
							nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
							//System.out.println(modalite + " : " + compteur);
							//on ajoute l'objet a la base de nombreTweet
							bdnt.ajouteNombreTweet(donnee_barchart);
							compteur =1;
							//la variable modalité prend la valeur de la nouvelle heure
							modalite = infoTweet.getT_jour();
							//on reinitialise le compteur de modalité a 1

						}
					}
				}
			}
		}
		nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
		bdnt.ajouteNombreTweet(donnee_barchart);
		return bdnt;
	}

	public ArrayList<tweet> tri_tweet_date(){
		Collections.sort(maCollec,tweet.triDate);
		return maCollec;
	}

	public ArrayList<tweet> tri_tweet_pseudo(){
		Collections.sort(maCollec,tweet.triUsers);
		return maCollec;
	}

	public ArrayList<tweet> tri_tweet_retweet(){
		Collections.sort(maCollec,tweet.triRetweet);
		return maCollec;
	}




	public BaseDeUtilisateurs creer_donnee_tableau(String choix_tri) {
		BaseDeUtilisateurs bdu = new BaseDeUtilisateurs();
		String pseudo_users = "";
		Integer compteur_nombre_tweet = 0;
		Integer compteur_nombre_mention = 0;
		Integer compteur_nombre_retweet = 0;
		utilisateur user;


		switch (choix_tri) {
		case "Nombre de tweet":
			maCollec = tri_tweet_pseudo();
			Iterator  iterator_tweet=maCollec.iterator();

			while (iterator_tweet.hasNext()) {
				tweet infoTweet = (tweet)iterator_tweet.next();
				//System.out.println(infoTweet.getPseudo_users());
				if (pseudo_users.equals(infoTweet.getT_pseudo_users()) || pseudo_users.equals("") ){
					//System.out.println(infoTweet.getPseudo_users());
					compteur_nombre_tweet +=1;
					pseudo_users = infoTweet.getT_pseudo_users();
				} else {

					//System.out.println(infoTweet.getT_pseudo_users() + " : " +  compteur_nombretweet );
					user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
					bdu.ajouteUtilisateur(user);
					pseudo_users = infoTweet.getT_pseudo_users();
					compteur_nombre_tweet = 1;
				}

			}

			user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
			bdu.ajouteUtilisateur(user);	
			bdu.tri_utilisateur_nombreTweet();
			return bdu;
			/*	
		case "Nombre de mentions":

			tweet infoTweet = (tweet)iterator_retweet.next();
			String pseudo = infoTweet.getT_pseudo_users();
			pseudo= "@"+pseudo;
			compteur_nombremention = compte_mention(pseudo);
			 */
		case "Nombre de retweet":
			maCollec = tri_tweet_retweet();
			Iterator  iterator_retweet=maCollec.iterator();

			while (iterator_retweet.hasNext()) {
				tweet infoTweet = (tweet)iterator_retweet.next();
				//System.out.println(infoTweet.getPseudo_users());
				if (infoTweet.getT_pseudo_retweet().equals("NA") == false){
					if (pseudo_users.equals(infoTweet.getT_pseudo_retweet()) || pseudo_users.equals("")){
						//System.out.println(infoTweet.getPseudo_users());
						compteur_nombre_tweet +=1;
						pseudo_users = infoTweet.getT_pseudo_retweet();
					} else {

						user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
						bdu.ajouteUtilisateur(user);
						pseudo_users = infoTweet.getT_pseudo_retweet();
						compteur_nombre_tweet = 1;
					}
				}
			}
			user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
			bdu.ajouteUtilisateur(user);	
			bdu.tri_utilisateur_nombreTweet();
			return bdu;

		default :
			return bdu;
		}


	}

	/*
	public int compte_mention(String rech) {
		Iterator  iterator=maCollec.iterator();
		int compteur_nombre_mention=0;
		while (iterator.hasNext())
		{
			tweet n= (tweet) iterator.next();
			String contenu = n.getT_contenu();
			String rt = n.getT_pseudo_retweet();
			boolean exist =contenu.contains(rech);
			if (exist==true && rt=="NA"){
				compteur_nombre_mention += 1;
			}
		}
		return compteur_nombre_mention;
	}
	 */


	public void explore(int i) {
		Iterator  iterator=maCollec.iterator();

		while (iterator.hasNext())
		{
			System.out.println("tweet nÂ° "+ i + " :" + iterator.next());

			i=i+1;
		}
	}


	public static int compterOccurrences(String maChaine, char recherche) // pompÃ© internet
	{
		int nb = 0;
		for (int i=0; i < maChaine.length(); i++)
		{
			if (maChaine.charAt(i) == recherche)
				nb++;
		}
		return nb;
	}

	public void trouveactu(String rech) {
		Iterator  iterator=maCollec.iterator();
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(rech);

		while (iterator.hasNext())
		{
			tweet n= (tweet) iterator.next();
			String mat= n.toString();
			//System.out.println(n.getTitre());
			matcher = pattern.matcher(mat);
			while (matcher.find()) {
				System.out.println(mat);
			}

		}
	}

	public void importation(String fic) throws IOException {
		@SuppressWarnings("resource")

		BufferedReader reader = new BufferedReader(new FileReader("resources/"+fic));
		String ligne;
		while((ligne = reader.readLine()) != null){
			String[] sepligne = ligne.split("\t");
			String pseudo_users = sepligne[1];
			String date_heure = sepligne[2];
			String contenu = sepligne[3];
			String lien_dans_contenu="";
			String hashtag="";
			String pseudo_mentionne="";

			int nb_hashtag=0;
			int nb_pseudo_mentionne=0;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String[] sep_date_heure = date_heure.split(" ");

			String heure = sep_date_heure[1];
			LocalDate date = LocalDate.parse(sep_date_heure[0], formatter);

			//cette variable sert uniquement a separer les jours mois et annee
			String date_string = sep_date_heure[0];
			String[] sepdate_j = date_string.split("-");
			String annee = sepdate_j[0];
			String mois = trouvemois(date);
			//String jourSemaine_t = sepdate_j[];
			String jour = sepdate_j[2];

			String jour_semaine= trouvejour(date);
			int l = Integer.parseInt(jour);
			String semaine=trouvenomsemaine(date,jour_semaine,l);

			boolean exist = contenu.contains("#");
			if(exist==true) {
				char c= '#';
				int nb = compterOccurrences(contenu, c);
				int deb=0;
				nb_hashtag=nb;
				for(int j = 1; j <= nb; j++) {
					deb = contenu.indexOf(c,deb);
					if(deb != -1) {
						int fin = contenu.indexOf(" ",deb);
						if(fin != -1) {
							hashtag=hashtag+contenu.substring(deb,fin);
						}else {
							hashtag=hashtag+contenu.substring(deb,contenu.length()-1);
						}
						if(fin != -1) {
							deb=fin;
						}else break;	
					}else break;	
				}
			}else {
				hashtag="NA";
			}
			hashtag = hashtag.replace(",", "");

			boolean existb = contenu.contains("@");
			if(existb==true) {
				char c= '@';
				int nb = compterOccurrences(contenu, c);
				int deb=0;
				nb_pseudo_mentionne=nb;
				for(int j = 1; j <= nb; j++) {
					deb = contenu.indexOf(c,deb);
					if(deb != -1) {
						int fin = contenu.indexOf(" ",deb);
						if(fin != -1) {
							pseudo_mentionne=pseudo_mentionne+contenu.substring(deb,fin);
						}else {
							pseudo_mentionne=pseudo_mentionne+contenu.substring(deb,contenu.length()-1);
						}
						if(fin != -1) {
							deb=fin;
						}else break;	
					}else break;	
				}
			}else {
				pseudo_mentionne="NA";
			}

			boolean existc = contenu.contains("https://");
			if(existc==true) {
				String c="https://";
				int deb = contenu.indexOf(c);
				int fin = contenu.indexOf(" ",deb);
				if(fin != -1) {
					lien_dans_contenu=contenu.substring(deb,fin);
				}else {
					lien_dans_contenu=contenu.substring(deb,contenu.length()-1);
				}


			}else {
				lien_dans_contenu="non";
			}


			try {
				String pseudo_retweet = sepligne[4];
				pseudo_mentionne="NA";
				hashtag="NA";
				nb_hashtag=0;
				nb_pseudo_mentionne=0;

				tweet t = new tweet(pseudo_users,date,heure,contenu,lien_dans_contenu,hashtag,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne,annee,mois,jour,jour_semaine,semaine);
				//System.out.println(t+"\n");
				maCollec.add(t);

			} catch (Exception ex){
				String pseudo_retweet = "NA";


				tweet t = new tweet(pseudo_users,date,heure,contenu,lien_dans_contenu,hashtag,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne,annee,mois,jour,jour_semaine,semaine);
				//System.out.println(t+"\n");
				maCollec.add(t);
				//System.out.println("pas de retweet");
			}
		}

	}


	// ceci est un essai
	//@SuppressWarnings("unchecked")
	/*public void ouvrir() throws Exception {
		try {
			FileInputStream w = new FileInputStream("resources/data.txt");
			ObjectInputStream o = new ObjectInputStream(w);
			Object lu =o.readObject();
			maCollec = (TreeSet<tweet>)lu;
			w.close();
			o.close();
		} catch (IOException e) {
			System.out.println("erreur d'IO");
		}


	}*/
	/*
	public void enregistrer() throws Exception {
		try {
			FileOutputStream w = new FileOutputStream("resources/data.txt");
			ObjectOutputStream o = new ObjectOutputStream(w);
			o.writeObject(maCollec);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	/*
	private void writeObject(java.io.ObjectOutputStream out) throws IOException{
		out.defaultWriteObject();
	}
	private void readObject(java.io.ObjectInputStream out) throws IOException, ClassNotFoundException{
		out.defaultReadObject();
	}*/

	//a completer
}
