/****************************************************************************
 *
 *  Something!!!!!
 *
 *
 *  Percolation.
 *
 ****************************************************************************/

public class PercolationStats
{
    private int T;
    private int N;
    private double[] threshold;
    private double mean;
    private double stddev;


    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        this.N = N;
        //grid = new Percolation(N);
        this.T = T;
        threshold = new double[T];
        mean = 0;
        stddev = 0;
        this.simulation();
    }

    private void simulation() {
        for (int i = 0; i < T; i++) {
            Percolation grid = new Percolation(N);
            int counter = 0;
            while (!grid.percolates()) {
                int j = (int) (Math.floor(Math.random()*N + 1));
                int k = (int) (Math.floor(Math.random()*N + 1));
                if (grid.isFull(j, k)) {
                   grid.open(j, k);
                   counter++;
                   //System.out.println("counter++");
                }
            }
            threshold[i] = ((double) counter) / ((double) N*N);

        }
    }

    // sample mean of percolation threshold
    public double mean()
    {

        for (int i = 0; i < T; i++) {
            mean += (threshold[i]/T);
        }
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        for (int i = 0; i < T; i++) {
            stddev += ((threshold[i] - mean)*(threshold[i] - mean)/(T-1));
        }

        return Math.sqrt(stddev);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo()
    {
        return (mean - (1.96*Math.sqrt(stddev))/Math.sqrt(T));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi()
    {
        return (mean + (1.96*Math.sqrt(stddev))/Math.sqrt(T));
    }

    public static void main(String[] args) {

//        if (args.length != 2)
//            System.out.println("Arguments, please");
//        System.out.println("Grid size: " + args[0]);
//        System.out.println("Number of independent simulations: " + args[1]);
//
//
//        PercolationStats test =
//                new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        PercolationStats test = new PercolationStats(200, 100);
        System.out.println("mean = " + test.mean());
        System.out.println("stddev = " + test.stddev());
        System.out.println("95% confidence interval: "
                + test.confidenceLo() + ", " + test.confidenceHi());

        for (int i = 0; i < test.T; i++){
            System.out.println(test.threshold[i]);
                }



    }
}
