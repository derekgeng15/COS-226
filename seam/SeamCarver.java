import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StopwatchCPU;

public class SeamCarver {

    private Picture picture; // stores picture
    private int width, height; // dimension variables

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("picture cannot be null");
        this.picture = new Picture(picture); // defensive copy
        this.width = picture.width();
        this.height = picture.height();
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy (col, row)
    public double energy(int x, int y) {
        if (x >= width || y >= height || x < 0 || y < 0) {
            throw new IllegalArgumentException("x or y coord out of bounds");
        }
        return compEnergy(x, y);
    }

    // energy of pixel at column x and row y
    private double compEnergy(int x, int y) {
        // get the RGB values for the adjacent pixels
        int up = picture.getRGB(x, (y + height - 1) % height);
        int down = picture.getRGB(x, (y + 1) % height);
        int left = picture.getRGB((x + width - 1) % width, y);
        int right = picture.getRGB((x + 1) % width, y);
        
        // compute energy
        return Math.sqrt(delta(left, right) + delta(up, down));
    }

    // compute differences in RGB values
    private double delta(int x, int y) {
        return Math.pow(((x >> 16) & 0xFF) - ((y >> 16) & 0xFF), 2)  // delta R values
                + Math.pow(((x >> 8) & 0xFF) - ((y >> 8) & 0xFF), 2) // detla G values
                + Math.pow((x & 0xFF) - (y & 0xFF), 2); // delta B values
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // transpose & compute energy of pixels
        double[][] transpose = new double[height][width]; 
        for (int r = 0; r < height; r++)
            for (int c = 0; c < width; c++)
                transpose[r][c] = compEnergy(c, r);
        return findSeam(transpose, height, width);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        // compute energy of pixels
        double[][] energy = new double[width][height];
        for (int r = 0; r < height; r++)
            for (int c = 0; c < width; c++)
                energy[c][r] = compEnergy(c, r);
        return findSeam(energy, width, height);
    }
    
    // finds the minimum energy seam
    private int[] findSeam(double[][] e, int w, int h) {
        int[][] parent = new int[w][h]; // store col from previous row
        double[][] mem = new double[w][2]; // stores cummulative energy
        
        // stores energies of the first row (base case)
        for (int c = 0; c < w; c++)
            mem[c][0] = e[c][0];

        // determine best path for each subsequent row
        for (int r = 1; r < h; r++) {
            for (int c = 0; c < w; c++) {
                // default set path to pixel above it
                mem[c][1] = mem[c][0];
                parent[c][r] = c; 
                
                // compare top left pixel
                if (c - 1 >= 0 && mem[c - 1][0] < mem[c][1]) {
                    mem[c][1] = mem[c - 1][0];
                    parent[c][r] = c - 1;
                }

                // compare top left pixel
                if (c + 1 < w && mem[c + 1][0] < mem[c][1]) {
                    mem[c][1] = mem[c + 1][0];
                    parent[c][r] = c + 1;
                }
                mem[c][1] += e[c][r]; // add energy of pixel
            }
            // reset memory array
            for (int c = 0; c < w; c++)
                mem[c][0] = mem[c][1];
        }
        // find path with least energy
        int curr = 0;
        for (int c = 0; c < w; c++) {
            if (mem[c][0] < mem[curr][0]) 
                curr = c;
        }
        
        // trace path with least energy
        int[] out = new int[h]; // stores min path
        for (int r = h - 1; r >= 0; r--) {
            out[r] = curr;
            curr = parent[curr][r];
        }
        return out;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("seam cannot be null");
        if (seam.length != width)
            throw new IllegalArgumentException("seam is wrong length");
        for (int s : seam)
            if (s < 0 || s >= height)
                throw new IllegalArgumentException("element in seam is out of bounds");
        for (int i = 1; i < seam.length; i++) 
            if (Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException("seam path is invalid");
        
        // create updated picture, skip pixels in the removed seam
        Picture newPic = new Picture(width, height - 1);
        for (int c = 0; c < width; c++) {
            int curr = 0;
            for (int r = 0; r < height; r++)
                if (r != seam[c])
                    newPic.setRGB(c, curr++, picture.getRGB(c, r));
        }
        height--;
        picture = newPic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("seam cannot be null");
        if (seam.length != height)
            throw new IllegalArgumentException("seam is wrong length");
        for (int s : seam)
            if (s < 0 || s >= width)
                throw new IllegalArgumentException("element in seam is out of bounds");
        for (int i = 1; i < seam.length; i++) 
            if (Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException("seam path is invalid");
        // create updated picture, skip pixels in the removed seam
        Picture newPic = new Picture(width - 1, height);
        for (int r = 0; r < height; r++) {
            int curr = 0;
            for (int c = 0; c < width; c++)
                if (c != seam[r])
                    newPic.setRGB(curr++, r, picture.getRGB(c, r));
        }
        width--;
        picture = newPic;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Picture pic = new Picture("6x5.png");
        SeamCarver sc = new SeamCarver(pic);
        StdOut.println("Height: " + sc.height());
        StdOut.println("Width: " + sc.width());
        // test computing energy
        StdOut.println("Energy of each pixel");
        for (int r = 0; r < sc.height(); r++) {
            for (int c = 0; c < sc.width(); c++)
                StdOut.printf("%.2f\t", sc.energy(c, r));
            StdOut.println();
        }
        // test finding & removing vert seam
        int[] vs = sc.findVerticalSeam();
        // output removed seam
        StdOut.println("Vertical Seam of Least Energy");
        for (int i : vs)
            StdOut.print(i + " ");
        StdOut.println();
        sc.removeVerticalSeam(vs);
        // test finding and removing horizontal seam
        int[] hs = sc.findHorizontalSeam();
        StdOut.println("Horizontal Seam of Least Energy");
        for (int i : hs) 
            StdOut.print(i + " ");
        StdOut.println();
        sc.removeHorizontalSeam(hs);
        // return the new picture
        sc.picture().save("out.png");
        // timing test
        for (String s : args) {
            Picture stadium = new Picture(s);
            SeamCarver test = new SeamCarver(stadium);
            StopwatchCPU stopwatchCPU = new StopwatchCPU();
            test.removeVerticalSeam(test.findVerticalSeam());
            test.removeHorizontalSeam(test.findHorizontalSeam());
            StdOut.println("Time: " + stopwatchCPU.elapsedTime());
        }
    }

}
