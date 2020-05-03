
public class GraphNode {
	private int name;
	private boolean mark;
	
	public GraphNode(int name) {
		this.name = name;
		this.mark = false;
	}
	
	/**
	 * @param mark the mark to set
	 */
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	
	public boolean getMark() {
		return mark;
	}

	/**
	 * @return the name
	 */
	public int getName() {
		return name;
	}
	
//	public String toString() {
//		return "Node:"+Integer.toString(name)+"|"+Boolean.toString(mark);
//	}
}
