import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;


public class OutcastTest {
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCycle(){
        String synsetsFIle="Project6_WordNet/wordnet/synsets15.txt";
        String hypernymsFIle="Project6_WordNet/wordnet/hypernyms3InvalidCycle.txt";
//        try{
        WordNet wordnet = new WordNet(synsetsFIle, hypernymsFIle);
        Outcast outcast = new Outcast(wordnet);
        String outcastFIle= "Project6_WordNet/wordnet/outcast4.txt";

        In in = new In(outcastFIle);
        String[] nouns = in.readAllStrings();
        StdOut.println(outcastFIle + ": " + outcast.outcast(nouns));
    }

    @Test()
    public void testOutcast4(){
        String synsetsFIle="Project6_WordNet/wordnet/synsets.txt";
        String hypernymsFIle="Project6_WordNet/wordnet/hypernyms.txt";

        WordNet wordnet = new WordNet(synsetsFIle, hypernymsFIle);
        Outcast outcast = new Outcast(wordnet);
        String outcastFIle= "Project6_WordNet/wordnet/outcast4.txt";

        In in = new In(outcastFIle);
        String[] nouns = in.readAllStrings();
        StdOut.println(outcastFIle + ": " + outcast.outcast(nouns));}

}
