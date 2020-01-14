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
		drawEdges(gg);
		String info = game.toString();
		List<String> fruits=this.game.getFruits();
		Iterator<String> f_iter = fruits.iterator();
		while(f_iter.hasNext()) {
			fruit f1=new fruit(f_iter.next());
		}

		/**
		 * this method drawing all the vertices of the graph 
		 */
	}
	private void drawNodes(DGraph gg) {
		setRangeScale(gg);
		Collection<node_data> vert=gg.getV();
		Iterator<node_data> vert_it=vert.iterator();
		StdDraw.setPenColor(Color.red);
		StdDraw.setPenRadius(0.040);
		while(vert_it.hasNext()) {
			node_data tempV=vert_it.next();
			Point3D pp=new Point3D(tempV.getLocation());
			StdDraw.point(pp.x(), pp.y());
		}
	}

	/**
	 * this method is drawing all the edges of the graph
	 * @param gg
	 */
	private void drawEdges(DGraph gg) {
		StdDraw.setPenColor(Color.BLUE);
		StdDraw.setPenRadius(0.005);
		Collection<node_data> vert=gg.getV();
		Iterator<node_data> vert_it=vert.iterator();
		while(vert_it.hasNext()) {
			node_data tempV=vert_it.next();
			Collection<edge_data> egdesVert=gg.getE(tempV.getKey());
			Iterator<edge_data> egdesVert_it=egdesVert.iterator();
			while(egdesVert_it.hasNext()) {
				edge_data ee=egdesVert_it.next();
				Point3D srcV=new Point3D(gg.getNode(ee.getSrc()).getLocation());
				Point3D destV=new Point3D(gg.getNode(ee.getDest()).getLocation());
				StdDraw.line(srcV.x(), srcV.y(), destV.x(), destV.y());
			}
		}
	}
	
	/**
	 * this method gets the graph nodes and calculating the range of the X and 
	 * Y axis
	 * @param gg
	 */
	private void setRangeScale (DGraph gg) {
		double minX=Double.MAX_VALUE,minY=Double.MAX_VALUE;
		double maxX=Double.MIN_VALUE,maxY=Double.MIN_VALUE;
		Collection<node_data> vert=gg.getV();
		Iterator<node_data> vert_it=vert.iterator();
		while(vert_it.hasNext()) {
			node_data tt=vert_it.next();
			Point3D pp=new Point3D(tt.getLocation());
			if(pp.x()>maxX) 
				maxX=pp.x();
			if(pp.x()<minX) 
				minX=pp.x();
			if(pp.y()>maxY)
				maxY=pp.y();
			if(pp.y()<minY)
				minY=pp.y();
		}
		StdDraw.setXscale(minX-0.00020, maxX+0.00020);
		StdDraw.setYscale(minY-0.00020, maxY+0.00020);		
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
		StdDraw.setCanvasSize(1200, 640);
		GraphInit();
	}

	private game_service game;
}//end of the class
