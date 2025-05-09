import java.util.*;

public class FoodCommand implements Command {


    @Override
    public void execute() {
        FoodGraph foodGraph = new FoodGraph();

        foodGraph.buildGraph("/Users/varunsingh/Desktop/" +
                "Course notes/CIT 5940/cit-5940-final-project-citer/Databases/Philly Food DB V2.csv");

        foodGraph.findSuggestions();
    }
}