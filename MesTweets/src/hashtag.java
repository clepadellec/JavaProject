import java.util.Comparator;

public class hashtag {

		private String h_libele;
		private Integer h_nombre_occurence;
		
		
		public hashtag(String h_libele, Integer nombre_occurence) {
			super();
			this.h_libele = h_libele;
			this.h_nombre_occurence = nombre_occurence;
		}
		@Override
		public String toString() {
			return "hashtag [h_libele=" + h_libele + ", nombre_occurence=" + h_nombre_occurence + "]";
		}
		public String getH_libele() {
			return h_libele;
		}
		public void setH_libele(String h_libele) {
			this.h_libele = h_libele;
		}
		public int getNombre_occurence() {
			return h_nombre_occurence;
		}
		public void setNombre_occurence(Integer nombre_occurence) {
			this.h_nombre_occurence = nombre_occurence;
		} 
		
		public static Comparator<hashtag> triHashtag = new Comparator<hashtag>() {
			public int compare (hashtag t2,hashtag t1) {
				Integer nbh1 = t1.getNombre_occurence();
				Integer nbh2 = t2.getNombre_occurence();
				int resultat;
				resultat=nbh1.compareTo(nbh2);
				return resultat;
			}

		};
		
}
