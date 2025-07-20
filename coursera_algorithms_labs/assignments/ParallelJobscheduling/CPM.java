/** Parallel job scheduling.
 * (Page 663: Longest paths in edge-weighted DAGs: application)
 * Assumption: we have sufficient processors to perform as many jobs as possible
 * Given a set of jobs with durations and precedence constraints, schedule the jobs (by finding a start time for each)
 * so as to achieve the minimum completion time, while respecting the constraints.
 *
 *  Scheduling problems Section 4.2 (page 574):
 *  Assumption: Implicit in the model of Section 4.2 is a single processor
 *  we schedule the jobs in topological order and the total time required is the total duration of the jobs.
 *
 * An approach known as the critical path method(CPM) demonstrates that the problem is equivalent to a longest-paths  problem in an edge-weighted DAG.
 * uses AcyclicLP to solve it, then prints the job start times and schedule finish time
 *
 * Critical path method(CPM)
 * To solve a parallel job-scheduling problem, create edge-weighted DAG:
 * - Source and sink vertices.
 * - Two vertices (begin and end) for each job.
 * - Modeling each job:
 *    Three edges for each job.
 *   – begin to end (weighted by duration)
 *   – source to begin (0 weight)
 *   – end to sink (0 weight)
 * - Modeling precedence:
 *    One edge for each precedence constraint (0 weight).
 * CPM： Use longest path from the source to schedule each job.
 *
 *
 * Shortest path: finds the minimal cost/time to reach a node.
 * In a precedence‑constrained schedule you’re not trying to find a “cheapest” way to get from a start node to an end node (that would be a shortest‑path problem).
 *
 *
 * Longest path in a DAG:
 * Instead, becaue each job has  precedence‑constrained,  you’re trying to figure out how long you have to wait before each job can start—namely, you must wait for all of its prerequisites to finish.
 * finds the earliest finish time in scheduling problems, because waiting for all prerequisite jobs to finish may delay start time.
 *
 * The earliest time you can start job_j is the maximum (over all of its predecessors i)
 *
* */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.DirectedEdge;
//import edu.princeton.cs.algs4.EdgeWeightedDigraph;
//import edu.princeton.cs.algs4.AcyclicLP;
import chap4graphs.DirectedEdge;
import chap4graphs.EdgeWeightedDigraph;
import chap4graphs.AcyclicLP;

public class CPM {

    public static void main(String[] args) {
//        int N = StdIn.readInt(); // N jobs
        String filename= "algs4-data/jobsPC.txt";
        In in = new In(filename);
        int N = in.readInt();

        // each job has 2 vertices + source+sink
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(2*N + 2);

        int s=2*N, t= 2*N+1; // the index for source and sink

        // build the  graph
        String[] headline = in.readLine().split("\\s+");
        for (int i = 0; i < N; i++) {
//            String[] a = StdIn.readLine().split("\\s+");
            String[] a = in.readLine().split("\\s+");

            double duration = Double.parseDouble(a[0]);
            G.addEdge(new DirectedEdge(i, i+N, duration)); // begin to end for each job
            G.addEdge(new DirectedEdge(s, i, 0));// source to begin
            G.addEdge(new DirectedEdge(i+N, t, 0)); // end to sink

            int successorNum= Integer.parseInt(a[1]);
            for (int j = 2; j < a.length; j++) {
                int successor = Integer.parseInt(a[j]); // the successor jobs must start after job i completed
                G.addEdge(new DirectedEdge(i+N, successor, 0)); // end to successor
            }
        }

        AcyclicLP lp = new AcyclicLP(G, s);

        StdOut.println("Start times:");
        for(int i=0;i<N;i++)
            StdOut.printf("%4d: %5.1f\n", i, lp.distTo(i));
        StdOut.printf("Finish time: %5.1f\n", lp.distTo(t));
    }
}
