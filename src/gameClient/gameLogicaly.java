package gameClient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;

//*******************************Constractor *******************************
public class gameLogicaly {
	public gameLogicaly() {;}
//**************************************************************************
	
	/**
	 * this method guts the graph and the game at the specific level and calculate the place of the 
	 * fruit on it for the automatically game
	 * @param gg
	 * @param game
	 * @return
	 */
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
}
