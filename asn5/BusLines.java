import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

public class BusLines {
	private Graph graph;
	private int scale, width, length, changes,start,destination;
	public BusLines(String inputFile) 
			throws MapException, IOException, GraphException{
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			int counter = 0;
			String line = in.readLine();
			
			String[] factors = line.split(" ");
			scale = Integer.parseInt(factors[0]);
			width = Integer.parseInt(factors[1]);
			length = Integer.parseInt(factors[2]);
			changes = Integer.parseInt(factors[3]);
			graph = new Graph(width*length);
			char bus;
			String line1, line2;
			GraphNode u,v;
			int c = 0;
			
			for(int row = 0;row<length;row++) {
				line1 = in.readLine();
				line2 = in.readLine();
				
				if(line1.contains("S")) {
					start = row*width +line1.indexOf('S')/2;
				}
				if(line1.contains("D")) {
					destination = row*width + line1.indexOf('D')/2;
				}
				
				for (int i = 0; i <line1.length(); i++) {
					if(i%2==0) {
						if(line2!=null) {
							bus = line2.charAt(i);
							
							if(isBus(bus)) {
								u = graph.getNode(c);
								v = graph.getNode(c+width);
								graph.insertEdge(u, v, bus);
							}
						}
					}else {
						bus = line1.charAt(i);
						
						if(isBus(bus)) {
							u = graph.getNode(c);
							v = graph.getNode(c+1);
							graph.insertEdge(u, v, bus);
						}
						c++;
					}
				}
				c++;
				
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			throw new MapException("File Not Found!");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MapException("Wrong Input Format");
		}
		
		
		//testing
//		graph.display();
		System.out.println("Start is "+start+", and destination is "+destination);
		
	}
	
	private boolean isBus(char ch) {
		return(ch!='S'&&ch!='D'&&ch!='.'&&ch!=' ');
	}
	
	
	
	public Graph getGraph() throws MapException {
		return graph;
	}
	
	public Iterator<GraphNode> trip() throws GraphException{
		Stack<GraphNode> stack = new Stack<GraphNode>();
		GraphNode current = graph.getNode(start);
		
		current.setMark(true);
		stack.add(current);
		
		if(findPath(stack,0,'\0')) {
			return stack.iterator();
		}else {
			return null;
		}
	}
	
	private boolean findPath(Stack<GraphNode> stack, int currentChanges, char currentBusLine) throws GraphException {
		/*  TESTING  */
		System.out.println("# of Stack Elements = "+stack.size()+""
				+ ", currentChanges = "+currentChanges
				+ ", currentBusLine = "+currentBusLine);
		/* TESTING */
		
		
		GraphNode current = stack.peek(), nextNode;
		GraphEdge nextEdge;

		if(current.getName() == destination) {
			return true;
		}
		
		if(currentChanges<=changes+1) {
			Iterator<GraphEdge> incidentEdges = graph.incidentEdges(current);
			while(incidentEdges.hasNext()) {
				nextEdge = incidentEdges.next();
				nextNode = nextEdge.secondEndpoint();
				if(nextNode.getMark()==true) {
					continue;
				}
				if(nextEdge.getBusLine() != currentBusLine) {
					currentChanges++;
				}
				stack.push(nextNode);
				nextNode.setMark(true);
				
				if(findPath(stack,currentChanges,nextEdge.getBusLine())) {
					return true;
				}else {
					if(nextEdge.getBusLine() != currentBusLine) {
						currentChanges--;
					}
				}
			}
			
		}
		
		current.setMark(false);
		stack.pop();
		return false;
	}
	

	public static void main(String[] args) throws MapException, IOException, GraphException {
		BusLines buslines = new BusLines("sample.txt");
				
		Iterator<GraphNode> path = buslines.trip();
		while(path.hasNext()) {
			GraphNode next = path.next();
			System.out.println(next.getName());
		}
	}
}
