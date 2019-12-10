package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.Recipes;
import SpoonacularAPI.IngredientMeasures.RecipeIngredients;
import SpoonacularAPI.RecipeInstructionObjects.RecipeSection;
import com.google.gson.Gson;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;

public class APIContact {
    
    final static private String Spoonacular_API_KEY = System.getenv("Spoonacular_API_Key");
    
    public Recipes[] giveAPIIngredients(String ingredients) {
        configureUnirest();
        
        String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients="
                + ingredients + "&number=1" + "&apiKey=" + Spoonacular_API_KEY;
        
        Recipes[] recipeIngredients = Unirest.get(url)
                .asObject(Recipes[].class)
                .getBody();
        
        return recipeIngredients;
    }
    
    public void askAPIForRecipe(int recipeId) {
        
        configureUnirest();
        
        String instructionsUrl = "https://api.spoonacular.com/recipes/" + recipeId
                + "/analyzedInstructions?apiKey=" + Spoonacular_API_KEY;
        
        String ingredientAmountsUrl = "https://api.spoonacular.com/recipes/" + recipeId
                + "/ingredientWidget.json?apiKey=" + Spoonacular_API_KEY;
        
//        RecipeSection[] recipeSections = Unirest.get(instructionsUrl)
//                .asObject(RecipeSection[].class)
//                .getBody();
        
        RecipeIngredients recipeIngredients = Unirest.get(ingredientAmountsUrl)
                .asObject(RecipeIngredients.class)
                .getBody();
        
        // PRINTS the BLANK Name, then bourbon molasses butter. things are working. these should be sent back
        // and then cut up to make the steps strings for that side.
//        for (RecipeSection r : recipeSections) {
//            System.out.println(r.getName());
//        }
        System.out.println(recipeIngredients);
        
        
        
        
    }
    
    public void configureUnirest() {
        Unirest.config().setObjectMapper(new ObjectMapper() {
            private Gson gson = new Gson();
            @Override
            public <T> T readValue(String s, Class<T> aClass) { return gson.fromJson(s, aClass); }
            @Override
            public String writeValue(Object o) { return gson.toJson(o); }
        });
    }
}
