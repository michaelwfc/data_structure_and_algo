/*
*  https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php
*
* Measuring the semantic relatedness of two nouns. Semantic relatedness refers to the degree to which two concepts are related.
* Measuring semantic relatedness is a challenging problem. For example, you consider George W. Bush and John F. Kennedy (two U.S. presidents) to be more closely related than George W. Bush and chimpanzee (two primates). It might not be clear whether George W. Bush and Eric Arthur Blair are more related than two arbitrary people. However, both George W. Bush and Eric Arthur Blair (a.k.a. George Orwell) are famous communicators and, therefore, closely related.

We define the semantic relatedness of two WordNet nouns x and y as follows:

A = set of synsets in which x appears
B = set of synsets in which y appears
distance(x, y) = length of shortest ancestral path of subsets A and B
sca(x, y) = a shortest common ancestor of subsets A and B
*
* */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.DirectedCycle;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;



public class WordNet {
    // build a wordnet DAG, each node is a synset, each direction is defined by hypernyms
    private final Digraph G;
    private final SAP sapG;
    private final Set<String> nouns;
    private final List<SynsetVertex> synsetVertexList;
    private final Map<String, Integer> nounToVertexIndex;
    private final Map<Integer, Integer>  vertexIndexToSynsetID;
    private final Map<Integer, Integer>  synsetIDToVertexIndex;
    private final Map<Integer, String>  synsetIDToSynset;
    private final int V;
    private final int E;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        In synsets_in = new In(synsets);
        int vertexIndex = 0;
        nouns= new HashSet<>();
        synsetVertexList = new ArrayList<>();
        nounToVertexIndex = new HashMap<>();
        vertexIndexToSynsetID = new HashMap<>();
        synsetIDToVertexIndex = new HashMap<>();
        synsetIDToSynset = new HashMap<>();

        while(!synsets_in.isEmpty()){
            if(vertexIndex%10000==0){
                StdOut.println("Synsets processed:"+vertexIndex);
            }
            String synset_line = synsets_in.readLine();
            String[] fields = synset_line.split(",");
            int synsetID = Integer.parseInt(fields[0]);
            String synset = fields[1];
            String[] synsetNouns = synset.split(" ");

            nouns.addAll(Arrays.asList(synsetNouns));
            for(String noun:synsetNouns){
                nounToVertexIndex.put(noun, vertexIndex);
            }
            SynsetVertex synsetVertex = new SynsetVertex(synsetID, synsetNouns);
            synsetVertexList.add(synsetVertex);
            vertexIndexToSynsetID.put(vertexIndex,synsetID);
            synsetIDToVertexIndex.put(synsetID,vertexIndex);
            synsetIDToSynset.put(synsetID,synset);
            vertexIndex++;
        }

        this.V  = vertexIndex;

        G= new Digraph(this.V);

        In hypernyms_in = new In(hypernyms);
        int edgeCount = 0;
        while(!hypernyms_in.isEmpty()){
            String hypernyms_line =  hypernyms_in.readLine();
            String[] fields = hypernyms_line.split(",");
            int synsetID = Integer.parseInt(fields[0]);
            int synsetVertexIndex = this.synsetIDToVertexIndex.get(synsetID);
            for(int i=1;i< fields.length; i++){
                int hypernymID=Integer.parseInt(fields[i]);
                int hypernymVertexIndex = this.synsetIDToVertexIndex.get(hypernymID);
                G.addEdge(synsetVertexIndex, hypernymVertexIndex);
                edgeCount++;
            }
        }

        this.E =edgeCount;

        validateCycle();
        validateRoot();

        this.sapG = new SAP(G);
    }

    private static class SynsetVertex {
        private int id;
        private String[] nouns ;

        public SynsetVertex(int id, String[] nouns ){
           this.id = id;
           this.nouns =nouns;
        }

    }

    private void validateCycle(){
        // Idea: iterate from one node along the direction, if one node get 2 twice, then there is a cycle
        DirectedCycle cycle = new DirectedCycle(G);
        if(cycle.hasCycle()){
            throw  new IllegalArgumentException("word net should not have a cycle in it");
        }
    }
    private void validateRoot(){
        int rootNum=0;
        for(int i=0;i<V; i++){
            if(G.outdegree(i)==0){
                rootNum++;
                if(rootNum>1){
                    throw  new IllegalArgumentException("word net should not have more than 1 root in it");
                }
            }
        }

    }


    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(nouns.contains(word))return true;
        else return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(nounA==null || nounB==null|| !isNoun(nounA) || !isNoun(nounB)) return -1;
        int vertexA= nounToVertexIndex.get(nounA);
        int vertexB = nounToVertexIndex.get(nounB);

        int length = sapG.length(vertexA, vertexB);
        return length;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(nounA==null || nounB==null|| !isNoun(nounA) || !isNoun(nounB)) return null;
        int vertexA= nounToVertexIndex.get(nounA);
        int vertexB = nounToVertexIndex.get(nounB);
        int ancestor = sapG.ancestor(vertexA, vertexB);
        if(ancestor!=-1){
            int ancestorID = vertexIndexToSynsetID.get(ancestor);
            String  ancestorSynset = synsetIDToSynset.get(ancestorID);
            return ancestorSynset;
        }else return null;
    }

//    public void main(String[] args){
//        WordNet wordnet = new WordNet(args[0], args[1]);
//        wordnet.distance()
//    }
}