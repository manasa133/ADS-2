//tps://github.com/vinsonlee/coursera/blob/master/algs4partII-004/boggle/BoggleSolver.java
//import edu.princeton.cs.algs4.*;

// Java code for adding elements in Set
import java.util.Set;
import java.util.TreeSet;
public class BoggleSolver
{
	private static final char Q_LETTER = 'Q';

   private static final String QU_STRING = "QU";
	private FastPrefixTST dictionary;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary1){
    	this.dictionary = new FastPrefixTST();
    	for(int i =0 ;i<dictionary1.length;i++){
    		dictionary.put(dictionary1[i],i);
    	}

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
      if (board == null)
         throw new java.lang.IllegalArgumentException("Board is null!");
      // choose set because each word must appears only once
      Set<String> foundWords = new TreeSet<String>();
      // for all dices as first letter
      for (int row = 0; row < board.rows(); row++) {
         for (int col = 0; col < board.cols(); col++) {
            // current watching string
            String charSequence = addLetter("", board.getLetter(row, col));
            // array with visited dices
            boolean marked[][] = new boolean[board.rows()][board.cols()];
            marked[row][col] = true;
            dfs(foundWords, charSequence, marked, row, col, board);
         }
      }
      return foundWords;
   }

    private void dfs(Set<String> foundWords, String charSequence, boolean[][] marked,
         int startRow, int startCol, BoggleBoard board) {
      // add valid word to set
      if (isValidWord(charSequence) ) foundWords.add(charSequence);
      // check all adjacent dices
      // max & min methods to avoid going beyond the borders of board
      // start upper left adjacent dice
      for (int row = Math.max(0, startRow - 1); row <= Math.min(board.rows() - 1, startRow + 1); row++) {
         for (int col = Math.max(0, startCol - 1); col <= Math.min(board.cols() - 1,startCol + 1); col++) {
            if (marked[row][col]) continue;
            if (!dictionary.hasPrefix(charSequence)) continue;
            // prepare to recursive call
            marked[row][col] = true;
            dfs(foundWords, addLetter(charSequence, board.getLetter(row, col)), marked, row, col, board);
            // roll back after recursive call
            marked[row][col] = false;
         }
      }
   }

   private String addLetter(String to, char letter) {
      if (letter == Q_LETTER) return to + QU_STRING;
      else return to + letter;
   }

   private boolean isValidWord(String currentWord) {
      if (currentWord == null) return false;
      if (dictionary.contains(currentWord) && currentWord.length() > 2) return true;
      else return false;
   }



    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
    	//if (dictionary.contains(word)) {
            switch (word.length()) {
            case 0:
            case 1:
            case 2:
                return 0;
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
            default:
                return 11;
            }
        // } else {
        //     return 0;
        // }

    }
    public static void main(String[] args) {
    In in = new In(args[0]);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    for (String word : solver.getAllValidWords(board)) {
        StdOut.println(word);
        score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
}


}
