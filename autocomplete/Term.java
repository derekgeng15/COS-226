import java.util.Comparator;

public class Term implements Comparable<Term> {

    private String query; // query of term
    private long weight; // weight of term

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
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

        // initializes r variable
        public PrefixComparator(int r) { 
            this.r = r;
        }
        // compares the substrings from 0 to r of both terms
        public int compare(Term o1, Term o2) {
            return o1.query.substring(0, r).compareTo((o2.query.substring(0, r)));

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
        
    }
}
