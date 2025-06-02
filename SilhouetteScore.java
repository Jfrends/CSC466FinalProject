import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SilhouetteScore {

    public static double euclideanDistance(double[] p1, double[] p2) {
        double sum = 0.0;
        for (int i = 0; i < p1.length; i++) {
            double diff = p1[i] - p2[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    public static double computeSilhouetteScore(List<double[]> points, int[] labels) {
        int n = points.size();
        double total = 0.0;

        for (int i = 0; i < n; i++) {
            double[] pointI = points.get(i);
            int labelI = labels[i];

            double a = 0.0;
            int sameClusterCount = 0;

            for (int j = 0; j < n; j++) {
                if (i != j && labels[j] == labelI) {
                    a += euclideanDistance(pointI, points.get(j));
                    sameClusterCount++;
                }
            }
            if (sameClusterCount == 0) {
                a = 0;
            }
            else {
                a = a/sameClusterCount;
            }

            double b = Double.MAX_VALUE;
            Map<Integer, List<double[]>> clusterMap = new HashMap<>();
            for (int j = 0; j < n; j++) {
                if (labels[j] != labelI) {
                    clusterMap.computeIfAbsent(labels[j], k -> new ArrayList<>()).add(points.get(j));
                }
            }
            for (Map.Entry<Integer, List<double[]>> entry : clusterMap.entrySet()) {
                double avgDist = 0.0;
                List <double[]> otherCluster = entry.getValue();
                for (double[] otherPoint : otherCluster) {
                    avgDist += euclideanDistance(pointI, otherPoint);
                }
                avgDist /= otherCluster.size();
                if (avgDist < b) {
                    b = avgDist;
                }

            }
            double s = 0.0;
            if (a < b) {
                s = 1 - (a/b);
            } else if (a > b) {
                s = (b/a) - 1;
            }
            total += s;

        }
        return total/n;

    }


}
