package dataStructure;
/**
 * This class implements the interface of edge_data that represents tha all data
 * that have to be in every edge of the graph
 * @author tzion
 *
 */

public class edgeData implements edge_data {

	@Override
	public int getSrc() {
		return this.src;
	}

	@Override
	public int getDest() {
		return this.dest;
	}

	@Override
	public double getWeight() {
		return this.weigth;
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
	 * this method checking if two edges are equal
	 */
	@Override
	public boolean equals(Object obj) {
		boolean flag=true;
		if(obj!=null&&(obj instanceof edge_data)){
			edgeData e=(edgeData) obj;
			if(this.src!=e.getSrc()||this.dest!=e.dest||
					this.info!=e.getInfo()||this.tag!=e.getTag()
					||this.weigth!=e.getWeight())
			{
				flag=false;
				return flag;
			}
		}
		else
			return false;//if it's null or not instance of edge_data the false
		return true;//if it passed all the chacking and it's good so return true
	}//end equal

	//****************** Private Methods and Data *****************

	private int src;
	private int dest;
	private double weigth;
	private String info;
	private int tag;
	//****************** Constructors *****************

	public edgeData(int src, int dest,double weigth) {
		this.src=src;//the start of edge
		this.dest=dest;//the end of the edge
		this.weigth=weigth;//the weight
		this.info=null;//for meta data in the algorithms
		this.tag=0;//to make a sign in the algorithms 

	}

}
