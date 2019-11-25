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
import java.util.Iterator;
import java.util.Scanner;

public class BaseDeTweets {
	

	public TreeSet<tweet> maCollec;

	public void initialise() {
		maCollec = new TreeSet<tweet>();
	}

	public void ajoute(tweet n) {
		maCollec.add(n);
	}

	public void explore() {
		Iterator  iterator=maCollec.iterator();
		Integer i=1;

		while (iterator.hasNext())
		{
			System.out.println("actu n°" +i+"- "+iterator.next());
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
		BufferedReader reader = new BufferedReader(new FileReader("resources/"+fic));
		String ligne;
		while((ligne = reader.readLine()) != null){
			String[] sepligne = ligne.split("\t");
			String pseudo_u = sepligne[1];
			String date_t = sepligne[2];
			String heure_t = "a venir:";
			String tweet = sepligne[3];
			String lien_dans_tweet="";
			String hashtag="";
			String pseudo_mentionne="";

			try {
				String pseudo_retweet = sepligne[4];
				tweet t = new tweet(pseudo_u,date_t,heure_t,tweet,lien_dans_tweet,hashtag,pseudo_mentionne,pseudo_retweet);
				System.out.println("tweet :"+t+"\n");
				//maCollec.add(t);

			} catch (Exception ex){
				String pseudo_retweet = "NA";
				tweet t = new tweet(pseudo_u,date_t,heure_t,tweet,lien_dans_tweet,hashtag,pseudo_mentionne,pseudo_retweet);
				System.out.println("tweet :"+t+"\n");
				//maCollec.add(t);
				//System.err.println("Error. "+ex.getMessage());
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void ouvrir() throws Exception {
		try {
			FileInputStream w = new FileInputStream("D:\\data.dat");
			ObjectInputStream o = new ObjectInputStream(w);
			Object lu =o.readObject();
			maCollec = (TreeSet<tweet>)lu;
			w.close();
			o.close();


		} catch (IOException e) {
			System.out.println("erreur d'IO");
		}

	}

	public void enregistrer() throws Exception {
		try {
			FileOutputStream w = new FileOutputStream("D:\\data.dat");
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
