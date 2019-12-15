package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.Recipes;

public class RecipeController {
    
    private APIContact apiContact;
    // Linking the controller to the API contact, I don't fully understand how this is working.
    RecipeController(APIContact api) { apiContact = api; }

    // Passes the list of ingredients from the GUI to the API class, which returns an array
    // this will get pulled apart back in the main GUI class for display
    protected Recipes[] searchByIngredient(String ingredients) {
        Recipes[] apiResponse = apiContact.giveAPIIngredients(ingredients);
        return apiResponse;
    }
    
    // Takes in the ID and name, the ID is all that the API needs to send back the instructions and ingredients
    // Then that and the name is passed on to the Main class so it can be sent to the secondary GUI
    protected void askContactForRecipe(int recipeId, String recipeName) {
        FinalRecipeObject fullRecipe = apiContact.askAPIForRecipe(recipeId);
        Main.createRecipeSelected(fullRecipe, recipeName);
    }
    
    // Passes on quit prompts to the Main class.
    protected void quitProgram() { Main.quit(); }
    protected void secondaryQuit() { Main.secondaryQuit(); }
    
}
