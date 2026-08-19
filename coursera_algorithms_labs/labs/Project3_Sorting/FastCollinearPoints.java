/*
* https://introcs.cs.princeton.edu/java/assignments/collinear.html
* https://introcs.cs.princeton.edu/java/assignments/checklist/collinear.html
* A faster, sorting-based solution.
* Remarkably, it is possible to solve the problem much faster than the brute-force solution described above.
* Given a point p, the following method determines whether p participates in a set of 4 or more collinear points.

- Think of p as the origin.
- For each other point q, determine the slope it makes with p.
- Sort the points according to the slopes they makes with p.
- Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.
Applying this method for each of the n points in turn yields an efficient algorithm to the problem.
* The algorithm solves the problem because points that have equal slopes with respect to p are collinear, and sorting brings such points together.
* The algorithm is fast because the bottleneck operation is sorting.
* */


import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Merge;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        // Check for null or duplicate points
        if (points == null) throw new IllegalArgumentException("Points array is null");
        Point[] pCopy = points.clone();
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("Null Point found");
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException("Duplicate points found");
            }
        }

        Arrays.sort(pCopy);

        int n = points.length;
        for (int i = 0; i < n - 3; i++) {
            Point p = pCopy[i];
            Point[] otherPoints = new Point[n - i - 1];
            // For each other point q, determine the slope it makes with p.
            Point[] copy = pCopy.clone();
            for (int j = i + 1; j < n; j++) {
                otherPoints[j - i - 1] = copy[j];
            }
            // Sort the points according to the slopes they makes with p.
            Arrays.sort(otherPoints, p.slopeOrder());

            // Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
            for (int j = 0; j < n - i - 3; j++) {
                Point q = otherPoints[j];
                Point r = otherPoints[j + 1];
                Point s = otherPoints[j + 2];

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


    public int numberOfSegments()        // the number of line segments
    {
        return segments.size();
    }

    public LineSegment[] segments()                // the line segments
    {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}