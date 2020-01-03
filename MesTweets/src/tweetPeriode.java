import java.time.LocalDate;

public class tweetPeriode /*extends tweet*/{
	
	
	private Integer n_mois;
	private Integer n_heure;
	private Integer n_semaine;
	private Integer n_jour;
	
	public tweetPeriode(/*String pseudo_users, LocalDate date, String heure, String contenu, String lien_dans_contenu,
			String hashtag, String pseudo_mentionne, String pseudo_retweet, int nb_hashtag, int nb_pseudo_mentionne,*/
			Integer n_mois, Integer n_semaine, Integer n_jour, Integer n_heure) {
		
		/*super(pseudo_users, date, heure, contenu, lien_dans_contenu, hashtag, pseudo_mentionne, pseudo_retweet,
				nb_hashtag, nb_pseudo_mentionne);*/
		this.n_mois = n_mois;
		this.n_semaine = n_semaine;
		this.n_jour = n_jour;
		this.n_heure = n_heure;
	}

	public Integer getMois() {
		return n_mois;
	}

	public void setMois(Integer mois) {
		this.n_mois = mois;
	}

	public Integer getSemaine() {
		return n_semaine;
	}

	public void setSemaine(Integer semaine) {
		this.n_semaine = semaine;
	}

	public Integer getJour() {
		return n_jour;
	}

	public void setJour(Integer jour) {
		this.n_jour = jour;
	}

	public Integer getn_Heure() {
		return n_heure;
	}

	public void setHeure(Integer heure) {
		this.n_heure = heure;
	}


	

}
