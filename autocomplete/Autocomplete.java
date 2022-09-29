import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Autocomplete {

    Term[] terms; // stores all terms

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null)
            throw new IllegalArgumentException("Array cannot be null");
        for (Term t : terms)
            if (t == null)
                throw new IllegalArgumentException("Term in cannot be null");
        this.terms = terms;
        Arrays.sort(this.terms);

    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("prefix cannot be null");
        int n = numberOfMatches(prefix);
        Term[] sortedTerms = new Term[n];
        int first = BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix, 0), 
            Term.byPrefixOrder(prefix.length()));
        for (int i = 0; i < n; i++)
            sortedTerms[i] = terms[first + i];
        Arrays.sort(sortedTerms, Term.byReverseWeightOrder());
        return sortedTerms;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("prefix cannot be null");
        return BinarySearchDeluxe.lastIndexOf(terms, new Term(prefix, 0), 
                Term.byPrefixOrder(prefix.length()))
                - BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix, 0), 
                Term.byPrefixOrder(prefix.length())) + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt(), k = Integer.parseInt(args[1]);
        String query;
        Term[] terms = new Term[n];
        Term[] sortedTerms;

        for (int i = 0; i < n; i++) {
            long weight = in.readLong();
            in.readChar();
            String words = in.readLine();
            terms[i] = new Term(words, weight);
        }

        Autocomplete auto = new Autocomplete(terms);
        while (true) {
            query = StdIn.readLine();
            StdOut.println(auto.numberOfMatches(query) + " matches");
            sortedTerms = auto.allMatches(query);
            for (int i = 0; i < Math.min(k, sortedTerms.length); i++) {
                StdOut.println(sortedTerms[i]);
            }
        }

    }
}
