package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.Recipes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

public class RecipeChoiceGUI extends JFrame{
    
    private JLabel recipesDisplayed;
    private JTextField ingredientSearchTextField;
    private JLabel searchLabel;
    private JButton quitButton;
    private JButton searchButton;
    private JList<String> ingredientList;
    private JLabel ingredientsLabel;
    private JButton addIngredientButton;
    private JPanel mainPanel;
    private JButton getRecipeStepsButton;
    private JTable matchingRecipesJTable;
    
    private DefaultListModel<String> ingredientListModel;
    private DefaultTableModel matchingRecipesJTableModel;

    private RecipeController controller;

    // Setting up the GUI, running through the configuration methods
    // Also sets the enter key to enter in ingredients
    RecipeChoiceGUI(RecipeController controller) {

        this.controller = controller;

        setTitle("Recipe Finder");
        setContentPane(mainPanel);
        getRootPane().setDefaultButton(addIngredientButton);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        configureIngredientJList();
        configureMatchingRecipeJTable();
        configureIngredientPopUpMenu();
        addActionListeners();
        
    }
    
    // Sets up the list model that's used for displaying the User's entered ingredients.
    public void configureIngredientJList() {
        ingredientListModel = new DefaultListModel<>();
        ingredientList.setModel(ingredientListModel);
        ingredientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    // Configuring the JTable, which will display a column of recipe IDs and names
    public void configureMatchingRecipeJTable() {
        matchingRecipesJTableModel = new DefaultTableModel();
        matchingRecipesJTable.setModel(matchingRecipesJTableModel);
        matchingRecipesJTableModel.addColumn("Recipe ID");
        matchingRecipesJTableModel.addColumn("Recipe Name");
        // Using this site to figure out how to configure the column sizes
        // this means the ID column isn't unnecessarily wide:
        // https://kodejava.org/how-do-i-set-or-change-jtable-column-width/
        TableColumn recipeIdColumn = matchingRecipesJTable.getColumnModel().getColumn(0);
        recipeIdColumn.setMinWidth(100);
        recipeIdColumn.setMaxWidth(150);
        // Single selection is important because the User shouldn't be allowed to select multiple, it would crash.
        matchingRecipesJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    // Setting up a little popup menu that allows users to delete ingredients.
    public void configureIngredientPopUpMenu() {
        JPopupMenu rightClickMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        rightClickMenu.add(deleteMenuItem);
        // Clicking Delete from the right click menu will cause the delete method to be called
        deleteMenuItem.addActionListener(e -> deleteIngredient());
        // calling this method to make the mouse listener for the list, not especially necessary, but
        // if more mouse listeners need to be made after expansion, this would help.
        createMouseListener(ingredientList, rightClickMenu);
    }
    
    // A general method to make a mouse listener. Useful if more jlists are added and have their own popup menus
    public void createMouseListener(JList<String> listName, JPopupMenu jMenu) {
        listName.setComponentPopupMenu(jMenu);
        listName.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selection = listName.locationToIndex(e.getPoint());
                listName.setSelectedIndex(selection);
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }
    
    // Not very many action listeners, but important ones
    public void addActionListeners() {
        // the quit button links to the methods in the controller which pass to the Main class.
        quitButton.addActionListener(e -> controller.quitProgram());
        // these next action listeners call methods to handle things
        addIngredientButton.addActionListener(e -> addIngredient());
        
        searchButton.addActionListener(e -> searchForRecipes());
        // more complicated listener, this one handles first pulling the selected ID value and the recipe name
        getRecipeStepsButton.addActionListener(e -> {
            if (matchingRecipesJTable.getSelectedRow() == -1) {
                // If no recipe has been selected, then the User receives that notice.
                JOptionPane.showMessageDialog(mainPanel, "No recipe selected");
            } else {
                int selectedRow = matchingRecipesJTable.getSelectedRow();
                String recipeId = matchingRecipesJTableModel.getValueAt(selectedRow, 0).toString();
                String selectedRecipeName = matchingRecipesJTableModel.getValueAt(selectedRow, 1).toString();
                int convertedId = Integer.parseInt(recipeId);
                // Once these are collected, they're sent to the method that will eventually send them to the API class
                getRecipeSteps(convertedId, selectedRecipeName);
            }
        });
    }
    
    // this method deals with validating the user's entered ingredient before it's added to the list
    public void addIngredient() {
        String ingredient = ingredientSearchTextField.getText();
        // After getting the input text, all spaces have to be removed, or they'd break the API url
        ingredient = ingredient.replace(" ", "");
        // Then it has to be checked that only letters a-z, capital or not, are used in the string
        boolean characterTest = Pattern.matches("^[a-zA-Z]+$", ingredient);
        // If the user didn't enter anything (or entered spaces), they're told it's not a valid ingredient.
        if (ingredient.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Please enter a valid ingredient");
        } else if (!characterTest) {
            // Same if the boolean came back true and the string contained special characters
            JOptionPane.showMessageDialog(mainPanel, "Please remove invalid characters");
        } else if (ingredientListModel.contains(ingredient)) {
            // A different message is displayed if the list already has what was entered.
            JOptionPane.showMessageDialog(mainPanel, "This ingredient is already added");
        } else {
            // I've limited the number of ingredients to 3 to keep things from getting out of hand..
            if (ingredientListModel.size() < 3) {
                ingredientListModel.addElement(ingredient);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Three ingredients already entered");
            }
            // Blanks out the text field after the ingredient has been added.
            ingredientSearchTextField.setText("");
        }
    }
    
    // This method uses the selected index from the mouse listener to delete an item from the model
    public void deleteIngredient() {
        int selectedIndex = ingredientList.getSelectedIndex();
        if (selectedIndex != -1) {
            ingredientListModel.remove(selectedIndex);
        }
    }
    
    // This method organizes the ingredients into a format that can be plugged into the url
    public void searchForRecipes() {
        if (ingredientListModel.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Please enter ingredients");
        } else {
            // Each ingredient is taken from the list and formatted together in a way usable by the API
            String ingredientsURLChunk = "";
            for (int x = 0 ; x < ingredientListModel.size() ; x++) {
                if (ingredientsURLChunk.isEmpty()) {
                    ingredientsURLChunk = ingredientListModel.get(x);
                } else {
                    ingredientsURLChunk = ingredientsURLChunk + ",+" + ingredientListModel.get(x);
                }
            }
            // Replacing the + symbol, used by the API, with a space allows us to display a friendly version
            // of the ingredient list as part of the GUI
            String ingredientsSearched = ingredientsURLChunk.replace("+", " ");
            // The url ingredients piece is sent to the controller, which returns an array
            Recipes[] matchingRecipes = controller.searchByIngredient(ingredientsURLChunk);
            // then that array and the display string are sent to the next method
            displayMatchingRecipes(matchingRecipes, ingredientsSearched);
        }
    }
    
    // taking the array and display string from the previous method and putting it into the table
    public void displayMatchingRecipes(Recipes[] recipes, String ingredients) {
        // If nothing was returned, or the length of the array is 0, then the model is cleared and
        if (recipes == null || recipes.length == 0) {
            // Had to look up how to clear a jtable, this clears it without deleting the columns.
            // https://stackoverflow.com/questions/4577792/how-to-clear-jtable/4578501
            matchingRecipesJTableModel.getDataVector().clear();
            ingredientListModel.clear();
            recipesDisplayed.setText("No recipes found containing: " + ingredients);
        } else {
            matchingRecipesJTableModel.getDataVector().clear();
            // After clearing the table, the data that was received is looped through to display the ID and name
            for (Recipes r : recipes) {
                String recipeIdString = Integer.toString(r.getId());
                String recipeTitle = r.getTitle();
                matchingRecipesJTableModel.addRow(new String[]{recipeIdString, recipeTitle});
                recipesDisplayed.setText("Recipes containing: " + ingredients);
            }
            // After trying, the ingredient list is cleared as well
            ingredientListModel.clear();
        }
    }
    
    // The method called by the get instructions button, it sends that info to the controller which go to the API
    public void getRecipeSteps(int recipeId, String selectedRecipeName) {
        controller.askContactForRecipe(recipeId, selectedRecipeName);
    }
}
