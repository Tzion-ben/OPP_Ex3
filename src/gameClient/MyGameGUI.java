package gameClient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.source.tree.Tree;

import Server.Game_Server;
import Server.game_service;
import algorithems.Graph_Algo;
import dataStructure.*;
import oop_dataStructure.oop_graph;
import utils.*;

public class MyGameGUI implements ActionListener, MouseListener, Runnable {

	/**
	 * take the name of the picture of the earth to put at the canvas
	 */
	//	private void drayPicture (DGraph gg) {
	//		String gameDitales=this.game.toString();
	//		gameServerString gamePlayNow=new gameServerString(gameDitales);
	//		String allPicData=gamePlayNow.getgraphId();
	//		String [] picData =allPicData.split("/");
	//		Range middleOfCanvas=setRangeScale(gg);
	//		StdDraw.picture(middleOfCanvas.get_max(), middleOfCanvas.get_min(), picData[1]+".png");
	//	}

	/**
	 * this method is take the string with the details of the specific graph and make from it 
	 * the graph at the DGraph class with the method that create a graph from JSONString 
	 * and then send the graph to draw the nodes and the edges
	 */
	private void GraphInit() {
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		//drayPicture(gg);
		drawNodes(gg);
		drawEdges(gg);
		if(!game.isRunning())
			chooserGame(gg);
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
	private void drawFruit () {
		//String gameDitales=this.game.toString();
		//gameServerString gamePlayNow=new gameServerString(gameDitales);
		int iRun=1;
		List<String> ff=this.game.getFruits();
		Iterator<String> ff_it=ff.iterator();
		while(ff_it.hasNext()) {
			fruit f1=new fruit(ff_it.next());
			Point3D pF1=new Point3D(f1.getlocation());
			if(f1.getType()==-1) {
				StdDraw.setPenColor(Color.ORANGE);
				StdDraw.setPenRadius(0.035);
				StdDraw.point(pF1.x(), pF1.y());
				StdDraw.setPenColor(Color.red);
				StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 20));
				StdDraw.text(pF1.x()+0.0004, pF1.y()+0.0002, "Fruit "+iRun);
				//StdDraw.picture(pF1.x(), pF1.y(), "banana.png", 100, 100);
				//StdDraw.picture(pF1.x(), pF1.y(), "banana.png");
			}
			else if(f1.getType()==1) {
				StdDraw.setPenColor(Color.DARK_GRAY);
				StdDraw.setPenRadius(0.035);
				StdDraw.point(pF1.x(), pF1.y());
				StdDraw.setPenColor(Color.red);
				StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 20));
				StdDraw.text(pF1.x()+0.0004, pF1.y()+0.0002, "Fruit "+iRun);
			}
			iRun++;
		}
	} 

	/**
	 * draw all the robots at the first time
	 */
	private void drawRobbots() {
		int iRun=1;
		List<String> robotoss=this.game.getRobots();
		Iterator<String> r_iter = robotoss.iterator();
		while(r_iter.hasNext()) {
			robbot rr=new robbot(r_iter.next());
			Point3D ppRob=new Point3D(rr.getlocation());
			StdDraw.setPenColor(Color.GREEN);
			StdDraw.setPenRadius(0.035);
			StdDraw.point(ppRob.x(), ppRob.y());
			StdDraw.setPenColor(Color.red);
			StdDraw.text(ppRob.x()+0.0004, ppRob.y()+0.0002, "Robot "+iRun);
			iRun++;
		}
	}

	/**
	 * this method movea all the robots for the manually game
	 */
	private void moveRobManually (DGraph gg) {
		while(this.game.isRunning()) {
			String chooseNode=JOptionPane.showInputDialog(this.game,"choose the next vertex which"
					+ " you want to move to");
			int nextVV=Integer.parseInt(chooseNode);
			//	this.game.chooseNextEdge(startVV, nextVV);
		}
	}

	/**
	 * 
	 * @param game
	 * @param gg
	 */
	//	private void moveRobots(game_service game, DGraph gg) {
	//		List<String> log = game.move();
	//		if(log!=null) {
	//			long t = game.timeToEnd();
	//			for(int i=0;i<log.size();i++) {
	//				String robot_json = log.get(i);
	//				try {
	//					JSONObject line = new JSONObject(robot_json);
	//					JSONObject ttt = line.getJSONObject("Robot");
	//					int rid = ttt.getInt("id");
	//					int src = ttt.getInt("src");
	//					int dest = ttt.getInt("dest");
	//
	//					if(dest==-1) {	
	//
	//						game.chooseNextEdge(rid, dest);
	//						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
	//						System.out.println(ttt);
	//					}
	//				} 
	//				catch (JSONException e) {e.printStackTrace();}
	//			}
	//		}
	//	}
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

	//********************************************manually game method************************************

	/**
	 * the manually game manger , puts the first robots at the chosen vertices, and send
	 * them to draw the robots and activate the manually Game
	 */
	private void manuallyGameManger(DGraph gg) {
		drawFruit ();
		String thisGame=this.game.toString();
		gameServerString thisGamee=new gameServerString(thisGame);
		int numRob=thisGamee.getNumRobbots();
		for(int i=0;i<numRob;i++) {
			String chooseStart=JOptionPane.showInputDialog(this.game,"choose the vertex"
					+ " that you want to start with robot number "+i); 
			int startVV=Integer.parseInt(chooseStart);
			this.game.addRobot(startVV);
		}
		drawRobbots();
		this.game.startGame();
		manuallyGame();
	} 

	/**
	 * 
	 */
	private void manuallyGame() {
		try {
			List<String> robotoss=this.game.getRobots();
			Thread tt=new Thread(this);
			while(this.game.isRunning()) {

				String chooseStart=JOptionPane.showInputDialog(this.game,"choose the vertex"
						+ " that you want to move to"); 
				int nextVV=Integer.parseInt(chooseStart);
				String rrToMove=JOptionPane.showInputDialog(this.game,"ctype your robot id"
						+ " ? 1 ?2? and so on..."); 
				int rrToMoveInt=Integer.parseInt(rrToMove)-1;
				robbot rr=new robbot(robotoss.get(rrToMoveInt));
				this.game.chooseNextEdge(rr.getId(), nextVV);
				tt.start();
				System.out.println(this.game.timeToEnd()/1000);
				if (this.game.timeToEnd()==0)
					this.game.stopGame();
			}		
		}
		catch (Exception e) {
			e.getStackTrace();}
	}

	//********************************************Game choosers********************************************

	/**
	 * this method is about to let the user decide if he want to play automatically or manually,have to write
	 * 0 to automatically or 1 to manually, and if he put other number it will run again until he puts 0 o 1
	 */
	private void chooserGame(DGraph gg) {
		String chooseStr="-1";
		int choose=-1;
		chooseStr =JOptionPane.showInputDialog(this.game,"Choose 1 to play"
				+ " manually or 0  to play automatically");
		choose=Integer.parseInt(chooseStr);
		while(choose!=0&&choose!=1) {
			chooseStr=JOptionPane.showInputDialog( this.game, "ERROR, worng level input, please try again");
			choose=Integer.parseInt(chooseStr);
		}
		if(choose==1) 
			manuallyGameManger(gg);
		//else if(choose==0)
		//automaticallyGame(gg);
	}

	/**
	 * this mathod is let to the user to choose the level of the game that he want
	 * to play
	 */
	private void chooseLevel() {
		int level=0;
		String scenario_num="";
		try {
			scenario_num =JOptionPane.showInputDialog(this.game,"Choose a level to the game");
			level=Integer.parseInt(scenario_num);
			while(level>23||level<0) {
				JOptionPane.showMessageDialog((Component) this.game, "ERROR, worng level input, please try again");
				scenario_num =JOptionPane.showInputDialog(this.game,"Choose a level to the game");
				level=Integer.parseInt(scenario_num);
			}
			this.game=Game_Server.getServer(level);
			GraphInit();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog( null, this, "ERROR, worng level input", level);
		}
	}
	//*************************************************************************************************

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {


	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	//************************************** Contractor **************************************
	/**
	 * the game will ask the user insert a level until he will put a correct number : 0-23
	 */
	public MyGameGUI() {
		StdDraw.setCanvasSize(1200, 640);
		chooseLevel();
	}
	//************************************** private field **************************************
	private game_service game;
	//**********************************************************************************************************

	/*******************************the Thread run method ******************************************************
	/**
	 * run method of the thread
	 */
	@Override
	public void run() {  
		try {
			while(true) {
				this.game.move();
				StdDraw.clear();
				StdDraw.enableDoubleBuffering();
				GraphInit();
				drawFruit();
				drawRobbots();
				StdDraw.disableDoubleBuffering();
				StdDraw.show();
				Thread.sleep(100);
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}//end of the class