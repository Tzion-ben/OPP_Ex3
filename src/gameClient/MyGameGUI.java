package gameClient;
/**
 * This class represent game GUI of game that based on directional graph, on the edges  have a
 * fruits and robots and the robots competing between them on hoe will eat more fruits , 
 * every fruit gut a grade(value).
 * The class represent a manually ant automatically games , depend on the chose of the user
 * Another small thing that this class create , this class take the samples for the KML file, 
 * for the KML_Logger class.
 * @author tzion
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Server.Game_Server;
import Server.game_service;
import dataStructure.*;
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
			="data\\"+fullName+".png";		
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
				StdDraw.picture(pF1.x(), pF1.y(), "icons\\"
						+ "banana.png", 0.0012, 0.0012);
			}
			else if(f1.getType()==1) {
				StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 20));
				StdDraw.picture(pF1.x(), pF1.y(), "icons\\"
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
			StdDraw.picture(ppRob.x(), ppRob.y(), "icons\\"
					+ "robot.jpg", 0.0012, 0.0012);
			iRun++;
		}
	}

	/**
	 * this method draw the time on the screen
	 */
	private void drawTime() {
		String timeToEnd=String.valueOf(this.game.timeToEnd()/1000);
		StdDraw.setPenColor(Color.GRAY);
		StdDraw.setFont(new Font("Allegro", Font.HANGING_BASELINE, 20));
		StdDraw.text(35.20123001129944, 32.10820000084034,  "The time to end of the game "+timeToEnd);
		String thisGame=this.game.toString();
		gameServerString thisGamee=new gameServerString(thisGame);
		StdDraw.text(35.20123001129944, 32.10780000092025,  "Number of moves is  "+thisGamee.getNumMoves());	}
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
		manuallyGame(gg);
	} 

	/**
	 * this method is activate the manually game with all the drawing mehtods and the thread, 
	 * and in the end it print out the scores of all the robots
	 */
	private void manuallyGame(DGraph gg) {
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
				//this.game.move();
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
		//numderOfRobots

		List<String> fruitt=this.game.getFruits();
		Iterator<String> fruitt_iter=fruitt.iterator();
		fruit ff=new fruit(fruitt_iter.next());
		int destF=this.logicHelp.fruitDEST(ff, gg, game);
		//get the first fruit at the fruit list
		int numNODES=gg.getV().size();

		//if number of the robots is less then the number of the edges:
		for(int i=0;i<numRob;i++) {
			int type=ff.getType();
			if(destF!=0) {
				if(type==1) {
					destF--;
					this.game.addRobot(destF);
				}
				else {
					destF++;
					this.game.addRobot(destF);
				}
			}
			else {
				if(type==1) {
					this.game.addRobot(numNODES-1);
					destF++;
				}
				else {
					destF++;
					this.game.addRobot(destF);
				}
			}
		}

		drawRobbots();
		automaticallyGame(gg);
	}

	/*************************************************************************************************************
	 * this method is activate the automatically game with all the drawing methods and the thread, 
	 * and in the end it print out the scores of all the robots
	 ************************************************************************************************************/
	private void automaticallyGame(DGraph gg) {
		List <String> rr=this.game.getRobots();
		List <String> ff=this.game.getFruits();

		ArrayList<String> PlaceMarks=new ArrayList<String>();
		ArrayList<String> icons=new ArrayList<String>();
		setIcons(icons);
		setPalceMarks(PlaceMarks);

		Thread tt=new Thread(this);
		this.game.startGame();
		tt.start();

		while(this.game.isRunning()) {
			rr=this.game.getRobots();
			ff=this.game.getFruits();
			for(int i=0;i<rr.size();i++) {
				robbot rtemp=new robbot(rr.get(i));
				double theBestPathDist=Integer.MAX_VALUE;

				int theBestDestId=rtemp.getDest();
				int srcOFrobot=rtemp.getSrc();
				//take the every robot and runs on all the fruits

				/*******************************************************************8
				 * CHANGE HERE TO WHILE LOOP !!
				 * to jump to the next robot !!
				 */
				for(int j=0;j<ff.size();j++) {
					fruit ffTemp=new fruit(ff.get(j));
					ArrayList<Integer> postion=this.logicHelp.calculateFriutPosionToEdge(ffTemp, gg, game);
					//returns the src and the dest of the edge that the fruit is on it

					int destFruit=this.logicHelp.fruitDEST(ffTemp, gg, game);			
					//the dest of the fruit depend on it's type

					double tempPathDist=this.logicHelp.theBestWayToFruitDist(srcOFrobot,destFruit, gg);
					//****the shortedtdist from the robot to fruit
					if(tempPathDist!=-1) {
						if((tempPathDist<theBestPathDist)&&(this.game.timeToEnd()/1000!=0)) {
							theBestPathDist=tempPathDist;
							theBestDestId=destFruit;
						}
					}

					else {
						if(destFruit==postion.get(0))
							theBestDestId=postion.get(1);
						else
							theBestDestId=postion.get(0);		
					}
				}//end of the for of fruits
				if(this.game.timeToEnd()/1000==0) {
					StdDraw.clear();
					JOptionPane.showMessageDialog(this, "The time is end, GAME OVER");
					this.game.stopGame();
				}
				List<node_data> THEPATH=this.logicHelp.theBestWayToFruit(srcOFrobot, theBestDestId, gg);
				if(THEPATH!=null) {
					Iterator<node_data> path_it=THEPATH.iterator();
					while(path_it.hasNext()) {
						this.game.chooseNextEdge(rtemp.getId(),path_it.next().getKey());
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						this.game.move();
						setPalceMarks(PlaceMarks);
					}
				}
			}
		}//end of the while loop
		//**********iterator to print the score of all the robots after the game

		List<String> robotosAfter=this.game.getRobots();
		Iterator<String> r_after=robotosAfter.iterator();
		int iRun=1;
		while(r_after.hasNext()) {
			robbot rrr= new robbot(r_after.next());
			try {
				Thread.sleep(1000);}
			catch (InterruptedException e) {e.printStackTrace();}
			JOptionPane.showMessageDialog(this, "The score of the robot "+iRun+" is :"+rrr.getValue());
		}
		try {
			initKMLfile(icons, PlaceMarks, this.level);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/******************************************************************************************************
	 * this method make a samples of placemarks by KML_Logger class to create with the the KML file of the 
	 * specific level
	 *****************************************************************************************************/
	public void setPalceMarks (ArrayList<String> PlaceMarks) {
		List <String> rr=this.game.getRobots();
		List <String> ff=this.game.getFruits();

		//loop for the position of the robots
		for(int i=0;i<rr.size();i++) {
			robbot roTemp=new robbot(rr.get(i));
			String ttPlaceMark=this.newFileKMLforGame.placeMark(roTemp.getIconName(), roTemp.getlocation());
			PlaceMarks.add(ttPlaceMark);
		}//end for loop

		//loop for the position of the fruits
		for(int i=0;i<ff.size();i++) {
			fruit ffTemp=new fruit(ff.get(i));
			ffTemp.setIconID(i);
			ffTemp.setIconName(i);
			String ffPlaceMark=this.newFileKMLforGame.placeMark(ffTemp.getIconName(), ffTemp.getlocation());
			PlaceMarks.add(ffPlaceMark); 
		}//end for loop
	}

	/***************************************************************************************************
	 * this method create all the icons of the robots and the fruit for the KML file and sets the 
	 * fruit id that will be used at the KML file
	 ***************************************************************************************************/
	private void setIcons(ArrayList<String> icons) {
		List <String> rr=this.game.getRobots();
		List <String> ff=this.game.getFruits();
		Iterator<String> rr_it=rr.iterator();
		Iterator<String> ff_it=ff.iterator();

		while(rr_it.hasNext()){
			robbot rtemp=new robbot(rr_it.next());
			String iconRR=this.newFileKMLforGame.createIconStyle(rtemp.getIconName(), rtemp.getIconAddress());
			icons.add(iconRR);
		}//*************finished add all the robots to the icon list 

		int iRunff=0;
		while(ff_it.hasNext()) {
			fruit ftemp=new fruit(ff_it.next());
			ftemp.setIconID(iRunff);
			ftemp.setIconName(iRunff);

			String iconFF=this.newFileKMLforGame.createIconStyle(ftemp.getIconName(), ftemp.getIconAddress());
			icons.add(iconFF);
			iRunff++;
		}
	}

	//********************************************Game choosers********************************************

	/**
	 * this method is about to let the user decide if he want to play automatically or manually,have to write
	 * 0 to automatically or 1 to manually, and if he put other number it will run again until he press 0 o 1
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
			if(choose==1) {
				manuallyGameManger(gg);
				this.autoORmanual=1;
			}
			else if(choose==0) {
				automaticallyGameManger(gg);
				this.autoORmanual=0;
			}
		}
		catch (Exception e) {
			//JOptionPane.showMessageDialog(this, "ERROR, worng level input ,RUN IT AGAIN");
			System.out.println(e.getStackTrace().toString());
			ExceptionOFGAME();
		}
	}

	/***************************************************************************************************************
	 * this method is let to the user to choose the level of the game that he want
	 * to play
	 ***************************************************************************************************************/
	public void chooseLevel() {
		loGin();
		int level=0;
		String scenario_num="";
		try {
			scenario_num =JOptionPane.showInputDialog(this,"Choose a level to the game");
			level=Integer.parseInt(scenario_num);
			while(level>23||level<-10) {
				JOptionPane.showMessageDialog( this, "ERROR, worng level input, please try again");
				scenario_num =JOptionPane.showInputDialog(this,"Choose a level to the game");
				level=Integer.parseInt(scenario_num);
			}
			this.game=Game_Server.getServer(level);
			this.level=level;
			GraphInit();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, "ERROR, worng level input ,RUN IT AGAIN");
			ExceptionOFGAME();
		}
	}

	/**************************************************************************************************
	 * this method use the KML_Logger class to create the KML file with all the details that was collected
	 * until now: it include all the placemarks and the  icons 
	 * @param file
	 * @throws IOException
	 **************************************************************************************************/
	public void initKMLfile (ArrayList<String> icons,ArrayList<String> placeMarks, int level)
			throws IOException {
		String fileKML = "data\\"+level+".kml";
		this.newFileKMLforGame.alltDoc(icons, placeMarks, level,fileKML);
	}
	/*************************************************************************************************
	 * This private method is works only if there is some Exception, and this method allows to the user 
	 * start the again with new GUI all the GUI will be new and let the user play again	
	 *************************************************************************************************/
	private void ExceptionOFGAME() {
		chooseLevel();
	}
	//*************************************************************************************************

	/**************************************************************************************************
	 *this method let the user write his id and login
	 **************************************************************************************************/
	public void loGin() {
		String idString=JOptionPane.showInputDialog(this,"Enter your ID to play");
		while(idString.length()!=9) {
			JOptionPane.showMessageDialog( this, "ERROR, worng ID, please try again");
			idString=JOptionPane.showInputDialog(this,"Enter your ID to play");
		}
		Integer id =Integer.parseInt(idString);
		Game_Server.login(id);
	}
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
	KML_Logger newFileKMLforGame=new KML_Logger();
	private int level;
	private int autoORmanual;
	//auto==0 , manual==1
	//**********************************************************************************************************

	/*******************************the Thread run method ******************************************************
	/**
	 * run method of the thread
	 */
	@Override
	public void run() {  
		try {
			while(this.game.timeToEnd()/1000!=0) {
				if(this.autoORmanual==1)
					this.game.move();
				StdDraw.clear();
				StdDraw.enableDoubleBuffering();
				GraphInit();
				drawFruit();
				drawRobbots();
				drawTime();
				StdDraw.disableDoubleBuffering();
				StdDraw.show();
				Thread.sleep(100);
			}
		}
		catch (InterruptedException e) {e.printStackTrace();}
	}
}//end of the class