package assignment3.repository;

import assignment3.dto.Item;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Inventory {

    private final Map<Integer, Item> items = new HashMap<>();

    private final String file_location;

    @Autowired
    public Inventory(){
        this.file_location = "inventory.txt";
        load_from_file();
    }
    
    public Inventory(String file_location){
        this.file_location = file_location;
        load_from_file();
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    private void load_from_file(){
        try {
            Scanner scanner = new Scanner(new FileReader(file_location));
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                if(line.isEmpty()) break;
                Item item = Item.parse(line);
                items.put(item.getItemId(), item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void save_to_file(){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(file_location));
            items.values().forEach(item -> pw.println(item.toString()));
            pw.flush();
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
