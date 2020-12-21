package api;

import com.google.gson.*;
import gameClient.util.Point3D;


import java.io.*;
import java.util.*;



@SuppressWarnings("ALL")
public class DWGraph_Algo implements dw_graph_algorithms {

    private directed_weighted_graph algo;
    private static final String VISITED = "V";
    private static final String UNVISITED = "U";


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




    /** Updated*/
    @Override
    public boolean isConnected() {
        node_data check = algo.getV().iterator().next();
        if (algo.nodeSize() == 0 || algo.nodeSize() == 1) {
            return true;
        }
        if (this.algo.nodeSize() > this.algo.edgeSize() + 1) {
            return false;
        }

        directed_weighted_graph t = transposedGraph(algo);
        return Bfs(algo, check) && Bfs(t, check);
    }


    /**
     * Need To Fix
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;
//        if (shortestPath(src, dest) != null) return shortestPath(src, dest).size();
        node_data source = algo.getNode(src);
        node_data destination = algo.getNode(dest);
        HashMap<node_data,Double> tags = new HashMap<>();
        HashMap<Integer, Integer>prev = new LinkedHashMap<>();
        prev.put(src,-1);
        setGraph(Double.POSITIVE_INFINITY);
        PriorityQueue<node_data> pq = new PriorityQueue<>((o1, o2) -> {
            double a = o1.getWeight()-o2.getWeight();
            if(a > 0) return 1;
            if(a < 0) return -1;
            return 0;
        });
        pq.add(source);
        while (!pq.isEmpty()){
            node_data p=pq.poll();

        }

        return -1.0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {


        setGraph(Double.POSITIVE_INFINITY);

        node_data start = algo.getNode(src);
        start.setWeight(0);
        PriorityQueue<node_data> pq = new PriorityQueue<>((o1, o2) -> {
            double a = o1.getWeight()-o2.getWeight();
            if(a > 0) return 1;
            if(a < 0) return -1;
            return 0;
        });


        List<node_data> ans = new ArrayList<>();
         HashMap<node_data,Double> tags = new HashMap<>();
         HashMap<Integer, Integer>prev = new LinkedHashMap<>();
        pq.add(start);

        prev.put(src, -1);

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

                        prev.put(neighbor.getKey(), current.getKey());
                    }
                }
            }
        }


        /**
         * Need To Fix
         */
//        if (prev.get(dest) == null) return null;
//        while (prev.get(dest).getWeight() < Double.POSITIVE_INFINITY) {
//            ans.add(algo.getNode(dest));
//            dest = prev.get(dest).getKey();
//            if (algo.getNode(dest).getKey() == src) {
//                ans.add(algo.getNode(src));
//                break;
//            }
//        }
        Collections.reverse(ans);
        return ans;
    }

//    public  List<node_data> shortestPath(int src, int dest) {
//        HashMap<node_data, Double> distances = new HashMap<>();
//        Queue<node_data> q = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
//        HashMap<node_data, node_data> father = new HashMap<>();//this hashmap is using to recover the path
//        setGraph();//init all the tags to -1
//        node_data start = algo.getNode(src);
//        node_data end = algo.getNode(dest);
//        if (start == null || end == null) return null;
//        if (src == dest) {//if true then returns a list with only the start node
//            List<node_data> temp = new LinkedList<>();
//            temp.add(start);
//            return temp;
//        }
//        node_data Ni_node;
//        node_data curr;
//        distances.put(start, 0.0);//the distance between node to itself is 0
//        start.setTag(1);
//        q.add(start);
//        boolean flag = false;
//        while (!q.isEmpty()&&!flag) {
//            curr = q.poll();//take a node
//            if(curr.getKey()==dest)
//                flag=true;
//            for (edge_data edge : algo.getE(curr.getKey())) {//run for all of his Ni
//                Ni_node = algo.getNode(edge.getDest());
//                if (Ni_node.getTag() == -1) {//if the Ni never got visited
//                    distances.put(Ni_node, (distances.get(curr) + edge.getWeight()));//the tag of this node is his father tag(recursive)+the weight of the edge who connects between the father to him.
//                    father.put(Ni_node, curr);//the HashMap builds in this path--> <the neighbor, his father>
//                    q.add(Ni_node);//O(logV)
//                    Ni_node.setTag(1);
//                } else {//if the Ni already got visited
//                    //take the minimum between the Ni tag to the new path that found.
//                    double temp = Math.min(distances.get(Ni_node), distances.get(curr) + edge.getWeight());
//                    if (temp != distances.get(Ni_node)) {//if the new path is better
//                        father.put(Ni_node, curr);//set the new father of Ni
//                        distances.put(Ni_node, temp);//set the new path of Ni
//                        q.add(Ni_node);//for update the list, yes i know there will be duplicate nodes inside the q
//                    }
//                }
//            }
//
//        }
//        if (!flag) {//if there is no path then return null
//            return new LinkedList<>();
//        }
//        return buildPath(father,end);//builds path using `buildPath` and return this list
//    }
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









    /** Added new methods for better complexity*/

    private boolean Bfs(directed_weighted_graph g, node_data src) {
        VisitedOrNot(UNVISITED, g);
        BFS(src, g);
        for (node_data v : g.getV()) {
            if (v.getInfo() == UNVISITED) return false;
        }
        return true;
    }

    private void BFS(node_data src, directed_weighted_graph g) {
        Queue<node_data> q = new LinkedList<>();
        node_data v = src;
        v.setInfo(VISITED);
        q.add(v);
        while (!q.isEmpty()) {
            node_data current = q.poll();
            for (edge_data e : g.getE(current.getKey())) {
                node_data w = g.getNode(e.getDest());
                if (w.getInfo() == UNVISITED) {
                    q.add(w);
                    w.setInfo(VISITED);
                }
            }
        }

    }

    private void setGraph(double w) {
        for (node_data u : algo.getV()) {
            u.setWeight(w);
        }
    }

    private directed_weighted_graph transposedGraph(directed_weighted_graph g) {
        directed_weighted_graph answer = new DWGraph_DS();
        for (node_data i : g.getV()) {
            answer.addNode(new NodeData(i.getKey()));
        }
        for (node_data i : g.getV()) {
            for (edge_data e : g.getE(i.getKey())) {
                answer.connect(e.getDest(), i.getKey(), e.getWeight());
            }
        }
        return answer;
    }

    private void VisitedOrNot(String str, directed_weighted_graph g) {
        for (node_data v : g.getV()) {
            v.setInfo(str);
        }
    }

    /** Until  here are all the added methods*/
}
