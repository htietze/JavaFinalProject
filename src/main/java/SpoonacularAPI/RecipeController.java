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
        FinalRecipeObject fullRecipe = apiContact.askAPIForRecipe(recipeId);
        Main.createRecipeSelected(fullRecipe);
    }
    
    protected void quitProgram() { Main.quit(); }
    protected void secondaryQuit() { Main.secondaryQuit(); }
    
}
