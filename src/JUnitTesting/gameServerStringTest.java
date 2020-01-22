package JUnitTesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Server.Game_Server;
import Server.game_service;
import gameClient.gameServerString;

class gameServerStringTest {


	static String str;
	static gameServerString gg;
	static game_service gameTest=Game_Server.getServer(1);

	@BeforeAll
	public static void beforeall() {
		str=gameTest.toString();
		gg=new gameServerString(str);
	}

	@Test
	void testGetNumFruits() {
		int numFruitss=2;
		assertEquals(numFruitss, gg.getNumFruits());
	}

	@Test
	void testGetNumMoves() {
		int numMoves=0;
		assertEquals(numMoves, gg.getNumMoves());
	}

	@Test
	void testGetNumGrade() {
		int grade=0;
		assertEquals(grade, gg.getNumGrade());
	}

	@Test
	void testGetNumRobbots() {
		int numRobots=1;
		assertEquals(numRobots, gg.getNumRobbots());
	}

	@Test
	void testGetgraphId() {
		String graphId="data/A0";
		assertEquals(graphId, gg.getgraphId());
	}
}
