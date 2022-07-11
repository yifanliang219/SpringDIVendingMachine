package assignment3.exceptions;

import assignment3.dto.Item;

public class ItemNotAvailableException extends RuntimeException {

    private final String message;

    public ItemNotAvailableException(Item item){
        this.message = "Item " + item.getItemName() + " is out of stock.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
