public class Restaurant {
    private String name;
    private String cuisine;
    private double rating;

    public Restaurant(String name, String cuisine, double rating) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public String getName() { return name; }
    public String getCuisine() { return cuisine; }
    public double getRating() { return rating; }
}
