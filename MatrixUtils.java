public class MatrixUtils {

    // horizontally concatenate two matrices
    public static Matrix concatenate(Matrix a, Matrix b) {
        double[][] dataA = a.getData();
        double[][] dataB = b.getData();

        int rows = a.numRows();
        int colsA = dataA[0].length;
        int colsB = dataB[0].length;

        double[][] result = new double[rows][colsA + colsB];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colsA; j++) {
                result[i][j] = dataA[i][j];
            }
            for (int j = 0; j < colsB; j++) {
                result[i][colsA + j] = dataB[i][j];
            }
        }

        return new Matrix(result);
    }

    // encodes a categorical string column and returns a Matrix
    public static Matrix oneHotEncodeColumn(String[] column, String[] knownCategories) {
        OneHotEncoder encoder = new OneHotEncoder(knownCategories);
        double[][] encodedData = encoder.transform(column);
        return new Matrix(encodedData);
    }

    //combines a matrix with the string column
    public static Matrix addOneHotColumn(Matrix original, String[] column, String[] knownCategories) {
        Matrix oneHotMatrix = oneHotEncodeColumn(column, knownCategories);
        return concatenate(original, oneHotMatrix);
    }
}

