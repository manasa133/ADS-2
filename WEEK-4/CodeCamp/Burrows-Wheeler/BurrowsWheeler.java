import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BurrowsWheeler {
   /**
    *  Apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    */
   public static void transform() {

      String input = BinaryStdIn.readString();

      CircularSuffixArray circularSuffixArray = new CircularSuffixArray(input);
      for (int i = 0; i < circularSuffixArray.length(); i++) {
         if (circularSuffixArray.index(i) == 0) {
            BinaryStdOut.write(i);
            break;
         }
      }

      for (int i = 0; i < circularSuffixArray.length(); i++) {
         int index = circularSuffixArray.index(i);
         if (index == 0) {
            BinaryStdOut.write(input.charAt(input.length() - 1), 8);
            continue;
         }
         BinaryStdOut.write(input.charAt(index - 1), 8);
      }
      BinaryStdOut.close();
   }

   /**
    *  Apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    */
   public static void inverseTransform() {
      int first = BinaryStdIn.readInt();
      String chars = BinaryStdIn.readString();
      char[] t = chars.toCharArray();
      chars = null;
      int next[] = new int[t.length];
      Map<Character, Queue<Integer>> positions = new HashMap<Character, Queue<Integer>>();
      for (int i = 0; i < t.length; i++) {
         if(!positions.containsKey(t[i]))
            positions.put(t[i], new Queue<Integer>());
         positions.get(t[i]).enqueue(i);
      }
      Arrays.sort(t);
      for (int i = 0; i < t.length; i++) {
         next[i] = positions.get(t[i]).dequeue();
      }
      for (int i = 0, curRow = first; i < t.length; i++, curRow = next[curRow])
         BinaryStdOut.write(t[curRow]);
      BinaryStdOut.close();
   }

   public static void main(String[] args) {
      if (args.length == 0)
         throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
      if (args[0].equals("-"))
         transform();
      else if (args[0].equals("+"))
         inverseTransform();
      else
         throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
   }

}
