import java.util.ArrayList;

public class Bedroom extends Room{
    
    String name;
    String description;

    public Bedroom(String name, String description,Game game){
        super(name, description,game);
    }

    public void conversation(ArrayList<String> wordList){
        //inherit from Room
        super.conversation(wordList);
        //find first two words
        String word1=wordList.get(0);
        String word2=wordList.get(1);
        //turn second word into an item
        Item item2=this.returnItem(word2);

        if (word1.equals("take")||word1.equals("Take")){
            //if you have climbed on top of the bed 
            //if(Game.climbedOn=bed){}
                //if the item you're trying to take is the blanket 
                if (item2.equals(items.get(1))){
                    System.out.println("You are now holding the blanket.");
                    this.game.changeHolding(items.get(1));
                }
        }

    }
}
