# 4.1 UNDIRECTED GRAPHS

### Definition of A graph is connected

A graph is connected if there is a path from every vertex to every other vertex in the graph.
A graph that is not connected consists of a set of connected components, which are maximal connected subgraphs.

### Definition of A tree & A spanning tree.

A tree is an acyclic connected graph. A disjoint set of trees is called a forest.
A spanning tree of a connected graph is a subgraph that contains all of that graph’s vertices and is a single tree.
A spanning forest of a graph is the union of spanning trees of its connected components.

Mathematical properties of trees are well-studied and intuitive, so we state them without proof.
For example, a graph G with V vertices is a tree if and only if it satisfies any of the following five conditions:

■ G has V-1 edges and no cycles.
■ G has V-1 edges and is connected.
■ G is connected, but removing any edge disconnects it.
■ G is acyclic, but adding any edge creates a cycle.
■ Exactly one simple path connects each pair of vertices in G.

Several of the algorithms that we consider find spanning trees and forests, and these
properties play an important role in their analysis and implementation.


# 4.2 DIRECTED GRAPHS

## Directed graphs
Digraph. Set of vertices connected pairwise by directed edges.

### Some digraph problems
- Path. Is there a directed path from s to t ?
- Shortest path. What is the shortest directed path from s to t ?
- Topological sort. Can you draw a digraph so that all edges point upwards?
- Strong connectivity. Is there a directed path between all pairs of vertices?
- Transitive closure. For which vertices v and w is there a path from v to w ?
- PageRank. What is the importance of a web page?


## Digraph search

### Reachability
Problem. Find all vertices reachable from s along a directed path.


## Depth-first search in digraphs
Same method as for undirected graphs.
- Every undirected graph is a digraph (with edges in both directions).
- DFS is a digraph algorithm.

DFS (to visit a vertex v)
- Mark v as visited.
- Recursively visit all unmarked vertices w pointing from v.

### Reachability application: program control-flow analysis

Every program is a digraph.
- Vertex = basic block of instructions (straight-line program).
- Edge = jump.
Dead-code elimination:  Find (and remove) unreachable code.
Infinite-loop detection: Determine whether exit is unreachable

### Reachability application: mark-sweep garbage collector
Every data structure is a digraph.
- Vertex = object.
- Edge = reference.
Roots: Objects known to be directly accessible by program (e.g., stack).
Reachable objects:  Objects indirectly accessible by program (starting at a root and following a chain of pointers).

Mark-sweep algorithm. [McCarthy, 1960]
- Mark: mark all reachable objects.
- Sweep: if object is unmarked, it is garbage (so add to free list).
Memory cost: Uses 1 extra mark bit per object (plus DFS stack).

### Depth-first search in digraphs summary
DFS enables direct solution of simple digraph problems.
- Reachability.
- Path finding.
- Topological sort.
- Directed cycle detection.
Basis for solving difficult digraph problems.
- 2-satisfiability.
- Directed Euler path.
- Strongly-connected components.


## Breadth-first search in digraphs
Same method as for undirected graphs.
- Every undirected graph is a digraph (with edges in both directions).
- BFS is a digraph algorithm.

### BFS (from source vertex s)
Put s onto a FIFO queue, and mark s as visited.
Repeat until the queue is empty:
- remove the least recently added vertex v
- for each unmarked vertex pointing from v:
  add to queue and mark as visited.

Proposition. 
BFS computes shortest paths (fewest number of edges) from s to all other vertices in a digraph in time proportional to E + V.

### Multiple-source shortest paths
Given a digraph and a set of source vertices, find shortest path from any vertex in the set to each other vertex.

Q. How to implement multi-source shortest paths algorithm?
A. Use BFS, but initialize by enqueuing all source vertices.


### Breadth-first search in digraphs application: web crawler

Goal. Crawl web, starting from some root web page, say www.princeton.edu.
Solution. [BFS with implicit digraph]
- Choose root web page as source s.
- Maintain a Queue of websites to explore.
- Maintain a SET of discovered websites.
- Dequeue the next website and enqueue
websites to which it links (provided you haven't done so before).

Q. Why not use DFS?

## Topological sort

DAG. Directed acyclic graph.
Definition. A directed acyclic graph (DAG) is a digraph with no directed cycles.

Topological sort. Given a digraph, put the vertices in order such that all its directed edges point from a vertex
earlier in the order to a vertex later in the order (or report that doing so is not possible).

A topological sort of a DAG is an ordering of its vertices such that for every directed edge v → w, vertex v comes before w in the ordering.


### Precedence scheduling
Goal. Given a set of tasks to be completed with precedence constraints, in which order should we schedule the tasks?
Digraph model. vertex = task; edge = precedence constraint.

Solution. DFS. What else?
- Run depth-first search.
- Return vertices in reverse postorder.

### Cycles in digraphs
Directed cycle detection. Does a given digraph have a directed cycle? 
If so, find the vertices on some such cycle, in order from some vertex back to itself.

### What is Reverse Postorder?
- Reverse the postorder list: the last finished vertex comes first.
- This has the effect of putting each vertex before all vertices it can reach — perfect for topological order!

### Proposition. Reverse DFS postorder of a DAG is a topological order.

DFS ensures that a node finishes after all nodes it depends on (i.e., all it points to).
Reversing this finish order puts dependencies after their prerequisites, which is exactly what topological sorting needs.

Pf. Consider any edge v→w. When dfs(v) is called:
- Case 1: dfs(w) has already been called and returned.
In this case, w is completely explored and already in the postorder list.
Since dfs(v) finishes after dfs(w), v is added after w in postorder.
So in reverse postorder, v appears before w
Thus, w was done before v.

- Case 2: dfs(w) has not yet been called.
dfs(w) will get called directly or indirectly by dfs(v) and will finish before dfs(v).
DFS will go deep into w's path, finish all those descendants, and only then come back and finish dfs(v).
So in postorder: w comes before v → in reverse: v comes before w
So again, dfs(w) will finish before dfs(v) finishes.
Thus, w will be done before v.

- Case 3: dfs(w) has already been called, but has not yet returned.
This means: while doing dfs(v), we encounter w, and w is still on the call stack.
That means we have an edge from v → w, but w is in the process of being explored (we haven't finished it yet).
Can’t happen in a DAG: function call stack contains
path from w to v, so v→w would complete a cycle.

### Proposition. A digraph has a topological order iff no directed cycle.

### Directed cycle detection application: 
- precedence scheduling
  Scheduling. Given a set of tasks to be completed with precedence
  constraints, in what order should we schedule the tasks?

Remark. A directed cycle implies scheduling problem is infeasible.

- cyclic inheritance:
  The Java compiler does cycle detection.
- spreadsheet recalculation
  Microsoft Excel does cycle detection (and has a circular reference toolbar!)

# 4.3 MINIMUM SPANNING TREES

Given. Undirected graph G with positive edge weights (connected).
Def. A spanning tree of G is a subgraph T that is both a tree
(connected and acyclic) and spanning (includes all of the vertices).
Goal. Find a min weight spanning tree.

## Applications
MST is fundamental problem with diverse applications.
- Dithering.
- Cluster analysis.
- Max bottleneck paths.
- Real-time face verification.
- LDPC codes for error correction.
- Image registration with Renyi entropy.
- Find road networks in satellite and aerial imagery.
- Reducing data storage in sequencing amino acids in a protein.
- Model locality of particle interactions in turbulent fluid flows.
- Autoconfig protocol for Ethernet bridging to avoid cycles in a network.
- Approximation algorithms for NP-hard problems (e.g., TSP, Steiner tree).
- Network design (communication, electrical, hydraulic, computer, road).

http://www.ics.uci.edu/~eppstein/gina/mst.html

### Euclidean MST
Given N points in the plane, find MST connecting them, where the distances
between point pairs are their Euclidean distances.

Brute force. Compute ~ N^2 / 2 distances and run Prim's algorithm.
Ingenuity. Exploit geometry and do it in ~ c N log N.

### clustering
k-clustering. Divide a set of objects classify into k coherent groups.
Distance function. Numeric value specifying "closeness" of two objects.
Goal. Divide into clusters so that objects in different clusters are far apart.

Applications.
- Routing in mobile ad hoc networks.
- Document categorization for web search.
- Similarity searching in medical image databases.
- Skycat: cluster 109 sky objects into stars, quasars, galaxies.

Single link. Distance between two clusters equals the distance between the two closest objects (one in each cluster).

Single-link clustering. Given an integer k, find a k-clustering that maximizes the distance between two closest clusters.

“Well-known” algorithm in science literature for single-link clustering:
- Form V clusters of one object each.
- Find the closest pair of objects such that each object is in a different cluster, and merge the two clusters.
- Repeat until there are exactly k clusters.

Observation. This is Kruskal's algorithm (stop when k connected components).

Alternate solution. Run Prim's algorithm and delete k–1 max weight edges.

### Dendrogram of cancers in human
Tumors in similar tissues cluster together.


## Underlying principles

To begin, we recall from Section
4.1 two of the defining properties of a tree:
■ Adding an edge that connects two vertices in a tree creates a unique cycle.
■ Removing an edge

### Definition of Cut.

A cut of a graph is a partition of its vertices into two nonempty disjoint
sets. A crossing edge of a cut is an edge that connects a vertex in one set with a vertex
in the other.

### Proposition J. ( Cut property)

Given any cut in an edge-weighted graph, the crossing edge of minimum weight is in the MST of the graph.
Proof:
Let e be the crossing edge of minimum weight and let T be the MST.
The proof is by contradiction: Suppose that T does not contain e.
Now consider the graph formed by adding e to T.
This graph has a cycle that contains e, and that cycle must contain at least one other crossing edge—say, f,
which has higher weight than e (since e is minimal and all edge weights are different).
We can get a spanning tree of strictly lower weight by deleting f and adding e, contradicting the assumed minimality of T.

## Greedy algorithm

### Proposition K. ( Greedy MST algorithm)

The following method colors black all edges in the MST of any connected edge-weighted
graph with V vertices: starting with all edges colored gray, find a cut with no black edges,
color its minimum-weight edge black, and continue until V-1 edges have been colored black.

Proof:
For simplicity, we assume in the discussion that the edge
weights are all different, though the proposition is still true when
that is not the case (see Exercise 4.3.5). By the cut property, any
edge that is colored black is in the MST. If fewer than V-1 edges
are black, a cut with no black edges exists (recall that we assume
the graph to be connected). Once V-1 edges are black, the black
edges form a spanning tree.

## Prim’s algorithm

to attach a new edge to a single growing tree at each step.
Start with any vertex as a single-vertex tree;
then add V-1 edges to it, always taking next (coloring black) the minimum weight edge
that connects a vertex on the tree to a vertex not yet on the tree
(a crossing edge for the cut defined by tree vertices).

### Proposition L.

Prim’s algorithm computes the MST of any connected edge-weighted graph.
Proof: Immediate from Proposition K. The growing tree
defines a cut with no black edges; the algorithm takes the
crossing edge of minimal weight, so it is successively coloring
edges black in accordance with the greedy algorithm.

## Kruskal’s algorithm

process the edges in order of their weight values (smallest to largest), taking for the MST
(coloring black) each edge that does not form a cycle with
edges previously added, stopping after adding V-1 edges
have been taken. The black edges form a forest of trees that
evolves gradually into a single tree, the MST.

### Proposition O.

Kruskal’s algorithm computes the MST of any edge-weighted connected graph.

Proof:
Immediate from Proposition K. If the next
edge to be considered does not form a cycle with black
edges, it crosses a cut defined by the set of vertices
connected to one of the edge’s vertices by tree edges
(and its complement). Since the edge does not create a
cycle, it is the only crossing edge seen so far, and since
we consider the edges in sorted order, it is a crossing
edge of minimum weight. Thus, the algorithm is successively
taking a minimal-weight crossing edge, in accordance
with the greedy algorithm.

# 4.4 SHORTEST PATHS

Goal. Find the shortest path from s to every other vertex.
Observation. A shortest-paths tree (SPT) solution exists. Why?

### Data structures for shortest paths
Consequence. Can represent the SPT with two vertex-indexed arrays:

-   distTo[v] is length of shortest path from s to v.
-   edgeTo[v] is last edge on shortest path from s to v.

### Edge relaxation.

Relax edge e = v→w.

to relax an edge v->w means to test whether the best known way from s to w is to go from s to v, then take the edge from v to w, and, if so, update our
data structures to indicate that to be the case.

-   distTo[v] is length of shortest known path from s to v.
-   distTo[w] is length of shortest known path from s to w.
-   edgeTo[w] is last edge on shortest known path from s to w. 

If e = v→w gives shorter path to w through v, update both distTo[w] and edgeTo[w].

```JAVA
private void relax(DirectedEdge e)
{
  int v = e.from(), w = e.to();
  // if that value is not smaller than  distTo[w],  we say the edge is ineligible,
  // if the value is smaller than  distTo[w],   we say the edge is eligible,
  if (distTo[w] > distTo[v] + e.weight())
    {
        distTo[w] = distTo[v] + e.weight(); // update the shortest distance to w
        edgeTo[w] = e;  // update the edge
    }
}

```

## Theoretical basis for shortest-paths algorithms.

### Shortest-paths Optimality conditions

The following proposition shows an equivalence between the global condition that the distances are shortest-paths distances, and the local condition
that we test to relax an edge.

Proposition. Let G be an edge-weighted digraph. Then distTo[] are the shortest path distances from s iff:

-   distTo[s] = 0.
-   For each vertex v, distTo[v] is the length of some path from s to v.
-   For each edge e = v→w, distTo[w] ≤ distTo[v] + e.weight().

Pf. ⇐ [ necessary ]
- Suppose that distTo[w] > distTo[v] + e.weight() for some edge e = v→w.
- Then, e gives a path from s to w (through v) of length less than distTo[w].

## Generic shortest-paths algorithm

Initialize distTo[s] = 0 and distTo[v] = ∞ for all other vertices.
Repeat until optimality conditions are satisfied:
-   Relax any edge.


Proposition. Generic algorithm computes SPT (if it exists) from s.
Pf sketch.
- Throughout algorithm, distTo[v] is the length of a simple path from s to v (and edgeTo[v] is last edge on path).
- Each successful relaxation decreases distTo[v] for some v.
- The entry distTo[v] can decrease at most a finite number of times.

### Efficient implementations. How to choose which edge to relax?

Ex 1. Dijkstra's algorithm (nonnegative weights).
Ex 2. Topological sort algorithm (no directed cycles).
Ex 3. Bellman-Ford algorithm (no negative cycles).

## Dijkstra's algorithm

-   Consider vertices in increasing order of distance from s  (non-tree vertex with the lowest distTo[] value).
-   Add vertex to tree and relax all edges pointing from that vertex.

Proposition. Dijkstra's algorithm computes a SPT in any edge-weighted digraph with nonnegative weights.

### Computing spanning trees in graphs
- Dijkstra’s algorithm seem familiar?
    - Prim’s algorithm is essentially the same algorithm.
    - Both are in a family of algorithms that compute a graph’s spanning tree.

- Main distinction: Rule used to choose next vertex for the tree.
 - Prim’s: Closest vertex to the tree (via an undirected edge).
 - Dijkstra’s: Closest vertex to the source (via a directed path).
  Note: DFS and BFS are also in this family of algorithms

### Dijkstra's algorithm: which priority queue

Bottom line.
- Array implementation optimal for dense graphs.
- Binary heap much faster for sparse graphs.
- 4-way heap worth the trouble in performance-critical situations.
- Fibonacci heap best in theory, but not worth implementing.

## Acyclic edge-weighted digraphs
Q. Suppose that an edge-weighted digraph has no directed cycles.  Is it easier to find shortest paths than in a general digraph?
A. Yes!
vertex relaxation, in combination with topological sorting
- Solves the single-source problem in linear time
- Handles negative edge weights
- Solves related problems, such as finding longest paths.


- Consider vertices in topological order.
- Relax all edges pointing from that vertex.

Proposition. Topological sort algorithm computes SPT in any edgeweighted DAG in time proportional to E + V.
(edge weights can be negative!)


### Content-aware resizing: Seam carving.
[Avidan and Shamir] Resize an image without distortion for display on cell phones and web browsers.
To find vertical seam:
- Grid DAG: vertex = pixel; edge = from pixel to 3 downward neighbors.
- Weight of pixel = energy function of 8 neighboring pixels.
- Seam = shortest path (sum of vertex weights) from top to bottom.

To remove vertical seam:
- Delete pixels on seam (one in each row).


## Longest paths in edge-weighted DAGs: application
Formulate as a shortest paths problem in edge-weighted DAGs.
- Negate all weights.
- Find shortest paths.
- Negate weights in result.

### Parallel job scheduling. 
Given a set of jobs with durations and precedence constraints, schedule the jobs (by finding a start time for each) 
so as to achieve the minimum completion time, while respecting the constraints.


#### Critical path method(CPM)
CPM. To solve a parallel job-scheduling problem, create edge-weighted DAG:
- Source and sink vertices.
- Two vertices (begin and end) for each job.
- Three edges for each job.
  - begin to end (weighted by duration)
  - source to begin (0 weight)
  - end to sink (0 weight)
- One edge for each precedence constraint (0 weight).

CPM. Use longest path from the source to schedule each job.

## Negative weights

### Shortest paths with negative weights: failed attempts
Dijkstra. Doesn’t work with negative edge weights.
Re-weighting. Add a constant to every edge weight doesn’t work
Conclusion. Need a different algorithm.

### Negative cycles
Def. A negative cycle is a directed cycle whose sum of edge weights is
negative.

Proposition. A SPT exists iff no negative cycles.


### Bellman-Ford algorithm
Initialize distTo[s] = 0 and distTo[v] = ∞ for all other vertices.
Repeat V times:
- Relax each edge.

```java
for (int i = 0; i < G.V(); i++)
for (int v = 0; v < G.V(); v++)
for (DirectedEdge e : G.adj(v))
relax(e);
```
Proposition. Dynamic programming algorithm computes SPT in any edgeweighted
digraph with no negative cycles in time proportional to E × V.
Pf idea. After pass i, found shortest path containing at most i edges.

#### Bellman-Ford algorithm: practical improvement
Observation. If distTo[v] does not change during pass i, no need to relax any edge pointing from v in pass i+1.
FIFO implementation. Maintain queue of vertices whose distTo[] changed.
Overall effect.
- The running time is still proportional to E × V in worst case.
- But much faster than that in practice.

### Single source shortest-paths implementation: cost summary
Remark 1. Directed cycles make the problem harder.
Remark 2. Negative weights make the problem harder.
Remark 3. Negative cycles makes the problem intractable.

### Finding a negative cycle

### Negative cycle application: arbitrage detection
Observation. If there is a negative cycle, Bellman-Ford gets stuck in loop,
updating distTo[] and edgeTo[] entries of vertices in the cycle.

Proposition. If any vertex v is updated in phase V, there exists a negative
cycle (and can trace back edgeTo[v] entries to find it).

In practice. Check for negative cycles more frequently.

# 6.4 MAXIMUM FLOW
Problem. Given table of exchange rates, is there an arbitrage opportunity?
Ex. $

## Mincut problem & Maxflow problem

-   Input. A weighted digraph, source vertex s, and target vertex t.
-   Mincut problem. Find a cut of minimum capacity.
-   Maxflow problem. Find a flow of maximum value.

Definition. A flow network is an edge-weighted digraph with positive edge weights
(which we refer to as capacities). An st-flow network has two identified vertices, a
source s and a sink t.

Definition. An an st-flow network is a set of nonnegative values associated
with each edge, which we refer to as edge flows. We say that a flow is feasible if it
satisfies the condition that no edge’s flow is greater than that edge’s capacity and the
local equilibrium condition that the every vertex’s netflow is zero (except s and t).

## Maxflow problem

Input. An edge-weighted digraph, source vertex s, and target vertex t.
(each edge has a positive capacity)

### Def. st-flow (flow)

An st-flow (flow) is an assignment of values to the edges such that:

-   Capacity constraint: 0 ≤ edge's flow ≤ edge's capacity.
-   Local equilibrium: inflow = outflow at every vertex (except s and t).

### Def: the st-flow value

The value of a flow is the inflow at t.

-   we assume no edge points to s or from t
-   We refer to the sink’s inflow as the st-flow value.

### Def: Maximum st-flow (maxflow) problem.

Given an st-flow network, find an st-flow such that no other flow from s to t has a larger value.

## Ford-Fulkerson algorithm

## Idea: increase flow along augmenting paths

Initialization. Start with 0 flow.

Augmenting path. Find an undirected path from s to t such that:

-   Can increase flow on forward edges (not full).
-   Can decrease flow on backward edge (not empty).

Termination. All paths from s to t are blocked by either a

-   Full forward edge.
-   Empty backward edge.

## Ford-Fulkerson algorithm

Start with 0 flow.
While there exists an augmenting path:

-   find an augmenting path
-   compute bottleneck capacity
-   increase flow on that path by bottleneck capacity

Remarkably (under certain technical conditions about numeric properties of the flow),
this method always finds a maxflow, no matter how we choose the paths. Like

Questions.

-   How to compute a mincut? Easy. ✔
-   How to find an augmenting path? BFS works well.
-   If FF terminates, does it always compute a maxflow? Yes. ✔
-   Does FF always terminate? If so, after how many augmentations?
    yes, provided edge capacities are integers (or augmenting paths are chosen carefully)
    requires clever analysis

## Mincut problem

Input. An edge-weighted digraph, source vertex s, and target vertex t.
(each edge has a positive capacity)

### Def: st-cut (cut)

A st-cut (cut) is a partition of the vertices into two disjoint sets, with s in one set A and t in the other set B.

-   a cut set
    We sometimes refer to the set of crossing st-edges as a cut set.

-   st-edge and ts-edge
    Each crossing edge corresponding to an st-cut is either an st-edge that goes from a vertex
    in the set containing s to a vertex in the set containing t, or a ts-edge that goes in
    the other direction.

-   The capacity of the cut
    Its capacity is the sum of the capacities of the edges from A to B.

-   the flow across an st-cut
    the difference between the sum of the flows in that cut’s st-edges and the sum of the flows in that cut’s ts-edges.

### Minimum st-cut (mincut) problem.

Find a cut of minimum capacity.

## maxflow-mincut theorem

### Def: The net flow

The net flow across a cut (A, B) is the sum of the flows on its edges from A to B minus the sum of the flows on its edges from B to A.

### Flow-value lemma (Relationship between flows and cuts)

Let f be any flow and let (A, B) be any cut. Then, the net flow across (A, B) equals the value of f.

Pf. By induction on the size of B.

-   Base case: B = { t }.
-   Induction step: remains true by local equilibrium when moving any vertex from A to B.

### Corollary (local equilibrium in an st-flow implies global equilibrium)

Outflow from s = inflow to t = value of flow.

### Weak duality.

Let f be any flow and let (A, B) be any cut. Then, the value of the flow ≤ the capacity of the cut.

### Maxflow-mincut theorem

Augmenting path theorem. A flow f is a maxflow iff no augmenting paths.
Maxflow-mincut theorem. Value of the maxflow = capacity of mincut.

The following three conditions are equivalent for any flow f :
i. There exists a cut whose capacity equals the value of the flow f.
ii. f is a maxflow.
iii. There is no augmenting path with respect to f.

[ i -> ii ]

-   Suppose that (A, B) is a cut with capacity equal to the value of f.
-   Then, by Weak duality, the value of any flow f ' ≤ capacity of (A, B) = value of f.
-   Thus, f is a maxflow.

[ ii -> iii ] We prove contrapositive: ~iii -> ~ii.

-   Suppose that there is an augmenting path with respect to f.
-   Can improve flow f by sending flow along this path.
-   Thus, f is not a maxflow.

[ iii -> i ]
Suppose that there is no augmenting path with respect to f.

-   Let (A, B) be a cut where A is the set of vertices connected to s by an
    undirected path with no full forward or empty backward edges.
-   By definition, s is in A; since no augmenting path, t is in B.
-   Capacity of cut = net flow across cut (since forward edges are full and the backward edges are empty)
    = value of flow f. (flow-value lemma)

## Ford-Fulkerson algorithm with integer capacities

Important special case. Edge capacities are integers between 1 and U.

Invariant. The flow is integer-valued throughout Ford-Fulkerson.
Pf. [by induction]

-   Bottleneck capacity is an integer.
-   Flow on an edge increases/decreases by bottleneck capacity.

Proposition. Number of augmentations ≤ the value of the maxflow.
Pf. Each augmentation increases the value by at least 1.

### Integrality property

When capacities are integers, there exists an integer-valued maxflow, and the Ford-Fulkerson algorithm finds it.
Proof: Each augmenting path increases the flow by a positive integer (the minimum
of the unused capacities in the forward edges and the flows in the backward
edges, all of which are always positive integers).

Augmenting path theorem. A flow f is a maxflow iff no augmenting paths.
Maxflow-mincut theorem. Value of the maxflow = capacity of mincut.

### To compute mincut (A, B) from maxflow f :

-   By augmenting path theorem, no augmenting paths with respect to f.
-   Compute A = set of vertices connected to s by an undirected path with no full forward or empty backward edges.

Given a maxflow f in a flow network, what is the order of growth of the running time to compute a mincut? V+E
The algorithm is to find all of the vertices reachable from s using only forward edges that aren't full or backwards edges that aren't empty.
This can be done in linear time using either breadth-first search for depth-first search.

## How to choose augmenting paths?

### Definition. Residual network

Given a st-flow network and an st-flow, the residual network for the flow has the same vertices as the original and one or two edges in the residual network for each edge in the original, defined as follows:

For each edge e from v to w in the original, let fe be its flow and ce its capacity.

-   If fe is positive, include an edge w->v in the residual with capacity fe ;
-   if fe is less than ce, include an edge v->w in the residual with capacity ce - fe .
