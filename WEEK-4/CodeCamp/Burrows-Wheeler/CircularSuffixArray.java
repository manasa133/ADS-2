import edu.princeton.cs.algs4.SuffixArrayX;
import edu.princeton.cs.algs4.StdOut;
public class CircularSuffixArray {
    private SuffixArrayX suffix;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new NullPointerException();

        suffix = new SuffixArrayX(s);
    }

    // length of s
    public int length() {
        return suffix.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return suffix.index(i);
    }


















    public static void main(String[] args) {
        String[] strings = {
            "ABRACADABRA!",
        };

        for (String s : strings) {
            CircularSuffixArray suffix = new CircularSuffixArray(s);
            StdOut.println(s);
            StdOut.println("length: " + suffix.length());
            StdOut.println("index(0): " + suffix.index(0));
            StdOut.println(suffix);
            StdOut.println();
        }
    }
}
