package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.Recipes;
import SpoonacularAPI.IngredientMeasures.Ingredients;
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
                + ingredients + "&number=15" + "&apiKey=" + Spoonacular_API_KEY;
        
        Recipes[] recipeIngredients = Unirest.get(url)
                .asObject(Recipes[].class)
                .getBody();
        
        return recipeIngredients;
    }
    
    public FinalRecipeObject askAPIForRecipe(int recipeId) {
        
        RecipeSection[] sections = getRecipeSections(recipeId);
        Ingredients[] ingredients = getRecipeIngredients(recipeId);
        
        FinalRecipeObject fullRecipe = new FinalRecipeObject(sections, ingredients);
        
        return fullRecipe;
    }
    
    private void configureUnirest() {
        Unirest.config().setObjectMapper(new ObjectMapper() {
            private Gson gson = new Gson();
            @Override
            public <T> T readValue(String s, Class<T> aClass) { return gson.fromJson(s, aClass); }
            @Override
            public String writeValue(Object o) { return gson.toJson(o); }
        });
    }
    
    private RecipeSection[] getRecipeSections(int recipeId) {
    
        configureUnirest();
    
        String instructionsUrl = "https://api.spoonacular.com/recipes/" + recipeId
                + "/analyzedInstructions?apiKey=" + Spoonacular_API_KEY;
    
        RecipeSection[] recipeSections = Unirest.get(instructionsUrl)
                .asObject(RecipeSection[].class)
                .getBody();
        
        for (RecipeSection r : recipeSections) {
            if (r.getName() == null || r.getName().isEmpty()) {
                r.setName("Instructions:");
            }
        }
        
        return recipeSections;
    }
    
    private Ingredients[] getRecipeIngredients(int recipeId) {
    
        configureUnirest();
    
        String ingredientAmountsUrl = "https://api.spoonacular.com/recipes/" + recipeId
                + "/ingredientWidget.json?apiKey=" + Spoonacular_API_KEY;
    
        RecipeIngredients recipeIngredients = Unirest.get(ingredientAmountsUrl)
                .asObject(RecipeIngredients.class)
                .getBody();
        
        Ingredients[] ingredients = recipeIngredients.getIngredients();
        
//        for (Ingredients i : ingredients) {
//            System.out.println(i.getName());
//        }
        
        return ingredients;
    }
    
}
