import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;


public class DBSCAN {
    private final double[][] matrix; // Our data
    private final double epsilon; // Min distance to be considered neighbor
    private final int minPts; //  Min number of neighbors to be a core point
    private ArrayList<double[]> corePoints; // List of all core points regardless of cluster
    private int numClusters = 0; // Keeps track of cluster labels. Increments every time a new cluster is created
    private HashMap<double[], Integer> clusterMap; // Holds cluster information for each point
    private HashMap<Integer, ArrayList<double[]>> clusters; // Hplds clusters
    HashMap<double[], ArrayList<double[]>> neighborList;
    public DBSCAN(double[][] m, double e, int minpts){
        matrix = m;
        epsilon = e;
        minPts = minpts;
        neighborList = new HashMap<>();
        corePoints = new ArrayList<>();
        clusterMap = new HashMap<>();
        clusters = new HashMap<>();
    }

    private double distance(double[] p1, double[] p2){ // Calculates L2 Norm distance between two points
        double sum = 0;
        for (int i = 0; i < p1.length; i++){
            sum += Math.pow(p1[i] - p2[i], 2);
        }
        return Math.sqrt(sum);
    }

    public void populateNeighbors(){ // Calculates distances between each pair of points and adds them to the neighborList if they are less than epsilon apart from each other
        for (int i = 0; i < matrix.length; i++){
            for (int j = i+1; j < matrix.length; j++){
                if (distance(matrix[i], matrix[j]) <= epsilon){
                    if (!neighborList.containsKey(matrix[i])){
                        neighborList.put(matrix[i], new ArrayList<>());
                    }
                    if (!neighborList.containsKey(matrix[j])){
                        neighborList.put(matrix[j], new ArrayList<>());
                    }
                    neighborList.get(matrix[i]).add(matrix[j]);
                    neighborList.get(matrix[j]).add(matrix[i]);
                }
            }
        }
    }

    public void populateCorePoints(){ // Goes through neighborList and adds all points with at least minPts neighbors. Should be run after populateNeighbors
        for (double [] row : neighborList.keySet()){
            if (neighborList.get(row).size() >= minPts){
                corePoints.add(row);
            }
        }
    }

    public void DBSCAN(){ // Main function for the algorithm. Selects a random core point, assigns it to a cluster if it isn't a part of one already, and recurs on neighbors until no more core points are left uncategorized.
        Random rand = new Random();
        while (!corePoints.isEmpty()) {
            double[] randomCorePoint = corePoints.get(rand.nextInt(corePoints.size()));
            numClusters++;
            clusterMap.put(randomCorePoint, numClusters);
            clusters.put(numClusters, new ArrayList<>());
            clusters.get(numClusters).add(randomCorePoint);
            corePoints.remove(randomCorePoint);
            for (double[] neighbor : neighborList.get(randomCorePoint)){
                clusterMap.put(neighbor, numClusters);
                clusters.get(numClusters).add(neighbor);
                corePoints.remove(neighbor);
                DBRecur(neighbor);
            }
        }
        clusters.put(0, new ArrayList<>());
        for (double[] datapoint : matrix){
            if (!clusterMap.containsKey(datapoint)){
                clusterMap.put(datapoint, 0);
                clusters.get(0).add(datapoint);
            }
        }
    }

    public void DBRecur(double[] point){
        if (corePoints.contains(point)){
            for (double[] neighbor : neighborList.get(point)){
                clusterMap.put(neighbor, numClusters);
                clusters.get(numClusters).add(neighbor);
                corePoints.remove(neighbor);
                DBRecur(neighbor);
            }
        }
    }
}
