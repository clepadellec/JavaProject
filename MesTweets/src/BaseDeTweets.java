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

//
public class BaseDeTweets{

	//BaseDeTweets possede plusieurs attributs : voir avec Charlie
	public ArrayList<tweet> maCollec;
	public String f_mois = "Aucun" ;
	public String f_semaine = "Aucun" ;
	public String f_jour = "Aucun" ;
	public String f_joursem="Aucun";

	// La fonction initialise creer une nouvelle collection, on affecte un nouveau arraylist a  maCollec
	public void initialise() {
		maCollec = new ArrayList<tweet>();
	}

	//Si dessous on retrouve les getters et setters qui permettent de mettre a  jour ou recuperer la valeur des attributs
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

	// la fonction ajoute permet d'ajouter un tweet a  la base de tweets
	public void ajoute(tweet t) {
		maCollec.add(t);
	}
	public String getF_joursem() {
		return f_joursem;
	}

	public void setF_joursem(String f_joursem) {
		this.f_joursem = f_joursem;
	}
	
	/*Les fonctions de tri ci-dessous permettent d'ordonner la base de tweet de differentes manieres
	on fait appel a  des fonction de comparaison creees dans tweet*/
	
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

	//La fonction trouvejour prend en parametre un LocalDate et retourne le nom du jour de la semaine de la date rentree
	public String trouvejour(LocalDate date ) {
		//conversion de notre date de type 'LocalDate' en type 'Date'
		//operation necessaire pour utiliser les calendar
		Date datee = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		//creation d'une nouvelle variable de type calendrier 
		Calendar calendarNomJourSemaine = Calendar.getInstance();
		
		//on initialise le calendrier avec la date souhaitee
		calendarNomJourSemaine.setTime(datee);
		
		//L'integer semaine recupere le numero du jour de la semaine
		int semaine = calendarNomJourSemaine.get(Calendar.DAY_OF_WEEK);
		
		//initialisation de la variable qui renverra le nom du jour de la semaine
		String nomjourSemaine = "";
		
		//creation d'un switch qui en fonction du numero du jour de la semaine attribue le nom
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

	//La fonction trouvemois a  excatement le maªme fonctionnement que trouvejour sauf qu'elle retourne les noms de mois de la date rentree
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

	//la fonction trouvenomsemaine a  pou but, a  partir d'une date souhaitee, de renvoyer la date de debut et de fin de la semaine
	public String trouvenomsemaine(LocalDate date, String joursem, int jour) {
		//initialisation de la variable qui stockera le nom de la semaine
		String nomsemaine="";
		//conversion de notre LocalDate en Date 
		Date datee = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		//Initialisation du calendrier avec notre date
		Calendar calendarNomJourSemaine = Calendar.getInstance();
		calendarNomJourSemaine.setTime(datee);
		
		//on recupere le nb de jour dans le mois de la date rentree
		int nb_jour_mois = calendarNomJourSemaine.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// on initialise 2 variables, une pour le mois du debut et une pour le mois de fin
		// cette operation est necessaire car certaines semaines sont a  cheval sur 2 mois
		int num_mois_dep = calendarNomJourSemaine.get(Calendar.MONTH);
		int num_mois_fin = calendarNomJourSemaine.get(Calendar.MONTH);
		
		// la variable deb correspondra au jour du debut, la variable fin au jour de la fin et diff servira lorsque la semaine
		// sera a  cheval sur 2 mois
		int deb=0;
		int fin=0;
		int diff=0;
		
		//en parametre d'entree on a rentree le nom du jour de la semaine, et le numero du jour dans le mois
		//gra¢ce a  ces deux infos on peut facilement determiner la date de debut et la date de fin
		switch (joursem) {
		case "Dimanche":
			// ex: si le nom du jour est un dimanche (08/09 par exemple)
			//alors on sait que le lundi de la semaine sera en jour-6 (02/09)
			// on sait egalement que dimanche sera le dernier jour donc fin=jour
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
		
		// si le jour de fin est > au nombre de jours dans le mois c'est que la semaine est a  cheval sur 2 mois
		if (fin>nb_jour_mois) {
			//on calcul le nb de jours en trop
			diff= fin-nb_jour_mois;
			// on rajoute cette difference a  0 et on incremente le numero de mois de 1
			fin=0+diff;
			num_mois_fin=num_mois_fin+1;
		}
		
		// si le jour de debut est inferieur a  0 c'est que le debut de semaine se situe dans le mois precedent
		if (deb<=0) {
			//on decremente le numero de mois
			num_mois_dep = calendarNomJourSemaine.get(Calendar.MONTH)-1;
			
			// si le nombre de jour du mois actuel est 31 alors le mois precedent a  30j
			if(nb_jour_mois==31) {
				//avec une simple soustraction on obtient la bonne date de debut
				deb=30-Math.abs(deb);
			}else {
				deb=31-Math.abs(deb);
			}
			//dans cette methode on sait que l'on a pas de donnees en fevrier et que nous n'avons pas de chevauchement d'annee
			//dans le cas contraire la fonction ne marcherait pas
		}
		
		//l'indiacage des mois en java commence a  0 on incremente donc les deux numero de mois 
		num_mois_dep=num_mois_dep+1;
		num_mois_fin=num_mois_fin+1;
		
		//on met en forme le nom de la semaine et on le retourne
		nomsemaine= "Semaine du Lundi "+deb+"/"+num_mois_dep+" au Dimanche "+fin+"/"+num_mois_fin;
		return nomsemaine;
	}

	/*Cette fonction permet de rendre dynamique les liste deroulante 
	 * qui sont utilisee lors des filtres sur les diagrammes
	 * variable_tri : chaine de caractere qui determine la maniere dont les modalites vont etre ordone
	 */
	public ChoiceBox<String> setChoiceBox(String variable_tri){
		//l'objet retourne par la fonction est cree
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		//iterateur sur la base de tweet
		Iterator  it= maCollec.iterator();
		//variable pivot qui sert de comparateur entre deux objet successif dans la base. Elle est de type String ou Integer (semaine/mois/jour)
		Object modalite = "";
		//on ajoute une modalite "Aucun" dans le cas ou la liste ne contient aucun filtre
		choiceBox.getItems().add("Aucun");

		
		switch (variable_tri) {

		case "mois":
			//boucle sur la base de tweet
			while (it.hasNext()) {
				//Objet tweet de la base
				tweet infoTweet = (tweet)it.next();
				//si le mois de cet objet est diffrent du precedent (ou si il s'agit du premier element de la base)
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
				//si la semaine de cet objet est diffrente de la precedente(ou si il s'agit du premier element de la base)
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
				//si le mois de cet objet est diffrent du precedent (ou si il s'agit du premier element de la base)
				if ((modalite.equals(infoTweet.getT_jour()) == false || modalite.equals(""))){
					//on ajoute un element a la liste droulante
					choiceBox.getItems().add(infoTweet.getT_jour());
					//la pivot change 
					modalite = infoTweet.getT_jour();
				}

			}
		}

		//on initialise la valeur de cette liste deroulante a "Aucun"
		choiceBox.setValue("Aucun");
		return choiceBox;
	}

	/*fonction qui permet de creer dynamiquement un titre a un graphique en fonction du filtre applique*/
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
		// Le couple modalite compteur correspond a un jour (ou heure) accompagne de son nombre de tweet
		Integer compteur = 0;
		String modalite = "";
		//on appel la fontion qui permet de creer le titre
		creer_titre(bdnt);
		/*si il y a un filtre sur les jours */
		if (f_jour.equals("Aucun") == false) {
			// Boucle sur les element de la base

			while (it.hasNext()) {
				//l'element de la base est stocke dans un objet de type tweet
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
					//si l'heure du nouveau tweet est identique a celle du tweet precedent
					// ou si il s'agit du premier tweet

					if (modalite.equals(heure) || modalite.equals("")) {
						//on incremente de 1 le compteur correspondant a la valeur de la modalite
						compteur +=1;
						//la variable modalite prend la valeur de la nouvelle heure
						modalite = heure;
					} else {
						// On cree un objet nombreTweet
						nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
						//on ajoute l'objet a la base de nombreTweet
						bdnt.ajouteNombreTweet(donnee_barchart);
						//la variable modalite prend la valeur de la nouvelle heure
						modalite = heure;
						//on reinitialise le compteur de modalite a 1
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
						//si le jour du nouveau tweet est identique a celle du tweet precedent
						// ou si il s'agit du premier tweet
						if (modalite.equals(infoTweet.getT_jour()) || modalite.equals("")) {
							//on incremente la valeur 
							compteur +=1;
							modalite = infoTweet.getT_jour();
						} else {
							//on cre un objet nombre tweet
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
						//l'element du treeset est stocke dans un objet de type tweet
						tweet infoTweet = (tweet)it.next();
						// On test si le tweet correspond aux conditions de tri (mois)
						if (f_mois.equals(infoTweet.getT_mois())){
							/*la base est trie par ordre chronologique 
							 * donc si on observe un changement entre deux valeur
							 * on cree une nouvelle modalite
							 */
							//si le jour du nouveau tweet est identique a celui du tweet precedent
							// ou si il s'agit du premier tweet
							if (modalite.equals(infoTweet.getT_jour()) || modalite.equals("")) {
								//on incremente de 1 le compteur correspondant a la valeur de la modalite
								compteur +=1;
								//la variable modalite prend la valeur du nouveau jour
								modalite = infoTweet.getT_jour();
							} else {
								// On cree un objet nombreTweet
								nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
								//on ajoute l'objet a la base de nombreTweet
								bdnt.ajouteNombreTweet(donnee_barchart);
								//la variable modalite prend la valeur de la nouvelle heure
								modalite = infoTweet.getT_jour();
								//on reinitialise le compteur de modalite a 1
								compteur = 1;
							}
						}
					}

				} else {
					/*si il n'y a pas de filtres */
					// Boucle sur les element du treeset
					while (it.hasNext()) {
						//l'element du treeset est stocke dans un objet de type tweet
						tweet infoTweet = (tweet)it.next();
						/*la base est trie par ordre chronologique 
						 * donc si on observe un changement entre deux valeur
						 * on cree une nouvelle modalite
						 */
						//si le jour du nouveau tweet est identique a celui du tweet precedent
						// ou si il s'agit du premier tweet

						if (modalite.equals(infoTweet.getT_jour()) || modalite.equals("")) {
							//on incremente de 1 le compteur correspondant a la valeur de la modalite
							compteur +=1;
							//la variable modalite prend la valeur du nouveau jour
							modalite = infoTweet.getT_jour();
						} else {
							// On cree un objet nombreTweet
							nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
							//on ajoute l'objet a la base de nombreTweet
							bdnt.ajouteNombreTweet(donnee_barchart);
							compteur =1;
							//la variable modalite prend la valeur de la nouvelle heure
							modalite = infoTweet.getT_jour();
							//on reinitialise le compteur de modalite a 1

						}
					}
				}
			}
		}
		//on ajoute le dernier element car on est sorti de la boucle sans l'avoir ajoute
		nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
		bdnt.ajouteNombreTweet(donnee_barchart);
		return bdnt;
	}

	/* fonction qui permet d'ajouter les donnes necessaire a la constructon du tableau des utilisateurs*/
	/* La chaine choix_tri est un indicateur de la maniere dont le tableau va etre trier */
	public BaseDeUtilisateurs creer_donnee_tableau_utilisateurs(String choix_tri) {
		//objet renvoye par la fonction
		BaseDeUtilisateurs bdu = new BaseDeUtilisateurs();
		//pseudo de l(utilisateur courrant
		String pseudo_users = "";
		//donnees de l'utilisateur courrant
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
					//on cree notre objet utilisateur
					user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
					//on ajoute l'utilisateur a la base
					bdu.ajouteUtilisateur(user);
					pseudo_users = infoTweet.getT_pseudo_users();
					//on reinitialise le compteur
					compteur_nombre_tweet = 1;
				}
				
			}
			//on ajoute le dernier element car on est sorti de la boucle sans l'avoir ajoute
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
						//on incremente le compteur
						compteur_nombre_tweet +=1;
						pseudo_users = infoTweet.getT_pseudo_retweet();
					} else {

						user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
						//on cree notre objet utilisateur
						bdu.ajouteUtilisateur(user);
						//on ajoute l'utilisateur a la base
						pseudo_users = infoTweet.getT_pseudo_retweet();
						//on reinitialise le compteur
						compteur_nombre_tweet = 1;
					}
				}
			}
			//on ajoute le dernier element car on est sorti de la boucle sans l'avoir ajoute
			user = new utilisateur(pseudo_users,compteur_nombre_tweet,compteur_nombre_mention, compteur_nombre_retweet);
			bdu.ajouteUtilisateur(user);	
			bdu.tri_utilisateur_nombreTweet();
			return bdu;

		default :
			return bdu;
		}


	}
	
	//cette methode renvoie la liste de tous les hashtags tries et avec doublons
	//cette liste nous servira a  remplir la base de hashtags
	public ArrayList<String> intermediaire_rempli_bdh() {
		Integer cptlength = 0 ;
		
		// on declare un tableau avec tous les caracteres qui terminent un hashtag (ex : #FIFA. , #FIFA: -> les derniers caracteres ne comptent pas dans le hashtag)
		String tab_charFinTweet[ ];
		tab_charFinTweet = new String[] {" ",";",".",",","?","!",":","/","-"};
		// creation de la liste qui renverra a  la fin de la fonction
		ArrayList<String> list_hashtag = new ArrayList<String>();
		
		//creation d'un iterateur pour parcourir toute la base de tweet
		Iterator  iterator=maCollec.iterator();
		while (iterator.hasNext())
		{
			//on stock le tweet actuel dans la variable t
			tweet t = (tweet)iterator.next();
			//on recupere le contenu du tweet
			String contenu = t.getT_contenu();
			// creation d'une variable hashtag qui stockera les hashtags
			String hashtag="";
			//deb servira a  identifier la position de debut du hashtag
			int deb=0;
			// le caractere a  trouver pour identifier un hashtag est '#'
			String c="#";
			// on recupere le nombre de hashtag dans le tweet
			int nbhashtag= t.getT_nb_hashtag();
			
			//on boucle sur le nombre de hashtag du tweet pour recuperer chaque hashtag
			for(int j = 0; j <= nbhashtag; j++) {
				
				
				int min = contenu.length();
				//on recupere la position du premier #
				deb = contenu.indexOf(c,deb);
				int fin=0;
				
				// si deb est different de -1 ca veut dire qu'il existe un hashtag
				if(deb != -1) {
					
					// on parcout le tableau des elements qui finissent les tweets pour trouver le caractere qui termine le hashtag
					for (int k = 0; k < tab_charFinTweet.length ;k++) {
						if (contenu.indexOf(tab_charFinTweet[k],deb) != -1 && contenu.indexOf(tab_charFinTweet[k],deb) < min) {
							// on recupere la position la plusproche du debut
							min = contenu.indexOf(tab_charFinTweet[k],deb);
						}
					}
					//fin correspond a  la position ou s'arrete le hashtag
					fin = min;
					cptlength +=1;
					
					// si fin est different de -1 c'est qu'il reste encore du contenu apres le hashtag
					if(fin != -1) {
						
						//extraction du hashtag
						hashtag=contenu.substring(deb,fin);
						//correction d'un bug 
						hashtag = hashtag.replace(",", "");
						
						// si le hashtag contient quelque chose on l'ajoute sinon on ne l'ajoute pas
						// certaines personnes ont juste tweete "blabbla # blabla", on ne considere pas que la personne est utilise un hashtag
						if ((hashtag != " ") && (hashtag != " # ")) {
							list_hashtag.add(hashtag);
						}
						// la position de fin devient la position de debut
						deb=fin;
					}else {
						
						// si fin =-1 cela signifie que la hashtag se termine a  la fin du contenu
						hashtag=contenu.substring(deb,contenu.length()-1);
						hashtag = hashtag.replace(",", "");
						// les conditions sont les maªmes qu'au dessus
						if ((hashtag != " ") && (hashtag != " # ")) {		
							list_hashtag.add(hashtag);
						}	
						break;
					}
				}else break;
			}
		}
		// on tri ensuite notre liste gra¢ce au Collections.sort
		Collections.sort(list_hashtag);
		return list_hashtag;
	}
	
	
	// Cette methode va nous servir a  remplir la base de hashtag, elle prend en entree la liste des hashtags triee avec doublons
	public BaseDeHashtag creer_donnee_tableau_hashtag(ArrayList<String> list_hashtag) {
		//on stocke la longueur de la liste dans un integer
		int long_list =list_hashtag.size();
		//on declare la base de hashtags 
		BaseDeHashtag bdh = new BaseDeHashtag();
		//la variable cpt nous servira a  compter le nombre de fois que chaque hashtags apparaa®t
		int cpt=1;
		//on recupere la valeur du premier hashtag de la liste 
		String h_comp= list_hashtag.get(0);
		//le principe est de parcourir la liste, de comparer chaque hashtag avec celui du dessus
		//si les hashtags sont egaux, on incremente le cpt
		//si il est different alors on recupere la valeur du cpt et on ajoute la hashtag dans la base de hashtag avec le nombre de fois qu'il a ete cite (cpt)
		for (int i=1; i <long_list; i++) {
			
			//si il y a egalite entre le hashtag actuel et celui du dessus, on incremente le cpt
			if(h_comp.equals(list_hashtag.get(i))) {
				cpt=cpt+1;
			}else {
				
				//sinon on creer le hashtag avec sa valeur et le nombre de fois qu'il a ete cite
				hashtag h= new hashtag(h_comp, cpt);
				
				//comme vu dans la methode d'avant, certains hashtags extraient n'en sont pas reellement, on rajoute donc une condition
				if ((h.getH_libele() !=" #")&&(h.getH_libele() !=" ,")) {
					//ajout du hashtag a  la base de hashtag
					bdh.ajouteHashtag(h);
					
					//on reinitialise le cpt a  1
					cpt=1;
					//on initialise la valeur du hashtag a  comparer
					h_comp=list_hashtag.get(i);
				}

			}
		}
		
		//tri des hashtags en fonction du nombre d'occurences (decroissant)
		bdh.tri_hashtag_occurence();
		//on retourne la base de hashtags
		return bdh;
	}
	
	/*
	 * Cette fonction n'a pas ete utilise car elle aurait rendu
	 * la complexite du programme quadratique. Et nous n'avons pas trouve de moyen 
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

	// cette fonction a beaucoup servit pour effectuer des verifications lors de l'importation
	public void explore(int i) {
		Iterator  iterator=maCollec.iterator();
		// on parcourt la collection et on print chaque tweet
		while (iterator.hasNext())
		{
			System.out.println("tweet numero "+ i + " :" + iterator.next());

			i=i+1;
		}
	}

	//Nous avons trouve cette fonction sur internet
	//lien : https://www.journaldunet.fr/web-tech/developpement/1203045-java-comment-compter-le-nombre-d-occurrences-de-caractere-dans-une-chaine-string/
	public static int compterOccurrences(String maChaine, char recherche)
	{
		//initialisation du compteur "nb"
		int nb = 0;
		//on parcourt la chaa®ne 
		for (int i=0; i < maChaine.length(); i++)
		{
			if (maChaine.charAt(i) == recherche)
				nb++;
		}
		return nb;
	}
	

	//programme d'import qui prend en entree le nom du fichier a  importer
	public void importation(String fic) throws IOException {
		@SuppressWarnings("resource")
		
		//Initialisation du BufferedReader
		BufferedReader reader = new BufferedReader(new FileReader("resources/"+fic));
		//initialisation d'une variable qui recuperera la ligne lu
		String ligne;
		
		//tant que le parcours des lignes n'est pas fini
		while((ligne = reader.readLine()) != null){
			
			//on split la ligne en fonction des tabulations
			String[] sepligne = ligne.split("\t");
			
			//on attribu chaque case de sepligne a  une variable
			String pseudo_users = sepligne[1];
			String date_heure = sepligne[2];
			String contenu = sepligne[3];
			// on initialise des variables qu'on souhaite creer
			String lien_dans_contenu="";
			String pseudo_mentionne="";
			int nb_hashtag=0;
			int nb_pseudo_mentionne=0;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			//on separe la date et l'heure
			String[] sep_date_heure = date_heure.split(" ");

			String heure = sep_date_heure[1];
			LocalDate date = LocalDate.parse(sep_date_heure[0], formatter);

			//cette variable sert uniquement a separer les jours mois et annee
			String date_string = sep_date_heure[0];
			// on split la date pour recuperer jour/mois/annee
			String[] sepdate_j = date_string.split("-");
			String annee = sepdate_j[0];
			
			//on stocke le nom du mois qu'on recupere gra¢ce a  la fonction trouvemois
			String mois = trouvemois(date);
			//String jourSemaine_t = sepdate_j[];
			String jour = sepdate_j[2];
			//on stocke le nom du jour qu'on recupere gra¢ce a  la fonction trouvejour
			String jour_semaine= trouvejour(date);
			//conversion de la variable jour en integer pour utiliser "trouvenomsemaine"
			int l = Integer.parseInt(jour);
			//on stocke le nom de la semaine qu'on recupere gra¢ce a  la fonction trouvenomsemaine
			String semaine=trouvenomsemaine(date,jour_semaine,l);
			
			//on test si il existe un hashtag dans le contenu
			boolean exist = contenu.contains("#");
			// si il y a des hashtags on utilise la fonction compterOccurrences pour compter le nombre de hashtags du tweets
			if(exist==true) {
				char c= '#';
				int nb = compterOccurrences(contenu, c);
				//int deb=0;
				nb_hashtag=nb;
			}
			
			//on test si il existe un pseudo mentionne dans le contenu
			boolean existb = contenu.contains("@");
			if(existb==true) {
				
				//similaire au code du dessus
				char c= '@';
				int nb = compterOccurrences(contenu, c);
				int deb=0;
				nb_pseudo_mentionne=nb;
				
				// on boucle sur le nombre de pseudo mentionnes dans le contenu
				for(int j = 1; j <= nb; j++) {
					//on recupere la position du premier @
					deb = contenu.indexOf(c,deb);
					//si deb != -1 alors il existe au moins un @
					if(deb != -1) {
						// on recupere la position de fin du pseudo mentionne
						int fin = contenu.indexOf(" ",deb);
						if(fin != -1) {
							//si fin est !=-1 on extrait le pseudo qui se situe entre debut et fin 
							//on concatene tous les pseudos mentionnes
							pseudo_mentionne=pseudo_mentionne+contenu.substring(deb,fin);
						}else {
							//sinon on extrait de la position du debut du pseudo mentionne a  la fin du tweet
							pseudo_mentionne=pseudo_mentionne+contenu.substring(deb,contenu.length()-1);
						}
						if(fin != -1) {
							deb=fin;
						}else break;	
					}else break;	
				}
			}else {
				//sinon c'est qu'il n'y a pas de pseudos mentionnes
				pseudo_mentionne="NA";
			}
			
			//code similaire que ceux ci-dessus
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
				// si il existe un pseudo retweet alors on affecte la valeur a  pseudo_retweet
				String pseudo_retweet = sepligne[4];
				//on considere que si c'est un retweet alors il n'y a ni hashtag ni pseudo mentionne 
				pseudo_mentionne="NA";
				nb_hashtag=0;
				nb_pseudo_mentionne=0;
				//instanciation de tweet
				tweet t = new tweet(pseudo_users,date,heure,contenu,lien_dans_contenu,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne,annee,mois,jour,jour_semaine,semaine);
				//ajout a  la collection 
				maCollec.add(t);

			} catch (Exception ex){
				//sinon, ce n'est pas un retweet on affecte "NA" au pseudo_retweet
				String pseudo_retweet = "NA";

				tweet t = new tweet(pseudo_users,date,heure,contenu,lien_dans_contenu,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne,annee,mois,jour,jour_semaine,semaine);

				maCollec.add(t);
			}
		}

	}

}
