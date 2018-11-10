import  java.util.*;
public class Solution {

	public static void main(String[] args) {
		// Self loops are not allowed...
		// Parallel Edges are allowed...
		// Take the Graph input here...
		Scanner sc = new Scanner(System.in);
		int Vertices = Integer.parseInt(sc.nextLine());
		int Edges = Integer.parseInt(sc.nextLine());
		EdgeWeightedGraph G = new EdgeWeightedGraph(Vertices);
		for(int i =0 ;i < Edges;i++){
			String[] vw= sc.nextLine().split(" ");
			Edge e = new Edge(Integer.parseInt(vw[0]),Integer.parseInt(vw[1]),
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
		DijkstraUndirectedSP dsp = new DijkstraUndirectedSP(G,source);

		if(dsp.distTo(destination)==Double.POSITIVE_INFINITY){
			System.out.println("No Path Found.");

		}else{
				System.out.println(dsp.distTo(destination));
		}
		break;

		case "ViaPaths":
			// Handle the case of ViaPaths, where three integers are given.
			// First is the source and second is the via is the one where path should pass throuh.
			// third is the destination.
			// If the path exists print the distance between them.
			// Other wise print "No Path Found."
		String[] svd = sc.nextLine().split(" ");
		int source2 =  Integer.parseInt(svd[0]);
		int v =  Integer.parseInt(svd[1]);
		int destination2 = Integer.parseInt(svd[2]);
		DijkstraUndirectedSP dsp2 = new DijkstraUndirectedSP(G,source2);
		if(dsp2.distTo(destination2)==Double.POSITIVE_INFINITY || dsp2.distTo(v)==Double.POSITIVE_INFINITY){
			System.out.println("No Path Found.");

		}else{
			DijkstraUndirectedSP dsp3 = new DijkstraUndirectedSP(G,v);
				System.out.println(dsp2.distTo(v) +dsp3.distTo(destination2) );
				System.out.println(dsp2.pathTo(v)+""+ dsp3.pathTo(destination2)+""+destination2);
				System.out.println();
		}

			break;

		default:
			break;
		}

	}
}