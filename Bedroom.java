import java.util.ArrayList;
import java.util.Objects;

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

        //"Take the blanket"
        try{
            String word3=wordList.get(2);

            Boolean word1IsTake=word1.equals("take")||word1.equals("Take");
            //If they said "take the blanket"
            if (word1IsTake && word2.equals("the")&& word3.equals("blanket")){
                //if they have climbed onto anything 
                if(Objects.nonNull(this.game.getClimbedOn())){
                    //if they have climbed onto the bed
                    if(this.game.getClimbedOn().equals(this.returnItem("bed"))){
                        this.game.changeHolding(this.returnItem("blanket"));
                        System.out.println("\nYou are now holding the blanket. \nCurrent status:");
                        this.game.printNapStatus();
                        this.returnItem("bed").showOptions();

                    }
                }
                //if they haven't climbed onto anything
                else{
                    System.out.println("\nYou need to climb on to the bed before taking the blanket.");
                }
            }
        } catch(Exception e){}
    

    }
}
