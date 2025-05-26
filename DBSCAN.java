import java.util.ArrayList;
import java.util.HashMap;

public class DBSCAN {
    double[][] matrix;
    double epsilon;
    int minPts;
    HashMap<double[], ArrayList<double[]>> neighborList;
    public DBSCAN(double[][] m, double e, int minpts){
        matrix = m;
        epsilon = e;
        minPts = minpts;
        neighborList = new HashMap<>();
    }

    private double distance(double[] p1, double[] p2){
        double sum = 0;
        for (int i = 0; i < p1.length; i++){
            sum += Math.pow(p1[i] - p2[i], 2);
        }
        return Math.sqrt(sum);
    }

}
