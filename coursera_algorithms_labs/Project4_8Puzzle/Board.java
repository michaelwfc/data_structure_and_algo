/*
 *
 * The input will consist of the board dimension N followed by the N-by-N initial board position.
 * The input format uses 0 to represent the blank square. As an example,
 * */
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;


public class Board {
    private final int n;
    private final int[][] tiles;
    private final int blank_i; // the  index for empty
    private final int blank_j;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if(tiles ==null) throw new IllegalArgumentException("tiles is null");
        int n = tiles.length;
        int m = tiles[0].length;
        if (n != m) throw new IllegalArgumentException("row num is not equal to column num");
        if (n < 2 || n >= 128) throw new IllegalArgumentException("n is too small or to large:" + n);
        this.n = n;
        this.tiles = tiles;

        int bi=-1,bj=-1;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(tiles[i][j]==0){
                    bi=i;
                    bj=j;
                }
            }
        }
        this.blank_i = bi;
        this.blank_j= bj;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ",tiles[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension(){
        return n;
    }

    // number of tiles out of place
    public int hamming(){
        int hamming_distance=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                int expected = n*i+j+1;
                if(tiles[i][j]!=0&& tiles[i][j] != expected){
                    hamming_distance++;
                }
            }
        }
        return hamming_distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int manhattan_distance=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                int expected = n*i+j+1;
                if(tiles[i][j]!=0&& tiles[i][j] != expected) {
                    int num = tiles[i][j];
                    int i_goal = (num - 1) / n;
                    int j_goal = (num - 1) % n;
                    int d = Math.abs(i - i_goal) + Math.abs(j - j_goal);
                    manhattan_distance += d;
                }
            }
        }
        return manhattan_distance;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return this.hamming() == 0;
    }

    // does this board equal y?
    /*
     * Comparing two boards for equality.
     * Two boards are equal if they are have the same size and their corresponding tiles are in the same positions.
     * The equals() method is inherited from java.lang.Object, so it must obey all of Java’s requirements.
     */
    @Override
    public boolean equals(Object y){
        if(y==null) return false;
        if(this.getClass()!= y.getClass()) return false;
        Board that = (Board) y;
        if(this.n!= that.n) return false;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;

    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        ArrayList<Board> neighbors = new ArrayList<>();
        int[][] directions = {
                {-1, 0}, // up
                {1, 0},  // down
                {0, -1}, // left
                {0, 1}   // right
        };
        for(int[] direction:directions){
            int new_i = blank_i+ direction[0];
            int new_j = blank_j+ direction[1];
            if(isInBounds(new_i,new_j)){
                int[][] neighbor = copyTiles();
                neighbor[blank_i][blank_j]= neighbor[new_i][new_j];
                neighbor[new_i][new_j]=0;
                neighbors.add(new Board(neighbor));
            }
        }
        return neighbors;

    }

    private boolean isInBounds(int i, int j){
        return i>=0 && i<n && j>=0 && j<n;
    }

    private int[][] copyTiles(){
        int[][] copy = new int[n][n];
        for (int i=0;i<n;i++) copy[i] = tiles[i].clone();
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int[][] copy = copyTiles();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++) {
                // swap (i,j) and (i, j+1)
                int new_j = j+1;

                if (isInBounds(i,new_j) && copy[i][j] !=0 &&  copy[i][new_j]!=0){
                    int temp = copy[i][j];
                    copy[i][j]= copy[i][new_j];
                    copy[i][new_j] = temp;
                    return new Board(copy);
                }
            }
        }
        throw new IllegalStateException("No twin can be created");
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
        Board board = new Board(tiles);
        System.out.println("Board:");
        System.out.println(board);
        System.out.println("Hamming: " + board.hamming());
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());
        System.out.println("Neighbors:");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }
        System.out.println("Twin:");
        System.out.println(board.twin());
    }

}