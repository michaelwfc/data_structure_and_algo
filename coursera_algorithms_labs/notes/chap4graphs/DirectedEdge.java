package chap4graphs;

public class DirectedEdge{
    private final int v,w;
    private final double weight;

    //    weighted edge v→w
    public DirectedEdge(int v, int w, double weight){
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    //from() and to() replace either() and other()
    public int from(){
        return v;
    }

    public int to(){
        return w;
    }

    public double weight(){
        return weight;
    }

}
