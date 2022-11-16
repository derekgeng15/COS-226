import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;
    private IndexMinPQ<Double> pq; 
    private double[] distTo;  
    private int[] pathTo;   

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = picture;
        energy = new double[width()][height()];
        for(int r = 0; r < width(); r++)
            for(int c = 0; c < height(); c++) 
                energy[r][c] = compEnergy(r,c); 
    }
 
    // current picture
    public Picture picture() {
        return picture;
    }
 
    // width of current picture
    public int width() {
        return picture.width();
    }
 
    // height of current picture
    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        return energy[x][y];
    }
 
    // energy of pixel at column x and row y
    private double compEnergy(int x, int y) {
        int up = picture.getRGB(x, (y + picture.height() - 1) % picture.height());
        int down = picture.getRGB(x, (y + 1) % picture.height());
        int left = picture.getRGB((x + picture.width() - 1) % picture.width(), y);
        int right = picture.getRGB((x + 1) % picture.width(), y);
        
        int dx = delta(left, right);
        int dy = delta(up, down);

        return Math.sqrt(Math.pow(dx, 2)+ Math.pow(dy, 2));
    }


    private int delta(int x, int y) {
        return Math.abs(x >> 16 - y >> 16) + Math.abs(x >> 8 - y >> 8) + Math.abs(x >> 0 - y >> 0);
    }
   
 
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int size = 3* (height() - 1) + 1;
        pq = new IndexMinPQ<Double>(size);
        distTo = new double[size];
        pathTo = new int[size];

    }

    private 
 
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        
        
    }
 
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        
    }
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        
    }
 
    //  unit testing (required)
    public static void main(String[] args) {
        
    }
}
 
 