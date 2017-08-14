//import static org.junit.Assert.*;
//import org.junit.Test;

public class Percolation {


    private int[][] sites;
    private boolean[][] status;
    private int[][] weight;
    private int nOpen;
    private final int size;

    //private int frontnode;
    //private int backnode;

    public Percolation(int n)   {
        if(n <= 0) {
            throw new IllegalArgumentException("EROOR!! n <= 0, please use a valid n (n>0) to construct sites");
        }
        size = n;
        nOpen = 0;
        sites = new int[n][n];
        weight = new int[n][n];
        status = new boolean[n][n];
        //frontnode = 0;
        //backnode = n * n - 1;
        for(int i = 0; i < n; i ++ ) {
            for(int j = 0; j < n; j ++) {
                status[i][j] = false;
                weight[i][j] = 1;
                if(i == 0) {
                    sites[i][j] = 0;
                } else if(i == n-1) {
                    sites[i][j] = n * n - 1;
                } else {
                    sites[i][j] = i * n + j;
                }
            }
        }
        weight[0][0] = n;
        weight[n-1][n-1] = n;

    }             // create n-by-n grid, with all sites blocked

    private int index(int x) {
        return x - 1;
    }

    private int siteID(int i, int j) {
        return i * size + j;
    }
    private int root(int i, int j) {

        //if(sites[i][j] == 0) return 0;
        //if(sites[i][j] == size * size - 1) return  size * size - 1;
        if(sites[i][j] == siteID(i,j)) return siteID(i,j);

        int rooti =  getirow(sites[i][j]);
        int rootj = geticol(sites[i][j]);
        //sites[i][j] = sites[rooti][rootj];
        return root(rooti, rootj);

    }

    private int getirow(int id) {
        return id / size;
    }
    private int geticol(int id) {
        return id % size;
    }

    private boolean isConnected(int pi, int pj, int qi, int qj) {
        return root(pi,pj) == root(qi, qj);
    }

    private void union(int pi, int pj, int qi, int qj) {
        if(isOpen(pi, pj) && isOpen(qi, qj) && !(isConnected(index(pi), index(pj), index(qi), index(qj)))) {
            int rootpid = root(index(pi), index(pj));
            int rootqid = root(index(qi),index(qj));


            int rootpi = getirow(rootpid);
            int rootpj = geticol(rootpid);
            int rootqi = getirow(rootqid);
            int rootqj = geticol(rootqid);
            if(weight[rootpi][rootpj] < weight[rootqi][rootqj]) {
                sites[rootpi][rootpj] = rootqid;
                weight[rootqi][rootqj] += weight[rootpi][rootpj];
            } else {
                sites[rootqi][rootqj] = rootpid;
                weight[rootpi][rootpj] += weight[rootqi][rootqj];
            }
        }
    }

    private boolean checkIndex(int row, int col) {
        if( (row > 0) && (row <= size) && (col > 0 ) && (col <= size) ) return true;
        else {
            throw new IllegalArgumentException("Index out of range, please check 1<=row<=n,  1<=col<=n");
        }

    }
    public void open(int row, int col)    {
        //checkIndex(row, col);
        if(isOpen(row, col)) return;
        nOpen ++;
        status[index(row)][index(col)] = true;
        if(row != 1) union(row-1, col, row, col);
        if(col != 1) union(row, col-1, row, col);
        if(col != size) union(row, col+1, row, col);
        if(row != size) union(row+1, col, row, col);

    }// open site (row, col) if it is not open already

    public boolean isOpen(int row, int col)  {
        checkIndex(row,col);
        return status[index(row)][index(col)] == true;

    }// is site (row, col) open?
    public boolean isFull(int row, int col)  {
        checkIndex(row, col);
        return sites[index(row)][index(col)] == sites[0][0];

    }// is site (row, col) full?
    public     int numberOfOpenSites()       {
        return nOpen;

    }// number of open sites
    public boolean percolates()              {
        for(int i = 0; i < size; i ++) {
            if(isFull(size, i+1)) return true;
        }
        return false;
    }// does the system percolate?

    //@Test
    public static void main(String[] args)  {
        Percolation p1 = new Percolation(3);
        //assertFalse(p1.percolates());
        p1.open(1,1);
        p1.open(3,2);
        p1.open(2,1);
        p1.open(2,2);

        //assertTrue(p1.percolates());

    } // test client (optional)
}
