package algorithems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.management.RuntimeErrorException;

import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.edgeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.*;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author Tzion
 *
 */
public class Graph_Algo implements graph_algorithms{

	/**
	 * this method Initializing graphAlgo from graph type DGraph 
	 */
	@Override
	public void init(graph g) {
		this.grafAlgo= g;
	}

	/**
	 * this method Initializing graphAlgo from csv file that holding a graph type DGraph
	 */
	@Override
	public void init(String file_name) {
		graph graphHelp=new DGraph(); 
		String line = "";
		String cvsSplitBy = ",";
		try 
		{
			int i=0;
			BufferedReader brGraph = new BufferedReader(new FileReader(file_name));
			while ((line = brGraph.readLine()) != null) 
			{
				if(i!=0) {
					String[] edgesVertesis = line.split(cvsSplitBy);
					int src=Integer.parseInt(edgesVertesis[0]);
					int dest=Integer.parseInt(edgesVertesis[1]);
					int weigth=Integer.parseInt(edgesVertesis[2]);
					double xLocaSrc=Double.parseDouble(edgesVertesis[3]);
					double yLocaSrc=Double.parseDouble(edgesVertesis[4]);
					double xLocaDest=Double.parseDouble(edgesVertesis[5]);
					double yLocaDest=Double.parseDouble(edgesVertesis[6]);
					Point3D pSrc=new Point3D(xLocaSrc, yLocaSrc);
					Point3D pDest=new Point3D(xLocaDest, yLocaDest);

					//read the src to 0 place at the array, and the dest to 1 place 
					//of the array and the weigth at the place 2 of the array
					try {
						node_data addSrc=new NodeData(src);
						addSrc.setLocation(pSrc);
						graphHelp.addNode(addSrc);
						node_data addDest=new NodeData(dest);
						addDest.setLocation(pDest);
						graphHelp.addNode(addDest);					
					}
					catch (Exception e) {

					}
					graphHelp.connect(src, dest, weigth);

				}
				else if(i==0) {
					//don't want to read the first line of the explanation of every thing 
					//at the file
					line = "";
					i++;
				}
			}
			init(graphHelp);
		} 
		catch (IOException e) 
		{
			throw new RuntimeErrorException(null);
			//System.out.println("could not read file");
		}

	}

	/**
	 * this method is saving the graph to csv file
	 */
	@Override
	public void save(String file_name) {
		try 
		{
			PrintWriter printGraph = new PrintWriter(new File(file_name));
			//at the first line will be the name of the colons
			StringBuilder sbGraphFLine = new StringBuilder();
			sbGraphFLine.append("source");
			sbGraphFLine.append(",");
			sbGraphFLine.append("distention");
			sbGraphFLine.append(",");
			sbGraphFLine.append("weigth");
			sbGraphFLine.append(",");
			sbGraphFLine.append("x-src-Location");
			sbGraphFLine.append(",");
			sbGraphFLine.append("y-src-Location");
			sbGraphFLine.append(",");
			sbGraphFLine.append("x-dest-Location");
			sbGraphFLine.append(",");
			sbGraphFLine.append("y-dest-Location");
			sbGraphFLine.append("\n");
			printGraph.write(sbGraphFLine.toString());
			//end first line
			//fill the file with tha edges locations and so on
			Collection<node_data> vertices= this.grafAlgo.getV();
			Iterator<node_data> vert=vertices.iterator();
			while(vert.hasNext()) {
				node_data tempVertex=vert.next();
				StringBuilder sbGraph = new StringBuilder();
				Collection<edge_data> edgesOfNode= this.grafAlgo.getE(tempVertex.getKey());
				Iterator<edge_data> edge=edgesOfNode.iterator();
				while(edge.hasNext()) {
					edge_data tempEdge=edge.next();

					String nSrc=String.valueOf(tempEdge.getSrc());
					String nDest=String.valueOf( tempEdge.getDest());
					String weigthEdge=String.valueOf( tempEdge.getWeight());
					String locationXsrc=String.valueOf(this.grafAlgo.getNode(tempEdge.getSrc()).getLocation().x());
					String locationYsrc=String.valueOf(this.grafAlgo.getNode(tempEdge.getSrc()).getLocation().y());
					String locationXdest=String.valueOf(this.grafAlgo.getNode(tempEdge.getDest()).getLocation().x());
					String locationYdest=String.valueOf(this.grafAlgo.getNode(tempEdge.getDest()).getLocation().y());

					sbGraph.append(nSrc);
					sbGraph.append(",");
					sbGraph.append(nDest);
					sbGraph.append(",");
					sbGraph.append(weigthEdge);
					sbGraph.append(",");
					sbGraph.append(locationXsrc);
					sbGraph.append(",");
					sbGraph.append(locationYsrc);
					sbGraph.append(",");
					sbGraph.append(locationXdest);
					sbGraph.append(",");
					sbGraph.append(locationYdest);
					sbGraph.append("\n");

					printGraph.write(sbGraph.toString());

				} 
			}
			printGraph.close();
		}
		catch (FileNotFoundException e) 
		{
			//e.printStackTrace();
			return;
		}
	}
	/**
	 * this method is checking if the directional graph is strongly connect or not by
	 * the method of DFS traversal on the graph recursively
	 */
	@Override
	public boolean isConnected() {
		zeroAllTags();
		Collection<node_data> vertices= this.grafAlgo.getV();
		Iterator<node_data> vert=vertices.iterator();
		if(vert.hasNext()) {
			node_data tempVertex=new NodeData(vert.next().getKey());			
			DFSRec(tempVertex,this.grafAlgo);	
		}

		Iterator<node_data> vertSec=vertices.iterator();
		while(vertSec.hasNext()) {
			if(vertSec.next().getTag()==0) 
				return false;
		}

		graph transpoGraph=transpozeGraph();
		Collection<node_data> verticesT= transpoGraph.getV();
		Iterator<node_data> vertT=verticesT.iterator();
		if(vertT.hasNext()) {
			node_data tempVertexT=new NodeData(vertT.next().getKey());			
			DFSRec(tempVertexT ,transpoGraph);	
		}
		Iterator<node_data> vertSecT=verticesT.iterator();
		while(vertSecT.hasNext()) {
			if(vertSecT.next().getTag()==0) 
				return false;
		}
		return true;
	}//end of isConnected

	/**
	 * this method return the length of the shortest path between src to dest,
	 * if the path is does not exist it's returns infinity (double)
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		double shWeight=Integer.MAX_VALUE;
		//if the path is not exisest return infinity
		List<node_data> sgPath =shortestPath(src,dest);
		if(sgPath!=null) 
			shWeight=this.grafAlgo.getNode(dest).getWeight();

		return shWeight;
	}

	/**
	 * this method returns a List of all the nodes (vertices) that include in the shortest 
	 * path from src to dest, this method base on the dijkstra algorithm,
	 * if it not exist it returns null.
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		boolean flag=isConnected();
		if (!flag)
			return null;
		//if the graph is not a strongly connected then we return null
		Collection<node_data> toCheck=this.grafAlgo.getV();
		if(!toCheck.contains(this.grafAlgo.getNode(src))||
				!toCheck.contains(this.grafAlgo.getNode(dest))) 
			return null;
		//if it's invalid src or dest so return null

		zeroAllTags();
		infinityAllWeight();
		setsAllInfo();
		if(this.grafAlgo.getNode(src)!=null) {
			//that if is checking if the hashMap of the vertices is not empty and
			//contains the vertex with the id of src 
			this.grafAlgo.getNode(src).setWeight(0);
			DFSRecForWeight(this.grafAlgo.getNode(src),dest,this.grafAlgo);

			List<node_data> shPathVerts=new ArrayList<node_data>();
			String thePath=this.grafAlgo.getNode(dest).getInfo();
			String [] vertesisToPath=thePath.split(",");
			for(int i=0;i<vertesisToPath.length;i++) {
				shPathVerts.add(this.grafAlgo.getNode(Integer.parseInt(vertesisToPath[i])));
			}
			//at the last add the dest vertex
			shPathVerts.add(this.grafAlgo.getNode(dest));
			return shPathVerts;
		}
		//if the pay is not exisest so returns null
		return null;
	}
	/**
	 * this method computes a relatively short path which visit each node in the targets List,
	 * not exist it returns null
	 */
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		boolean flag=isConnected();
		if (!flag)
			return null;	
		//if the graph is not a strongly connected then we return null

		Collection<node_data> toCheck=this.grafAlgo.getV();
		for(int i=0;i<targets.size();i++){
			if(!toCheck.contains(this.grafAlgo.getNode(targets.get(i))))
				return null;
		}
		//if it's invalid targets so return null

		List<node_data> pathToReturn=new ArrayList<node_data>();
		int countNullAction=0;

		for(int i=0;i<targets.size()-1;i++) {
			int notFirst=0;
			List<node_data> tempPath=new ArrayList<node_data>();
			tempPath=shortestPath(targets.get(i), targets.get(i+1));
			if(tempPath.isEmpty()) 
				countNullAction++;
			Iterator<node_data> addToReturnPath=tempPath.iterator();
			while(addToReturnPath.hasNext()) {
				node_data tempAdd=addToReturnPath.next();
				if(notFirst>=1)//to not be a duplicate at the return list
					pathToReturn.add(tempAdd);
				else {
					if(pathToReturn.isEmpty())
						pathToReturn.add(tempAdd);
				}
				notFirst++;
			}
		}
		if(countNullAction==0) 
			return pathToReturn;
		//so if wasn't null at the way we can return this path , else, we move on in reverse order
		else {
			pathToReturn.clear();
			Collections.reverse(targets);
			//checking the opposite direction
			for(int i=0;i<targets.size()-1;i++) {
				int notFirst=0;
				List<node_data> tempPath=new ArrayList<node_data>();
				tempPath=shortestPath(targets.get(i), targets.get(i+1));
				if(tempPath==null) 
					return null;
				Iterator<node_data> addToReturnPath=tempPath.iterator();
				while(addToReturnPath.hasNext()) {
					notFirst++;
					node_data tempAdd=addToReturnPath.next();
					if(notFirst>=1)//to not be a duplicate at the return list
						pathToReturn.add(tempAdd);
				}
			}
		}
		return pathToReturn;
	}

	/**
	 * this method create a deep copy of the graph
	 */
	@Override
	public graph copy() {
		DGraph graphToCopy=new DGraph(); 
		zeroAllTags();
		Collection<node_data> vertices= this.grafAlgo.getV();

		Iterator<node_data> vertLocations=vertices.iterator();
		ArrayList<Point3D> allPoint=new ArrayList<Point3D>();
		while(vertLocations.hasNext()) 
			allPoint.add(vertLocations.next().getLocation());

		Iterator<node_data> vertInfo=vertices.iterator();
		ArrayList<String> allInfo=new ArrayList<String>();
		while(vertInfo.hasNext()) 
			allInfo.add(vertInfo.next().getInfo());

		Iterator<node_data> vertW=vertices.iterator();
		ArrayList<Double> allW=new ArrayList<Double>();
		while(vertW.hasNext()) 
			allW.add(vertW.next().getWeight());

		Iterator<node_data> vert=vertices.iterator();
		int runLocations=0;
		int runLInfo=0;
		int runW=0;

		while(vert.hasNext()) {
			//1. create a vertex 
			node_data tempVertex=new NodeData(vert.next().getKey());
			if(allPoint.get(runLocations)!=null) {
				Point3D p=new Point3D(allPoint.get(runLocations));
				runLocations++;
				tempVertex.setLocation(p);
			}
			if(allInfo.get(runLInfo)!=null) {
				String s=allInfo.get(runLInfo);
				runLInfo++;
				tempVertex.setInfo(s);
			}
			if(allW.get(runW)!=null) {
				double wTemp=allW.get(runW);
				runW++;
				tempVertex.setWeight(wTemp);
			}
			graphToCopy.addNode(tempVertex);
			//2. create the all edgaes for that specific vertex
			Collection<edge_data> edgesOfNode= this.grafAlgo.getE(tempVertex.getKey());
			Iterator<edge_data> edge=edgesOfNode.iterator();
			while(edge.hasNext()) {
				edge_data tempEdge=edge.next();
				graphToCopy.connect(tempEdge.getSrc(), tempEdge.getDest()
						, tempEdge.getWeight());
			}//end creating the edges
		}
		return graphToCopy;
	}
	//****************** My methods *****************

	/**
	 * this private methos is run on every neighbors of the node by the dest id of all the edges of the 
	 * specific node, and tag all the neighbors with '1' that mean this node was visited
	 * so at the end if all the nodes tags will be 1 the graph is strongly connected  
	 * tag==0 :NOT VISETED ,tag==1 :VISETED
	 * @param ver
	 */
	private void DFSRec(node_data vert , graph g) {
		vert.setTag(1);
		Collection<edge_data> edgesOfVert= g.getE(vert.getKey());
		Iterator<edge_data> edge=edgesOfVert.iterator();
		while(edge.hasNext()) {
			node_data tempVert=g.getNode(edge.next().getDest());
			//the next node to check is the vertex that is near we know it by the DEST
			if(tempVert.getTag()==0)
				DFSRec(tempVert,g);
		}
	}//end DFSRec

	/**
	 * this private method return the transpose graph the this graph
	 * @return
	 */
	private graph transpozeGraph () {
		graph grafAlgoT=new DGraph();
		Collection<node_data> vertices= this.grafAlgo.getV();
		Iterator<node_data> vert=vertices.iterator();
		while(vert.hasNext()) {
			node_data tempVertex=new NodeData(vert.next().getKey());
			tempVertex.setTag(0);//sets all the tags to 0 so can run again on the transpose graph
			grafAlgoT.addNode(tempVertex);	
		}	//create the Vertex, for dint stop at NULLPOINTEREXPTION with the oppisid srd and dest

		Iterator<node_data> vert1=vertices.iterator();
		while(vert1.hasNext()) {
			node_data tempVertex=new NodeData(vert1.next().getKey());
			//create the edges of the every vertex
			Collection<edge_data> edgesOfVert= this.grafAlgo.getE(tempVertex.getKey());
			Iterator<edge_data> edge=edgesOfVert.iterator();
			while(edge.hasNext()) {
				edge_data tempEdge=edge.next();
				grafAlgoT.connect(tempEdge.getDest(),tempEdge.getSrc(), tempEdge.getWeight());			
			}		
		}//create all the transpose graph
		return grafAlgoT;
	} 

	/**
	 * this private method sets all the w at the graph to zero 
	 * before started to check at the method isConnectd
	 */
	private void zeroAllTags() {
		Collection<node_data> vertices= this.grafAlgo.getV();
		Iterator<node_data> vert=vertices.iterator();
		while(vert.hasNext()) 
			vert.next().setTag(0);
	}

	/**
	 * this private method sets all the Weight at the graph to infinity 
	 * before started to check at the method the shortest path
	 */
	private void infinityAllWeight() {
		Collection<node_data> vertices= this.grafAlgo.getV();
		Iterator<node_data> vert=vertices.iterator();
		while(vert.hasNext()) 
			vert.next().setWeight(Double.MAX_VALUE);;
	}

	/**
	 * this private method sets all the info at the graph vertesis to the strinng
	 * with the number of the node id for help find the path at the method shortest

	 */
	private void setsAllInfo() {
		String s="";
		Collection<node_data> vertices= this.grafAlgo.getV();
		Iterator<node_data> vert=vertices.iterator();
		while(vert.hasNext()) {
			node_data tempVert=vert.next();
			tempVert.setInfo(String.valueOf(tempVert.getKey()));
		}
	}

	/**
	 * this private method find the vertex with the minimum Weight at all the graph
	 */
	private int findMinWeight() {
		int minNodeId=Integer.MAX_VALUE;
		double minNodeWeight=Double.MAX_VALUE;
		Collection<node_data> vertices= this.grafAlgo.getV();
		Iterator<node_data> vert=vertices.iterator();
		while(vert.hasNext()) {
			node_data tempVert=vert.next();
			if(tempVert.getWeight()<minNodeWeight&&tempVert.getTag()!=1) {
				minNodeWeight=tempVert.getWeight();
				minNodeId=tempVert.getKey();
			}
		}
		return minNodeId;
	}

	/**
	 * this private method is run on every neighbors of the node and if the wight of the neighbors nodes of
	 * this node is bigger then the wight of the edge to this node and the wight of the src node so it changes
	 * it to the wight of the edge and the src node (based on the Dijkstra Algorithm).
	 * In every iteration the  method calculates the smallest vertex and move from that vertex so on and recursvly
	 * calculates all weight of his neighbors and then take the smallest vertex again and so on
	 * Info of the dest vertex is full with the shortest path, if found more sorter then reset the info
	 * tag==0 :NOT VISETED ,tag==1 :VISETED
	 * @param ver
	 */
	private void DFSRecForWeight(node_data vert ,int dest, graph g) {
		vert.setTag(1);
		Collection<edge_data> edgesOfVert= g.getE(vert.getKey());
		Iterator<edge_data> edge=edgesOfVert.iterator();
		while(edge.hasNext()) {
			edge_data tempEdge=edge.next();
			node_data neighborVert=g.getNode(tempEdge.getDest());
			if((neighborVert.getTag()!=1)&&(vert.getWeight()+tempEdge.getWeight()<neighborVert.getWeight())) {
				neighborVert.setWeight(vert.getWeight()+tempEdge.getWeight());
				neighborVert.setInfo(vert.getInfo()+","+neighborVert.getInfo());
			}
		}
		int minNodeIdW= findMinWeight();
		if(minNodeIdW!=dest) {
			this.grafAlgo.getNode(dest).setInfo("");
			DFSRecForWeight(this.grafAlgo.getNode(minNodeIdW),dest,this.grafAlgo);
		}
		else {
			Collection<edge_data> edgesDest= g.getE(dest);
			Iterator<edge_data> edgeD=edgesDest.iterator();
			double minW=Double.MAX_VALUE;
			int minWId=Integer.MAX_VALUE;
			while(edgeD.hasNext()) {
				edge_data tempE=edgeD.next();
				if(this.grafAlgo.getNode(tempE.getDest()).getWeight()<minW) {
					minW=this.grafAlgo.getNode(tempE.getDest()).getWeight();
					minWId=this.grafAlgo.getNode(tempE.getDest()).getKey();
				}


			}
			this.grafAlgo.getNode(dest).setInfo("");
			this.grafAlgo.getNode(dest).setInfo(this.grafAlgo.getNode(minWId).getInfo());
		}


		return;

	}//end DFSRec

	//****************** Private Methods and Data *****************
	private graph grafAlgo; 
	//****************** Contractor *****************
	public Graph_Algo(graph g) {
		init(g);
	}
}