Programming Assignment 3: Autocomplete


/* *****************************************************************************
 *  Describe how your firstIndexOf() method in BinarySearchDeluxe.java
 *  finds the first index of a key that is equal to the search key.
 **************************************************************************** */
Our firstIndexOf() method works by setting the index of the high pointer 
equal the index of the middle pointer if compare returns 0 (key has been found). 
We do this so we can remember this key value and then search the lower half of 
the array for any more occurances of the key. We continue doing this until lo = hi,
and the value that it stops on is the first occurance of the key. We check that this
is the case by checking if the value at that index is equal to the key; if so, we
return lo and if not we return -1 (key wasn't found in the array).

/* *****************************************************************************
 *  Identify which sorting algorithm (if any) that your program uses in the
 *  Autocomplete constructor and instance methods. Choose from the following
 *  options:
 *
 *    none, selection sort, insertion sort, mergesort, quicksort, heapsort
 *
 *  If you are using an optimized implementation, such as Arrays.sort(),
 *  select the principal algorithm.
 **************************************************************************** */

Autocomplete() : mergesort 

allMatches() : mergesort 

numberOfMatches() : none

/* *****************************************************************************
 *  How many compares (in the worst case) does each of the operations in the
 *  Autocomplete data type make, as a function of both the number of terms n
 *  and the number of matching terms m? Use Big Theta notation to simplify
 *  your answers.
 *
 *  Recall that with Big Theta notation, you should discard both the
 *  leading coefficients and lower-order terms, e.g., Theta(m^2 + m log n).
 **************************************************************************** */

Autocomplete():     Theta(n*lg(n))

allMatches():       Theta(m*lg(m))

numberOfMatches():  Theta(lg(n))




/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
The default method name and arguments of firstIndexOf and lastIndexOf exceed 
the character limit of a line, causing a checkstyle warning

/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *
 *  Also include any resources (including the web) that you may
 *  may have used in creating your design.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe any serious problems you encountered.                    
 **************************************************************************** */
1. How to account for terms that were shorter than the prefix in the prefix
comparator 
    - We solved this by using Math.min(prefix length, term length) in the 
      substring function so that we wouldn't get an out of bounds error.
2. How to read the query strings from the file
    - At first, we were including the whitespace from the text files in our
      query variables, which messed up our prefix comparator (because we
      compared via substring starting at index 0). We fixed this by first reading
      the tab and then storing the rest of the line in the query var.

/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
Worked with Emily Luo. We followed the collaboration protocol as described
on the assignment page and worked on all aspects of the project together.

Together: discussed code for the binary search and comparators, came up with
unit tests
Derek Geng: fixed the problem with reading inputs
Emily Luo: fixed the prefix comparator

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **************************************************************************** */

