import edu.princeton.cs.algs4.Queue;

public class KDTree<Value> {

    private Node root; // root of KD Tree

    private final double INF = Double.POSITIVE_INFINITY;
    private class Node {
        private Point2D p;
        private Value val;

        private Node left, right;

        private RectHV rect;
        private int size;

        public Node(Point2D p, Value val, int size, RectHV rect) {
            this.p = p;
            this.val = val;
            this.size = size;
            this.rect = rect;
        }
    }

    // construct an empty symbol table of points
    public KDTree() {
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points
    public int size() {
        if (root == null)
            return 0;
        return root.size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null)
            throw new IllegalArgumentException("first argument to put() is null");
        put(root, p, val, true, new RectHV(-INF, -INF, INF, INF));
    }

    private Node put(Node curr, Point2D p, Value v, boolean vert, RectHV rect) {
        if (curr == null) {
            return new Node(p, v, 1, rect);
        }

        if (vert) { // in a vertical layer, compare x values
            if (p.x() >= curr.p.x()) {
                // curr's x coord becomes min bound for x
                rect = new RectHV(curr.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
                curr.right = put(curr.right, p, v, !vert, rect);
            } else {
                // curr's x coord becomes max bound for x
                rect = new RectHV(rect.xmin(), rect.ymin(), curr.p.x(), rect.ymax());
                curr.left = put(curr.left, p, v, !vert, rect);
            }
        } else { // in a horizontal layer, compare y values
            if (p.y() <= curr.p.y()) {
                // curr's y coord becomes max bound for y
                rect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), curr.p.y());
                curr.left = put(curr.left, p, v, !vert, rect);
            } else {
                // curr's y coord becomes min bound for y
                rect = new RectHV(rect.xmin(), curr.p.y(), rect.xmax(), rect.ymax());
                curr.right = put(curr.right, p, v, !vert, rect);
            }
        }
        // return tree w/ placed node
        return curr;
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to get() is null");
        return get(root, p, true);
    }
    private Value get(Node curr, Point2D p, boolean vert) {
        if (curr == null)
            return null;
        if (curr.p.equals(p)) return curr.val;
        if (vert) {
            if (p.x() >= curr.p.x()) 
                return get(curr.right, p, !vert);
            return get(curr.left, p, !vert);  
        }
        else {
            if (p.y() <= curr.p.y()) return get(curr.left, p, !vert);
            return get(curr.right, p, !vert);
        }
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        return get(p) != null;
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        Queue<Point2D> qPoint = new Queue<>();
        Queue<Node> qNode = new Queue<>();
        qNode.enqueue(root);

        while (!qNode.isEmpty()) {
            Node curr = qNode.dequeue();
            qPoint.enqueue(null);
        }

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {

    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
