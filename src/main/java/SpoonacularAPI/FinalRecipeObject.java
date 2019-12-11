package SpoonacularAPI;

import SpoonacularAPI.IngredientMeasures.Ingredients;
import SpoonacularAPI.RecipeInstructionObjects.RecipeSection;

public class FinalRecipeObject {
    
    private RecipeSection[] sections;
    private Ingredients[] ingredients;
    
    FinalRecipeObject(RecipeSection[] instructions
            , Ingredients[] ingredients) {
        this.sections = instructions;
        this.ingredients = ingredients;
    }
    
    public RecipeSection[] getSections() {
        return sections;
    }
    
    public void setSections(RecipeSection[] sections) {
        this.sections = sections;
    }
    
    public Ingredients[] getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(Ingredients[] ingredients) {
        this.ingredients = ingredients;
    }
}
