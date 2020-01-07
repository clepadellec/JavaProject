import java.io.Serializable;
import java.util.Comparator;

public class utilisateur  {

	private String u_pseudo_users = "";
	private Integer u_nombre_tweet = 0;	
	private Integer u_nombre_retweet = 0;
	private Integer u_nombre_mention = 0;
	


	public utilisateur(String u_pseudo_users, Integer u_nombre_tweet, Integer u_nombre_retweet,
			Integer u_nombre_mention) {
		super();
		this.u_pseudo_users = u_pseudo_users;
		this.u_nombre_tweet = u_nombre_tweet;
		this.u_nombre_retweet = u_nombre_retweet;
		this.u_nombre_mention = u_nombre_mention;
	}

	public String getU_pseudo_users() {
		return u_pseudo_users;
	}
	
	public void setU_pseudo_users(String u_pseudo_users) {
		this.u_pseudo_users = u_pseudo_users;
	}
	
	public Integer getU_nombre_tweet() {
		return u_nombre_tweet;
	}

	public void setU_nombre_tweet(Integer u_nombre_tweet) {
		this.u_nombre_tweet = u_nombre_tweet;
	}

	public Integer getU_nombre_mention() {
		return u_nombre_mention;
	}

	public void setU_nombre_mention(Integer u_nombre_mention) {
		this.u_nombre_mention = u_nombre_mention;
	}
	
	public Integer getU_nombre_retweet() {
		return u_nombre_retweet;
	}

	public void setU_nombre_retweet(Integer u_nombre_retweet) {
		this.u_nombre_retweet = u_nombre_retweet;
	}

	public static Comparator<utilisateur> triTweet = new Comparator<utilisateur>() {
		public int compare (utilisateur t2,utilisateur t1) {
			Integer nbt1 = t1.getU_nombre_tweet();
			Integer nbt2 = t2.getU_nombre_tweet();
			int resultat;
			resultat=nbt1.compareTo(nbt2);
			return resultat;
		}

	};
	
	public static Comparator<utilisateur> triMention = new Comparator<utilisateur>() {
		public int compare (utilisateur t2,utilisateur t1) {
			Integer nbt1 = t1.getU_nombre_mention();
			Integer nbt2 = t2.getU_nombre_mention();
			int resultat;
			resultat=nbt1.compareTo(nbt2);
			return resultat;
		}

	};
	
	public static Comparator<utilisateur> triRetweet = new Comparator<utilisateur>() {
		public int compare (utilisateur t2,utilisateur t1) {
			Integer nbt1 = t1.getU_nombre_retweet();
			Integer nbt2 = t2.getU_nombre_retweet();
			int resultat;
			resultat=nbt1.compareTo(nbt2);
			return resultat;
		}

	};
	
}
