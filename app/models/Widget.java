package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "widget", schema = "kanban")
public class Widget{

	@Id
	@Column(name = "id")
	private int mId;

	@Column(name = "name")
	private String mName;

	@Column(name = "email")
	private String email;

	@Column(name = "status")
	private String mStatus;

	@Column(name = "description")
	private String mDescription;

	public Widget(){}

	public Widget(int id, String email, String name, String description){
		mId = id;
		this.email = email;
		mName = name;
		mStatus = "TODO";
		mDescription = description;
	}

	public Widget(int id, String email, String name, String status, String description){
		mId = id;
		this.email = email;
		mName = name;
		mStatus = status;
		mDescription = description;	
	}

	public int getId(){
		return mId;
	}

	public void setId(int id){
		mId = id;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}
	
	public String getName(){
		return mName;
	}

	public void setName(String name){
		mName = name;
	}

	public String getStatus(){
		return mStatus;
	}

	public void setStatus(String status){
		mStatus = status;
	}

	public String getDescription(){
		return mDescription;
	}

	public void setDescription(String description){
		mDescription = description;
	}

	public String toString(){
		return String.format("Name: %s\nDescription: %s", mName, mDescription); 
	}
}