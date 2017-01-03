package models;

import java.util.Map;
import java.util.HashMap;

public class UserDB{
	private Map<String, User> users;

	public UserDB(){
		users = new HashMap<>();
	}

	public void addUser(String name, String email, String password){
		users.put(email, new User(name, email, password));
	}

	public void deleteUser(String email){
		users.remove(email);
	}

	public boolean isUser(String email){
		return users.containsKey(email);
	}

	public User getUser(String email){
		return users.get(email);
	}

	public boolean isEmpty(){
		return users.isEmpty();
	}

	public boolean isValid(String email, String password){
		return ((email != null) && (password != null) && isUser(email) && getUser(email).getPassword().equals(password));
	}
}