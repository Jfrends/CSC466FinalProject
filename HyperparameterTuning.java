import java.awt.image.DataBuffer;
import java.util.List;

public class HyperparameterTuning {
    public static void main(String[] args){
        DBSCAN_tuning();
    }

    public static void DBSCAN_tuning(){
        String donutFile = "src/donut_data.txt";
        Matrix donutMatrix = new Matrix(donutFile,Matrix.DONUTFILE);
        double[][] donutMatrixData = donutMatrix.getData();

        double max = 0;
        double bestE = 0;
        int bestMinPts = 0;
        for (int minPts = 3; minPts < 20; minPts++){
            for (double e = 0.05; e < 2; e+=0.05) {
                DBSCAN db = new DBSCAN(donutMatrixData, e, minPts, false);
                if (db.getNumClusters() == 2){
                    double silhoutteScore = db.getSilhoutteScore();
                    if (silhoutteScore > max){
                        max = silhoutteScore;
                        bestE = e;
                        bestMinPts = minPts;
                    }
                }
            }
        }
        System.out.println("Best e: " + bestE);
        System.out.println("Best minPts: " + bestMinPts);
        System.out.println(max);
        //DBSCAN db = new DBSCAN(donutMatrixData, bestE, bestMinPts, false);
    }

    public static void KMeans_tuning(){
        String donutFile = "src/donut_data.txt";
        Matrix donutMatrix = new Matrix(donutFile,Matrix.DONUTFILE);
        double[][] donutMatrixData = donutMatrix.getData();

        List<Point> donutMatrixPoints = donutMatrix.getListOfPoints();
        double max = 0;
        int bestK = 0;
        for (int k = 2; k < 13; k ++){
            KMeansCluster KMeansClusterDonut = new KMeansCluster(donutMatrixPoints, k, 15);
            double silhoutteScore = KMeansClusterDonut.getSilhoutteScore();
            if (silhoutteScore > max){
                max = silhoutteScore;
                bestK = k;
            }
            System.out.println(silhoutteScore);
        }
        System.out.println("Best k: " + bestK);
        KMeansCluster KMeansClusterDonut = new KMeansCluster(donutMatrixPoints, bestK, 15);
        KMeansClusterDonut.printClusters();
    }
}
