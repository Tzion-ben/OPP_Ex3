package gameClient;
/**
 * this class is a help class to the gameGUI, this class include all the logically methods
 * that help to calculate all the things for the manually and automatically games
 * @author tzion
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import algorithems.*;
import utils.Point3D;
import utils.Range;

//*******************************contractor *******************************
public class gameLogicaly {
	public gameLogicaly() {;}
	//**************************************************************************
	/***********************************************************************************************
	 * this method guts the graph and the game at the specific level and calculate the place of the 
	 * fruit on it for the automatically game
	 * @param gg
	 * @param game
	 * @return ArryList of all the closely vertexes to all the fruits
	 ***********************************************************************************************/
	public ArrayList<Integer> calculateFriutPosionToEdge(DGraph gg,game_service game) {
		ArrayList<Integer> srces=new ArrayList<Integer>();

		Collection<node_data> vv=gg.getV();
		Iterator<node_data> vv_iter=vv.iterator();

		List<String> fruitss=game.getFruits();
		Iterator<String> f_iter=fruitss.iterator();
		while(f_iter.hasNext()) {
			fruit ff=new fruit(f_iter.next());
			Point3D pF=new Point3D(ff.getlocation());
			//**********takes the position of the fruit
			while(vv_iter.hasNext()) {
				node_data nn=vv_iter.next();
				Point3D nnPPSrc=nn.getLocation(); 
				//of 1 maximum from the fruit them work on his edges
				Collection<edge_data> eVV=gg.getE(nn.getKey());
				Iterator <edge_data> ee=eVV.iterator();
				//iterate on all his edges
				while(ee.hasNext()) {
					edge_data tempEE=ee.next();
					int srcE=tempEE.getSrc();
					int destE=tempEE.getDest();
					Point3D destEPoint=new Point3D(gg.getNode(destE).getLocation());
					//calculating the distance of the edge
					double destanceE =nnPPSrc.distance2D(destEPoint);
					String destanceETo4Digits=new DecimalFormat("0.0000").format(destanceE);
					double destanceEAfter=Double.parseDouble(destanceETo4Digits);
					//calculating the distance from the fruit to the one side of the edge
					double destanceEFS1 =nnPPSrc.distance2D(pF);
					String destanceEFS1To4Digits=new DecimalFormat("0.0000").format(destanceEFS1);
					double destanceEFS1After=Double.parseDouble(destanceEFS1To4Digits);
					//calculating the distance from the fruit to the one side of the edge
					double destanceEFS2 =destEPoint.distance2D(pF);
					String destanceEFS2To4Digits=new DecimalFormat("0.0000").format(destanceEFS2);
					double destanceEFS2After=Double.parseDouble(destanceEFS2To4Digits);
					if((destanceEFS1After+destanceEFS2After)<=destanceEAfter) { 
						srces.add(srcE);
						srces.add(destE);
					}
				}
			}
		}
		if(srces.isEmpty())
			srces.add(-1);
		return srces;
	}

	/*****************************************************************************************************
	 * this method returns the specific destination of the edge that on it the fruit is
	 * @return
	 *****************************************************************************************************/
	public int getFruitEdgeDest (fruit ff,DGraph gg,game_service game) {
		int destff=-1;

		Collection<node_data> vv=gg.getV();
		Iterator<node_data> vv_iter=vv.iterator();

		while(vv_iter.hasNext()) {
			Point3D pF=new Point3D(ff.getlocation());
			//**********takes the position of the fruit
			node_data nn=vv_iter.next();
			Point3D nnPPSrc=nn.getLocation(); 
			//take the source of the node
			Collection<edge_data> eVV=gg.getE(nn.getKey());
			Iterator <edge_data> ee=eVV.iterator();
			//iterate on all his edges
			while(ee.hasNext()) {
				edge_data tempEE=ee.next();
				int destE=tempEE.getDest();
				Point3D destEPoint=new Point3D(gg.getNode(destE).getLocation());
				//calculating the distance of the edge
				double destanceE =nnPPSrc.distance2D(destEPoint);
				String destanceETo4Digits=new DecimalFormat("0.0000").format(destanceE);
				double destanceEAfter=Double.parseDouble(destanceETo4Digits);
				//calculating the distance from the fruit to the one side of the edge
				double destanceEFS1 =nnPPSrc.distance2D(pF);
				String destanceEFS1To4Digits=new DecimalFormat("0.0000").format(destanceEFS1);
				double destanceEFS1After=Double.parseDouble(destanceEFS1To4Digits);
				//calculating the distance from the fruit to the one side of the edge
				double destanceEFS2 =destEPoint.distance2D(pF);
				String destanceEFS2To4Digits=new DecimalFormat("0.0000").format(destanceEFS2);
				double destanceEFS2After=Double.parseDouble(destanceEFS2To4Digits);
				///*****************
				double allDistPlus=destanceEFS1After+destanceEFS2After;
				String allDistPlusString=new DecimalFormat("0.0000").format(allDistPlus);
				double allDistPlus4Digits=Double.parseDouble(allDistPlusString);
				if(allDistPlus4Digits==destanceEAfter)
					//if((destanceEFS1After+destanceEFS2After)+0.000001==destanceEAfter) 
					destff=destE;
			}
		}
		return destff;
	}

	/********************************************************************************************************
	 * this method returns the location to put the clock on the screen of the game
	 * @param gg
	 * @return the point to put the time at the game
	 ********************************************************************************************************/
	public Range setRangeScaleToClock(DGraph gg) {
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
		Range middleX =new Range(minX, maxX);
		double xMid=(middleX.get_length()/2)+minX+0.0035;
		Range middleY =new Range(minY, maxY);
		double yMid=(middleY.get_length()/2)+minY+0.0035;
		Range corner =new Range(xMid, yMid);
		//i am not use the range object "middle" at his purpose , 
		//i decided to put it not minimum and maximum,nut to put the
		//middle of x and middle of y scales to draw the picture of the map
		return corner;
	}

	/*******************************************************************************************************
	 * this method return the shortest path distance from the robot to the dest node of the fruit
	 * @param srcV
	 * @param destV
	 * @param gg
	 * @return the shortest path distance from src to dest
	 *******************************************************************************************************/
	public double theBestWayToFruitDist (int srcV,int destV,DGraph gg) {
		double toReturn=-1;
		Graph_Algo ggALGO=new Graph_Algo(gg);
		if(srcV!=destV)
			toReturn=ggALGO.shortestPathDist(srcV, destV);		
		return toReturn;
	}

	/*******************************************************************************************************
	 * this method return the shortest path from the robot to the fruit
	 * @param srcV
	 * @param destV
	 * @param gg
	 * @return the shortest payh
	 *******************************************************************************************************/
	public List<node_data> theBestWayToFruit (int srcV,int destV,DGraph gg) {
		Graph_Algo ggALGO=new Graph_Algo(gg);
		List <node_data> ansPath=ggALGO.shortestPath(srcV, destV);	
		return ansPath;
	}
}// end of the class