//package api;
//
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DWGraph_AlgoTest {
//
//    static dw_graph_algorithms ga = new DWGraph_Algo();
//    static directed_weighted_graph g1 = new DWGraph_DS();
//    static directed_weighted_graph g2 = new DWGraph_DS();
//    static directed_weighted_graph g3 = new DWGraph_DS();
//    static directed_weighted_graph g4 = new DWGraph_DS();
//    static directed_weighted_graph g5 = new DWGraph_DS();
//
//    /**
//     * This functions create new graph with number of vertex by the parameter size
//     * */
//    static void createG(int size, directed_weighted_graph g) {
//        for(int i = 1; i <= size; i++){
//            g.addNode(new NodeData(i));
//        }
//    }
//    /**
//     * This functions set Up new graphs with number of vertex by the parameter size
//     * */
//    @BeforeAll
//    static void setUp() {
//        createG(10,g1);
//        g1.connect(1,2,0.5);
//        g1.connect(1,3,2.5);
//        g1.connect(3,4,1.98);
//        g1.connect(2,5,8.3);
//        g1.connect(5,7,4.1);
//        g1.connect(9,7,2.4);
//        g1.connect(6,7,3.1);
//        g1.connect(7,6,3.1);
//        g1.connect(8,9,1.8);
//        g1.connect(4,9,9.6);
//        g1.connect(2,6,5.6);
//
//        createG(10,g2);
//        g2.connect(1,6,5);
//        g2.connect(1,5,20);
//        g2.connect(1,4,20);
//        g2.connect(1,2,10);
//        g2.connect(1,7,15);
//        g2.connect(2,4,10);
//        g2.connect(2,3,5);
//        g2.connect(3,4,5);
//        g2.connect(3,2,15);
//        g2.connect(4,5,10);
//        g2.connect(5,6,5);
//        g2.connect(7,6,10);
//        g2.connect(8,7,5);
//        g2.connect(8,1,5);
//        g2.connect(8,2,20);
//        g2.connect(9,8,20);
//        g2.connect(9,2,15);
//        g2.connect(9,10,10);
//        g2.connect(10,3,15);
//        g2.connect(10,2,5);
//
//        createG(4,g3);
//        g3.connect(1,2,5);
//        g3.connect(3,2,5);
//        g3.connect(1,3,5);
//        g3.connect(4,1,5);
//        g3.connect(2,4,5);
//        g3.connect(4,2,5);
//
//        createG(4,g4);
//        g4.connect(1,2,5);
//        g4.connect(3,2,5);
//        g4.connect(3,1,5);
//        g4.connect(1,4,5);
//        g4.connect(2,4,5);
//        g4.connect(4,2,5);
//
//
//        createG(3,g5);
//        g5.connect(1,2,5);
//        g5.connect(2,3,5);
//        g5.connect(3,1,5);
//
//    }
//    /**
//     * We are tests all the graph in this class.
//     * This functions tests the isConnected function for the class DWGraph_DS
//     * */
//    @Test
//    void isConnected() {
//        ga.init(g1);
//        System.out.println(g1);
//        System.out.println();
//        assertFalse(ga.isConnected());
//
//        ga.init(g2);
//        System.out.println(g2);
//        System.out.println("g2 = " + ga.isConnected());
//        assertFalse(ga.isConnected());
//        ga.init(g3);
//        System.out.println(g3);
//        System.out.println("g3 = " + ga.isConnected());
//        assertTrue(ga.isConnected());
//        ga.init(g4);
//        System.out.println("g4 = " + ga.isConnected());
//        assertFalse(ga.isConnected());
//        ga.init(g5);
//        System.out.println("g5 = " + ga.isConnected());
//        assertTrue(ga.isConnected());
//    }
//    /**
//     * We are tests all the graph in this class.
//     * This functions tests the shortestPathDistGraph1 function for the class DWGraph_DS
//     * */
//    @Test
//    void shortestPathDistGraph1() {
//        ga.init(g1);
//        assertEquals(5.6,ga.shortestPathDist(2,6));
//        assertEquals(0.5,ga.shortestPathDist(1,2));
//        assertEquals(4.48,ga.shortestPathDist(1,4));
//        assertEquals(7.300000000000001,ga.shortestPathDist(8,6));
//        assertEquals(-1.0,ga.shortestPathDist(9,4));
//        assertEquals(0.0,ga.shortestPathDist(3,3));
//        assertEquals(-1.0,ga.shortestPathDist(20,20));
//        assertEquals(-1.0,ga.shortestPathDist(1,20));
//    }
//    /**
//     * We are tests all the graph in this class.
//     * This functions tests the shortestPathDistGraph2 function for the class DWGraph_DS
//     * */
//    @Test
//    void shortestPathDistGraph2() {
//        ga.init(g2);
//        assertEquals(25.0,ga.shortestPathDist(2,6));
//        assertEquals(10.0,ga.shortestPathDist(1,2));
//        assertEquals(20.0,ga.shortestPathDist(1,4));
//        assertEquals(10.0,ga.shortestPathDist(8,6));
//        assertEquals(25.0,ga.shortestPathDist(9,4));
//        assertEquals(25.0,ga.shortestPathDist(9,1));
//        assertEquals(-1.0,ga.shortestPathDist(10,1));
//        assertEquals(0.0,ga.shortestPathDist(3,3));
//        assertEquals(-1.0,ga.shortestPathDist(20,20));
//        assertEquals(-1.0,ga.shortestPathDist(1,20));
//        assertEquals(5.0,ga.shortestPathDist(3,4));
//        assertEquals(30.0,ga.shortestPathDist(10,6));
//    }
//    /**
//     * We are tests all the graph in this class.
//     * This functions tests the shortestPathGraph1 function for the class DWGraph_DS
//     * */
//    @Test
//    void shortestPathGraph1() {
//        ga.init(g1);
//        assertEquals("[#4, #9, #7, #6]", ga.shortestPath(4,6).toString());
//        assertEquals("[#1, #2, #6, #7]", ga.shortestPath(1,7).toString());
//        assertEquals("[#3, #4, #9, #7, #6]", ga.shortestPath(3,6).toString());
//        assertEquals("[#1]", ga.shortestPath(1,1).toString());
//        assertEquals("[#2, #6]", ga.shortestPath(2,6).toString());
//        assertEquals("[#4, #9, #7]", ga.shortestPath(4,7).toString());
//        assertNull(ga.shortestPath(0,3));
//        assertNull(ga.shortestPath(20,4));
//        assertNull(ga.shortestPath(8,4));
//        assertNull(ga.shortestPath(7,1));
//    }
//
//    /**
//     * We are tests all the graph in this class.
//     * This functions tests the shortestPathGraph2 function for the class DWGraph_DS
//     * */
//    @Test
//    void shortestPathGraph2() {
//        ga.init(g2);
//        assertEquals("[#8, #1, #6]", ga.shortestPath(8,6).toString());
//        assertEquals("[#8, #1, #5]", ga.shortestPath(8,5).toString());
//        assertEquals("[#9, #2, #4, #5]", ga.shortestPath(9,5).toString());
//        assertEquals("[#10, #2]", ga.shortestPath(10,2).toString());
//        assertEquals("[#10]", ga.shortestPath(10,10).toString());
//        assertEquals("[#10]", ga.shortestPath(10,10).toString());
//        assertNull(ga.shortestPath(11,3));
//        assertNull(ga.shortestPath(11,11));
//        assertNull(ga.shortestPath(6,1));
//        assertNull(ga.shortestPath(6,2));
//        assertNull(ga.shortestPath(6,3));
//        assertNull(ga.shortestPath(6,4));
//        assertNull(ga.shortestPath(6,5));
//    }
//    /**
//     * We are tests all the graph in this class.
//     * This functions tests the save_Load function for the class DWGraph_DS
//     * */
//    @Test
//    void save_Load(){
//        ga.init(g1);
//        //System.out.println(g1.toString());
//        ga.save("e.json");
//        dw_graph_algorithms new_ga = new DWGraph_Algo();
//        directed_weighted_graph g = g1;
//        for (int i = 0; i < 6; i++){
//            new_ga.load("files/A"+i+"");
//            assertNotEquals(g,new_ga.getGraph());
//            System.out.println("after load graph A"+i+" from Json:\n" + new_ga.getGraph());
//            g = new_ga.getGraph();
//        }
//    }
//
//}