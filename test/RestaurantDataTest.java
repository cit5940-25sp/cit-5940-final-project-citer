import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;

public class RestaurantDataTest {
    @Test
    public void testUniqueCuisineCount() {
        String filePath = "restaurant_data.csv";
        List<Restaurant> restaurants = RestaurantData.loadFromCSV(filePath);

        Set<String> uniqueCuisines = new HashSet<>();
        for (Restaurant r : restaurants) {
            uniqueCuisines.add(r.getCuisine());
        }

        assertEquals(8, uniqueCuisines.size());

    }
}
