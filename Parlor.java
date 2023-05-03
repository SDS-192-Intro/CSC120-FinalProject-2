import java.util.ArrayList; 
import java.util.Objects;

public class Parlor extends Room{
    String name;
    String description;

    public Parlor(String name, String description,Game game){
        super(name, description,game);
    }

    public void conversation(ArrayList<String> wordList){
        //Redo methods for "window seat" (only item with two words)
        //find first two words
        String word1=wordList.get(0);
        String word2=wordList.get(1);
        
        //In order to send back to game class 
        Boolean b =false;

        //Fixing window seat bug (two words!)
        try{
            String word3=wordList.get(2);
            String word4=wordList.get(3);
            String word5=wordList.get(4);
            Boolean isGo=word1.equals("go")||word1.equals("Go");
            if(isGo&&word2.equals("to")&&word3.equals("the")&&word4.equals("window")&& word5.equals("seat")){
                this.game.changeSuccess(true);
                this.game.slowPrint("\nYou are at the window seat");
                this.addressing=this.returnItem("window seat");
                this.returnItem("window seat").showOptions();
                //send back to game class (don't go through methods in Room)
                b=true;
            }
        }
        catch (Exception e){}
        //"Climb the window seat"
        try{
            Boolean isClimb=word1.equals("climb")||word1.equals("Climb");
            String word3=wordList.get(2);
            String word4=wordList.get(3);
            if(isClimb && word2.equals("the") && word3.equals("window")&&word4.equals("seat")){
                //if you're not addressing any item
                if (Objects.isNull(this.addressing)){ 
                    this.game.changeSuccess(true);
                    this.game.slowPrint("\nYou must be at the window seat in order to climb it. Try [go to the window seat].");
                    //send back to game class (don't go through methods in Room)
                    b=true;   
                }
                if(Objects.nonNull(this.addressing)){
                    if(!this.addressing.equals(this.returnItem("window seat"))){
                        this.game.changeSuccess(true);
                        this.game.slowPrint("\nYou must be at the window seat in order to climb it. Try [go to the window seat].");   
                       //send back to game class (don't go through methods in Room)
                        b=true;
                    }
                    else{ 
                        this.game.changeSuccess(true);
                        this.game.changeClimbedOn(this.items.get(0));
                        //If they haven't won the game yet, give them pointers 
                        if(!this.game.canNap()){
                            this.game.changeSuccess(true);
                            this.game.slowPrint("\nYou have climbed onto the window seat BUT... ");
                            this.game.printNapStatus();
                            this.game.slowPrint("\nTry [jump off of the window seat]");
                            //send back to game class (don't go through methods in Room)
                            b=true;
                        }
                        //If they have won the game
                        else{
                            this.game.slowPrint("\nYou have climbed onto the window seat AND...");
                            this.game.printNapStatus();
                            //send back to game class (don't go through methods in Room)
                            b=true;
                        }
                    }
                }
            }
    
        } 
        catch (Exception e) {}
        //"Jump off of the window seat"
        try{
            String word3=wordList.get(2);
            String word4=wordList.get(3);
            String word5=wordList.get(4);
            String word6=wordList.get(5);

            Boolean word1isJump=word1.equals("Jump")||word1.equals("jump");
            //If they said "Jump off of the window seat"
            if(word1isJump&& word2.equals("off")&&word3.equals("of")&&word4.equals("the")&&word5.equals("window")&&word6.equals("seat")){
                Item item=this.returnItem("window seat");

                if(this.game.getClimbedOn().equals(item)){
                    //If it is jump-off-able
                    if(item.isJumpOffable){
                        //If you are addressing it 
                        if(this.addressing.equals(item)){
                            //Then you may jump off 
                            this.game.changeSuccess(true);
                            //Change climbedOn attribute in the game 
                            this.game.changeClimbedOn(null);
                            this.game.slowPrint("\nYou have jumped off of the "+item.getName()+".");
                            this.game.slowPrint("You are now standing next to the "+item.getName()+" in the "+this.getName()+".\n");
                            this.game.slowPrint(this.lookAround()+"\nThere are doors connecting to: \n");
                            this.game.printNeighbors(this);
                            //send back to game class (don't go through methods in Room)
                            b=true;
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
                                this.game.slowPrint("\nYou have jumped off of the "+item.getName()+".");
                                this.game.slowPrint("You are now standing next to the "+item.getName()+" in the "+this.getName()+".");
                                this.game.slowPrint(this.lookAround()+"\nThere are doors connecting to: \n");
                                this.game.printNeighbors(this);
                                //send back to game class (don't go through methods in Room)
                                b=true;
                                //throw runtime exception to avoid being caught in the final conditional
                                throw new RuntimeException();
                            }
                        }
                        //If you are NOT addressing the item
                        else{
                            this.game.changeSuccess(true);
                            this.game.slowPrint("\nIn order to jump off, you must first be *at* the "+item.getName()+". Try [go to the "+item.getName()+"], and then [jump off of the "+item.getName()+"].");
                            //send back to game class (don't go through methods in Room)
                            b=true;
                        }

                
                    }
            }}} catch (Exception e){}
        
        if (b){
            return;
        }
        //inherit from Room
        super.conversation(wordList);
    

    }
}
