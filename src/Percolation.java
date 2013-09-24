/****************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:  java Percolation
 *
 *
 *  Percolation.
 *
 ****************************************************************************/

public class Percolation
{
    private int N;                               //grid size
    private int[][] grid;                          //grid of open and full cells
    private WeightedQuickUnionUF unit;           //set of objects

    // create N-by-N grid, with all sites blocked
    public Percolation(int size)
    {
        N = size;
        grid = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                grid[i][j] = 0;

        unit = new WeightedQuickUnionUF(N*N + 2);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        check(i);
        check(j);
        if (grid[i-1][j-1] == 0) {
        grid[i-1][j-1] = 1;
        if (j != N) { if (isOpen(i, j+1))
             { unit.union(intxyTo1D(i, j), intxyTo1D(i, j) + 1); } }
        if (j != 1) { if (isOpen(i, j-1))
             { unit.union(intxyTo1D(i, j), intxyTo1D(i, j) - 1); } } //left
        if (i != N)
             { if (isOpen(i+1, j))
             { unit.union(intxyTo1D(i, j), intxyTo1D(i, j) + N); } } //up
        if (i != 1)
             { if (isOpen(i-1, j))
             { unit.union(intxyTo1D(i, j), intxyTo1D(i, j) - N); } } //down
        if (i == 1) { unit.union(intxyTo1D(i, j), 0); }
        if (i == N) { unit.union(intxyTo1D(i, j), N*N + 1); }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
       return grid[i-1][j-1] == 1;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        return (unit.connected(intxyTo1D(i, j), 0)
                || unit.connected(intxyTo1D(i, j), N*N + 1));
    }

    // does the system percolate?
    public boolean percolates()
    {
        return unit.connected(0, N*N + 1);
    }

    private void check(int i)
    {
        if (i <= 0 || i > N)
        { throw new IndexOutOfBoundsException("Row index i out of bounds"); }
    }

    private int intxyTo1D(int i, int j) {
        check(i);
        check(j);
        return ((i - 1)*N + j);

    }

    //Temp method!!
    private void showGrid() {
        for (int i = 0; i < N; i++)
        { for (int j = 0; j < N; j++)
            { System.out.print(grid[i][j] + " "); }
        System.out.println(); }

    }
    /* Temp method!! */

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();

        Percolation test = new Percolation(5);
        test.showGrid();
        System.out.println();
        System.out.println(test.percolates());
        test.open(1, 3);
        test.open(2, 3);
        test.open(3, 3);
        test.open(4, 3);
        test.open(5, 3);
        System.out.println(test.isFull(2, 4));
        System.out.println("/////////////////");




        System.out.println("Runtime: " + stopwatch.elapsedTime());
    }
}
