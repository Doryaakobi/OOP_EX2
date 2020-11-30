package api;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class NodeData implements node_data, Serializable,Comparable<NodeData> {
    private int key;
    private int tag;
    private geo_location nodeLocation;
    private double weight;
    private String info;

    public NodeData(int id) {
        this.key = id;
    }

    public NodeData(node_data other) {
        this.key = other.getKey();
        this.info = other.getInfo();
        this.tag = other.getTag();
        this.weight = other.getWeight();
        this.nodeLocation = other.getLocation();
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public geo_location getLocation() {
        return nodeLocation;
    }

    @Override
    public void setLocation(geo_location p) {
        this.nodeLocation = p;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj.getClass() != this.getClass() || obj == null) return false;
        NodeData n = (NodeData) obj;
        return key == n.key && Objects.equals(info, n.info);
    }

    @Override
    public String toString() {
        return "NodeData{" +
                "key=" + key +
//                ", tag=" + tag +
////                ", nodeLocation=" + nodeLocation +
//                ", weight=" + weight +
                '}';
    }

    public static void main(String[] args) {
        node_data n0=new NodeData(1);
        System.out.println(n0);
    }

    @Override
    public int compareTo(@NotNull NodeData o) {
        return weight<o.weight?1:-1;
    }
}
