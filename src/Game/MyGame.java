package Game;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("ALL")
public class MyGame implements Runnable {

    public static directed_weighted_graph graph;
    public static dw_graph_algorithms algo;
    public static Arena myArena;
    public static List<Agent> myAgents;
    public static List<Pokemon> myPokemons;
    public static game_service myGame;
    public int myGameLevel;
    public int score;
    public int totalMovesMade;
    public static HashMap<Integer, Pokemon> caughtTargets = new HashMap<>();
    public static LinkedHashMap<Integer, HashMap<Integer, List<node_data>>> journey;
    public static HashMap<Integer, HashMap<Integer, Double>> weightedMap;
    public static Window window;

    public MyGame(String level) {
        int after_parse;
        try {
            after_parse = Integer.parseInt(level);
           myGameLevel = after_parse;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        myGame = Game_Server_Ex2.getServer(myGameLevel);
        initialize(myGame);
        myGame.startGame();

        int ind = 0;

        while (myGame.isRunning()) {
            window.update(myArena);
            try {
                if (ind % 1 == 0) {
                    window.repaint();
                }
                synchronized (this) {
                    moveAgents(myGame, graph);
                }
                if (noAgentOnPokemonEdge() != null) {
                    double d = sleepTime();
                    Thread.sleep((long) (d));
                } else {
                    Thread.sleep(150);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            refresh();
        }




        String res = myGame.toString();
        System.out.println(res);
        System.out.println("Result :");
        System.out.println("score is: " + score + ", and the moves are: " + totalMovesMade);
        System.out.println();
        System.exit(0);
    }



    private void moveAgents(game_service game, directed_weighted_graph g) {
        int srcPok = 0;
        String agentsMove = game.move();
        myAgents = Arena.getAgents(agentsMove, g);
        myArena.setAgents(myAgents);
        myPokemons = Arena.json2Pokemons(game.getPokemons());
        for (Pokemon pok : myPokemons) {
            myArena.updateEdge(pok, g);
        }
        myArena.setPokemons(myPokemons);
        for (Agent agent : myAgents) {
            int dest = agent.getNextNode();
            int src = agent.getCurrentVertex();
            if (dest == -1) {
                agent.setPresent(false);
                synchronized (Thread.currentThread()) {
                    Pokemon pok = findValuestPokemon(src);
                    srcPok = pok.getEdge().getSrc();
                    dest = nextNode(agent, srcPok, pok);
                    agent.set_curr_fruit(pok);
                    caughtTargets.put(agent.getID(), pok);
                    pok.setAimOn();
                    if (graph.getE(dest).size() == 0) {
                        dest = (int) (Math.random() * graph.nodeSize());
                    }
                    game.chooseNextEdge(agent.getID(), dest);
                }
            }
        }
    }

    private int randDest() {
        int num_of_nodes = graph.nodeSize();
        int rand = (int) (Math.random() * num_of_nodes);
        return rand;
    }

    private int nextNode(Agent a, int pokSrc, Pokemon pok) {
        if (a.getCurrentVertex() == pokSrc) {
            a.setPresent(true);
            pok.setAimOn();
            return pok.getEdge().getDest();
        }
        List<node_data> path = journey.get(a.getCurrentVertex()).get(pokSrc);
        if(path.size() == 1) {
            return path.get(0).getKey();
        }
        return path.get(1).getKey();
    }

    public Pokemon findValuestPokemon(int srcAgent) {
        int pokIndex = 0;
        double max = 0;
        double dis;
        double valueWithDis;
        for (int i = 0; i < myPokemons.size(); i++) {
            if (!(caughtTargets.containsValue(myPokemons.get(i)))) {
                if (!(myPokemons.get(i).isAimOn())) {
                    dis = weightedMap.get(srcAgent).get(myPokemons.get(i).getEdge().getSrc());
                    valueWithDis = (myPokemons.get(i).getValue() / dis);
                    if (valueWithDis > max) {
                        max = valueWithDis;
                        pokIndex = i;
                    }
                }
            }
        }
        return myPokemons.get(pokIndex);
    }


    public double sleepTime() {
        double de, w, d;
        double dist = 0;
        double min = -1;
        for (Agent agent : myAgents) {
            if (caughtTargets.get(agent.getID()) != null) {
                if (caughtTargets.get(agent.getID()).getEdge() != null) {
                    edge_data e = caughtTargets.get(agent.getID()).getEdge();
                    w = e.getWeight();
                    geo_location dest = graph.getNode(e.getDest()).getLocation();
                    geo_location src = graph.getNode(e.getSrc()).getLocation();
                    de = src.distance(dest);
                    if (agent.get_curr_fruit() != null) {
                        dist = agent.get_curr_fruit().getLocation().distance(agent.getAgentLocation());
                    }
                    double norm = dist / de;
                    double dt = w * norm / agent.getSpeed();
                    d = (long) (1000.0 * dt);
                    if (min == -1 || d < min) {
                        min = d;
                    }
                }
            }
        }
        if (min == 0) {
            return 10;
        }
        if (min != -1 && dist != 0) {
            return min;
        }
        return 100;
    }

    public Agent noAgentOnPokemonEdge() {
        for (Agent a : myAgents) {
            if (a.isPresent()) {
                return a;
            }
        }
        return null;
    }

    public void refresh() {
        String gameString = myGame.toString();
        Gson gson = new Gson();
        JsonObject jsonObjectGame = gson.fromJson(gameString, JsonObject.class);
        JsonObject gameServer = jsonObjectGame.getAsJsonObject("GameServer");
        myGameLevel = gameServer.get("game_level").getAsInt();
        myArena.setLevel(myGameLevel);
        score = gameServer.get("grade").getAsInt();
        myArena.setScore(score);
        totalMovesMade = gameServer.get("moves").getAsInt();
        myArena.setTotalMoves(totalMovesMade);
        myArena.setTimer(myGame.timeToEnd());
    }



    void mapsConfig(directed_weighted_graph d) {
        journey = new LinkedHashMap<Integer, HashMap<Integer, List<node_data>>>();
        weightedMap = new HashMap<Integer, HashMap<Integer, Double>>();
        for (node_data v : d.getV()) {
            HashMap<Integer, List<node_data>> j = new HashMap<>();
            HashMap<Integer, Double> w = new HashMap<>();
            weightedMap.put(v.getKey(), w);
            journey.put(v.getKey(), j);
        }
        for (node_data v : d.getV()) {
            for (node_data w : d.getV()) {
                double weight = algo.shortestPathDist(v.getKey(), w.getKey());
                List<node_data> shortPathList = algo.shortestPath(v.getKey(), w.getKey());
                journey.get(v.getKey()).put(w.getKey(), shortPathList);
                weightedMap.get(v.getKey()).put(w.getKey(), weight);
            }
        }
    }

    void initialize(game_service game) {
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        int w=d.width;
        int h=d.height;
        myArena = new Arena();
        algo = new DWGraph_Algo();
        algo.init(graphFJson(game.getGraph()));
        graph = algo.getGraph();
        myArena.setGraph(graph);
        myPokemons = (Arena.json2Pokemons(game.getPokemons()));
        myArena.setPokemons(myPokemons);
        window = new Window("Ex2");
        window.setSize(w, h);
        window.update(myArena);
        window.setVisible(true);
        mapsConfig(graph);
        for (int a = 0; a < myPokemons.size(); a++) {
            Arena.updateEdge(myPokemons.get(a), graph);
        }
        optimal_starting_point_for_the_agents();

    }

    void optimal_starting_point_for_the_agents() {
        JSONObject object;
        try {
            object = new JSONObject(myGame.toString());
            JSONObject server = object.getJSONObject("GameServer");
            int ag = server.getInt("agents");
            for (int indx = 0; indx < ag; indx++) {
                Pokemon p = myPokemons.get(indx % myPokemons.size());
                int optimal = p.getEdge().getDest();
                if (p.getType() < 0) {
                    optimal = p.getEdge().getSrc();
                }

                myGame.addAgent(optimal);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public directed_weighted_graph graphFJson(String graph) {
        GraphDeserializer graphDeserializer = new GraphDeserializer();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(directed_weighted_graph.class, graphDeserializer);
        Gson gson = gsonBuilder.create();
        directed_weighted_graph graphFjson = gson.fromJson(graph, directed_weighted_graph.class);
        return graphFjson;

    }
}
