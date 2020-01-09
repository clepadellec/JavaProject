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

	//BaseDeTweets possède plusieurs attributs : voir avec Charlie
	public ArrayList<tweet> maCollec;
	public String f_mois = "Aucun" ;
	public String f_semaine = "Aucun" ;
	public String f_jour = "Aucun" ;
	public String f_joursem="Aucun";

	// La fonction initialise créer une nouvelle collection, on affecte un nouveau arraylist à maCollec
	public void initialise() {
		maCollec = new ArrayList<tweet>();
	}

	//Si dessous on retrouve les getters et setters qui permettent de mettre à jour ou récupérer la valeur des attributs
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

	// la fonction ajoute permet d'ajouter un tweet à la base de tweets
	public void ajoute(tweet t) {
		maCollec.add(t);
	}
	public String getF_joursem() {
		return f_joursem;
	}

	public void setF_joursem(String f_joursem) {
		this.f_joursem = f_joursem;
	}
	
	/*Les fonctions de tri ci-dessous permettent d'ordonner la base de tweet de différentes manieres
	on fait appel à des fonction de comparaison créees dans tweet*/
	
	//tri par date
	public ArrayList<tweet> tri_tweet_date(){
		Collections.sort(maCollec,tweet.triDate);
		return maCollec;
	}
	
	//tri par pseudo
	public ArrayList<tweet> tri_tweet_pseudo(){
		Collections.sort(maCollec,tweet.triUsers);
		return maCollec;
	}
	
	//tri par retweet
	public ArrayList<tweet> tri_tweet_retweet(){
		Collections.sort(maCollec,tweet.triRetweet);
		return maCollec;
	}

	//La fonction trouvejour prend en paramètre un LocalDate et retourne le nom du jour de la semaine de la date rentrée
	public String trouvejour(LocalDate date ) {
		//conversion de notre date de type 'LocalDate' en type 'Date'
		//opération nécessaire pour utiliser les calendar
		Date datee = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		//création d'une nouvelle variable de type calendrier 
		Calendar calendarNomJourSemaine = Calendar.getInstance();
		
		//on initialise le calendrier avec la date souhaitée
		calendarNomJourSemaine.setTime(datee);
		
		//L'integer semaine récupère le numéro du jour de la semaine
		int semaine = calendarNomJourSemaine.get(Calendar.DAY_OF_WEEK);
		
		//initialisation de la variable qui renverra le nom du jour de la semaine
		String nomjourSemaine = "";
		
		//creation d'un switch qui en fonction du numéro du jour de la semaine attribue le nom
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
		//on retourne le nom
		return nomjourSemaine;
	}

	//La fonction trouvemois à excatement le même fonctionnement que trouvejour sauf qu'elle retourne les noms de mois de la date rentrée
	public String trouvemois(LocalDate date ) {
		Date datee = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Calendar calendarNomJourSemaine = Calendar.getInstance();
		calendarNomJourSemaine.setTime(datee);
		int semaine = calendarNomJourSemaine.get(Calendar.MONTH);
		String nomMois = "";
		switch (semaine) {
		case 0:  nomMois = "Janvier";
		break;
		case 1:  nomMois = "Fevrier";
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
		case 7:  nomMois = "Aout";
		break;
		case 8:  nomMois = "Septembre";
		break;
		case 9:  nomMois = "Octobre";
		break;
		case 10:  nomMois = "Novembre";
		break;
		case 11:  nomMois = "Decembre";
		break;
		}

		return nomMois;
	}

	//la fonction trouvenomsemaine à pou but, à partir d'une date souhaitée, de renvoyer la date de début et de fin de la semaine
	public String trouvenomsemaine(LocalDate date, String joursem, int jour) {
		//initialisation de la variable qui stockera le nom de la semaine
		String nomsemaine="";
		//conversion de notre LocalDate en Date 
		Date datee = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		//Initialisation du calendrier avec notre date
		Calendar calendarNomJourSemaine = Calendar.getInstance();
		calendarNomJourSemaine.setTime(datee);
		
		//on récupère le nb de jour dans le mois de la date rentrée
		int nb_jour_mois = calendarNomJourSemaine.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// on initialise 2 variables, une pour le mois du début et une pour le mois de fin
		// cette opération est nécessaire car certaines semaines sont à cheval sur 2 mois
		int num_mois_dep = calendarNomJourSemaine.get(Calendar.MONTH);
		int num_mois_fin = calendarNomJourSemaine.get(Calendar.MONTH);
		
		// la variable deb correspondra au jour du début, la variable fin au jour de la fin et diff servira lorsque la semaine
		// sera à cheval sur 2 mois
		int deb=0;
		int fin=0;
		int diff=0;
		
		//en paramètre d'entrée on a rentrée le nom du jour de la semaine, et le numéro du jour dans le mois
		//grâce à ces deux infos on peut facilement déterminer la date de début et la date de fin
		switch (joursem) {
		case "Dimanche":
			// ex: si le nom du jour est un dimanche (08/09 par exemple)
			//alors on sait que le lundi de la semaine sera en jour-6 (02/09)
			// on sait également que dimanche sera le dernier jour donc fin=jour
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
		
		// si le jour de fin est > au nombre de jours dans le mois c'est que la semaine est à cheval sur 2 mois
		if (fin>nb_jour_mois) {
			//on calcul le nb de jours en trop
			diff= fin-nb_jour_mois;
			// on rajoute cette différence à 0 et on incrémente le numéro de mois de 1
			fin=0+diff;
			num_mois_fin=num_mois_fin+1;
		}
		
		// si le jour de début est inférieur à 0 c'est que le début de semaine se situe dans le mois précédent
		if (deb<=0) {
			//on décrémente le numéro de mois
			num_mois_dep = calendarNomJourSemaine.get(Calendar.MONTH)-1;
			
			// si le nombre de jour du mois actuel est 31 alors le mois précédent à 30j
			if(nb_jour_mois==31) {
				//avec une simple soustraction on obtient la bonne date de début
				deb=30-Math.abs(deb);
			}else {
				deb=31-Math.abs(deb);
			}
			//dans cette méthode on sait que l'on a pas de données en février et que nous n'avons pas de chevauchement d'année
			//dans le cas contraire la fonction ne marcherait pas
		}
		
		//l'indiçage des mois en java commence à 0 on incrémente donc les deux numéro de mois 
		num_mois_dep=num_mois_dep+1;
		num_mois_fin=num_mois_fin+1;
		
		//on met en forme le nom de la semaine et on le retourne
		nomsemaine= "Semaine du Lundi "+deb+"/"+num_mois_dep+" au Dimanche "+fin+"/"+num_mois_fin;
		return nomsemaine;
	}

	/*Cette fonction permet de rendre dynamique les liste deroulante 
	 * qui sont utilisée lors des filtres sur les diagrammes
	 * variable_tri : chaine de caractere qui determine la maniere dont les modalités vont etre ordoné
	 */
	public ChoiceBox<String> setChoiceBox(String variable_tri){
		//l'objet retourné par la fonction est créé
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		//iterateur sur la base de tweet
		Iterator  it= maCollec.iterator();
		//variable pivot qui sert de comparateur entre deux objet successif dans la base. Elle est de type String ou Integer (semaine/mois/jour)
		Object modalite = "";
		//on ajoute une modalité "Aucun" dans le cas ou la liste ne contient aucun filtre
		choiceBox.getItems().add("Aucun");

		
		switch (variable_tri) {

		case "mois":
			//boucle sur la base de tweet
			while (it.hasNext()) {
				//Objet tweet de la base
				tweet infoTweet = (tweet)it.next();
				//si le mois de cet objet est diffrent du précédent (ou si il s'agit du premier element de la base)
				if (modalite.equals(infoTweet.getT_mois()) == false || modalite.equals("") ){
					//on ajoute un element a la liste droulante
					choiceBox.getItems().add(infoTweet.getT_mois());
					//la pivot change 
					modalite = infoTweet.getT_mois();
				}

			}
		case "semaine":
			//boucle sur la base de tweet
			while (it.hasNext()) {
				//Objet tweet de la base
				tweet infoTweet = (tweet)it.next();
				//si la semaine de cet objet est diffrente de la précédente(ou si il s'agit du premier element de la base)
				if ((modalite.equals(infoTweet.getT_semaine()) == false || modalite.equals(""))){
					//on ajoute un element a la liste droulante
					choiceBox.getItems().add(infoTweet.getT_semaine());
					//la pivot change 
					modalite = infoTweet.getT_semaine();
				}

			}

		case "jour":
			//boucle sur la base de tweet
			while (it.hasNext()) {
				//Objet tweet de la base
				tweet infoTweet = (tweet)it.next();
				//si le mois de cet objet est diffrent du précédent (ou si il s'agit du premier element de la base)
				if ((modalite.equals(infoTweet.getT_jour()) == false || modalite.equals(""))){
					//on ajoute un element a la liste droulante
					choiceBox.getItems().add(infoTweet.getT_jour());
					//la pivot change 
					modalite = infoTweet.getT_jour();
				}

			}
		}

		//on initialise la valeur de cette liste déroulante a "Aucun"
		choiceBox.setValue("Aucun");
		return choiceBox;
	}

	/*fonction qui permet de créer dynamiquement un titre a un graphique en fonction du filtre appliqué*/
	public void creer_titre(BaseDeNombreTweet bdnt){
		String titre;
		//si il y a un filtre sur les jours
		if (f_jour.equals("Aucun") == false) {
			titre = "Nombre de tweet : " + f_jour + " " + f_mois;
		} else {
			//si il y a un filtre sur les semaines
			if (f_semaine.equals("Aucun") == false) {
				titre = "Nombre de tweet : " + f_semaine;
			} else {
				//si il y a un filtre sur les mois
				if (f_mois.equals("Aucun") == false) {
					titre =  "Nombre de tweet : " + f_mois;
				} else {
					//si il n'y a pas de filtre
					titre = "Nombre de tweet par jour";
				}
			}
		}
		//on ajoute le filtre au barchart
		bdnt.setTitle_chart(titre);
	}



	//fonction qui permet de gerer les filtres a appliquer syr le barchart
	public BaseDeNombreTweet creer_donnee_barchart() {
		//on tir la base en fonction des dates
		maCollec = tri_tweet_date();
		//Iterateur qui permet de parcourir la base
		Iterator  it=maCollec.iterator();
		//La base de nombreTweet contient toutes les donnees necessaire a la construction du barchart
		BaseDeNombreTweet bdnt = new BaseDeNombreTweet();
		// Le couple modalite compteur correspond a un jour (ou heure) accompagné de son nombre de tweet
		Integer compteur = 0;
		String modalite = "";
		//on appel la fontion qui permet de creer le titre
		creer_titre(bdnt);
		/*si il y a un filtre sur les jours */
		if (f_jour.equals("Aucun") == false) {
			// Boucle sur les element de la base

			while (it.hasNext()) {
				//l'element de la base est stockÃ© dans un objet de type tweet
				tweet infoTweet = (tweet)it.next();

				// On test si le tweet correspond aux conditions de tri (jour,semaine,mois)

				if (f_jour.equals(infoTweet.getT_jour()) && f_mois.equals(infoTweet.getT_mois())) {

					//On reccupere l'heure du tweet

					String[] split_h = infoTweet.getT_heure().split(":");
					String heure = split_h[0];

					/*la base est trie par ordre chronologique 
					 * donc si on observe un changement entre deux valeur
					 * on cree une nouvelle modalite
					 */
					//si l'heure du nouveau tweet est identique a celle du tweet prÃ©cÃ©dent
					// ou si il s'agit du premier tweet

					if (modalite.equals(heure) || modalite.equals("")) {
						//on incrÃ©mente de 1 le compteur correspondant a la valeur de la modalitÃ©
						compteur +=1;
						//la variable modalitÃ© prend la valeur de la nouvelle heure
						modalite = heure;
					} else {
						// On crÃ©Ã© un objet nombreTweet
						nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
						//on ajoute l'objet a la base de nombreTweet
						bdnt.ajouteNombreTweet(donnee_barchart);
						//la variable modalitÃ© prend la valeur de la nouvelle heure
						modalite = heure;
						//on reinitialise le compteur de modalitÃ© a 1
						compteur = 1;
					}
				}
			}

		} else {
			/*si il y a un filtre sur les Semaines */

			if (f_semaine.equals("Aucun") == false) {
				//oucle sur les elements de la base
				while (it.hasNext()) {
					//tweet courrant de la base
					tweet infoTweet = (tweet)it.next();
					//si la semaine du tweet courrant est la meme que celle du filtre
					if (f_semaine.equals(infoTweet.getT_semaine())){
						/*la base est trie par ordre chronologique 
						 * donc si on observe un changement entre deux valeur
						 * on cree une nouvelle modalite
						 */
						//si le jour du nouveau tweet est identique a celle du tweet prÃ©cÃ©dent
						// ou si il s'agit du premier tweet
						if (modalite.equals(infoTweet.getT_jour()) || modalite.equals("")) {
							//on incrémente la valeur 
							compteur +=1;
							modalite = infoTweet.getT_jour();
						} else {
							//on cré un objet nombre tweet
							nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
							//on ajoute l'objet a la base de nombreTweet
							bdnt.ajouteNombreTweet(donnee_barchart);
							modalite = infoTweet.getT_jour();
							//on reinitialise le compteur
							compteur = 1;
						}
					}
				}


			} else {


				/*si il y a un filtre sur les mois */
				if (f_mois.equals("Aucun") == false) {
					// Boucle sur les element du treeset
					while (it.hasNext()) {
						//l'element du treeset est stockÃ© dans un objet de type tweet
						tweet infoTweet = (tweet)it.next();
						// On test si le tweet correspond aux conditions de tri (mois)
						if (f_mois.equals(infoTweet.getT_mois())){
							/*la base est triÃ© par ordre chronologique 
							 * donc si on observe un changement entre deux valeur
							 * on crÃ©Ã© une nouvelle modalitÃ©
							 */
							//si le jour du nouveau tweet est identique a celui du tweet prÃ©cÃ©dent
							// ou si il s'agit du premier tweet
							if (modalite.equals(infoTweet.getT_jour()) || modalite.equals("")) {
								//on incrÃ©mente de 1 le compteur correspondant a la valeur de la modalitÃ©
								compteur +=1;
								//la variable modalitÃ© prend la valeur du nouveau jour
								modalite = infoTweet.getT_jour();
							} else {
								// On crÃ©Ã© un objet nombreTweet
								nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
								//on ajoute l'objet a la base de nombreTweet
								bdnt.ajouteNombreTweet(donnee_barchart);
								//la variable modalitÃ© prend la valeur de la nouvelle heure
								modalite = infoTweet.getT_jour();
								//on reinitialise le compteur de modalitÃ© a 1
								compteur = 1;
							}
						}
					}

				} else {
					/*si il n'y a pas de filtres */
					// Boucle sur les element du treeset
					while (it.hasNext()) {
						//l'element du treeset est stockÃ© dans un objet de type tweet
						tweet infoTweet = (tweet)it.next();
						/*la base est triÃ© par ordre chronologique 
						 * donc si on observe un changement entre deux valeur
						 * on crÃ©Ã© une nouvelle modalitÃ©
						 */
						//si le jour du nouveau tweet est identique a celui du tweet prÃ©cÃ©dent
						// ou si il s'agit du premier tweet

						if (modalite.equals(infoTweet.getT_jour()) || modalite.equals("")) {
							//on incrÃ©mente de 1 le compteur correspondant a la valeur de la modalitÃ©
							compteur +=1;
							//la variable modalitÃ© prend la valeur du nouveau jour
							modalite = infoTweet.getT_jour();
						} else {
							// On crÃ©Ã© un objet nombreTweet
							nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
							//System.out.println(modalite + " : " + compteur);
							//on ajoute l'objet a la base de nombreTweet
							bdnt.ajouteNombreTweet(donnee_barchart);
							compteur =1;
							//la variable modalitÃ© prend la valeur de la nouvelle heure
							modalite = infoTweet.getT_jour();
							//on reinitialise le compteur de modalitÃ© a 1

						}
					}
				}
			}
		}
		//on ajoute le dernier element car on est sorti de la boucle sans l'avoir ajouté
		nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
		bdnt.ajouteNombreTweet(donnee_barchart);
		return bdnt;
	}

	/* fonction qui permet d'ajouter les donnes necessaire a la constructon du tableau des utilisateurs*/
	/* La chaine choix_tri est un indicateur de la maniere dont le tableau va etre trier */
	public BaseDeUtilisateurs creer_donnee_tableau_utilisateurs(String choix_tri) {
		//objet renvoyé par la fonction
		BaseDeUtilisateurs bdu = new BaseDeUtilisateurs();
		//pseudo de l(utilisateur courrant
		String pseudo_users = "";
		//données de l'utilisateur courrant
		Integer compteur_nombre_tweet = 0;
		Integer compteur_nombre_mention = 0;
		Integer compteur_nombre_retweet = 0;
		//objet Utilisateur a ajoiter dans la base 
		utilisateur user;
		

		switch (choix_tri) {
		case "Nombre de tweet":
			//on tri en fonction du pseudo
			maCollec = tri_tweet_pseudo();
			Iterator  iterator_tweet=maCollec.iterator();
			//parcour de la base
			while (iterator_tweet.hasNext()) {
				//obet courrant de la base
				tweet infoTweet = (tweet)iterator_tweet.next();
				//si la valeur courrante est identique a la precdente, ou si c'ets le premier element de la base
				if (pseudo_users.equals(infoTweet.getT_pseudo_users()) || pseudo_users.equals("") ){
					//on incremente le compteur 
					compteur_nombre_tweet +=1;
					pseudo_users = infoTweet.getT_pseudo_users();
				} else {
					//on créé notre objet utilisateur
					user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
					//on ajoute l'utilisateur a la base
					bdu.ajouteUtilisateur(user);
					pseudo_users = infoTweet.getT_pseudo_users();
					//on réinitialise le compteur
					compteur_nombre_tweet = 1;
				}
				
			}
			//on ajoute le dernier element car on est sorti de la boucle sans l'avoir ajouté
			user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
			bdu.ajouteUtilisateur(user);	
			bdu.tri_utilisateur_nombreTweet();
			return bdu;
			/*	
			 * Ici on fait appel a la fonction compte_mention mais elle rend le programme trop lourd
			 * 
		case "Nombre de mentions":

			tweet infoTweet = (tweet)iterator_retweet.next();
			String pseudo = infoTweet.getT_pseudo_users();
			pseudo= "@"+pseudo;
			compteur_nombremention = compte_mention(pseudo);
			 */
		case "Nombre de retweet":
			//on tri la base par pseudo de retweet
			maCollec = tri_tweet_retweet();
			Iterator  iterator_retweet=maCollec.iterator();
			//on parcour la base
			while (iterator_retweet.hasNext()) {
				//obet courrant de la base
				tweet infoTweet = (tweet)iterator_retweet.next();
				//Si le tweet est un retweet
				if (infoTweet.getT_pseudo_retweet().equals("NA") == false){
					//si la valeur courrante est identique a la precdente, ou si c'ets le premier element de la base
					if (pseudo_users.equals(infoTweet.getT_pseudo_retweet()) || pseudo_users.equals("")){
						//on incrémente le compteur
						compteur_nombre_tweet +=1;
						pseudo_users = infoTweet.getT_pseudo_retweet();
					} else {

						user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
						//on créé notre objet utilisateur
						bdu.ajouteUtilisateur(user);
						//on ajoute l'utilisateur a la base
						pseudo_users = infoTweet.getT_pseudo_retweet();
						//on réinitialise le compteur
						compteur_nombre_tweet = 1;
					}
				}
			}
			//on ajoute le dernier element car on est sorti de la boucle sans l'avoir ajouté
			user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
			bdu.ajouteUtilisateur(user);	
			bdu.tri_utilisateur_nombreTweet();
			return bdu;

		default :
			return bdu;
		}


	}
	
	//cette méthode renvoie la liste de tous les hashtags triés et avec doublons
	//cette liste nous servira à remplir la base de hashtags
	public ArrayList<String> intermediaire_rempli_bdh() {
		Integer cptlength = 0 ;
		
		// on déclare un tableau avec tous les caractères qui terminent un hashtag (ex : #FIFA. , #FIFA: -> les derniers caractères ne comptent pas dans le hashtag)
		String tab_charFinTweet[ ];
		tab_charFinTweet = new String[] {" ",";",".",",","?","!",":","/","-"};
		// creation de la liste qui renverra à la fin de la fonction
		ArrayList<String> list_hashtag = new ArrayList<String>();
		
		//creation d'un iterateur pour parcourir toute la base de tweet
		Iterator  iterator=maCollec.iterator();
		while (iterator.hasNext())
		{
			//on stock le tweet actuel dans la variable t
			tweet t = (tweet)iterator.next();
			//on récupère le contenu du tweet
			String contenu = t.getT_contenu();
			// creation d'une variable hashtag qui stockera les hashtags
			String hashtag="";
			//deb servira à identifier la position de début du hashtag
			int deb=0;
			// le caractère à trouver pour identifier un hashtag est '#'
			String c="#";
			// on récupère le nombre de hashtag dans le tweet
			int nbhashtag= t.getT_nb_hashtag();
			
			//on boucle sur le nombre de hashtag du tweet pour récupérer chaque hashtag
			for(int j = 0; j <= nbhashtag; j++) {
				
				
				int min = contenu.length();
				//on recupere la position du premier #
				deb = contenu.indexOf(c,deb);
				int fin=0;
				
				// si deb est différent de -1 ça veut dire qu'il existe un hashtag
				if(deb != -1) {
					
					// on parcout le tableau des éléments qui finissent les tweets pour trouver le caractère qui termine le hashtag
					for (int k = 0; k < tab_charFinTweet.length ;k++) {
						if (contenu.indexOf(tab_charFinTweet[k],deb) != -1 && contenu.indexOf(tab_charFinTweet[k],deb) < min) {
							// on recupere la position la plusproche du début
							min = contenu.indexOf(tab_charFinTweet[k],deb);
						}
					}
					//fin correspond à la position ou s'arrete le hashtag
					fin = min;
					cptlength +=1;
					
					// si fin est différent de -1 c'est qu'il reste encore du contenu après le hashtag
					if(fin != -1) {
						
						//extraction du hashtag
						hashtag=contenu.substring(deb,fin);
						//correction d'un bug 
						hashtag = hashtag.replace(",", "");
						
						// si le hashtag contient quelque chose on l'ajoute sinon on ne l'ajoute pas
						// certaines personnes ont juste tweeté "blabbla # blabla", on ne considère pas que la personne est utilisé un hashtag
						if ((hashtag != " ") && (hashtag != " # ")) {
							list_hashtag.add(hashtag);
						}
						// la position de fin devient la position de début
						deb=fin;
					}else {
						
						// si fin =-1 cela signifie que la hashtag se termine à la fin du contenu
						hashtag=contenu.substring(deb,contenu.length()-1);
						hashtag = hashtag.replace(",", "");
						if ((hashtag != " ") && (hashtag != " # ")) {		
							list_hashtag.add(hashtag);
						}	
						break;
					}
				}else break;
			}
		}
		Collections.sort(list_hashtag);
		System.out.println(cptlength);
		return list_hashtag;
	}
	
	public BaseDeHashtag rempli_bdh(ArrayList<String> list_hashtag) {
		
		int long_list =list_hashtag.size();
		BaseDeHashtag bdh = new BaseDeHashtag();
		int cpt=1;
		String h_comp= list_hashtag.get(0);
		
		for (int i=1; i <long_list; i++) {
		
			if(h_comp.equals(list_hashtag.get(i))) {
				cpt=cpt+1;
			}else {

				hashtag h= new hashtag(h_comp, cpt);
				if ((h.getH_libele() !=" #")&&(h.getH_libele() !=" ,")) {
					bdh.ajouteHashtag(h);
					cpt=1;
					h_comp=list_hashtag.get(i);
				}

			}
		}

		bdh.tri_hashtag_occurence();
		return bdh;
	}
	
	/*
	 * Cette fonction n'a pas été utilisé car elle aurait rendu
	 * la complexité du programme quadratique. Et nous n'avons pas trouvé de moyen 
	 * de proceder autrement pour le moment
	 */
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
			System.out.println("tweet nÃÂ° "+ i + " :" + iterator.next());

			i=i+1;
		}
	}


	public static int compterOccurrences(String maChaine, char recherche) // pompÃÂ© internet
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
			}

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
				nb_hashtag=0;
				nb_pseudo_mentionne=0;

				tweet t = new tweet(pseudo_users,date,heure,contenu,lien_dans_contenu,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne,annee,mois,jour,jour_semaine,semaine);
				//System.out.println(t+"\n");
				maCollec.add(t);

			} catch (Exception ex){
				String pseudo_retweet = "NA";

				tweet t = new tweet(pseudo_users,date,heure,contenu,lien_dans_contenu,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne,annee,mois,jour,jour_semaine,semaine);
				//System.out.println(t+"\n");
				maCollec.add(t);
				//System.out.println("pas de retweet");
			}
		}

	}

}
