/*
* Write a data type to represent a set of points in the unit square (all points have x- and y-coordinates between 0 and 1)
* using a 2d-tree to support
* -  efficient range search (find all of the points contained in a query rectangle)
* -  nearest-neighbor search (find a closest point to a query point).
* 2d-trees have numerous applications, ranging from classifying astronomical objects to computer animation to speeding up neural networks to mining data to image retrieval.
*
* 2d-tree implementation.
* Write a mutable data type KdTree.java that uses a 2d-tree to implement the same API (but replace PointSET with KdTree).
* A 2d-tree is a generalization of a BST to two-dimensional keys.
* The idea is to build a BST with points in the nodes, using the x- and y-coordinates of the points as keys in strictly alternating sequence.

Search and insert.
* The algorithms for search and insert are similar to those for BSTs, but at the root we use the x-coordinate
* (if the point to be inserted has a smaller x-coordinate than the point at the root, go left; otherwise go right);
* then at the next level, we use the y-coordinate (if the point to be inserted has a smaller y-coordinate than the point in the node, go left;  otherwise go right);
* then at the next level the x-coordinate, and so forth.
*
* The prime advantage of a 2d-tree over a BST is that it supports efficient implementation of range search and nearest-neighbor search.
*
* Each node corresponds to an axis-aligned rectangle in the unit square, which encloses all of the points in its subtree.
* The root corresponds to the unit square;
* the left and right children of the root corresponds to the two rectangles split by the x-coordinate of the point at the root; and so forth.
*
* Range search.
* To find all points contained in a given query rectangle, start at the root and recursively search for points in both subtrees using the following pruning rule:
* if the query rectangle does not intersect the rectangle corresponding to a node, there is no need to explore that node (or its subtrees).
* A subtree is searched only if it might contain a point contained in the query rectangle.
*
* Nearest-neighbor search.
* To find a closest point to a given query point, start at the root and recursively search in both subtrees using the following pruning rule:
* if the closest point discovered so far is closer than the distance between the query point and the rectangle corresponding to a node, there is no need to explore that node (or its subtrees).
* That is, search a node only only if it might contain a point that is closer than the best one found so far.
* The effectiveness of the pruning rule depends on quickly finding a nearby point.
* To do this, organize the recursive method so that when there are two possible subtrees to go down,
* you always choose the subtree that is on the same side of the splitting line as the query point as the first subtree to
* explore—the closest point found while exploring the first subtree may enable pruning of the second subtree.
*
** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.List;
import java.util.ArrayList;


public class KdTree {
    private Node root;
    private int size;

    /*
     * Each node corresponds to an axis-aligned rectangle in the unit square, which encloses all of the points in its subtree.
     * The root corresponds to the unit square;
     * the left and right children of the root corresponds to the two rectangles split by the x-coordinate of the point at the root; and so forth.
     *
     * */
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int num;

        public Node(Point2D p, RectHV rect, Node lb, Node rt, int num) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
            this.num = num;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    private int updateOrientation(int orientation){
        return  (orientation + 1) % 2;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (contains(p)) {
            return;
        }
        if (isEmpty()) {
            size++;
            root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), null, null, size);
            return;
        }

        //  insert the point the root with horizontal line
        insert(root, p, 0);
    }




    private void insert(Node x, Point2D p, int orientation) {
        /*
         * orientation: 0 means vertical, 1 means horizontal
         * */
        // lb contain p
        if (x.lb != null && x.lb.rect.contains(p)) {
            insert(x.lb, p, (orientation + 1) % 2);
            // rt contain p
        } else if (x.rt != null && x.rt.rect.contains(p)) {
            insert(x.rt, p, (orientation + 1) % 2);
        } else {
            RectHV lb = getRect(x, orientation, 0);
            if (x.lb == null && lb.contains(p)) {
                // add new node to lb
                size++;
                x.lb = new Node(p, lb, null, null, size);
                return;
            }
            RectHV rt = getRect(x, orientation, 1);
            if (x.rt == null && rt.contains(p)) {
                size++;
                x.rt = new Node(p, rt, null, null, size);
                return;
            }
        }

    }



    private RectHV getRect(Node x, int orientation, int region) {
        /*
         * orientation: 0 means vertical, 1 means horizontal
         * region: 0 means lb, 1 means rt
         * */
        if (orientation == 0 && region == 0) {
            // left
            return new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else if (orientation == 0 && region == 1) {
            // right
            return new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
        } else if (orientation == 1 && region == 0) {
            // bottom
            return new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
        } else {
            // up
            return new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return false;
        return contains(root, p, 0);
    }

    private boolean contains(Node x, Point2D p, int orientation) {
        if (x == null) return false;
        if (x.p.equals(p)) return true;
        //  Using .rect.contains(p) in contains() is incorrect for traversal decisions.
//        if (x.lb != null && x.lb.rect.contains(p)) return contains(x.lb, p, (orientation + 1) % 2);
//        else if (x.rt != null && x.rt.rect.contains(p)) return contains(x.rt, p, (orientation + 1) % 2);
//        else return false;

        // The search path in a kd-tree is determined only by comparing point coordinates, not rectangles.
        if (orientation == 0) { // vertical compare x-coordinates
            if (p.x() <= x.p.x()) return contains(x.lb, p, 1);
            else return contains(x.rt, p, 1);
        } else { // horizontal compare y-coordinates
            if (p.y() <= x.p.y()) return contains(x.lb, p, 0);
            else return contains(x.rt, p, 0);

        }
    }


    // draw all points to standard draw
    public void draw() {
        draw(root, 0, 1);
    }

    private void draw(Node x, int orientation, int split) {
        if (x == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        x.p.draw();
        StdDraw.text(x.p.x() + 0.03, x.p.y() + 0.02, String.format("%d-%d", x.num, split));
        split++;
        if (orientation == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());

        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }

        if (x.lb != null) {
            draw(x.lb, (1 + orientation) % 2, split);
        }
        if (x.rt != null) {
            draw(x.rt, (1 + orientation) % 2, split);
        }


    }

    // all points that are inside the rectangle (or on the boundary)
    /*
     * */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        //Use List<Point2D> to preserve insertion order (or allow duplicates if needed).
        List<Point2D> rangPoints = new ArrayList<Point2D>();
        if (isEmpty()) return rangPoints;
        range(root, rect, rangPoints);
        return rangPoints;
    }

    /*
    * Goal. Find all points in a query axis-aligned rectangle.
    - Check if point in node lies in given rectangle.
    - Recursively search left/bottom (if any could fall in rectangle).
    - Recursively search right/top (if any could fall in rectangle).
    *
    * pruning rule:
    * if the query rectangle does not intersect the rectangle corresponding to a node, there is no need to explore that node (or its subtrees).
    * A subtree is searched only if it might contain a point contained in the query rectangle.
    *
    * Typical case. R + log N.
    * Worst case (assuming tree is balanced). R + √N.
    * */
    private void range(Node x, RectHV rect, List<Point2D> rangPoints) {
        if (x == null) return;
        // Only visit this subtree if its region intersects the query rect
        if (!rect.intersects(x.rect)) return;

        if (rect.contains(x.p)) rangPoints.add(x.p);
        // Recurse on both subtrees (if their region intersects)
        if (x.lb != null && rect.intersects(x.lb.rect)) range(x.lb, rect, rangPoints);
        if (x.rt != null && rect.intersects(x.rt.rect)) range(x.rt, rect, rangPoints);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    /*
    􀉾Check distance from point in node to query point.
    􀉾Recursively search left/bottom (if it could contain a closer point).
    􀉾Recursively search right/top (if it could contain a closer point).
    􀉾Organize method so that it begins by searching for query point.
    *
    To find a closest point to a given query point, start at the root and recursively search in both subtrees using the following pruning rule:
    if the closest point discovered so far is closer than the distance between the query point and the rectangle corresponding to a node, there is no need to explore that node (or its subtrees).
    That is, search a node only only if it might contain a point that is closer than the best one found so far.
    The effectiveness of the pruning rule depends on quickly finding a nearby point.
    To do this, organize the recursive method so that when there are two possible subtrees to go down,
    you always choose the subtree that is on the same side of the splitting line as the query point as the first subtree to explore—the closest point found while exploring the first subtree may enable pruning of the second subtree.
    * */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (size == 0) return null;
        double minDistance = Double.POSITIVE_INFINITY;
//        return nearest(root, p, null, minDistance).nearestPoint;
        return nearest(root, p,root.p,0);

    }

//    // pass the nearestPoint and minDistance back when iterative
//    private static class NearestResult {
//        Point2D nearestPoint;
//        Double minDistance;
//
//        public NearestResult(Point2D nearestPoint, Double minDistance) {
//            this.nearestPoint = nearestPoint;
//            this.minDistance = minDistance;
//        }
//
//    }
//
//    private NearestResult nearest(Node x, Point2D p, Point2D nearestPoint, double minDistance) {
//        if (x == null) {
//            return new NearestResult(nearestPoint, minDistance);
//        }
//        ;
//        // update the minDistance with the current node
//        double distance = x.p.distanceSquaredTo(p);
//        if (distance < minDistance) {
//            nearestPoint = x.p;
//            minDistance = distance;
//        }
//        if (x.lb == null && x.rt == null) {
//            return new NearestResult(nearestPoint, minDistance);
//        }
//        //when there is only  possible subtrees to go down
//        //  pruning rule
//        if (x.lb != null && minDistance < x.lb.rect.distanceSquaredTo(p)) {
//            return nearest(x.rt, p, nearestPoint, minDistance);
//        }else if (x.rt != null && minDistance < x.rt.rect.distanceSquaredTo(p)) {
//            return nearest(x.lb, p, nearestPoint, minDistance);
//        }
//        //when there are two possible subtrees to go down
//        //Recursively search left/bottom (if it could contain a closer point).
//        else if (x.rt == null || (x.lb != null && x.lb.rect.distanceSquaredTo(p) < x.rt.rect.distanceSquaredTo(p))) {
//            // first search lb then rt
//            NearestResult nearestResultLb = nearest(x.lb, p, nearestPoint, minDistance);
//            // do not forget pass the minDistance updated from last step
//            NearestResult nearestResult = nearest(x.rt, p, nearestResultLb.nearestPoint, nearestResultLb.minDistance);
//            return nearestResult;
//        } else {
//            NearestResult nearestResultRt = nearest(x.rt, p, nearestPoint, minDistance);
//            NearestResult nearestResult = nearest(x.lb, p, nearestResultRt.nearestPoint, nearestResultRt.minDistance);
//            return nearestResult;
//        }
//    }

    // From chatgpt
    private Point2D nearest(Node x, Point2D query, Point2D nearestPoint, int depth) {
        if(x==null) return nearestPoint;
        //compare with the current point
        if(x.p.distanceSquaredTo(query)< nearestPoint.distanceSquaredTo(query)){
            nearestPoint = x.p;
        }
        // Determine search order: go to the side of the query point first
        Node first, second;
        if((depth%2==0 && query.x() < x.p.x()) || (depth%2==1 && query.y() < x.p.y())){
            first = x.lb;
            second =x.rt;
        }else{
            first = x.rt;
            second =x.lb;
        }

        nearestPoint = nearest(first, query, nearestPoint, depth+1);
        // Check if we need to explore the other subtree
        if(second!=null && second.rect.distanceSquaredTo(query)< nearestPoint.distanceSquaredTo(query)){
            nearestPoint = nearest(second, query, nearestPoint, depth+1);
        }
        return nearestPoint;
    }


}
