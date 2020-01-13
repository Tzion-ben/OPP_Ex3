package dataStructure;
import java.util.ArrayList;
/**
 * 
 * @author Tzion
 */
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

public class DGraph implements graph{

	/**
	 * this method returns the specific vertex by his id in O(1)
	 */
	@Override
	public node_data getNode(int key) {
		if(!this.vertesis.isEmpty()&&this.vertesis.containsKey(key))
			return this.vertesis.get(key);
		return null;
	}

	/**
	 * this method returns a specific edge with unique source and destination in O(1)
	 */
	@Override
	public edge_data getEdge(int src, int dest) {
		if(!this.edges.isEmpty()&&this.edges.containsKey(src)&&this.edges.get(src)!=null) 
			//checking if the collection of the edges is not empty,
			//if it contains  source(src)and if for that source have a destination
			//and it is not a independent vertex without edges
			if(this.edges.get(src).containsKey(dest)) 
				return this.edges.get(src).get(dest);

		return null;//if the collection is Empty or the edge is not exist
	}

	@Override
	public void addNode(node_data n) {
		if(!this.vertesis.containsKey(n.getKey())) {
			modeCount++;
			this.vertesis.put(n.getKey(), n);
			HashMap<Integer, edge_data> helpEdges=new HashMap<>();
			this.edges.put(n.getKey(),helpEdges);
			//when i create a vertex i create to him a specific hashMap 
			//that will hold his edges in the future
		}
		else 
			throw new RuntimeException();
	}

	/**
	 * this method is create a new edge between src to dest
	 */
	@Override
	public void connect(int src, int dest, double w) {
		if(this.vertesis.containsKey(src)) {
			modeCount++;
			edgeData newEdge=new edgeData(src, dest, w);
			if(!edges.get(src).containsKey(dest))
				edges.get(src).put(dest, newEdge);

		}
		//		else 
		//			throw new RuntimeException();
		//			System.out.println("Can't add the edge, it alredy contains\n"
		//					+ "such edge with that src and that dest\n");
	}

	/**
	 * This method return a pointer (shallow copy) for the
	 * collection representing all the nodes in the graph. 
	 */
	@Override
	public Collection<node_data> getV() {
		return vertesis.values();
	}

	/**
	 * This method return a pointer (shallow copy) for the
	 * collection representing all the edges getting out of 
	 * the given node (all the edges starting (source) at the given node). 
	 */
	@Override
	public Collection<edge_data> getE(int node_id) {
		return edges.get(node_id).values();
	}

	/**
	 * this method removes all the edges the goes from the vertex in O(1)
	 * and then runs in O(n) on the all other vertices and remove all the 
	 * edges from all the vertices that there destination is the vertex that
	 * the method will remove
	 * @return the data of the removed node (null if none). 
	 * @param key 
	 */
	@Override
	public node_data removeNode(int key) {
		if(this.vertesis.containsKey(key)) {//iff a such vertex is exists remove it
			modeCount++;			
			int edgesSize=edges.size();
			for(int i=0;i<edgesSize;i++) {//O(n), |v|=n
				node_data temp=this.vertesis.get(i);
				if(temp!=null) {
					if(key!=temp.getKey()) {
						if(edges.get(i).containsKey(key)) 
							edges.get(i).remove(key);
					}
				}
			}
			this.edges.remove(key);//removing all the edges that goes from the
			//specific vertex
			node_data removeNode=new NodeData(key);
			//save all the data of the removed vertex in new vertex to return
			removeNode.setLocation(this.vertesis.get(key).getLocation());
			removeNode.setWeight(this.vertesis.get(key).getWeight());
			removeNode.setInfo(this.vertesis.get(key).getInfo());
			removeNode.setTag(this.vertesis.get(key).getTag());
			this.vertesis.remove(key);//remove the vertex
			return removeNode;
		}
		//else: iff such vertex is NOT exists, return null
		return null;
	}

	/**
	 * this method will remove the edge iff it exists and return it's data,
	 * if not, it will return null 
	 */
	@Override
	public edge_data removeEdge(int src, int dest) {
		if(edges.get(src).containsKey(dest)) {//iff a such edge is exists remove it
			modeCount++;
			edge_data removeEdge=new edgeData(src, dest, edges.get(src).get(dest).getWeight());
			//save all the data of the removed edge in new edge to return
			removeEdge.setInfo(edges.get(src).get(dest).getInfo());
			removeEdge.setTag(edges.get(src).get(dest).getTag());
			edges.get(src).remove(dest);
			return removeEdge;
		}
		//else: iff such edge is NOT exists, return null
		return null;
	}

	/**
	 * this method returns the number of the vertices in the graph
	 */
	@Override
	public int nodeSize() {
		return vertesis.size();
	}

	/**
	 * this method returns the number of the edges in the directional graph
	 */
	@Override
	public int edgeSize() {
		return edges.size();
	}

	/**
	 * this method return the number of changes at the graph
	 */
	@Override
	public int getMC() {
		return this.modeCount;
	}


	//****************** My methods *****************
	/**
	 * this method checking if two graps are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj!=null&&(obj instanceof graph)){
			graph n=(DGraph) obj;
			Collection<node_data> verticesN= n.getV();
			Iterator<node_data>  vertN=verticesN.iterator();
			Collection<node_data> verticesThis= this.getV();
			Iterator<node_data>  vertT=verticesThis.iterator();

			while(vertN.hasNext()||vertT.hasNext()) {
				if(vertN.hasNext()&&vertT.hasNext()) {
					NodeData tempN=(NodeData) vertN.next();
					NodeData tempT=(NodeData) vertT.next();
					if(!tempN.equals(tempT))
						return false;

					Collection<edge_data>edge= n.getE(tempN.getKey());
					Iterator<edge_data>edgeN=edge.iterator();
					Collection<edge_data>edgeThis= this.getE(tempN.getKey());
					Iterator<edge_data>edgeT=edgeThis.iterator();

					while(vertN.hasNext()&&edgeT.hasNext()) {
						edgeData tempEdge=(edgeData) edgeN.next();
						if(!tempEdge.equals(edgeT.next())) 
							return false;
					}
				}
				else
					//if the graps are not the same then it returns false
					return false;
			}
		}
		else
			return false;//if it's null or not instance of graph the false
		return true;//if it passed all the chacking and it's good so return true
	}//end equal

	/**
	 * this method initialize DGraph from JsonString
	 */
	public void init(String str) {
		JSONObject line;
		try {
			line = new JSONObject(str);
			//*********add the nodes
			JSONArray temparrVert= line.getJSONArray("Nodes");
			JSONObject y;
			for(int i=0;i<temparrVert.length();i++) {
				y=temparrVert.getJSONObject(i);
				Point3D tempP=new Point3D(y.getString("pos"));
				node_data tempVert=new NodeData(y.getInt("id"));
				tempVert.setLocation(tempP);
				addNode(tempVert);	
			}//end add node
			//*********add the edges
			JSONArray temparrEdge= line.getJSONArray("Edges");
			JSONObject x;
			for(int i=0;i<temparrEdge.length();i++) {
				x=temparrEdge.getJSONObject(i);
				this.connect(x.getInt("src"), x.getInt("dest"), x.getDouble("w"));
			}
		}//end add edges
		catch (JSONException e) {e.printStackTrace();}
	}

	//****************** Private Methods and Data *****************

	static int modeCount=0;//static value to count hoe many times the graph was changed
	private HashMap<Integer, node_data> vertesis;
	//the keys is the id of the vertex and the value is a specific vertex 

	private HashMap<Integer,HashMap<Integer, edge_data>> edges ;
	//the keys is the source of the edge and the key of the HashMap the represents the values is the 
	//destination of the edge

	//****************** Contractors and setters *****************

	public void setVertesis() {
		this.vertesis=new HashMap<Integer,node_data>();
	}
	public void setEdges() {
		this.edges=new HashMap <Integer,HashMap<Integer, edge_data>>();
	}

	public DGraph() {
		setVertesis();
		setEdges();
	}
}