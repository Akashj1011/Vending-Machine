package service;

import interfaces.VendingMachine;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VendingMachineImpl implements VendingMachine {

    // We have executorService as a thread pool to prepare all the beverages parallely.
    // currentIngredientStock and outlets are static variables because it is common for all the objects of Vending Machine.
    // currentIngredientStock is critical object, so we have put it in synchronized block.

    private static int outlets;
    private static HashMap<String, Long> currentIngredientStock = new HashMap<String, Long>();
    private ExecutorService executorService;

    public VendingMachineImpl(int outlets) {
        this.outlets = outlets;
        executorService = Executors.newFixedThreadPool(outlets);

    }

    // Method to get for current Ingredient stock
    public static HashMap<String, Long> getCurrentIngredientStock() {
        return currentIngredientStock;
    }

    // Adding new ingredient to current stock
    public void addNewIngredient(String ingredientName, Long ingredientQuantity){
        synchronized (currentIngredientStock) {
            currentIngredientStock.put(ingredientName, ingredientQuantity);
        }
    }

    // Updating current stock
    public void updateExistingIngredientQuantity(String ingredientName, Long ingredientQuantity){
        synchronized (currentIngredientStock) {
            long availableIngredientQuantity = currentIngredientStock.get(ingredientName);
            currentIngredientStock.put(ingredientName, availableIngredientQuantity + ingredientQuantity);
        }
    }

    // Removing ingredient from VendingMachine
    public void removeIngredient(String ingredient) {
        synchronized (currentIngredientStock) {
            currentIngredientStock.remove(ingredient);
        }
    }

    // Preparing beverage with the help of thread
    public void prepareBeverage(String beverageName, HashMap<String, Long> ingredients) {
        executorService.execute(new BeverageMaker(beverageName, ingredients));
    }

    // Waiting for all threads to complete and then shut down.
    public void shutdown() {
        executorService.shutdown();
    }
}
