import service.VendingMachineService;
import utility.InputJsonDataLoader;

import java.util.HashMap;

public class MainClass {

    public static void main(String args[]) {

        // Loading the data from the input file input.json which is placed in input.json file.
        InputJsonDataLoader loader = new InputJsonDataLoader();
        loader.loadJsonData();
        int outletsCount = loader.getOutletsCount();
        HashMap<String, Long> totalIngredients = loader.getTotalItemsQuantity();
        HashMap<String, Object> beverages = loader.getBeverages();

        // Adding ingredients to stock and serving the beverages ordered from the data loaded from input file.
        VendingMachineService vendingMachineService = new VendingMachineService(outletsCount);
        vendingMachineService.addIngredients(totalIngredients);
        vendingMachineService.prepareAllBeverages(beverages);

        // Stopping the execution.
        vendingMachineService.shutdownMachine();
    }
}
