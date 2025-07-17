
# Reductions
## Def. 
Problem X reduces to problem Y if you can use an algorithm that
solves Y to help solve X.

## Linear-time reductions

Def. Problem X linear-time reduces to problem Y if X can be solved with:
- Linear number of standard computational steps.
- Constant number of calls to Y.

## Lower bound for convex hull

### Proposition. In quadratic decision tree model, any algorithm for sorting  N integers requires Ω(N log N) steps.

### Proposition. Sorting linear-time reduces to convex hull


### Establishing lower bounds

Establishing lower bounds through reduction is an important tool in guiding algorithm design efforts.
Q. How to convince yourself no linear-time convex hull algorithm exists?
A1. [hard way] Long futile search for a linear-time algorithm.
A2. [easy way] Linear-time reduction from sorting.


Pf.
- Region { x : x2 ≥ x } is convex ⇒ all points are on hull.
- Starting at point with most negative x, counterclockwise order of hull points yields integers in ascending order.


## Decision Tree Model
The quadratic decision tree model is a restricted computational model used in theoretical computer science to analyze the complexity of algorithms — particularly geometric problems and algebraic computation.

### What is a Decision Tree Model?
A decision tree model analyzes an algorithm as a tree of decisions based on input values.

- At each node, a decision (like a comparison or algebraic test) is made.
- The path through the tree depends on the results of these decisions.
- The height of the tree gives a lower bound on the number of operations needed in the worst case.

### What is the Quadratic Decision Tree Model?
In the quadratic decision tree model, the algorithm is restricted to making decisions based only on evaluating polynomials of degree ≤ 2 on the input values.
That is, each decision is of the form:

```css
Is P(x₁, x₂, ..., xₙ) > 0?
```
Where P is a degree-2 polynomial (quadratic function).

This is a generalization of:
- Linear decision trees, where P is linear (degree 1)
- Comparison trees, where decisions are based on comparisons like xᵢ < xⱼ


## what is "Proposition. Sorting linear-time reduces to convex hull." meaning?
The proposition "Sorting linear-time reduces to convex hull" is a fundamental statement in computational complexity theory that establishes a relationship between the problems of sorting numbers and computing convex hulls. Here's a breakdown of its meaning and implications:

1. Core Concept: Linear-Time Reduction

   • Reduction means transforming an instance of Problem A (sorting) into an instance of Problem B (convex hull) such that solving Problem B also solves Problem A.

   • Linear-time specifies that this transformation can be done efficiently, in time proportional to the input size (O(n)).

   • Thus, if we can compute the convex hull of a set of points efficiently, we can also sort numbers efficiently using the convex hull as a "subroutine."

2. How the Reduction Works

   • Mapping Sorting to Convex Hull Input:

   ◦ Given a list of numbers to sort (e.g., [4, 1, 3, 2]), map each number x_i to a point \((x_i, x_i^2)\) on a parabola (e.g., x=4 → \((4, 16)\)). This takes O(n) time .

   ◦ The parabola y = x^2 is used because its convex hull inherently reflects the sorted order of the original numbers.

   • Convex Hull Output as Sorted Order:

   ◦ The convex hull of these parabolic points will be a polygonal chain wrapping the parabola. The leftmost and rightmost points (min and max) form a base edge. The remaining hull edges connect points in strictly increasing or decreasing x-order (due to the parabola's convexity) .

   ◦ Traversing these edges (e.g., left-to-right along the lower hull) directly yields the sorted sequence of numbers in O(n) time .

3. Implications for Problem Complexity

   • Lower Bound Transfer: Sorting has a known lower bound of Ω(n log n) in the comparison model. Since sorting reduces to convex hull in linear time:

   ◦ If a convex hull algorithm existed with complexity better than Ω(n log n) (e.g., O(n)), we could sort in O(n) time by extension—contradicting sorting's lower bound.

   ◦ Thus, convex hull algorithms must also have a lower bound of Ω(n log n). This proves that algorithms like Graham's scan (O(n log n)) are asymptotically optimal .

   • Equivalence in Hardness: The reduction shows that convex hull is at least as hard as sorting under linear-time reductions, linking geometric and combinatorial problems.

4. Practical Insight: Why Parabolas?

   • The parabola y = x^2 ensures all input points lie on a convex curve. Consequently:

   ◦ The convex hull vertices correspond exactly to the input points in sorted order.

   ◦ No three points are collinear, avoiding degenerate cases during hull construction .

5. Example Workflow

   Sorting Problem → Convex Hull Problem
   Input: [3, 1, 4, 2] → Points: [(1,1), (2,4), (3,9), (4,16)]
   Convex Hull Output → Sorted Output
   Hull edges: (1,1)→(2,4)→(3,9)→(4,16) → Sequence: [1, 2, 3, 4]

Summary

This proposition demonstrates that sorting can be solved by leveraging a convex hull algorithm, with the transformation between problems taking linear time. Consequently, it proves that the computational complexity of convex hull is bounded below by that of sorting (Ω(n log n)), establishing convex hull as a problem of inherent geometric and combinatorial significance. This reduction is a classic tool for teaching problem hardness and algorithm optimality in computational geometry .


## Classifying problems

Desiderata. Problem with algorithm that matches lower bound.
Ex. Sorting and convex hull have complexity N log N.

Desiderata'. Prove that two problems X and Y have the same complexity.
- First, show that problem X linear-time reduces to Y.
- Second, show that Y linear-time reduces to X.
- Conclude that X and Y have the same complexity.