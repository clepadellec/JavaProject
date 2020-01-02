
public class nombreTweet {

		private Integer n_mois;
		private Integer n_semaine;
		private Integer n_jour;
		private Integer n_heure;
		
		public nombreTweet(Integer mois, Integer semaine, Integer jour, Integer heure) {
			super();
			this.n_mois = mois;
			this.n_semaine = semaine;
			this.n_jour = jour;
			this.n_heure = heure;
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

		public Integer getHeure() {
			return n_heure;
		}

		public void setHeure(Integer heure) {
			this.n_heure = heure;
		}


		
		
}
