/**
 * 
 */
package fr.epita.iam.datamodel;

/**
 * @author tbrou
 *
 */
public class Identity {
	
	private String uid;
	private String displayName;
	private String email;
	private String date;
	
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the date
	 */
	public String getDate(){
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date){
		this.date = date;
	}
	/**
	 * @param uid
	 * @param displayName
	 * @param email
	 * @param date
	 */
	public Identity(String uid, String displayName, String email, String date) {
		this.uid = uid;
		this.displayName = displayName;
		this.email = email;
		this.date = date;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Identity [uid=" + this.uid + ", displayName=" + this.displayName + ", email=" + this.email + ", birthdate=" + this.date + "]";
	}

	
}
