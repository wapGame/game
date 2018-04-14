package lu.lllc;

import java.io.Serializable;

public class User implements Serializable {
	static final long serialVersionUID = 12345L;
	private String username;
	private String password;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		
	}
	
	public void doTest() {
		//doNothing
	}

}

//gitTest