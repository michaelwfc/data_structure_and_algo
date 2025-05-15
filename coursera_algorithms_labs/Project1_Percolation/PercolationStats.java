
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        validate(n, trials);
        this.thresholds = new double[trials];
        for (int i = 0; i < trials; i++){
            // Monte Carlo simulation. To estimate the percolation threshold, consider the following computational experiment:
            Percolation perc = new Percolation(n);
            while (!perc.percolates()){
                perc.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
            }
            thresholds[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    private void validate(int n, int trials){
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0");
        if (trials <= 0) throw new IllegalArgumentException("trials must be greater than 0");
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(thresholds);

    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return  mean() - 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return  mean() + 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    // test client (see below)
    /*
     * Also, include a main() method that takes two command-line arguments n and T, performs T independent computational experiments (discussed above) on an n-by-n grid,
     * and prints the sample mean, sample standard deviation, and the 95% confidence interval for the percolation threshold.
     * Use StdRandom to generate random numbers;
     * use StdStats to compute the sample mean and sample standard deviation.
     */
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %f\n", ps.mean());
        StdOut.printf("stddev                  = %f\n", ps.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", ps.confidenceLo(), ps.confidenceHi());
    }

}