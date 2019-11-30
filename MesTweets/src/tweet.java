import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Set;

@SuppressWarnings("serial")
public class tweet implements Comparable<tweet>, Serializable {

	protected String t_pseudo_users="";
	protected LocalDate t_date;
	protected String t_heure="";
	protected String t_contenu="";
	protected String t_lien_dans_contenu="";
	protected String t_hashtag="";
	protected String t_pseudo_mentionne="";
	protected String t_pseudo_retweet="";

	//protected LocalDate date = LocalDate.now();
	//private String date="";


	public tweet(String pseudo_users, LocalDate date, String heure, String contenu, String lien_dans_contenu, String hashtag,
			String pseudo_mentionne, String pseudo_retweet) {
		super();
		this.t_pseudo_users = pseudo_users;
		this.t_date = date;
		this.t_heure = heure;
		this.t_contenu = contenu;
		this.t_lien_dans_contenu = lien_dans_contenu;
		this.t_hashtag = hashtag;
		this.t_pseudo_mentionne = pseudo_mentionne;
		this.t_pseudo_retweet = pseudo_retweet;
	}
	
	public int compareTo(tweet arg0) {
		// TODO Auto-generated method stub

		String titre1 = this.getPseudo_users();
		String titre2 = arg0.getPseudo_users();


		return titre1.compareTo(titre2);
	}

	public String getPseudo_users() {
		return t_pseudo_users;
	}

	public void setPseudo_users(String pseudo_users) {
		this.t_pseudo_users = pseudo_users;
	}

	public LocalDate getDate() {
		return t_date;
	}

	public void setDate(LocalDate date) {
		this.t_date = date;
	}

	public String getHeure() {
		return t_heure;
	}

	public void setHeure(String heure) {
		this.t_heure = heure;
	}

	public String getcontenu() {
		return t_contenu;
	}

	public void setcontenu(String contenu) {
		this.t_contenu = contenu;
	}

	public String getLien_dans_contenu() {
		return t_lien_dans_contenu;
	}

	public void setLien_dans_contenu(String lien_dans_contenu) {
		this.t_lien_dans_contenu = lien_dans_contenu;
	}

	public String getHashtag() {
		return t_hashtag;
	}

	public void setHashtag(String hashtag) {
		this.t_hashtag = hashtag;
	}

	public String getPseudo_mentionne() {
		return t_pseudo_mentionne;
	}

	public void setPseudo_mentionne(String pseudo_mentionne) {
		this.t_pseudo_mentionne = pseudo_mentionne;
	}

	public String getpseudo_retweet() {
		return t_pseudo_retweet;
	}

	public void setpseudo_retweet(String pseudo_retweet) {
		this.t_pseudo_retweet = pseudo_retweet;
	}

	@Override
	public String toString() {
		return "contenu [pseudo_users=" + t_pseudo_users + ", date=" + t_date + ", heure=" + t_heure + ", contenu=" + t_contenu
				+ ", lien_dans_contenu=" + t_lien_dans_contenu + ", hashtag=" + t_hashtag + ", pseudo_mentionne="
				+ t_pseudo_mentionne + ", pseudo_retweet=" + t_pseudo_retweet + "]";
	}



	/*
	public News(String t,String a,String s, String d) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String date;
		this.t_titre=t;
		this.t_auteur=a;
		this.t_source=s;
		date=d;

		try {
			this.t_date = LocalDate.parse(date, formatter);
		} catch (DateTimeParseException e) { System.out.println( "erreur, la date sera celle du  jour"); };


	}

	 */


}
