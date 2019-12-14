package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.Recipes;
import SpoonacularAPI.IngredientObjects.Ingredients;
import SpoonacularAPI.IngredientObjects.RecipeIngredients;
import SpoonacularAPI.RecipeInstructionObjects.RecipeSection;
import com.google.gson.Gson;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;

public class APIContact {
    
    // establishing the environment variable, which is the User key for the Spoonacular API
    final static private String Spoonacular_API_KEY = System.getenv("Spoonacular_API_Key");
    
    // Method called for the first search, this will use the API to get 15 recipes.
    public Recipes[] giveAPIIngredients(String ingredients) {
        // calling the method to configure Unirest and Gson for object mapping.
        configureUnirest();
        
        // Building the string for requesting data
        // Uses the ingredients set that have been sent through the controller from the GUI, then adding the API key
        // at the end.
        String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients="
                + ingredients + "&number=15&apiKey=" + Spoonacular_API_KEY;
        
        // The request returns an array of Recipe objects, the parts of which are set up in the Recipes object class
        Recipes[] recipeIngredients = Unirest.get(url)
                .asObject(Recipes[].class)
                .getBody();
        // Then this array is sent back through the controller to the GUI which will pull out the necessary data.
        return recipeIngredients;
    }
    
    // Method for setting up the request from the API for recipe ingredients and instructions
    public FinalRecipeObject askAPIForRecipe(int recipeId) {
        
        // Set into separate methods, for each API request.
        // The returned data is in the form of more arrays, of RecipeSections, another object class
        RecipeSection[] sections = getRecipeSections(recipeId);
        // and Ingredients objects.
        Ingredients[] ingredients = getRecipeIngredients(recipeId);
        
        // To pass these both on to the controller and on to the secondary GUI for display, they're
        // put together into a final recipe object, which is taken apart on the other end.
        FinalRecipeObject fullRecipe = new FinalRecipeObject(sections, ingredients);
        
        return fullRecipe;
    }
    
    private RecipeSection[] getRecipeSections(int recipeId) {
    
        // First of two requests, again configuring Unirest to set up object mapping.
        configureUnirest();
        // This string is set a bit different, just requiring the recipe's ID and the environment variable
        String instructionsUrl = "https://api.spoonacular.com/recipes/" + recipeId
                + "/analyzedInstructions?apiKey=" + Spoonacular_API_KEY;
        // Returns an array of sections to be used.
        RecipeSection[] recipeSections = Unirest.get(instructionsUrl)
                .asObject(RecipeSection[].class)
                .getBody();
        // The API will sometimes return sections that have empty names,
        // so these are set to a default Instructions title.
        for (RecipeSection r : recipeSections) {
            if (r.getName() == null || r.getName().isEmpty()) {
                r.setName("Instructions:");
            }
        }
        // then the array is sent back to the previous method
        return recipeSections;
    }
    
    private Ingredients[] getRecipeIngredients(int recipeId) {
        // Configuring unirest for object mapping..
        configureUnirest();
        // Again the url just requires the recipe ID to find the ingredients.
        String ingredientAmountsUrl = "https://api.spoonacular.com/recipes/" + recipeId
                + "/ingredientWidget.json?apiKey=" + Spoonacular_API_KEY;
        // the returned object isn't an array this time, however it contains one
        RecipeIngredients recipeIngredients = Unirest.get(ingredientAmountsUrl)
                .asObject(RecipeIngredients.class)
                .getBody();
        // So this is extracted since there's no other useful info the first piece
        Ingredients[] ingredients = recipeIngredients.getIngredients();
        // then that's passed back.
        return ingredients;
    }
    
    private void configureUnirest() {
        // setting up Unirest and Gson for object mapping.
        // reading the value from a json response and using gson to write it to the matching object.
        Unirest.config().setObjectMapper(new ObjectMapper() {
            private Gson gson = new Gson();
            @Override
            public <T> T readValue(String s, Class<T> aClass) { return gson.fromJson(s, aClass); }
            @Override
            public String writeValue(Object o) { return gson.toJson(o); }
        });
    }
    
}
