package controllers;

import models.Widget;

import java.util.List;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import javax.inject.Inject;

import play.db.Database;
import play.db.NamedDatabase;
import play.mvc.Controller;

public class WidgetDB extends Controller{
	private static Connection mConnection;

	private Database mWidgetDB;

	@Inject
	public WidgetDB(@NamedDatabase("widgets") Database db){
		mWidgetDB = db;
		connect();
		init();
	}

	private void connect(){
		mConnection = mWidgetDB.getConnection();
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

	public void init(){
		initDatabase();
		initWidgetTable();
	}

	private void initDatabase(){
		Statement statment = null;

		try{
			statment = mConnection.createStatement();

			statment.executeUpdate("Create database if not exists widgets");
			statment.executeUpdate("use widgets");
			statment.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	private void initWidgetTable(){
		Statement statment = null;

		try{
			statment = mConnection.createStatement();
			String query = "create table if not exists widget " + 
				"(email varchar(255), id integer, name varchar(40), status varchar(11), description varchar(255))";

			statment.executeUpdate(query);
			statment.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void addWidget(String email, Widget widget){
		PreparedStatement statment = null;

		try{
			String query = "insert into widget (email, id, name, status, description) values (?, ?, ?, ?, ?)";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);
			statment.setInt(2, widget.getId());
			statment.setString(3, widget.getName());
			statment.setString(4, widget.getStatus());
			statment.setString(5, widget.getDescription());
			statment.execute();
			statment.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void removeWidget(String email, int widgetId){
		PreparedStatement statment = null;

		try{
			String query = "delete from widget where email = ? and id = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);
			statment.setInt(2, widgetId);
			statment.execute();
			statment.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public boolean containsWidget(String email, int widgetId){
		boolean isFound = false;
		PreparedStatement statment = null;

		try{
			String query = "select * from widget where email = ? and id = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);
			statment.setInt(2, widgetId);

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

	public Widget getWidget(String email, int widgetId){
		Widget widget = null;
		PreparedStatement statment = null;

		try{
			String query = "select * from widget where email = ? and id = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);
			statment.setInt(2, widgetId);

			ResultSet resultSet = statment.executeQuery();

			if(resultSet.next())
				widget = new Widget(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("status"), 
					resultSet.getString("description"));

			statment.close();
			resultSet.close();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return widget;
	}

	public List<Widget> getWidgets(String email){
		List<Widget> widgetList = new ArrayList<>();
		PreparedStatement statment = null;

		try{
			String query = "select * from widget where email = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);

			ResultSet resultSet = statment.executeQuery();

			while(resultSet.next())
				widgetList.add(new Widget(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("status"), 
					resultSet.getString("description")));

			statment.close();
			resultSet.close();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return widgetList;
	}

	public boolean updateWidgetStatus(String email, int widgetId, String status){
		boolean statusChanged = false;
		PreparedStatement statment = null;

		try{
			String query = "update widget set status = ? where email = ? and id = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, status);
			statment.setString(2, email);
			statment.setInt(3, widgetId);
			statment.execute();
			statment.close();

			statusChanged = true;
		}catch(SQLException e){
			e.printStackTrace();
		}

		return statusChanged;
	}

	public int getUserWidgetCount(String email){
		int userWidgetCount = 0;

		PreparedStatement statment = null;

		try{
			String query = "select * from widget where email = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);
			
			ResultSet resultSet = statment.executeQuery();

			while(resultSet.next())
				userWidgetCount++;

			statment.close();
			resultSet.close();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return userWidgetCount;
	}
}