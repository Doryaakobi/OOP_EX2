package api;

import com.google.gson.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.io.*;
import java.util.*;


import com.google.gson.*;
        import gameClient.util.Point3D;
        import org.json.JSONObject;

        import java.io.*;
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


    @Override
    public boolean isConnected() {
        for (node_data v : algo.getV()) {
            if (!BFS(v.getKey())) {
                return false;
            }
        }
        setGraph();
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;
        if (shortestPath(src, dest) != null) return shortestPath(src, dest).size();
        return -1.0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {


        for (node_data v : algo.getV()) {
            v.setWeight(Double.POSITIVE_INFINITY);
            v.setTag(-1);
        }
        node_data start = algo.getNode(src);
        start.setWeight(0);
        PriorityQueue<node_data> pq = new PriorityQueue<>((o1, o2) -> (int) (o1.getWeight() - o2.getWeight()));
        List<node_data> ans = new ArrayList<>();
        HashMap<Integer, node_data> prev = new HashMap<>();
        pq.add(start);

        prev.put(src, start);
        while (!pq.isEmpty()) {
            node_data current = pq.remove();
            current.setTag(0);
            for (edge_data e : algo.getE(current.getKey())) {
                node_data neighbor = algo.getNode(e.getDest());
                if (neighbor.getTag() == -1) {
                    double w = current.getWeight() + e.getWeight();
                    if (neighbor.getWeight() > w) {
                        neighbor.setWeight(w);
                        neighbor.setTag(0);
                        pq.add(neighbor);

                        prev.put(neighbor.getKey(), current);
                    }
                }
            }
        }
        if (prev.get(dest) == null) return null;
        while (prev.get(dest).getWeight() < Double.POSITIVE_INFINITY) {
            ans.add(algo.getNode(dest));
            dest = prev.get(dest).getKey();
            if (algo.getNode(dest).getKey() == src) {
                ans.add(algo.getNode(src));
                break;
            }
        }
        Collections.reverse(ans);
        return ans;
    }


    @Override
    public boolean save(String file) {

        try {
            Gson convertedGraph = new Gson();
            JsonObject json_graph = new JsonObject();
            JsonArray json_vertices = new JsonArray();
            JsonArray json_edges = new JsonArray();
            for (node_data v : this.algo.getV()) {
                JsonObject node = new JsonObject();
                node.addProperty("pos", v.getLocation().x() + "," + v.getLocation().y() + "," + v.getLocation().z());
                node.addProperty("id", v.getKey());
                json_vertices.add(node);
                for (edge_data e : this.algo.getE(v.getKey())) {
                    JsonObject edge = new JsonObject();
                    edge.addProperty("src", e.getSrc());
                    edge.addProperty("w", e.getWeight());
                    edge.addProperty("dest", e.getDest());
                    json_edges.add(edge);
                }
                json_graph.add("Edges", json_edges);
                json_graph.add("Nodes", json_vertices);

            }
            File graphFile = new File(file);
            FileWriter saved_graph = new FileWriter(file);
            saved_graph.write(convertedGraph.toJson(json_graph));
            saved_graph.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean load(String file) {
        try {
            FileReader graph_tl = new FileReader(file);
            JsonObject json_graph = new JsonParser().parse(graph_tl).getAsJsonObject();
            JsonArray loaded_edges = json_graph.getAsJsonArray("Edges");
            JsonArray loaded_vertices = json_graph.getAsJsonArray("Nodes");
            directed_weighted_graph loaded_graph = new DWGraph_DS();

            for (JsonElement node : loaded_vertices) {
                node_data n = new NodeData(((JsonObject) node).get("id").getAsInt());
                String location = ((JsonObject) node).get("pos").getAsString();
                String[] xyz = location.split(",");
                Point3D nLocation = new Point3D(Double.parseDouble(xyz[0]), Double.parseDouble(xyz[1]), Double.parseDouble(xyz[2]));
                n.setLocation(nLocation);
                loaded_graph.addNode(n);
            }

            for (JsonElement edge : loaded_edges) {
                int src = ((JsonObject) edge).get("src").getAsInt();
                double w = ((JsonObject) edge).get("w").getAsDouble();
                int dest = ((JsonObject) edge).get("dest").getAsInt();
                edge_data json_edge = new EdgeData(src, dest, w);
                loaded_graph.connect(json_edge.getSrc(), json_edge.getDest(), json_edge.getWeight());

            }
            this.algo = loaded_graph;
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return algo.toString();
    }

    private boolean BFS(int src) {
        if (algo.nodeSize() == 0 || algo.nodeSize() == 1) {
            return true;
        }
        if (this.algo.nodeSize() > this.algo.edgeSize() + 1) {
            return false;
        }
        setGraph();
        int counter = 0;
        Queue<node_data> q = new LinkedList<>();
        node_data v = algo.getNode(src);
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

    private void setGraph() {
        for (node_data u : algo.getV()) {
            u.setTag(-1);
        }
    }

    private void setWeights(double weights) {
        for (node_data v : algo.getV()) {
            v.setWeight(weights);
            v.setTag(-1);
        }
    }
}
