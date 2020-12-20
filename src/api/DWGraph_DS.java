package api;

import java.util.*;


public class DWGraph_DS implements directed_weighted_graph {

    private HashMap<Integer, node_data> Vertices;

    private HashMap<Integer, HashMap<Integer, edge_data>> inDegree;
    private HashMap<Integer, HashMap<Integer, edge_data>> outDegree;

    private int V;
    private int E;
    private int MC;

    public DWGraph_DS() {
        Vertices = new HashMap<>();
        inDegree = new HashMap<>();
        outDegree = new HashMap<>();
        V = 0;
        E = 0;
        MC = 0;
    }

    public DWGraph_DS(directed_weighted_graph other) {
        Vertices = new HashMap<>();
        outDegree = new LinkedHashMap<>();
        inDegree = new LinkedHashMap<>();
        for (node_data v : other.getV()) {
            node_data u = new NodeData(v);
            this.addNode(u);
        }

        for (node_data v : getV()) {
            if (other.getE(v.getKey()) != null) {
                for (edge_data e : other.getE(v.getKey())) {
                    this.connect(v.getKey(), e.getDest(), e.getWeight());
                }
            }
        }
        this.V = other.nodeSize();
        this.E = other.edgeSize();
        this.MC = other.getMC();
    }


    @Override
    public node_data getNode(int key) {
        return Vertices.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        boolean firstCondition = Vertices.containsKey(src) && Vertices.containsKey(dest);
        boolean secondCondition = inDegree.get(src).containsKey(dest);

        if (firstCondition) {
            if (secondCondition) {
                return inDegree.get(dest).get(src);
            }
        }
        return null;

    }

    @Override
    public void addNode(node_data n) {
//        if (Vertices.containsKey(n.getKey())) {
//            System.err.println("Already exists");
//        }
        Vertices.put(n.getKey(), n);
        inDegree.put(n.getKey(), new HashMap<>());
        outDegree.put(n.getKey(), new HashMap<>());

        MC++;
        V++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        boolean primary = Vertices.containsKey(src) && Vertices.containsKey(dest);
        boolean nodes = Vertices.get(src) != null && Vertices.get(dest) != null;
        boolean edge = outDegree.get(src).containsKey(dest);

        if (primary && nodes && src != dest) {
            if (!edge) {
                edge_data e = new EdgeData(src, dest, w);
                outDegree.get(src).put(dest, e);
                inDegree.get(dest).put(src, e);
                E++;
                MC++;
            }
        }

    }

    @Override
    public Collection<node_data> getV() {
        return Vertices.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {

        return outDegree.get(node_id).values();
    }

    @Override
    public node_data removeNode(int key) {

        node_data removedNode = Vertices.get(key);
        if (Vertices.containsKey(key)) {
            for (edge_data e : getE(key)) {
                outDegree.get(e.getDest()).remove(key);
                inDegree.get(key).remove(e.getSrc());
                E--;
                MC++;
            }
            Vertices.remove(key);
        }
        return removedNode;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        boolean outEdge = outDegree.get(src).containsKey(dest);

        if (outEdge) {
            edge_data inRemoved = outDegree.get(src).remove(dest);
            if (inRemoved != null) {
                E--;
                MC++;
            }
            return inRemoved;
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return Vertices.size();
    }

    @Override
    public int edgeSize() {
        return E;
    }

    @Override
    public int getMC() {
        return MC;
    }

    public String toString() {
        String ans = "|V|=" + nodeSize() + ",|E|=" + edgeSize() + ",ModeCount=" + getMC() + "\n";

        for (node_data v : getV()) {
            ans += v.getKey() + ": ";
            for (edge_data e : getE(v.getKey())) {
                ans += "{<V:" + e.getSrc() + ",E:" + e.getDest() + ",W:" + e.getWeight() + ">},";
            }
            ans += "\n";
        }
        return ans;
    }

    public static void main(String[] args) {
        directed_weighted_graph G = new DWGraph_DS();
        G.addNode(new NodeData(2));
        G.addNode(new NodeData(3));
        G.addNode(new NodeData(0));
        G.addNode(new NodeData(1));

        G.connect(0, 3, 10);
        G.connect(0, 2, 10);
        G.connect(0, 1, 10);


        G.removeNode(0);

        directed_weighted_graph o = new DWGraph_DS(G);
        System.out.println(G);
        System.out.println(o.equals(G));
    }
}
