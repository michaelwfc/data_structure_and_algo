package chap5string;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stack;

public class NFA {
    private char[] re;
    private Digraph G;
    private int M;

    public NFA(String regexp) {
        M = regexp.length();
        re = regexp.toCharArray();
        G = buildEpsilonTransitionDigraph(regexp);
    }

    public boolean recognize(String txt) {
        // all possible state reachable from start
        Bag<Integer> pc = new Bag<>();
        // states reachable from start by ε-transitions
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v)) pc.add(v);
        }
        // scan the text
        for (int i = 0; i < txt.length(); i++) {
            // set of states reachable after scanning past txt.charAt(i)
            Bag<Integer> match = new Bag<Integer>();
            for (int v : pc) {
                if (v == M) continue;
                if ((re[v] == txt.charAt(i)) || re[v] == '.')
                    match.add(v + 1); // put the next state to match
            }
            // from current match state find all the reachable state by ε-transitions
            dfs = new DirectedDFS(G, match);
            pc = new Bag<>(); // create new all possible state reachable
            //add state reachable follow ε-transitions
            for (int v = 0; v < G.V(); v++)
                if (dfs.marked(v)) pc.add(v);
        }

        for (int v : pc)
            if (v == M) return true;
        return false;
    }

    /*
     * Goal. Write a program to build the ε-transition digraph.
     *
     * States. Include a state for each symbol in the RE, plus an accept state.
     * Concatenation. Add match-transition edge from state corresponding to characters in the alphabet to next state.
     * - Alphabet. A B C D
     * - Metacharacters. ( ) . * |
     * Parentheses. Add ε-transition edge from parentheses to next state.
     * Closure. Add three ε-transition edges for each * operator
     * Or. Add two ε-transition edges for each | operator.
     *
     * Solution. Maintain a stack.
     * ( symbol: push ( onto stack.
     * | symbol: push | onto stack.
     * ) symbol: pop corresponding ( and any intervening |;  add ε-transition edges for closure/or.
     *
     * */
    public Digraph buildEpsilonTransitionDigraph(String regexp) {
        Digraph G = new Digraph(M + 1);
        Stack<Integer> ops = new Stack<>();
        for (int i = 0; i < M; i++) {
            int lp = i;
            if (re[i] == '(' || re[i] == '|') ops.push(i); // left parentheses and |
            else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    // 2-way or
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                } else lp = or;
            }
            // closure (needs 1-character lookahead)
            if (i < M - 1 && re[i + 1] == '*') {
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            // meta symbols
            if (re[i] == '(' || re[i] == '*' || re[i] == ')')
                G.addEdge(i, i + 1);
        }
        return G;
    }

}

