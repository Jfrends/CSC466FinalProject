import java.util.Arrays;
import java.util.List;

public class Point {
    double[] coordinates;

    public Point(double... coords) {
        this.coordinates = coords;
    }

    public double distanceTo(Point other) {
        double sum = 0.0;
        for (int i = 0; i < coordinates.length; i++) {
            sum += Math.pow(this.coordinates[i] - other.coordinates[i], 2);
        }
        return Math.sqrt(sum);
    }

    public static Point average(List<Point> points) {
        if (points.isEmpty()) return null;
        int dim = points.get(0).coordinates.length;
        double[] sum = new double[dim];
        for (Point p : points) {
            for (int i = 0; i < dim; i++) {
                sum[i] += p.coordinates[i];
            }
        }
        for (int i = 0; i < dim; i++) {
            sum[i] = sum[i] / points.size();
        }
        return new Point(sum);
    }


    public String toString() {
        return Arrays.toString(coordinates);
    }
}
