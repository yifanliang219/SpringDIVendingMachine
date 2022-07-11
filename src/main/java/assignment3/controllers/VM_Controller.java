package assignment3.controllers;

import assignment3.dto.Coin;
import assignment3.dto.Item;
import assignment3.dto.VM_State;
import assignment3.exceptions.ItemNotAvailableException;
import assignment3.exceptions.ItemNotFoundException;
import assignment3.exceptions.MoneyNotEnoughException;
import assignment3.services.VM_Services;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class VM_Controller implements Runnable {

    private final VM_Services services;

    public VM_Controller(VM_Services services) {
        this.services = services;
    }


    @Override
    public void run() {
        while (true) {
            run_vm();
        }
    }

    private void run_vm() {
        VM_State state = services.getCurrentState();
        switch (state) {
            case WAIT_FOR_NEXT_USER:
                wait_for_next_user();
                break;
            case SELECTING_ITEM:
                selecting_item();
                break;
            case PROCESSING_PAYMENT:
                processing_payment();
                break;
        }
    }

    private void wait_for_next_user() {
        System.out.println("Press Enter to start an order.");
        try {
            System.in.read();
            services.setCurrentState(VM_State.SELECTING_ITEM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selecting_item() {
        try {
            System.out.println("Please select an item. (Enter the item id.)\n");
            System.out.println(services.display_items());
            Scanner scanner = new Scanner(System.in);
            int id = Integer.parseInt(scanner.nextLine());
            Item item = services.getItemById(id);
            if (item == null) throw new ItemNotFoundException(id);
            int number_in_stock = item.getNumberInStock();
            if (number_in_stock == 0) throw new ItemNotAvailableException(item);
            services.setSelectedItem(item);
            services.setCurrentState(VM_State.PROCESSING_PAYMENT);
        } catch (ItemNotFoundException | ItemNotAvailableException e) {
            System.err.println(e.getMessage());
        }
    }

    private void processing_payment() {
        try {
            Item item = services.getSelectedItem();
            BigDecimal price = item.getUnitPrice();
            BigDecimal moneyPutIn = services.getInputMoney();
            BigDecimal priceMinusPutIn = price.subtract(moneyPutIn);
            if (priceMinusPutIn.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ZERO) < 0) {
                BigDecimal change = priceMinusPutIn.negate();
                Map<Coin, Integer> coinsReturned = services.getChange(change);
                System.out.println("Payment successful. Change:");
                coinsReturned.forEach((coin, number) -> System.out.println(number + " of " + coin.name()));
                System.out.println();
                services.vend(item);
                services.update_inventory();
                services.setCurrentState(VM_State.WAIT_FOR_NEXT_USER);
            } else throw new MoneyNotEnoughException(priceMinusPutIn);
        } catch (MoneyNotEnoughException e) {
            System.err.println(e.getMessage());
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine().trim();
            double input = Double.parseDouble(line);
            services.setInputMoney(services.getInputMoney().add(BigDecimal.valueOf(input)));
        }
    }

}
