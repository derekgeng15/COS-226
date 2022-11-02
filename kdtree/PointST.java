import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class PointST<Value> {
        
    // RedBlackBST to store point - value pairs
    private RedBlackBST<Point2D, Value> symTable;

    // construct an empty symbol table of points
    public PointST() {
        symTable = new RedBlackBST<>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return symTable.isEmpty();
    }

    // number of points
    public int size() {
        return symTable.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null)
            throw new IllegalArgumentException("p cannot be null");
        if (val == null)
            throw new IllegalArgumentException("value cannot be null");
        symTable.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("p cannot be null");
        return symTable.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("p cannot be null");
        return symTable.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return symTable.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("rect cannot be null");
        Queue<Point2D> out = new Queue<>();
        for (Point2D p : symTable.keys())
            // add point if within rectangle
            if (rect.contains(p))
                out.enqueue(p);
        return out;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;
        if (p == null)
            throw new IllegalArgumentException("p cannot be null");
        Point2D champ = symTable.min();
        for (Point2D q : points())
            if (q.distanceSquaredTo(p) < champ.distanceSquaredTo(p))
                champ = q;
        return champ;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<String> pointST = new PointST<>();
        StdOut.print("Empty: ");
        StdOut.println(pointST.isEmpty()); // true
        pointST.put(new Point2D(8, 8), "A"); // A
        pointST.put(new Point2D(10, 6), "B"); // B
        pointST.put(new Point2D(5, 11), "C"); // C
        pointST.put(new Point2D(3, 2), "D"); // D
        pointST.put(new Point2D(2, 9), "E"); // E
        pointST.put(new Point2D(7, 12), "F"); // F
        pointST.put(new Point2D(8, 4), "G"); // G
        StdOut.print("Empty: ");
        StdOut.println(pointST.isEmpty()); // false
        StdOut.print("Size: ");
        StdOut.println(pointST.size()); // 7
        StdOut.print("Contains Point (8, 8): "); // true
        StdOut.println(pointST.contains(new Point2D(8, 8)));
        StdOut.print("Contains Point (0, 0): "); // false
        StdOut.println(pointST.contains(new Point2D(0, 0)));
        StdOut.println("Level Order Traversal: ");
        for (Point2D p : pointST.points()) {
            StdOut.print(p + " " + pointST.get(p) + ": ");
            StdOut.println(p.distanceSquaredTo(new Point2D(100, 100)));
        }
        // points in rectangle
        StdOut.println("range [4,2] x [11, 13]: ");
        for (Point2D p : pointST.range(new RectHV(4, 2, 11, 13)))
            StdOut.print(p + " ");
        StdOut.println();
        // no points in rectangle
        StdOut.println("range [1,2] x [2, 4]: ");
        for (Point2D p : pointST.range(new RectHV(1, 2, 2, 4)))
            StdOut.print(p + " ");
        StdOut.println();
        // testing closest point
        StdOut.print("Closest point to (0, 0): ");
        StdOut.println(pointST.nearest(new Point2D(0, 0)));
        StdOut.print("Closest point to (1, 12): ");
        StdOut.println(pointST.nearest(new Point2D(1, 12)));
        StdOut.print("Closest point to (8, 5): ");
        StdOut.println(pointST.nearest(new Point2D(8, 5)));
        StdOut.print("Closest point to (8, 11): ");
        StdOut.println(pointST.nearest(new Point2D(8, 11)));
        StdOut.print("Closest point to (100, 100): ");
        StdOut.println(pointST.nearest(new Point2D(100, 100)));

        // nearest timing test
        PointST<Integer> sTTimer = new PointST<>();
        In in = new In("input1M.txt");
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            sTTimer.put(new Point2D(x, y), i);
        }
        int t = 100; // number of trials
        Point2D[] randPoints = new Point2D[t];
        // generate t random points
        for (int i = 0; i < t; i++)
            randPoints[i] = 
                new Point2D(StdRandom.uniformDouble(), StdRandom.uniformDouble());
        StopwatchCPU stopwatchCPU = new StopwatchCPU();
        for (int i = 0; i < t; i++) // run nearest() t times
            sTTimer.nearest(randPoints[i]);
        double time = stopwatchCPU.elapsedTime();
        StdOut.print("Time to do ");
        StdOut.print(t);
        StdOut.print(" nearest calls: ");
        StdOut.println(time);
    }

}
