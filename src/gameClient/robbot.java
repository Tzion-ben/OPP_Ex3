package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Point3D;

public class robbot {

	//***************Contractor************
	public robbot(String strFr) {
		setFromJson(strFr);
	}


	//*************only getters because the parameters are fron json file****************
	public int getId() {
		return this.id;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public int getSrc() {
		return this.src;
	}

	public int getDest() {
		return this.dest;
	}
	
	public double getSpeed() {
		return this.speed;
	}

	public Point3D getlocation() {
		return this.location;
	}
	//end getters

	private  void setFromJson(String str) {		
		try {
			JSONObject line = new JSONObject(str);;
			JSONObject tt= line.getJSONObject("Robot");
			int idd=tt.getInt("id");
			int val=tt.getInt("value");
			int srcc=tt.getInt("src");
			int destt=tt.getInt("dest");
			int speedd=tt.getInt("speed");
			Point3D tempLocation=new Point3D(tt.getString("pos"));
			
			this.id=idd;
			this.value=val;
			this.src=srcc;
			this.dest=destt;
			this.speed=speedd;
			this.location=tempLocation;
		}//end try
		catch (JSONException e) {e.printStackTrace();}	
	}

	//********************private fields***********************
	private int id;
	private int value;
	private int src;
	private int dest;
	private int speed;
	private Point3D location;
}