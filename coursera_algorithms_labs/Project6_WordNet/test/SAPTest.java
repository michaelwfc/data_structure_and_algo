
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class SAPTest {
    @Test
    public void dummyTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void testDigraph1() {

        String workingDir = System.getProperty("user.dir");
        System.out.println("Working Directory: " + workingDir);

        String digraph1Path = "Project6_WordNet/wordnet/digraph1.txt";

        In in = new In(digraph1Path);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v=3;
        int w=11;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

        // v = 1, w = 3
        // Ancestor should be 2, length = 2 (1->2, 3->2)
        assertEquals(4, length);
        assertEquals(1, ancestor);
    }

    @Test
    public void testDigraph2() {
        String workingDir = System.getProperty("user.dir");
        System.out.println("Working Directory: " + workingDir);

        String digraph1Path = "Project6_WordNet/wordnet/digraph2.txt";

        In in = new In(digraph1Path);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v=2;
        int w=3;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        // v = 1, w = 3
        // Ancestor should be 2, length = 2 (1->2, 3->2)
        assertEquals(1, length);
        assertEquals(3, ancestor);
    }

    @Test
    public void test16DigraphWordnet() {
        String workingDir = System.getProperty("user.dir");
        System.out.println("Working Directory: " + workingDir);

        String digraph1Path = "Project6_WordNet/wordnet/digraph-wordnet.txt";

        In in = new In(digraph1Path);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        Iterable<Integer> v = List.of(58962);
        Iterable<Integer> w = List.of(27758);

        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        // v = 1, w = 3
        // Ancestor should be 2, length = 2 (1->2, 3->2)
        assertEquals(12, length);
        assertEquals(57333, ancestor);
    }

}
