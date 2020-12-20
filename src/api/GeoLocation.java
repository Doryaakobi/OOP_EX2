package api;

public class GeoLocation implements geo_location {

    private double x;
    private double y;
    private double z;

    public GeoLocation(double X, double Y, double Z) {
        x=X;
        y=Y;
        z=Z;
    }
    public GeoLocation(geo_location g) {
        this.x = g.x();
        this.y = g.y();
        this.z = g.z();
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

    @Override
    public double distance(geo_location g) {
        double answer=Math.sqrt(Math.pow(x-g.x(),2) + Math.pow(y-g.y(),2) + Math.pow(z-g.z(),2));
        return answer;
    }
}
