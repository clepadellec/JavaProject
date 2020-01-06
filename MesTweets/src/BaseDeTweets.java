import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.Serializable;

public class BaseDeTweets{

	public TreeSet<tweet> maCollec;
	public String f_mois = "" ;
	public String f_semaine = "" ;
	public String f_jour = "" ;
	
	public void initialise() {
		maCollec = new TreeSet<tweet>();
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
	
	//fonction qui permet de gerer les filtres a appliquer syr le barchart
	public BaseDeNombreTweet creer_donnee_barchart() {
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
				if (f_jour.equals(infoTweet.getJour_t()) && f_mois.equals(infoTweet.getMois_t())) {
					//On réccupère l'heure du tweet
					String[] split_h = infoTweet.getHeure().split(":");
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
			/*
			if (f_semaine.equals("") == false) {
				
				while (it.hasNext()) {
					tweet infoTweet = (tweet)it.next();
					if (f_mois.equals(infoTweet.getSemaine_t())){
						if (modalite.equals(infoTweet.getJour_t()) || modalite.equals("")) {
							compteur +=1;
							modalite = infoTweet.getJour_t();
						} else {
							nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
							System.out.println(infoTweet.getJour_t() + " != " + modalite + " : " + compteur);
							bdnt.ajoute(donnee_barchart);
							modalite = infoTweet.getJour_t();
							compteur = 1;
						}
					}
				}
			} else {
			*/	
				/*si il y a un filtre sur les mois */
				if (f_mois.equals("") == false) {
					// Boucle sur les element du treeset
					while (it.hasNext()) {
						//l'element du treeset est stocké dans un objet de type tweet
						tweet infoTweet = (tweet)it.next();
						// On test si le tweet correspond aux conditions de tri (mois)
						if (f_mois.equals(infoTweet.getMois_t())){
							/*le treeset est trié par ordre chronologique 
							 * donc si on observe un changement entre deux valeur
							 * on créé une nouvelle modalité
							 */
							//si le jour du nouveau tweet est identique a celui du tweet précédent
							// ou si il s'agit du premier tweet
							if (modalite.equals(infoTweet.getJour_t()) || modalite.equals("")) {
								//on incrémente de 1 le compteur correspondant a la valeur de la modalité
								compteur +=1;
								//la variable modalité prend la valeur du nouveau jour
								modalite = infoTweet.getJour_t();
							} else {
								// On créé un objet nombreTweet
								nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
								//on ajoute l'objet a la base de nombreTweet
								bdnt.ajouteNombreTweet(donnee_barchart);
								//la variable modalité prend la valeur de la nouvelle heure
								modalite = infoTweet.getJour_t();
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
						
						if (modalite.equals(infoTweet.getJour_t()) || modalite.equals("")) {
							//on incrémente de 1 le compteur correspondant a la valeur de la modalité
							compteur +=1;
							//la variable modalité prend la valeur du nouveau jour
							modalite = infoTweet.getJour_t();
						} else {
							// On créé un objet nombreTweet
							nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
							//on ajoute l'objet a la base de nombreTweet
							bdnt.ajouteNombreTweet(donnee_barchart);
							//la variable modalité prend la valeur de la nouvelle heure
							modalite = infoTweet.getJour_t();
							//on reinitialise le compteur de modalité a 1
							compteur = 1;
						}
						
					}
				}
			//}
		}
	
		nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
		bdnt.ajouteNombreTweet(donnee_barchart);
		return bdnt;
	}
	
	public BaseDeUtilisateurs creer_donnee_tableau() {
		BaseDeUtilisateurs bdu = new BaseDeUtilisateurs();
		String pseudo_users = "";
		Integer compteur_tweet = 0;
		Iterator  it=maCollec.iterator();
		
		while (it.hasNext()) {
			tweet infoTweet = (tweet)it.next();
			//System.out.println(infoTweet.getPseudo_users());
			if (pseudo_users.equals(infoTweet.getPseudo_users()) || pseudo_users.equals("")){
				//System.out.println(infoTweet.getPseudo_users());
				compteur_tweet +=1;
				pseudo_users = infoTweet.getPseudo_users();
			} else {

				System.out.println(infoTweet.getPseudo_users() + " : " +  compteur_tweet );
				utilisateur user = new utilisateur(pseudo_users,compteur_tweet,5);
				bdu.ajouteUtilisateur(user);
				pseudo_users = infoTweet.getPseudo_users();
				compteur_tweet = 1;
			}
			//l'element du treeset est stocké dans un objet de type tweet
		
		}
		utilisateur user = new utilisateur(pseudo_users,compteur_tweet,5);
		bdu.ajouteUtilisateur(user);		
		return bdu;
	}
	

	
	public void explore(int i) {
		Iterator  iterator=maCollec.iterator();
		
		while (iterator.hasNext())
		{
			System.out.println("tweet n° "+ i + " :" + iterator.next());
			i=i+1;
		}
	}
	
	public static int compterOccurrences(String maChaine, char recherche) // pompé internet
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
		//commit
		BufferedReader reader = new BufferedReader(new FileReader("resources/"+fic));
		String ligne;
		int i=1;
		while((ligne = reader.readLine()) != null){
			String[] sepligne = ligne.split("\t");
			String pseudo_u = sepligne[1];
			String date = sepligne[2];
			String tweet = sepligne[3];
			String lien_dans_tweet="";
			String hashtag="";
			String pseudo_mentionne="";
			int nb_hashtag=0;
			int nb_pseudo_mentionne=0;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String[] sepdate_h = date.split(" ");
			String heure_t = sepdate_h[1];
			LocalDate date_t = LocalDate.parse(sepdate_h[0], formatter);
			//cette variable sert uniquement a séparer les jours mois et annee
			String date_string = sepdate_h[0];
			String[] sepdate_j = date_string.split("-");
			String annee_t = sepdate_j[0];
			String mois_t = sepdate_j[1];
			//String jourSemaine_t = sepdate_j[];
			String jour_t = sepdate_j[2];
			
			boolean exist = tweet.contains("#");
			if(exist==true) {
				char c= '#';
				int nb = compterOccurrences(tweet, c);
				int deb=0;
				nb_hashtag=nb;
				for(int j = 1; j <= nb; j++) {
					deb = tweet.indexOf(c,deb);
					if(deb != -1) {
						int fin = tweet.indexOf(" ",deb);
						if(fin != -1) {
							hashtag=hashtag+tweet.substring(deb,fin);
						}else {
							hashtag=hashtag+tweet.substring(deb,tweet.length()-1);
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

			boolean existb = tweet.contains("@");
			if(existb==true) {
				char c= '@';
				int nb = compterOccurrences(tweet, c);
				int deb=0;
				nb_pseudo_mentionne=nb;
				for(int j = 1; j <= nb; j++) {
					deb = tweet.indexOf(c,deb);
					if(deb != -1) {
						int fin = tweet.indexOf(" ",deb);
						if(fin != -1) {
							pseudo_mentionne=pseudo_mentionne+tweet.substring(deb,fin);
						}else {
							pseudo_mentionne=pseudo_mentionne+tweet.substring(deb,tweet.length()-1);
						}
						if(fin != -1) {
						deb=fin;
						}else break;	
					}else break;	
				}
			}else {
				pseudo_mentionne="NA";
			}

			boolean existc = tweet.contains("https://");
			if(existc==true) {
				String c="https://";
				int deb = tweet.indexOf(c);
				int fin = tweet.indexOf(" ",deb);
				if(fin != -1) {
					lien_dans_tweet=tweet.substring(deb,fin);
				}else {
					lien_dans_tweet=tweet.substring(deb,tweet.length()-1);
				}


			}else {
				lien_dans_tweet="non";
			}

			
			try {
				String pseudo_retweet = sepligne[4];
				tweet t = new tweet(pseudo_u,date_t,heure_t,tweet,lien_dans_tweet,hashtag,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne,annee_t,mois_t,jour_t);
				//System.out.println("tweet nÂ°"+i+" :"+t+"\n");
				i=i+1;
				maCollec.add(t);

			} catch (Exception ex){
				String pseudo_retweet = "aucun pseudo rt";
				tweet t = new tweet(pseudo_u,date_t,heure_t,tweet,lien_dans_tweet,hashtag,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne,annee_t,mois_t,jour_t);
				//System.out.println("tweet nÂ°"+i+" :"+t+"\n");
				i=i+1;
				maCollec.add(t);
				//System.out.println("pas de retweet");
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void ouvrir(int i) throws Exception {
		try {
			FileInputStream w = new FileInputStream("resources/data.dat");
			ObjectInputStream o = new ObjectInputStream(w);
			Object lu =o.readObject();
			maCollec = (TreeSet<tweet>)lu;
			w.close();
			o.close();
			System.out.println("nb de tweets"+i);
			i=i+1;

		} catch (IOException e) {
			System.out.println("erreur d'IO");
			i=i+1;
			System.out.println("nb de tweets"+i);
		}


	}

	public void enregistrer() throws Exception {
		try {
			FileOutputStream w = new FileOutputStream("resources/data.dat");
			ObjectOutputStream o = new ObjectOutputStream(w);
			o.writeObject(maCollec);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException{
		out.defaultWriteObject();
	}
	private void readObject(java.io.ObjectInputStream out) throws IOException, ClassNotFoundException{
		out.defaultReadObject();
	}
	//a completer
}
