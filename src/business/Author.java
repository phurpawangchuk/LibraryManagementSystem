package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private String bio, credit;
	public String getBio() {
		return bio;
	}
	
	public Author(String f, String l, String t, Address a, String credit,  String bio) {
		super(f, l, t, a);
		this.bio = bio;
		this.credit=credit;
	}
	
	public String getCredit() {
		return credit;
	}





	private static final long serialVersionUID = 7508481940058530471L;
}
