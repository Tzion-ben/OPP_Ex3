package JUnitTesting;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Server.Game_Server;
import Server.game_service;
import gameClient.*;
import utils.Point3D;

class fruitTest {

	static String str;
	static fruit ff;
	static game_service gameTest=Game_Server.getServer(1);

	@BeforeAll
	public static void beforeall() {
		List<String> fruits=gameTest.getFruits();	
		ff=new fruit(fruits.get(0));
	}

	@Test
	void testGetValue() {
		double expected=5.0;
		assertEquals(expected, ff.getValue());
	}

	@Test
	void testGetType() {
		int expected=-1;
		assertEquals(expected, ff.getType());
	}

	@Test
	void testGetlocation() {
		String thePoint="35.197656770719604,32.10191878639921,0.0";
		Point3D expected=new Point3D(thePoint);
		assertEquals(expected, ff.getlocation());
	}
}
