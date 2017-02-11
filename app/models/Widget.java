package models;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Widget{
	private int mId;
	private String mName;
	private String mStatus;
	private String mDescription;

	public Widget(int id, String name, String description){
		mId = id;
		mName = name;
		mStatus = "TODO";
		mDescription = description;
	}

	public Widget(int id, String name, String status, String description){
		mId = id;
		mName = name;
		mStatus = status;
		mDescription = description;	
	}

	public int getId(){
		return mId;
	}
	
	public String getName(){
		return mName;
	}

	public String getStatus(){
		return mStatus;
	}

	public String getDescription(){
		return mDescription;
	}

	public void setStatus(String status){
		mStatus = status;
	}

	public String toString(){
		return String.format("Name: %s\nDescription: %s", mName, mDescription); 
	}
}