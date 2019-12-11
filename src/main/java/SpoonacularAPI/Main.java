package SpoonacularAPI;

public class Main {
    
    static RecipeChoiceGUI recipeChoiceGUI;
    static RecipeSelectedGUI recipeSelectedGUI;

    public static void main(String[] args) {
        
        APIContact apiContact = new APIContact();
        RecipeController recipeController = new RecipeController(apiContact);
        recipeChoiceGUI = new RecipeChoiceGUI(recipeController);
    }
    
    public static void createRecipeSelected(FinalRecipeObject fullRecipe, String recipeName) {
        APIContact apiContact = new APIContact();
        RecipeController recipeController = new RecipeController(apiContact);
        recipeSelectedGUI = new RecipeSelectedGUI(recipeController);
        recipeSelectedGUI.delegateRecipeParts(fullRecipe, recipeName);
    }
    
    public static void quit() { recipeChoiceGUI.dispose(); }
    public static void secondaryQuit() { recipeSelectedGUI.dispose(); }
    
}
