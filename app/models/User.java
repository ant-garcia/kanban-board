package models;

public class User{
	private String mName;
	private String mEmail;
	private String mPassword;

	public User(String name, String email, String password){
		mName = name;
		mEmail = email;
		mPassword = password;
	}

	public String getName(){
		return mName;
	}

	public String getEmail(){
		return mEmail;
	}

	public String getPassword(){
		return mPassword;
	}
}