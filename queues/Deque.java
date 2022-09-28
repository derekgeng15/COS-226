import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // helper node class
    private Node first, last; // node references to first and last of deque
    private int size; // size of deque
    private class Node {
        private Item item; // item stored in node
        private Node prev, next; // references to previous and next node
    }
    // construct an empty deque
    public Deque() {
        first = new Node();
        last = first;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0; // empty if size is zero
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the first
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot push null object");
        // create new node before first
        first.prev = new Node();
        // move first and save item
        first.prev.next = first;
        first = first.prev;
        first.item = item;
        size++;
    }


    // add the item to the last
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot push null object");
        // save item to last node
        last.item = item;
        // create new node next and move last
        last.next = new Node();
        last.next.prev = last;
        last = last.next;
        size++;
    }


    // remove and return the item from the first
    public Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException("Cannot remove from empty deque");
        // save item to return
        Item out = first.item;
        first.item = null; // avoids loitering
        // move first to next node and remove old first
        first = first.next;
        first.prev = null;
        size--;
        return out;
    }


    // remove and return the item from the last
    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException("Cannot remove from empty deque");
        // move to previous node and remove old last
        last = last.prev;
        last.next = null;
        // save item to return
        Item out = last.item;
        last.item = null; // avoids loitering
        size--;
        return out;
    }

    // return an iterator over items in order from first to last
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current; // current position on deque
        // set current to front of deque
        public DequeIterator() {
            current = first;
        }
        public boolean hasNext() {
            return current.next != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No More Elements in Deque");
            // save item to return
            Item out = current.item;
            // iterate current to next node;
            current = current.next;
            return out;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 20; // size of test
        Deque<Integer> deque = new Deque<>();
        // add [0, n) to front 
        for (int i = 0; i < n; i++)
            deque.addFirst(i);
        while (!deque.isEmpty())
            StdOut.print(deque.removeFirst() + " "); // n - 1 to 0
        StdOut.println();
        // add [0, n) to back
        for (int i = n - 1; i >= 0; i--)
            deque.addLast(i);
        while (!deque.isEmpty())
            StdOut.print(deque.removeLast() + " "); // 0 to n - 1
        StdOut.println();

        for (int i = 0; i < n; i++)
            if (i % 2 == 0)
                deque.addFirst(i); // add evens to front
            else
                deque.addLast(i); // add odds to back
        StdOut.println("Size: " + deque.size()); // n
        StdOut.println("Empty: " + deque.isEmpty()); // false
        StdOut.print("Single Iterator Test: ");
        for (int i : deque) // evens from descending, odds ascending
            StdOut.print(i + " ");
        StdOut.println();
        StdOut.println("Double Iterator Test: ");
        for (int i : deque) { // evens from descending, odds ascending
            for (int j : deque) // evens from descending, odds ascending
                StdOut.print(i + "-" + j + " ");
            StdOut.println();
        }
        StdOut.println();
        for (int i = 0; i < n; i++) // print n -> 0
            if (i % 2 == 0)
                StdOut.print(deque.removeLast() + " "); // pop back if even
            else
                StdOut.print(deque.removeFirst() + " "); // pop front if odd
        StdOut.println();
        StdOut.println("Size: " + deque.size()); // 0
        StdOut.println("Empty: " + deque.isEmpty()); // true

    }


}
