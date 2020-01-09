
public class nombreTweet {

		//la modalite servira à stocker la variable pour laquelle on souhaitera compter le nombre de tweet (ex: jour, mois...)
		public String modalite;
		//c'est le nombre de tweets qui correspondent Ã  la modalite
		public Integer valeur;
		
		//constructeur
		public nombreTweet(String modalite, Integer valeur) {
			this.modalite = modalite;
			this.valeur = valeur;
		}
		
		//getters et setters
		public String getModalite() {
			return modalite;
		}
		public void setModalite(String modalite) {
			this.modalite = modalite;
		}
		public Integer getValeur() {
			return valeur;
		}
		public void setValeur(Integer valeur) {
			this.valeur = valeur;
		}
		
		
		
}
