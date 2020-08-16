import service.VendingMachineImpl;
import org.awaitility.Awaitility;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class VendingMachineImplTest {

    private VendingMachineImpl vendingMachine;
    private int outlets = 3;


    @Before
    public void init() {
        vendingMachine = new VendingMachineImpl(outlets);
    }

    @Test
    public void InitialStockEmptyTest() {
        HashMap<String, Long> initialStock = VendingMachineImpl.getCurrentIngredientStock();
        assertEquals(initialStock.size(), 0);
    }

    @Test
    public void TestAfterAddingIngredients() {
        HashMap<String, Long> initialStock = VendingMachineImpl.getCurrentIngredientStock();
        initialStock.clear();
        HashMap<String, Long> initialStockClone = (HashMap<String, Long>) initialStock.clone();
        vendingMachine.addNewIngredient("Water", 100L);

        HashMap<String, Long> currentStock = VendingMachineImpl.getCurrentIngredientStock();
        assertNotEquals(initialStockClone.size(), currentStock.size());
        currentStock.clear();
    }

    @Test
    public void TestToMakeBeverageWithSuffiecientStock() {
        HashMap<String, Long> initialStock = VendingMachineImpl.getCurrentIngredientStock();
        initialStock.clear();
        vendingMachine.addNewIngredient("hot_water", 300L);
        vendingMachine.addNewIngredient("ginger_syrup", 30L);
        vendingMachine.addNewIngredient("sugar_syrup", 50L);
        vendingMachine.addNewIngredient("tea_leaves_syrup", 30L);

        HashMap<String, Long> beverage = new HashMap<String, Long>();
        beverage.put("hot_water", 300L);
        beverage.put("ginger_syrup", 30L);
        beverage.put("sugar_syrup", 50L);
        beverage.put("tea_leaves_syrup", 30L);
        vendingMachine.prepareBeverage("black_tea", beverage);
        HashMap<String, Long> currentStock = VendingMachineImpl.getCurrentIngredientStock();
        Awaitility.await().atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    assertEquals(currentStock.get("hot_water"), new Long(0));
                    assertEquals(currentStock.get("ginger_syrup"), new Long(0));
                    assertEquals(currentStock.get("sugar_syrup"), new Long(0));
                    assertEquals(currentStock.get("tea_leaves_syrup"), new Long(0));
                });

        currentStock.clear();
    }

    @Test
    public void TestToMakeBeverageWithInSuffiecientStock() {
        HashMap<String, Long> initialStock = VendingMachineImpl.getCurrentIngredientStock();
        initialStock.clear();
        vendingMachine.addNewIngredient("hot_water", 300L);
        vendingMachine.addNewIngredient("ginger_syrup", 30L);
        vendingMachine.addNewIngredient("sugar_syrup", 50L);
        vendingMachine.addNewIngredient("tea_leaves_syrup", 30L);

        HashMap<String, Long> beverage = new HashMap<String, Long>();
        beverage.put("hot_water", 400L);
        beverage.put("ginger_syrup", 30L);
        beverage.put("sugar_syrup", 50L);
        beverage.put("tea_leaves_syrup", 30L);
        vendingMachine.prepareBeverage("black_tea", beverage);

        HashMap<String, Long> currentStock = VendingMachineImpl.getCurrentIngredientStock();

        Awaitility.await().atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    assertEquals(currentStock.get("hot_water"), new Long(300L));
                    assertEquals(currentStock.get("ginger_syrup"), new Long(30L));
                    assertEquals(currentStock.get("sugar_syrup"), new Long(50L));
                    assertEquals(currentStock.get("tea_leaves_syrup"), new Long(30L));
                });

        currentStock.clear();
    }

    @Test
    public void TestToMakeBeverageWithUnAvailableStock() {
        HashMap<String, Long> initialStock = VendingMachineImpl.getCurrentIngredientStock();
        initialStock.clear();
        vendingMachine.addNewIngredient("hot_water", 300L);
        vendingMachine.addNewIngredient("ginger_syrup", 30L);
        vendingMachine.addNewIngredient("sugar_syrup", 50L);

        HashMap<String, Long> beverage = new HashMap<String, Long>();
        beverage.put("hot_water", 400L);
        beverage.put("ginger_syrup", 30L);
        beverage.put("sugar_syrup", 50L);
        beverage.put("tea_leaves_syrup", 30L);

        CompletableFuture<String> future = new CompletableFuture<>();

        vendingMachine.prepareBeverage("black_tea", beverage);
        HashMap<String, Long> currentStock = VendingMachineImpl.getCurrentIngredientStock();
        Awaitility.await().atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    assertEquals(currentStock.get("hot_water"), new Long(300L));
                    assertEquals(currentStock.get("ginger_syrup"), new Long(30L));
                    assertEquals(currentStock.get("sugar_syrup"), new Long(50L));
                });

        currentStock.clear();
    }
}
