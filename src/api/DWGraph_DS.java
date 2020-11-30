package api;

import java.util.*;

public class DWGraph_DS implements directed_weighted_graph {

    private HashMap<Integer, node_data> Vertices;

    private HashMap<Integer, HashMap<Integer, edge_data>> Edges;

    private int V;
    private int E;
    private int MC;

    public DWGraph_DS() {
        Vertices = new HashMap<>();
        Edges = new HashMap<>();
        V = 0;
        E = 0;
        MC = 0;
    }

    public DWGraph_DS(directed_weighted_graph other) {
        Vertices = new HashMap<>();
        Edges = new LinkedHashMap<>();
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
        boolean secondCondition = Edges.get(src).containsKey(dest);
        boolean thirdCondition = Edges.get(dest).containsKey(src);

        if (firstCondition) {
            if (secondCondition) {
                return Edges.get(src).get(dest);
            } else if (thirdCondition) {
                return Edges.get(dest).get(src);
            }
        }
        return null;

    }

    @Override
    public void addNode(node_data n) {
        if (Vertices.containsKey(n.getKey())) {
            System.err.println("Already exists");
        }
        Vertices.put(n.getKey(), n);
        Edges.put(n.getKey(), new HashMap<>());
        MC++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (src != dest && getNode(dest) != null && getNode(src) != null) {
            edge_data e = new EdgeData(src, dest, w);
            if (!Edges.containsKey(src) || Edges.get(src) == null) {
                Edges.put(src, new HashMap<>());
            }
            if (!Edges.get(src).containsKey(dest)) {
                Edges.get(src).put(dest, e);
            }
            E++;
            MC++;
        } else {
            return;
        }


    }

    @Override
    public Collection<node_data> getV() {
        return Vertices.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        Collection<edge_data> neighbors = new ArrayList<>();
        for (edge_data e : Edges.get(node_id).values()) {
            neighbors.add(e);
        }
        return neighbors;
    }

    @Override
    public node_data removeNode(int key) {
        node_data v = getNode(key);

        Iterator<edge_data> iterator = getE(key).iterator();
        while (iterator.hasNext()) {
            removeEdge(key, iterator.next().getDest());
        }
        Vertices.remove(key);
        MC++;
        return v;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {

        edge_data e = Edges.get(src).remove(dest);
        if (e != null) {
            E--;
            MC++;
        }
        return e;
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
                ans += "{<V:" + e.getSrc() + ",E:" + e.getDest() + ",W:" + e.getWeight() + ">}";
            }
            ans += "\n";
        }
        return ans;
    }

//    public static void main(String[] args) {
//        directed_weighted_graph G = new DWGraph_DS();
//        G.addNode(new NodeData(2));
//        G.addNode(new NodeData(3));
//        G.addNode(new NodeData(0));
//        G.addNode(new NodeData(1));
//
//        G.connect(0, 3, 10);
//        G.connect(0, 2, 10);
//        G.connect(0, 1, 10);
//
//
//        G.removeNode(0);
//
//       directed_weighted_graph o=new DWGraph_DS(G);
//        System.out.println(G);
//        System.out.println(o.equals(G));
//    }
}
