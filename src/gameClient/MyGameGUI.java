package gameClient;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Server.Game_Server;
import Server.game_service;
import algorithems.Graph_Algo;
import dataStructure.*;
import gameClient.*;
import oop_dataStructure.OOP_DGraph;
import utils.*;

public class MyGameGUI {

	public void GraphInit() {
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		String info = game.toString();
		//		JSONObject line;
		//		try {
		//			line = new JSONObject(info);
		//			JSONObject ttt = line.getJSONObject("GameServer");
		//			int rs = ttt.getInt("robots");
		//			System.out.println(info);
		//			System.out.println(g);
		//			// the list of fruits should be considered in your solution
		//Iterator<String> f_iter = game.getFruits().iterator();
		List<String> fruits=this.game.getFruits();
		Iterator<String> f_iter = fruits.iterator();
		while(f_iter.hasNext()) {
			fruit f1=new fruit(f_iter.next());
		}

		//			while(f_iter.hasNext()) {System.out.println(f_iter.next());}	
		//			int src_node = 0;  // arbitrary node, you should start at one of the fruits
		//			for(int a = 0;a<rs;a++) {
		//				game.addRobot(src_node+a);
		//			}
		//		}
		//		catch (JSONException e) {e.printStackTrace();}
		//		game.startGame();
		//		// should be a Thread!!!
		//		while(game.isRunning()) {
		//			moveRobots(game, gg);
		//		}
		//		String results = game.toString();
		//		System.out.println("Game Over: "+results);
	}


	//************************************** Contractor and private fields **************************************
	/**
	 * the game will ask the user insert a level until he will put a correct number : 0-23
	 */
	public MyGameGUI() {
		String scenario_num =JOptionPane.showInputDialog(this.game,"Choose a level");
		int level=Integer.parseInt(scenario_num);
		while(level>23||level<0) {
			JOptionPane.showMessageDialog((Component) this.game, "ERROR worng levell input, tyr again");
			scenario_num =JOptionPane.showInputDialog(this.game,"Choose a level");
			level=Integer.parseInt(scenario_num);
		}
		this.game=Game_Server.getServer(level);
		StdDraw.setCanvasSize(1000, 625);
		GraphInit();
	}

	private game_service game;
}//end of the class
