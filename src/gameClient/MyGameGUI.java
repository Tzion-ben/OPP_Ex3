package gameClient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
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
		drawNodes(gg);
		String info = game.toString();
		List<String> fruits=this.game.getFruits();
		Iterator<String> f_iter = fruits.iterator();
		while(f_iter.hasNext()) {
			fruit f1=new fruit(f_iter.next());
		}
		

	}
	public void drawNodes(DGraph gg) {
		Collection<node_data> vert=gg.getV();
		Iterator<node_data> vert_it=vert.iterator();
		StdDraw.setPenColor(Color.red);
		StdDraw.setPenRadius(0.005);
		StdDraw.setXscale(-100, 100);
		StdDraw.setYscale(-50, 50);
		while(vert_it.hasNext()) {
			node_data tempV=vert_it.next();
			Point3D pp=new Point3D(tempV.getLocation());
			
			StdDraw.point(pp.x(), pp.y());
		}
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
