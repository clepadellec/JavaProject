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
import java.util.Iterator;
import java.util.Scanner;
import java.io.Serializable;
public class BaseDeTweets{


	public TreeSet<tweet> maCollec;

	public void initialise() {
		maCollec = new TreeSet<tweet>();
	}

	public void ajoute(tweet t) {
		maCollec.add(t);
	}

	public void explore() {
		Iterator  iterator=maCollec.iterator();
		Integer i=1;

		while (iterator.hasNext())
		{
			System.out.println("tweet n°" +i+"- "+iterator.next());
			i=i+1;
		}
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
		maCollec = new TreeSet<tweet>();
		while((ligne = reader.readLine()) != null){
			String[] sepligne = ligne.split("\t");
			String pseudo_u = sepligne[1];
			String date = sepligne[2];
			String tweet = sepligne[3];
			String lien_dans_tweet="";
			String hashtag="";
			String pseudo_mentionne="";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String[] sepdate = date.split(" ");
			String heure_t = sepdate[1];
			LocalDate date_t = LocalDate.parse(sepdate[0], formatter);
			boolean exist = tweet.contains("#");
			if(exist==true) {
				String c="#";
				int deb = tweet.indexOf(c);
				int fin = tweet.indexOf(" ",deb);
				System.out.println(fin);
				if(fin != -1) {
					hashtag=tweet.substring(deb,fin);
				}else {
					hashtag=tweet.substring(deb,tweet.length()-1);
				}

			}else {
				hashtag="NA";
			}
			boolean existb = tweet.contains("@");
			if(existb==true) {
				String c="@";
				int deb = tweet.indexOf(c);
				int fin = tweet.indexOf(" ",deb);
				System.out.println(fin);
				if(fin != -1) {
					pseudo_mentionne=tweet.substring(deb,fin);
				}else {
					pseudo_mentionne=tweet.substring(deb,tweet.length()-1);
				}

			}else {
				pseudo_mentionne="NA";
			}

			boolean existc = tweet.contains("https://");
			if(existc==true) {
				String c="https://";
				int deb = tweet.indexOf(c);
				int fin = tweet.indexOf(" ",deb);
				System.out.println(fin);
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
				tweet t = new tweet(pseudo_u,date_t,heure_t,tweet,lien_dans_tweet,hashtag,pseudo_mentionne,pseudo_retweet);
				System.out.println("tweet n°"+i+" :"+t+"\n");
				i=i+1;
				maCollec.add(t);

			} catch (Exception ex){
				String pseudo_retweet = "aucun pseudo rt";
				tweet t = new tweet(pseudo_u,date_t,heure_t,tweet,lien_dans_tweet,hashtag,pseudo_mentionne,pseudo_retweet);
				System.out.println("tweet n°"+i+" :"+t+"\n");
				i=i+1;
				maCollec.add(t);
				System.out.println("pas de retweet");
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
