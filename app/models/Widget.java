package models;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Widget{
	private long mId;
	private String mName;
	private String mStatus;
	private String mDescription;

	public Widget(long id, String name, String description){
		mId = id;
		mName = name;
		mStatus = "TODO";
		mDescription = description;
	}

	public Widget(long id, String name, String status, String description){
		mId = id;
		mName = name;
		mStatus = status;
		mDescription = description;	
	}

	public long getId(){
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