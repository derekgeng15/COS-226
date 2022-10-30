import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

public class KdTreeST<Value> {

    // infinity
    private static final double INF = Double.POSITIVE_INFINITY;

    private Node root; // root of KD Tree

    // number of nodes
    private int size;

    private class Node {
        private Point2D p; // stores point
        private Value val; // stores value

        private Node left, right; // pointers to left & right nodes

        private RectHV rect; // stores bounding rectangle

        // construct Node
        public Node(Point2D p, Value val, RectHV rect) {
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
            throw new IllegalArgumentException("p cannot be null");
        if (val == null)
            throw new IllegalArgumentException("value cannot be null");
        root = put(root, p, val, true, new RectHV(-INF, -INF, INF, INF));
    }

    // traverse the tree recursively to find where to place the new point
    private Node put(Node curr, Point2D p, Value v, boolean vert, RectHV rect) {
        // once at the correct position, create new Node
        if (curr == null) {
            size++;
            return new Node(p, v, rect);
        }
        // checks if the key already exists, updates value if it does
        if (curr.p.equals(p)) {
            curr.val = v;
            return curr;
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
            if (p.y() < curr.p.y()) {
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

    //
    private Value get(Node curr, Point2D p, boolean vert) {
        // point doesn't exist in tree
        if (curr == null)
            return null;
        // once point is found, return its value
        if (curr.p.equals(p))
            return curr.val;
        // compare x coords if vertical layer
        if (vert) {
            if (p.x() >= curr.p.x())
                return get(curr.right, p, !vert);
            return get(curr.left, p, !vert);
            // compare y coords
        } else {
            if (p.y() < curr.p.y())
                return get(curr.left, p, !vert);
            return get(curr.right, p, !vert);
        }
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("p cannot be null");
        return get(p) != null;
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        Queue<Point2D> qPoint = new Queue<>();
        Queue<Node> qNode = new Queue<>();
        if (root == null)
            return qPoint;
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
        if (rect == null)
            throw new IllegalArgumentException("rect cannot be null");
        Queue<Point2D> qPoints = new Queue<>(); // queue for points inside rectangle
        range(rect, root, true, qPoints);
        return qPoints;
    }

    // searches for points inside rectangle and adds them to queue when found
    private void range(RectHV rect, Node curr, boolean vert, Queue<Point2D> qPoints) {
        // done searching
        if (curr == null)
            return;

        // add point to queue, search its left and right branches
        if (rect.contains(curr.p)) {
            qPoints.enqueue(curr.p);
        }
        // if bounding rect doesn't intersect query rect, prune branch
        if (curr.left != null && curr.left.rect.intersects(rect))
            range(rect, curr.left, !vert, qPoints);
        if (curr.right != null && curr.right.rect.intersects(rect))
            range(rect, curr.right, !vert, qPoints);
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("p cannot be null");
        // tree is empty
        if (root == null)
            return null;
        return nearest(p, root, root.p);
    }

    // nearest neighbor search
    private Point2D nearest(Point2D q, Node curr, Point2D champ) {
        // search over, return champ
        if (curr == null)
            return champ;
        
        // stores champ dist
        double champDist = champ.distanceSquaredTo(q);
        // variables for the distances
        double leftDist = INF, rightDist = INF;

        // compute distances to each rectangle
        if (curr.left != null) 
            leftDist = curr.left.rect.distanceSquaredTo(q);
        if (curr.right != null)
            rightDist = curr.right.rect.distanceSquaredTo(q);
        // update champ and champ distance if smaller distance found
        if (curr.p.distanceSquaredTo(q) < champDist) {
            champ = curr.p;
            champDist = champ.distanceSquaredTo(q);
        }
        if (leftDist < rightDist) { // check which direction to go first
            // prune branch if dist to bounding rectangle is greater than champ dist
            if (leftDist < champDist) {
                champ = nearest(q, curr.left, champ);
                champDist = champ.distanceSquaredTo(q);
            }
            if (rightDist < champDist) {
                champ = nearest(q, curr.right, champ);
                champDist = champ.distanceSquaredTo(q);
            }
            
        } 
        else {
            // prune branch if dist to bounding rectangle is greater than champ dist
            if (rightDist < champDist) {
                champ = nearest(q, curr.right, champ);
                champDist = champ.distanceSquaredTo(q);
            }
            if (leftDist < champDist) {
                champ = nearest(q, curr.left, champ);
                champDist = champ.distanceSquaredTo(q);
            }
        }
        
        return champ;
    }

    // unit testing (required)
    public static void main(String[] args) {
        KdTreeST<String> kdTree = new KdTreeST<>();
        StdOut.print("Empty: ");
        StdOut.println(kdTree.isEmpty()); // true
        kdTree.put(new Point2D(8, 8), "A"); // A
        kdTree.put(new Point2D(10, 6), "B"); // B
        kdTree.put(new Point2D(5, 11), "C"); // C
        kdTree.put(new Point2D(3, 2), "D"); // D
        kdTree.put(new Point2D(2, 9), "E"); // E
        kdTree.put(new Point2D(7, 12), "F"); // F
        kdTree.put(new Point2D(8, 4), "G"); // G
        StdOut.print("Empty: ");
        StdOut.println(kdTree.isEmpty()); // false
        StdOut.print("Size: ");
        StdOut.println(kdTree.size()); // 7
        kdTree.put(new Point2D(8, 4), "H"); // G
        StdOut.print("Size: ");
        StdOut.println(kdTree.size()); // 7
        StdOut.print("Contains Point (8, 8): "); // true
        StdOut.println(kdTree.contains(new Point2D(8, 8)));
        StdOut.print("Contains Point (0, 0): "); // false
        StdOut.println(kdTree.contains(new Point2D(0, 0)));
        StdOut.println("Level Order Traversal: ");
        for (Point2D p : kdTree.points()) {
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
        // testing closest point
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

        // nearest timing test
        KdTreeST<Integer> STTimer = new KdTreeST<>();
        In in = new In("input1M.txt");
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            STTimer.put(new Point2D(x, y), i);
        }
        int t = 1000000; // number of trials
        Point2D[] randPoints = new Point2D[t];
        // generate t random points
        for (int i = 0; i < t; i++)
            randPoints[i] = new Point2D(StdRandom.uniformDouble(), StdRandom.uniformDouble());
        StopwatchCPU stopwatchCPU = new StopwatchCPU();
        for (int i = 0; i < t; i++) // run nearest() t times
            STTimer.nearest(randPoints[i]);
        double time = stopwatchCPU.elapsedTime();
        StdOut.print("Time to do ");
        StdOut.print(t);
        StdOut.print(" nearest calls: ");
        StdOut.println(time);
    }

}
