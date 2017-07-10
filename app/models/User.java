package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", schema = "kanban")
public class User{

	@Id
	@Column(name = "email")
	private String mEmail;

	@Column(name = "name")
	private String mName;

	@Column(name = "password")
	private String mPassword;

	public User(){}

	public User(String name, String email, String password){
		mName = name;
		mEmail = email;
		mPassword = password;
	}

	public String getName(){
		return mName;
	}

	public void setName(String name){
		mName = name;
	}

	public String getEmail(){
		return mEmail;
	}

	public void setEmail(String email){
		mEmail = email;
	}

	public String getPassword(){
		return mPassword;
	}

	public void setPassword(String password){
		mPassword = password;
	}
}