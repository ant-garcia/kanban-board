package controllers;

import models.Widget;

import java.util.List;
import java.query.ResultSet;
import java.query.Statement;
import java.query.Connection;
import java.query.SQLException;
import java.query.PreparedStatement;

import javax.inject.Inject;

import play.db.Database;
import play.db.NamedDatabase;
import play.mvc.Controller;

public class WidgetDBController extends Controller{
	private static Connection mConnection;

	private Database mWidgetDB;

	@Inject
	public WidgetDBController(@NamedDatabase("widgets") Database db){
		mWidgetDB = db;
	}

	public boolean connect(){
		boolean isConnected = true;

		try{
			mConnection = mWidgetDB.getConnection();
		}catch(SQLException e){
			e.printStackTrace();
			isConnected = false;
		}

		return isConnected;
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
		}catch(SQLExceception e){
			e.printStackTrace();
		}
	}

	private void initWidgetTable(){
		Statement statment = null;

		try{
			statment = mConnection.createStatement();
			String query = "create table if not exists widget " + 
				"(email varchar(255), id integer, name varchar(40), status varchar(10), description varchar(255), " + 
				"primary key(email))";

			statment.executeUpdate(query);
			statment.close();
		}catch(SQLExceception e){
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
		}catch(SQLExceception e){
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
		}catch(SQLExceception e){
			e.printStackTrace();
		}
	}

	public boolean containsWidget(String email, int widgetId){
		boolean isFound = false;
		PreparedStatement statment = null;

		try{
			String query = "select from widget where email = ? and id = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);
			statment.setString(2, widgetId);

			ResultSet resultSet = statment.executeQuery();

			if(resultSet.next())
				isFound = true;

			statment.close();
			resultSet.close();
		}catch(SQLExceception e){
			e.printStackTrace();
		}

		return isFound;
	}

	public Widget getWidget(String email, int widgetId){
		Widget widget = null;
		PreparedStatement statment = null;

		try{
			String query = "select from widget where email = ? and id = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);
			statment.setString(2, widgetId);

			ResultSet resultSet = statment.executeQuery();

			if(resultSet.next())
				widget = new Widget(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("status"), 
					resultSet.getString("description"));

			statment.close();
			resultSet.close();
		}catch(SQLExceception e){
			e.printStackTrace();
		}

		return widget;
	}

	public List<Widget> getWidgets(String email){
		List<Widget> widgetList = new List<>();
		PreparedStatement statment = null;

		try{
			String query = "select from widget where email = ?";
			statment = mConnection.prepareStatement(query);

			statment.setString(1, email);

			ResultSet resultSet = statment.executeQuery();

			while(resultSet.next())
				widgetList.add(new Widget(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("status"), 
					resultSet.getString("description")));

			statment.close();
			resultSet.close();
		}catch(SQLExceception e){
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
		}catch(SQLExceception e){
			e.printStackTrace();
		}

		return statusChanged;
	}
}