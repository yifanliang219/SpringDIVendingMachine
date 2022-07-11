package assignment3;

import assignment3.controllers.VM_Controller;
import assignment3.dto.VendingMachine;
import assignment3.repository.Inventory;
import assignment3.services.VM_Services;

public class VM_Main {

    public static void main(String[] args){

        Inventory inventory = new Inventory("inventory.txt");
        VendingMachine vendingMachine = new VendingMachine();
        VM_Controller controller = new VM_Controller(new VM_Services(vendingMachine, inventory, "order_history.txt"));
        controller.run();

    }

}
