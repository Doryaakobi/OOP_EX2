package api;

import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms {

    private directed_weighted_graph algo;

    public DWGraph_Algo(){
        algo=new DWGraph_DS();
    }

    private void setGraph(double w){
        for (node_data v: algo.getV()){
            v.setWeight(w);
            v.setTag(-1);
        }
    }

    @Override
    public void init(directed_weighted_graph g) {
        this.algo=g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return algo;
    }

    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(algo);
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {

        


        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {

        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
