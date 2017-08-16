// import static org.junit.Assert.*;
// import org.junit.Test;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF wqu;

    private int nOpen;
    private final int size;
    private boolean[][] status;


    public Percolation(int n)   {
        if (n <= 0) {
            throw new IllegalArgumentException("EROOR!! n <= 0, please use a valid n (n>0) to construct sites");
        }
        size = n;
        nOpen = 0;
        wqu = new WeightedQuickUnionUF(n * n);
        status = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            wqu.union(0, i);
            wqu.union(n * n - 1 - i, n * n - 1);
            for (int j = 0; j < n; j++) {
                status[i][j] = false;
            }

        }

    }             // create n-by-n grid, with all sites blocked


    public void open(int row, int col)    {

        if (isOpen(row, col)) return;
        nOpen++;
        status[index(row)][index(col)] = true;

        if (row != 1 && isOpen(row - 1, col)) wqu.union(convertID(row-1, col), convertID(row, col));
        if (col != 1 && isOpen(row, col - 1)) wqu.union(convertID(row, col-1), convertID(row, col));
        if (col != size && isOpen(row, col + 1)) wqu.union(convertID(row, col+1), convertID(row, col));
        if (row != size && isOpen(row + 1, col)) wqu.union(convertID(row+1, col), convertID(row, col));

    } // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col)  {
        checkIndex(row, col);
        return status[index(row)][index(col)];

    } // is site (row, col) open?


    public boolean isFull(int row, int col)  {
        checkIndex(row, col);
        return (isOpen(row, col) && wqu.connected(convertID(row, col), 0));

    } // is site (row, col) full?
    public int numberOfOpenSites()  {
        return nOpen;

    } // number of open sites
    public boolean percolates()              {
        return isFull(size, size);
    } // does the system percolate?


    private int index(int x) {
        return x - 1;
    }

    private boolean checkIndex(int row, int col) {
        if ((row > 0) && (row <= size) && (col > 0) && (col <= size)) return true;
        else {
            throw new IllegalArgumentException("Index out of range, please check 1<=row<=n,  1<=col<=n");
        }
    }

    private int convertID(int row, int col) {
        return (row - 1) * size + col - 1;
    }



    // @Test
    public static void main(String[] args)  {

        Percolation p1 = new Percolation(3);
        // assertFalse(p1.percolates());
        p1.open(1, 3);
        // assertTrue(p1.isFull(1,1));
        // assertTrue(p1.isOpen(1,1));
        p1.open(2,3);
        p1.open(3,3);
        p1.open(3,1);
        System.out.print(p1.isFull(3,1));

        // assertTrue(p1.percolates());

    } //  test client (optional)
}
