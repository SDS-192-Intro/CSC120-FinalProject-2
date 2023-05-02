import java.util.ArrayList;

public class Kitchen extends Room{
    
    public Kitchen(String name, String description,Game game){
        super(name, description,game);
    }

    public void conversation(ArrayList<String> wordList){
        //inherit from Room
        super.conversation(wordList);
        

    }
}
