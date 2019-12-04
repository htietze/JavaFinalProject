package SpoonacularAPI.BasicRecipeObjects;

public class MissedIngredients {

    private String id;
    private String amount;
    private String originalName;
    private String image;
    private String unit;
    private String unitShort;
    private String original;
    private String name;
    private String unitLong;
    private String originalString;
    private String aisle;
    private String[] metaInformation;

//    public MissedIngredients(String originalName, String image,
//                           double amount, String unit,
//                           String unitShort, String original,
//                           String name, String unitLong,
//                           String originalString, int id,
//                           String aisle, String[] metaInformation) {
//        this.originalName = originalName;
//        this.image = image;
//        this.amount = amount;
//        this.unit = unit;
//        this.unitShort = unitShort;
//        this.original = original;
//        this.name = name;
//        this.unitLong = unitLong;
//        this.originalString = originalString;
//        this.id = id;
//        this.aisle = aisle;
//        this.metaInformation = metaInformation;
//    }

    public String getOriginalName () { return originalName; }
    public void setOriginalName (String originalName) { this.originalName = originalName; }

    public String getImage () { return image; }
    public void setImage (String image) { this.image = image; }

    public String getAmount () { return amount; }
    public void setAmount (String amount) { this.amount = amount; }

    public String getUnit () { return unit; }
    public void setUnit (String unit) { this.unit = unit; }

    public String getUnitShort () { return unitShort; }
    public void setUnitShort (String unitShort) { this.unitShort = unitShort; }

    public String getOriginal () { return original; }
    public void setOriginal (String original) { this.original = original; }

    public String getName () { return name; }
    public void setName (String name) { this.name = name; }

    public String getUnitLong () { return unitLong; }
    public void setUnitLong (String unitLong) { this.unitLong = unitLong; }

    public String getOriginalString () { return originalString; }
    public void setOriginalString (String originalString) { this.originalString = originalString; }

    public String getId () { return id; }
    public void setId (String id) { this.id = id; }

    public String getAisle () { return aisle; }
    public void setAisle (String aisle) { this.aisle = aisle; }

    public String[] getMetaInformation () { return metaInformation; }
    public void setMetaInformation (String[] metaInformation) { this.metaInformation = metaInformation; }

    @Override
    public String toString()
    {
        return "ClassPojo [originalName = "+originalName+", image = "+image+", amount = "+amount+", unit = "+unit+", unitShort = "+unitShort+", original = "+original+", name = "+name+", unitLong = "+unitLong+", originalString = "+originalString+", id = "+id+", aisle = "+aisle+", metaInformation = "+metaInformation+"]";
    }
}
