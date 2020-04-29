package it.polito.ezgas.dto;

public class IdPw {
	
	private String user;
	private String pw;
	
	public IdPw(String id, String pw) {
		this.user = id;
		this.pw = pw;
	}
	
	public IdPw() {}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
}
