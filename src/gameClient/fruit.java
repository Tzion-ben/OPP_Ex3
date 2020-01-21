package gameClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import dataStructure.NodeData;
import dataStructure.node_data;
import utils.Point3D;



public class fruit {

	//***************Contractor************
	public fruit(String strFr) {
		setFromJson(strFr);
	}


	//*************only getters because the parameters are from json file****************
	public double getValue() {
		return this.value;
	}

	public int getType() {
		return this.type;
	}

	public Point3D getlocation() {
		return this.location;
	}
	
	public String getIconName() {
		return this.iconName;
	}
	
	public String getIconAddress() {
		return this.iconAddress;
	}
	
	public int getIconID() {
		return this.iconID;
	}
	//end getters
	
	//set only for the icon id to KML file
	public void setIconID(int iconID) {
		this.iconID=iconID;
	}
	
	public void setIconName(int ID) {
		String fullName="fruit num "+ID;
		this.iconName=fullName;
	}
	//end of the set
	
	private  void setFromJson(String str) {		
		try {
			JSONObject line = new JSONObject(str);;
			JSONObject tt= line.getJSONObject("Fruit");
			int val=tt.getInt("value");
			int ty=tt.getInt("type");
			Point3D tempLocation=new Point3D(tt.getString("pos"));
			this.value=val;
			this.type=ty;
			this.location=tempLocation;
			this.iconName=null;
			this.iconAddress="https://img.icons8.com/carbon-copy/100/000000/broken-robot.png";
			this.iconID=-100;
		}//end try
		catch (JSONException e) {e.printStackTrace();}	
	}

	//********************private fields***********************
	private double value;
	private int type;
	private Point3D location;
	private String iconName;
	private String iconAddress;
	private int iconID;
}
