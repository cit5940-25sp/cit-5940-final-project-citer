import java.util.*;

public class FoodCommand implements Command {
    private FoodGraph foodGraph;

    public FoodCommand() {
        foodGraph = new FoodGraph();
        foodGraph.buildGraph("/Users/varunsingh/Desktop/" +
                "Course notes/CIT 5940/cit-5940-final-project-citer/Databases/Philly Food DB V2.csv");
    }

    @Override
    public void execute() {
        //Find suggestions to the restaurants
        foodGraph.findSuggestions();
    }
}
