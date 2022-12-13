import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.LSD;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    
    private static final int R = 256;

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
        int[] sorted = new int[t.length()];
        for (int i = 0; i < t.length(); i++)
            sorted[i] = t.charAt(i);
        LSD.sort(sorted);
        SeparateChainingHashST<Character, Queue<Integer>> freq = new SeparateChainingHashST<>();
        for (char i = 0; i < R; i++)
            freq.put(i, new Queue<Integer>());
        for (int i = 0; i < sorted.length; i++)
            freq.get((char)sorted[i]).enqueue(i);
        int[] next = new int[t.length()];
        int curr = first;
        for (int i = 0; i < t.length(); i++) {
            StdOut.println(curr);
            int prev = freq.get(t.charAt(curr)).dequeue();
            next[prev] = curr;
            curr = prev;
        }
        // for (int i : next)
        //     StdOut.println(i);
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) 
            transform();
        else if (args[0].equals("+"))
            inverseTransform();
    }

}
