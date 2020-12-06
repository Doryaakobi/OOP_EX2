package api;

import java.util.*;


public class DWGraph_Algo implements dw_graph_algorithms {

    private directed_weighted_graph algo;

    public DWGraph_Algo() {
        algo = new DWGraph_DS();
    }

    public DWGraph_Algo(directed_weighted_graph G) {
        algo = new DWGraph_DS(G);
    }

    @Override
    public void init(directed_weighted_graph g) {
        this.algo = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return algo;
    }

    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(algo);
    }


    private List<edge_data> list(int v) {
        List<edge_data> ans = new ArrayList<>();
        for (edge_data e : algo.getE(v)) {
            if (e != null) {
                ans.add(e);
            }
        }
        return ans;
    }


    @Override
    public boolean isConnected() {
        if (algo.nodeSize() == 0 || algo.nodeSize() == 1) {
            return true;
        }
        if (this.algo.nodeSize() > this.algo.edgeSize() + 1) {
            return false;
        }
        setGraph();
        int counter = 0;
        Queue<node_data> q = new LinkedList<>();
        node_data v = algo.getV().iterator().next();
        v.setTag(0);

        q.add(v);
        while (!q.isEmpty()) {
            node_data current = q.poll();
            counter++;
            for (edge_data e : algo.getE(current.getKey())) {
                node_data w = algo.getNode(e.getDest());
                if (w.getTag() == -1) {
                    q.add(w);
                    w.setTag(0);
                }
            }
        }

        if (counter == algo.nodeSize()) {
            return true;
        }
        return false;
    }

    private void reset() {
        for (node_data n : algo.getV()) {
            n.setWeight(Double.MAX_VALUE);
            n.setInfo("");
        }
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

    private void setGraph() {
        for (node_data u : algo.getV()) {
            u.setTag(-1);

        }
    }

    private void setWeights(double weights) {
        for (node_data v : algo.getV()) {
            v.setWeight(weights);
        }
    }

    @Override
    public String toString() {
        return algo.toString();
    }

    public static void main(String[] args) {
        directed_weighted_graph G = new DWGraph_DS();
        for (int i = 0; i <= 8; i++) {
            node_data d = new NodeData(i);
            G.addNode(d);
        }

//
        G.connect(0, 1, 0);
        G.connect(0, 2, 0);
        G.connect(1, 3, 0);
        G.connect(3, 1, 0);
        G.connect(3, 7, 0);
        G.connect(7, 3, 0);
        G.connect(8, 3, 0);
        G.connect(3, 8, 0);
        G.connect(2, 4, 0);
        G.connect(4, 2, 0);
        G.connect(2, 5, 0);
        G.connect(5, 2, 0);
        G.connect(6, 2, 0);
        G.connect(2, 6, 0);
        G.connect(2, 1, 0);
        G.removeNode(0);
//        G.connect(1, 4, 0);
//        G.connect(1, 2, 0);
//        G.connect(1, 6, 0);
//        G.connect(6, 2, 0);
//        G.connect(6, 0, 0);
//        G.connect(2, 3, 0);
//        G.connect(3, 2, 0);
//        G.connect(3, 4, 0);
//        G.connect(4, 3, 0);
//
        System.out.println(G);
        dw_graph_algorithms algorithms = new DWGraph_Algo();
        algorithms.init(G);
        System.out.println(algorithms.isConnected());
//
//        System.out.println(algorithms.shortestPathDist(1, 3));


    }
}
