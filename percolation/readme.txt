/* *****************************************************************************
 *  Operating system: MacOS
 *  Compiler: Java JDK 17.0.2
 *  Text editor / IDE: Intellij
 *
 *  Have you taken (part of) this course before: No
 *  Have you taken (part of) the Coursera course Algorithms, Part I or II: No
 *  Hours to complete assignment (optional): 3
 *
 **************************************************************************** */

Programming Assignment 1: Percolation


/* *****************************************************************************
 *  Describe the data structures (i.e., instance variables) you used to
 *  implement the Percolation API.

 Used a 2D boolean array "open" (size nxn) to represent whether a site is opened,
 where the first index represented the row # [0, n),
 and the second index represented the col # [0, n)

 Used two union finds: "Master Union Find" (mUF), "Final Union Find" (fUF). mUF
 had a master node that connected to the top row, and fUF had a final node that
 connected to the last row. Each time a site was opened, the corresponding node
 on each union find would be connected to adjacent sites that were also open
 **************************************************************************** */


/* *****************************************************************************
 *  Briefly describe the algorithms you used to implement each method in
 *  the Percolation API.
 **************************************************************************** */
open():
First, return if the current site is already open.

Increment the number of open sites.

Set the current site as open in our boolean array.

Check each adacent tile (up, down, left, right). If the adjacent tile is open,
then union them together in our Union Find.

If the current node we are on shares a set with the final set with the final
node in fUF and is connected to the master node, connect the current node
in mUF to the final node in mUF to percolate the system.

isOpen():
Check the boolean array open for if the current tile is open

isFull():
A site is full if it is in the same set as the master node, as that means there
is a path of adjacent tiles to the top row.

To check if full, call finds on current and master
to see if the current site and the master node share a root

numberOfOpenSites():
Return the counter of open sites

percolates():
If the final node and the master node are in the same set, that means
there is a path from some site in the top row to some site in the bottom row

To check for percolation, call finds on final and master
to see if the final and master node share a root

/* *****************************************************************************
 *  First, implement Percolation using QuickFindUF.
 *  What is the largest values of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 *  n = 268
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
25          0.017
50          0.101
100         1.227
200         18.543
250         44.515

/* *****************************************************************************
 *  Describe the strategy you used for selecting the values of n.
 **************************************************************************** */
I first started by n = 25, and doubled each value to get another n. n=400 was
too high, so used 250 instead.

/* *****************************************************************************
 *  Next, implement Percolation using WeightedQuickUnionUF.
 *  What is the largest values of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 *  n = 2500
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
125         0.072
250         0.272
500         1.03
1000        5.02
2000        30.087


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */




/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.

 TA: Morgan
 **************************************************************************** */


/* *****************************************************************************
 *  Describe any serious problems you encountered.

 At first, it was difficult to check for percolation faster than n**2 time,
 as all I could think to do is brute force check if any of the top row sets
 were the same as the last row. But when I realized that the problem didn't
 specify we needed to know which specific path was taken to the bottom,
 I realized that I could add in two extra nodes (one connected to the top,
 one connected to the bottom) and check those nodes to see if sites in
 the top and bottom rows were connected.

 One other problem I ran into was selecting random nodes. Just randomly
 selecting nodes inefficient, as oftentimes the randomizer would repeat chosen
 nodes. So, I constructed an array with all the node IDs [0, n), and used
 StdRandom to randomly shuffle the array. Then, I iterated through the array and
 open sites until the system percolated. This way, each site is only opened
 once and the random selection requirement is still satisfied.

 To solve backwash, I realized that just having one Union-Find makes it
 impossible. We use common sets to check for if a site is full and connected
 to the final node. Thus, if we used two union finds instead (one for checking
 if it is connected to the master node, one for checking if it is connected
 to the final node), we can aliveate this issue. Let fUF be the second
 union find for checking if a node is connected to the bottom row. If we open
 a new site, and that site is connected to the master node in mUF and to the
 final node in fUF, we can deduce that the system percolates through that
 set. Thus, we can directly connect that set to the final node to ensure
 percolation.
 **************************************************************************** */



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
 The checkstyle program indicates that my direction for loop in open() in
 Percolation.java was faulty, as the method should not use a for loop. However,
 my implementation for checking directions has arguably cleaner syntax and is
 more efficent, as I am not repeating code for every adjacent tile in checking.
