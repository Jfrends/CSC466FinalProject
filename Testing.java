import java.util.Arrays;
import java.util.List;

public class Testing {
    public static void main(String[] args){
        double[][] data = {{1, 1}, {1, 2}, {2, 1}, {5, 6}, {5, 5}, {4, 5}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {20, 20}};
        DBSCAN db = new DBSCAN(data, 2,  2, false);
        db.getClusters();

        List<double[]> points = Arrays.asList(
                new double[]{1.0, 2.0},
                new double[]{1.1, 2.1},
                new double[]{5.0, 8.0},
                new double[]{5.1, 8.1}
        );
        int[] labels = {0, 0, 1, 1};

        double score = SilhouetteScore.computeSilhouetteScore(points, labels);
        System.out.println("Average silhouette score: " + score);

        String[] categories = {"male", "female"};
        String[] data2 = {"male", "female", "female", "male"};

        OneHotEncoder encoder = new OneHotEncoder(categories);
        double[][] encoded = encoder.transform(data2);

        for (double[] row : encoded) {
            System.out.println(Arrays.toString(row));
        }
    }



}
