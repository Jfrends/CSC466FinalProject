import java.util.Arrays;
import java.util.List;

public class Testing {
    public static void main(String[] args){

        //testing to see if Matrix Constructor properly populated matrix with file names
        String mallFile = "C://Users//tarar//Downloads//Mall_Customers.csv";
        Matrix mallMatrix = new Matrix(mallFile, Matrix.MALLFILE);


        //Mall File K Means Clustering:
        List<Point> mallMatrixPoints = mallMatrix.getListOfPoints();
        KMeansCluster KMeansClusterMall = new KMeansCluster(mallMatrixPoints, 5, 15);
        KMeansClusterMall.algo();
        List<Cluster> listClusters = KMeansClusterMall.getClusters();
        /*for(Cluster cluster : listClusters)
        {
            Point centroid = cluster.getCenroid();
            List<Point> points = cluster.getPoints();
            System.out.println("Centroid is:  " + centroid.toString());
            System.out.println("Points are below: ");
            for(Point point : points)
            {
                System.out.println(point.toString());
            }
        }*/

        //Reading in the donutData File
        String donutFile = "C://Users//tarar//Downloads//donut_data.txt";
        Matrix donutMatrix = new Matrix(donutFile,Matrix.DONUTFILE);
        double[][] donutMatrixData = donutMatrix.getData();

        //K means clustering for donutData File below:
        List<Point> donutMatrixPoints = donutMatrix.getListOfPoints();
        KMeansCluster KMeansClusterDonut = new KMeansCluster(donutMatrixPoints, 5, 15);
        KMeansClusterDonut.algo();
        List<Cluster> donutListClusters = KMeansClusterDonut.getClusters();

        /*System.out.println("Donut Clusters are Printed Below: ");
        for(Cluster cluster : donutListClusters)
        {
            Point centroid = cluster.getCenroid();
            List<Point> points = cluster.getPoints();
            System.out.println("Centroid is:  " + centroid.toString());
            System.out.println("Points are below: ");
            for(Point point : points)
            {
                System.out.println(point.toString());
            }
        }*/

        /*System.out.println("normalized donutData is below: ");
        for(int row = 0; row < donutMatrixData.length; row++)
        {
            for(int col = 0; col < donutMatrixData[row].length; col++)
            {
                System.out.print(donutMatrixData[row][col] + " ");
            }
            System.out.println();

        }*/

        //donutData K Means Clustering:







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
        Matrix numericMatrix = new Matrix(new double[][] {
                {1.0, 2.0},
                {3.0, 4.0},
                {5.0, 6.0},
                {7.0, 8.0}
        });

        String[] gender = {"Male", "Female", "Female", "Male"};

        Matrix full = MatrixUtils.addOneHotColumn(numericMatrix, gender, new String[]{"Male", "Female"});

        for (double[] row : full.getData()) {
            System.out.println(Arrays.toString(row));
        }
    }



}
