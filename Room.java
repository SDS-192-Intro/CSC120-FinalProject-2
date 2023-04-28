import java.util.ArrayList;

public class Room {
    
    ArrayList<Item> items = new ArrayList<Item>();
    String name;

    public Room (String name){
        this.name=name;
    }

    public void addItem(Item i){
        items.add(i);
    }

    public void lookAround(){
        String toPrint="In this room, there is: \n";
        for (int i=0; i<items.size(); i++){
           toPrint+=items.get(i).description+"\n";
        }
        System.out.println(toPrint);
    }

    public boolean isInRoom(Item i){
        if (items.contains(i)){
            return true;
        }else{
            return false;
        }

    }


}
