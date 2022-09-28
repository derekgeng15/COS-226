import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]); // number of strings to output
        // Randomized queue to store strings
        RandomizedQueue<String> rQueue = new RandomizedQueue<>();
        for (int i = 0; i < k; i++)
            rQueue.enqueue(StdIn.readString());
        int n = k + 1; // current # of strings read in
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString(); // input string
            // replace an item in queue if probablity lands k / (k + 1)
            if (StdRandom.uniform(n) < k) {
                rQueue.dequeue();
                rQueue.enqueue(s);
            }
            n++; // update n
        }
        while (!StdIn.isEmpty())
            rQueue.enqueue(StdIn.readString());
        while (!rQueue.isEmpty()) // empty out rQueue (size k)
            StdOut.println(rQueue.dequeue()); // print elements at random
    }

}
