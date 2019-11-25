import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Set;

public class tweet {
	//ceci est un commentaire
	//commentaire charlie
	//encore un commentaire
	protected String pseudo_users="";
	protected String date="";
	protected String heure="";
	protected String tweet="";
	protected String lien_dans_tweet="";
	protected String hashtag="";
	protected String pseudo_mentionne="";
	protected String pseudo_retweet="";
	
	//protected LocalDate date = LocalDate.now();
	//private String date="";


	public tweet(String pseudo_users, String date, String heure, String tweet, String lien_dans_tweet, String hashtag,
			String pseudo_mentionne, String pseudo_retweet) {
		super();
		this.pseudo_users = pseudo_users;
		this.date = date;
		this.heure = heure;
		this.tweet = tweet;
		this.lien_dans_tweet = lien_dans_tweet;
		this.hashtag = hashtag;
		this.pseudo_mentionne = pseudo_mentionne;
		this.pseudo_retweet = pseudo_retweet;
	}

	public String getPseudo_users() {
		return pseudo_users;
	}

	public void setPseudo_users(String pseudo_users) {
		this.pseudo_users = pseudo_users;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public String getLien_dans_tweet() {
		return lien_dans_tweet;
	}

	public void setLien_dans_tweet(String lien_dans_tweet) {
		this.lien_dans_tweet = lien_dans_tweet;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public String getPseudo_mentionne() {
		return pseudo_mentionne;
	}

	public void setPseudo_mentionne(String pseudo_mentionne) {
		this.pseudo_mentionne = pseudo_mentionne;
	}

	public String getPseudo_retweet() {
		return pseudo_retweet;
	}

	public void setPseudo_retweet(String pseudo_retweet) {
		this.pseudo_retweet = pseudo_retweet;
	}

	@Override
	public String toString() {
		return "tweet [pseudo_users=" + pseudo_users + ", date=" + date + ", heure=" + heure + ", tweet=" + tweet
				+ ", lien_dans_tweet=" + lien_dans_tweet + ", hashtag=" + hashtag + ", pseudo_mentionne="
				+ pseudo_mentionne + ", pseudo_retweet=" + pseudo_retweet + "]";
	}



	/*
	public News(String t,String a,String s, String d) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String date;
		this.titre=t;
		this.auteur=a;
		this.source=s;
		date=d;

		try {
			this.date = LocalDate.parse(date, formatter);
		} catch (DateTimeParseException e) { System.out.println( "erreur, la date sera celle du  jour"); };


	}
	
*/


}






