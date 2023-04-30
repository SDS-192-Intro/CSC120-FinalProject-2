import java.util.ArrayList;

public class Room {
    
    ArrayList<Item> items = new ArrayList<Item>();
    String name;
    String description;

    public Room (String name, String description){
        this.name=name;
        this.description=description;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public void addItem(Item i){
        items.add(i);
    }

    public String lookAround(){
        String toReturn="In this room, there is: \n";
        for (int i=0; i<items.size(); i++){
           toReturn+=items.get(i).description+"\n";
        }
        return toReturn;
    }

    public boolean isInRoom(Item i){
        if (items.contains(i)){
            return true;
        }else{
            return false;
        }
    }


}
