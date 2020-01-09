import java.util.Comparator;

public class hashtag {

		// un hashtag possede un libelle et le nombre d'occurences correspondant
		private String h_libele;
		private Integer h_nombre_occurence;
		
		@Override
		//methode tostring
		public String toString() {
			return "hashtag [h_libele=" + h_libele + ", h_nombre_occurence=" + h_nombre_occurence + "]";
		}
		
		//constructeur
		public hashtag(String h_libele, Integer h_nombre_occurence) {
			super();
			this.h_libele = h_libele;
			this.h_nombre_occurence = h_nombre_occurence;
		}

		//getters et setters
		public String getH_libele() {
			return h_libele;
		}

		public void setH_libele(String h_libele) {
			this.h_libele = h_libele;
		}

		public Integer getH_nombre_occurence() {
			return h_nombre_occurence;
		}

		public void setH_nombre_occurence(Integer h_nombre_occurence) {
			this.h_nombre_occurence = h_nombre_occurence;
		}


		//on creer une fonction de tri des hashtags en fonction du nombre d'occurences
		public static Comparator<hashtag> triHashtag = new Comparator<hashtag>() {
			public int compare (hashtag t2,hashtag t1) {
				Integer nbh1 = t1.getH_nombre_occurence();
				Integer nbh2 = t2.getH_nombre_occurence();
				int resultat;
				resultat=nbh1.compareTo(nbh2);
				return resultat;
			}

		};
		
}
