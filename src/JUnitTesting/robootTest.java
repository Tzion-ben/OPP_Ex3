package JUnitTesting;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Server.Game_Server;
import Server.game_service;
import gameClient.robbot;
import utils.Point3D;

class robootTest {


	static String str;
	static robbot rr;
	static game_service gameTest=Game_Server.getServer(1);

	@BeforeAll
	public static void beforeall() {
		gameTest.addRobot(2);
		List<String> robots=gameTest.getRobots();	
		rr=new robbot(robots.get(0));
	}

	@Test
	void testGetId() {
		int expected=0;
		assertEquals(expected, rr.getId());
	}

	@Test
	void testGetValue() {
		double expected=0.0;
		assertEquals(expected, rr.getValue());
		}

	@Test
	void testGetSrc() {
		int expected=2;
		assertEquals(expected, rr.getSrc());
	}

	@Test
	void testGetDest() {
		int expected=-1;
		assertEquals(expected, rr.getDest());
	}

	@Test
	void testGetSpeed() {
		double expected=1.0;
		assertEquals(expected, rr.getSpeed());
	}

	@Test
	void testGetlocation() {
		String thePoint="35.19341035835351,32.10610841680672,0.0";
		Point3D expected=new Point3D(thePoint);
		assertEquals(expected, rr.getlocation());
	}

}
