
public class GraphEdge {
	private GraphNode first, second;
	private char busLine;
	
	public GraphEdge(GraphNode u, GraphNode v, char busLine) {
		this.first = u;
		this.second = v;
		this.busLine = busLine;
	}
	
	public GraphNode firstEndpoint() {
		return first;
	}
	
	public GraphNode secondEndpoint() {
		return second;
	}
	
	public char getBusLine() {
		return busLine;
	}
	
	
	
}
