import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

   private WordNet wordnet; // WordNet object

   // constructor takes a WordNet object
   public Outcast(WordNet wordnet) {
      this.wordnet = wordnet;
      
   }

   // given an array of WordNet nouns, return an outcast
   public String outcast(String[] nouns) {
      // keep track of outcasts
      int champ = 0;
      int champID = 0;

      for (int i = 0; i < nouns.length; i++) {
         int sumDist = 0;
         // compute the dist to all nouns in the set
         for (int j = 0; j < nouns.length; j++)
            sumDist += wordnet.distance(nouns[i], nouns[j]);
         // save dist if greater than champ dist
         if (sumDist >= champ) {
            champ = sumDist;
            champID = i;
         }
      }
      return nouns[champID];
   }

   // test client (see below)
   public static void main(String[] args) {
      WordNet wordnet = new WordNet(args[0], args[1]);
      Outcast outcast = new Outcast(wordnet);
      for (int t = 2; t < args.length; t++) {
        In in = new In(args[t]);
        String[] nouns = in.readAllStrings();
        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
      }
   }

}
