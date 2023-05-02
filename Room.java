import java.util.ArrayList;

public class Room {
    
    ArrayList<Item> items = new ArrayList<Item>();
    String name;
    String description;
    Item addressing;
    Game game;

    public Room (String name, String description, Game game){
        this.name=name;
        this.description=description;
        this.addressing=null;
        this.game=game;
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
        for(Item i:this.items){
            if (item.equals(i.getName())){
                return i;
            }
        }
        return null;
    }

    public void conversation(ArrayList<String> wordList){
        String word1=wordList.get(0);
        String word2=wordList.get(1);

        //TRY GO TO FUNCTIONS
        try{
            
            //check if they say "look around"
            if (word1.equals("Look")||word1.equals("look")&&word2.equals("around")){
                System.out.println(this.lookAround());
            }

            //check if they say 'go to' that the item is in room 
            String word3=wordList.get(2);
            Item item=this.returnItem(word3);
            Boolean isGo=false;
            if (word1.equals("Go")||word1.equals("go")){
                isGo=true;
            }
            if(isGo&& word2.equals("to")){
                //if the item is not an item --fill out 
                //if you have jumped up on something -- fill out 
                //if the item is not in the room 
                
                if(!this.isInRoom(word3)){
                    //fix window seat bug 
                    if(!word3.equals("window")){
                        System.out.println("This is not an item or this item is not in this room. "+this.lookAround());
                    }
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
                            System.out.println("\nYou are at the "+item.getName());
                            item.showOptions();
                        }
                        else{
                            System.out.println("You cannot get to "+item.getName()+" without first going to "+parent.getName()+". Try calling 'Go to "+parent.getName()+"'");
                        }
                        }
                    else{
                        this.addressing=item;
                        System.out.println("\nYou are at the "+item.getName());
                        item.showOptions();
                        if(item.hasChild){
                            item.getChild().getDescription();
                            item.getChild().showOptions();
                        }
                    }
                }   
            }
        } catch (Exception e){
            //i don't care
        }


        //TRY CLIMB
        try{
            //IF you are addressing the item
            Item item2=this.returnItem(word2);
            if (this.addressing.equals(item2)){
                Boolean isClimb=word1.equals("Climb")||word1.equals("climb");
                //if the first word is "Climb"
                if(isClimb){
                    // //if the item has already been climbed 
                    // if(this.game.getClimbedOn().equals(item2)){
                    //     System.out.println("You have already climbed on this item.");
                    // }
                    //if the item isn't in room
                    if(!this.isInRoom(item2)){
                        System.out.println("This item is not in the room."+this.lookAround());
                    }
                    //if the item isn't climbable  
                    if(!item2.isClimbable){
                        System.out.println("This item is not climbable.");
                        item2.showOptions();
                    }
                    //check if the item is in the room and if it's climbable 
                    System.out.println(this.isInRoom(item2));
                    System.out.println(item2.isClimbable());
                    if (this.isInRoom(item2)&&item2.isClimbable()){

                        this.game.changeClimbedOn(item2);
                        System.out.println("\nYou have climbed on to the "+word2+".");
                        if (item2.hasChild()){
                            System.out.println("On top of the "+word2+" there is: "+item2.getChild().getDescription());
                            item2.getChild().showOptions();
                        }
                    }
                }
            }
            //if not addressing item-- DEBUG!!! 
            //else{
                //System.out.println("You cannot climb an item you are not at. Try 'Go to "+word2+"'");
           // }
        } catch (Exception e){
            //i don't care
        }


        //JUMP OFF 
        try{
            //get third word
            String word3=wordList.get(2);
            //make item from third word 
            Item item2=this.returnItem(word3);
            //if the first two words are jump and off
            Boolean isJump=word1.equals("jump")||word1.equals("Jump");
            if(isJump&&word2.equals("off")){
                //check if the item is jumpoffable
                if(item2.isJumpOffAble()){
                    //remove from climbedOn
                    this.game.changeClimbedOn(null);
                    System.out.println("You have jumped off of the "+word3);
                }
            }
        } catch (Exception e){
            //i don't care
        }


        //EAT 
        try{
            Item item2=this.returnItem(word2);
            //If you are addressing the item's parent 
            if(this.addressing.equals(item2.getParent())){
                //If the first word is eat 
                if(word1.equals("eat")||word1.equals("Eat")){
                    //Check whether you can eat the item 
                    if(item2.isEdible()){
                        this.game.changeHaveEaten(true);
                        System.out.println("Yum! You ate the "+word2);
                    }
                }
            } 
            //If not addressing item (debug)
           // else{
               // System.out.println("You cannot eat an item you are not at. Try 'Go to "+word2+"'");
            //}

        } catch (Exception e){
            //I don't care
        }


        //DRINK 
        try{
            Item item2=this.returnItem(word2);
            //If you are addressing the item's parent 
            if(this.addressing.equals(item2.getParent())){
                //if you have climbed parent!! --fill in 
                //If the first word is eat 
                if(word1.equals("drink")||word1.equals("Drink")){
                    //Check whether you can eat the item 
                    if(item2.isDrinkable()){
                        this.game.changeHaveDrunk(true);
                        System.out.println("Gulp! You drank the "+word2);
                    }
                }
            } 
            //If not addressing item (debug)
           // else{
               // System.out.println("You cannot drink an item you are not at. Try 'Go to "+word2+"'");
            //}

        } catch (Exception e){
            //I don't care
        }           
        


    }


}
