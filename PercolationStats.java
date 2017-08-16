import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    //private Percolation perc;
    private final double[] results;
    private final int ntrials;
    private final double mean;
    private final double stddev;

    public PercolationStats(int n, int trials) {
        if (trials < 1) throw new IllegalArgumentException("Invalid trial number. Check!!!");
        ntrials = trials;
        results = new double[ntrials];

        for (int i = 0; i < trials; i++) {
            results[i] = singleExperiemnt(n);
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);

    }    // perform trials independent experiments on an n-by-n grid

    public double mean()  {

        return mean;
    } // sample mean of percolation threshold

    public double stddev()   {
        return stddev;
    }                      // sample standard deviation of percolation threshold
    public double confidenceLo()  {
        return mean - 1.96 * stddev / Math.sqrt(ntrials);
    }                 // low  endpoint of 95% confidence interval
    public double confidenceHi()     {
        return mean + 1.96 * stddev / Math.sqrt(ntrials);
    }              // high endpoint of 95% confidence interval

    private double singleExperiemnt(int n) {

        Percolation perc = new Percolation(n);
        int row, col;
        while (!perc.percolates()) {
            row = StdRandom.uniform(n) + 1;
            col = StdRandom.uniform(n) + 1;
            perc.open(row, col);
        }

        int nopen = perc.numberOfOpenSites();
        return (double) nopen / (n * n);

    }


    public static void main(String[] args)     {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats exp = new PercolationStats(n, trials);

        System.out.println("Mean:                      " + exp.mean());
        System.out.println("Stddev:                    " + exp.stddev());
        System.out.println("95% confidence interval:  [" + exp.confidenceLo() + ", " + exp.confidenceHi()+"]");
    }    // test client (described b


}
