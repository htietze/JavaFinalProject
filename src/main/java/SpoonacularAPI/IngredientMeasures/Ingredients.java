package SpoonacularAPI.IngredientMeasures;

public class Ingredients {

    private String name;
    private String image;
    private Amount amount;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public Amount getAmount() {
        return amount;
    }
    
    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
