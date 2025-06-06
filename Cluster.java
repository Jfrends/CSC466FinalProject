import java.util.*;

public class Cluster {
    Point centroid;
    List<Point> points;

    public Cluster(Point firstCentroid){
        this.centroid = firstCentroid;
        this.points = new ArrayList<>();
    }



    public void addPoints(Point p){
        points.add(p);
    }

    public void clearPoints(){
        points.clear();
    }

    public void updateCentroid(){
        Point newCentroid = Point.average(points);
        if (newCentroid != null){
            centroid = newCentroid;
        }
    }

    public Point getCentroid() {
        return centroid;
    }

    public List<Point> getPoints() {
        return points;
    }
}
