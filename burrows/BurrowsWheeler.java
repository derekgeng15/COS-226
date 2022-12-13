import java.util.ArrayList;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.LSD;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Queue;

public class BurrowsWheeler {
    private static final int R = 256; // alphabet size

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
        String s = BinaryStdIn.readString();
        // create circular suffix array
        CircularSuffixArray cs = new CircularSuffixArray(s);
        int first = 0;
        int size = cs.length();
        // construct the last char column of sorted suffix array
        char c[] = new char[size]; 
        // 
        for (int i = 0; i < size; i++) {
            int j = cs.index(i);
            if (j != 0) 
                c[i] = s.charAt(j - 1);
            else {      
                first = i;  // starting index found
                c[i] = s.charAt(size - 1); 
            }
        }
        // print index of first and prints the char array 
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
        // create int array from the chars of string t, sort using LSD
        int[] sorted = new int[t.length()];
        for (int i = 0; i < t.length(); i++)
            sorted[i] = t.charAt(i);
        LSD.sort(sorted);   
        // create hash map to store characters & the indexes where they occur
        SeparateChainingHashST<Character, Queue<Integer>> freq = new SeparateChainingHashST<>();
        for (char i = 0; i < R; i++)
            freq.put(i, new Queue<Integer>());
        // enqueue index where that char occurs
        for (int i = 0; i < sorted.length; i++)
            freq.get(t.charAt(i)).enqueue(i); 
        
        // construct next array
        int[] next = new int[t.length()];
        for (int i = 0; i < t.length(); i++) {
            // dequeue index where char occurs in sorted array
            next[i] = freq.get((char)sorted[i]).dequeue();
        }
        // rebuild original string using next[], starting with index = first
        int curr = first;
        for (int i = 0; i < t.length(); i++) {
            BinaryStdOut.write((char)sorted[curr]);
            curr = next[curr];
        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
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
