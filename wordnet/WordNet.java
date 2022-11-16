import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;
// import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
   // symtable mapping nouns to corresponding IDs
   private RedBlackBST<String, Queue<Integer>> map; 
   private RedBlackBST<Integer, String> synMap; // maps IDs to synsets
   private Digraph G; // digraph representing the wordnet 
   private ShortestCommonAncestor sca; // used to compute sca for this digraph

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
      In fSynSets = new In(synsets);
      map = new RedBlackBST<>();
      synMap = new RedBlackBST<>();

      while (!fSynSets.isEmpty()) {
         // split line by commas
         String[] lineArray = fSynSets.readLine().split("[,]", 0);
         int ID = Integer.parseInt(lineArray[0]);
         // parse each noun in second element
         for (String s : lineArray[1].split("[ ]", 0)) {
            if (!map.contains(s))
               map.put(s, new Queue<>());
            // for each noun, add on new corresponding ID
            map.get(s).enqueue(ID);
         }
         // save ID -> synset mapping
         synMap.put(ID, lineArray[1]);
      }
      G = new Digraph(synMap.size());
      In fHypernyms = new In(hypernyms);
      while (!fHypernyms.isEmpty()) {
         // split line by commas
         String[] numArray = fHypernyms.readLine().split("[,]", 0);
         int ID = Integer.parseInt(numArray[0]); // starting node
         // for each ID in the line, create directed edge from starting node
         for (int i = 1; i < numArray.length; i++) 
            G.addEdge(ID, Integer.parseInt(numArray[i]));
      }
      // sca for this graph
      sca = new ShortestCommonAncestor(G);
      fSynSets.close();
      fHypernyms.close();
   }

   // the set of all WordNet nouns
   public Iterable<String> nouns() {
      return map.keys();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
      if (word == null) 
         throw new IllegalArgumentException("word cannot be null");
      // checks if noun is in this wordnet 
      return map.contains(word);
   }

   // a synset (second field of synsets.txt) that is a shortest common ancestor
   // of noun1 and noun2 (defined below)
   public String sca(String noun1, String noun2) {
      if (!isNoun(noun1) && !isNoun(noun2))
         throw new IllegalArgumentException("word is not in WordNet");
      // finds the set of IDs for each nodes and computes the sca
      return synMap.get(sca.ancestorSubset(map.get(noun1), map.get(noun2)));
   }

   // distance between noun1 and noun2 (defined below)
   public int distance(String noun1, String noun2) {
      if (!isNoun(noun1) && !isNoun(noun2))
         throw new IllegalArgumentException("word is not in WordNet");
      // finds the set of IDs for each nodes and computes the distance
      return sca.lengthSubset(map.get(noun1), map.get(noun2));
   }

   // unit testing 
   public static void main(String[] args) {
      WordNet testnet = new WordNet("synsets15.txt", "hypernyms15Tree.txt");
      // test distances
      StdOut.println(testnet.distance("a", "b")); // 1
      StdOut.println(testnet.distance("d", "c")); // 3
      StdOut.println(testnet.distance("o", "i")); // 7
      // test sca
      StdOut.println(testnet.sca("a", "b")); // a
      StdOut.println(testnet.sca("d", "c")); // a
      StdOut.println(testnet.sca("o", "i")); // b
      // test noun iterator
      for (String s : testnet.nouns()) 
         StdOut.println(s);

      WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
      // test distances
      StdOut.println(wordnet.distance("white_marlin", "mileage")); // 22
      StdOut.println(wordnet.distance("Black_Plague", "black_marlin")); // 33
      StdOut.println(wordnet.distance("American_water_spaniel", "histology")); // 27
      StdOut.println(wordnet.distance("Brown_Swiss", "barrel_roll")); // 29
      StdOut.println(wordnet.distance("individual", "edible_fruit"));
      StdOut.println(wordnet.distance("congener", "alpha_methyl_dopa"));
      // test scas
      StdOut.println(wordnet.sca("white_marlin", "mileage"));
      StdOut.println(wordnet.sca("congener", "alpha_methyl_dopa"));
      // test is noun
      StdOut.println(wordnet.isNoun("123127836"));

   }

}
