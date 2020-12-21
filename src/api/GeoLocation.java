package api;

public class GeoLocation implements geo_location {

    private double x;
    private double y;
    private double z;

    public GeoLocation(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public GeoLocation(String g){
        String [] pos = g.split(",");
        x = Double.parseDouble(pos[0]);
        y = Double.parseDouble(pos[1]);
        z = Double.parseDouble(pos[2]);
    }
    public GeoLocation(double X, double Y, double Z) {
        x=X;
        y=Y;
        z=Z;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public double distance(geo_location g) {
        double answer=Math.sqrt(Math.pow(x-g.x(),2) + Math.pow(y-g.y(),2) + Math.pow(z-g.z(),2));
        return answer;
    }
}
