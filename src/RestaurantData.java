import java.io.*;
import java.util.*;

public class RestaurantData {
    public static List<Restaurant> loadFromCSV(String filePath) {
        List<Restaurant> restaurants = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",", -1);

                try {
                    if (parts.length < 5) continue;

                    String name = parts[0].trim();
                    double businessStars = Double.parseDouble(parts[1].trim());
                    String categories = parts[3].trim();

                    List<String> targetCuisines = List.of("Chinese", "Indian", "Italian", "Mexican", "American", "Japanese", "Pizza", "Thai");

                    for (String target : targetCuisines) {
                        if (categories.toLowerCase().contains(target.toLowerCase())) {
                            restaurants.add(new Restaurant(name, target, businessStars));
                            break;
                        }
                    }

                } catch (Exception e) {
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return restaurants;
    }
}