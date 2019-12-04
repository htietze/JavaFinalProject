package SpoonacularAPI;

import com.google.gson.Gson;
import kong.unirest.JsonNode;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

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

        //String testString = Unirest.get(url).asString().getBody();

        JsonNode test = Unirest.get(url).asJson().getBody();

        System.out.println(test);

        JSONArray test2 = test.getArray();

        System.out.println(test2);

        JSONObject test3 = test2.getJSONObject(0);

        String test4 = test3.getString("title");

        System.out.println(test4);

    }
}
