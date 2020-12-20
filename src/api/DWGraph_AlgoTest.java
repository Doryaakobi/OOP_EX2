package api;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
class DWGraph_AlgoTest {
    private static Random _rnd = null;

    private static DWGraph_DS graph_creator(int v_size, int e_size, int seed) {
        DWGraph_DS g = new DWGraph_DS();
        _rnd = new Random(seed);
        for (int i = 0; i < v_size; i++) {

            g.addNode(new NodeData(i));
        }
        while (g.edgeSize() < e_size) {
            int a = nextRnd(0, v_size);
            int b = nextRnd(0, v_size);
            g.connect(a, b, nextRnd(1.0, 10));
        }
        return g;
    }

    private static directed_weighted_graph connected_graph_creator(int v_size, int seed) {
        directed_weighted_graph g = new DWGraph_DS();
        _rnd = new Random(seed);
        for (int i = 0; i < v_size; i++) {

            g.addNode(new NodeData(i));
        }
        for (int i = 0; i < v_size - 1; i++) {

            g.connect(i, i + 1, i);
            g.connect(i + 1, i, i);
        }
        for (int i = 0; i < v_size; i++) {
            int key = nextRnd(0, v_size);
            g.connect(i, key, i);
        }
        return g;
    }

    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }
    @Test
    void copy() {
    }

    @Test
    void isConnected() {
        long start = new Date().getTime();

        DWGraph_DS g0=graph_creator(1000000,5000000,1);


        DWGraph_Algo algorithms=new DWGraph_Algo();
        algorithms.init(g0);
        System.out.println(algorithms.isConnected());
        long end = new Date().getTime();
        double dt = (end-start)/1000.0;
        System.out.println(dt);
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {


        DWGraph_DS G=graph_creator(1000,100001,1);

        System.out.println(G);
        DWGraph_Algo algorithms = new DWGraph_Algo();
        algorithms.init(G);
        System.out.println(algorithms.shortestPathDist(0,100000));

//        double dt = (end-start)/1000.0;
//        System.out.println(dt);
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}