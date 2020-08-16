package service;

import java.util.HashMap;
import java.util.Map;

public class BeverageMaker implements Runnable {

    private String beverage;
    private HashMap<String, Long> ingredients;
    private HashMap<String, Long> totalIngredientStock;

    public BeverageMaker(String beverage, HashMap<String, Long> ingredients) {

        this.beverage = beverage;
        this.ingredients = ingredients;
    }

    public void run() {
        checkStockAndPrepareBeverage(beverage, ingredients);
    }

    // Checks the current Ingredient Stock and prepare the beverage
    // totalIngredientStock is synchronized because its a critical section where multiple threads try to access it simultaneously.
    public void checkStockAndPrepareBeverage(String beverage, Map<String, Long> ingredients) {
        totalIngredientStock = VendingMachineImpl.getCurrentIngredientStock();
        synchronized (totalIngredientStock) {
            boolean isStockAvailable = checkIngredientStock(beverage, ingredients);
            if (isStockAvailable) {
                prepareBeverage(ingredients);
                System.out.println(beverage + " is prepared");
            }
        }
    }

    // Checking the stock of the ingredients if all the ingredients are available and sufficient to prepare beverage then it return true, else returns false.
    private boolean checkIngredientStock(String beverage, Map<String, Long> ingredients) {
        for (Map.Entry<String, Long> entry : ingredients.entrySet()) {
            String ingredientName = entry.getKey();
            Long quantity = entry.getValue();
            try {
                checkAvailabilityOfIngredient(ingredientName);
                checkSufficiencyOfIngredient(ingredientName, quantity);
            }
            catch (Exception ex) {
                System.out.println(beverage +" cannot be prepared because " + ex.getMessage());
                return false;
            }
        }
        return true;
    }

    // Starts preparing beverage where it will reduce the ingredient quantity from the available ingredient stock to make beverage.
    private void prepareBeverage(Map<String, Long> ingredients) {
        for (Map.Entry<String, Long> entry : ingredients.entrySet()) {
            String ingredientName = entry.getKey();
            Long quantity = entry.getValue();
            totalIngredientStock.put(ingredientName, totalIngredientStock.get(ingredientName) - quantity);
        }
    }

    // Checking if ingredient is sufficient to make beverage else throws an exception.
    private void checkSufficiencyOfIngredient(String ingredientName, Long quantity) throws Exception {
        if (totalIngredientStock.get(ingredientName) < quantity) {
            throw new Exception(ingredientName + " is not sufficient");
        }
    }

    // Checking if ingredient is available to make beverage else throws an exception.
    private void checkAvailabilityOfIngredient(String ingredientName) throws Exception {
        if(!totalIngredientStock.containsKey(ingredientName)) {
            throw new Exception(ingredientName + " is not available");
        }
    }
}
