public class Testing {
    public static void main(String[] args){
        double[][] data = {{1, 1}, {1, 2}, {2, 1}, {5, 6}, {5, 5}, {4, 5}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {20, 20}};
        DBSCAN db = new DBSCAN(data, 2,  2);
        db.getClusters();
    }
}
