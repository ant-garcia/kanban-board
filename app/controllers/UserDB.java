package controllers;

import models.User;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import javax.inject.Inject;

import play.db.Database;
import play.db.NamedDatabase;
import play.mvc.Controller;

public class UserDB extends Controller{
	private static Connection mConnection;

	private Database mUserDB;

	@Inject
	public UserDB(@NamedDatabase("users") Database db){
		mUserDB = db;
		connect();
		init();
	}

	private void connect(){
		mConnection = mUserDB.getConnection();
	}

	public boolean close(){
		boolean isClosed = false;

		try{
			mConnection.close();

			isClosed = true;
		}catch(SQLException e){
			e.printStackTrace();
		}

		return isClosed;
	}

	private void init(){
		initDatabase();
		initUserTable();
	}

	private void initDatabase(){
		Statement statment = null;

		try{
			statment = mConnection.createStatement();

			statment.executeUpdate("Create database if not exists users");
			statment.executeUpdate("use users");
			statment.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	private void initUserTable(){
		Statement statment = null;

		try{
			statment = mConnection.createStatement();
			String query = "create table if not exists user " + 
				"(email varchar(255), password varchar(20), name varchar(40), primary key(email))";

			statment.executeUpdate(query);
			statment.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void addUser(User user){
		PreparedStatement statment = null;

		try{
			String query = "insert into user (email, password, name) values (?, ?, ?)";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, user.getEmail());
			statment.setString(2, user.getPassword());
			statment.setString(3, user.getName());
			statment.execute();
			statment.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void removeUser(String email){
		PreparedStatement statment = null;

		try{
			String query = "delete from user where email = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);
			statment.execute();
			statment.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public boolean containsUser(String email){
		boolean isFound = false;
		PreparedStatement statment = null;

		try{
			String query = "select email from user where email = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);

			ResultSet resultSet = statment.executeQuery();

			if(resultSet.next())
				isFound = true;

			statment.close();
			resultSet.close();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return isFound;
	}

	public User getUser(String email, String password){
		User user = null;
		PreparedStatement statment = null;

		try{
			String query = "select * from user where email = ? and password = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);
			statment.setString(2, password);

			ResultSet resultSet = statment.executeQuery();

			if(resultSet.next())
				user = new User(resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password"));

			statment.close();
			resultSet.close();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return user;
	}

	public boolean changeUserPassword(String email, String oldPassword, String newPassword){
		boolean passwordChanged = false;
		PreparedStatement statment = null;

		try{
			String query = "select * from user where email = ? and password = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);
			statment.setString(2, oldPassword);

			ResultSet resultSet = statment.executeQuery();

			if(resultSet.next()){
				String passwordQuery = "update user set password = ? where email = ?";
				PreparedStatement passwordStatement = mConnection.prepareStatement(passwordQuery);

				passwordStatement.setString(1, newPassword);
				passwordStatement.setString(2, email);
				passwordStatement.execute();
				passwordStatement.close();

				passwordChanged = true;
			}

			statment.close();
			resultSet.close();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return passwordChanged;
	}
}
	