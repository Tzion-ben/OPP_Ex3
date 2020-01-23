package JUnitTesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.node_data;
import utils.Point3D;

class DGraphTest_v2 {

	static String str;
	static DGraph gg=new DGraph();
	static game_service gameTest=Game_Server.getServer(1);

	@BeforeAll
	public static void beforeall() {
		str=gameTest.getGraph();
		gg.init(str);
	}

	@Test
	void testInit() {
		DGraph ggCOpy= new DGraph();
		node_data Node0=new NodeData(0);
		node_data Node1=new NodeData(1);
		node_data Node2=new NodeData(2);
		node_data Node3=new NodeData(3);
		node_data Node4=new NodeData(4);
		node_data Node5=new NodeData(5);
		node_data Node6=new NodeData(6);
		node_data Node7=new NodeData(7);
		node_data Node8=new NodeData(8);
		node_data Node9=new NodeData(9);
		node_data Node10=new NodeData(10);

		String p0STRING="35.18753053591606,32.10378225882353,0.0";
		Point3D p0=new Point3D(p0STRING);
		Node0.setLocation(p0);

		String p1STRING="35.18958953510896,32.10785303529412,0.0";
		Point3D p1=new Point3D(p1STRING);
		Node1.setLocation(p1);

		String p2STRING="35.19341035835351,32.10610841680672,0.0";
		Point3D p2=new Point3D(p2STRING);
		Node2.setLocation(p2);

		String p3STRING="35.197528356739305,32.1053088,0.0";
		Point3D p3=new Point3D(p3STRING);
		Node3.setLocation(p3);

		String p4STRING="35.2016888087167,32.10601755126051,0.0";
		Point3D p4=new Point3D(p4STRING);
		Node4.setLocation(p4);

		String p5STRING="35.20582803389831,32.10625380168067,0.0";
		Point3D p5=new Point3D(p5STRING);
		Node5.setLocation(p5);

		String p6STRING="35.20792948668281,32.10470908739496,0.0";
		Point3D p6=new Point3D(p6STRING);
		Node6.setLocation(p6);

		String p7STRING="35.20746249717514,32.10254648739496,0.0";
		Point3D p7=new Point3D(p7STRING);
		Node7.setLocation(p7);

		String p8STRING="35.20319591121872,32.1031462,0.0";
		Point3D p8=new Point3D(p8STRING);
		Node8.setLocation(p8);

		String p9STRING="35.19597880064568,32.10154696638656,0.0";
		Point3D p9=new Point3D(p9STRING);
		Node9.setLocation(p9);

		String p10STRING="35.18910131880549,32.103618700840336,0.0";
		Point3D p10=new Point3D(p10STRING);
		Node10.setLocation(p10);

		ggCOpy.addNode(Node0);
		ggCOpy.addNode(Node1);
		ggCOpy.addNode(Node2);
		ggCOpy.addNode(Node3);
		ggCOpy.addNode(Node4);
		ggCOpy.addNode(Node5);
		ggCOpy.addNode(Node6);
		ggCOpy.addNode(Node7);
		ggCOpy.addNode(Node8);
		ggCOpy.addNode(Node9);
		ggCOpy.addNode(Node10);

		ggCOpy.connect(0, 1, 1.4004465106761335);
		ggCOpy.connect(0, 10, 1.4620268165085584);
		ggCOpy.connect(1, 0, 1.8884659521433524);
		ggCOpy.connect(1, 2, 1.7646903245689283);
		ggCOpy.connect(2, 1, 1.7155926739282625);
		ggCOpy.connect(2, 3, 1.1435447583365383);
		ggCOpy.connect(3, 2, 1.0980094622804095);
		ggCOpy.connect(3, 4, 1.4301580756736283);
		ggCOpy.connect(4, 3, 1.4899867265011255);
		ggCOpy.connect(4, 5, 1.9442789961315767);
		ggCOpy.connect(5, 4, 1.4622464066335845);
		ggCOpy.connect(5, 6, 1.160662656360925);
		ggCOpy.connect(6, 5, 1.6677173820549975);
		ggCOpy.connect(6, 7, 1.3968360163668776);
		ggCOpy.connect(7, 6, 1.0176531013725074);
		ggCOpy.connect(7, 8, 1.354895648936991);
		ggCOpy.connect(8, 7, 1.6449953452844968);
		ggCOpy.connect(8, 9, 1.8526880332753517);
		ggCOpy.connect(9, 8, 1.4575484853801393);
		ggCOpy.connect(9, 10, 1.022651770039933);
		ggCOpy.connect(10, 0, 1.1761238717867548);
		ggCOpy.connect(10, 9, 1.0887225789883779);

		assertEquals(gg, ggCOpy);
	}
}