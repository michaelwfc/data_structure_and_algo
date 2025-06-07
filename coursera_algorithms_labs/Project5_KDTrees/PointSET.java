/*
 * Brute-force implementation.
 * Write a mutable data type PointSET.java that represents a set of points in the unit square.
 * Implement the following API by using a red–black BST:
 *
 * */


import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;


public class PointSET {
    // the implementation of SET is red–black BST
    private final SET<Point2D> pointSet;
    private int size;

    // construct an empty set of points
    public PointSET() {
        this.pointSet = new SET<Point2D>();
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p)) {
            pointSet.add(p);
            size++;
        }
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return pointSet.contains(p);
    }


    // draw all points to standard draw
    public void draw() {
//        StdDraw.clear();
//        StdDraw.setPenColor(StdDraw.BLACK);


        for (Point2D p : pointSet) {
//            StdDraw.setPenColor(StdDraw.BLACK);
//            StdDraw.setPenRadius(0.01);
            p.draw();

        }

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> rangPoints = new ArrayList<Point2D>();
        for (Point2D p : pointSet) {
//            double distance = rect.distanceSquaredTo(p);
//            if (distance == 0.0) {
            if (rect.contains(p)){
                rangPoints.add(p);
            }
        }
        return rangPoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (size == 0) return null;
        Point2D nearestNeighbor = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (Point2D p1 : pointSet) {
            double distance = p.distanceSquaredTo(p1);
            if (distance < minDistance) {
                minDistance = distance; // update minDistance
                nearestNeighbor = p1; // update nearestNeighbor
            }
        }
        return nearestNeighbor;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.0, 1.0);
        StdDraw.setYscale(-0.0, 1.0);

        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            StdDraw.show();
            StdDraw.pause(3);
        }
    }
}
