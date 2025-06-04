import java.util.HashMap;
import java.util.Map;

public class OneHotEncoder {
    private Map<String, Integer> categoryIndexMap;
    private String[] categories;

    public OneHotEncoder(String[] categories) {
        this.categories = categories;
        categoryIndexMap = new HashMap<>();
        for (int i = 0; i < categories.length; i++) {
            categoryIndexMap.put(categories[i], i);

        }
    }

    public double[][] transform(String[] data) {
        double[][] encoded = new double[data.length][categories.length];

        for (int i = 0; i < data.length; i++) {
            String category = data[i];
            Integer index = categoryIndexMap.get(category);
            if (index != null) {
                encoded[i][index] = 1.0;
            } else {
                System.err.println("unknown category : " + category);
            }
        }

        return encoded;
    }

    public String[] getCategories() {
        return categories;
    }

}
