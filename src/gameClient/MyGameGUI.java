package gameClient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
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

public class MyGameGUI extends JFrame implements ActionListener, MouseListener, Runnable {

	/**
	 * take the name of the picture of the earth to put at the canvas
	 */
	private void drawPicture (DGraph gg,Point3D pp) {
		String gameDitales=this.game.toString();
		gameServerString gamePlayNow=new gameServerString(gameDitales);
		try {
			String allPicData=gamePlayNow.getgraphId();
			String [] justName=allPicData.split("/");
			String fullName=justName[1];
			String allPath
			="C:\\Users\\tzion\\Desktop\\java Progects\\Ex3_v2\\data\\"+fullName+".png";		
			StdDraw.picture(pp.x(), pp.y(), allPath);
		}catch (Exception e){e.getStackTrace();}
	}

	/**
	 * this method is take the string with the details of the specific graph and make from it 
	 * the graph at the DGraph class with the method that create a graph from JSONString 
	 * and then send the graph to draw the nodes and the edges
	 */
	private void GraphInit() {
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
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
		Point3D ppMap=new Point3D(tt.get_min(), tt.get_max());
		drawPicture(gg ,ppMap);
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
		List<String> ff=this.game.getFruits();
		Iterator<String> ff_it=ff.iterator();
		while(ff_it.hasNext()) {
			fruit f1=new fruit(ff_it.next());
			Point3D pF1=new Point3D(f1.getlocation());
			if(f1.getType()==-1) {

				StdDraw.setPenColor(Color.red);
				StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 20));
				StdDraw.picture(pF1.x(), pF1.y(), "C:\\Users\\tzion\\Desktop\\java Progects\\Ex3_v2\\icons\\"
						+ "banana.png", 0.0012, 0.0012);
			}
			else if(f1.getType()==1) {
				StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 20));
				StdDraw.picture(pF1.x(), pF1.y(), "C:\\Users\\tzion\\Desktop\\java Progects\\Ex3_v2\\icons\\"
						+ "apple.png", 0.0012, 0.0012);
			}

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
			StdDraw.setPenColor(Color.red);
			StdDraw.text(ppRob.x()+0.0004, ppRob.y()+0.0002, "Robot "+iRun);
			StdDraw.picture(ppRob.x(), ppRob.y(), "C:\\Users\\tzion\\Desktop\\java Progects\\Ex3_v2\\icons\\"
					+ "robot.jpg", 0.0012, 0.0012);
			iRun++;
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

		Range middleX =new Range(minX, maxX);
		double xMid=(middleX.get_length()/2)+minX;
		Range middleY =new Range(minY, maxY);
		double yMid=(middleY.get_length()/2)+minY;
		Range middle =new Range(xMid, yMid);
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
		for(int i=1;i<=numRob;i++) {
			try {
				String chooseStart=JOptionPane.showInputDialog(this,"choose the vertex"
						+ " that you want to start with robot number "+i); 
				int startVV=Integer.parseInt(chooseStart);
				this.game.addRobot(startVV);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(this, "such a vertex is doesn't exist so please try again");
				manuallyGameManger(gg);
			}
		}
		drawRobbots();
		manuallyGame();
	} 

	/**
	 * this method is activate the manually game with all the drawing mehtods and the thread, 
	 * and in the end it print out the scores of all the robots
	 */
	private void manuallyGame() {
		try {
			List<String> robotoss=this.game.getRobots();
			Thread tt=new Thread(this);
			this.game.startGame();
			tt.start();
			while(this.game.isRunning()) {
				Thread.sleep(1000);
				String chooseStart=JOptionPane.showInputDialog(this,"choose the vertex"
						+ " that you want to move to"); 
				int nextVV=Integer.parseInt(chooseStart);
				String rrToMove=JOptionPane.showInputDialog(this,"ctype your robot id"
						+ " ? 1 ?2? and so on..."); 
				int rrToMoveInt=Integer.parseInt(rrToMove)-1;
				robbot rr=new robbot(robotoss.get(rrToMoveInt));
				this.game.chooseNextEdge(rr.getId(), nextVV);

				if(this.game.timeToEnd()/1000==0) {
					StdDraw.clear();
					JOptionPane.showMessageDialog(this, "The time is end, GAME OVER");
					this.game.stopGame();
				}
			}//end of the while loop
			//**********iterator to print the score of all the robots after the game
			List<String> robotosAfter=this.game.getRobots();
			Iterator<String> r_after=robotosAfter.iterator();
			int iRun=1;
			while(r_after.hasNext()) {
				robbot rrr= new robbot(r_after.next());
				Thread.sleep(1000);
				JOptionPane.showMessageDialog(this, "The score of the robot "+iRun+" is :"+rrr.getValue());
			}
		}
		catch (Exception e) {
			e.getStackTrace();
		}
	}

	//****************************************automatically game method************************************
	/**
	 * the automatically game manger , puts the first robots at the chosen vertices with the logical algorithm
	 * at the gameLogicaly class
	 * and send them to draw the robots and activate the manually Game
	 */
	private void automaticallyGameManger(DGraph gg) {
		drawFruit ();
		String thisGame=this.game.toString();
		gameServerString thisGamee=new gameServerString(thisGame);
		int numRob=thisGamee.getNumRobbots();
		ArrayList<Integer> srcesOfPosFruit =logicHelp.calculateFriutPosionToEdge(gg, game);
		int sizeSRC=srcesOfPosFruit.size();
		//if number of the robots is less then the number of the edges:
		if(numRob<sizeSRC) {
			for(int i=1;i<=numRob;i++) 
				this.game.addRobot(srcesOfPosFruit.get(i));
		}
		else {
			int tt=-1;
			Iterator<Integer> ssrrcc=srcesOfPosFruit.iterator();
			while(ssrrcc.hasNext()) {
				tt=ssrrcc.next();
				for(int i=1;i<=numRob;i++) 
					this.game.addRobot(tt);
			}
		}
		drawRobbots();
		automaticallyGame(gg);
	}

	/**
	 * this method is activate the automatically game with all the drawing methods and the thread, 
	 * and in the end it print out the scores of all the robots
	 */
	private void automaticallyGame(DGraph gg) {
		List <String> rr=this.game.getRobots();
		List <String> ff=this.game.getFruits();

		while(this.game.isRunning()) {
			for(int i=1;i<=rr.size();i++) {
				robbot rtemp=new robbot(rr.get(i));
				double theBestPathDist=Double.MAX_VALUE;
				int theBestDestId=-1;
				int srcOFrobot=rtemp.getSrc();
				//take the every robot and runs on all the fruits
				for(int j=0;j<ff.size();j++) {
					fruit ffTemp=new fruit(ff.get(j));
					int destFruit=this.logicHelp.getFruitEdgeDest(ffTemp, gg, this.game);
					double tempPathDist=this.logicHelp.theBestWayToFruitDist(srcOFrobot, destFruit, gg);
					//****the shortedtdist from the robot to fruit
					if(tempPathDist<theBestPathDist) {
						theBestPathDist=tempPathDist;
						theBestDestId=destFruit;
					}
				}//end of the for of fruits
				this.game.chooseNextEdge(rtemp.getId(), );
			}

		}
	}
	//********************************************Game choosers********************************************

	/**
	 * this method is about to let the user decide if he want to play automatically or manually,have to write
	 * 0 to automatically or 1 to manually, and if he put other number it will run again until he puts 0 o 1
	 */
	private void chooserGame(DGraph gg) {
		try {
			String chooseStr="-1";
			int choose=-1;
			chooseStr =JOptionPane.showInputDialog(this,"Choose 1 to play"
					+ " manually or 0  to play automatically");
			choose=Integer.parseInt(chooseStr);
			while(choose!=0&&choose!=1) {
				chooseStr=JOptionPane.showInputDialog( this, "ERROR, worng level input, please try again");
				choose=Integer.parseInt(chooseStr);
			}
			if(choose==1) 
				manuallyGameManger(gg);
			else if(choose==0)
				automaticallyGameManger(gg);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, "ERROR, worng level input ,RUN IT AGAIN");
			ExceptionOFGAME();
		}
	}

	/**
	 * this mathod is let to the user to choose the level of the game that he want
	 * to play
	 */
	private void chooseLevel() {
		int level=0;
		String scenario_num="";
		try {
			scenario_num =JOptionPane.showInputDialog(this,"Choose a level to the game");
			level=Integer.parseInt(scenario_num);
			while(level>23||level<0) {
				JOptionPane.showMessageDialog( this, "ERROR, worng level input, please try again");
				scenario_num =JOptionPane.showInputDialog(this,"Choose a level to the game");
				level=Integer.parseInt(scenario_num);
			}
			this.game=Game_Server.getServer(level);
			GraphInit();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, "ERROR, worng level input ,RUN IT AGAIN");
			ExceptionOFGAME();
		}
	}
	/*************************************************************************************************
	 This private method is works only if there is some Exception, and this method allows to the user 
	 start the again with new GUI all the GUI will be new and let the user play again	
	 *************************************************************************************************/
	private void ExceptionOFGAME() {
		chooseLevel();
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
	public MyGameGUI() {
		StdDraw.setCanvasSize(1200, 640);
		chooseLevel();
	}
	//************************************** private field **************************************
	private game_service game;
	private gameLogicaly logicHelp=new gameLogicaly();
	//**********************************************************************************************************

	/*******************************the Thread run method ******************************************************
	/**
	 * run method of the thread
	 */
	@Override
	public void run() {  
		try {
			while(this.game.timeToEnd()/1000!=0) {
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
		catch (InterruptedException e) {e.printStackTrace();}
	}
}//end of the class