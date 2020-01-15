package gameClient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.dgc.DGC;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithems.Graph_Algo;
import dataStructure.*;
import oop_dataStructure.oop_graph;
import utils.*;

public class MyGameGUI implements Runnable {

	/**
	 * take the name of the picture of the earth to put at the canvas

	public void drayPicture (DGraph gg) {
		String gameDitales=this.game.toString();
		gameServerString gamePlayNow=new gameServerString(gameDitales);
		String allPicData=gamePlayNow.getgraphId();
		String [] picData =allPicData.split("/");
		Range middleOfCanvas=setRangeScale(gg);
		StdDraw.picture(middleOfCanvas.get_max(), middleOfCanvas.get_min(), picData[1]+".png");
	}*/

	/**
	 * this method is take the string with the details of the specific graph and make from it 
	 * the graph at the DGraph class with the method that create a graph from JSONString 
	 * and then send the graph to draw the nodes and the edges
	 */
	public void GraphInit() {
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		//drayPicture(gg);
		drawNodes(gg);
		drawEdges(gg);
	}

	/**
	 * this method drawing all the vertices of the graph 
	 */
	private void drawNodes(DGraph gg) {
		Range tt=setRangeScale(gg);
		Collection<node_data> vert=gg.getV();
		Iterator<node_data> vert_it=vert.iterator();
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.025);
		while(vert_it.hasNext()) {
			node_data tempV=vert_it.next();
			Point3D pp=new Point3D(tempV.getLocation());
			StdDraw.point(pp.x(), pp.y());

			//drawing the id number of the node
			StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));
			StdDraw.text(pp.x()+0.0004, pp.y()+0.0002, String.valueOf(tempV.getKey()));
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
				StdDraw.setPenRadius(0.035);
				StdDraw.point(pF1.x(), pF1.y());
			}
			else if(f1.getType()==1) {
				StdDraw.setPenColor(Color.DARK_GRAY);
				StdDraw.setPenRadius(0.035);
				StdDraw.point(pF1.x(), pF1.y());
			}
		}


	}

	/**
	 * 
	 */
	public void drawRobbots(DGraph gg) {
		List<String> fruits=this.game.getFruits();
		Iterator<String> f_iter = fruits.iterator();
		//running all over all the fruits
		/*while(f_iter.hasNext()) {
			fruit ff=new fruit(f_iter.next());
			Point3D ffP=new Point3D(ff.getlocation());
			//to every fruit run on all the graph to find his place and the put the robot near it
			Collection<node_data> vert=gg.getV();
			Iterator<node_data> vert_it=vert.iterator();
			while(vert_it.hasNext()) {
				node_data tempV=vert_it.next();
				Collection<edge_data> egdesVert=gg.getE(tempV.getKey());
				Iterator<edge_data> egdesVert_it=egdesVert.iterator();
				while(egdesVert_it.hasNext()) {
					edge_data ee=egdesVert_it.next();
					Point3D locSrc=new Point3D(gg.getNode(ee.getSrc()).getLocation().x(), 
							gg.getNode(ee.getSrc()).getLocation().y());
					Point3D locDest=new Point3D(gg.getNode(ee.getDest()).getLocation().x(), 
							gg.getNode(ee.getDest()).getLocation().y());
					//coagulating the distance of the line based on PITAGORAS
					double powerX=Math.pow((locSrc.x()-locDest.x()), 2);
					double powerY=Math.pow((locSrc.y()-locDest.y()), 2);
					double dd=Math.sqrt(powerX+powerY);
					//end all line coagulating

					if(ee.getSrc()<0) {

					}
				}


			}
		}
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
		}*/
		//int robbots =gamePlayNow.getNumRobbots();
		
		StdDraw.setPenColor(Color.ORANGE);
		StdDraw.setPenRadius(0.035);
		
		int rr=this.game.getRobots().size();
		for(int i=0;i<rr;i++) {
			this.game.addRobot(0+i);
		}
		this.game.startGame();
		while(game.isRunning()) {
			moveRobots(game, gg);
		}

	}

	
	private void moveRobots(game_service game, DGraph gg) {
		List<String> log = game.move();
		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++) {
				String robot_json = log.get(i);
				try {
					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
				
					if(dest==-1) {	
						
						game.chooseNextEdge(rid, dest);
						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						System.out.println(ttt);
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
	}
	/**
	 * this method gets the graph nodes and calculating the range of the X and 
	 * Y axis
	 * @param gg
	 */
	private Range setRangeScale (DGraph gg) {
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
		StdDraw.setXscale(minX-0.0006, maxX+0.0006);
		StdDraw.setYscale(minY-0.0006, maxY+0.0006);		

		Range middle =new Range(((-1*minX)+maxX)/2, ((-1*minY)+maxY)/2);
		//i am not use the range object "middle" at his purpose , 
		//i decided to put it not minimum and maximum,nut to put the
		//middle of x and middle of y scales to draw the picture of the map
		return middle;
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