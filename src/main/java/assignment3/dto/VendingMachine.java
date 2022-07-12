package assignment3.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class VendingMachine {

    private VM_State currentState;
    private Item selectedItem;
    private BigDecimal inputMoney;

    public VM_State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(VM_State currentState) {
        this.currentState = currentState;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public BigDecimal getInputMoney() {
        return inputMoney;
    }

    public void setInputMoney(BigDecimal inputMoney) {
        this.inputMoney = inputMoney;
    }

}
