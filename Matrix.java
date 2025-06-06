import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Matrix {
    private double[][] data;

    public static int DONUTFILE = 1;
    public static int MALLFILE = 2;
    //Constructors
    public Matrix(double[][] data) {
        this.data = data;
    }

    public Matrix(String fileName, int typeOfFile)
    {
        if(typeOfFile == DONUTFILE)
        {
            //create donut matrix
            createDonutMatrix(fileName);
        }
        else if(typeOfFile == MALLFILE)
        {
            //create mall matrix
            createMallMatrix(fileName);
        }
    }

    public void createMallMatrix(String fileName)
    {

        int numCols = 0;
        int numRows = 0;
        File file = new File(fileName);
        Scanner scanner = null;

        //determining number of rows and columns in the data set and initializing its dimensions
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineElements = line.split(",");
                numCols = lineElements.length;
                numRows++;

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("NumCols: " + numCols + " NumRows: " + numRows);



        data = new double[numRows-1][numCols];

        //this is temporarily storing just Age, Annual Income, Spending Score; Gender is excluded here
        double[][] dataTemp = new double[numRows-1][numCols-2];


        //genderColumn temporarily stores gender
        String[] genderColumn = new String[numRows-1];

        try {
            Scanner scanner2 = new Scanner(file);
            int row = 0;
            while(scanner2.hasNextLine())
            {

                String line = scanner2.nextLine().trim();
                //excluding the header row
                if(row == 0)
                {
                    row++;
                    continue;
                }


                String[] elements = line.split(",");

                for(int col = 1; col < elements.length; col++)
                {

                    if(col == 1)
                    {
                        genderColumn[row-1] = elements[col];
                    }
                    else
                    {
                        int val = Integer.parseInt(elements[col]);
                        dataTemp[row-1][col-2] = (double) val;
                    }

                }
                row++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //print out the data double array list:
        for(int row = 0; row < data.length; row++)
        {
            System.out.println(dataTemp[row][0] + " " + dataTemp[row][1] + " " + dataTemp[row][2] + " " + genderColumn[row]);
        }

        String[] categories = {"Male", "Female"};


        OneHotEncoder encoder = new OneHotEncoder(categories);
        double[][] encoded = encoder.transform(genderColumn);

        for(int row = 0; row < numRows-1; row++ )
        {
            data[row][numCols-2] = encoded[row][0];
            data[row][numCols-1] = encoded[row][1];


        }


        for(int row = 0; row < dataTemp.length; row++)
        {
            for(int col = 0; col < dataTemp[row].length; col++)
            {
                data[row][col] = Processing.standardize(dataTemp, row,col);
            }
        }

        for(int row = 0; row < numRows -1; row++)
        {
            System.out.println(data[row][3] + "  " + data[row][4] + " " + data[row][0] + " " + data[row][1] + " " + data[row][2]);

        }



    }



    //Matrix Constructor for Donut File
    public void createDonutMatrix(String fileName) {
        int numCols = 0;
        int numRows = 0;
        File file = new File(fileName);
        Scanner scanner = null;

        //determining number of rows and columns in the data set and initializing its dimensions
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineElements = line.split(",");
                numCols = lineElements.length;
                numRows++;

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        data = new double[numRows][numCols];


        try {
            Scanner scanner2 = new Scanner(file);
            int row = 0;
            while(scanner2.hasNextLine())
            {
                String line = scanner2.nextLine().trim();
                String[] elements = line.split(",");
                for(int col = 0; col < elements.length; col++)
                {
                    data[row][col] = Double.parseDouble(elements[col]);
                }
                row++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public int numRows() {
        return this.data.length;
    }

    public double[][] getData() {
        return this.data;
    }

    /*
    private int findFrequency(int attribute, int value, ArrayList<Integer> rows) {
        int count = 0;

        for (int rowIndex : rows) {
            if (data[rowIndex][attribute] == value) {
                count++;
            }
        }
        return count;
    } //Examines only the specified rows of the array. It returns the number of rows in
    // which the element at position attribute (a number between 0 and 4) is equal to value.



    private HashSet<Double> findDifferentValues(int attribute, ArrayList<Integer> rows) {
        HashSet<Double> values = new HashSet<>();
        for (int rowIndex : rows) {
            double value = data[rowIndex][attribute];
            values.add(value);
        }
        return values;
    } //Examines only the specified rows of the array. It returns a HashSet of the different values
    // for the specified attribute.
    private ArrayList<Integer> findRows(int attribute, int value, ArrayList<Integer> rows) {
        ArrayList<Integer> foundRows = new ArrayList<>();
        for (int rowIndex : rows) {
            if (value == data[rowIndex][attribute]) {
                foundRows.add(rowIndex);
            }
        }
        return foundRows;
    } //Examines only the specified rows of the array. Returns an ArrayList of the rows where
    // the value for the attribute is equal to value.

    public double findEntropy(ArrayList<Integer> rows) {
        double entropy = 0.0;
        int total = rows.size();
        HashSet<Double> classValues = findDifferentValues(4, rows);

        for (double classValue : classValues) {
            int frequency = findFrequency(4, classValue, rows);
            double prob = (double) frequency / total;
            entropy -= prob * log2(prob);
        }
        return entropy;
    } //finds the entropy of the dataset that consists of the specified rows.
    private double findEntropy(int attribute, ArrayList<Integer> rows) {
        double entropy = 0.0;
        int totalRows = rows.size();

        HashSet<Double> uniqueValues = findDifferentValues(attribute, rows);

        for (double value : uniqueValues) {
            ArrayList<Integer> valueRows = findRows(attribute, value, rows);
            double subSetEntropy = findEntropy(valueRows);
            double weight = (double) valueRows.size() / totalRows;
            entropy += weight * subSetEntropy;
        }

        return entropy;
    } //finds the entropy of the dataset that consists of the specified rows after
    // it is partitioned on the attribute.
    private double findGain(int attribute, ArrayList<Integer> rows) {
        double overallEntropy = findEntropy(rows);
        double entropyAfterSplit = findEntropy(attribute, rows);
        return overallEntropy - entropyAfterSplit;
    }// finds the information gain of partitioning on the attribute.
    // Considers only the specified rows.

    public double computeIGR(int attribute, ArrayList<Integer> rows) {
        double gain = findGain(attribute, rows);
        int totalRows = rows.size();
        double entropy = 0.0;
        HashSet<Integer> uniqueValues = findDifferentValues(attribute, rows);

        for (int value : uniqueValues) {
            ArrayList<Integer> valueRows = findRows(attribute, value, rows);
            double prob = (double) valueRows.size() / totalRows;
            entropy -= prob * log2(prob);
        }
        if (entropy == 0) {
            return 0;
        }

        return gain / entropy;

    }// returns the Information Gain Ratio, where we only look at the data defined by the set of rows and we consider splitting on attribute.
    public int findMostCommonValue(ArrayList<Integer> rows) {
        int[] counts = new int[4];

        for (int rowIndex : rows) {
            int classLabel = data[rowIndex][4];
            counts[classLabel]++;
        }

        int maxCount = -1;
        int mostCommon = -1;

        for (int i = 1; i <= 3; i++) {
            if (counts[i] > maxCount) {
                maxCount = counts[i];
                mostCommon = i;
            }
        }

        return mostCommon;

    }// returns the most common category for the dataset that is the defined by the specified rows.

    // value, rows that have that value
    public HashMap<Integer, ArrayList<Integer>> split(int attribute, ArrayList<Integer> rows) {
        HashMap<Integer, ArrayList<Integer>> splitData = new HashMap<>();

        HashSet<Double> uniqueValues = findDifferentValues(attribute, rows);

        for (double value : uniqueValues) {
            ArrayList<Integer> valueRows = findRows(attribute, value, rows);
            splitData.put(value, valueRows);
        }
        return splitData;


    } //Splits the dataset that is defined by rows on the attribute.
    // Each element of the HashMap that is returned contains the value for the attribute
    // and an ArrayList of rows that have this value.
     */

    private double log2(double number) {
        return Math.log(number) / Math.log(2);
    }//returns log2 of the input



    private HashSet<String> findDifferentValues(String[][] data, int attribute, ArrayList<Integer> rows) {
        HashSet<String> values = new HashSet<>();
        for (int rowIndex : rows) {
            String value = data[rowIndex][attribute];
            values.add(value);
        }
        return values;
    }




}
