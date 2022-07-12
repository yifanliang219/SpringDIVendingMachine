package assignment3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import assignment3.controllers.VM_Controller;
import assignment3.dto.VendingMachine;
import assignment3.repository.Inventory;
import assignment3.services.VM_Services;

public class VM_Main {

    public static void main(String[] args){
    	
    	AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
    	appContext.scan("assignment3");
    	appContext.refresh();
    	VM_Controller controller = appContext.getBean(VM_Controller.class);
        controller.run();

    }

}
