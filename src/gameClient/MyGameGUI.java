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

public class MyGameGUI implements Runnable {
	
	/**
	 * take the name of the picture of the earth to put at the canvas
	 */
	public void drayPicture () {
		String gameDitales=this.game.toString();
		gameServerString gamePlayNow=new gameServerString(gameDitales);
		String allPicData=gamePlayNow.getgraphId();
		String [] picData =allPicData.split("/");
		StdDraw.picture(x, y, picData[1]);
	}
	
	/**
	 * this method is take the string with the details of the specific graph and make from it 
	 * the graph at the DGraph class with the method that create a graph from JSONString 
	 * and then send the graph to draw the nodes and the edges
	 */
	public void GraphInit() {
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		drawNodes(gg);
		drawEdges(gg);
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
	 * this method puts the fruits and the robbots at the first tome
	 * near to the fruits 
	 */
	public void drawFruitAndRobbots () {
		String gameDitales=this.game.toString();
		gameServerString gamePlayNow=new gameServerString(gameDitales);
		
		List<String> ff=this.game.getFruits();
		Iterator<String> ff_it=ff.iterator();
		while(ff_it.hasNext()) {
			fruit f1=new fruit(ff_it.next());
			Point3D pF1=new Point3D(f1.getlocation());
			if(f1.getType()==-1) {
				StdDraw.setPenColor(Color.ORANGE);
				StdDraw.setPenRadius(0.050);
				StdDraw.point(pF1.x(), pF1.y());
			}
			else if(f1.getType()==1) {
				StdDraw.setPenColor(Color.DARK_GRAY);
				StdDraw.setPenRadius(0.050);
				StdDraw.point(pF1.x(), pF1.y());
			}
		}
		
//		int robbots =gamePlayNow.getNumRobbots();
//		for(int i=0;i<robbots;i++) {
//			
//		}
		
	}

	/**
	 * this method puts the robbots at there placeT the first time 
	 */
	public void drawRobbots() {
		List<String> rr=this.game.getRobots();
		Iterator<String> rr_it=rr.iterator();
		StdDraw.setPenColor(Color.ORANGE);
		StdDraw.setPenRadius(0.050);
		int i=1;
		while(rr_it.hasNext()) {
			this.game.addRobot(i);
			robbot r1=new robbot(rr_it.next());
			Point3D pp=new Point3D(r1.getlocation());
			StdDraw.point(pp.x(), pp.y());
			i++;
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



	/**
	 * the game will ask the user insert a level until he will put a correct number : 0-23
	 */
	public MyGameGUI() {
		StdDraw.setCanvasSize(1200, 640);
		String scenario_num =JOptionPane.showInputDialog(this.game,"Choose a level to the game");
		int level=Integer.parseInt(scenario_num);
		while(level>23||level<0) {
			JOptionPane.showMessageDialog((Component) this.game, "ERROR, worng level input, please try again");
			scenario_num =JOptionPane.showInputDialog(this.game,"Choose a level to the game");
			level=Integer.parseInt(scenario_num);
		}
		this.game=Game_Server.getServer(level);
		Thread dd=new Thread(this);
		dd.start();
		drayPicture();
		GraphInit();
	}
	//************************************** private field **************************************
	private game_service game;
	//**********************************************************************************************************
	/**
	 * run method of the thread
	 */
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);} catch (InterruptedException e) {
				e.printStackTrace();}
			GraphInit();
			//drawRobbots();
			drawFruitAndRobbots ();
			
		}
	}
}//end of the class
