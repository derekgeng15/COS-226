import java.util.Comparator;

import edu.princeton.cs.algs4.StdOut;

public class Term implements Comparable<Term> {

    private String query; // query of term
    private long weight; // weight of term

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null)
            throw new IllegalArgumentException("query is null");
        if (weight < 0)
            throw new IllegalArgumentException("weight cannot be negative");
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new WeightComparator();
    }

    // helper class for weight comparator
    private static class WeightComparator implements Comparator<Term> {

        public int compare(Term o1, Term o2) {
            // negative if o2 < o1, 0 if o2 == o1, postive o2 > o1
            return (int) (o2.weight - o1.weight);
        }

    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        return new PrefixComparator(r);
    }

    private static class PrefixComparator implements Comparator<Term> {

        private int r; // length of query to compare

        // initializes Prefix Comparator 
        public PrefixComparator(int r) {
            if (r < 0)
                throw new IllegalArgumentException("r cannot be negative");
            this.r = r;
        }
        
        // compares the substrings from 0 to r of both terms
        public int compare(Term o1, Term o2) {
            // string length is restricted 
            String s1 = o1.query.substring(0, Math.min(r, o1.query.length()));
            String s2 = o2.query.substring(0, Math.min(r, o2.query.length()));
            return s1.compareTo(s2);

        }

    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return query.compareTo(that.query); // use String compareTo()
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(weight);
        sb.append("\t");
        sb.append(query);
        return sb.toString();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] terms = { new Term("apple", 1), new Term("antsy", 12), 
                        new Term("banana", 3), new Term("ants", 12)};
        for (Term t : terms)
            StdOut.println(t.toString()); // testing toString()
        // testing lexographic compare
        StdOut.println("Lexographic Compare: ");
        for (int i = 0; i < terms.length; i++) {
            for (int j = i + 1; j < terms.length; j++) {
                StdOut.print("(" + terms[i] + ", " + terms[j] + "): ");
                int cmp = terms[i].compareTo(terms[j]);
                if (cmp < 0)
                    StdOut.println("Less");
                else if (cmp > 0)
                    StdOut.println("Greater");
                else 
                    StdOut.println("Equal");
            }
        }

        // testing weight compare
        StdOut.println("Weight Compare: ");
        Comparator<Term> weightCompare = byReverseWeightOrder();
        for (int i = 0; i < terms.length; i++) {
            for (int j = i + 1; j < terms.length; j++) {
                StdOut.print("(" + terms[i] + ", " + terms[j] + "): ");
                int cmp = weightCompare.compare(terms[i], terms[j]);
                if (cmp < 0)
                    StdOut.println("Greater");
                else if (cmp > 0)
                    StdOut.println("Less");
                else 
                    StdOut.println("Equal");
            }
        }

        // testing prefix compare
        
        StdOut.println("Prefix Compare: ");
        Comparator<Term> prefixCompare = byPrefixOrder(3);
        for (int i = 0; i < terms.length; i++) {
            for (int j = i + 1; j < terms.length; j++) {
                StdOut.print("(" + terms[i] + ", " + terms[j] + "): ");
                int cmp = prefixCompare.compare(terms[i], terms[j]);
                if (cmp < 0)
                    StdOut.println("Greater");
                else if (cmp > 0)
                    StdOut.println("Less");
                else 
                    StdOut.println("Equal");
            }
        }
        
    }
}
