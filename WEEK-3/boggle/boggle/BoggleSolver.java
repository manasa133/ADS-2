// import edu.princeton.cs.algs4.*;
import java.util.*;

public class BoggleSolver
{
	private TST<Integer> tst;
	private boolean[][] marked;
	private BoggleBoard board;
	private int score = 0;
	private ArrayList<String> list;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dic){
    	tst = new TST<Integer>();
    	for(int i = 0; i < dic.length; i++){
    		tst.put(dic[i], i);
    	}
    	score = 0;
    	list = new ArrayList<String>();
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard boar){
    	this.board = boar;
    	int row = board.rows();
    	int col = board.cols();
    	for(int i = 0; i < row; i++){
    		for(int j = 0; j < col; j++){
    			marked = new boolean[board.rows()][board.cols()];
    			dfs("",i,j);
    		}
    	}
    	Collections.sort(list);
    	return list;
    }

    private void dfs(String str,int i, int j){
    		marked[i][j] = true;

    		if(board.getLetter(i,j) == 'Q'){
    			str += board.getLetter(i,j) + "U";
    		}else{
    			str += board.getLetter(i,j);
    		}
    	   	if(!tst.hasPrefixOf(str)){
    	   		return;
    	   	}
    	   	if(str.length() > 2){
    	   		if(tst.contains(str) && !list.contains(str)){
    	   			System.out.println(str);
    	   			score +=scoreOf(str);
    	   			list.add(str);
    	   		}
    	   	}
    	   	for(int k = i - 1; k < i - 1 + 3; k++){
    	   		for(int l = j - 1; l < j - 1 + 3; l++){
    	   			if(validate(k, l) && !marked[k][l]){
    	   				dfs(str,k,l);
    	   				marked[k][l] = false;
    	   			}
    	   		}
    	   	}

    }

    private boolean validate(int i, int j){
    	if(i >= 0 && i < board.rows() && j >= 0 && j < board.cols()){
    		return true;
    	}return false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
    	if(word.length() == 0 || word.length() == 1 || word.length() == 2){
    		return 0;
    	}
    	else if(word.length() == 3 || word.length() == 4){
    		return 1;
    	}else if(word.length() == 5){
    		return 2;
    	}else if(word.length() == 6){
    		return 3;
    	}else if(word.length() == 7){
    		return 5;
    	}else {
    		return 11;
    	}
    }
    public static void main(String[] args) {
    In in = new In(args[0]);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    for (String word : solver.getAllValidWords(board)) {
        System.out.println(word);
        score += solver.scoreOf(word);
    }
    System.out.println("Score = " + score);
	}
}