package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.Recipes;

public class RecipeController {
    
    private APIContact apiContact;
    
    RecipeController(APIContact api) { apiContact = api; }

    protected Recipes[] searchByIngredient(String ingredients) {
        Recipes[] apiResponse = apiContact.giveAPIIngredients(ingredients);
        return apiResponse;
    }
    
    protected void askContactForRecipe(int recipeId) {
        apiContact.askAPIForRecipe(recipeId);
    }
    
    protected void quitProgram() { Main.quit(); }
    
}
