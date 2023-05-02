import java.util.ArrayList; 

public class Parlor extends Room{
    String name;
    String description;

    public Parlor(String name, String description,Game game){
        super(name, description,game);
    }

    public void conversation(ArrayList<String> wordList){
        //inherit from Room
        super.conversation(wordList);
        //find first two words
        String word1=wordList.get(0);
        String word2=wordList.get(1);
        //Fixing window seat bug (two words!)
        try{
            String word3=wordList.get(2);
            String word4=wordList.get(3);
            Boolean isGo=word1.equals("go")||word1.equals("Go");
            if(isGo&&word2.equals("to")&&word3.equals("window")&&word4.equals("seat")){
                System.out.println("You are at the window seat");
                this.addressing=this.returnItem("window seat");
                this.returnItem("window seat").showOptions();
            }
        }
        catch (Exception e){}
        try{
            Boolean isClimb=word1.equals("climb")||word1.equals("Climb");
            String word3=wordList.get(2);
            //if this.addressing == window seat
              if(isClimb && word2.equals("window") && word3.equals("seat")){
                    this.game.changeClimbedOn(this.items.get(0));
                    System.out.println("You have climbed the window seat");
                }
        } 
        catch (Exception e) {}
    

    }
}
