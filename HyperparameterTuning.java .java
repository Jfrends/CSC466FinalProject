import java.util.ArrayList;
import java.util.List;

public class HyperparameterTuning {

    public static void main(String[] args) {
        String filePath = "/Users/justinmai/Downloads/donut_data.txt";
        int fileType = Matrix.DONUTFILE;


        Matrix matrix = new Matrix(filePath, fileType);
        List<Point> points = matrix.getListOfPoints();

        List<double[]> pointsAsDoubleArray = new ArrayList<>();
        for (Point p : points) {
            pointsAsDoubleArray.add(p.coordinates);
        }


        int bestK = -1;
        double bestSilhouetteScore = -2.0;

        int minK = 2;
        int maxK = 10;


        for (int k = minK; k <= maxK; k++) {

            KMeansCluster kMeans = new KMeansCluster(points, k, 100);
            kMeans.algo();
            List<Cluster> clusters = kMeans.getClusters();


            int[] labels = new int[points.size()];
            for (int i = 0; i < points.size(); i++) {
                Point p = points.get(i);
                for (int clusterId = 0; clusterId < clusters.size(); clusterId++) {

                    if (clusters.get(clusterId).getPoints().contains(p)) {
                        labels[i] = clusterId;
                        break;
                    }
                }
            }


            double silhouetteScore = SilhouetteScore.computeSilhouetteScore(pointsAsDoubleArray, labels);
            System.out.printf(k, silhouetteScore);


            if (silhouetteScore > bestSilhouetteScore) {
                bestSilhouetteScore = silhouetteScore;
                bestK = k;
            }
        }

        System.out.println("\nTuning Complete.");
        System.out.printf("Best k: %d\n", bestK);
        System.out.printf("Highest Silhouette Score: ", bestSilhouetteScore);
    }
}
