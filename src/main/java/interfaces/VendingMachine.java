package interfaces;

import java.util.HashMap;

public interface VendingMachine {

    void addNewIngredient(String ingredientName, Long ingredientQuantity);
    void updateExistingIngredientQuantity(String ingredientName, Long ingredientQuantity);
    void prepareBeverage(String beverage, HashMap<String, Long> ingredients);
    void removeIngredient(String beverage);
    void shutdown();

}
