import java.util.*;


public class KMeansCluster {
    private List<Point> points;
    private List<Cluster> clusters;
    private int k;
    private int maxIter;

    public KMeansCluster(List<Point> data, int k, int maxIter){
        this.points = data;
        this.clusters = new ArrayList<>();
        this.k = k;
        this.maxIter = maxIter;
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
                double minDist = p.distanceTo(bestCluster.getCenroid());

                for (int j = 1; j < clusters.size(); j++) {
                    Cluster cluster = clusters.get(j);
                    double dist = p.distanceTo(cluster.getCenroid());
                    if (dist < minDist) {
                        minDist = dist;
                        bestCluster = cluster;
                    }
                }

                bestCluster.addPoints(p);

            }
            for (Cluster cluster: clusters){
                Point oldCentroid = cluster.getCenroid();
                cluster.updateCentroid();
                if (!Arrays.equals(oldCentroid.coordinates, cluster.getCenroid().coordinates)){
                    changed = true;
                }

            }
            if (!changed){
                break;
            }
        }
    }
    public List<Cluster> getClusters() {
        return clusters;
    }
}
