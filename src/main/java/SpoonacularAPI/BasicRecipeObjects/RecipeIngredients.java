package SpoonacularAPI.BasicRecipeObjects;

public class RecipeIngredients {

    private int id;
    private String title;
    private String image;
    private String imageType;
    private int usedIngredientCount;
    private int missedIngredientCount;
    private MissedIngredients[] missedIngredients;
    private UsedIngredients[] usedIngredients;
    private String[] unusedIngredients;
    private int likes;

//    public RecipeIngredients(String image, UsedIngredients[] usedIngredients,
//                             MissedIngredients[] missedIngredients, int missedIngredientCount,
//                             String[] unusedIngredients, int id,
//                             String title, String imageType,
//                             int usedIngredientCount, int likes) {
//        this.image = image;
//        this.usedIngredients = usedIngredients;
//        this.missedIngredients = missedIngredients;
//        this.missedIngredientCount = missedIngredientCount;
//        this.unusedIngredients = unusedIngredients;
//        this.id = id;
//        this.title = title;
//        this.imageType = imageType;
//        this.usedIngredientCount = usedIngredientCount;
//        this.likes = likes;
//    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getImageType() {
        return imageType;
    }
    
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
    
    public int getUsedIngredientCount() {
        return usedIngredientCount;
    }
    
    public void setUsedIngredientCount(int usedIngredientCount) {
        this.usedIngredientCount = usedIngredientCount;
    }
    
    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }
    
    public void setMissedIngredientCount(int missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }
    
    public MissedIngredients[] getMissedIngredients() {
        return missedIngredients;
    }
    
    public void setMissedIngredients(MissedIngredients[] missedIngredients) {
        this.missedIngredients = missedIngredients;
    }
    
    public UsedIngredients[] getUsedIngredients() {
        return usedIngredients;
    }
    
    public void setUsedIngredients(UsedIngredients[] usedIngredients) {
        this.usedIngredients = usedIngredients;
    }
    
    public String[] getUnusedIngredients() {
        return unusedIngredients;
    }
    
    public void setUnusedIngredients(String[] unusedIngredients) {
        this.unusedIngredients = unusedIngredients;
    }
    
    public int getLikes() {
        return likes;
    }
    
    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", usedIngredients = "+usedIngredients+", missedIngredients = "+missedIngredients+", missedIngredientCount = "+missedIngredientCount+", unusedIngredients = "+unusedIngredients+", id = "+id+", title = "+title+", imageType = "+imageType+", usedIngredientCount = "+usedIngredientCount+", likes = "+likes+"]";
    }
}
