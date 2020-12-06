package api;


import java.io.Serializable;
import java.util.Objects;

public class EdgeData implements edge_data, Serializable,Comparable<EdgeData> {

    private int source;
    private int dest;
    private int tag;
    private double weight;
    private String info;


    public EdgeData(int src, int d, double w) {
        this.source = src;
        this.dest = d;
        this.weight = w;
    }


    @Override
    public int getSrc() {
        return this.source;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return info;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        edge_data edgeData = (edge_data) o;
        if (weight == edgeData.getWeight() && source == edgeData.getSrc() && tag == edgeData.getTag()) return true;


        return false;


    }
    @Override
    public int hashCode() {
        return Objects.hash(source, dest, weight);
    }

    @Override
    public String toString() {
        return "EdgeData{" +
                "source=" + source +
                ", dest=" + dest +
                ", weight=" + weight +
                '}';
    }



    @Override
    public int compareTo( EdgeData o) {
        return weight<o.weight?1:-1;
    }
}
