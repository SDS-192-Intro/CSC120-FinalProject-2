import java.util.ArrayList;

/* 
 * Child class of Room
 */
public class Bathroom extends Room{
    String name;
    String description;


    /*
     * Bathroom constructor
     */
    public Bathroom(String name, String description,Game game){
        super(name, description,game);
    }

    /*
     * Addition to Room's conversation method to add the bathtub loophole
     */
    public void conversation(ArrayList<String> wordList){
        //inherit from Room
        super.conversation(wordList);
        //find first two words
        String word1=wordList.get(0);
        String word2=wordList.get(1);
        try{
            String word3=wordList.get(2);
            Boolean isJump=word1.equals("jump")||word1.equals("Jump");
            if (isJump&& word2.equals("off")&&word3.equals("bathtub")){
                this.game.changeSuccess(true);
                this.game.changeStuckInBath(true);
                this.game.slowPrint("You cannot jump out of the bathtub. You are stuck here, napless until the end of the game.");
            }
        } catch (Exception e){}
    

    }
}
