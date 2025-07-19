/**
 * we create a flow network and solve a maxflow problem in it.
 * In the network, feasible integral flows correspond to outcomes of the remaining schedule.
 * There are vertices corresponding to teams (other than team x) and to remaining divisional games (not involving team x).
 * Intuitively, each unit of flow in the network corresponds to a remaining game.
 * As it flows through the network from s to t, it passes from  a game vertex, say between teams i and j,
 * then through one of the team vertices i or j, classifying this game as being won by that team.
 * <p>
 * <p>
 * - We connect an artificial source vertex s to each game vertex i-j and set its capacity to g[i][j].
 * If a flow uses all g[i][j] units of capacity on this edge, then we interpret this as playing all of these games, with the wins distributed between the team vertices i and j.
 * - We connect each game vertex i-j with the two opposing team vertices to ensure that one of the two teams earns a win.
 * We do not need to restrict the amount of flow on such edges.
 * - Finally, we connect each team vertex to an artificial sink vertex t.
 * We want to know if there is some way of completing all the games so that team x ends up winning at least as many games as team i.
 * Since team x can win as many as w[x] + r[x] games, we prevent team i from winning more than that many games in total,
 * by including an edge from team vertex i to the sink vertex with capacity w[x] + r[x] - w[i].
 * <p>
 * If all edges in the maxflow that are pointing from s are full, then this corresponds to assigning winners to
 * all of the remaining games in such a way that no team wins more games than x.
 * If some edges pointing from s are not full, then there is no scenario in which team x can win the division
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Objects;
import java.util.ArrayList;


public class BaseballElimination {
    private final int n; //  number of teams
    private final String[] teams; // index to team name
    private final Map<String, Integer> teamIndex;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] games;  // games[i][j] = # games between team i and j

    private FordFulkerson fordFulkerson ;


    /**
     * create a baseball division from given filename in format specified below
     * Input format.
     * The input format is
     * - the number of teams in the division n followed by one line for each team.
     * - Each line contains the team name (with no internal whitespace characters), the number of wins, the number of losses, the number of remaining games,
     * and the number of remaining games against each team in the division.
     */
    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = in.readInt();
        teams = new String[n];
        teamIndex = new HashMap<String, Integer>();
        wins = new int[n];
        losses = new int[n];
        remaining = new int[n];
        games = new int[n][n];

        for (int i = 0; i < n; i++) {
            String name = in.readString();
            teams[i] = name;
            teamIndex.put(name, i);
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < n; j++)
                games[i][j] = in.readInt();
        }
    }

    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return List.of(teams);
    }

    // number of wins for given team
    public int wins(String team) {
        return wins[teamIndex.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return losses[teamIndex.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return remaining[teamIndex.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return games[teamIndex.get(team1)][teamIndex.get(team2)];
    }

    /** is given team eliminated?
     *
     * 1. for team x : build a flow network
     * 2. build the flow network
     * 3. compute the maximum flow  f from s to t
     *
     * W = Sum(g[i][j])(i<j,i,j!=x) : the total number of games among the other teams
     * so because the max of all capacities from source -> game = W, the max flow f of this flow network is W.
     * - if f<W: some games cannot be assigned without some team exceeding x's maximum, so x is eliminated
     * - if f=W: then it is possible to assign winners to every remaining game so that no team exceeds x's maximum wins, x is not eliminated
     * */
    public boolean isEliminated(String team) {

        int x = teamIndex.get(team);
        boolean isTrivial = compareOtherWins(team);

        // trivial case
        if (isTrivial)return true;

        // build a flow network, source node s (0), sink node t(index num-1)
        // initial  num = s,t,  game nodes: n(n-1)/2, team nodes:  n-1
        int num = 1 + n * n + n ;
        FlowNetwork flowNetwork = buildFlowNetwork(team, num);

        fordFulkerson = new FordFulkerson(flowNetwork, 0, num-1);

        double maxFlowValue = fordFulkerson.value();

        // calculate all the remain games
        double W = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++)
                if (i != x && j != x)
                    W += games[i][j];
        }
        if (maxFlowValue == W) return false;
        else if (maxFlowValue < W) return true;
        else throw new RuntimeException("max flow value should not larger than W");
    }

    private boolean compareOtherWins(String team) {
        int maxWins = wins(team) + remaining(team);
        for (String t : teams) {
            if (wins(t) > maxWins && !Objects.equals(t, team)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Flow network:
     * source node s, sink node t
     * game nodes: i-j (i<j, i!=x, j!=x)
     * team nodes: i (i!=x):
     *
     * edges:
     * Source -> game(i,j) edge :  If a flow uses all g[i][j] units of capacity on this edge, then we think of all these games as being played, and the wins are distributed between team nodes i and j.
     * capacity g[i][j] , the number of remaining games between team i and j , this enforce we must schedule all the remaining games when calculate the max flow
     *
     * game(i,j) -> team i edge:  We do not need to restrict the amount of flow on such edges. which allow all any wins to team i,
     * capacity is infinite, which allow all any wins to team i
     *
     * team i> sink edge:  Team x can win as many as w[x] + r[x] games, so to prevent team i from winning more than that many games in total,  we include an edge from team node i to the sink with capacity w[x] + r[x] - w[i].
     * capacity w[x] + r[x] - w[i], capture the team i additional wins so that it can not exceed x's maximum wins
     *
     *
     * If you can push all game‑flows through the teams into the sink without violating those caps,
     * If the max flow in the network below saturates all arcs leaving s then this corresponds to assigning winners to all of the remaining games in such a way that no team wins more games than x.
     * if the max flow does not saturate all arcs leaving s then there is no scenario in which team x wins the division.
     * there’s a feasible way to finish the season without any team surpassing x,
     * Otherwise,  x is out of contention.
     * */
    private FlowNetwork buildFlowNetwork(String team, int num) {
        int x = teamIndex.get(team);
        FlowNetwork flowNetwork = new FlowNetwork(num);
        // add vertexes
        for (String teami:teams) {
            int i= teamIndex.get(teami);
            for (String teamj:teams) {
                int j = teamIndex.get(teamj);
                if (i<j && i != x && j != x) {
                    // add source -> game(i,j) edge with capacity, game(i,j) index = i * n + j
                    flowNetwork.addEdge(new FlowEdge(0, i * n + j, games[i][j]));
                    // add game(i,j) -> team i edge with infinite capacity, team i index = n*n + i
                    flowNetwork.addEdge(new FlowEdge(i * n + j, n*n+ i, Double.POSITIVE_INFINITY));
                    // add game(i,j) -> team j edge with infinite capacity
                    flowNetwork.addEdge(new FlowEdge(i * n + j, n*n +j, Double.POSITIVE_INFINITY));
                }
            }
            // add team i -> sink edge(index num-1) with capacity
            flowNetwork.addEdge(new FlowEdge(n*n+ i, num-1, wins(team) + remaining(team) - wins(teami)));
        }
//        StdOut.println(flowNetwork.toString());
        return flowNetwork;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    /*
    * In fact, when a team is mathematically eliminated there always exists such a convincing certificate of elimination, where R is some subset of the other teams in the division.
    * Moreover, you can always find such a subset R by choosing the team vertices on the source side of a min s-t cut in the baseball elimination network.
    * Note that although we solved a maxflow/mincut problem to find the subset R, once we have it, the argument for a team's elimination involves only grade-school algebra.
    * */
    public Iterable<String> certificateOfElimination(String team) {
        boolean isEliminated  = isEliminated(team);
        if(!isEliminated) return null;

        List<String> cert = new ArrayList<>();
        int maxWins = wins(team) + remaining(team);
        for (String t : teams) {
            if (wins(t) > maxWins && !Objects.equals(t, team)) {
                cert.add(t);
                return cert;
            }
        }

        for (String teami:teams) {
            // the teami node index = n*n + teami index
            int teamiIndex = n*n+ teamIndex.get(teami);
            if (fordFulkerson.inCut( teamiIndex) && !Objects.equals(team, teami))
                cert.add(teami);
        }
        return cert;

    }

    public static void main(String[] args) {
        String filename = "baseball/teams4.txt";//args[0];
        BaseballElimination division = new BaseballElimination(filename);

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
