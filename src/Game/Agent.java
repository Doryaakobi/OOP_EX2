package Game;

import api.*;
import org.json.JSONObject;


@SuppressWarnings("ALL")
public class Agent {
    private Pokemon startPointTreatPokemon;
    public static final double EPS = 0.0001;
    private static int _count = 0;
    private static int _seed = 3331;
    private int ID;
    private double speed;
    private double value;
    private directed_weighted_graph graph;
    private geo_location agentPosition;
    private node_data currentVertex;
    private edge_data currentEdge;
    private boolean isPresent;
    private boolean isChased;
    private long _sg_dt;

    public Agent(directed_weighted_graph g, int startNodeIndex) {
        isChased = false;
        isPresent = false;
        setSpeed(0);
        setMoney(0);
        ID = -1;
        graph = g;
        currentVertex = graph.getNode(startNodeIndex);
        agentPosition = currentVertex.getLocation();
    }

    public void update(String json) {
        JSONObject line;
        try {
            line = new JSONObject(json);
            JSONObject ttt = line.getJSONObject("Agent");
            int id = ttt.getInt("id");
            if (id == this.getID() || this.getID() == -1) {
                if (this.getID() == -1) {
                    ID = id;
                }
                double speed = ttt.getDouble("speed");
                String p = ttt.getString("pos");
                Point3D pp = new Point3D(p);
                int src = ttt.getInt("src");
                int dest = ttt.getInt("dest");
                double value = ttt.getDouble("value");
                this.agentPosition = pp;
                this.setCurrNode(src);
                this.setSpeed(speed);
                this.setNextNode(dest);
                this.setMoney(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setMoney(double v) {
        value = v;
    }

    public boolean setNextNode(int dest) {
        boolean ans = false;
        int src = this.currentVertex.getKey();
        this.currentEdge = graph.getEdge(src, dest);
        if (currentEdge != null) {
            ans = true;
        } else {
            currentEdge = null;
        }
        return ans;
    }

    public int getNextNode() {
        int ans = -2;
        if (currentEdge == null) {
            ans = -1;
        } else {
            ans = currentEdge.getDest();
        }
        return ans;
    }

    public void setCurrNode(int src) {
        this.currentVertex = graph.getNode(src);
    }

    public boolean isMoving() {
        return this.currentEdge != null;
    }

    public String toString() {
        return toJSON();
    }

    public String toString1() {
        String ans = "" + this.getID() + "," + agentPosition + ", " + isMoving() + "," + this.getValue();
        return ans;
    }

    public int getID() {
        return this.ID;
    }

    public geo_location getAgentLocation() {
        return agentPosition;
    }


    public double getValue() {
        return this.value;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double v) {
        this.speed = v;
    }

    public Pokemon get_curr_fruit() {
        return startPointTreatPokemon;
    }

    public void set_curr_fruit(Pokemon curr_fruit) {
        this.startPointTreatPokemon = curr_fruit;
    }


    public int getCurrentVertex() {
        return currentVertex.getKey();
    }

    public void setCurrentVertex(node_data currentVertex) {
        this.currentVertex = currentVertex;
    }

    public edge_data getCurrentEdge() {
        return currentEdge;
    }

    public void setCurrentEdge(edge_data currentEdge) {
        this.currentEdge = currentEdge;
    }

    public void set_SDT(long ddtt) {
        long ddt = ddtt;
        if (this.currentEdge != null) {
            double w = getCurrentEdge().getWeight();
            geo_location dest = graph.getNode(getCurrentEdge().getDest()).getLocation();
            geo_location src = graph.getNode(getCurrentEdge().getSrc()).getLocation();
            double de = src.distance(dest);
            double dist = agentPosition.distance(dest);
            if (this.get_curr_fruit().getEdge() == this.getCurrentEdge()) {
                dist = startPointTreatPokemon.getLocation().distance(this.agentPosition);
            }
            double norm = dist / de;
            double dt = w * norm / this.getSpeed();
            ddt = (long) (1000.0 * dt);
        }
        this.set_sg_dt(ddt);
    }

    public String toJSON() {
        int d = this.getNextNode();
        String ans = "{\"Agent\":{"
                + "\"id\":" + this.ID + ","
                + "\"value\":" + this.value + ","
                + "\"src\":" + this.currentVertex.getKey() + ","
                + "\"dest\":" + d + ","
                + "\"speed\":" + this.getSpeed() + ","
                + "\"pos\":\"" + agentPosition.toString() + "\""
                + "}"
                + "}";
        return ans;
    }

    public long get_sg_dt() {
        return _sg_dt;
    }

    public void set_sg_dt(long _sg_dt) {
        this._sg_dt = _sg_dt;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public boolean isChased() {
        return isChased;
    }

    public void setChased(boolean chased) {
        isChased = chased;
    }
}
