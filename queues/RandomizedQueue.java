import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] randQueue; // array storing randQueue
    private int first, last; // indexes to first & last element of deque
    private int size; // stores size of deque
    // construct an empty deque
    public RandomizedQueue() {
        randQueue = (Item[]) new Object[1];
        first = 0;
        last = 0;
        size = 0;
    }

    // resizes randQueue[] to nsize
    private void resize(int nsize) {
        Item[] out =  (Item[]) new Object[nsize];
        // copy over elements from first to last into out
        for (int i = 0; i < size(); i++)
            out[i] = randQueue[(first + i) % randQueue.length];
        // save out to randQueue[]
        randQueue = out;
        first = 0;
        last = size;
    }
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0; // empty if size is zero
    }


    // return the number of items on the deque[]
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) // cannot add null items
            throw new IllegalArgumentException("Item added was null");
        if (size + 1 == randQueue.length) // double when at capacity
            resize(randQueue.length * 2);
        // save item and increment last index
        randQueue[last] = item;
        last = (last + 1) % randQueue.length;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size <= 0) // no elements to remove if size <= 0
            throw new NoSuchElementException("randQueue is empty");
        if (size - 1 <= randQueue.length / 4) // resize to 1/2 when at 1/4 length
            resize(randQueue.length / 2);
        // randomly select an item to remove
        int swap = (first + StdRandom.uniform(size)) % randQueue.length;
        Item out = randQueue[swap]; // save return item
        randQueue[swap] = randQueue[first]; // save first item to removed item
        randQueue[first] = null; // set null to avoid loitering
        first = (first + 1) % randQueue.length; // increment to move to new last item
        size--;
        return out;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size <= 0) // no elements to sample if size <= 0
            throw new NoSuchElementException("randQueue is empty");
        return randQueue[(first + StdRandom.uniform(size)) % randQueue.length];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {

        private int[] id; // array of randomized ids
        private int curr; // current id the iterator is on

        // helper class for creating iterator
        public RandomizedQueueIterator() {
            id = new int[size];
            curr = 0;
            for (int i = 0; i < id.length; i++) // save indexes [0, size) to id[]
                id[i] = i;
            StdRandom.shuffle(id); // shuffles id[]
        }
        public boolean hasNext() {
            return curr < size;
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("Exceeded Randomized Queue");
            return randQueue[(first + id[curr++]) % randQueue.length];
        }

    }
    // unit testing (required)
    public static void main(String[] args) {
        int n = 10; // size of test;
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();
        for (int i = 0; i < n; i++)
            rQueue.enqueue(i);
        StdOut.println("Size: " + rQueue.size()); // n
        StdOut.println("Empty: " + rQueue.isEmpty()); // false
        StdOut.print("Single Iterator Test: ");
        for (int i : rQueue) // print [0, n) randomly
            StdOut.print(i + " ");
        StdOut.println();
        StdOut.println("Double Iterator Test: ");
        for (int i : rQueue) { // print [0, n) randomly
            for (int j : rQueue) // print [0, n) randomly
                StdOut.print(i + "-" + j + " ");
            StdOut.println();
        }
        StdOut.println();
        StdOut.println("Sample: " + rQueue.sample());
        while (!rQueue.isEmpty()) // pop [0, n) randomly
            StdOut.print(rQueue.dequeue() + " ");
        StdOut.println();
        StdOut.println("Size: " + rQueue.size()); // 0
        StdOut.println("Empty: " + rQueue.isEmpty()); // true
        // mixed enqueue and dequeue testing
        StdOut.println("Mixed Enqueue/Dequeue Test: ");
        StdOut.println("Dequeued: ");
        for (int i = 0; i < n; i++) {
            rQueue.enqueue(i);
            if (i % 2 != 0) // randomly remove an element for odd i
                StdOut.print(rQueue.dequeue() + " ");
        }
        StdOut.println();
        StdOut.println("Enqueued: ");
        for (int i : rQueue)
            StdOut.print(i + " ");
        StdOut.println();
    }


}
