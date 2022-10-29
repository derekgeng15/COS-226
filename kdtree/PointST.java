import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class PointST<Value> {

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
        symTable.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        return symTable.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        return symTable.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return symTable.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        return symTable.keys(new Point2D(rect.xmin(), rect.ymin()), new Point2D(rect.xmax(), rect.ymax()));
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        Point2D champ = symTable.select(0);
        for (Point2D q : points())
            if (q.distanceSquaredTo(p) < champ.distanceSquaredTo(p))
                champ = q;
        return champ;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<String> pointST = new PointST<>();
        pointST.put(new Point2D(8, 8), "A"); // A
        pointST.put(new Point2D(10, 6), "B"); // B
        pointST.put(new Point2D(5, 11), "C"); // C
        pointST.put(new Point2D(3, 2), "D"); // D
        pointST.put(new Point2D(2, 9), "E"); // E
        pointST.put(new Point2D(7, 12), "F"); // F
        pointST.put(new Point2D(8, 4), "G"); // G

        StdOut.println("Level Order Traversal: ");
        for (Point2D p : pointST.points()){
            StdOut.print(pointST.get(p) + ": " + p + ", ");
        }

        StdOut.println("Points in Rectangle [4,2] x [11, 13]: ");
        for (Point2D p : pointST.range(new RectHV(4, 2, 11, 13)))
            StdOut.print(pointST.get(p) + ": "+ p + ", ");
        StdOut.println();
         // no points in rectangle
        StdOut.println("range [1,2] x [2, 4]: ");
        for (Point2D p : pointST.range(new RectHV(1, 2, 2, 4)))
            StdOut.print(pointST.get(p) + ": " + p + ", ");
        
        StdOut.println();
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
    }

}
