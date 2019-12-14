package SpoonacularAPI.IngredientObjects;

import java.util.Arrays;

public class RecipeIngredients {
    
    private Ingredients[] ingredients;
    
    public Ingredients[] getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(Ingredients[] ingredients) {
        this.ingredients = ingredients;
    }
    
    @Override
    public String toString() {
        return "RecipeIngredients{" +
                "ingredients=" + Arrays.toString(ingredients) +
                '}';
    }
}
