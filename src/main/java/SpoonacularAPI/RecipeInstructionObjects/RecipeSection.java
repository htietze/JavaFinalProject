package SpoonacularAPI.RecipeInstructionObjects;

public class RecipeSection {

    private String name;
    private Steps[] steps;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Steps[] getSteps() {
        return steps;
    }
    
    public void setSteps(Steps[] steps) {
        this.steps = steps;
    }
}
