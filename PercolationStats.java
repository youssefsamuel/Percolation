import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final double[] probability;
    private final int nTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        int i;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        nTrials = trials;
        probability = new double[trials];
        for (i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int r = StdRandom.uniform(n) + 1;
                int c = StdRandom.uniform(n) + 1;
                p.open(r, c);
            }
            probability[i] = p.numberOfOpenSites() / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(probability);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(probability);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - ((CONFIDENCE * this.stddev()) / Math.sqrt(nTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + ((CONFIDENCE * this.stddev()) / Math.sqrt(nTrials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, t);
        System.out.println("mean            = " + p.mean());
        System.out.println("stddev          = " + p.stddev());
        System.out.println("95% confidence interval = " + "[" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }
}
