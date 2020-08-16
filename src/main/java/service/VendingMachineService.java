package service;

import java.util.HashMap;
import java.util.Map;

public class VendingMachineService {

    private VendingMachineImpl vendingMachine;

    public VendingMachineService(int outlets) {
        this.vendingMachine = new VendingMachineImpl(outlets);
    }

    // Adding the ingredients to the Vending Machine Stock.
    // If there is already some stock of ingredient it will add with the existing quantity else it will add new ingredient.
    public void addIngredients(HashMap<String, Long> ingredientMap) {
        HashMap<String, Long> currentIngredientsStock = VendingMachineImpl.getCurrentIngredientStock();
        for (Map.Entry<String, Long> entry : ingredientMap.entrySet()) {
            String ingredientName = entry.getKey();
            Long ingredientQuantity = entry.getValue();
            if (currentIngredientsStock.containsKey(ingredientName)) {
                vendingMachine.updateExistingIngredientQuantity(ingredientName, ingredientQuantity);
            } else {
                vendingMachine.addNewIngredient(ingredientName, ingredientQuantity);
            }
        }
    }

    // Starts preparing all the beverages. It parallely makes all the beverages using threads of thread pool.
    public void prepareAllBeverages(HashMap<String, Object> beverages) {
        for (Map.Entry<String, Object> entry : beverages.entrySet()) {
            String beverageName = entry.getKey();
            HashMap<String, Long> ingredients = (HashMap<String, Long>)entry.getValue();
            vendingMachine.prepareBeverage(beverageName, ingredients);
        }
    }

    // Stopping down the machine.
    public void shutdownMachine() {
        vendingMachine.shutdown();
    }

}
