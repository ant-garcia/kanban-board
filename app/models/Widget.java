package models;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Widget{
	private int mId;
	private String mName;
	private String mStatus;
	private String mDescription;

	public Widget(String name, String description){
		mId = 0;
		mName = name;
		mStatus = "TODO";
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

	public String toString(){
		return String.format("Name: %s\nDescription: %s", mName, mDescription); 
	}
}