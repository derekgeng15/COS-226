
public class Point2D implements Comparable<Point2D> {

    private double x, y;
    // construct the point (x, y)
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // x-coordinate 
    public double x() {
        return x;
    }

    // y-coordinate 
    public double y() {
        return y;
    }

    // square of Euclidean distance between two points 
    public double distanceSquaredTo(Point2D that) {
        return Math.pow((that.x - x), 2) + Math.pow((that.y - y),2);
    }

    // for use in an ordered symbol table 
    public int compareTo(Point2D that) {
        if (x == that.x) // check y coord if x is equal
            return (int)(y - that.y);
        return (int)(x - that.x); // check x first
    }

    // does this point equal that object? 
    public boolean equals(Object that) {
        return that.equals(this);
    }

    // string representation with format (x, y) 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(x);
        sb.append(", ");
        sb.append(y);
        sb.append(")");
        return sb.toString();
        
    }

}

