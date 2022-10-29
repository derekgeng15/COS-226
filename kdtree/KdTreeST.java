import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeST<Value> {

    private Node root; // root of KD Tree

    private final double INF = Double.MAX_VALUE;

    private int size;
    private class Node {
        private Point2D p;
        private Value val;

        private Node left, right;

        private RectHV rect;

        public Node(Point2D p, Value vals, RectHV rect) {
            this.p = p;
            this.val = val;
            this.rect = rect;
        }
    }

    // construct an empty symbol table of points
    public KdTreeST() {
        size = 0;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points
    public int size() {
        return size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null)
            throw new IllegalArgumentException("first argument to put() is null");
        root = put(root, p, val, true, new RectHV(-INF, -INF, INF, INF));
        size++;
    }

    private Node put(Node curr, Point2D p, Value v, boolean vert, RectHV rect) {
        if (curr == null) {
            return new Node(p, v, rect);
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
        if (p == null)
            throw new IllegalArgumentException("argument to get() is null");
        return get(root, p, true);
    }

    private Value get(Node curr, Point2D p, boolean vert) {
        if (curr == null)
            return null;
        if (curr.p.equals(p))
            return curr.val;
        if (vert) {
            if (p.x() >= curr.p.x())
                return get(curr.right, p, !vert);
            return get(curr.left, p, !vert);
        } else {
            if (p.y() <= curr.p.y())
                return get(curr.left, p, !vert);
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
            qPoint.enqueue(curr.p);
            if (curr.left != null)
                qNode.enqueue(curr.left);
            if (curr.right != null)
                qNode.enqueue(curr.right);
        }

        return qPoint;
    }
   // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> qPoints = new Queue<>();
        range(rect, root, true, qPoints);
        return qPoints;
    }

    private void range(RectHV rect, Node curr, boolean vert, Queue<Point2D> qPoints) {
        if (curr == null)
            return;

        if (rect.contains(curr.p)) {
            qPoints.enqueue(curr.p);
            range(rect, curr.left, !vert, qPoints);
            range(rect, curr.right, !vert, qPoints);
        } 
        else if (vert && curr.p.x() > rect.xmax())
            range(rect, curr.left, !vert, qPoints);
        else if (vert && curr.p.x() < rect.xmin())
            range(rect, curr.right, !vert, qPoints);
        else if (!vert && curr.p.y() > rect.ymax())
            range(rect, curr.right, !vert, qPoints);
        else if (!vert && curr.p.y() < rect.ymin())
            range(rect, curr.left, !vert, qPoints);
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (root == null)
            return null;
        return nearest(p, root, root.p);
    }

    private Point2D nearest(Point2D q, Node curr, Point2D champ) {
        if (curr == null)
            return champ;
        
        double champDist = champ.distanceSquaredTo(q);
        if (curr.p.distanceSquaredTo(q) < champDist) {
            champ = curr.p;
            champDist = curr.p.distanceSquaredTo(q);
        }
        // StdOut.println(curr.val + " " + champ + " " + champDist);
        // StdOut.println(curr.left.rect.distanceSquaredTo(q));
        if (curr.left != null && curr.left.rect.distanceSquaredTo(q) < champDist)
            champ = nearest(q, curr.left, champ);
        if (curr.right != null && curr.right.rect.distanceSquaredTo(q) < champDist)
            champ = nearest(q, curr.right, champ);
        return champ;
    }

    // unit testing (required)
    public static void main(String[] args) {
        KdTreeST<String> kdTree = new KdTreeST<>();
        kdTree.put(new Point2D(8, 8), "A"); // A
        kdTree.put(new Point2D(10, 6), "B"); // B
        kdTree.put(new Point2D(5, 11), "C"); // C
        kdTree.put(new Point2D(3, 2), "D"); // D
        kdTree.put(new Point2D(2, 9), "E"); // E
        kdTree.put(new Point2D(7, 12), "F"); // F
        kdTree.put(new Point2D(8, 4), "G"); // G
        
        StdOut.println("Level Order Traversal: ");
        for (Point2D p : kdTree.points()){
            StdOut.print(p + " " + kdTree.get(p) + ": ");
            StdOut.println(p.distanceSquaredTo(new Point2D(100, 100)));
        }
        // points in rectangle
        StdOut.println("range [4,2] x [11, 13]: ");
        for (Point2D p : kdTree.range(new RectHV(4, 2, 11, 13)))
            StdOut.print(p + " ");
        StdOut.println();
        // no points in rectangle
        StdOut.println("range [1,2] x [2, 4]: ");
        for (Point2D p : kdTree.range(new RectHV(1, 2, 2, 4)))
            StdOut.print(p + " ");
        StdOut.println();
        StdOut.print("Closest point to (0, 0): ");
        StdOut.println(kdTree.nearest(new Point2D(0, 0)));
        StdOut.print("Closest point to (1, 12): ");
        StdOut.println(kdTree.nearest(new Point2D(1, 12)));
        StdOut.print("Closest point to (8, 5): ");
        StdOut.println(kdTree.nearest(new Point2D(8, 5)));
        StdOut.print("Closest point to (8, 11): ");
        StdOut.println(kdTree.nearest(new Point2D(8, 11)));
        StdOut.print("Closest point to (100, 100): ");
        StdOut.println(kdTree.nearest(new Point2D(100, 100)));
        // StdOut.println(new RectHV(-INF, INF, 8, INF).distanceSquaredTo((new Point2D(100, 100))))
    }

}
