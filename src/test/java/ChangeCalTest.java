import assignment3.dto.Coin;
import assignment3.dto.Item;
import assignment3.dto.VendingMachine;
import assignment3.repository.Inventory;
import assignment3.services.VM_Services;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

public class ChangeCalTest {

    Inventory inventory;
    VendingMachine vendingMachine;
    VM_Services services;
    Map<Coin, Integer> coinReturned;

    @Before
    public void init() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("test_inventory.txt"));
            pw.write("1,Water,0.49,10\n" +
                    "2,Juice,0.99,9\n" +
                    "3,Milk,1.2,8\n" +
                    "4,Coffee,1.8,7\n");
            pw.flush();
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        inventory = new Inventory("test_inventory.txt");
        vendingMachine = new VendingMachine();
        services = new VM_Services(vendingMachine, inventory, "test_order_history.txt");
    }

    @Test
    public void testInventory(){
        Map<Integer, Item> items  = inventory.getItems();
        Assert.assertEquals(items.get(1).getItemName(), "Water");
        Assert.assertEquals(items.get(2).getNumberInStock(), 9);
        Assert.assertEquals(items.get(3).getUnitPrice(), BigDecimal.valueOf(1.2));
        Assert.assertEquals(items.get(4).getItemId(), 4);
        Assert.assertEquals(items.keySet().size(), 4);
    }

    @Test
    public void testChangeCal() {

        coinReturned = services.getChange(BigDecimal.valueOf(0.7));
        Assert.assertEquals(coinReturned.get(Coin.FIFTY_PENCE).intValue(), 1);
        Assert.assertEquals(coinReturned.get(Coin.TWENTY_PENCE).intValue(), 1);
        Assert.assertEquals(coinReturned.keySet().size(), 2);

        coinReturned = services.getChange(BigDecimal.valueOf(1));
        Assert.assertEquals(coinReturned.get(Coin.ONE_POUND).intValue(), 1);
        Assert.assertEquals(coinReturned.keySet().size(), 1);

        coinReturned = services.getChange(BigDecimal.valueOf(1.2));
        Assert.assertEquals(coinReturned.get(Coin.ONE_POUND).intValue(), 1);
        Assert.assertEquals(coinReturned.get(Coin.TWENTY_PENCE).intValue(), 1);
        Assert.assertEquals(coinReturned.keySet().size(), 2);

        coinReturned = services.getChange(BigDecimal.valueOf(4));
        Assert.assertEquals(coinReturned.get(Coin.TWO_POUND).intValue(), 2);
        Assert.assertEquals(coinReturned.keySet().size(), 1);

    }

}
