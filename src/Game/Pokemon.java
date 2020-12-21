package Game;

import api.*;

import org.json.JSONObject;

@SuppressWarnings("ALL")
public class Pokemon {
    private edge_data edge;
    private double value;
    private int type;
    private Point3D location;
    private boolean aimOn;
    private double minDist;
    private int min_ro;


    public Pokemon(Point3D p,int t,double v,double s,edge_data e){
        value=v;
        min_ro=-1;
        location=p;
        type=t;
        edge=e;
        aimOn=false;
        minDist=-1;
    }
    public static Pokemon init_from_json(String json){
        Pokemon ans=null;
        try {
            JSONObject p=new JSONObject(json);
            int id=p.getInt("id");

        }catch (Exception e){
            e.printStackTrace();
        }
        return ans;
    }
    public String toString(){return  "F:{v="+value+", t="+type+"}";}

    public edge_data getEdge() {
        return edge;
    }

    public double getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    public Point3D getLocation() {
        return location;
    }

    public boolean isAimOn() {
        return aimOn;
    }

    public double getMinDist() {
        return minDist;
    }

    public int getMin_ro() {
        return min_ro;
    }

    public void setEdge(edge_data edge) {
        this.edge = edge;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public void setAimOn() {
        this.aimOn = true;
    }

    public void setMinDist(double minDist) {
        this.minDist = minDist;
    }

    public void setMin_ro(int min_ro) {
        this.min_ro = min_ro;
    }
}
