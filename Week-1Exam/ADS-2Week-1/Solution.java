import java.util.*;
class PageRank {
	Digraph graph;
	double pageRankArr[];
	double tempPR[];
	Digraph reverse;
	PageRank(Digraph g){
		//System.out.println(5.0/8.0);
		graph = g;

		pageRankArr = new double[g.V()];
		for(int j=0; j<g.V();j++){
			if(graph.outdegree(j)==0){
				for(int k=0;k<g.V();k++){
				if(k!=j){
					graph.addEdge(j,k);
				}
			}
			}
		}
		reverse = graph.reverse();
		for(int i =0;i< g.V();i++){
			pageRankArr[i]= (double)1.0/g.V();
		}
		tempPR= new double[g.V()];
		int iterations =1000;
		while(iterations>0){
			//System.out.println("IIII"+iterations);
			for(int j=0;j<g.V();j++){
				tempPR[j] = pageRankArr[j];
			}
			for(int i =0;i< g.V();i++){
				//System.out.println("V-"+i);
				pageRankArr[i]= getPR(i);
				//System.out.println();
			}
			if(Arrays.equals(tempPR, pageRankArr)) {
				break;
			}
			iterations--;
		}
	}

	double getPR(int v){

		if(graph.indegree(v) == 0){
			return 0.0;
		}
		double sum=0.0;
			int indegreeCount  = graph.indegree(v);
			for(int adjs :  reverse.adj(v)){
					sum+=(tempPR[adjs]/graph.outdegree(adjs));

			}

		return sum;

	}
	public String toString(){
		String res ="";
		for(int i=0;i<graph.V();i++){
			System.out.println(i+" - "+pageRankArr[i]);
		}
		return res;
	}



}

class WebSearch {

}


public class Solution {
	public static void main(String[] args) {
		// read the first line of the input to get the number of vertices
		Scanner sc = new Scanner(System.in);
		int vertices = Integer.parseInt(sc.nextLine());
		Digraph graph = new Digraph(vertices);

		// iterate count of vertices times
		// to read the adjacency list from std input
		// and build the graph
		for(int i=0;i<vertices;i++){
			String[] list = sc.nextLine().split(" ");
			for(int j=1;j<list.length;j++){
				graph.addEdge(Integer.parseInt(list[0]),Integer.parseInt(list[j]));
			}
		}
		// Create page rank object and pass the graph object to the constructor
		System.out.println(graph);
		PageRank prObj =  new PageRank(graph);

		System.out.println(prObj);

		// print the page rank object

		// This part is only for the final test case

		// File path to the web content
		String file = "WebContent.txt";


		// instantiate web search object
		// and pass the page rank object and the file path to the constructor

		// read the search queries from std in
		// remove the q= prefix and extract the search word
		// pass the word to iAmFeelingLucky method of web search
		// print the return value of iAmFeelingLucky

	}
}
