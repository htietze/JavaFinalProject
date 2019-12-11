package SpoonacularAPI;

import SpoonacularAPI.IngredientMeasures.Ingredients;
import SpoonacularAPI.RecipeInstructionObjects.RecipeSection;
import SpoonacularAPI.RecipeInstructionObjects.Steps;

import javax.swing.*;

public class RecipeSelectedGUI extends JFrame {
    private JPanel secondaryPanel;
    private JList<String> focusedIngredientsJList;
    private JList<String> focusedInstructionsJList;
    private JButton secondaryQuitButton;
    private JLabel ingredientsLabel;
    private JLabel instructionsLabel;
    private JLabel recipeNameLabel;
    
    private DefaultListModel<String> focusedIngredientListModel;
    private DefaultListModel<String> focusedInstructionListModel;
    
    private RecipeController controller;
    
    RecipeSelectedGUI(RecipeController controller) {
        
        this.controller = controller;
    
        setTitle("Recipe Instructions");
        setContentPane(secondaryPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        configureActionListeners();
        configureFocusedIngredientList();
        configureFocusedInstructionsList();
        
    }
    
    private void configureActionListeners() {
        
        secondaryQuitButton.addActionListener(e -> controller.secondaryQuit());
        
    }
    
    private void configureFocusedIngredientList() {
        focusedIngredientListModel = new DefaultListModel<>();
        focusedIngredientsJList.setModel(focusedIngredientListModel);
    }
    
    private void configureFocusedInstructionsList() {
        focusedInstructionListModel = new DefaultListModel<>();
        focusedInstructionsJList.setModel(focusedInstructionListModel);
    }
    
    public void delegateRecipeParts(FinalRecipeObject fullRecipe, String recipeName) {
        RecipeSection[] recipeSections = fullRecipe.getSections();
        Ingredients[] recipeIngredients = fullRecipe.getIngredients();
        
        recipeNameLabel.setText(recipeName);
        displayInstructions(recipeSections);
        displayIngredients(recipeIngredients);
    }
    
    private void displayInstructions(RecipeSection[] recipeSections) {
        for (RecipeSection r : recipeSections) {
            if (r.getName().isEmpty()) {
                focusedInstructionListModel.addElement("Next Section");
            } else {
                focusedInstructionListModel.addElement(r.getName());
            }
            
            for (Steps s : r.getSteps()) {
                focusedInstructionListModel.addElement(s.getNumber()
                        + ". " + s.getStep());
            }
        }
    }
    
    private void displayIngredients(Ingredients[] recipeIngredients) {
        for (Ingredients i : recipeIngredients) {
            String name = i.getName();
            String unit = i.getAmount().getUs().getUnit();
            double value = i.getAmount().getUs().getValue();
            focusedIngredientListModel.addElement(value + " " + unit + " " + name);
        }
    }
}
