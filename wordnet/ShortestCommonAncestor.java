
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class ShortestCommonAncestor {

   private Digraph G; // digraph of rooted DAG

   // constructor takes a rooted DAG as argument
   public ShortestCommonAncestor(Digraph G) {
      this.G = new Digraph(G);

      // create directed cycle object to check if G has cycle
      DirectedCycle dCycle = new DirectedCycle(G);
      if (dCycle.hasCycle())
         throw new IllegalArgumentException("graph has a cycle");

      // check if graph has multiple roots
      int root = -1;
      for (int v = 0; v < G.V(); v++) {
         if (G.outdegree(v) == 0) {
            if (root != -1)
               throw new IllegalArgumentException("graph has multiple roots");
            root = v;
         }
      }
      // check if graph has no root
      if (root == -1)
         throw new IllegalArgumentException("graph is has no root");

   }

   // helper class for storing an ancestor
   private class Ancestor {
      private int iD; // stores ID
      private int totDist; // stores distance to this vertex

      // stores ID and dist to this vertex from both nodes
      public Ancestor(int iD, int totDist) {
         this.iD = iD;
         this.totDist = totDist;
      }
   }

   // calculates the shortest common ancestor between set A and B
   private Ancestor bfs(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
      int[] distToV = new int[G.V()]; // array storing dist to vertice V
      int[] distToW = new int[G.V()]; // array storing dist to vertice W
      Queue<Integer> queue = new Queue<>();

      // enqueue all verticies in set A
      for (int a : subsetA) {
         queue.enqueue(a);
         distToV[a] = 1; // start all dist at 1 to mark as visited
      }
      // run BFS from set A
      while (!queue.isEmpty()) {
         int curr = queue.dequeue();
         for (int adj : G.adj(curr)) {
            // queue adj if not visited
            if (distToV[adj] == 0) {
               queue.enqueue(adj);
               distToV[adj] = distToV[curr] + 1;
            }
            // take the smallest dist to each node
            distToV[adj] = Math.min(distToV[adj], distToV[curr] + 1);
         }
      }

      // enqueue all verticies in set B
      for (int b : subsetB) {
         queue.enqueue(b);
         distToW[b] = 1; // start all dist at 1 to mark as visited
      }
      int champ = -1, champDist = Integer.MAX_VALUE;
      // run BFS from set B
      while (!queue.isEmpty()) {
         int curr = queue.dequeue();
         // save if shares ancestor with v
         if (distToV[curr] != 0 && distToV[curr] + distToW[curr] <= champDist) {
            champDist = distToV[curr] + distToW[curr];
            champ = curr;
         }
         for (int adj : G.adj(curr)) {
            // queue adj if not visited
            if (distToW[adj] == 0) {
               queue.enqueue(adj);
               distToW[adj] = distToW[curr] + 1;
            }
            // take smallest dist to each node
            distToW[adj] = Math.min(distToW[adj], distToW[curr] + 1);
         }
      }

      // adjust champDist by offset
      return new Ancestor(champ, champDist - 2);
   }

   // length of shortest ancestral path between v and w
   public int length(int v, int w) {
      if (v < 0 || v >= G.V())
         throw new IllegalArgumentException("v is out of range");
      if (w < 0 || w >= G.V())
         throw new IllegalArgumentException("w is out of range");
      return bfs(Collections.singletonList(v), Collections.singletonList(w)).totDist;
   }

   // a shortest common ancestor of vertices v and w
   public int ancestor(int v, int w) {
      if (v < 0 || v >= G.V())
         throw new IllegalArgumentException("v is out of range");
      if (w < 0 || w >= G.V())
         throw new IllegalArgumentException("w is out of range");
      return bfs(Collections.singletonList(v), Collections.singletonList(w)).iD;
   }

   // checks if subset A and B are valid subsets
   private void checkValid(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
      // checks if subsets are null
      if (subsetA == null)
         throw new IllegalArgumentException("subsetA is null");
      if (subsetB == null)
         throw new IllegalArgumentException("subsetB is null");
      // checks if subsets are empty
      if (!subsetA.iterator().hasNext())
         throw new IllegalArgumentException("element in subsetA is empty");
      if (!subsetB.iterator().hasNext())
         throw new IllegalArgumentException("element in subsetB is empty");
      // checks if subsets contain null elements
      for (Integer a : subsetA) {
         if (a == null)
            throw new IllegalArgumentException("subsetA has null values");
         if (a < 0 || a >= G.V())
            throw new IllegalArgumentException("element in subsetA is out of range");
      }
      for (Integer b : subsetB) {
         if (b == null)
            throw new IllegalArgumentException("subsetB has null values");
         if (b < 0 || b >= G.V())
            throw new IllegalArgumentException("element in subsetB is out of range");
      }
   }

   // length of shortest ancestral path of vertecurr subsets A and B
   public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
      checkValid(subsetA, subsetB);
      return bfs(subsetA, subsetB).totDist;
   }

   // a shortest common ancestor of vertecurr subsets A and B
   public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
      checkValid(subsetA, subsetB);
      return bfs(subsetA, subsetB).iD;
   }

   // unit testing
   public static void main(String[] args) {
      In in = new In("digraph11.txt");
      Digraph G = new Digraph(in);
      ShortestCommonAncestor sca = new ShortestCommonAncestor(G);

      StdOut.println("common ancestor between 0 and 5: " + sca.ancestor(0, 5)); // 4
      StdOut.println("dist between 0 and 5: " + sca.length(0, 5)); // 5
      StdOut.print("common ancestor for set [0, 1, 2] and [8, 9, 10]: ");
      StdOut.println(sca.ancestorSubset(Arrays.asList(0, 1, 2), 
         Arrays.asList(8, 9, 10))); // 4
      StdOut.print("dist for set [0, 1, 2] and [8, 9, 10]: ");
      StdOut.println(sca.lengthSubset(Arrays.asList(0, 1, 2), 
         Arrays.asList(8, 9, 10))); // 2
   }

}
