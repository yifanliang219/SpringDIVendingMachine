package assignment3.exceptions;

import java.math.BigDecimal;

public class MoneyNotEnoughException extends RuntimeException {

    private final String message;

    public MoneyNotEnoughException(BigDecimal priceMinusPutIn){
        this.message = "Please enter £" + priceMinusPutIn + " to purchase the item.";
    }

    @Override
    public String getMessage() {
        return message;
    }

}
