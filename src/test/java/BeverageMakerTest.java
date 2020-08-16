import service.BeverageMaker;
import service.VendingMachineImpl;
import org.junit.Before;
import org.junit.Test;
import service.VendingMachineService;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class BeverageMakerTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private BeverageMaker beverageMaker;
    HashMap<String, Long> beverage1;
    private VendingMachineService vendingMachineService;
    private int outlets = 3;


    @Before
    public void init() {
        beverage1 = new HashMap<>();
        beverage1.put("hot_water", 200L);
        beverage1.put("hot_milk", 100L);
        beverage1.put("ginger_syrup", 10L);
        beverage1.put("sugar_syrup", 10L);
        beverage1.put("tea_leaves_syrup", 30L);
        beverageMaker = new BeverageMaker("hot_tea", beverage1);
        vendingMachineService = new VendingMachineService(outlets);
    }

    @Test
    public void checkStockAndPrepareBeverageTest() {
        HashMap<String, Long> ingredientsMap = new HashMap<>();
        ingredientsMap.put("hot_water", 500L);
        ingredientsMap.put("hot_milk", 500L);
        ingredientsMap.put("ginger_syrup", 100L);
        ingredientsMap.put("sugar_syrup", 100L);
        ingredientsMap.put("tea_leaves_syrup", 100L);
        vendingMachineService.addIngredients(ingredientsMap);

        HashMap<String, Long> currentStock = VendingMachineImpl.getCurrentIngredientStock();
        assertEquals(currentStock.size(), 5);
        assertEquals(currentStock.get("hot_water"), new Long(500));
        assertEquals(currentStock.get("hot_milk"), new Long(500));
        assertEquals(currentStock.get("ginger_syrup"), new Long(100));
        assertEquals(currentStock.get("sugar_syrup"), new Long(100));
        assertEquals(currentStock.get("tea_leaves_syrup"), new Long(100));

        beverageMaker.checkStockAndPrepareBeverage("hot_tea", beverage1);

        assertEquals(currentStock.size(), 5);
        assertEquals(currentStock.get("hot_water"), new Long(300));
        assertEquals(currentStock.get("hot_milk"), new Long(400));
        assertEquals(currentStock.get("ginger_syrup"), new Long(90));
        assertEquals(currentStock.get("sugar_syrup"), new Long(90));
        assertEquals(currentStock.get("tea_leaves_syrup"), new Long(70));

        currentStock.clear();
    }

    @Test
    public void insufficientStockToPrepareBeverageTest() {
        HashMap<String, Long> ingredientsMap = new HashMap<>();
        ingredientsMap.put("hot_water", 100L);
        ingredientsMap.put("hot_milk", 500L);
        ingredientsMap.put("ginger_syrup", 100L);
        ingredientsMap.put("sugar_syrup", 100L);
        ingredientsMap.put("tea_leaves_syrup", 100L);
        vendingMachineService.addIngredients(ingredientsMap);

        HashMap<String, Long> currentStock = VendingMachineImpl.getCurrentIngredientStock();
        assertEquals(currentStock.size(), 5);
        assertEquals(currentStock.get("hot_water"), new Long(100));
        assertEquals(currentStock.get("hot_milk"), new Long(500));
        assertEquals(currentStock.get("ginger_syrup"), new Long(100));
        assertEquals(currentStock.get("sugar_syrup"), new Long(100));
        assertEquals(currentStock.get("tea_leaves_syrup"), new Long(100));

        beverageMaker.checkStockAndPrepareBeverage("hot_tea", beverage1);

        assertEquals(currentStock.size(), 5);
        assertEquals(currentStock.get("hot_water"), new Long(100));
        assertEquals(currentStock.get("hot_milk"), new Long(500));
        assertEquals(currentStock.get("ginger_syrup"), new Long(100));
        assertEquals(currentStock.get("sugar_syrup"), new Long(100));
        assertEquals(currentStock.get("tea_leaves_syrup"), new Long(100));

        currentStock.clear();
    }

    @Test
    public void unAvailableIngredientToPrepareBeverageTest() {
        HashMap<String, Long> ingredientsMap = new HashMap<>();
        ingredientsMap.put("hot_water", 500L);
        ingredientsMap.put("hot_milk", 500L);
        ingredientsMap.put("ginger_syrup", 100L);
        ingredientsMap.put("sugar_syrup", 100L);

        vendingMachineService.addIngredients(ingredientsMap);

        HashMap<String, Long> currentStock = VendingMachineImpl.getCurrentIngredientStock();
        assertEquals(currentStock.size(), 4);
        assertEquals(currentStock.get("hot_water"), new Long(500));
        assertEquals(currentStock.get("hot_milk"), new Long(500));
        assertEquals(currentStock.get("ginger_syrup"), new Long(100));
        assertEquals(currentStock.get("sugar_syrup"), new Long(100));

        beverageMaker.checkStockAndPrepareBeverage("hot_tea", beverage1);

        assertEquals(currentStock.size(), 4);
        assertEquals(currentStock.get("hot_water"), new Long(500));
        assertEquals(currentStock.get("hot_milk"), new Long(500));
        assertEquals(currentStock.get("ginger_syrup"), new Long(100));
        assertEquals(currentStock.get("sugar_syrup"), new Long(100));

        currentStock.clear();
    }

}
