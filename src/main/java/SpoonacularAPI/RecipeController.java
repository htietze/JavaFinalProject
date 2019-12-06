package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.RecipeIngredients;

public class RecipeController {
    
    private APIContact apiContact;
    
    RecipeController(APIContact api) { apiContact = api; }

    protected RecipeIngredients[] searchByIngredient(String ingredients) {
        RecipeIngredients[] apiResponse = apiContact.giveAPIIngredients(ingredients);
        return apiResponse;
    }
    
    protected void quitProgram() { Main.quit(); }
    
}
