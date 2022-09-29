import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdOut;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null)
            throw new IllegalArgumentException("Array cannot be null");
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");
        if (comparator == null)
            throw new IllegalArgumentException("Comparator cannot be null");
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0)
                hi = mid - 1; // mid too high, seek lower
            else if (compare > 0)
                lo = mid + 1; // mid too low, seek higher
            else
                hi = mid; // keep mid in bound & find potential lower key

        }
        if (comparator.compare(a[lo], key) != 0) // key not found in array
            return -1;
        return lo;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null)
            throw new IllegalArgumentException("Array cannot be null");
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");
        if (comparator == null)
            throw new IllegalArgumentException("Comparator cannot be null");
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int mid = (lo + hi + 1) >>> 1;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0)
                hi = mid - 1; // mid too high, seek lower
            else if (compare > 0)
                lo = mid + 1; // mid too low, seek higher
            else
                lo = mid; // keep mid in bound & find potential higher keys
        }
        if (comparator.compare(a[lo], key) != 0) // key not found in array
            return -1;
        return lo;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] a = { new Term("a", 1), new Term("a", 2), 
                    new Term("b", 1), new Term("b", 2), 
                    new Term("b", 3), new Term("c", 1), 
                    new Term("d", 1), new Term("d", 2), 
                    new Term("f", 1) };
                    
        Term key = new Term("b", 1);
        for (Term t : a)
            StdOut.println(t + " ");
        StdOut.println("Key: " + key);
        StdOut.println("First Index Of: " + firstIndexOf(a, key, Term.byPrefixOrder(1)));
        StdOut.println("Last Index Of: " + lastIndexOf(a, key, Term.byPrefixOrder(1)));

        // test searching based on reverse weight order
        Arrays.sort(a, Term.byReverseWeightOrder());
        for (Term t : a)
            StdOut.println(t + " ");
        StdOut.println("Key: " + key);
        StdOut.println("First Index: ");
        StdOut.println(firstIndexOf(a, key, Term.byReverseWeightOrder()));
        StdOut.println("Last Index: ");
        StdOut.println(lastIndexOf(a, key, Term.byReverseWeightOrder()));
    }
}
