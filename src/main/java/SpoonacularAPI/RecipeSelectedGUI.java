package SpoonacularAPI;

import SpoonacularAPI.IngredientObjects.Ingredients;
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
    
    // setting up the secondary GUI, configuring the two lists and the quit button
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
    
    // Quit button linked to the controller, then main, which closes only the secondary window
    // Clicking the x would close both windows.
    private void configureActionListeners() {
        
        secondaryQuitButton.addActionListener(e -> controller.secondaryQuit());
        
    }
    
    // The ingredient list only uses a basic list
    private void configureFocusedIngredientList() {
        focusedIngredientListModel = new DefaultListModel<>();
        focusedIngredientsJList.setModel(focusedIngredientListModel);
    }
    
    // Same here. The complicated stuff will be done elsewhere for displaying
    private void configureFocusedInstructionsList() {
        focusedInstructionListModel = new DefaultListModel<>();
        focusedInstructionsJList.setModel(focusedInstructionListModel);
    }
    
    // The API sends data to the controller, which goes to main, which launches the
    // second window and sends the data here
    public void delegateRecipeParts(FinalRecipeObject fullRecipe, String recipeName) {
        // the final object is split back into the needed components and are sent to their
        // respective methods
        RecipeSection[] recipeSections = fullRecipe.getSections();
        Ingredients[] recipeIngredients = fullRecipe.getIngredients();
        
        recipeNameLabel.setText(recipeName);
        displayInstructions(recipeSections);
        displayIngredients(recipeIngredients);
    }
    
    // This method will take apart the instructions object for display
    private void displayInstructions(RecipeSection[] recipeSections) {
        for (RecipeSection r : recipeSections) {
            // Sometimes the API sends sections that don't have names?
            // So they're added in case.
            if (r.getName().isEmpty()) {
                focusedInstructionListModel.addElement("Next Section");
            } else {
                focusedInstructionListModel.addElement(r.getName());
            }
            // for each object in the Steps objects held by the sections, the step number and instructions are pulled
            // and set into a more readable format.
            for (Steps s : r.getSteps()) {
                focusedInstructionListModel.addElement(s.getNumber()
                        + ". " + s.getStep());
            }
        }
    }
    
    // This method takes apart the ingredients object
    private void displayIngredients(Ingredients[] recipeIngredients) {
        // Each ingredient has three parts needed: the name, the unit, and the value (or amount)
        for (Ingredients i : recipeIngredients) {
            String name = i.getName();
            String unit = i.getAmount().getUs().getUnit();
            double value = i.getAmount().getUs().getValue();
            // After taking these three out, they're set together in a fairly readable format
            // sometimes the API sends back ingredients that aren't very useful unfortunately.
            focusedIngredientListModel.addElement(value + " " + unit + " " + name);
        }
    }
}
