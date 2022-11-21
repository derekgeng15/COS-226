Programming Assignment 7: Seam Carving


/* *****************************************************************************
 *  Describe concisely your algorithm to find a horizontal (or vertical)
 *  seam.
 **************************************************************************** */
We use a bottom-up dynamic programming approach. We figure out which path to 
take for a specific pixel based on the energy of the adjacent pixels in the 
row above it.

/* *****************************************************************************
 *  Describe what makes an image suitable to the seam-carving approach
 *  (in terms of preserving the content and structure of the original
 *  image, without introducing visual artifacts). Describe an image that
 *  would not work well.
 **************************************************************************** */
An image suitable to the seam-carving approach is one that has a lot of pixels
of similar color grouped together so those pixels can be removed 
without sacrificing the original image. An image that wouldn't work well is 
one that has a lot of different colored pixels next to each other, 
so removing a seam would change a lot.  

/* *****************************************************************************
 *  Perform computational experiments to estimate the running time to reduce
 *  a W-by-H image by one column and one row (i.e., one call each to
 *  findVerticalSeam(), removeVerticalSeam(), findHorizontalSeam(), and
 *  removeHorizontalSeam()). Use a "doubling" hypothesis, where you
 *  successively increase either W or H by a constant multiplicative
 *  factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one.
 **************************************************************************** */

(keep W constant) we use the stadium pictures, time slowed down with -Xint
 W = 2000
 multiplicative factor (for H) = 2

 H           time (seconds)      ratio       log ratio
------------------------------------------------------
250          4.375              
500          8.703               1.989          0.992
1000         17.410              2.000          1.000
2000         35.187              2.021          1.015
4000         70.101              1.992          0.994
8000         140.642             2.006          1.004


(keep H constant) we use the city pictures, time slowed down with -Xint
 H = 2000
 multiplicative factor (for W) = 2   

 W           time (seconds)      ratio       log ratio
------------------------------------------------------
250          4.467             
500          8.832               1.977        0.983
1000         17.570              1.989        0.992
2000         35.169              2.002        1.001
4000         71.289              2.027        1.019
8000         142.302             1.996        0.997



/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula 
 *  (using tilde notation) for the running time (in seconds) as a function
 *  of both W and H, such as
 *
 *       ~ 5.3*10^-8 * W^5.1 * H^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */
In a google sheet, we plotted the log of the heights vs the log of the
runtime and then had it calculate the linear best-fit line, which had a 
slope of 1.000. We did the same for the widths and found that the slope of 
that best fit line was also 1.000. Multiplying the 1.000W x 1.000H = WH.


Running time (in seconds) to find and remove one horizontal seam and one
vertical seam, as a function of both W and H:


    ~   1.000WH




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
Our main problem was with the memory constraint. Originally we were storing 
energy in a double matrix globally, but we changed it to be a local variable.

/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
We followed the partner protocol as described on the assignment page. 

Derek Geng: optimized the program for memory, fixed energy function bugs.  
Emily Luo: developed DP approach to find seam, worked on energy function.  

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **************************************************************************** */
