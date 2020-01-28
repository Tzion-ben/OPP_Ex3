package gameClient;
/**
 * This class is a help class to the gameGUI, this class include all the logically methods
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
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.node_data;
import algorithems.*;
import utils.Point3D;

//*******************************constructor *******************************
public class gameLogicaly {
	public gameLogicaly() {;}
	//**************************************************************************
	/***********************************************************************************************
	 * this method guts the graph and the game at the specific level and calculate the place of the 
	 * fruit on it for the automatically game, and returns arrayList with id's of src and the dest of the edge that
	 * the fruit on it
	 * @param gg
	 * @param game
	 * @return ArryList of all the closely vertexes to all the fruits
	 ***********************************************************************************************/
	public ArrayList<Integer> calculateFriutPosionToEdge(fruit ff,DGraph gg,game_service game) {
		ArrayList<Integer> Position=new ArrayList<Integer>();

		Collection<node_data> vv=gg.getV();
		Iterator<node_data> vv_iter=vv.iterator();

		Point3D ffP=ff.getlocation();
		//the location of the fruit
		while(vv_iter.hasNext()) {
			node_data nn=vv_iter.next();
			Point3D src=nn.getLocation(); 
			//takes the next node and it's location
			Collection<edge_data> ee=gg.getE(nn.getKey());
			Iterator <edge_data> ee_iter=ee.iterator();
			//iterate all the edges of the node
			while(ee_iter.hasNext()) {
				edge_data eee=ee_iter.next();
				Point3D dest=gg.getNode(eee.getDest()).getLocation();
				//location of the dest node of that edge

				double distAllE=src.distance2D(dest);
				double distFtoSrc=src.distance2D(ffP);
				double distFtoDest=ffP.distance2D(dest);
				if(Position.size()!=2) {
					if((distFtoSrc+distFtoDest)<=distAllE+0.00000001) { 
						Position.add(eee.getSrc());
						Position.add(eee.getDest());
					}
				}
			}//end while
		}
		if(Position.isEmpty())
			Position.add(-1);
		return Position;
	}

	/*******************************************************************************************************
	 * this method guts the fruit and return the dest of the fruit according to the fruit type
	 *******************************************************************************************************/
	public int fruitDEST(fruit ff,DGraph gg,game_service game) {
		int destF=-1;
		ArrayList<Integer>ans=calculateFriutPosionToEdge(ff,gg,game);
		if(ans.get(0)!=-1) {
			if(ff.getType()==1) {
				if(ans.get(0)>ans.get(1)) {
					destF=ans.get(0);
					return destF;
				}
				else {
					destF=ans.get(1);
					return destF;
				}
			}
			//it the fruit type is -1
			else {
				if(ans.get(0)>ans.get(1)) {
					destF=ans.get(1);
					return destF;
				}
				else {
					destF=ans.get(0);
					return destF;
				}
			}
		}
		return destF;
	}
	
	/*******************************************************************************************************
	 * this method guts the fruit and return the src of the fruit according to the fruit type
	 *******************************************************************************************************/
	public int fruitSRC(fruit ff,DGraph gg,game_service game) {
		int srcF=-1;
		ArrayList<Integer>ans=calculateFriutPosionToEdge(ff,gg,game);
		if(ans.get(0)!=-1) {
			if(ff.getType()==1) {
				if(ans.get(0)>ans.get(1)) {
					srcF=ans.get(1);
					return srcF;
				}
				else {
					srcF=ans.get(0);
					return srcF;
				}
			}
			//it the fruit type is -1
			else {
				if(ans.get(0)>ans.get(1)) {
					srcF=ans.get(0);
					return srcF;
				}
				else {
					srcF=ans.get(1);
					return srcF;
				}
			}
		}
		return srcF;
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
	 * this method return the shortest path (List of Nodes) from the robot to the fruit
	 * @param srcV
	 * @param destV
	 * @param gg
	 * @return the shortest path
	 *******************************************************************************************************/
	public List<node_data> theBestWayToFruit (int srcV,int destV,DGraph gg) {
		Graph_Algo ggALGO=new Graph_Algo(gg);
		List <node_data> ansPath=ggALGO.shortestPath(srcV, destV);	
		return ansPath;
	}
}// end of the class