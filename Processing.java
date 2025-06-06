public class Processing {


    public static void testFunction()
    {
        System.out.println("testing");
    }

    public static double[][] normalize(double[][] matrix, int end){
        double [][] output_matrix = new double[matrix.length][matrix[0].length];
        for (int col = 0; col < end; col++){
            for (int row = 0; row < matrix.length; row++){

                output_matrix[row][col] = standardize(matrix, row, col);
            }
        }
        return output_matrix;
    }

    public static double distance(double[] p1, double[] p2){ // Calculates L2 Norm distance between two points
        double sum = 0;
        for (int i = 0; i < p1.length; i++){
            sum += Math.pow(p1[i] - p2[i], 2);
        }
        return Math.sqrt(sum);
    }

    public static double standardize(double[][] matrix, int row, int col){
        double mean =  mean(matrix, col);
        return (matrix[row][col] - mean) / stddev(matrix, col, mean);
    }

    private static double mean(double[][] matrix, int col){
        double sum = 0;
        for (double[] doubles : matrix) {
            sum += doubles[col];
        }
        return sum / matrix.length;
    }

    private static double stddev(double[][] matrix, int col, double mean){
        double stddev = 0;
        for (double[] doubles : matrix) {
            stddev += Math.pow((doubles[col] - mean), 2);
        }
        stddev = Math.sqrt(stddev/matrix.length);
        return stddev;
    }



}
