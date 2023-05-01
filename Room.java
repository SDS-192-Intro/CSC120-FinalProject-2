import java.util.ArrayList;

public class Room {
    
    ArrayList<Item> items = new ArrayList<Item>();
    String name;
    String description;
    Item addressing;

    public Room (String name, String description){
        this.name=name;
        this.description=description;
        this.addressing=null;
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

    public boolean isInRoom(String item){
        ArrayList<String> stringItems=new ArrayList<String>();
        for (Item i :items){
            stringItems.add(i.getName());
        }
        if (stringItems.contains(item)){
            return true;
        }
        else{
            return false;
        }
    }

    public Item returnItem(String item){
        for(Item i:items){
            if (item.equals(i.getName())){
                return i;
            }
        }
        return null;
    }

    public void conversation(ArrayList<String> wordList){
        String word1=wordList.get(0);
        String word2=wordList.get(1);
        
        //check if they say "look around"
        if (word1.equals("Look")||word1.equals("look")&&word2.equals("around")){
            System.out.println(this.lookAround());
        }

        //check if they say 'go to' that the item is in room 
        try{
            String word3=wordList.get(2);
            Item item=this.returnItem(word3);
            Boolean isGo=false;
            if (word1.equals("Go")||word1.equals("go")){
                isGo=true;
            }
            if(isGo&& word2.equals("to")){
                //if the item is not an item --fill out 
                
                //if the item is not in the room 
                if(!this.isInRoom(word3)){
                    System.out.println("This is not an item or this item is not in this room. "+this.lookAround());
                }
                //if the item IS in the room 
                else{
                    //if the item has a parent, don't let them go right to the item 
                    if (item.hasParent()){
                        Item parent=item.getParent();

                        //if we are already addressing the parent 
                        if(this.addressing==parent){
                            //then we are allowed to address the item  
                            this.addressing=item;
                            System.out.println("You are at the "+item.getName());
                        }
                        else{
                            System.out.println("You cannot get to "+item.getName()+" without first going to "+parent.getName()+". Try calling 'Go to' "+parent.getName());
                        }
                    }
                    else{
                        this.addressing=item;
                        System.out.println("You are at the "+item.getName());
                    }
                }
            }
                
            } catch (Exception e){
            //I don't care 
        }
    }


}
