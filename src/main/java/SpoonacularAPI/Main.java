package SpoonacularAPI;

public class Main {
    
    // The program has two GUIs, one to display the user input sections for finding a list of recipes
    // and the second which displays the selected recipe's ingredients and instructions.
    static RecipeChoiceGUI recipeChoiceGUI;
    static RecipeSelectedGUI recipeSelectedGUI;
    
    public static void main(String[] args) {
        // Setting up the connections between classes.
        // The API contact deals with all connections to the Spoonacular API and sends data back
        APIContact apiContact = new APIContact();
        // Setting up requests to the API or sending data to be displayed in the GUI is done
        // with the recipe controller class.
        RecipeController recipeController = new RecipeController(apiContact);
        // then the recipe choice GUI which takes in ingredient input,
        // displays recipe names and IDs, and connects to the controller to relay these things.
        recipeChoiceGUI = new RecipeChoiceGUI(recipeController);
    }
    
    // Setting up a secondary GUI for the selected recipe, only happens when the button is pushed.
    // Takes the recipe name that was selected, as well as the recipe ingredients and instructions
    // which are the two pieces of the Final Recipe Object
    public static void createRecipeSelected(FinalRecipeObject fullRecipe, String recipeName) {
        // This GUI needs to be tied into the API contact through the controller as well (I think?)
        APIContact apiContact = new APIContact();
        RecipeController recipeController = new RecipeController(apiContact);
        recipeSelectedGUI = new RecipeSelectedGUI(recipeController);
        // After the GUI is made, the data is passed along to the GUI class' method
        recipeSelectedGUI.delegateRecipeParts(fullRecipe, recipeName);
    }
    
    // Allowing user's to quit the main GUI or the close the secondary GUI
    public static void quit() { recipeChoiceGUI.dispose(); }
    public static void secondaryQuit() { recipeSelectedGUI.dispose(); }
    
}
