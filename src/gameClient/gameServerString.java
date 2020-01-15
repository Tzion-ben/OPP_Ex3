package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

public class gameServerString {
	//***************Contractor************
	public gameServerString(String strFr) {
		setFromJson(strFr);
	}


	//*************only getters because the parameters are from json file****************
	public int getNumFruits() {
		return this.numFruits;
	}

	public int getNumMoves() {
		return this.numMoves;
	}

	public int getNumGrade() {
		return this.numGrade;
	}

	public int getNumRobbots() {
		return this.numRobbots;
	}

	public String getgraphId() {
		return this.graphId;
	}

	//end getters

	private  void setFromJson(String str) {		
		try {
			JSONObject line = new JSONObject(str);;
			JSONObject tt= line.getJSONObject("GameServer");
			int numFruitss=tt.getInt("fruits");
			int movess=tt.getInt("moves");
			int gradee=tt.getInt("grade");
			int robotss=tt.getInt("robots");
			String graphh=tt.getString("graph");

			this.numFruits=numFruitss;
			this.numMoves=movess;
			this.numGrade=gradee;
			this.numRobbots=robotss;
			this.graphId=graphh;
		}//end try
		catch (JSONException e) {e.printStackTrace();}	
	}

	//********************private fields***********************
	private int numFruits;
	private int numMoves;
	private int numGrade;
	private int numRobbots;
	private String graphId;
}
