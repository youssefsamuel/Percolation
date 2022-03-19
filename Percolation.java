import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int rows;
    private int numOpenSites;
    private boolean[] id;
    private final int top;
    private final int bottom;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufBackWash;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufBackWash = new WeightedQuickUnionUF(n * n + 2);
        id = new boolean[n * n + 2];
        rows = n;
        top = n * n;
        bottom = n * n + 1;
        id[top] = true;
        id[bottom] = true;
        numOpenSites = 0;
    }

    private boolean isLegalArgument(int row, int col) {
        if (row < 1 || row > rows || col < 1 || col > rows) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    private int getIndex(int row, int col) {
        return ((row - 1) * rows) + (col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        isLegalArgument(row, col);
        int index = getIndex(row, col);
        if (!isOpen(row, col)) {
            id[index] = true;
            numOpenSites++;
            if (row == 1 && uf.find(index) != uf.find(top)) {
                uf.union(index, top);
                ufBackWash.union(index, top);
            }
            if (row == rows) {
                uf.union(index, bottom);
            }
            if (row != 1) {
                if (isOpen(row - 1, col)) {
                    uf.union(index, getIndex(row - 1, col));
                    ufBackWash.union(index, getIndex(row - 1, col));
                }
            }
            if (col != rows) {
                if (isOpen(row, col + 1)) {
                    uf.union(index, getIndex(row, col + 1));
                    ufBackWash.union(index, getIndex(row, col + 1));
                }
            }
            if (row != rows) {
                if (isOpen(row + 1, col)) {
                    uf.union(index, getIndex(row + 1, col));
                    ufBackWash.union(index, getIndex(row + 1, col));
                }
            }
            if (col != 1) {
                if (isOpen(row, col - 1)) {
                    uf.union(index, getIndex(row, col - 1));
                    ufBackWash.union(index, getIndex(row, col - 1));
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isLegalArgument(row, col);
        if (!id[getIndex(row, col)])
            return false;
        return true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        isLegalArgument(row, col);
        if (!isOpen(row, col))
            return false;
        if (ufBackWash.find(top) == ufBackWash.find(getIndex(row, col)))
            return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(top) == uf.find(bottom))
            return true;
        return false;
    }
}
