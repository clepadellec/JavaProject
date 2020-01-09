import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;
//
//@SuppressWarnings("serial")
public class tweet{

	//création des attributs
	//dans un tweet le premier élément est le pseudo de l'utilisateur
	protected String t_pseudo_users="";
	//puis on trouve la date du tweet
	protected LocalDate t_date;
	// ainsi que l'heure
	protected String t_heure="";
	//ensuite on retrouve le contenu du tweet
	protected String t_contenu="";
	//on va extraire les liens des tweets
	protected String t_lien_dans_contenu="";
	//on extrait également les pseudos mentonnés
	protected String t_pseudo_mentionne="";
	// a la fin du tweet on retrouve le pseudo de l'utilisateur originaire du tweet (s'il s'agit d'un retweet)
	protected String t_pseudo_retweet="";
	// on créer des attributs qui stockeront les nb de hashtags et de pseudos mentionnés
	protected int t_nb_hashtag = 0;
	protected int t_nb_pseudo_mentionne=0;
	//on séparera les dates en mois/année/jour/nom du jour/nom de la semaine
	protected String t_annee;
	protected String t_mois;
	protected String t_jour;
	protected String t_jour_Semaine;
	protected String t_semaine;
	//protected LocalDate date = LocalDate.now();
	//private String date="";





	
	@Override
	//méthode tostring générée automatiquement
	public String toString() {
		return "tweet [t_pseudo_users=" + t_pseudo_users + ", t_date=" + t_date + ", t_heure=" + t_heure
				+ ", t_contenu=" + t_contenu + ", t_lien_dans_contenu=" + t_lien_dans_contenu + ", t_hashtag="
				+ ", t_pseudo_mentionne=" + t_pseudo_mentionne + ", t_pseudo_retweet=" + t_pseudo_retweet
				+ ", t_nb_pseudo_mentionne=" + t_nb_pseudo_mentionne + ", t_nb_hashtag=" + t_nb_hashtag + ", t_annee="
				+ t_annee + ", t_mois=" + t_mois + ", t_jour=" + t_jour + ", t_jour_Semaine=" + t_jour_Semaine
				+ ", t_semaine=" + t_semaine + "]";
	}

	//constructeur
	public tweet(String t_pseudo_users, LocalDate t_date, String t_heure, String t_contenu, String t_lien_dans_contenu,
			String t_pseudo_mentionne, String t_pseudo_retweet, int t_nb_pseudo_mentionne,
			int t_nb_hashtag, String t_annee, String t_mois, String t_jour, String t_jour_Semaine, String t_semaine) {
		super();
		this.t_pseudo_users = t_pseudo_users;
		this.t_date = t_date;
		this.t_heure = t_heure;
		this.t_contenu = t_contenu;
		this.t_lien_dans_contenu = t_lien_dans_contenu;
		this.t_pseudo_mentionne = t_pseudo_mentionne;
		this.t_pseudo_retweet = t_pseudo_retweet;
		this.t_nb_pseudo_mentionne = t_nb_pseudo_mentionne;
		this.t_nb_hashtag = t_nb_hashtag;
		this.t_annee = t_annee;
		this.t_mois = t_mois;
		this.t_jour = t_jour;
		this.t_jour_Semaine = t_jour_Semaine;
		this.t_semaine = t_semaine;
	}

	//génération des getters et setters
	public String getT_pseudo_users() {
		return t_pseudo_users;
	}

	public void setT_pseudo_users(String t_pseudo_users) {
		this.t_pseudo_users = t_pseudo_users;
	}

	public LocalDate getT_date() {
		return t_date;
	}

	public void setT_date(LocalDate t_date) {
		this.t_date = t_date;
	}

	public String getT_heure() {
		return t_heure;
	}

	public void setT_heure(String t_heure) {
		this.t_heure = t_heure;
	}

	public String getT_contenu() {
		return t_contenu;
	}

	public void setT_contenu(String t_contenu) {
		this.t_contenu = t_contenu;
	}

	public String getT_lien_dans_contenu() {
		return t_lien_dans_contenu;
	}

	public void setT_lien_dans_contenu(String t_lien_dans_contenu) {
		this.t_lien_dans_contenu = t_lien_dans_contenu;
	}

	public String getT_pseudo_mentionne() {
		return t_pseudo_mentionne;
	}

	public void setT_pseudo_mentionne(String t_pseudo_mentionne) {
		this.t_pseudo_mentionne = t_pseudo_mentionne;
	}

	public String getT_pseudo_retweet() {
		return t_pseudo_retweet;
	}

	public void setT_pseudo_retweet(String t_pseudo_retweet) {
		this.t_pseudo_retweet = t_pseudo_retweet;
	}

	public int getT_nb_pseudo_mentionne() {
		return t_nb_pseudo_mentionne;
	}

	public void setT_nb_pseudo_mentionne(int t_nb_pseudo_mentionne) {
		this.t_nb_pseudo_mentionne = t_nb_pseudo_mentionne;
	}

	public int getT_nb_hashtag() {
		return t_nb_hashtag;
	}

	public void setT_nb_hashtag(int t_nb_hashtag) {
		this.t_nb_hashtag = t_nb_hashtag;
	}

	public String getT_annee() {
		return t_annee;
	}

	public void setT_annee(String t_annee) {
		this.t_annee = t_annee;
	}

	public String getT_mois() {
		return t_mois;
	}

	public void setT_mois(String t_mois) {
		this.t_mois = t_mois;
	}

	public String getT_jour() {
		return t_jour;
	}

	public void setT_jour(String t_jour) {
		this.t_jour = t_jour;
	}

	public String getT_jour_Semaine() {
		return t_jour_Semaine;
	}

	public void setT_jour_Semaine(String t_jour_Semaine) {
		this.t_jour_Semaine = t_jour_Semaine;
	}

	public String getT_semaine() {
		return t_semaine;
	}

	public void setT_semaine(String t_semaine) {
		this.t_semaine = t_semaine;
	}

	// on créer des méthode de comparaison des tweets
	//ici on compare les dates
	public static Comparator<tweet> triDate = new Comparator<tweet>() {
		public int compare (tweet t1,tweet t2) {
			// on recupere les deux dates
			LocalDate tdate1 = t1.getT_date();
			LocalDate tdate2 = t2.getT_date();
			int resultat;
			//on compare les dates
			resultat=tdate1.compareTo(tdate2);
			// si elles sont égales on compare les heures
			if (resultat==0) {
				String h1 = t1.getT_heure();
				String h2 = t2.getT_heure();
				resultat=h1.compareTo(h2);
			}
			return resultat;
		}
	};
	
	// cette méthode est similaire à celle du dessus
	// on tri d'abord par pseudo utilisateurs puis par date
	public static Comparator<tweet> triUsers = new Comparator<tweet>() {
		public int compare (tweet t1,tweet t2) {
			String pseu1 = t1.getT_pseudo_users();
			String pseu2 = t2.getT_pseudo_users();
			int resultat;
			resultat=pseu1.compareTo(pseu2);
			if (resultat==0) {
				LocalDate tdate1 = t1.getT_date();
				LocalDate tdate2 = t2.getT_date();
				resultat=tdate1.compareTo(tdate2);
			}
			return resultat;
		}
	};

	
	// cette méthode est similaire à celle du dessus
	// on tri d'abord par pseudo pseudo retweet puis par date
	public static Comparator<tweet> triRetweet = new Comparator<tweet>() {
		public int compare (tweet t1,tweet t2) {
			String pseu1 = t1.getT_pseudo_retweet();
			String pseu2 = t2.getT_pseudo_retweet();
			int resultat;
			resultat=pseu1.compareTo(pseu2);
			if (resultat==0) {
				LocalDate tdate1 = t1.getT_date();
				LocalDate tdate2 = t2.getT_date();
				resultat=tdate1.compareTo(tdate2);
			}
			return resultat;
		}
	};




}
