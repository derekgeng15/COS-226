import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private String s; // input string
    private int size; // length of s
    private int[] indexes; // stores indexes
    private static final int R = 256; // alphabet size

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s.equals(null)) 
            throw new IllegalArgumentException("String cannot be null");
        this.s = s;
        size = s.length();
        // build default indexes array
        indexes = new int[size];
        for (int i = 0; i < size; i++)
            indexes[i] = i;
        sort(0, size-1, 0);
    }
    // sort the suffixes
    private void sort (int lo, int hi, int d) {
        if (lo >= hi)
            return;
        // create hash map to store characters & the indexes where they occur
        SeparateChainingHashST<Character, Queue<Integer>> freq = new SeparateChainingHashST<>();
        for (char i = 0; i < R; i++)
            freq.put(i, new Queue<Integer>());
        // for the character at given index, enqueue index where it occurs
        for (int i = lo; i <= hi; i++)
            freq.get(s.charAt((indexes[i] + d) % s.length())).enqueue(indexes[i]);
        int ct = lo;
        for (char i = 0; i < R; i++) {
            int size = freq.get(i).size();
            while(!freq.get(i).isEmpty())
                indexes[ct++] = freq.get(i).dequeue();
            sort(ct - size, ct - 1, d + 1); 
        }
    }

    // length of s
    public int length() {
        return size;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length())
            throw new IllegalArgumentException("i is not in range");
        return indexes[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray t = new CircularSuffixArray(s);
        StdOut.println("length: " + t.length());
        StdOut.print("indexes: ");
        for (int i = 0; i < s.length(); i++) {
            StdOut.print(t.index(i) + " ");
        }
    }
}

