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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.Serializable;

public class BaseDeTweets{

//test charlie 
	public ArrayList<tweet> maCollec;
	//public ArrayList<tweet> maCollec;

	public void initialise() {
		maCollec = new ArrayList<tweet>();
	}

	public void ajoute(tweet t) {
		maCollec.add(t);
	}
	
	public void moisDate() {
		//Iterator  it=maCollec.iterator();
		//Integer i = 0;
		//Integer cpt = 0;
		//while (it.hasNext() && cpt < 100)
		//{
		System.out.println(maCollec.size());
		
		//for (int i=0; i<maCollec.size(); i++){
		//	tweet t = (tweet)(maCollec.get(i));
			//System.out.println(t.getDate().getMonth());
			//System.out.println(it.next());
			//cpt +=1;
		//}
	}
	
	
	
	public void explore(int i) {
		Iterator  iterator=maCollec.iterator();
		//Integer cpt = 0;
		while (iterator.hasNext())
		{
			System.out.println("tweet n° "+ i + " :"+iterator.next());
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
			String[] sepdate = date.split(" ");
			String heure_t = sepdate[1];
			LocalDate date_t = LocalDate.parse(sepdate[0], formatter);
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
				tweet t = new tweet(pseudo_u,date_t,heure_t,tweet,lien_dans_tweet,hashtag,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne);
				System.out.println("tweet nÂ°"+i+" :"+t+"\n");
				i=i+1;
				maCollec.add(t);

			} catch (Exception ex){
				String pseudo_retweet = "aucun pseudo rt";
				tweet t = new tweet(pseudo_u,date_t,heure_t,tweet,lien_dans_tweet,hashtag,pseudo_mentionne,pseudo_retweet,nb_hashtag,nb_pseudo_mentionne);
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
			maCollec = (ArrayList<tweet>)lu;
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
