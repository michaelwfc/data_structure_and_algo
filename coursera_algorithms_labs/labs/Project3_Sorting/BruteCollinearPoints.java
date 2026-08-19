/*
Brute force.
Write a program BruteCollinearPoints.java that examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments.
To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q, between p and r, and between p and s are all equal.

*/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        // Check for null or duplicate points
        if (points == null) throw new IllegalArgumentException("Points array is null");
        Point[] copy = points.clone();

        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("Null Point found");
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException("Duplicate points found");
            }
        }
        Arrays.sort(copy);
        //Brute-force all combinations of 4 points
        int n = points.length;
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = copy[i];
                        Point q = copy[j];
                        Point r = copy[k];
                        Point s = copy[l];

                        double slopePQ = p.slopeTo(q);
                        double slopePR = p.slopeTo(r);
                        double slopePS = p.slopeTo(s);
                        // Check if slopes are equal
                        if (Double.compare(slopePQ, slopePR) == 0 && Double.compare(slopePQ, slopePS) == 0) {
                            Point[] line = {p, q, r, s};
                            // Sort the 4 points, create a LineSegment from smallest to largest
                            Arrays.sort(line);
                            segments.add(new LineSegment(line[0], line[3]));

                        }
                    }
                }
            }
        }
    }


    public int numberOfSegments()        // the number of line segments
    {
        return segments.size();
    }

    /*
    the line segments
    The method segments() should include each line segment containing 4 points exactly once.
    If 4 points appear on a line segment in the order p→q→r→s, then you should include either the line segment p→s or s→p (but not both)
    and you should not include subsegments such as p→r or q→r.
    For simplicity, we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points) ;
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

//    public static double angle(Point a, Point b) {
//         Math.atan((b.y-a.y));
//    }

//    public static boolean areCollinear(Point a, Point b, Point c)
//
//    public static boolean areCollinear(Point a, Point b, Point c, Point d)