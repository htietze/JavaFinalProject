package SpoonacularAPI;

public class Main {
    
    static RecipeChoiceGUI recipeChoiceGUI;

    public static void main(String[] args) {
        
        APIContact apiContact = new APIContact();
        RecipeController recipeController = new RecipeController(apiContact);
        recipeChoiceGUI = new RecipeChoiceGUI(recipeController);
        
        
        
    }
    
    public static void quit() { recipeChoiceGUI.dispose(); }
    
}
