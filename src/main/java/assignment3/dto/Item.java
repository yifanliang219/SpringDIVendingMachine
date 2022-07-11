package assignment3.dto;

import java.math.BigDecimal;

public class Item {

    private final int itemId;
    private final String itemName;
    private final BigDecimal unitPrice;
    private int numberInStock;

    public Item(int itemId, String itemName, BigDecimal unitPrice, int numberInStock) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.numberInStock = numberInStock;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }

    @Override
    public String toString(){
        return String.format("%d,%s,%s,%d",
                this.itemId,
                this.itemName,
                this.unitPrice,
                this.numberInStock);
    }

    public String item_info(){
        return String.format("Item id: %d, Item name: %s, Price: £%s", itemId, itemName, unitPrice);
    }

    public static Item parse(String line) {
        String[] attributes = line.split(",");
        int id = Integer.parseInt(attributes[0]);
        String name = attributes[1];
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(attributes[2]));
        int quantity = Integer.parseInt(attributes[3]);
        return new Item(id, name, price, quantity);
    }

}
