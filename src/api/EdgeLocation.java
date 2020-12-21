package api;

public class EdgeLocation implements edge_location {

    private edge_data e;
    private double ratio;




    @Override
    public edge_data getEdge() {
        return e;
    }

    @Override
    public double getRatio() {
        return ratio;
    }
}
