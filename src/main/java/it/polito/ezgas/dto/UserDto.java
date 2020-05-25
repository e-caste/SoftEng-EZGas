package it.polito.ezgas.dto;

/**
 * Created by softeng on 27/4/2020.
 */
public class UserDto {

    Integer userId;
    String userName;
    String password;
    String email;
    Integer reputation;
    Boolean admin;

    public UserDto() {}

    public UserDto(Integer userId, String userName, String password, String email, Integer reputation) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.reputation = reputation;
        admin = false;
    }
    
    public UserDto(Integer userId, String userName, String password, String email, Integer reputation, Boolean admin) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.reputation = reputation;
        this.admin = admin;
    }

    public String toString() {
        return this.userId + " " +
               this.userName + " " +
               this.email + " " +
               this.password + " " +
               this.reputation + " " +
               this.admin;
    }

    public boolean equals(UserDto other) {
        return this.userId.equals(other.getUserId()) &&
               this.userName.equals(other.getUserName()) &&
               this.email.equals(other.getEmail()) &&
               this.password.equals(other.getPassword()) &&
               this.reputation.equals(other.getReputation()) &&
               this.admin.equals(other.getAdmin());
    }

    public boolean equalsIgnoreUserId(UserDto other) {
        return this.userName.equals(other.getUserName()) &&
               this.email.equals(other.getEmail()) &&
               this.password.equals(other.getPassword()) &&
               this.reputation.equals(other.getReputation()) &&
               this.admin.equals(other.getAdmin());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getReputation() {
		return reputation;
	}

	public void setReputation(Integer reputation) {
		this.reputation = reputation;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
}
