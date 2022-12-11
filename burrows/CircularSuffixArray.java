import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Quick3string;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {

    private String s;
    private String[] suffixes;
    // private int[] indexes;
    private int size;
    private int[] indexes;
    private final int R;
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        this.s = s;
        size = s.length();
        indexes = new int[size];
        R = 256;
        
        for (int i = 0; i < size; i++)
            indexes[i] = i;


        sort(0, size-1, 0);
    }

    private void sort (int lo, int hi, int d) {
        if (lo >= hi)
            return;
        SeparateChainingHashST<Character, Queue<Integer>> freq = new SeparateChainingHashST<>();
        for (char i = 0; i < R; i++)
            freq.put(i, new Queue<Integer>());
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
        return indexes[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray t = new CircularSuffixArray(s);
        for (int i = 0; i < s.length(); i++) {
            StdOut.print(t.index(i) + " ");
        
        }
    }
}

