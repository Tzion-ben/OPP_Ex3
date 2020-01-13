package dataStructure;
/**
 * This class implements the node_data interface on a node that represents
 * vertex
 * @author Tzion
 */

import utils.Point3D;

public class NodeData implements node_data {

	@Override
	public int getKey() {
		return this.id;
	}

	@Override
	public Point3D getLocation() {
		return this.Location;
	}

	@Override
	public void setLocation(Point3D p) {
		this.Location=p;
	}

	@Override
	public double getWeight() {
		return this.weigth;
	}

	@Override
	public void setWeight(double w) {
		this.weigth=w;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info=s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag=t;
	}

	//****************** My methods *****************
	/**
	 * this method checking if two nodes are equal
	 */
	@Override
	public boolean equals(Object obj) {
		boolean flag=true;
		if(obj!=null&&(obj instanceof node_data)){
			NodeData n=(NodeData) obj;
			if(this.Location!=n.getLocation()||this.weigth!=n.weigth||
					this.info!=n.getInfo()||this.tag!=n.getTag()
					||this.id!=n.getKey())
			{
				flag=false;
				return flag;
			}
		}
		else
			return false;//if it's null or not instance of node_data the false
		return true;//if it passed all the chacking and it's good so return true
	}//end equal
	
	
	/**
	 * this method returns a string with the id of the vertex
	 */
	@Override
	public String toString() {
		String ans="";
		ans+=String.valueOf(this.id);
		return ans;
	}

	//****************** Private Methods and Data *****************

	private Point3D Location;
	private int id;
	private double weigth;
	private String info;
	private int tag;
	//****************** Constructors *****************

	public NodeData(int id) {
		this.Location=null;//the coordinate of the vertices with x,y,z 3D
		this.id=id;//the id of vertex ,i put it to -1 because id can be i to infinity 
		this.weigth=0;
		this.info=null;//for meta data in the algorithms
		this.tag=0;//to make a sign in the algorithms 
	}
}
