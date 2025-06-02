import java.util.*;

public class Cluster {
    Point cenroid;
    List<Point> points;

    public Cluster(Point firstCentroid){
        this.cenroid = firstCentroid;
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
            cenroid = newCentroid;
        }
    }

    public Point getCenroid() {
        return cenroid;
    }

    public List<Point> getPoints() {
        return points;
    }
}
