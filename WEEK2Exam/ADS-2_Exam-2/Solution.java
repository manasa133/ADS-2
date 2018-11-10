import  java.util.*;
public class Solution {

	public static void main(String[] args) {
		// Self loops are not allowed...
		// Parallel Edges are allowed...
		// Take the Graph input here...
		Scanner sc = new Scanner(System.in);
		int Vertices = Integer.parseInt(sc.nextLine());
		int Edges = Integer.parseInt(sc.nextLine());
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(Vertices);
		for(int i =0 ;i < Edges;i++){
			String[] vw= sc.nextLine().split(" ");
			DirectedEdge e = new DirectedEdge(Integer.parseInt(vw[0]),Integer.parseInt(vw[1]),
				Double.parseDouble(vw[2]));
			G.addEdge(e);
		}


		String caseToGo = sc.nextLine();
		switch (caseToGo) {
		case "Graph":
			//Print the Graph Object.
		System.out.println(G);
			break;

		case "DirectedPaths":
			// Handle the case of DirectedPaths, where two integers are given.
			// First is the source and second is the destination.
			// If the path exists print the distance between them.
			// Other wise print "No Path Found."
		String[] sd = sc.nextLine().split(" ");
		int source =  Integer.parseInt(sd[0]);
		int destination = Integer.parseInt(sd[1]);
		DijkstraSP dsp = new DijkstraSP(G,source);
		//System.out.println(dsp.distTo(destination));
		if(dsp.distTo(destination)==Double.POSITIVE_INFINITY){
			System.out.println("No Path Found.");

		}
		break;

		case "ViaPaths":
			// Handle the case of ViaPaths, where three integers are given.
			// First is the source and second is the via is the one where path should pass throuh.
			// third is the destination.
			// If the path exists print the distance between them.
			// Other wise print "No Path Found."
			break;

		default:
			break;
		}

	}
}