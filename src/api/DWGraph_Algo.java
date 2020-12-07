package api;

import org.json.JSONObject;
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
        if (src==dest)return 0;
        if (shortestPath(src, dest)!=null) return shortestPath(src, dest).size();
        return -1.0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {


        for (node_data v : algo.getV()) {
            v.setWeight(Double.POSITIVE_INFINITY);
            v.setTag(-1);
        }
        node_data start= algo.getNode(src);
        start.setWeight(0);
        PriorityQueue<node_data> pq = new PriorityQueue<>((o1, o2) -> (int)(o1.getWeight()-o2.getWeight()));
        List<node_data>ans=new ArrayList<>();
        HashMap<Integer,node_data>prev=new HashMap<>();
        pq.add(start);

        prev.put(src,start);
        while (!pq.isEmpty()){
            node_data current= pq.remove();
            current.setTag(0);
            for (edge_data e: algo.getE(current.getKey())){
                node_data neighbor= algo.getNode(e.getDest());
                if (neighbor.getTag()==-1) {
                    double w = current.getWeight() + e.getWeight();
                    if (neighbor.getWeight() > w) {
                        neighbor.setWeight(w);
                        neighbor.setTag(0);
                        pq.add(neighbor);

                        prev.put(neighbor.getKey(),current );
                    }
                }
            }
        }
        if (prev.get(dest)==null)return null;
        while (prev.get(dest).getWeight()<Double.POSITIVE_INFINITY){
            ans.add(algo.getNode(dest));
           dest=prev.get(dest).getKey();
           if (algo.getNode(dest).getKey()==src){
               ans.add(algo.getNode(src));break;
           }
        }
        Collections.reverse(ans);
        return ans;
    }


    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        JSONObject obj=new JSONObject();
        return false;
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

    public static void main(String[] args) {
        directed_weighted_graph G = new DWGraph_DS();
        G.addNode(new NodeData(0));
        G.addNode(new NodeData(1));
        G.addNode(new NodeData(2));
        G.addNode(new NodeData(3));
        G.addNode(new NodeData(4));
        G.addNode(new NodeData(5));
        G.addNode(new NodeData(6));
        G.addNode(new NodeData(7));




        G.connect(0,1,8);
        G.connect(0,2,5);
        G.connect(0,4,3);



        G.connect(1, 2, 3);
        G.connect(1, 5, 5);

        G.connect(2, 4, 7);
        G.connect(2, 6, 2);


//        G.connect(3, 5, 8);
        G.connect(3, 7, 5);


        G.connect(5, 3, 8);


        G.connect(6, 7, 8);
        G.connect(6, 2, 5);


//        G.connect(7, 5, 6);


        System.out.println(G);
        DWGraph_Algo algorithms = new DWGraph_Algo();
        algorithms.init(G);
        System.out.println(algorithms.shortestPathDist(0,7));

    }

}
