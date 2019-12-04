package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.RecipeIngredients;
import com.google.gson.Gson;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;

public class Main {

    public static void main(String[] args) {

        Unirest.config().setObjectMapper(new ObjectMapper() {
            private Gson gson = new Gson();
            @Override
            public <T> T readValue(String s, Class<T> aClass) { return gson.fromJson(s, aClass); }
            @Override
            public String writeValue(Object o) { return gson.toJson(o); }
        });

        String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=chicken,+capers&number=1&apiKey=63c0322b83d54b45814e74049b156266";

        RecipeIngredients recipeIngredients = Unirest.get(url).asObject(RecipeIngredients.class).getBody();

        System.out.println(recipeIngredients.getTitle());

    }
}
