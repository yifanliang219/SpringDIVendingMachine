package assignment3.exceptions;

public class ItemNotFoundException extends RuntimeException {

    private final String message;

    public ItemNotFoundException(int id){
        this.message = "Item with id=" + id + " is not found.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
