import edu.princeton.cs.algs4.RedBlackBST;

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
        Point2D ceiling = symTable.ceiling(p);
        Point2D floor = symTable.floor(p);
        if (ceiling.distanceSquaredTo(p) >= floor.distanceSquaredTo(p))
            return ceiling;
        return floor;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
