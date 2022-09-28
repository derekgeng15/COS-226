import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96; // z* for 95 % CI
    private int trials; // number of trials
    private double mean; // mean of trials
    private double stddev; // standard deviation of trials

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0)
            throw new IllegalArgumentException("invalid value for n");
        if (trials <= 0)
            throw new IllegalArgumentException("invalid value for trials");
        this.trials = trials;
        // percolation thresholds for each trial
        double[] pThresh = new double[trials];
        // create an array with all cell IDs to randomize [0, n * n)
        int[] randCell = new int[n * n];
        for (int i = 0; i < n * n; i++)
            randCell[i] = i;

        for (int t = 0; t < trials; t++) { // simulate trials
            Percolation p = new Percolation(n); // percolation of size nxn
            StdRandom.shuffle(randCell); // shuffle cell order
            for (int i = 0; i < randCell.length && !p.percolates(); i++) {
                // randomly select a cell to open
                p.open(randCell[i] / n, randCell[i] % n);
            }
            // save this trial's threshold
            pThresh[t] = (double) p.numberOfOpenSites() / (n * n);
        }
        // compute mean and standard deviation
        mean = StdStats.mean(pThresh);
        stddev = StdStats.stddev(pThresh);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }


    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean - CONFIDENCE_95 * stddev / Math.sqrt(trials);
    }


    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean + CONFIDENCE_95 * stddev / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]), trials = Integer.parseInt(args[1]);
        Stopwatch sw = new Stopwatch();
        PercolationStats ps = new PercolationStats(n, trials);
        double endTime = sw.elapsedTime();
        StdOut.printf("Mean() = %f\n", ps.mean());
        StdOut.printf("stddev() = %f\n", ps.stddev());
        StdOut.printf("confidenceLow() = %f\n", ps.confidenceLow());
        StdOut.printf("confidenceHigh() = %f\n", ps.confidenceHigh());
        StdOut.printf("elapsed time = %f\n", endTime);
    }
}
