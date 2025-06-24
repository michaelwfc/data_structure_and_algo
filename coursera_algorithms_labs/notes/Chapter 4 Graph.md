
# 4.1 UNDIRECTED GRAPHS
### Definition of A graph is connected 
A graph is connected if there is a path from every vertex to every other vertex in the graph. 
A graph that is not connected consists of a set of connected components, which are maximal connected subgraphs.


### Definition of A tree  & A spanning tree. 
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



# 4.3 MINIMUM SPANNING TREES

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
Consequence. Can represent the SPT with two vertex-indexed arrays:
- distTo[v] is length of shortest path from s to v.
- edgeTo[v] is last edge on shortest path from s to v.


### Edge relaxation.
to relax an edge v->w means to test whether the best known way from s to w is to go from s to v, then take the edge from v to w, and, if so, update our
data structures to indicate that to be the case. 

Relax edge e = v→w.
- distTo[v] is length of shortest known path from s to v.
- distTo[w] is length of shortest known path from s to w.
- edgeTo[w] is last edge on shortest known path from s to w.
- If e = v→w gives shorter path to w through v, update both distTo[w] and edgeTo[w].

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

Proposition. Let G be an edge-weighted digraph.
Then distTo[] are the shortest path distances from s iff:
- distTo[s] = 0.
- For each vertex v, distTo[v] is the length of some path from s to v.
- For each edge e = v→w, distTo[w] ≤ distTo[v] + e.weight().


## Generic shortest-paths algorithm
Initialize distTo[s] = 0 and distTo[v] = ∞ for all other vertices.
Repeat until optimality conditions are satisfied:
- Relax any edge.

### Efficient implementations. How to choose which edge to relax?
  Ex 1. Dijkstra's algorithm (nonnegative weights).
  Ex 2. Topological sort algorithm (no directed cycles).
  Ex 3. Bellman-Ford algorithm (no negative cycles).
  

## Dijkstra's algorithm

- Consider vertices in increasing order of distance from s
(non-tree vertex with the lowest distTo[] value).
- Add vertex to tree and relax all edges pointing from that vertex.
  
Proposition. Dijkstra's algorithm computes a SPT in any edge-weighted digraph with nonnegative weights.


## Edge-weighted DAGs



## Negative weights


# 6.4 MAXIMUM FLOW

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
Capacity constraint: 0 ≤ edge's flow ≤ edge's capacity.
Local equilibrium: inflow = outflow at every vertex (except s and t).

### Def: the st-flow value
The value of a flow is the inflow at t. 
- we assume no edge points to s or from t
- We refer to the sink’s inflow as the st-flow value.

### Def: Maximum st-flow (maxflow) problem. 
Given an st-flow network, find an st-flow such that no other flow from s to t has a larger value.


## Ford-Fulkerson algorithm

### Idea: increase flow along augmenting paths
Initialization. Start with 0 flow.
#### Def: Augmenting path.
Find an undirected path from s to t such that:
- Can increase flow on forward edges (not full).
- Can decrease flow on backward edge (not empty).

Termination. All paths from s to t are blocked by either a
- Full forward edge.
- Empty backward edge.








## Mincut problem
Input. An edge-weighted digraph, source vertex s, and target vertex t.
(each edge has a positive capacity)

### Def: st-cut (cut)
A st-cut (cut) is a partition of the vertices into two disjoint sets, with s in one set A and t in the other set B.
We sometimes refer to the set of crossing st-edges as a cut set.

### Def: the capacity of the cut
Its capacity is the sum of the capacities of the edges from A to B.


### Minimum st-cut (mincut) problem. 
Find a cut of minimum capacity.




## maxflow-mincut theorem

### Def: The net flow
The net flow across a cut (A, B) is the sum of the flows on its edges from A to B minus the sum of the flows on its edges from from B to A.


### Flow-value lemma (Relationship between flows and cuts)
Let f be any flow and let (A, B) be any cut. Then, the net flow across (A, B) equals the value of f.

Pf. By induction on the size of B.
- Base case: B = { t }.
- Induction step: remains true by local equilibrium when moving any vertex from A to B.

### Corollary (local equilibrium in an st-flow implies global equilibrium)
Outflow from s = inflow to t = value of flow.


### Weak duality. 
Let f be any flow and let (A, B) be any cut. Then, the value of the flow ≤ the capacity of the cut.

### Maxflow-mincut theorem
 The following three conditions are equivalent for any flow f :
i. There exists a cut whose capacity equals the value of the flow f.
ii. f is a maxflow.
iii. There is no augmenting path with respect to f.

[ i -> ii ]
- Suppose that (A, B) is a cut with capacity equal to the value of f.
- Then, the value of any flow f ' ≤ capacity of (A, B) = value of f.
- Thus, f is a maxflow.

[ ii -> iii ] We prove contrapositive: ~iii ->  ~ii.
- Suppose that there is an augmenting path with respect to f.
- Can improve flow f by sending flow along this path.
- Thus, f is not a maxflow.


[ iii -> i ]
Suppose that there is no augmenting path with respect to f.
- Let (A, B) be a cut where A is the set of vertices connected to s by an
undirected path with no full forward or empty backward edges.
- By definition, s is in A; since no augmenting path, t is in B.
- Capacity of cut = net flow across cut (since forward edges are full and the backward edges are empty)
   = value of flow f. (flow-value lemma)

### Corollary. ( Integrality property) 
When capacities are integers, there exists an integer-valued maxflow, and the Ford-Fulkerson algorithm finds it.
Proof: Each augmenting path increases the flow by a positive integer (the minimum
of the unused capacities in the forward edges and the flows in the backward
edges, all of which are always positive integers).


Augmenting path theorem. A flow f is a maxflow iff no augmenting paths.
Maxflow-mincut theorem. Value of the maxflow = capacity of mincut.


### To compute mincut (A, B) from maxflow f :
- By augmenting path theorem, no augmenting paths with respect to f.
- Compute A = set of vertices connected to s by an undirected path with no full forward or empty backward edges. 

Given a maxflow f  in a flow network, what is the order of growth of the running time to compute a mincut? V+E
The algorithm is to find all of the vertices reachable from s using only forward edges that aren't full or backwards edges that aren't empty. 
This can be done in linear time using either breadth-first search for depth-first search.


### Definition.  Residual network
Given a st-flow network and an st-flow, the residual network for the
flow has the same vertices as the original and one or two edges in the residual network
for each edge in the original, defined as follows: 
For each edge e from v to w in the original, let fe be its flow and ce its capacity. 
If fe is positive, include an edge w->v in the residual with capacity fe ; 
and if fe is less than ce, include an edge v->w in the residual with capacity ce - fe .



## Ford-Fulkerson algorithm
Start with 0 flow.
While there exists an augmenting path:
- find an augmenting path
- compute bottleneck capacity
- increase flow on that path by bottleneck capacity

Questions.
- How to compute a mincut? Easy. ✔
- How to find an augmenting path? BFS works well.
- If FF terminates, does it always compute a maxflow? Yes. ✔
- Does FF always terminate? If so, after how many augmentations?
yes, provided edge capacities are integers (or augmenting paths are chosen carefully) 
requires clever analysis
