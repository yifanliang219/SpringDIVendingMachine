package assignment3.services;

import assignment3.dto.Coin;
import assignment3.dto.Item;
import assignment3.dto.VM_State;
import assignment3.dto.VendingMachine;
import assignment3.repository.Inventory;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VM_Services {

    private final VendingMachine vendingMachine;
    private final Inventory inventory;

    private final String order_history_file_location; 

    @Autowired
    public VM_Services(VendingMachine vendingMachine, Inventory inventory) {
        this.vendingMachine = vendingMachine;
        this.inventory = inventory;
        this.order_history_file_location = "order_history.txt";
        initialize_vm();
    }
    
    public VM_Services(VendingMachine vendingMachine, Inventory inventory, String order_history_file_location) {
        this.vendingMachine = vendingMachine;
        this.inventory = inventory;
        this.order_history_file_location = order_history_file_location;
        initialize_vm();
    }

    private void initialize_vm() {
        vendingMachine.setCurrentState(VM_State.WAIT_FOR_NEXT_USER);
        vendingMachine.setInputMoney(BigDecimal.ZERO);
    }

    public VM_State getCurrentState() {
        return vendingMachine.getCurrentState();
    }

    public void setCurrentState(VM_State currentState) {
        vendingMachine.setCurrentState(currentState);
    }

    public Item getSelectedItem() {
        return vendingMachine.getSelectedItem();
    }

    public void setSelectedItem(Item selectedItem) {
        vendingMachine.setSelectedItem(selectedItem);
    }

    public BigDecimal getInputMoney() {
        return vendingMachine.getInputMoney();
    }

    public void setInputMoney(BigDecimal inputMoney) {
        vendingMachine.setInputMoney(inputMoney);
    }

    public String display_items() {
        StringBuilder sb = new StringBuilder();
        inventory.getItems().values().forEach(item -> sb.append(item.item_info()).append("\n"));
        return sb.toString();
    }

    public Item getItemById(int id) {
        return inventory.getItems().get(id);
    }

    public Map<Coin, Integer> getChange(BigDecimal change) {
        Map<Coin, Integer> coinsToReturn = new HashMap<>();
        List<Coin> coins = new LinkedList<>(Arrays.asList(Coin.values()));
        BigDecimal residual = change;
        while (residual.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ZERO) > 0) {
            Coin coin = coins.get(0);
            if (coin.getValue().setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(residual) > 0) {
                coins.remove(coin);
            } else {
                if (coinsToReturn.containsKey(coin)) {
                    coinsToReturn.put(coin, coinsToReturn.get(coin) + 1);
                } else {
                    coinsToReturn.put(coin, 1);
                }
                residual = residual.subtract(coin.getValue());
            }
        }
        return coinsToReturn;
    }

    public void vend(Item item) {
        item.setNumberInStock(item.getNumberInStock() - 1);
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            Date date = new Date();
            PrintWriter pw = new PrintWriter(new FileWriter(order_history_file_location, true));
            pw.write(String.format("Item Id=%d, Name=%s, Sold £%s. %s\n", item.getItemId(),
                    item.getItemName(),
                    item.getUnitPrice(),
                    date.toString()));
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update_inventory() {
        inventory.save_to_file();
    }

}
