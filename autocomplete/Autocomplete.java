import java.util.Arrays;

public class Autocomplete {

    Term[] terms; // stores all terms

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        this.terms = terms;
        Arrays.sort(terms);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        int n = numberOfMatches(prefix);
        Term[] sortedTerms = new Term[n];
        int first = BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        for (int i = 0; i < n; i++)
            sortedTerms[i] = terms[first + i];
        Arrays.sort(sortedTerms, Term.byReverseWeightOrder());
        return sortedTerms;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix){
        return BinarySearchDeluxe.lastIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length())) 
            - BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}
