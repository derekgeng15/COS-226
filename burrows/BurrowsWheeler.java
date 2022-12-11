import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Quick3string;
import edu.princeton.cs.algs4.Quick3way;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray cs = new CircularSuffixArray(s);
        int first = 0;
        int size = cs.length();
        char c[] = new char[size];
        for (int i = 0; i < size; i++) {
            int j = cs.index(i);
            if (j != 0) 
                c[i] = s.charAt(j - 1);
            else {
                first = i;
                c[i] = s.charAt(size - 1);
            }
        }
        BinaryStdOut.write(first);
        for (int i = 0; i < size; i++) 
            BinaryStdOut.write(c[i]);
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString();
        String[] sorted = new String[t.length()];
        for (int i = 0; i < t.length(); i++) {
           // sorted[i] = new String(t.charAt(i));
        }
        Quick3way.sort(sorted);

        int[] next = new int[t.length()];
        int curr = first;
        SeparateChainingHashST<Character, Queue<Integer>> freq = new SeparateChainingHashST<>();
        

    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) 
            transform();
    }

}
