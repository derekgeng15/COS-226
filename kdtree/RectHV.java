
public class RectHV {

    private double xmin, ymin, xmax, ymax;

    // construct the rectangle [xmin, xmax] x [ymin, ymax]
    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    // minimum x-coordinate of rectangle
    public double xmin() {
        return xmin;
    }

    // minimum y-coordinate of rectangle
    public double ymin() {
        return ymin;
    }

    // maximum x-coordinate of rectangle
    public double xmax() {
        return xmax;
    }

    // maximum y-coordinate of rectangle
    public double ymax() {
        return ymax;
    }

    // does this rectangle contain the point p (either inside or on boundary)?
    public boolean contains(Point2D p) {
        // check if point coordinates are within bound of rectangle
        return p.x() >= xmin && p.x() <= xmax && p.y() >= ymin && p.y() <= ymax();
    }

    // does this rectangle intersect that rectangle (at one or more points)?
    public boolean intersects(RectHV that) {
        // check all four vertices
        if (contains(new Point2D(that.xmin, that.ymin)))
            return true;
        if (contains(new Point2D(that.xmin, that.ymax)))
            return true;
        if (contains(new Point2D(that.xmax, that.ymin)))
            return true;
        if (contains(new Point2D(that.xmax, that.ymax)))
            return true;
        
        return false;
    }

    // square of Euclidean distance from point p to closest point in rectangle
    public double distanceSquaredTo(Point2D p) {

        if (contains(p)) // inside rectange, find min dist to each border
            return Math.pow(Math.min(Math.min(p.x() - xmin, xmax - p.x()),
                    Math.min(p.y() - ymin, ymax - p.y())), 2);
        // inside x bounds, find min dist to y border
        else if (p.x() >= xmin && p.x() <= xmax) 
            return Math.min(Math.pow(p.x() - xmin, 2), Math.pow(p.x() - xmax, 2));
        // inside y bounds, find min dist to x border
        else if (p.y() >= ymin && p.y() <= ymax()) 
            return Math.min(Math.pow(p.y() - ymin, 2), Math.pow(p.y() - ymax, 2));
        else {
            // compute distances from each of the four verticies
            double bl = p.distanceSquaredTo(new Point2D(xmin, ymin));
            double br = p.distanceSquaredTo(new Point2D(xmin, ymax));
            double tl = p.distanceSquaredTo(new Point2D(xmax, ymin));
            double tr = p.distanceSquaredTo(new Point2D(xmax, ymax));
            // return miminum distance
            return Math.min(Math.min(bl, br), Math.min(tl, tr));
        }

    }

    // does this rectangle equal that object?
    public boolean equals(Object that) {
        return that.equals(this);
    }

    // string representation with format [xmin, xmax] x [ymin, ymax]
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(xmin);
        sb.append(", ");
        sb.append(xmax);
        sb.append("] x ");
        sb.append("[");
        sb.append(ymin);
        sb.append(", ");
        sb.append(ymax);
        sb.append("]");
        return sb.toString();
    }

}
