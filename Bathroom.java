import java.util.ArrayList;

public class Bathroom extends Room{
    String name;
    String description;

    public Bathroom(String name, String description){
        super(name, description);
    }

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
                System.out.println("You cannot jump out of the bathtub. You are stuck here, napless until the end of the game.");
                //end game somehow -- add stuck in bathtub to game 
            }
        } catch (Exception e){}
    

    }
}
