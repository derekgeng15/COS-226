import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int NUM_OF_DIR = 4; // number of directions

    // each node represents one site in grid
    // node 0 will be the "master node". All sites in the same set as the
    // same set as the master node is considered full
    // node n * n + 1 will be the "final node," and sites in the
    // last layer are connected to the final node when they become full
    // if the final is connected with the master node, the system percolates
    private WeightedQuickUnionUF mUF; // "Master Union Find"

    // fUF will be used to track whether current sets connect to final row
    // sites. If they do, then in the main UF, connect to final node. This
    // method avoids back washing, as it does not attach final sites that are
    // not full
    private WeightedQuickUnionUF fUF; // "Final Union Find"
    private int n; // number of rows/columns
    private boolean[][] open; // true if open, false if blocked
    private int nOpen; // number of open sites


    // Initializes grid to be fully blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("invalid value for n");
        // add extra nodes for the master and final node
        mUF = new WeightedQuickUnionUF(n * n + 2);
        // add extra node for final layer node
        fUF = new WeightedQuickUnionUF(n * n + 1);
        this.n = n;
        open = new boolean[n][n];
        nOpen = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                open[i][j] = false;

        // add all final row sites to final node in fUF
        for (int i = 0; i < n; i++)
            fUF.union((n - 1) * n + i, n * n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // error check for valid rows + cols
        if (row < 0 || row >= n)
            throw new IllegalArgumentException("Row out of range");
        if (col < 0 || col >= n)
            throw new IllegalArgumentException("Column out of range");

        if (open[row][col]) // return if site is already open
            return;
        nOpen++; // increment # of open sites
        open[row][col] = true;
        // if 1st row, union with master node
        if (row == 0)
            mUF.union(0, col + 1);

        // check each direction (up, down, left, right)
        int[] dx = { 1, 0, -1, 0 }, dy = { 0, 1, 0, -1 };
        for (int d = 0; d < NUM_OF_DIR; d++) {
            int x = row + dx[d], y = col + dy[d];
            // union cells if valid site and open
            if (x >= 0 && x < n && y >= 0 && y < n && open[x][y]) {
                mUF.union(row * n + col + 1, x * n + y + 1);
                fUF.union(row * n + col, x * n + y); // same union on fUF
            }
        }

        // if current set is full and is connected to a node in the final row,
        // union with final node to percolate
        if (isFull(row, col) && fUF.find(row * n + col) == fUF.find(n * n))
        // when union with final node, can be any node in the same set
            mUF.union(row * n + col + 1, n * n + 1);

    }

    // returns if site (row, col) is open
    public boolean isOpen(int row, int col) {
        // error check for valid rows + cols
        if (row < 0 || row >= n)
            throw new IllegalArgumentException("Row out of range");
        if (col < 0 || col >= n)
            throw new IllegalArgumentException("Column out of range");
        return open[row][col];
    }

    // returns if site (row, col) is open
    public boolean isFull(int row, int col) {
        // error check for valid rows + cols
        if (row < 0 || row >= n)
            throw new IllegalArgumentException("Row out of range");
        if (col < 0 || col >= n)
            throw new IllegalArgumentException("Column out of range");
        if (!open[row][col]) // if not open, cannot be full
            return false;

        // full if the current node is in the same set as the master node
        return mUF.find(row * n + col + 1) == mUF.find(0);


    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // returns if the system percolates
    public boolean percolates() {
        // percolates if the master and final node are in the same set
        return mUF.find(0) == mUF.find(n * n + 1);
    }

    // outputs grid to String, F = full, O = open, X = closed
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isFull(i, j))
                    out.append("F");
                else if (isOpen(i, j))
                    out.append("O");
                else
                    out.append("X");
            }
            out.append("\n");
        }
        return out.toString();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        percolation.open(3, 3);
        percolation.open(2, 2);
        percolation.open(1, 1);
        percolation.open(0, 0);
        percolation.open(3, 0);

        // Top left should be full, followed by open diagonals
        StdOut.print(percolation);

        StdOut.println("Percolates: " + percolation.percolates()); // false
        StdOut.println("Num of open: " + percolation.numberOfOpenSites()); // 5
        StdOut.println("isOpen(3, 3): " + percolation.isOpen(3, 3)); // true
        StdOut.println("isFull(3, 3): " + percolation.isFull(3, 3)); // false
        StdOut.println("isOpen(3, 0): " + percolation.isOpen(3, 0)); // true
        StdOut.println("isFull(3, 0): " + percolation.isFull(3, 0)); // false

        percolation.open(1, 0);
        percolation.open(2, 1);
        percolation.open(3, 2);
        percolation.open(1, 3);

        // All full, follow snake pattern down with (3, 0) open, but not full
        StdOut.print(percolation);
        StdOut.println("Percolates: " + percolation.percolates()); // true
        StdOut.println("Num of open: " + percolation.numberOfOpenSites()); // 9
        StdOut.println("isOpen(3, 3): " + percolation.isOpen(3, 3)); // true
        StdOut.println("isFull(3, 3): " + percolation.isFull(3, 3)); // true
        StdOut.println("isOpen(3, 0): " + percolation.isOpen(3, 0)); // true
        StdOut.println("isFull(3, 0): " + percolation.isFull(3, 0)); // false
    }
}
