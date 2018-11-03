import java.util.*;
class PageRank {
	Digraph graph;
	double pageRankArr[];
	PageRank(Digraph g){
		graph = g;
		pageRankArr = new double[g.V()];
		System.out.println((double)1/4);
		for(int i =0;i< g.V();i++){
			pageRankArr[i]= 1/g.V();
		}



	}
	double getPR(int v){
		double one = 1/4;




		return 0;

	}
	public String toString(){
		String res ="";
		for(int i=0;i<graph.V();i++){
			System.out.println(pageRankArr[i]);
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
		PageRank prObj =  new PageRank(graph);
		System.out.println(prObj.graph);
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
