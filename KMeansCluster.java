import java.awt.*;
import java.util.*;
import java.util.List;


public class KMeansCluster {
    private List<Point> points;
    private List<Cluster> clusters;
    private int k;
    private int maxIter;

    private HashMap<Point, Integer> clusterMap =  new HashMap<>();


    public KMeansCluster(List<Point> data, int k, int maxIter){
        this.points = data;
        this.clusters = new ArrayList<>();
        this.k = k;
        this.maxIter = maxIter;
        algo();
    }

    public void algo(){
        Random rand = new Random();
        Set<Integer> choose = new HashSet<>();

        for (int i = 0; i<k; i++){
            int index;
            do {
                index = rand.nextInt(points.size());
            } while (choose.contains(index));
            choose.add(index);
            clusters.add(new Cluster(points.get(index)));
        }
        for (int iter = 0; iter < maxIter; iter++){
            boolean changed = false;
            for (Cluster cluster : clusters){
                cluster.clearPoints();
            }
            for (Point p: points){
                Cluster bestCluster = clusters.get(0);
                double minDist = p.distanceTo(bestCluster.getCentroid());

                for (int j = 1; j < clusters.size(); j++) {
                    Cluster cluster = clusters.get(j);
                    double dist = p.distanceTo(cluster.getCentroid());
                    if (dist < minDist) {
                        minDist = dist;
                        bestCluster = cluster;
                    }
                }

                bestCluster.addPoints(p);

            }
            for (Cluster cluster: clusters){
                Point oldCentroid = cluster.getCentroid();
                cluster.updateCentroid();
                if (!Arrays.equals(oldCentroid.coordinates, cluster.getCentroid().coordinates)){
                    changed = true;
                }

            }
            if (!changed){
                break;
            }
        }
        int numClusters = 0;
        for (Cluster c : clusters){
            numClusters++;
            for (Point p : c.getPoints()){
                clusterMap.put(p, numClusters);
            }
        }
    }
    public List<Cluster> getClusters() {
        return clusters;
    }

    public void printClusters(){
        for (Point point : clusterMap.keySet()){
            System.out.println(point.coordinates[0] + ", " + point.coordinates[1] + ", " + clusterMap.get(point));
        }
    }

    public double getSilhoutteScore(){
        ArrayList<double[]> pointsList = new ArrayList<>();
        int[] c = new int[clusterMap.size()];
        int i = 0;
        for (Point point : clusterMap.keySet()){
            c[i] = clusterMap.get(point);
            pointsList.add(point.coordinates);
            i++;
        }
        return SilhouetteScore.computeSilhouetteScore(pointsList, c);
    }
}
