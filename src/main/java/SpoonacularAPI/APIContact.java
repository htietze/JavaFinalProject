package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.RecipeIngredients;
import com.google.gson.Gson;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;

public class APIContact {
    
    final static private String Spoonacular_API_KEY = System.getenv("Spoonacular_API_Key");
    
    public RecipeIngredients[] giveAPIIngredients(String ingredients) {
    
        Unirest.config().setObjectMapper(new ObjectMapper() {
            private Gson gson = new Gson();
            @Override
            public <T> T readValue(String s, Class<T> aClass) { return gson.fromJson(s, aClass); }
            @Override
            public String writeValue(Object o) { return gson.toJson(o); }
        });
        
        String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients="
                + ingredients + "&number=3" + "&apiKey=" + Spoonacular_API_KEY;
        
        RecipeIngredients[] recipeIngredients = Unirest.get(url)
                .asObject(RecipeIngredients[].class)
                .getBody();
        
        return recipeIngredients;
    }
}
