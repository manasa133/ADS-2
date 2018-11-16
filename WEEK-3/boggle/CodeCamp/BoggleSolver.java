// import edu.princeton.cs.algs4.*;
import java.util.*;

public class BoggleSolver
{
	private TST<Integer> tst;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dic){
    	tst = new TST<Integer>();
    	for(int i = 0; i < dic.length; i++){
    		tst.put(dic[i], i);
    	}

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
    	ArrayList<String> list = new ArrayList<String>();
    	for(int i= 0;i<board.rows();i++){
    		for(int j= 0;j<board.cols();j++){
    			boolean marked[][] = new boolean[board.rows()][board.cols()];
    			dfs(i,j,marked,list,board,"");

    		}
    	}
    	return list;

    }

    void dfs(int row,int col, boolean[][]marked,ArrayList<String> list,BoggleBoard board,String str){
    	marked[row][col]= true;
    	str += board.getLetter(row,col)+"";
    	if(str.length()>2 && tst.contains(str)){
    		if(!list.contains(str)){
    			list.add(str);
    		}
    	}
    	for(int r= row-1;r<row-1+3;r++){
	    	for(int c= col-1;c<col-1+3;c++){
	    		if(validate(r,c,board) && !marked[r][c]){
	    			dfs(r,c,marked,list,board,str);
	    			marked[r][c]=false;

	    		}
	    	}
    	}
    }

    boolean validate(int row,int col,BoggleBoard board){
    	if(row>=0 && col>=0 && row<board.rows() && col < board.cols()){
    		return true;
    	}
    	return false;

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