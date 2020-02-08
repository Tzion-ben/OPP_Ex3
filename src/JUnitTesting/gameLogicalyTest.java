package JUnitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.node_data;
import gameClient.fruit;
import gameClient.gameLogicaly;
import utils.Point3D;

class gameLogicalyTest {

	static String str;
	static fruit ff;
	static DGraph gg=new DGraph();
	static gameLogicaly gameLogicalyToTest=new gameLogicaly();
	static game_service gameTest=Game_Server.getServer(1);

	@BeforeAll
	public static void beforeall() {
		List<String> fruits=gameTest.getFruits();	
		ff=new fruit(fruits.get(0));
		String graph=gameTest.getGraph();
		gg.init(graph);
	}


	@Test
	void testCalculateFriutPosionToEdge() {
		Point3D ffP=ff.getlocation();
		ArrayList<Integer> ans= gameLogicalyToTest.calculateFriutPosionToEdge(ff,gg, gameTest);

		ArrayList<Integer> expected=new ArrayList<Integer>();
		expected.add(8);
		expected.add(9);
		//************the fruit is on the edge from 8 to 9 or 9 to 8 it's the same
		assertEquals(expected, ans);
	}

	@Test
	void testGetFruitEdgeDest() {
		int actual=gameLogicalyToTest.fruitDEST(ff, gg, gameTest);
		int expected =8;
		//*********is contain a edge 8 to 9 and 9 to 8 so at the end it return the at the sect actually the src 
		//*********node id 
		assertEquals(expected, actual);
	}

	@Test
	void testGetFruitEdgeSrc() {
		int actual=gameLogicalyToTest.fruitSRC(ff, gg, gameTest);
		int expected =9;
		//*********is contain a edge 8 to 9 and 9 to 8 so at the end it return the at the sect actually the src 
		//*********node id 
		assertEquals(expected, actual);
	}


	@Test
	void testTheBestWayToFruitDist() {
		int srcV=9;
		int destV=8;
		double TheBestWayToFruitDist=gameLogicalyToTest.theBestWayToFruitDist(srcV, destV, gg);
		double expected=1.4575484853801393;

		assertEquals(expected, TheBestWayToFruitDist);
	}

	@Test
	void testTheBestWayToFruit() {
		int srcV=9;
		int destV=8;
		List<node_data> ans=gameLogicalyToTest.theBestWayToFruit(srcV, destV, gg);

		int expectedSrc=9;
		int expectedDest=8;

		if(ans.get(0).getKey()!=expectedSrc)
			fail();
		if(ans.get(1).getKey()!=expectedDest)
			fail();
	}
}