Programming Assignment 5: K-d Trees

/* *****************************************************************************
 *  First, fill out the mid-semester survey:
 *  https://forms.gle/LdhX4bGvaBYYYXs97
 *
 *  If you're working with a partner, please do this separately.
 *
 *  Type your initials below to confirm that you've completed the survey.
 **************************************************************************** */
DG, EL


/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **************************************************************************** */
Node data type contains the Point2D key, the value it holds, the bounding 
rectangle region, and pointers to the left and right regions.


/* *****************************************************************************
 *  Describe your method for range search in a k-d tree.
 **************************************************************************** */
Check if the bounding rectangle of a branch intersects the query rectangle,
if so, continue searching down that branch. If not, then that branch can be 
pruned. With each point visited, if it is within the query rectangle, add 
it to a queue to be returned. 


/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a k-d tree.
 **************************************************************************** */
At each iteration, update the champion point by taking the point which
has a smaller distance to the query point between the current point and the
old champion. To determine which branch to traverse down first, compare the 
distance from the left branch's bounding rectangle to the query point and 
the right branch's bounding rectangle to the query point. Whichever distance 
is less, traverse that side first. To prune a branch, the distance from its 
bounding rectangle has to be greater than the champ distance. 


/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, rounding each value to use one digit after
 *  the decimal point. Use at least 1 second of CPU time. Do not use -Xint.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */


                 # calls to         /   CPU time     =   # calls to nearest()
                 client nearest()       (seconds)        per second
                ------------------------------------------------------
PointST:        100 / 4.9 = 20.5

KdTreeST:       1000000 / 2.2 = 442188.7

Note: more calls per second indicates better performance.


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
- Range search: We initially didn't use the intersects function, we were 
trying to compare min/max coordinates, which wasn't exhaustive enough
- Incorrect traversal of the tree for nearest: we didn't satisfy the condition
to go to the branch that is closer to the query point first. 
    - to make sure our traversal was correct, we created our own test files 
    using the tiger file tests and compared our code's results
- Placing nodes with equal y values: we swapped which side of the tree 
we should've placed the node on. 

/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
We followed the partner protocol as described on the assignment page.
Derek: optimized nearest, created tiger file test cases, ran runtime tests 
Emily: brainstormed methods for range search & nearest, created unit tests

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on  how helpful the class meeting was and on how much you learned 
 * from doing the assignment, and whether you enjoyed doing it.
 **************************************************************************** */
