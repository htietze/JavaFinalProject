package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.Recipes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RecipeChoiceGUI extends JFrame{

//    private static String selectedRecipeName;
    
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
    
    public void configureIngredientJList() {
        
        ingredientListModel = new DefaultListModel<>();
        ingredientList.setModel(ingredientListModel);
        ingredientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
    }
    
    public void configureMatchingRecipeJTable() {
        
        matchingRecipesJTableModel = new DefaultTableModel();
        matchingRecipesJTable.setModel(matchingRecipesJTableModel);
        matchingRecipesJTableModel.addColumn("Recipe ID");
        matchingRecipesJTableModel.addColumn("Recipe Name");
        // https://kodejava.org/how-do-i-set-or-change-jtable-column-width/
        TableColumn recipeIdColumn = matchingRecipesJTable.getColumnModel().getColumn(0);
        recipeIdColumn.setMinWidth(100);
        recipeIdColumn.setMaxWidth(150);
        matchingRecipesJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void configureIngredientPopUpMenu() {
        JPopupMenu rightClickMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        rightClickMenu.add(deleteMenuItem);
        deleteMenuItem.addActionListener(e -> deleteIngredient());
        createMouseListener(ingredientList, rightClickMenu);
    }
    
    public void createMouseListener(JList listName, JPopupMenu jMenu) {
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
    
    public void addActionListeners() {
        
        quitButton.addActionListener(e -> controller.quitProgram());
        
        addIngredientButton.addActionListener(e -> addIngredient());
        
        searchButton.addActionListener(e -> searchForRecipes());
        
        getRecipeStepsButton.addActionListener(e -> {
            // getRecipeSteps(324694);
            if (matchingRecipesJTable.getSelectedRow() != -1) {
                int selectedRow = matchingRecipesJTable.getSelectedRow();
                String recipeId = matchingRecipesJTableModel.getValueAt(selectedRow, 0).toString();
                String selectedRecipeName = matchingRecipesJTableModel.getValueAt(selectedRow, 1).toString();
                int convertedId = Integer.parseInt(recipeId);
                // int testId = 324694;
                getRecipeSteps(convertedId, selectedRecipeName);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "No recipe selected");
            }
            
            // ADD MOUSE LISTENER FOR JTABLE
            // send Recipe ID to controller, which boots up
            // selectedGUI and then calls the API contact class
            // to get the instructions objects!
            // MORE OBJECTS
            // then feed those object parts into the selectedGUI class
            // for displaying them
            
        });
    }
    
    public void displayMatchingRecipes(Recipes[] recipes, String ingredients) {
        
        // ADD VALIDATION TO SEE IF THERE ARE ANY RECIPES
        // breaks if no recipes match.
        if (recipes != null) {
            for (Recipes r : recipes) {
                String recipeIdString = Integer.toString(r.getId());
                String recipeTitle = r.getTitle();
                matchingRecipesJTableModel.addRow(new String[]{recipeIdString, recipeTitle});
                recipesDisplayed.setText("Recipes containing:\n" + ingredients);
                ingredientListModel.clear();
            }
        } else {
            ingredientListModel.clear();
            recipesDisplayed.setText("No recipes found containing:\n" + ingredients);
        }
    }
    
    public void addIngredient() {
        // find out how to check for numbers and symbols, get rid of em.
        // pattern compile thing and matching..
        if (ingredientSearchTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Please enter a valid ingredient");
        } else if (ingredientListModel.contains(ingredientSearchTextField.getText())) {
            JOptionPane.showMessageDialog(mainPanel, "This ingredient is already added");
        } else {
            String ingredient = ingredientSearchTextField.getText();
            if (ingredientListModel.size() < 3) {
                ingredientListModel.addElement(ingredient);
                ingredientSearchTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Three ingredients already entered");
                ingredientSearchTextField.setText("");
            }
        }
    }
    
    public void deleteIngredient() {
        int selectedIndex = ingredientList.getSelectedIndex();
        if (selectedIndex != -1) {
            ingredientListModel.remove(selectedIndex);
        }
    }
    
    public void searchForRecipes() {
        if (!ingredientListModel.isEmpty()) {
            String ingredientsURLChunk = "";
            String ingredientsSearched = "";
            for (int x = 0 ; x < ingredientListModel.size() ; x++) {
                if (ingredientsURLChunk.isEmpty()) {
                    ingredientsURLChunk = ingredientListModel.get(x);
                    ingredientsSearched = ingredientListModel.get(x);
                } else {
                    ingredientsURLChunk = ingredientsURLChunk + ",+" + ingredientListModel.get(x);
                    ingredientsSearched = ingredientsSearched + ", " + ingredientListModel.get(x);
                }
            }
            Recipes[] matchingRecipes = controller.searchByIngredient(ingredientsURLChunk);
            displayMatchingRecipes(matchingRecipes, ingredientsSearched);
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Please enter ingredients");
        }
    }
    
    public void getRecipeSteps(int recipeId, String selectedRecipeName) {
        controller.askContactForRecipe(recipeId, selectedRecipeName);
        
    }
}
