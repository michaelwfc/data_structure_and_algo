/* Seam-carving
 * https://coursera.cs.princeton.edu/algs4/assignments/seam/specification.php
 *
 *
 *
 * */

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SeamCarver {
    private Picture picture;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if(picture ==null) throw new IllegalArgumentException("picture is null");
        // make a defensive copy
        // this ensures the internal state of your object cannot be altered by outside code after construction
        this.picture = new Picture(picture);

    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    // which is a measure of the importance of each pixel—the higher the energy, the less likely that the pixel will be included as part of a seam
    // the dual-gradient energy function,

    public double energy(int x, int y) {
        if (x <= 0 || x >= picture.width()-1 || y <= 0 || y >= picture.height()-1) {
            return 1000;
        }
        double deviationX = calculateDeviation(picture.get(x + 1, y), picture.get(x - 1, y));
        double deviationY = calculateDeviation(picture.get(x, y + 1), picture.get(x, y - 1));
        double energyValue = Math.sqrt(deviationX + deviationY);
        return energyValue;
    }

    private double calculateDeviation(Color nextColor, Color beforeColor) {
        return Math.pow(nextColor.getRed() - beforeColor.getRed(), 2) + Math.pow(nextColor.getGreen() - beforeColor.getGreen(), 2)
                + Math.pow(nextColor.getBlue() - beforeColor.getBlue(), 2);

    }

    /**
     * When finding a seam, call energy() at most once per pixel.
     * For example, you can save the energies in a local variable energy[][] and access the information directly from the 2D array (instead of recomputing from scratch).
     */

    private double getEnergy(int x, int y, double[][] energyArray) {
        validateIndex(x,y);
        double energyValue;
        if (energyArray[x][y] != -1) {
            energyValue = energyArray[x][y];
        } else {
            energyValue = energy(x, y);
            energyArray[x][y] = energyValue; // update energyArray"

        }
        return energyValue;
    }

        // A vertical seam in an image is a path of pixels connected from the top to the bottom with one pixel in each row;
        // find a vertical seam of minimum total energy.
        // - The weights are on the vertices instead of the edges.
        // - We want to find the shortest path from any of the W pixels in the top row to any of the W pixels in the bottom row
        // sequence of indices for vertical seam:  an array of length H such that entry y is the column number of the pixel to be removed from row y of the image.
        //Don’t use an explicit EdgeWeightedDigraph. Instead, execute the topological sort algorithm directly on the pixels.
        public int[] findVerticalSeam () {
            int height = picture.height();
            int width = picture.width();

            //initial energyArray
            double[][] energyArray = new double[width][height];
            for (int i = 0; i < picture.width(); i++)
                for (int j = 0; j < picture.height(); j++)
                    energyArray[i][j] = -1;


            double[][] distTo = new double[width][height];   // Use a distance-to array distTo[x][y] to track minimum energy from top row to (x,y)
            int[][] edgeTo = new int[width][height];      //  Use a path-to array edgeTo[x][y] to remember the previous pixel's x coordinate.

            // Step 1: Initialize distTo
            for (int i = 0; i < width; i++) {
                distTo[i][0] = 1000;    // top row: base energy
                for (int j = 1; j < height; j++)
                    distTo[i][j] = Double.POSITIVE_INFINITY;
            }

            // Step 2: build shortest path from top to bottom
            for (int j = 0; j < height; j++)
                for (int i = 0; i < width; i++) {
                    // The digraph is acyclic, where there is a downward edge from pixel (x, y) to pixels (x − 1, y + 1), (x, y + 1), and (x + 1, y + 1)
                    for (int dx = -1; dx <= 1; dx++) {
                        int nextX = i + dx;
                        int nextY = j + 1;
                        if (nextX >= 0 && nextX< this.width() && nextY >= 0 && nextY < this.height()) {
                            double energyValue = getEnergy(nextX,nextY, energyArray);
                            double newDist = distTo[i][j] + energyValue;
                            // use Topological and relaxing the edges in the digraph
                            // if the new distance is smaller than the current distance, update the distance and the path
                            if (newDist < distTo[nextX][nextY]) {
                                distTo[nextX][nextY] = newDist; // update the minium energy
                                edgeTo[nextX][nextY] = i;       //
                            }
                        }
                    }
                }

            // Step 3: Find bottom pixel with min total energy
            double minEnergy = Double.POSITIVE_INFINITY;
            int minX = -1;
            for (int x = 0; x < width; x++) {
                int y = height - 1;
                if (distTo[x][y] < minEnergy) {
                    minEnergy = distTo[x][y];
                    minX = x;
                }
            }


            // Step 4: Backtrack the seam with the minX
            int[] verticalSeam = new int[height];
            // the x in the bottom row
            verticalSeam[height - 1] = minX;

            int currentX = minX;
            for (int y = height - 1; y >= 1; y--) {
                int lastX = edgeTo[currentX][y];
                verticalSeam[y - 1] = lastX;
                currentX = lastX; // update currentX to track back

            }
            return verticalSeam;

        }

        private void validateIndex ( int x, int y){
            if(x < 0 || x >= this.width() || y < 0 || y >= this.height()) throw new IllegalArgumentException("invalid index");
        }


        // sequence of indices for horizontal seam
        // To write findHorizontalSeam(), transpose the image, call findVerticalSeam(), and transpose it back.
        public int[] findHorizontalSeam () {
            Picture original = this.picture;
            Picture transposed = transpose(original);
            this.picture = transposed;
            int[] seam = findVerticalSeam();
            this.picture = original;
            return seam;
        }

        private Picture transpose (Picture original){
            int width = original.width();
            int height = original.height();
            Picture transposed = new Picture(height, width);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    transposed.set(y, x, original.get(x, y));
                }
            }
            return transposed;
        }

        // remove vertical seam from current picture
        // Consider using System.arraycopy() to shift elements within an array
        // Creating Color objects can be a bottleneck. Each call to the get() method in Picture creates a new Color object.
        // optimation 1:
        // You can avoid this overhead by using the getRGB() method in Picture, which returns the color, encoded as a 32-bit int.
        // The companion setRGB() method sets the color of a given pixel using a 32-bit int to encode the color.

        //  optimation 2:
        // Reuse the energy array and shift array elements to plug the holes left from the seam that was just removed.
        // You will need to recalculate the energies for the pixels along the seam that was just removed, but no other energies will change.

        public void removeVerticalSeam ( int[] seam){
            int height = height();
            int width = width();
            if (seam == null || seam.length != height) throw new IllegalArgumentException("Invalid seam");

            // Validate seam is within image bounds and is continuous
            for (int y = 0; y < height(); y++) {
                if (seam[y] < 0 || seam[y] >= width)
                    throw new IllegalArgumentException("Seam value out of bounds at row " + y);
                if(y>0 && Math.abs(seam[y]- seam[y-1])>1)
                    throw new IllegalArgumentException("Seam not continuous");

            }

            Picture newPicture = new Picture(width - 1, height);

            for (int y = 0; y < height; y++) {
                int xToRemove = seam[y];
                for (int x = 0; x < xToRemove; x++) {
                    newPicture.set(x, y, picture.get(x, y));
                }
                // Copy all pixels after the seam, shifted left by 1
                if (xToRemove < width - 1) {
                    for (int x = xToRemove + 1; x < width; x++) {
                        newPicture.set(x - 1, y, picture.get(x, y));
                    }
                }
            }
            this.picture = newPicture; // update picture

        }


        // remove horizontal seam from current picture
        // transpose the image, call removeVerticalSeam(), and transpose it back.
        // optimazation:
        // Don’t explicitly transpose the Picture or int[][] until you need to do so. For example, if you perform a sequence of 50 consecutive horizontal seam removals, you should need only two transposes (not 100).
        public void removeHorizontalSeam ( int[] seam){
            Picture original = this.picture;
            Picture transposed = transpose(original);
            this.picture = transposed;
            removeVerticalSeam(seam);
            Picture removedOriginal = transpose(this.picture);
            this.picture = removedOriginal;

        }


        //  unit testing (optional)
//    public static void main(String[] args)

    }