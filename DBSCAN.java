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
    private final boolean debugMode;
    public DBSCAN(double[][] data, double epsilon_value, int minimumPts, boolean debugModeOn){
        matrix = data;
        epsilon = epsilon_value;
        minPts = minimumPts;
        neighborList = new HashMap<>();
        corePoints = new ArrayList<>();
        clusterMap = new HashMap<>();
        clusters = new HashMap<>();
        debugMode = debugModeOn;
        populateNeighbors();
        if (debugMode) {
            System.out.print("Neighbors: ");
            for (double[] point : neighborList.keySet()) {
                System.out.print(Arrays.toString(point) + "'s neighbors: ");
                for (double[] neighbor : neighborList.get(point)) {
                    System.out.print(Arrays.toString(neighbor) + "  ");
                }
                System.out.println();
            }
        }
        populateCorePoints();
        if (debugMode) {
            System.out.print("Core points: ");
            for (double[] point : corePoints) {
                System.out.print(Arrays.toString(point) + " ");
            }
            System.out.println();
        }
        DBSCAN();
    }

    public void populateNeighbors(){ // Calculates distances between each pair of points and adds them to the neighborList if they are less than epsilon apart from each other
        for (int i = 0; i < matrix.length; i++){
            for (int j = i+1; j < matrix.length; j++){
                if (Processing.distance(matrix[i], matrix[j]) <= epsilon){
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
            if (debugMode) {
                System.out.println("Random point: " + Arrays.toString(randomCorePoint));
            }
            numClusters++;
            clusterMap.put(randomCorePoint, numClusters);
            clusters.put(numClusters, new ArrayList<>());
            clusters.get(numClusters).add(randomCorePoint);
            corePoints.remove(randomCorePoint);
            for (double[] neighbor : neighborList.get(randomCorePoint)){
                if (!clusterMap.containsKey(neighbor)) {
                    clusterMap.put(neighbor, numClusters);
                    clusters.get(numClusters).add(neighbor);
                    DBRecur(neighbor);
                }
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
            corePoints.remove(point);
            for (double[] neighbor : neighborList.get(point)){
                if (!clusterMap.containsKey(neighbor)){
                    clusterMap.put(neighbor, numClusters);
                    clusters.get(numClusters).add(neighbor);
                    DBRecur(neighbor);
                }
            }
        }
    }

    public void getClusters(){
        for (int i = 1; i < clusters.keySet().size(); i++){
            System.out.println("Cluster " + i + ": ");
            for (int j = 0; j < clusters.get(i).size(); j++){
                System.out.print(Arrays.toString(clusters.get(i).get(j)) + "   ");
            }
            System.out.println();
        }
        System.out.print("Noise points: ");
        for (int j = 0; j < clusters.get(0).size(); j++){
            System.out.print(Arrays.toString(clusters.get(0).get(j)) + "   ");
        }
        System.out.println();
    }
}
