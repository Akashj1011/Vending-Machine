import service.VendingMachineImpl;
import org.awaitility.Awaitility;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import service.VendingMachineService;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class VendingMachineServiceTest {

    private VendingMachineService vendingMachineService;
    private int outlets = 3;

    @Before
    public void init() {
        vendingMachineService = new VendingMachineService(outlets);
    }

    @Test
    public void InitialStockEmptyTest() {
        HashMap<String, Long> initialStock = VendingMachineImpl.getCurrentIngredientStock();
        assertEquals(initialStock.size(), 0);
    }

    @Test
    public void TestOnAddingIngredients() {
        HashMap<String, Long> initialStock = VendingMachineImpl.getCurrentIngredientStock();
        initialStock.clear();
        HashMap<String, Long> initialStockClone = (HashMap<String, Long>) initialStock.clone();
        HashMap<String, Long> ingredientsMap = new HashMap<>();
        ingredientsMap.put("Water", 100L);
        ingredientsMap.put("ginger_syrup", 30L);
        vendingMachineService.addIngredients(ingredientsMap);

        HashMap<String, Long> currentStock = VendingMachineImpl.getCurrentIngredientStock();
        assertNotEquals(initialStockClone.size(), currentStock.size());
        assertEquals(currentStock.size(), 2);
        assertEquals(initialStockClone.size(), 0);
        currentStock.clear();
    }

    @Test
    public void TestForPreparingAllBeverages() {
        HashMap<String, Long> initialStock = VendingMachineImpl.getCurrentIngredientStock();
        initialStock.clear();
        HashMap<String, Long> initialStockClone = (HashMap<String, Long>) initialStock.clone();
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

        HashMap<String, Object> beverages = new HashMap<>();


        HashMap<String, Object> beverage1 = new HashMap<>();
        beverage1.put("hot_water", 200L);
        beverage1.put("hot_milk", 100L);
        beverage1.put("ginger_syrup", 10L);
        beverage1.put("sugar_syrup", 10L);
        beverage1.put("tea_leaves_syrup", 30L);
        JSONObject beverageJson1 = new JSONObject();
        beverageJson1.putAll( beverage1 );

        HashMap<String, Long> beverage2 = new HashMap<>();
        beverage2.put("hot_water", 100L);
        beverage2.put("hot_milk", 400L);
        beverage2.put("ginger_syrup", 30L);
        beverage2.put("sugar_syrup", 50L);
        beverage2.put("tea_leaves_syrup", 30L);
        JSONObject beverageJson2 = new JSONObject();
        beverageJson2.putAll( beverage2 );

        HashMap<String, Long> beverage3 = new HashMap<>();
        beverage3.put("hot_water", 300L);
        beverage3.put("ginger_syrup", 30L);
        beverage3.put("sugar_syrup", 50L);
        beverage3.put("tea_leaves_syrup", 30L);
        JSONObject beverageJson3 = new JSONObject();
        beverageJson3.putAll( beverage3 );

        HashMap<String, Long> beverage4 = new HashMap<>();
        beverage4.put("hot_water", 100L);
        beverage4.put("ginger_syrup", 30L);
        beverage4.put("sugar_syrup", 50L);
        beverage4.put("green_mixture", 30L);
        JSONObject beverageJson4 = new JSONObject();
        beverageJson4.putAll( beverage4 );

        beverages.put("hot_tea", beverageJson1);
        beverages.put("hot_coffee", beverage2);
        beverages.put("black_tea", beverageJson3);
        beverages.put("green_tea", beverageJson4);

        vendingMachineService.prepareAllBeverages(beverages);
        Awaitility.await().atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    assertEquals(currentStock.size(), 5);
                    assertNotEquals(currentStock.get("hot_water"), new Long(500));
                    assertNotEquals(currentStock.get("ginger_syrup"), new Long(100));
                    assertNotEquals(currentStock.get("sugar_syrup"), new Long(100));

                });
        currentStock.clear();
    }
}
