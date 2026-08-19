/*
 *
 * */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;

import java.util.HashMap;
import java.util.Map;

public class Outcast {
    private WordNet wordnet;
    private Map<String[], Integer> cache;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
        cache = new HashMap<>();
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxAnchorSumDistance=-1;
        String outCastNouns=null;
        for (String anchorNoun : nouns) {
            int anchorSumDistance= 0;
            for (String noun : nouns) {
                int dis;
                String[] pair = new String[]{anchorNoun, noun};
                if (anchorNoun == noun) {
                    dis = 0;
                } else if (cache.get(pair)!=null){
                    dis = cache.get(pair); // use cache
                }
                else {
                    dis = wordnet.distance(anchorNoun, noun);
                }
                anchorSumDistance+=dis;
            }
            if(anchorSumDistance > maxAnchorSumDistance){
                outCastNouns = anchorNoun;
                maxAnchorSumDistance= anchorSumDistance;
            }
        }
        return outCastNouns;
    }


    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}