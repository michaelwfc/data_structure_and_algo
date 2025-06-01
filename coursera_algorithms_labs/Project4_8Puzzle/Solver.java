/*
 * https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php
 * https://introcs.cs.princeton.edu/java/assignments/8puzzle.html
 *
 *
 * Best-first search/A* search
 * We now describe an algorithmic solution to the problem that illustrates a general artificial intelligence methodology known as the A* search algorithm.
 * We define a state of the game to be the board position, the number of moves made to reach the board position, and the previous state.
 * First, insert the initial state/node (the initial board, 0 moves, and a null previous state) into a priority queue.
 * Then, delete from the priority queue the state with the minimum priority, and insert onto the priority queue all neighboring states (those that can be reached in one move).
 * Repeat this procedure until the state dequeued is the goal state.
 *
 * The success of this approach hinges on the choice of priority function for a state.
 * - Hamming priority function.
 * The number of blocks in the wrong position, plus the number of moves made so far to get to the state.
 * Intutively, states with a small number of blocks in the wrong position are close to the goal state, and we prefer states that have been reached using a small number of moves.
 *
 * Manhattan priority function.
 * The sum of the distances (sum of the vertical and horizontal distance) from the blocks to their goal positions, plus the number of moves made so far to get to the state.
 *
 * We make a key oberservation:
 * to solve the puzzle from a given state on the priority queue, the total number of moves we need to make (including those already made) is at least its priority,
 * using either the Hamming or Manhattan priority function.
 *  (For Hamming priority, this is true because each block out of place must move at least once to reach its goal position.
 * For Manhattan priority, this is true because each block must move its Manhattan distance from its goal position.
 * Note that we do not count the blank tile when computing the Hamming or Manhattan priorities.)
 *
 * Consequently, as soon as we dequeue a state, we have not only discovered a sequence of moves from the initial board to the board associated with the state,
 * but one that makes the fewest number of moves. (Challenge for the mathematically inclined: prove this fact.)
 *
 * Game tree.
 * One way to view the computation is as a game tree, where each search node is a node in the game tree and the children of a node correspond to its neighboring search nodes.
 * The root of the game tree is the initial search node;
 * the internal nodes have already been processed;
 * the leaf nodes are maintained in a priority queue;
 * at each step, the A* algorithm removes the node with the smallest priority from the priority queue and processes it (by adding its children to both the game tree and the priority queue).
 *
 *
 * Two optimizations.
 * To speed up your solver, implement the following two optimizations:
 * 1. The critical optimization.
 * A* search has one annoying feature: search nodes corresponding to the same board are enqueued on the priority queue many times (e.g., the bottom-left search node in the game-tree diagram above).
 * To reduce unnecessary exploration of useless search nodes, when considering the neighbors of a search node, don’t enqueue a neighbor if its board is the same as the board of the previous search node in the game tree.
 *
 * 2. Caching the Hamming and Manhattan priorities.
 * To avoid recomputing the Manhattan priority of a search node from scratch each time during various priority queue operations,
 * pre-compute its value when you construct the search node; save it in an instance variable; and return the saved value as needed.
 * This caching technique is broadly applicable: consider using it in any situation where you are recomputing the same quantity many times and for which computing that quantity is a bottleneck operation.
 *
 *
 * Detecting unsolvable boards. Not all initial boards can lead to the goal board by a sequence of moves, including these two:
 * To detect such situations, use the fact that boards are divided into two equivalence classes with respect to reachability:
 * A. Those that can lead to the goal board
 * B. Those that can lead to the goal board if we modify the initial board by swapping any pair of tiles (the blank square is not a tile).
 * (Difficult challenge for the mathematically inclined: prove this fact.)
 * To apply the fact, run the A* algorithm on two puzzle instances—one with the initial board and one with the initial board modified by swapping a pair of tiles—in lockstep
 * (alternating back and forth between exploring search nodes in each of the two game trees).
 * Exactly one of the two will lead to the goal board.
 *
 * */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;

public class Solver {
    private final boolean solvable;
    private final ArrayList<Board> solutionBoards;
    private final int solutionMoves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // * First, insert the initial state/node (the initial board, 0 moves, and a null previous state) into a priority queue.
        // * Then, delete from the priority queue the state with the minimum priority, and insert onto the priority queue all neighboring states (those that can be reached in one move).
        // * Repeat this procedure until the state dequeued is the goal state.
        if (initial == null) throw new IllegalArgumentException("Initial board is null");

        MinPQ<State> minPQ = new MinPQ<>();
        MinPQ<State> twinMinPQ = new MinPQ<>();

        minPQ.insert(new State(initial, 0, null));
        twinMinPQ.insert(new State(initial.twin(), 0, null));


        State currentState = null;
        State twinCurrentState = null;
        // end the while loop if either minPQ or twinMinPQ is empty
        while (!minPQ.isEmpty() && !twinMinPQ.isEmpty()) {
            currentState = process(minPQ);
            if (currentState != null && currentState.board.isGoal()) {
                // end the while loop
                solvable = true;
                solutionMoves = currentState.moves;
                solutionBoards = new ArrayList<>();

                // loop untile the current state is null
                while (currentState != null) {
                    solutionBoards.add(0, currentState.board);
                    currentState = currentState.previousState;
                }
                return;
            }
            twinCurrentState = process(twinMinPQ);
            if (twinCurrentState != null && twinCurrentState.board.isGoal()) {
                // end the while loop because of twinTitles
                solvable = false;
                solutionMoves = -1;
                solutionBoards = null;
                return;
            }

        }
        solvable = false;
        solutionMoves = -1;
        solutionBoards = null;

    }

    private State process(MinPQ<State> minPQ) {
        if (minPQ.isEmpty()) return null;
        State currentState = minPQ.delMin(); // update the current state with the minimum  priority
        if (currentState.board.isGoal()) return currentState;

        Iterable<Board> neighbors = currentState.board.neighbors();
        int currentMoves = currentState.moves;
        currentMoves++;
        for (Board neighbor : neighbors) {
            // don’t enqueue a neighbor if its board is the same as the board of the previous search node in the game tree.
            if(currentState.previousState==null || !neighbor.equals(currentState.previousState.board)){
                State state = new State(neighbor, currentMoves, currentState);
                minPQ.insert(state);
            }

        }
        return currentState;
    }


    /*
     *Write a class State that represents a state of the game (board, number of moves to reach it, and previous state).
     *Make it implement the Comparable<State> interface so that you can use it with a MinPQ.
     *The compareTo() method will compare states based on their Hamming or Manhattan priorities.
     *You can either make this a subclass within Solver or make it a stand-alone class.
     *
     * */
    private static class State implements Comparable<State> {
        private final Board board;
        private final int moves;
        private final State previousState;
        private final int priority;

        public State(Board board, int moves, State previousState) {
            this.board = board;
            this.moves = moves;
            this.previousState = previousState;
            this.priority = moves + board.manhattan(); //  priority function: hamming or Manhattan + moves
        }

        @Override
        public int compareTo(State that) {
            return Integer.compare(this.priority, that.priority);
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solutionMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionBoards;
    }

    // test client (see below)
    public static void main(String[] args) {
//        int[][] tiles = {
//                {1, 2, 3},
//                {4, 0, 6},
//                {7, 5, 8}
//        };
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();


        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            System.out.println("No solution possible");
        } else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        }
    }

}