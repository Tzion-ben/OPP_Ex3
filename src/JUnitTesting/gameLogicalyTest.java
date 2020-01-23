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
import dataStructure.edge_data;
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
		ArrayList<Integer> ans= gameLogicalyToTest.calculateFriutPosionToEdge(gg, gameTest);

		ArrayList<Integer> expected=new ArrayList<Integer>();
		expected.add(8);
		expected.add(9);
		expected.add(9);
		expected.add(8);
		//************the fruit is ont the edge from 8 to 9 and fron 9 to 8
		assertEquals(expected, ans);
	}

	@Test
	void testGetFruitEdgeDest() {
		fail("Not yet implemented");
	}

	@Test
	void testTheBestWayToFruitDist() {
		fail("Not yet implemented");
	}

	@Test
	void testTheBestWayToFruit() {
		fail("Not yet implemented");
	}

}
