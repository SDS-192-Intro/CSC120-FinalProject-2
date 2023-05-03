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
        //Fixing window seat bug (two words!)
        try{
            String word3=wordList.get(2);
            String word4=wordList.get(3);
            String word5=wordList.get(4);
            Boolean isGo=word1.equals("go")||word1.equals("Go");
            if(isGo&&word2.equals("to")&&word3.equals("the")&&word4.equals("window")&& word5.equals("seat")){
                this.game.changeSuccess(true);
                System.out.println("You are at the window seat");
                this.addressing=this.returnItem("window seat");
                this.returnItem("window seat").showOptions();
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
                    System.out.println("You must be at the window seat in order to climb it. Try [go to the window seat].");   
                }
                if(Objects.nonNull(this.addressing)){
                    if(!this.addressing.equals(this.returnItem("window seat"))){
                        this.game.changeSuccess(true);
                        System.out.println("You must be at the window seat in order to climb it. Try [go to the window seat].");   
                    }
                    else{ 
                        this.game.changeSuccess(true);
                        this.game.changeClimbedOn(this.items.get(0));
                        System.out.println("\nYou have climbed the window seat");
                        this.game.printNapStatus();
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

        } catch (Exception e){}

        //inherit from Room
        super.conversation(wordList);
    

    }
}
