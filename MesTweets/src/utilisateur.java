import java.io.Serializable;

public class utilisateur  implements Comparable<utilisateur>, Serializable{

	private String u_pseudo_users = "";
	private Integer u_nombre_retweet = 0;
	private Integer u_nombre_mention = 0;
	
	public utilisateur(String u_pseudo_users, Integer u_nombre_retweet, Integer u_nombre_mention) {
		this.u_pseudo_users = u_pseudo_users;
		this.u_nombre_retweet = u_nombre_retweet;
		this.u_nombre_mention = u_nombre_mention;
	}
	

	public String getU_pseudo_users() {
		return u_pseudo_users;
	}
	public void setU_pseudo_users(String u_pseudo_users) {
		this.u_pseudo_users = u_pseudo_users;
	}
	public Integer getU_nombre_retweet() {
		return u_nombre_retweet;
	}
	public void setU_nombre_retweet(Integer u_nombre_retweet) {
		this.u_nombre_retweet = u_nombre_retweet;
	}
	public Integer getU_nombre_mention() {
		return u_nombre_mention;
	}
	public void setU_nombre_mention(Integer u_nombre_mention) {
		this.u_nombre_mention = u_nombre_mention;
	}


	public int compareTo(utilisateur arg0) {
		if (getU_nombre_retweet().compareTo(arg0.getU_nombre_retweet())>0) return -1;
		else if (getU_nombre_retweet().compareTo(arg0.getU_nombre_retweet())<0) return 1;
		else return 0;
	}


	
}
