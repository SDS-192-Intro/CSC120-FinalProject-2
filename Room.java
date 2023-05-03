import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Objects;

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
            if (items.get(i).hasParent()){
                toReturn+=items.get(i).description+" on top of the "+items.get(i).getParent().getName()+"\n";
            }else{
                toReturn+=items.get(i).description+"\n";
            }
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
        //General note: change success attribute in game every time the program parses a response,
        //to determine whether or not to print a general error message 

        String word1=wordList.get(0);
        String word2=wordList.get(1);

        //Check if they said "Look around"
        Boolean word1IsLook=word1.equals("Look")||word1.equals("look");
        if (word1IsLook &&word2.equals("around")){
            System.out.println(this.lookAround());
        }
    
        //"Go to the *item*"
        try{
            //Find the third & fourth word 
            String word3=wordList.get(2);
            String word4=wordList.get(3);

            //word1 should be "Go" or "go", word2 should be "to"
            //word3 should be "the", and word 4 should be the *item*

            Boolean word1IsGo=word1.equals("Go")||word1.equals("go");
                
            //Check if they have said "Go to the ..."
            if (word1IsGo && word2.equals("to") && word3.equals("the")){

                //Return the string from response as an item 
                Item item=this.returnItem(word4);

                //Check if you are already at that item
                //If this.addressing is not Null 
                if (Objects.nonNull(this.addressing)){
                    //If this.addressing contains item
                    if(this.addressing.equals(item)){
                        this.game.changeSuccess(true);
                        System.out.println("\nYou are already at the "+word4+".");
                        item.showOptions();
                        //Throw new exception to avoid re-updating this.addressing (it will not print)
                        throw new RuntimeException();
                    }
                }
                
                //Check that the item is in the room (also applies to locations)
                if (!this.isInRoom(item)){
                    this.game.changeSuccess(true);
                    System.out.println("\nThat is not a valid location nor item.");
                    this.lookAround();
                }

                //If the item is an item and is in the room 
                else{
                    //If item has a parent 
                    if(item.hasParent){
                        //You have to be climbed on to the parent 

                        //If you haven't climbed on anything yet, then you automatically won't be able to access child 
                        if(this.game.climbedOn==null){
                            this.game.changeSuccess(true);
                            System.out.println("You have to be climbed on to the "+item.getParent().getName()+" to go to the "+word4+". Try [Go to the "+item.getParent().getName()+"] and then climbing it.");
                        }
                        //If you have climbed on something, but it is not the right parent
                        if(!this.game.climbedOn.equals(item.getParent())){
                            this.game.changeSuccess(true);
                            System.out.println("You have to be climbed on to the "+item.getParent().getName()+" to go to the "+word4);
                        }
                        //They are climbed onto the parent 
                        else{
                            this.game.changeSuccess(true);
                            this.addressing=item;
                            System.out.println("\nYou are at the "+item.getName());
                            item.showOptions();
                        }
                    }
                    //If item doesn't have a parent
                    else{
                        this.game.changeSuccess(true);
                        this.addressing=item;
                        System.out.println("\nYou are at the "+item.getName());
                        item.showOptions();
                    }
                } 
            } } catch (Exception e){}

        
        //"Climb the *item*"
        try{
            //Get third word 
            String word3=wordList.get(2);

            //Check whether they said "Climb the *item*"
            Boolean firstWordIsClimb=word1.equals("climb")||word1.equals("Climb");
            if(firstWordIsClimb && word2.equals("the")){

                //Return the string from response as an item 
                Item item=this.returnItem(word3);

                //Check whether we are addressing the item 
                if (this.addressing.equals(item)){
                    //Check that the item is climbable 
                    if(item.isClimbable()){
                        //If this.climbedOn isn't void (to avoid throwing a runtime exception)
                        if(Objects.nonNull(this.game.getClimbedOn())){
                            //Check whether we have already climbed the item
                            if(this.game.getClimbedOn().equals(item)){
                                this.game.changeSuccess(true);
                                System.out.println("\nYou have already climbed onto the "+word3+".");
                            }
                        }
                        //Then you are allowed to climb the item 
                        else{
                            this.game.changeSuccess(true);
                            System.out.println("\nYou have climbed on "+word3+".");
                            this.game.changeClimbedOn(item);
                            //if the item has a child (ex. milk on table), then show options for child
                            if(item.hasChild()){
                                //change the wording for garden 
                                if(word3.equals("Pond")||word3.equals("pond")){
                                    this.game.changeSuccess(true);
                                    System.out.println("There are "+item.getChild().getName()+" in the "+word3+".");
                                    System.out.println("\nTo get to the "+item.getChild().getName()+" try [go to the "+item.getChild().getName()+"].");
                                }
                                else{
                                    this.game.changeSuccess(true);
                                    System.out.println("The "+item.getChild().getName()+" is on top of the "+word3+".");
                                    System.out.println("\nTo get to the "+item.getChild().getName()+" try [go to the "+item.getChild().getName()+"].");
                                }
                            }
                        }
                    }
                    //If not climbable, say that it is not climbable & print options 
                    else{
                        this.game.changeSuccess(true);
                        System.out.println("The "+word3+" is not climbable.");
                        item.showOptions();
                    }
                }
                //If we are not addressing any item or if we are addressing another item
                if(Objects.isNull(this.addressing)||!this.addressing.equals(item)){
                    this.game.changeSuccess(true);
                    System.out.println("You cannot climb this item if you are not at it. Try 'Go to "+item.getName()+"'.");
                }
            }

        }catch(Exception e){}
        
        //Climb *item*
        try{
            //Check whether there is only two words (Don't want "Climb the *item*" to also end up here)
            if(wordList.size()==2){
                //Check whether they said "Climb *item*"
                if(word1.equals("Climb")||word1.equals("climb")){
                    //Return the item
                    Item item=this.returnItem(word2);
                    //Check whether we are addressing the item
                    if (this.addressing.equals(item)){
                        //Check that the item is climbable
                        if(item.isClimbable){
                            ///If this.climbedOn isn't void (to avoid throwing a runtime exception)
                            if(Objects.nonNull(this.game.getClimbedOn())){
                                //Check whether we have already climbed the item
                                if(this.game.getClimbedOn().equals(item)){
                                    this.game.changeSuccess(true);
                                    System.out.println("\nYou have already climbed onto the "+this.getName()+".");
                                }
                            }
                            //Then you are allowed to climb the item 
                            else{
                                this.game.changeSuccess(true);
                                System.out.println("\nYou have climbed on "+this.getName()+".");
                                this.game.changeClimbedOn(item);
                                //if the item has a child (ex. milk on table), then show options for child
                                if(item.hasChild()){
                                    System.out.println("The "+item.getChild().getName()+" is on top of the "+this.getName()+".");
                                    System.out.println("\nTo get to the "+item.getChild().getName()+" try [go to the "+item.getChild().getName()+"].");
                                }
                        }
                        }
                        //If not climbable, say that it is not climbable & print options 
                        else{
                            this.game.changeSuccess(true);
                            System.out.println("The "+this.getName()+" is not climbable.");
                            item.showOptions();
                        }   
                    }
                    //If we are not addressing any item or if we are addressing another item
                    if(Objects.isNull(this.addressing)||!this.addressing.equals(item)){
                        this.game.changeSuccess(true);
                        System.out.println("You cannot climb this item if you are not at it. Try 'Go to "+this.getName()+"'.");
                }
                }

            }
        }catch (Exception e){}


        //"Jump off of the *item*"
        try{
            //Get third, fourth, and fifth words 
            String word3=wordList.get(2);
            String word4=wordList.get(3);
            String word5=wordList.get(4);

            //If they said "Jump off of the *item*" 
            Boolean word1isJump=word1.equals("Jump")||word1.equals("jump");
            if(word1isJump && word2.equals("off") && word3.equals("of") && word4.equals("the")){
                Item item=this.returnItem(word5);
                 //If you have NOT climbed on to the item 
                if(Objects.isNull(this.game.getClimbedOn())){
                    this.game.changeSuccess(true);
                    System.out.println("\nYou need to have climbed onto the "+item.getName()+" in order to jump off of it. Try [climb the "+item.getName()+"].");
                }

                //If you have climbed onto the item
                if(this.game.getClimbedOn().equals(item)){
                    //If it is jump-off-able
                    if(item.isJumpOffable){
                        //If you are addressing it 
                        if(this.addressing.equals(item)){
                            //Then you may jump off 
                            this.game.changeSuccess(true);
                            //Change climbedOn attribute in the game 
                            this.game.changeClimbedOn(null);
                            System.out.println("\nYou have jumped off of the "+item.getName()+".");
                            System.out.println("You are now standing next to the "+item.getName()+" in the "+this.getName()+".\n");
                            System.out.println(this.lookAround()+"\nThere are doors connecting to: \n");
                            this.game.printNeighbors(this);
                            //throw runtime exception to avoid being caught in the final conditional
                            throw new RuntimeException();
                        }
                        //if you are addressing its child
                        if(item.hasChild()){
                            if(this.addressing.equals(item.getChild())){
                                //Then you may jump off 
                                this.game.changeSuccess(true);
                                //Change climbedOn attribute in the game 
                                this.game.changeClimbedOn(null);
                                System.out.println("\nYou have jumped off of the "+item.getName()+".");
                                System.out.println("You are now standing next to the "+item.getName()+" in the "+item.getName()+".");
                                System.out.println(this.lookAround()+"\nThere are doors connecting to: \n");
                                this.game.printNeighbors(this);
                                //throw runtime exception to avoid being caught in the final conditional
                                throw new RuntimeException();
                            }
                        }
                        //If you are NOT addressing the item
                        else{
                            this.game.changeSuccess(true);
                            System.out.println("\nIn order to jump off, you must first be *at* the "+item.getName()+". Try [go to the "+item.getName()+"], and then [jump off of the "+item.getName()+"].");
                            //throw runtime exception to avoid being caught in the final conditional
                            throw new RuntimeException();
                        }
                    }
                    //If it is NOT jump-off-able (only the case for the bathtub)
                    else{
                        this.game.changeSuccess(true);
                        System.out.println("\nYou cannot jump out of the slippery porcelain bath. Unfortuntely, this means that you will not be able to find the perfect nap spot before sunset.");
                        System.out.println("Ah well, you can just rest your eyes....");
                        this.game.changeStuckInBath(true);
                        //throw runtime exception to avoid being caught in the final conditional
                        throw new RuntimeException();
                    }
                }
            }
        }
        catch(Exception e) {}

        //"Eat the *item*"
        try{
            String word3=wordList.get(2);

            //If the first two words are "Eat the "
            Boolean word1isEat=word1.equals("Eat")||word1.equals("eat");
            if(word1isEat && word2.equals("the")){
                //If we not have already eaten 
                if(this.game.getHaveEaten()==false){
                    //Turn string into item
                    Item item=this.returnItem(word3);
                    //If we are at the item 
                    if(this.addressing.equals(item)){
                        //If the item is drinkable 
                        if(item.isEdible()){
                            this.game.changeHaveEaten(true);
                            System.out.println("\nGulllp!! Delicious");
                            System.out.println("\nCurrent status: ");
                            this.game.printNapStatus();
                            System.out.println("\nYou are currently on the pond. Try [jump off of the pond]");
                        }
                        //If the item is not  drinkable 
                    }
                    //If we are not at the item
                    else{
                        System.out.println("You need to be at the "+word3+" in order to eat it. Try [go to the "+word3+"].");
                    }
                }
                //If we have already eaten
                else{
                    System.out.println("\nYou have already eaten the "+word3+".\n");
                }
            }
        }
        catch(Exception e) {}

        //"Drink the *item*"
        try{
            String word3=wordList.get(2);

            //If the first two words are "Drink the "
            Boolean word1isDrink=word1.equals("Drink")||word1.equals("drink");
            if(word1isDrink && word2.equals("the")){
                //If we not have already drunk it 
                if(this.game.getHaveDrunk()==false){
                    //Turn string into item
                    Item item=this.returnItem(word3);

                    //If we are at the item 
                    if(this.addressing.equals(item)){
                        //If the item is drinkable 
                        if(item.isDrinkable){
                            this.game.changeHaveDrunk(true);
                            System.out.println("\nSlurrrp!!! So refreshing!");
                            System.out.println("\nCurrent status: ");
                            this.game.printNapStatus();
                            System.out.println("\nYou are climbed on the "+item.getParent().getName()+". You can [jump off of the "+item.getParent().getName()+"]");
                        }
                        //If the item is not  drinkable 
                    }
                    //If we are not at the item
                    else{
                        System.out.println("You need to be at the "+word3+" in order to drink it. Try [go to the "+word3+"].");
                    }
                }
                //If we have already drunk it 
                else{
                    System.out.println("You have already drunk the "+word3+".\n");
                }
            }
        }
        catch(Exception e) {}

        //"Take the *item*"
        //The only item you can take in the game is the blanket, so this just provides an error message
        //if someone tries to take something they shouldn't
        try{
            String word3=wordList.get(2);

            Boolean word1IsTake=word1.equals("take")||word1.equals("Take");
            //If they have said "Take the"
            if(word1IsTake && word2.equals("the")){
                Item item=this.returnItem(word3);
                //If the item is not takeable
                if(!item.isTakeable){
                    this.game.changeSuccess(true);
                    System.out.println("\nYou cannot take this item. The options for "+word3+" are: ");
                    item.showOptions();
                }
            }
        } catch (Exception e){}
    }}
