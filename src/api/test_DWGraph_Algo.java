package api;

import api.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class test_DWGraph_Algo {
    private static Random _rnd = null;

    private static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
        directed_weighted_graph g = new DWGraph_DS();
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

    private static List<node_data> modifySP(directed_weighted_graph g, int start) {
        List<node_data> res = new LinkedList<node_data>();
        node_data cur = g.getNode(start);
        if (cur == null) return null;
        res.add(cur);
        Collection<edge_data> ni = g.getE(cur.getKey());
        while (ni != null && !ni.isEmpty() && res.size() < 10) {
            cur.setTag(0);
            Iterator<edge_data> i = ni.iterator();
            edge_data tmp = i.next();
            node_data next = g.getNode(tmp.getDest());
            while (i.hasNext() && next.getTag() == 0) {
                tmp = i.next();
                next = g.getNode(tmp.getDest());
            }
            if (next.getTag() == 0) return res;
            g.connect(cur.getKey(), next.getKey(), nextRnd(0.0, 0.1));
            cur = next;
            res.add(cur);
            ni = g.getE(cur.getKey());
        }
        return res;
    }

    private static void remove_file(String file_name) throws IOException {
        try {
            Path path = Paths.get(file_name);
            Files.deleteIfExists(path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

    public static boolean compare(directed_weighted_graph g0, directed_weighted_graph g1) {
        for (node_data n : g0.getV()) {
            int nkey = n.getKey();

            for (edge_data ni : g0.getE(nkey)) {
                int dest = ni.getDest();
                if (g1.getEdge(nkey, dest) == null) return false;
                if (g1.getEdge(nkey, dest).getWeight() != g0.getEdge(nkey, dest).getWeight() ||
                        g1.getEdge(nkey, dest).getSrc() != g0.getEdge(nkey, dest).getSrc() ||
                        g1.getEdge(nkey, dest).getDest() != g0.getEdge(nkey, dest).getDest()) return false;



            }
        }
        if (g1.edgeSize() != g0.edgeSize()) {return false;}
        return (g1.nodeSize() == g0.nodeSize());
    }


    @Test
    public void copy_struct() {
        directed_weighted_graph g0 = new DWGraph_DS();
        g0.addNode(new NodeData(1));
        g0.addNode(new NodeData(2));
        g0.connect(2, 1, 7);
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        directed_weighted_graph g1 = ga.copy();
        boolean b = true;
        b &= (g1.getNode(1) != null);
        b &= (g1.getNode(2) != null);
        b &= (g1.getEdge(2, 1).getWeight() == 7);
        b &= (g1.getEdge(2, 1) != g0.getEdge(1, 2));
        assertEquals(true, b);
    }

    @Test
    public void copy_independency1() {
        directed_weighted_graph g0 = new DWGraph_DS();
        g0.addNode(new NodeData(1));
        g0.addNode(new NodeData(2));
        g0.connect(2, 1, 7);
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        directed_weighted_graph g1 = ga.copy();

        assertEquals(false, g0.getNode(1) == g1.getNode(1));
    }

    @Test
    public void copy_independency2() {
        directed_weighted_graph g0 = new DWGraph_DS();
        g0.addNode(new NodeData(1));
        g0.addNode(new NodeData(2));
        g0.connect(2, 1, 7);
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        directed_weighted_graph g1 = ga.copy();
        g0.removeEdge(2, 1);
        assertEquals(true, g1.getEdge(2, 1) != null);
    }

    @Test
    public void copy_independency3() {
        directed_weighted_graph g0 = new DWGraph_DS();
        g0.addNode(new NodeData(1));
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        directed_weighted_graph g1 = ga.copy();
        int g1_1st_tag = g1.getNode(1).getTag();
        g0.getNode(1).setTag(g1_1st_tag + 1);
        assertEquals(false, g1.getNode(1).getTag() == g0.getNode(1).getTag());
    }

    @Test
    public void SP_basic() {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 3; i++) {
            g0.addNode(new NodeData(i));
        }
        g0.connect(0, 1, 1);
        g0.connect(1, 2, 1);
        g0.connect(0, 2, 3);

        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        List<node_data> SP = ga.shortestPath(0, 2);
        int[] expected = {0, 1, 2};
        boolean b = true;
        int i = 0;
        for (node_data n : SP) {
            b &= n.getKey() == expected[i++];
        }
        assertEquals(true, b);
    }

    @Test
    public void SP_regular() {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 10; i++) {
            g0.addNode(new NodeData(i));
        }
        g0.connect(0, 1, 1); // 2 10
        g0.connect(0, 2, 2); // (5)---(8)__________
        g0.connect(1, 4, 1); // /5 \3 \
        g0.connect(2, 5, 5); // (2)----\-----\10 \
        g0.connect(2, 7, 10); // 2/ \ 4 \ 4 \
        g0.connect(4, 3, 1); // (0) 1 _ (4)-----(7)--------(9)
        g0.connect(4, 5, 3); // 1\ / 1| /
        g0.connect(4, 7, 4); // (1) (3) /
        g0.connect(3, 6, 1); // 1\ 1 /
        g0.connect(5, 8, 2); // (6)----------
        g0.connect(6, 9, 1); //
        g0.connect(7, 9, 4); //
        g0.connect(9, 8, 10); //
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        List<node_data> SP = ga.shortestPath(0, 9);
        int[] expected = {0, 1, 4, 3, 6, 9};
        boolean b = true;
        int i = 0;
        for (node_data n : SP) {
            b &= n.getKey() == expected[i++];
        }
        assertEquals(true, b);
    }


    @Test
    public void SP_advanced() {
        _rnd = new Random(1);
        for (int i = 0; i < 400000; i++) {
            directed_weighted_graph g = graph_creator(50, nextRnd(0, 100), i);
            int start = nextRnd(0, g.getV().size());
            List<node_data> expected = modifySP(g, start);
            dw_graph_algorithms ga = new DWGraph_Algo();
            ga.init(g);
            List<node_data> SP = ga.shortestPath(start, ((node_data) (expected.toArray()[expected.size() - 1])).getKey());
            boolean b = expected.equals(SP);
            if (!b) {
                System.out.println("failed with this graph:");
                System.out.println("(graph_craetor(" + "50, " + g.edgeSize() + ", " + i + ")");
                System.out.println(expected);
                System.out.println(SP);
                fail();
            }
        }
    }


    @Test
    public void SP_dead_end() {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 10; i++) {
            g0.addNode(new NodeData(i));
        }
        g0.connect(0, 1, 1);
        g0.connect(0, 2, 10);
        g0.connect(0, 3, 10);
        g0.connect(2, 4, 10);
        g0.connect(4, 5, 10);
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        List<node_data> SP = ga.shortestPath(0, 5);
        int[] expected = {0, 2, 4, 5};
        boolean b = true;
        int i = 0;
        for (node_data n : SP) {
            b &= n.getKey() == expected[i++];
        }
        assertEquals(true, b);
    }

    @Test
    public void SP_reversed() {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 10; i++) {
            g0.addNode(new NodeData(i));
        }
        g0.connect(0, 1, 1);
        g0.connect(0, 2, 2);
        g0.connect(1, 4, 1);
        g0.connect(2, 5, 5);
        g0.connect(2, 7, 10);
        g0.connect(4, 3, 1);
        g0.connect(4, 5, 3);
        g0.connect(4, 7, 4);
        g0.connect(3, 6, 1);
        g0.connect(5, 8, 2);
        g0.connect(6, 9, 1);
        g0.connect(7, 9, 4);
        g0.connect(9, 8, 10);
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        List<node_data> SP = ga.shortestPath(9, 0);
        assertEquals(null, SP);
    }

    @Test
    public void SP_no_path() {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 10; i++) {
            g0.addNode(new NodeData(i));
        }
        g0.connect(0, 1, 1);
        g0.connect(1, 4, 1);
        g0.connect(2, 5, 5);
        g0.connect(2, 7, 10);
        g0.connect(3, 6, 1);
        g0.connect(5, 8, 2);
        g0.connect(6, 9, 1);
        g0.connect(7, 9, 4);
        g0.connect(9, 8, 10);
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        List<node_data> SP = ga.shortestPath(0, 9);
        assertEquals(null, SP);
    }

    @Test
    public void SP_node_to_itself() {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 10; i++) {
            g0.addNode(new NodeData(i));
        }
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        boolean b = true;
        for (node_data n : g0.getV()) {
            b &= ga.shortestPath(n.getKey(), n.getKey()).size() == 1;
            b &= ga.shortestPath(n.getKey(), n.getKey()).get(0) == n;
        }
        assertEquals(true, b);
    }

    @Test
    public void SP_dist_basic() {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 3; i++) {
            g0.addNode(new NodeData(i));
        }
        g0.connect(0, 1, 1);
        g0.connect(1, 2, 1);
        g0.connect(0, 2, 3);

        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        assertEquals(2, ga.shortestPathDist(0, 2));
    }

    @Test
    public void SP_dist_regular() {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 10; i++) {
            g0.addNode(new NodeData(i));
        }
        g0.connect(0, 1, 1); // 2 10
        g0.connect(0, 2, 2); // (5)---(8)__________
        g0.connect(1, 4, 1); // /5 \3 \
        g0.connect(2, 5, 5); // (2)----\-----\10 \
        g0.connect(2, 7, 10); // 2/ \ 4 \ 4 \
        g0.connect(4, 3, 1); // (0) 1 _ (4)-----(7)--------(9)
        g0.connect(4, 5, 3); // 1\ / 1| /
        g0.connect(4, 7, 4); // (1) (3) /
        g0.connect(3, 6, 1); // 1\ 1 /
        g0.connect(5, 8, 2); // (6)----------
        g0.connect(6, 9, 1); //
        g0.connect(7, 9, 4); //
        g0.connect(9, 8, 10); //
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        assertEquals(5, ga.shortestPathDist(0, 9));
    }

    @Test
    public void save_basic() throws IOException {
        String file_name = System.getProperty("user.dir") + "\\data\\g0";
        directed_weighted_graph g0 = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        boolean b = ga.save(file_name);
        remove_file(file_name);
        assertEquals(true, b);
    }

    @Test
    public void load_A1() throws IOException {
        directed_weighted_graph g0 = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        String file_name = System.getProperty("user.dir") + "\\data\\A1";
        ga.load(file_name);
        assertEquals(17, ga.getGraph().nodeSize());
        assertEquals(1.3118716362419698, ga.getGraph().getEdge(0, 16).getWeight());
    }

    @Test
    public void isConnected_empty() {
        directed_weighted_graph g0 = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        assertEquals(true, ga.isConnected());
    }

    @Test
    public void isConnected_1_node() {
        directed_weighted_graph g0 = new DWGraph_DS();
        g0.addNode(new NodeData(0));
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        assertEquals(true, ga.isConnected());
    }

    @Test
    public void load() throws IOException {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 10; i++) {
            g0.addNode(new NodeData(i));
        }
        g0.connect(0, 1, 1); // 2 10
        g0.connect(0, 2, 2); // (5)---(8)__________
        g0.connect(1, 4, 1); // /5 \3 \
        g0.connect(2, 5, 5); // (2)----\-----\10 \
        g0.connect(2, 7, 10); // 2/ \ 4 \ 4 \
        g0.connect(4, 3, 1); // (0) 1 _ (4)-----(7)--------(9)
        g0.connect(4, 5, 3); // 1\ / 1| /
        g0.connect(4, 7, 4); // (1) (3) /
        g0.connect(3, 6, 1); // 1\ 1 /
        g0.connect(5, 8, 2); // (6)----------
        g0.connect(6, 9, 1); //
        g0.connect(7, 9, 4); //
        g0.connect(9, 8, 10); //
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        String file_name = System.getProperty("user.dir") + "\\out\\g0.json";
        System.out.println(file_name);
        remove_file(file_name);
        ga.save(file_name);
        dw_graph_algorithms gi = new DWGraph_Algo();
        gi.load(System.getProperty("user.dir") + "\\out\\g0.json");

        if (!compare(gi.getGraph(),g0)){fail();}

        remove_file(file_name);
    }

    @Test
    public void save() throws IOException {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 10; i++) {
            g0.addNode(new NodeData(i));
        }
        g0.connect(0, 1, 1); // 2 10
        g0.connect(0, 2, 2); // (5)---(8)__________
        g0.connect(1, 4, 1); // /5 \3 \
        g0.connect(2, 5, 5); // (2)----\-----\10 \
        g0.connect(2, 7, 10); // 2/ \ 4 \ 4 \
        g0.connect(4, 3, 1); // (0) 1 _ (4)-----(7)--------(9)
        g0.connect(4, 5, 3); // 1\ / 1| /
        g0.connect(4, 7, 4); // (1) (3) /
        g0.connect(3, 6, 1); // 1\ 1 /
        g0.connect(5, 8, 2); // (6)----------
        g0.connect(6, 9, 1); //
        g0.connect(7, 9, 4); //
        g0.connect(9, 8, 10); //
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        String file_name = System.getProperty("user.dir") + "\\out\\g0.json";
        System.out.println(file_name);
        remove_file(file_name);

        assertEquals(true, ga.save(file_name));
        remove_file(file_name);
    }

    @Test
    public void isConnected_basic1() {
        directed_weighted_graph g0 = new DWGraph_DS();
        g0.addNode(new NodeData(0));
        g0.addNode(new NodeData(100));
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        assertEquals(false, ga.isConnected());
    }

    @Test
    public void isConnected_basic2() {
        directed_weighted_graph g0 = new DWGraph_DS();
        g0.addNode(new NodeData(0));
        g0.addNode(new NodeData(100));
        g0.connect(100, 0, 77.999998);
        g0.connect(0, 100, 77.999998); // I add this connect to make the graph be connected.

        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        assertEquals(true, ga.isConnected());
    }

    @Test
    public void isConnected_advanced1() {
        dw_graph_algorithms ga = new DWGraph_Algo();
        for (int i = 0; i < 1000000; i++) {
            directed_weighted_graph g0 = new DWGraph_DS();
            g0.addNode(new NodeData(0));
            _rnd = new Random(1);
            int v = nextRnd(0, 100);
            for (int j = 1; j < v; j++) {
                g0.addNode(new NodeData(j));
                int localrnd = nextRnd(0, j - 1);
                g0.connect(localrnd, j, j);
                g0.connect(j, localrnd, j);
            }
            ga.init(g0);
            if (!ga.isConnected()) {
                System.out.println("failed with this graph:");
                System.out.println(g0);
                fail();
            }
        }
    }


    @Test
    public void isConnected_advanced2() {
        dw_graph_algorithms ga = new DWGraph_Algo();
        for (int i = 0; i < 1000000; i++) {
            directed_weighted_graph g0 = new DWGraph_DS();
            g0.addNode(new NodeData(0));
            _rnd = new Random(1);
            int v = nextRnd(2, 100);
            for (int j = 1; j < v / 2; j++) {
                g0.addNode(new NodeData(j));
                int localrnd = nextRnd(0, j - 1);
                g0.connect(localrnd, j, j);
                g0.connect(j, localrnd, j);
            }
            g0.addNode(new NodeData(v / 2));
            for (int j = v / 2 + 1; j < v; j++) {
                g0.addNode(new NodeData(j));
                g0.connect(nextRnd(0, j - 1), j, j);
            }
            ga.init(g0);
            if (ga.isConnected()) {
                System.out.println("nodes 0-" + (v / 2 - 1) + " are not connected to nodes " + v / 2 + "-" + (v - 1));
                System.out.println("failed with this graph:");
                System.out.println(g0);
                fail();
            }
        }
    }

    @Test
    public void runtime1() {
        directed_weighted_graph g0 = new DWGraph_DS();
        for (int i = 0; i < 100000; i++) {
            g0.addNode(new NodeData(i));
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 100000; j++) {
                g0.connect(i, j, i + j);
            }
        }
        long start = System.currentTimeMillis();
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        ga.copy();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                ga.shortestPath(i, j);
                ga.shortestPathDist(i, j);
            }
        }
        ga.save("g0");
        ga.load("g0");
        try {
            remove_file("g0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - start) / 1000.0 + "s'");
    }

    @Test
    public void runtime2() {
        long start = System.currentTimeMillis();
        directed_weighted_graph g0 = graph_creator(1000000, 10000000, 1);
        long cur = System.currentTimeMillis() - start;
        System.out.println("graph created after " + cur / 1000.0 + "s'");
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        ga.copy();
        cur = System.currentTimeMillis() - cur;
        System.out.println("performed deep copy in " + cur / 1000.0 + "s'");
        int p = nextRnd(0, 1000000);
        int q = nextRnd(0, 1000000);
        ga.shortestPath(p, q);
        cur = System.currentTimeMillis() - cur;
        System.out.println("performed shortest path in " + cur / 1000.0 + "s'");
        ga.shortestPathDist(p, q);
        cur = System.currentTimeMillis() - cur;
        System.out.println("performed shortest path dist in " + cur / 1000.0 + "s'");
        ga.save("g0");
        cur = System.currentTimeMillis() - cur;
        System.out.println("performed save in " + cur / 1000.0 + "s'");
        ga.load("g0");
        cur = System.currentTimeMillis() - cur;
        System.out.println("performed load in " + cur / 1000.0 + "s'");
        try {
            remove_file("g0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("overall: " + (System.currentTimeMillis() - start) / 1000.0 + "s'");
    }

    @Test
    public void get_graph1() {
        directed_weighted_graph g0 = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        assertEquals(ga.getGraph().hashCode(), g0.hashCode());
    }

    @Test
    public void get_graph2() {
        directed_weighted_graph g0 = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g0);
        assertEquals(g0, ga.getGraph());
    }

}
