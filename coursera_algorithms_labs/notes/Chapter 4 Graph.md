
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