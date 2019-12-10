package SpoonacularAPI.IngredientMeasures;

public class Amount {
    
    private IngredientMetricMeasure metric;
    private IngredientUsMeasure us;
    
    public IngredientUsMeasure getUs() {
        return us;
    }
    
    public void setUs(IngredientUsMeasure us) {
        this.us = us;
    }
    
    public IngredientMetricMeasure getMetric() {
        return metric;
    }
    
    public void setMetric(IngredientMetricMeasure metric) {
        this.metric = metric;
    }
}
