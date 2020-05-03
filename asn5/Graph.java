import java.util.ArrayList;
import java.util.Iterator;

public class Graph implements GraphADT {
	private int n;
	private GraphEdge[][] matrix;
	private GraphNode[] nodes;
	
	public Graph(int n) {

		this.n = n;
		matrix = new GraphEdge[n][n];
		nodes = new GraphNode[n];
		for(int i = 0;i<n;i++) {
			nodes[i] = new GraphNode(i);
		}

	}
	
	

	public void insertEdge(GraphNode nodeu, GraphNode nodev, char busLine) throws GraphException {
		if(!validate(nodeu.getName())||!validate(nodev.getName())) {
			throw new GraphException("Non-existent Node!");
		}
		
		if(matrix[nodeu.getName()][nodev.getName()] != null) {
			
			throw new GraphException("Edge between nodes "
								+nodeu.getName()
								+" and "+nodev.getName()
								+" Already Exists!");
		}
		
		//Testing
//		System.out.println("Insert "+busLine+" between "
//							+ nodeu.getName()
//							+" and "+nodev.getName());
							
		
		
		GraphEdge edge = new GraphEdge(nodeu, nodev, busLine);
		matrix[nodeu.getName()][nodev.getName()] = edge;
		edge = new GraphEdge(nodev, nodeu, busLine);
		matrix[nodev.getName()][nodeu.getName()] = edge;
	}


	public GraphNode getNode(int name) throws GraphException {
		if(!validate(name)) {
			throw new GraphException("Node does not exist!");
		}
		
		return nodes[name];
	}


	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		if(!validate(u.getName())) {
			throw new GraphException("Node does not exist!");
		}
		
		ArrayList<GraphEdge> edges = new ArrayList<GraphEdge>();
		for(GraphEdge edge: matrix[u.getName()]) {
			if(edge!=null) {
				edges.add(edge);
			}
		}
		
		return edges.iterator();
	}

	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		if(u.getName()>=n || v.getName()>=n) {
			throw new GraphException("Non-existent Node!");
		}
		
		if(matrix[u.getName()][v.getName()] == null) {
			throw new GraphException("Edge Already Exists!");
		}
		
		return matrix[u.getName()][v.getName()];
	}


	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		if(!validate(u.getName())||!validate(v.getName())) {
			throw new GraphException("Non-existent Node!");
		}
		
		return (matrix[u.getName()][v.getName()]!=null);
	}
	

	private boolean validate(int name) {
		return (name>=0 && name<n);
	}
	
	public void display() {
		System.out.println("------------------------------------");
		System.out.println("Nodes:");
		System.out.println(nodes.length);
		for(GraphNode node:nodes) {
			System.out.println("Node - "+node.getName()+"  ("+node.getMark()+")");
		}
		System.out.println("------------------------------------");
		
		System.out.println("Edges:");
		for(int row = 0;row<n;row++) {
			for(int col = 0;col<n;col++) {
				GraphEdge edge = matrix[row][col];
				if(edge!=null) {
					System.out.println("Edge - "+edge.getBusLine()
					+"("+  row+","+col   +")");
				}
			}
		}
	}
	
}
