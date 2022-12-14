import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private static final int R = 256; // ascii size

    private String s; // input string
    private int size; // length of s
    private int[] indexes; // stores starting index of the ith sorted suffix 

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) 
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
    private void sort(int lo, int hi, int d) {
        if (d == size)
            return;
        if (lo >= hi)
            return;
        // create hash map to store characters & the indexes where they occur
        SeparateChainingHashST<Character, Queue<Integer>> freq 
            = new SeparateChainingHashST<>();
        // for the character at given index, enqueue index where it occurs
        for (int i = lo; i <= hi; i++) {
            char key = s.charAt((indexes[i] + d) % s.length());
            // add queue if key hasn't been added yet
            if (!freq.contains(key))
                freq.put(key, new Queue<Integer>());
            freq.get(key).enqueue(indexes[i]);
        }
        int ct = lo;
        // iterate through all characters
        for (char i = 0; i < R; i++) {
            if (!freq.contains(i))
                continue;
            int len = freq.get(i).size(); // len 
            // for each character, place indexes into array
            while (!freq.get(i).isEmpty())
                indexes[ct++] = freq.get(i).dequeue();
            // sort each group of characters by the next occuring character
            sort(ct - len, ct - 1, d + 1); 
        }
    }

    // length of s
    public int length() {
        return size;
    }

    // returns starting index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length())
            throw new IllegalArgumentException("i is not in range");
        return indexes[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = "ABBBBAABBBBA";
        CircularSuffixArray t = new CircularSuffixArray(s);
        StdOut.println("length: " + t.length());
        StdOut.print("indexes: ");
        for (int i = 0; i < t.length(); i++) 
            StdOut.print(t.index(i) + " ");
    }
}

