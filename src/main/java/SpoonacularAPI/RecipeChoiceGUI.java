package SpoonacularAPI;

import SpoonacularAPI.BasicRecipeObjects.RecipeIngredients;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    RecipeChoiceGUI(RecipeController controller) {

        this.controller = controller;

        setTitle("Recipe Finder");
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        configureIngredientJList();
        configureMatchingRecipeJTable();
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
    
    public void addActionListeners() {
    
        JPopupMenu rightClickMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        rightClickMenu.add(deleteMenuItem);
        deleteMenuItem.addActionListener(e -> {
            deleteIngredient();
        });
        
        ingredientList.setComponentPopupMenu(rightClickMenu);
        ingredientList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selection = ingredientList.locationToIndex(e.getPoint());
                ingredientList.setSelectedIndex(selection);
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
        
        quitButton.addActionListener(e -> controller.quitProgram());
        
        addIngredientButton.addActionListener(e -> {
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
        });
        
        searchButton.addActionListener(e -> {
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
                RecipeIngredients[] matchingRecipes = controller.searchByIngredient(ingredientsURLChunk);
                displayMatchingRecipes(matchingRecipes, ingredientsSearched);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Please enter ingredients");
            }
        });
    }
    
    // add listeners
    
    public void displayMatchingRecipes(RecipeIngredients[] recipes, String ingredients) {
        
        // ADD VALIDATION TO SEE IF THERE ARE ANY RECIPES
        // breaks if no recipes match.
        if (recipes != null) {
            for (RecipeIngredients r : recipes) {
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
    
    public void deleteIngredient() {
        int selectedIndex = ingredientList.getSelectedIndex();
        ingredientListModel.remove(selectedIndex);
    }
}
