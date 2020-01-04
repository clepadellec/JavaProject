import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
		Iterator  it=maCollec.iterator();
		BaseDeNombreTweet bdnt = new BaseDeNombreTweet();
		Integer compteur = 0;
		String modalite = "";
		
		/*si il y a un filtre sur les jours */
		if (f_jour.equals("") == false) {
				
			while (it.hasNext()) {
				tweet infoTweet = (tweet)it.next();
	
				if (f_jour.equals(infoTweet.getJour_t()) && f_mois.equals(infoTweet.getMois_t())) {
				
					String[] split_h = infoTweet.getHeure().split(":");
					String heure = split_h[0];
					
					if (modalite.equals(heure) || modalite.equals("")) {
						compteur +=1;
						modalite = heure;
					} else {
						nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
						System.out.println(heure + " != " + modalite + " : " + compteur);
						bdnt.ajoute(donnee_barchart);
						modalite = heure;
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
					
					while (it.hasNext()) {
						tweet infoTweet = (tweet)it.next();
						
						if (f_mois.equals(infoTweet.getMois_t())){
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
					/*si il n'y a pas de filtres */
					while (it.hasNext()) {
						tweet infoTweet = (tweet)it.next();
						
						if (f_mois.equals(infoTweet.getMois_t())){
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
				}
			//}
		}
	
		nombreTweet donnee_barchart = new nombreTweet(modalite, compteur);
		bdnt.ajoute(donnee_barchart);
		return bdnt;
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
				System.out.println("tweet nÂ°"+i+" :"+t+"\n");
				i=i+1;
				maCollec.add(t);

			} catch (Exception ex){
				String pseudo_retweet = "aucun pseudo rt";
				tweet t = new tweet(pseudo_u,date_t,heure_t,tweet,lien_dans_tweet,hashtag,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne,annee_t,mois_t,jour_t);
				System.out.println("tweet nÂ°"+i+" :"+t+"\n");
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
