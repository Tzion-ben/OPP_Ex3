package gameClient;

import org.json.JSONObject;
import org.json.JSONString;

import utils.Point3D;



public class fruit {

//***************Contractor************
	public fruit(JSONObject js) {
		setFromJson(js);
	}
	
	
//*************only getters because the parameters are fron json file****************
	public double getValue() {
		return this.value;
	}
	
	public int getType() {
		return this.type;
	}
	
	public Point3D getlocation() {
		return this.location;
	}
	//end getters
	
	private  void setFromJson(JSONObject js) {
		
		
	}
	
//********************private fields***********************
	private double value;
	private int type;
	private Point3D location;
}
