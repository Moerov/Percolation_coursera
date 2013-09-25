/****************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:  java PercolationStats N T
 *  Name: Andrey Moerov
 *  Date: 25 September
 *  This program takes two command-line arguments N and T,
 *  performs T independent computational experiments on a N-by-N grid,
 *  and prints out the mean, standard deviation, and the 95% confidence
 *  interval for the percolation threshold.
 *
 ****************************************************************************/

public class PercolationStats
{
    private int numberOfExperiments;  // number of independent
                                      // computational experiments
    private int size;                 // grid size
    private double[] threshold;       // an array of thresholds
    private double mean;              // mean
    private double stddev;            // standard deviation


    // perform numberOfExperiments independent computational
    // experiments on an size-by-size grid
    public PercolationStats(int N, int T)
    {
        check(N, T);
        if (N > 1) {
           numberOfExperiments = T;
           size = N;
           threshold = new double[numberOfExperiments];
           mean = 0;
           stddev = 0;
           this.simulation();
           } else {
              System.out.println("Case of N = 1. "
                     + "System percolates iff there is one open site.");

        }
    }

    //private method for performing experiments and calculating thresholds
    private void simulation() {
        for (int i = 0; i < numberOfExperiments; i++) {
            Percolation grid = new Percolation(size);
            int counter = 0;
            while (!grid.percolates()) {
                int j = (int) (Math.floor(Math.random()* size + 1));
                int k = (int) (Math.floor(Math.random()* size + 1));
                if (!grid.isOpen(j, k)) {
                   grid.open(j, k);
                   counter++;
                   }
            }
            threshold[i] = ((double) counter) / ((double) size * size);

        }
    }

    //is size and number of Experiments a valid number? Otherwise throw an exception.
    private void check(int N, int T)
    {
        if (N <= 0 || T <= 0)
        { throw new IndexOutOfBoundsException("Row index i out of bounds"); }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        for (int i = 0; i < numberOfExperiments; i++) {
            mean += (threshold[i]/ numberOfExperiments);
        }
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        for (int i = 0; i < numberOfExperiments; i++) {
            stddev += ((threshold[i] - mean)*(threshold[i] - mean)
                    /(numberOfExperiments -1));
        }
        return Math.sqrt(stddev);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo()
    {
        return (mean - (1.96*Math.sqrt(stddev))/Math.sqrt(numberOfExperiments));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi()
    {
        return (mean + (1.96*Math.sqrt(stddev))/Math.sqrt(numberOfExperiments));
    }

    //Main method takes two arguments and creates an instance of PeroclationStats
    public static void main(String[] args) {

        Stopwatch stopwatch = new Stopwatch();
        System.out.println("Grid size: " + args[0]);
        System.out.println("Number of independent simulations: " + args[1]);
        PercolationStats test =
                new PercolationStats(Integer.parseInt(args[0]),
                        Integer.parseInt(args[1]));
        System.out.println("mean = " + test.mean());
        System.out.println("stddev = " + test.stddev());
        System.out.println("95% confidence interval: "
                + test.confidenceLo() + ", " + test.confidenceHi());
        System.out.println("Runtime: " + stopwatch.elapsedTime());
    }
}
