Programming Assignment 2: Deques and Randomized Queues


/* *****************************************************************************
 *  Explain briefly how you implemented the randomized queue and deque.
 *  Which data structure did you choose (array, linked list, etc.)
 *  and why?
 **************************************************************************** */
For the deque, I used a doubly linked list data structure. Because we had to
implement both a stack and queue in the same data structure, each node needed
to be able to reference both the next and previous node, thus requiring a 
doubly linked list. Implementation from there was straightforward, as I could
now freely push and pop nodes from the beginning and end of the doubly linked
list, simulating a deque. In addition, the runtime requirement for the deque was 
O(1) with each operation, so I had to use a linked list instead of a resizing
array. For the iterator, I created a reference pointing to the first node, and
had it traverse the list until it became null. 

For the randomized queue, my concept was to implement it the same as a normal
queue, but randomly swap the front element with another element in the queue 
to return elements in a randomized order. However, with a linked list
accessing each element requires O(n) traversal, so I needed to use a resizing
array instead to have O(1) access to each element. From there, I used StdRandom
to generate a random index to swap with the first element with popping, 
creating a randomized queue. Luckily, the program was allowed to have O(1)
operations in amortized time, so a resizing array met the runtime requirements.
For the iterator, I generated a list of indicies [0, n) and used StdRandom to
shuffle the indicies. From there, I had another integer i=0 iterate through
this list and return the items in random order. Constructing this iterator takes
O(n) time, as that's how long it takes to create + shuffle the list of indices.

/* *****************************************************************************
 *  How much memory (in bytes) do your data types use to store n items
 *  in the worst case? Use the 64-bit memory cost model from Section
 *  1.4 of the textbook and use tilde notation to simplify your answer.
 *  Briefly justify your answers and show your work.
 *
 *  Do not include the memory for the items themselves (as this
 *  memory is allocated by the client and depends on the item type)
 *  or for any iterators, but do include the memory for the references
 *  to the items (in the underlying array or linked list).
 **************************************************************************** */

Randomized Queue:   ~  8n  bytes
Item Reference (8 bytes) * n = 8n

Deque:              ~  48n  bytes

(Class Overhead (16 bytes) 
+ Item, Next, & Prev Node Reference (3 * 8 bytes) 
+ Nested Class Reference (8 bytes)) * n = 48n




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
For the extra credit problem, I first took a mathematical approach towards 
deriving  the algorithm. It was difficult, as the probably for each case
depended on k and n, where n was unknown. I realized a few key things after
some casework and drawing probability trees: 
1) I only had to worry about getting each combination uniformly, as the 
random queue will generate the rest of the permutations
2) For each local case, I only had to worry about choosing the valid
combinations at that moment in time uniformly, as it'll make the global
probability of all combinations uniformly random as well.

I found that at each local level, the probability of adding in the new item is 
k / n. From there I used StdRandom.uniform() to generate that probability. 


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
