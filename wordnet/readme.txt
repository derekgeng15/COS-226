Programming Assignment 6: WordNet


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 **************************************************************************** */
First, we used two red-black binary search trees as symbol tables to map
synsets to IDs, as we want to represent all of WordNet in a directed
graph. Operations on red-black trees are all Θ(log(N)) worst case,
which meets the runtime requirements. 

For mapping nouns -> IDs, our key was a String and the value was a 
collection of integers, as a noun may appear in multiple synsets.
In this case, we used a queue due to Θ(1) insertion. 

For mapping IDs -> synsets, our key was an Integer, the ID, and the value
was a string, the corresponding synset. 

/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 **************************************************************************** */
To store the hypernyms, we use a digraph. The vertex represents the original
synset, and the edges represent the other synsets it points to as specified
in the hypernyms text file. 

We use this because it provides a good representation of all the different 
inputs and outputs to and from the synsets, and allows us to traverse paths 
to see how different synsets are connected.


/* *****************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithm? Express your answer as a function of the
 *  number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify
 *  your answer.
 **************************************************************************** */

Description:
We checking for if the digraph is rooted and acyclic individually.
To check if the digraph is rooted, cycle through all verticies and ensure that 
only one vertex has an outdegree of zero. Any more or less means that the 
digraph is not rooted.
For checking cycles, we use the Directed Cycle class, which returns whether the 
inputed digraph has a cycle. If the digraph passes both the root and cycle
test, then it is a DAG


Order of growth of running time: Θ(V + E)


/* *****************************************************************************
 *  Describe concisely your algorithm to compute the shortest common ancestor
 *  in ShortestCommonAncestor. For each method, give the order of growth of
 *  the best- and worst-case running times. Express your answers as functions
 *  of the number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify your
 *  answers.
 *
 *  If you use hashing, assume the uniform hashing assumption so that put()
 *  and get() take constant time per operation.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't forget
 *  to count the time needed to initialize the marked[], edgeTo[], and
 *  distTo[] arrays.
 **************************************************************************** */

Description:
To compute SCA, we run two BFS's, one for input v and one for input w. On the 
first run through, we keep track of which vertices are visited. On the 
second run through, for each vertex that is visited again, we store that vertex
as well as the distance to that vertex in a common ancestor queue. Then we 
iterate through that queue and keep track of the champ minimum distance of the
original vertices from that common ancestor. The common ancestor with the
champ minimum distance is returned. 

To generalize this algorithm to input sets, we queue all verticies in the input 
set and run our BFS algorithm as normal.


                                 running time
method                  best case            worst case
--------------------------------------------------------
length()                Θ(V)                 Θ(V + E)

ancestor()              Θ(V)                 Θ(V + E)

lengthSubset()          Θ(V)                 Θ(V + E)

ancestorSubset()        Θ(V)                 Θ(V + E)



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe any serious problems you encountered.                    
 **************************************************************************** */
We didn't realize at first that the same noun could map to multiple IDs, so to 
fix that we included a queue of the IDs in the two red black trees instead of 
just storing the first instance's ID.


/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
We followed the partner protocal as described on the assignment page.

Derek Geng: brainstormed how to find SCA, tested to see if digraph was a DAG. 
Emily Luo: developed the BFS method, fixed the BSTs to include repeat nouns.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **************************************************************************** */
