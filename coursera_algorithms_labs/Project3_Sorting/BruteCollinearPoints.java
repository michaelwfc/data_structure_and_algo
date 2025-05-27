/*
Brute force.
Write a program BruteCollinearPoints.java that examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments.
To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q, between p and r, and between p and s are all equal.

*/


public class BruteCollinearPoints {
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points

    public int numberOfSegments()        // the number of line segments

    /*
    the line segments
    The method segments() should include each line segment containing 4 points exactly once.
    If 4 points appear on a line segment in the order p→q→r→s, then you should include either the line segment p→s or s→p (but not both)
    and you should not include subsegments such as p→r or q→r.
    For simplicity, we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.
     */
    public LineSegment[] segments()


    public static double angle(Point a, Point b) {
         Math.atan((b.y-a.y));
    }

    public static boolean areCollinear(Point a, Point b, Point c)

    public static boolean areCollinear(Point a, Point b, Point c, Point d)